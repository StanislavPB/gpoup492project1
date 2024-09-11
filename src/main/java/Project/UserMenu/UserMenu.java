package Project.UserMenu;

import Project.DTO.Response;
import Project.Entity.Balance;
import Project.Entity.User;
import Project.Repository.Repository;
import Project.Service.BalanceOperationService;
import Project.Service.UserService;
import Project.Service.Validation;

import java.time.LocalDate;
import java.util.Scanner;

public class UserMenu {
    private final Repository repository = new Repository();
    private final Validation validation = new Validation();
    private final UserService userService = new UserService(repository, validation);
    private final Scanner scanner = new Scanner(System.in);
    private final BalanceOperationService balanceOperationService = new BalanceOperationService(repository);

    public void menuStart(){
        boolean working = true;
        while (working) {
            System.out.println("Меню:");
            System.out.println("1. Добавить пользователя");
            System.out.println("2. Ввести данные о доходе");
            System.out.println("3. Ввести данные о расходе");
            System.out.println("4. Вывести информацию о доходах и расходах, отфильтрованных по датам и категориям");
            System.out.println("5. Сгенерировать сводный отчет по месяцам");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    addIncome();
                    break;
                case 3:
                    addOutcome();
                    break;
                case 4:
                    showHistory();
                    break;
                case 5:
                    generateReport();
                    break;
                case 0:
                    working = false;
                    System.out.println("Выход из программы");
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова");
                    break;
            }
        }
    }
    public void addUser(){
        System.out.println("Введите имя пользователя: ");
        String name = scanner.nextLine();
        Response<User> response = userService.addNewUser(name);

        if (!response.getError().isEmpty()) {
            System.out.println("Ошибка: " + response.getError());
        } else {
            System.out.println("Пользователь успешно добавлен: " + response.getBody().toString());
        }
    }
private void addIncome(){
    System.out.println("Введите ID пользователя: ");
    Integer id = scanner.nextInt();
    scanner.nextLine();
    Response<Balance> response = balanceOperationService.addNewIncome(id);

    if (response.getError().isEmpty()) {
        System.out.println("Ошибка: " + response.getError());
    } else {
        System.out.println("Доход добавлен: " + response.getBody().toString());
    }
}
private void addOutcome(){
    System.out.println("Введите ID пользователя: ");
    Integer id = scanner.nextInt();
    scanner.nextLine();
    Response<Balance> response = balanceOperationService.addNewOutcome(id);

    if (response.getError().isEmpty()) {
        System.out.println("Ошибка: " + response.getError());
    } else {
        System.out.println("Расход добавлен: " + response.getBody().toString());
    }
}
private void showHistory(){
System.out.println("Введите ID пользователя: ");
    Integer id = scanner.nextInt();
    balanceOperationService.showHistoryOfOperations(id);
}
    private void generateReport() {
        System.out.print("Введите ID пользователя: ");
        Integer id = scanner.nextInt();
        scanner.nextLine();
        LocalDate startDate = getValidDateInput("Введите начальную дату (YYYY-MM-DD): ");
        LocalDate endDate = getValidDateInput("Введите конечную дату (YYYY-MM-DD): ");

        Response<String> reportResponse = balanceOperationService.generateReport(id, startDate, endDate);

        if (!reportResponse.getError().isEmpty()) {
            System.out.println("Ошибка: " + reportResponse.getError());
        } else {
            System.out.println(reportResponse.getBody());
        }

    }

    private LocalDate getValidDateInput(String prompt) {
        return validation.getDateInput();
    }
}
