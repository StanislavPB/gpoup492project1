package Project.Service;

import Project.Entity.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Validation {

    public String validateUser(User user){
        String validationResult = "";
        if (user == null||user.getName()==null||user.getName().isEmpty()){
            validationResult = validationResult+"Ошибка: имя пользователя не может быть пустым.\n";
        }
        return validationResult;
}
    public String validateIncome(double amount) {
        String validationResult = "";
        if (amount <= 0) {
            validationResult = validationResult + "Ошибка: доход должен быть положительным числом.\n";
        }
        return validationResult;
    }

        public String validateOutcome(double amount){
        String validationResult = "";
        if(amount<=0){
            validationResult=validationResult+"Ошибка: расход должен быть положительным числом.\n";
        }
        return validationResult;
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
        while (date ==null){
            System.out.println("Введите дату в формате yyyy-MM-dd:");
            try{
                date = LocalDate.parse(scanner.nextLine(), dateTimeFormatter);
                String validationError = validateDate(date);
                if (!validationError.isEmpty()){
                    System.out.println(validationError);
                    date = null;
                }
            }catch (DateTimeParseException e){
                System.out.println("Ошибка: некорректный формат даты. Попробуйте снова.");
            }
        }
        return date;
    }
    public double getDoubleInput(){
        Scanner scanner = new Scanner(System.in);
        while(true){
            try {
                return Double.parseDouble(scanner.nextLine());
            }catch (NumberFormatException e){
                System.out.println("Ошибка: введите число типа double.");
            }
        }
    }


}
