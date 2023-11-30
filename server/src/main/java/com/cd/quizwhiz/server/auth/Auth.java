package com.cd.quizwhiz.server.auth;

import java.io.*;
import java.util.ArrayList;

/**
 * The Auth class handles user registration and login
 * for a quiz application. It allows users to register
 * with a username and password and then verify their credentials during login.
 */
// Auth class handles user registration and login.
public class Auth {
    // Directory where user data files are stored.
    /**
     * String userFolder: A string representing the directory where user data files
     * are stored. By default, it is set to "users."
     */
    public static String userFolder = "users";

    public static String register(String username, String password) {
        /**
         * @Description:
         *               Registers a user with a given username and password. It checks
         *               if a file with the same username already exists and,
         *               if not, creates a new file with the provided credentials.
         *
         * @Parameters:
         *              username (String): The username to register.
         *              password (String): The password associated with the username.
         *
         * @Returns:
         *           String: A string indicating the result of the registration:
         *           "Username already exists" if the username is already taken.
         *           "Error creating user file" if there is an issue creating the user
         *           file.
         *           The registered username if registration is successful.
         */
        // Construct the filename based on the username.
        File userFile = User.getUserFile(username);
        if (userFile.exists()) {
            return "Username already exists";
        }

        User user = new User(username, PasswordEncryption.hashPassword(password), new ArrayList<>());
        try {
            user.save();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error creating user file";
        }

        return username;
    }

    public static boolean login(String username, String password) {
        User user;

        try {
            user = User.readUserFromFile(username);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return PasswordEncryption.hashPassword(password).equals(user.getHashedPassword());
    }

    /**
     * @@UseCases
     *
     * @UserRegistration
     * 
     *                   String registrationResult = Auth.register("JohnDoe",
     *                   "mySecretPassword");
     *                   if (registrationResult.equals("JohnDoe")) {
     *                   System.out.println("Registration successful!");
     *                   } else {
     *                   System.out.println("Registration failed: " +
     *                   registrationResult);
     *                   }
     * 
     * @UserLogin
     * 
     *            boolean loggedIn = Auth.login("JohnDoe", "mySecretPassword");
     *            if (loggedIn) {
     *            System.out.println("Login successful!");
     *            } else {
     *            System.out.println("Login failed. Invalid credentials.");
     *            }
     */
}
