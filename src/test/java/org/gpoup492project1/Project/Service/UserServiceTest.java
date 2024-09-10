package org.gpoup492project1.Project.Service;

import static org.junit.jupiter.api.Assertions.*;

import org.gpoup492project1.Project.DTO.Response;
import org.gpoup492project1.Project.Entity.User;
import org.gpoup492project1.Project.Repository.Repository;
import org.gpoup492project1.Project.Service.UserService;
import org.gpoup492project1.Project.Service.Validation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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