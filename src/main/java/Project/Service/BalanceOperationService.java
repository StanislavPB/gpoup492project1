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
    private Validation validation = new Validation();

    public BalanceOperationService(Repository repository) {
        this.repository = repository;
    }



    public Response<Account> addNewIncome(Integer id, double amount, String source, LocalDate date) {
        String validationResult = validation.validateIncome(id);
        if (!validationResult.isEmpty()) {
            return new Response<>(null, validationResult);
        }

        Account newIncome = new Account(amount, 0.0, source, date);
        Optional<User> userOptional = repository.findUserById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            repository.addIncomeToUser(user, newIncome);
            return new Response<>(newIncome, "Доход добавлен.");
        } else {
            return new Response<>(null, "Ошибка: пользователь не найден.");
        }
    }


    public Response<Account> addNewOutcome(Integer id, double amount, String category, LocalDate date) {
        String validationResult = validation.validateOutcome(id);
        if (!validationResult.isEmpty()) {
            return new Response<>(null, validationResult);
        }

        Account newOutcome = new Account(0.0, amount, category, date);
        Optional<User> userOptional = repository.findUserById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            repository.addOutcomeToUser(user, newOutcome);
            return new Response<>(newOutcome, "Расход добавлен.");
        } else {
            return new Response<>(null, "Ошибка: пользователь не найден.");
        }
    }
    public Response<List<Account>> getHistoryOfOperations (Integer id, LocalDate startDate, LocalDate endDate, String category) {
    Response<User> userResponse = findUserById(id);
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
                .filter(balance -> (startDate == null||balance.getDate().isAfter(startDate)) &&
                        (endDate == null || balance.getDate().isBefore(endDate)) &&
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


    public  Response<User> findUserById(Integer id){
        Optional<User> foundUser = repository.findUserById(id);
        if(foundUser.isEmpty()){
        return new Response<>(null, "Ошибка: пользователь с ID " + id + " не найден.");
    }
        return new Response<>(foundUser.get(),"");

}}
