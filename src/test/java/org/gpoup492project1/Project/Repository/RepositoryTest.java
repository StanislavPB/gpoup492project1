package org.gpoup492project1.Project.Repository;


import Project.Repository.Repository;
import Project.Entity.Balance;
import Project.Entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RepositoryTest {
    private Repository repository;
    private User testUser;

    @BeforeEach
    public void setUp() {
        // Инициализация перед каждым тестом
        repository = new Repository();
        testUser = new User(null,"",new ArrayList<>());
        testUser.setName("Тест User");
       testUser.getBalances();
    }

    @Test
    public void testAddIncomeToUser() {

        repository.addUser(testUser);
        Balance income = new Balance("Зарплата", 1000);


        repository.addIncomeToUser(testUser, income);


        List<Balance> balances = repository.getTransactionHistory(testUser);
        assertEquals(1, balances.size(), "В истории транзакций должен быть один элемент.");
        assertEquals(income, balances.get(0), "Доход должен быть добавлен к балансу пользователя.");
    }

    @Test
    public void testAddOutcomeToUser() {

        repository.addUser(testUser);
        Balance outcome = new Balance("Еда", -500); // Создаем баланс расхода


        repository.addOutcomeToUser(testUser, outcome);


        List<Balance> balances = repository.getTransactionHistory(testUser);
        assertEquals(1, balances.size(), "В истории транзакций должен быть один элемент.");
        assertEquals(outcome, balances.get(0), "Расход должен быть добавлен к балансу пользователя.");
    }

    @Test
    public void testGetCategoriesOfOutcomes() {

        List<String> categories = repository.getCategoriesOfOutcomes();
        assertEquals(5, categories.size(), "Список категорий расходов должен содержать 5 элементов.");
        assertTrue(categories.contains("Еда"), "Список должен содержать категорию 'Еда'.");
    }

    @Test
    public void testGetTransactionHistory() {

        repository.addUser(testUser);


        Balance income = new Balance("Зарплата", 1000);
        Balance outcome = new Balance("Еда", -500);
        repository.addIncomeToUser(testUser, income);
        repository.addOutcomeToUser(testUser, outcome);


        List<Balance> history = repository.getTransactionHistory(testUser);

        // Проверяем количество транзакций и их значения
        assertEquals(2, history.size(), "В истории транзакций должно быть два элемента.");
        assertTrue(history.contains(income), "История должна содержать доход.");
        assertTrue(history.contains(outcome), "История должна содержать расход.");
    }
}

