package org.gpoup492project1.Project.Service;


import org.gpoup492project1.Project.DTO.Response;
import org.gpoup492project1.Project.Entity.User;
import org.gpoup492project1.Project.Repository.Repository;

import java.util.ArrayList;

public class UserService {

    private Repository repository = new Repository();
    private Validation validation = new Validation();


    public UserService(Repository repository, Validation validation) {
        this.repository = repository;
        this.validation = validation;
    }
    public Response<User> addNewUser(String name){
        User user = new User(null, name, new ArrayList<>());
        String validationResult = validation.validateUser(user);
        if(!validationResult.isEmpty()){
            return new Response<>(null,validationResult);
        }
        User newUser = repository.addUser(user);
        return new Response<>(newUser,"");
    }


}
