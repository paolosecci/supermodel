package com.supermodel.api;

import com.supermodel.model.Supermodel;
import com.supermodel.model.User;
import com.supermodel.service.SupermodelService;
import com.supermodel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("supermodel")
public class SupermodelController {

    @Autowired
    private SupermodelService supermodelService;

    @Autowired
    private UserService userService;

    @PostMapping(path = "/add")
    public @ResponseBody
    String addSupermodel(@RequestParam String name, @RequestParam String imgUrl) {
        return supermodelService.addSupermodel(name, imgUrl);
    }

    @PostMapping(path = "/add98")
    public @ResponseBody
    String addSupermodels(@RequestParam List<String> names, @RequestParam List<String> imgUrls) {
        return supermodelService.addSupermodels(names, imgUrls);
    }

    @GetMapping(path="/api/supermodels")
    public @ResponseBody
    List<Supermodel> getSupermodels() {
        return supermodelService.getSupermodels();
    }

    @GetMapping(path="/api/runway")
    public @ResponseBody
    List<Supermodel> getRunway(@CookieValue("SupermodelUserId") String userId){
        return userService.getUserRunway(Integer.parseInt(userId));
    }

    @DeleteMapping(path = "/self-destruct")
    public @ResponseBody
    String deleteAllSupermodels() {
        return supermodelService.deleteAllSupermodels();
    }

    @PostMapping(path = "/update-{oldName}") // path_example: "127.0.0.1:8080/supermodel/update-adriana"
    public @ResponseBody
    String updateSupermodelByName(@PathVariable("oldName") String oldName, @RequestParam String name, @RequestParam String imgUrl) {
        return supermodelService.updateSupermodelByName(oldName, name, imgUrl);
    }

    @PostMapping(path = "/user/addRunway")
    public @ResponseBody Boolean postUserNewRunway(@RequestParam String allRunwayData) {
        String[] runwayCookies = allRunwayData.split(";");
        return true;
    }

    @GetMapping(path="/")
    public String entryPoint(){
        return "home";
    }

    @GetMapping(path="/login")
    public String promptUserLogin(HttpServletRequest request){
        // get client's IP
        String clientIP = request.getRemoteAddr();
        System.out.println(clientIP);

        return "login";
    }

    @GetMapping(path="/register")
    public String registerUserPage(){
        return "register";
    }

    @GetMapping(path="/login-{usernamePasswordHash}")
    public @ResponseBody String validateUser(@PathVariable("usernamePasswordHash") String uph, HttpServletRequest request, Model model){

        System.out.println("Logging in User");

        // get client's IP
        String clientIP = request.getRemoteAddr();
        System.out.println(clientIP);

        String[] usernamePassword = userService.unhash(uph);

        String username = usernamePassword[0];
        String password = usernamePassword[1];

        Boolean re = userService.validateUser(username, password);

        System.out.println(re);

        if(re){
            //account exists, log in
            User user = userService.findByUsername("username");
            return user.getUsername() + "%" + user.getId();
        }else{
            //wrong password, might b a hacker
            return null;
        }
    }

    @GetMapping(path="/register-{usernamePasswordHash}")
    public @ResponseBody String addUser(@PathVariable("usernamePasswordHash") String uph, HttpServletRequest request, Model model){

        // get client's IP
        String clientIP = request.getRemoteAddr();
        System.out.println(clientIP);

        String[] usernamePassword = userService.unhash(uph);

        String username = usernamePassword[0];
        String password = usernamePassword[1];

        if(userService.findByUsername(username) == null){
            System.out.println("Registering User.");
            userService.addUser(username, password);
            int userId = userService.getUserId(username);
            return username + "%" + userId;
        }else{
            System.out.println("User Already Registered...");
            return "%" + userService.getUserId(username);
        }
    }

    @GetMapping(path="/users/list")
    public @ResponseBody String listUsers(){
        return userService.getUsers().toString();
    }

    @GetMapping(path = "/in")
    public String returnUserHome(HttpServletRequest request){

        // get client's IP
        String clientIP = request.getRemoteAddr();
        System.out.println(clientIP);

        return "userHome";
    }

    @GetMapping(path = "/browse")
    public String returnRosterPage(HttpServletRequest request, Model model){

        // get client's IP
        String clientIP = request.getRemoteAddr();
        System.out.println(clientIP);

        return "browse";
    }

    @GetMapping(path = "{name}")
    public String getDataToPopulateTemplate(@PathVariable("name") String name, HttpServletRequest request, Model model) {

        // get client's IP
        String clientIP = request.getRemoteAddr();
        System.out.println(clientIP);

        //find supermodel in db by name
        Supermodel supermodel = supermodelService.findByName(name);

        //error handling if not in DB
        if (supermodel == null) {
            name = "Error: " + name.toUpperCase() + " not found in DB.";
            supermodel = new Supermodel();
            supermodel.setName("error");
            supermodel.setImgUrl("https://media3.giphy.com/media/V8vOT1JVj1ok/giphy.gif");
        } else {
            name = name.toUpperCase();
        }

        //add supermodel's name and url to spring model as attribute
        model.addAttribute("supermodelId", supermodel.getId());
        model.addAttribute("NAME", name);
        model.addAttribute("imgUrl", supermodel.getImgUrl());

        return "supermodel";
    }

    @GetMapping(path = "/runway-{name}")
    public @ResponseBody String addSupermodelToRunway(@PathVariable String name, HttpServletRequest request, HttpServletResponse response) {

        // get client's IP
        String clientIP = request.getRemoteAddr();
        System.out.println(clientIP);

        //find supermodel in db by name
        Supermodel supermodel = supermodelService.findByName(name);

        //error handling if not in DB
        if (supermodel == null) {
            return "Error: " + name.toUpperCase() + " not found in DB.";
        } else {
            //increase supermodel's runway count
            supermodelService.incrementRunwayCount(supermodel.getId());

            //add supermodel to cookie store
            response.addCookie(new Cookie(supermodel.getName(), supermodel.getId() + "," + supermodel
            .getImgUrl()));
        }
        return name.toUpperCase() + " has been added to runway.";
    }

    @GetMapping(path="/runway/stage")
    public String returnRunwayStage(@CookieValue(value="runway", defaultValue="gucci") String runwayCookie, HttpServletRequest request){
        System.out.println(runwayCookie);
        return "runwayStage";
    }
}
