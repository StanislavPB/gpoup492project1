package Project.Service;

import Project.DTO.Response;
import Project.Entity.User;
import Project.Repository.Repository;

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
