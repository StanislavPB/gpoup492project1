package Project.Repository;



import Project.Entity.Account;
import Project.Entity.User;

import java.util.List;

public interface RepositoryInterface {
    User addUser(User user);
    void addIncomeToUser(User user, Account income);
    void addOutcomeToUser(User user, Account outcome);
    List<String> getCategoriesOfOutcomes();
    List<String> getSourcesOfIncome();
    List<Account> getTransactionHistory(User user);
}
