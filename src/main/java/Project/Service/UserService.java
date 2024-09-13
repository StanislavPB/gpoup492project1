package Project.Service;

import Project.DTO.Response;
import Project.Entity.Account;
import Project.Entity.User;
import Project.Repository.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class UserService {
    private Repository repository;
    private Validation validation;


    public UserService(Repository repository, Validation validation) {
        this.repository = repository;
        this.validation = validation;
    }
    public Response<User> addNewUser(String name){
        User user = new User(0, name, new ArrayList<>());
        String validationResult = validation.validateUser(user);
        if(!validationResult.isEmpty()){
            return new Response<>(null,validationResult);
        }
        User newUser = repository.addUser(user);
        return new Response<>(newUser,"");
    }

    public  Response<User> findUserById(Integer id){
        Optional<User> foundUser = repository.findUserById(id);
        if(foundUser.isEmpty()){
            return new Response<>(null, "Ошибка: пользователь с ID " + id + " не найден.");
        }
        return new Response<>(foundUser.get(),"");
    }

    public Response<Account> addNewIncomeToUser(Integer id, double amount, String source, LocalDate date) {
        String validationResult = validation.validateIncome(amount);
        if (!validationResult.isEmpty()) {
            return new Response<>(null, validationResult);
        }

        Account newIncome = new Account(amount, 0.0, source, date);
        Optional<User> userOptional = repository.findUserById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            repository.addIncomeToUser(user, newIncome);
            return new Response<>(newIncome, "");
        } else {
            return new Response<>(null, "Ошибка: пользователь не найден.");
        }
    }


    public Response<Account> addNewOutcomeToUser(Integer id, double amount, String category, LocalDate date) {
        String validationResult = validation.validateOutcome(amount);
        if (!validationResult.isEmpty()) {
            return new Response<>(null, validationResult);
        }

        Account newOutcome = new Account(0.0, amount, category, date);
        Optional<User> userOptional = repository.findUserById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            repository.addOutcomeToUser(user, newOutcome);
            return new Response<>(newOutcome, "");
        } else {
            return new Response<>(null, "Ошибка: пользователь не найден.");
        }
    }


}
