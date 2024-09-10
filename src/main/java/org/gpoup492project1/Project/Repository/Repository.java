package org.gpoup492project1.Project.Repository;

import org.gpoup492project1.Project.Entity.Balance;
import org.gpoup492project1.Project.Entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Repository implements RepositoryInterface{
    private List<User> users = new ArrayList<>();
    private Integer userId=0;

    @Override
    public User addUser(User user) {
        userId++;
        user.setId(userId);
        users.add(user);
        return user;
    }

    @Override
    public void addIncomeToUser(User user, Balance income) {
        Optional<User>foundUser = findUserById(user.getId());
        if(foundUser.isPresent()){
            foundUser.get().getBalances().add(income);
        }
    }

    @Override
    public void addOutcomeToUser(User user, Balance outcome) {
    Optional<User> foundUser = findUserById(user.getId());
    if(foundUser.isPresent()){
        foundUser.get().getBalances().add(outcome);
    }
    }

    @Override
    public List<String> getCategoriesOfOutcomes() {
        return List.of("Еда","Транспорт","Развлечения","Страховка","Инвестиции");
    }

    @Override
    public List<String> getSourcesOfIncome() {
        return List.of("Зарплата","Инвестиции","Аренда","Сетевой маркетинг","Дивиденды");
    }

    @Override
    public List<Balance> getTransactionHistory(User user) {
        Optional<User> foundUser = findUserById(user.getId());
        if(foundUser.isPresent()){
            return foundUser.get().getBalances();
        }
        return new ArrayList<>();
    }


    public Optional<User> findUserById(Integer id){
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }
}


