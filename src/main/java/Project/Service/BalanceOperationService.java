package Project.Service;

import Project.DTO.Response;
import Project.Entity.Balance;
import Project.Entity.User;
import Project.Repository.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BalanceOperationService {
    private final Repository repository;
    private final Scanner scanner = new Scanner(System.in);
    private Validation validation = new Validation();

    public BalanceOperationService(Repository repository) {
        this.repository = repository;
    }



    public Response<Balance> addNewIncome(Integer id, double amount, String source, LocalDate date) {
        String validationResult = validation.validateIncome(id);
        if (!validationResult.isEmpty()) {
            return new Response<>(null, validationResult);
        }

        Balance newIncome = new Balance(amount, 0.0, source, date);
        Optional<User> userOptional = repository.findUserById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            repository.addIncomeToUser(user, newIncome);
            return new Response<>(newIncome, "Доход добавлен.");
        } else {
            return new Response<>(null, "Ошибка: пользователь не найден.");
        }
    }


    public Response<Balance> addNewOutcome(Integer id, double amount, String category, LocalDate date) {
        String validationResult = validation.validateOutcome(id);
        if (!validationResult.isEmpty()) {
            return new Response<>(null, validationResult);
        }

        Balance newOutcome = new Balance(0.0, amount, category, date);
        Optional<User> userOptional = repository.findUserById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            repository.addOutcomeToUser(user, newOutcome);
            return new Response<>(newOutcome, "Расход добавлен.");
        } else {
            return new Response<>(null, "Ошибка: пользователь не найден.");
        }
    }
    public Response<List<Balance>> getHistoryOfOperations (Integer id, LocalDate startDate, LocalDate endDate, String category) {
    Response<User> userResponse = findUserById(id);
    if(!userResponse.getError().isEmpty()){
        return new Response<>(null, userResponse.getError());
    }
       User user = userResponse.getBody();
    List<Balance> history = user.getBalances();
    List<Balance> filteredBalances = history.stream()
            .filter(balance -> (startDate == null||!balance.getDate().isBefore(startDate))&&
                               (endDate==null||!balance.getDate().isAfter(endDate)) &&
                               (category==null||category.isEmpty()||balance.getCategory().equalsIgnoreCase(category)))
            .collect(Collectors.toList());
            return new Response<>(filteredBalances, "");
    }

    public Response<String> generateReport(Integer id, LocalDate startDate, LocalDate endDate, String category) {
        Response<User> userResponse = findUserById(id);
        if (!userResponse.getError().isEmpty()) {
            return new Response<>(null, userResponse.getError());
        }
        User user = userResponse.getBody();
        List<Balance> history = user.getBalances();

        List<Balance> filteredBalances = history.stream()
                .filter(balance -> (startDate == null || !balance.getDate().isBefore(startDate)) &&
                        (endDate == null || !balance.getDate().isAfter(endDate)) &&
                        (category == null || category.isEmpty() || balance.getCategory().equalsIgnoreCase(category)))
                .collect(Collectors.toList());

        StringBuilder report = new StringBuilder("Отчет о балансе пользователя " + user.getName() + ":\n");
        filteredBalances.forEach(balance -> report.append(balance.toString()).append("\n"));
        if(filteredBalances.isEmpty()){
            report.append("Нет операций за указанный период.");
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
