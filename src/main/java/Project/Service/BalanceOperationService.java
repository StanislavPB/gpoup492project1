package Project.Service;

import Project.DTO.Response;
import Project.Entity.Account;
import Project.Entity.User;
import Project.Repository.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BalanceOperationService {

    private final Repository repository;
    private final Validation validation;
    private final UserService userService;
    public BalanceOperationService(Repository repository, Validation validation) {
        this.repository = repository;
        this.validation = validation;
        this.userService = new UserService(repository, validation);
    }




    public Response<List<Account>> getHistoryOfOperations (Integer id, LocalDate startDate, LocalDate endDate, String category) {
    Response<User> userResponse = userService.findUserById(id);
    if(!userResponse.getError().isEmpty()){
        return new Response<>(null, userResponse.getError());
    }
       User user = userResponse.getBody();
    List<Account> history = user.getBalances();
    List<Account> filteredAccounts = filteredBalances(history,startDate,endDate,category);
            return new Response<>(filteredAccounts, "");
    }
    public List<Account> filteredBalances(List<Account> history, LocalDate startDate, LocalDate endDate, String category) {
        return history.stream()
                .filter(balance -> (startDate == null||!balance.getDate().isBefore(startDate)) &&
                        (endDate == null || !balance.getDate().isAfter(endDate)) &&
                        (category == null || balance.getCategory().equalsIgnoreCase(category)))
                .collect(Collectors.toList());
    }


    public Response<String> generateReport(Integer id, LocalDate startDate, LocalDate endDate, String category) {
        Response<List<Account>> historyResponse = getHistoryOfOperations(id, startDate, endDate, category);
        if (!historyResponse.getError().isEmpty()) {
            return new Response<>(null, historyResponse.getError());
        }

        List<Account> filteredAccounts = historyResponse.getBody();

        StringBuilder report = new StringBuilder("Отчет о балансе пользователя:\n");
        double totalIncome = 0;
        double totalOutcome = 0;

        for (Account account : filteredAccounts){
            report.append(account.toString()).append("\n");
            totalIncome += account.getIncome();
            totalOutcome += account.getOutcome();
        }
        double remainingBalance = totalIncome - totalOutcome;

        if(filteredAccounts.isEmpty()){
            report.append("Нет операций за указанный период.\n");
    }else {
            report.append("\n Общий доход: ").append(totalIncome)
                    .append("\n Общие расходы: ").append(totalOutcome)
                    .append("\n Остаток на счете: ").append(remainingBalance);
        }
    return new Response<>(report.toString(),"");
    }


}
