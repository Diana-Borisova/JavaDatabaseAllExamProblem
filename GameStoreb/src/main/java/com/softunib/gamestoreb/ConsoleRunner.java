package com.softunib.gamestoreb;

import com.softunib.gamestoreb.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.Scanner;

import static com.softunib.gamestoreb.constants.Commands.*;

public class ConsoleRunner implements CommandLineRunner {
    private static final Scanner scanner = new Scanner(System.in);
    private final UserService userService;

    @Autowired
    public ConsoleRunner(UserService userService) {
        this.userService = userService;
    }


    @Override
    public void run(String... args) throws Exception{
        String input = scanner.nextLine();

        while (!input.equals("close")) {
            final String[] arguments = input.split("\\|");
            final String command = arguments[0];

            final String output = switch (command) {
                case LOGIN_USER -> userService.loginUser(arguments);
                case REGISTER_USER -> userService.registerUser(arguments);
                case LOGOUT_USER ->  userService.logout();

                default -> "No command found";
            };

            System.out.println(output);
            input = scanner.nextLine();

        }



    }
}
