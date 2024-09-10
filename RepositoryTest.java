package Project.Repository;

import Project.Entity.User;
import Project.Repository.Repository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryTest {

    @Test
    void testAddUser() {
        Repository repository = new Repository();
        User user = new User(null, "Kim", new ArrayList<>());

        User addedUser = repository.addUser(user);

        assertNotNull(addedUser.getId()); // Sprawdza, czy użytkownik otrzymał ID

        assertEquals("Kim", addedUser.getName()); // Sprawdza, czy nazwa użytkownika jest poprawna

        // Sprawdzamy, czy użytkownik został poprawnie dodany do repozytorium
        Optional<User> foundUser = repository.findUserById(addedUser.getId());
        assertTrue(foundUser.isPresent(), "User is added?");
    }

    @Test
    void testGetCategoriesOfOutcomes() {
        Repository repository = new Repository();

        List<String> outcomes = repository.getCategoriesOfOutcomes();

        assertEquals(5, outcomes.size()); // Sprawdza, czy są jest kategorie
        assertTrue(outcomes.contains("Еда")); // Sprawdza, czy jedna z kategorii to "Еда"
    }

    @Test
    void testGetSourcesOfIncome() {
        Repository repository = new Repository();

        List<String> incomes = repository.getSourcesOfIncome();

        assertEquals(5, incomes.size()); // Sprawdza, czy jest 5 źródeł
        assertTrue(incomes.contains("Зарплата")); // Sprawdza, czy jedno ze źródeł to "Зарплата"
    }

}
