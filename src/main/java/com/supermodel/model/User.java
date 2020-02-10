package com.supermodel.model;

import com.supermodel.service.SupermodelService;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String runway;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public Boolean addSupermodelToRunway(String supermodelId){
        for(String smID: runway.split(",") ){
            if(smID == supermodelId){ return false; }
        }
        runway += supermodelId + ",";
        return true;
    }

    public String getRunway() {
        return runway.substring(0,runway.length()-1);
    }
}
