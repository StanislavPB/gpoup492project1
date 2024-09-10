package org.gpoup492project1.Project.Repository;



import org.gpoup492project1.Project.Entity.Balance;
import org.gpoup492project1.Project.Entity.User;

import java.util.List;

public interface RepositoryInterface {
    User addUser(User user);
    void addIncomeToUser(User user, Balance income);
    void addOutcomeToUser(User user, Balance outcome);
    List<String> getCategoriesOfOutcomes();
    List<String> getSourcesOfIncome();
    List<Balance> getTransactionHistory(User user);
}
