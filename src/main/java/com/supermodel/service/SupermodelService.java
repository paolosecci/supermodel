package com.supermodel.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.supermodel.dao.SupermodelRepository;
import com.supermodel.model.Supermodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SupermodelService{

    private SupermodelRepository supermodelRepo;

    @Autowired
    public SupermodelService(@Qualifier("mysql") SupermodelRepository supermodelRepo) {

        this.supermodelRepo = supermodelRepo;
    }

    public String addSupermodel(String name, String imgUrl){

        //build new supermodel
        Supermodel supermodel = new Supermodel();
        supermodel.setName(name);
        supermodel.setImgUrl(imgUrl);

        //save new supermodel to db
        supermodelRepo.save(supermodel);

        return "\n\n$PDBM >> Added " + name + " to DB.\n";
    }

    public Iterable<Supermodel> getSupermodels() {

        return supermodelRepo.findAll();
    }

    public Supermodel findByName(String name){

        return supermodelRepo.findByName(name);
    }

    public String deleteAllSupermodels() {

        supermodelRepo.deleteAll();

        return "\n\n$PDBM >> DB Emptied.\n";
    }

    public String incrementRunwayCount(Integer id){

        //get supermodel
        Supermodel supermodel = supermodelRepo.findById(id).orElse(null);

        if(supermodel == null){
            return "\n\n$PDBM >> entry with id=" + id + " not found in db.";
        }

        //increment runway count
        supermodel.setRunwayCount(supermodel.getRunwayCount() + 1);

        //save back to db
        supermodelRepo.save(supermodel);

        return "\n\n$PDBM >> " + supermodel.getName().toUpperCase() + " is on the runway.";
    }

    public String updateSupermodelByName(String oldName, String newName, String newImgUrl) {

        //remove old supermodel
        Supermodel newSupermodel = supermodelRepo.findByName(oldName);
        newSupermodel.setName(newName);
        newSupermodel.setImgUrl(newImgUrl);
        supermodelRepo.save(newSupermodel);

        return  "\n\n$PDBM >> Supermodel Updated." +
                "\n      >> Deleted " + oldName + " from DB." +
                "\n      >> Added " + newName + " to DB.\n";
    }

    public String addSupermodels(List<String> names, List<String> imgUrls) {
        if (names.size() != imgUrls.size()){
            return  "\n\n$PDBM >> 498: names.size() != imgUrls.size()" +
                    "\n      >> DB not edited.\n";
        }

        String outStr = "\n\n$PDBM >> Added the following " + names.size() + " supermodels to DB:";
        for(int i = 0; i < names.size(); i++){
            Supermodel supermodel = new Supermodel();
            supermodel.setName(names.get(i));
            supermodel.setImgUrl(imgUrls.get(i));

            supermodelRepo.save(supermodel);
            outStr += "\n      >> " + supermodel.getName();
        }
        outStr += "\n";

        return outStr;
    }

    public String jsonify(Iterable<Supermodel> supermodels) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        List<Supermodel> supermodelList = StreamSupport.stream(supermodels.spliterator(),false).collect(Collectors.toList());

        String jsonStr = om.writeValueAsString(supermodelList);
        return jsonStr;
    }
}
