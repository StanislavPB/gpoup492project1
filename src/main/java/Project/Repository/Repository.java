package Project.Repository;

import Project.Entity.Account;
import Project.Entity.User;

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
    public void addIncomeToUser(User user, Account income) {
        Optional<User>foundUser = findUserById(user.getId());
        if(foundUser.isPresent()){
            foundUser.get().getBalances().add(income);
        }
    }

    @Override
    public void addOutcomeToUser(User user, Account outcome) {
    Optional<User> foundUser = findUserById(user.getId());
    if(foundUser.isPresent()){
        foundUser.get().getBalances().add(outcome);
    }
    }

    @Override
    public List<String> getCategoriesOfOutcomes() {
        return List.of("Еда","Транспорт","Развлечения","Страховка","Инвестиции","Другое");
    }

    @Override
    public List<String> getSourcesOfIncome() {
        return List.of("Зарплата","Инвестиции","Аренда","Сетевой маркетинг","Другое");
    }

    @Override
    public List<Account> getTransactionHistory(User user) {
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


