package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Alice", "Smith", (byte) 25);
        userService.saveUser("Bob", "Johnson", (byte) 30);
        userService.saveUser("Charlie", "Brown", (byte) 35);
        userService.saveUser("Diana", "Miller", (byte) 40);

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            log.info(String.valueOf(user));
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}