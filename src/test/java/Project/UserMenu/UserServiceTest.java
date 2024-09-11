package Project.UserMenu;

import Project.DTO.Response;
import Project.Entity.User;
import Project.Repository.Repository;
import Project.Service.UserService;
import Project.Service.Validation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class UserServiceTest {

    private UserService userService;
    private Repository repository;
    private Validation validation;

    @BeforeEach
    public void setUp() {
        repository = new Repository();
        validation = new Validation();
        userService = new UserService(repository, validation);
    }

    @Test
    public void testAddNewUser_Success() {
        // gdy dodajemy poprawnego użytkownika
        String userName = "Kim";

        Response<User> response = userService.addNewUser(userName);

        assertNotNull(response.getBody());
        assertEquals(userName, response.getBody().getName());
        assertTrue(response.getError().isEmpty());
    }

    @Test
    public void testAddNewUser_ValidationError() {
        // gdy walidacja nie przechodzi (np. puste imię)
        String userName = "";
        Response<User> response = userService.addNewUser(userName);

        assertNull(response.getBody());
        assertFalse(response.getError().isEmpty());
    }
}
