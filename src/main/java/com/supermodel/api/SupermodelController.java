package com.supermodel.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.supermodel.model.Supermodel;
import com.supermodel.service.SupermodelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping("supermodel")
public class SupermodelController {

    @Autowired
    private SupermodelService supermodelService;

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

    @GetMapping(path = "/api/supermodels")
    public @ResponseBody
    List<Supermodel> getSupermodels() {
        return StreamSupport.stream(supermodelService.getSupermodels().spliterator(),false).collect(Collectors.toList());
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

    @GetMapping(path = "/")
    public String returnRosterPage(HttpServletRequest request, Model model) throws JsonProcessingException {

        // get client's IP
        String clientIP = request.getRemoteAddr();
        System.out.println(clientIP);

        //cast supermodelDB data into String from JSONArray (using jsonify service.method)
        String jsonStr = supermodelService.jsonify(supermodelService.getSupermodels());

        // add json as spring MODEL attribute
        model.addAttribute("dataJson", jsonStr);

        return "home";
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

    @GetMapping(path="/runway/v1")
    public @ResponseBody String launchRunway(@CookieValue(value="runway", defaultValue="gucci") String runwayCookie, HttpServletRequest request){
        Cookie[] cookie_jar = request.getCookies();
        System.out.println(runwayCookie);
        return "Check browser cookies for runway.";
    }
}
