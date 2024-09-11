package Project.Repository;



import Project.Entity.Balance;
import Project.Entity.User;

import java.util.List;

public interface RepositoryInterface {
    User addUser(User user);
    void addIncomeToUser(User user, Balance income);
    void addOutcomeToUser(User user, Balance outcome);
    List<String> getCategoriesOfOutcomes();
    List<String> getSourcesOfIncome();
    List<Balance> getTransactionHistory(User user);
}
