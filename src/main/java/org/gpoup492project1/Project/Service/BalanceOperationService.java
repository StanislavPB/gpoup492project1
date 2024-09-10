package org.gpoup492project1.Project.Service;

import org.gpoup492project1.Project.DTO.Response;
import org.gpoup492project1.Project.Entity.Balance;
import org.gpoup492project1.Project.Entity.User;
import org.gpoup492project1.Project.Repository.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class BalanceOperationService {
    private final Repository repository;
    private final Scanner scanner = new Scanner(System.in);
    private Validation validation = new Validation();

    public BalanceOperationService(Repository repository) {
        this.repository = repository;
    }


    private String chooseFromList(List<String> options, String message){
        System.out.println(message);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i+1)+". "+options.get(i));
        }
        int choice=-1;
        while (choice<1||choice>options.size()){
            System.out.println("Выберите номер из списка:");
            choice = Integer.parseInt(scanner.nextLine());
        }
        return options.get(choice-1);
    }

    public Response<Balance> addNewIncome(Integer id) {
        String validationResult = validation.validateIncome(id);
        if (!validationResult.isEmpty()) {
            return new Response<>(null, validationResult);
        }

        String source = chooseFromList(repository.getSourcesOfIncome(), "Выберите источник дохода:");

        System.out.println("Введите сумму дохода:");
        double amount = validation.getDoubleInput();

        LocalDate date = validation.getDateInput();

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
    public Response<Balance> addNewOutcome(Integer id) {
        String validationResult = validation.validateOutcome(id);
        if (!validationResult.isEmpty()) {
            return new Response<>(null, validationResult);
        }

        String category = chooseFromList(repository.getCategoriesOfOutcomes(), "Выберите категорию расхода:");

        System.out.println("Введите сумму расхода:");
        double amount = validation.getDoubleInput();

        LocalDate date = validation.getDateInput();

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
    public void showHistoryOfOperations (Integer id) {
    Response<User> userResponse = findUserById(id);
    if(!userResponse.getError().isEmpty()) {
        System.out.println(userResponse.getError());
        return;
    }
       User user = userResponse.getBody();
    List<Balance> history = repository.getTransactionHistory(user);
    history.forEach(System.out::println);
    }

    public Response<String> generateReport(Integer id, LocalDate startDate, LocalDate endDate) {
        Response<User> userResponse = findUserById(id);
        if (!userResponse.getError().isEmpty()) {
            return new Response<>(null, userResponse.getError());
        }
        User user = userResponse.getBody();
        double totalIncome = 0.0;
        double totalOutcome = 0.0;

        List<Balance> history = repository.getTransactionHistory(user);
        for (Balance balance : history) {
            if (!balance.getDate().isBefore(startDate) && !balance.getDate().isAfter(endDate)) {
                totalIncome += balance.getIncome();
                totalOutcome += balance.getOutcome();
            }
        }

        String report = "Отчет с " + startDate + " по " + endDate +
                "\nОбщий доход: " + totalIncome +
                "\nОбщий расход: " + totalOutcome +
                "\nБаланс: " + (totalIncome - totalOutcome);
        return new Response<>(report, "");
    }





    public  Response<User> findUserById(Integer id){
        Optional<User> foundUser = repository.findUserById(id);
        if(foundUser.isEmpty()){
        return new Response<>(null, "Ошибка: пользователь с ID " + id + " не найден.");
    }
        return new Response<>(foundUser.get(),"");

}}
