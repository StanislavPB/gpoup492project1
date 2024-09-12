package Project.UserMenu;

import Project.DTO.Response;
import Project.Entity.Account;
import Project.Entity.User;
import Project.Repository.Repository;
import Project.Service.BalanceOperationService;
import Project.Service.UserService;
import Project.Service.Validation;

import java.time.LocalDate;
import java.util.List;
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
    Integer id = validation.getValidateId();
    Response<User> userResponse = balanceOperationService.findUserById(id);
    if (!userResponse.getError().isEmpty()) {
        System.out.println("Ошибка: " + userResponse.getError());
        return;
    }
    User user = userResponse.getBody();
    String source = chooseFromList(repository.getSourcesOfIncome(),"Выберите источник дохода:");
    System.out.println("Введите сумму дохода: ");
    double amount = validation.getDoubleInput();
    System.out.println("Введите дату дохода (в формате ГГГГ-ММ-ДД): ");
    LocalDate date = validation.getDateInput();

    Response<Account> response = balanceOperationService.addNewIncome(user.getId(), amount, source, date);

    if (!response.getError().isEmpty()) {
        System.out.println("Ошибка: " + response.getError());
    } else {
        System.out.println("Доход добавлен: " + response.getBody().toString());
    }
}

private void addOutcome(){
    Integer id = validation.getValidateId();
    Response<User> userResponse = balanceOperationService.findUserById(id);
    if(!userResponse.getError().isEmpty()){
        System.out.println("Ошибка: " + userResponse.getError());
        return;
    }
    User user = userResponse.getBody();
    String category = chooseFromList(repository.getCategoriesOfOutcomes(), "Выберите категорию расхода:");

    System.out.println("Введите сумму расхода: ");
    double amount = validation.getDoubleInput();

    System.out.println("Введите дату расхода (в формате ГГГГ-ММ-ДД): ");
    LocalDate date = validation.getDateInput();

    Response<Account> response = balanceOperationService.addNewOutcome(user.getId(), amount, category, date);

    if (!response.getError().isEmpty()) {
        System.out.println("Ошибка: " + response.getError());
    } else {
        System.out.println("Расход добавлен: " + response.getBody().toString());
    }
}
private void showHistory(){
    Integer id = validation.getValidateId();

     System.out.println("Введите начальную дату (в формате ГГГГ-ММ-ДД) или оставьте пустым для пропуска: ");
     LocalDate startDate = validation.getDateInput();
     System.out.println("Введите конечную дату (в формате ГГГГ-ММ-ДД) или оставьте пустым для пропуска: ");
     LocalDate endDate = validation.getDateInput();


    Response<List<Account>> response = balanceOperationService.getHistoryOfOperations(id, startDate, endDate,  null);
    if (!response.getError().isEmpty()) {
        System.out.println("Ошибка: " + response.getError());
    } else {
        List<Account> history = response.getBody();
        if (history.isEmpty()) {
            System.out.println("Операции не найдены.");
        } else {
            System.out.println("История операций:");
            history.forEach(operation -> System.out.println(operation.toString()));
        }
}}
    private void generateReport() {
        Integer id = validation.getValidateId();
        System.out.println("Введите начальную дату (в формате ГГГГ-ММ-ДД) или оставьте пустым для просмотра всех операций: ");
        LocalDate startDate = getValidDateInput();
        System.out.println("Введите конечную дату (в формате ГГГГ-ММ-ДД) или оставьте пустым для просмотра всех операций: ");
        LocalDate endDate = getValidDateInput();

        Response<String> reportResponse = balanceOperationService.generateReport(id, startDate, endDate, null);

        if (!reportResponse.getError().isEmpty()) {
            System.out.println("Ошибка: " + reportResponse.getError());
        } else {
            System.out.println(reportResponse.getBody());
        }

    }
    private String chooseFromList(List<String> options, String message){
        Scanner scanner = new Scanner(System.in);
        String choice = null;
        while (choice == null) {
            System.out.println(message);
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }
            System.out.println("Выберите номер из списка: ");
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                return "";
            }

            try {
                int selectedOption = Integer.parseInt(input);
                if (selectedOption > 0 && selectedOption <= options.size()) {
                    choice = options.get(selectedOption - 1);
                } else {
                    System.out.println("Ошибка: выберите номер из списка.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите числовое значение.");
            }
        }

            return choice;
        }


    private LocalDate getValidDateInput() {
        return validation.getDateInput();
    }
}
