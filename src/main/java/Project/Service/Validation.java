package Project.Service;

import Project.Entity.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Validation {

    public String validateUser(User user){
        if (user == null||user.getName()==null||user.getName().isEmpty()){
            return "Ошибка: имя пользователя не может быть пустым.\n";
        }
        return "";
}
    public String validateIncome(double amount) {

        if (amount <= 0) {
            return "Ошибка: доход должен быть положительным числом.\n";
        }
        return "";
    }

        public String validateOutcome(double amount){
        if(amount<=0){
            return "Ошибка: расход должен быть положительным числом.\n";
        }
        return "";
    }
    public String validateDate(LocalDate date) {
        if (date == null) {
            return "Ошибка: дата не может быть пустой.";
        }
        if (date.isAfter(LocalDate.now())) {
            return "Ошибка: дата из будущего.";
        }
        return "";
    }
    public LocalDate getDateInput(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Scanner scanner = new Scanner(System.in);
        LocalDate date = null;
        boolean valid = false;

        while (!valid) {
            String input = scanner.nextLine();

            if (input.isEmpty()) {
                return null;
            }
            try {
                date = LocalDate.parse(input, dateTimeFormatter);
                String validationError = validateDate(date);
                if (validationError.isEmpty()) {
                    valid = true;
                } else {
                    System.out.println(validationError);
                }
            }catch (DateTimeParseException e) {
                System.out.println("Ошибка: неправильный формат даты. Пожалуйста, введите дату в формате ГГГГ-ММ-ДД или оставьте поле пустым.");
            }
        }

        return date;
    }

    public double getDoubleInput(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            String input = scanner.nextLine();
            try {
                double value = Double.parseDouble(input);
                String validationError = validateIncome(value);
                if(validationError.isEmpty()) {
                    return value;
                }else {
                    System.out.println(validationError);
                }
            }catch (NumberFormatException e){
                System.out.println("Ошибка: введите число типа double.");
            }
        }
    }
    public Integer getValidateId(){
        Scanner scanner = new Scanner(System.in);
        Integer id = null;
        while (id == null){
            System.out.println("Введите ID пользователя: ");
            try {
                id = Integer.parseInt(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Ошибка: пожалуйста, введите целое число в качестве ID.");
            }
        }
        return id;
    }
}
