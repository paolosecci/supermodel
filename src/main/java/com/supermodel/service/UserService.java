package com.supermodel.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supermodel.dao.UserRepository;
import com.supermodel.model.Supermodel;
import com.supermodel.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService{

    private UserRepository userRepo;

    private List<Supermodel> runway;
    SupermodelService supermodelService;

    @Autowired
    public UserService(@Qualifier("mysqlUsers") UserRepository userRepo) {

        this.userRepo = userRepo;
    }

    public String addUser(String username, String password){

        //build new user
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        //save new user to db
        userRepo.save(user);

        return "\n\n$PDBM >> Added " + username + " to User Table in supermodelDB.\n";
    }

    public Iterable<User> getUsers() {

        return userRepo.findAll();
    }

    public User findByUsername(String username){

        return userRepo.findByUsername(username);
    }

    public String deleteAllUsers() {

        userRepo.deleteAll();

        return "\n\n$PDBM >> User Table in supermodelDB Emptied.\n";
    }


    public String updateUserByUsername(String oldUsername, String newUsername, String newPassword) {

        //update old user
        User newUser = userRepo.findByUsername(oldUsername);
        newUser.setUsername(newUsername);
        newUser.setPassword(newPassword);
        userRepo.save(newUser);

        return  "\n\n$PDBM >> Supermodel Updated." +
                "\n      >> Deleted " + oldUsername + " from User Table of supermodelDB." +
                "\n      >> Added " + newUsername + " to User Table of supermodelDB.\n";
    }

    public Boolean validateUser(String username, String password){
        //1: valid user correct password
        //0:
        User user = userRepo.findByUsername(username);
        if(user == null){return false;}

        return user.getPassword().equals(password);
    }

    public String[] unhash(String uph) {
        String sauce = "";
        int hash = 7;
        String[] yosemite = uph.split(":");

        for(int i = 0; i < yosemite.length; i++){
            hash *= 3;

            int asciiValue = Integer.parseInt(yosemite[i]) / hash;
            sauce += Character.toString((char) asciiValue);
        }

        return sauce.split("%");

    }

    public List<Supermodel> getUserRunway(int userId){
        //create runway List
        List<Supermodel> runway = new ArrayList<>();

        //get user
        User user = userRepo.findById(userId).orElse(null);
        if(user == null){return null;}

        for(String supermodelId: user.getRunway().split(",")){
            runway.add(supermodelService.findById(Integer.parseInt(supermodelId)));
        }

        return runway;
    }

    public Boolean addSupermodelToRunway(int supermodelId){
        if(runway.contains(supermodelService.findById(supermodelId))){
            return false;
        }else{
            runway.add(supermodelService.findById(supermodelId));
            return true;
        }
    }

    public Integer getUserId(String username){
        return findByUsername(username).getId();
    }

    public void setRunway(List<Supermodel> runway) {
        this.runway = runway;
    }
}
