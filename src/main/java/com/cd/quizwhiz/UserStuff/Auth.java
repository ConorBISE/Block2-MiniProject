package com.cd.quizwhiz.UserStuff;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * The Auth class handles user registration and login
 * for a quiz application. It allows users to register
 * with a username and password and then verify their credentials during login.
 */
// Auth class handles user registration and login.
public class Auth {
    // Directory where user data files are stored.
    /**
     * String userFolder: A string representing the directory where user data files are stored. By default, it is set to "users."
     */
    public static String userFolder = "users";

    private static final Logger logger = LoggerFactory.getLogger(Auth.class);

    public static String register(String username, String password) {
        /**
         * @Description:
         * Registers a user with a given username and password. It checks if a file with the same username already exists and,
         * if not, creates a new file with the provided credentials.
         *
         * @Parameters:
         * username (String): The username to register.
         * password (String): The password associated with the username.
         *
         * @Returns:
         * String: A string indicating the result of the registration:
         * "Username already exists" if the username is already taken.
         * "Error creating user file" if there is an issue creating the user file.
         * The registered username if registration is successful.
         */
        // Construct the filename based on the username.
        String userDataFileName = username + ".txt";
        File userFile = new File(userFolder, userDataFileName);

        // If there is no file in the "users" folder with the given username.
        if (!userFile.exists()) {
            // Try to write a new file with the user's data.
            try {
                FileWriter writer = new FileWriter(userFile);
                String passwordEnc = PasswordEncryption.encrypt(password);
                // Write the user's password into the file.
                writer.write(passwordEnc);
                writer.close();
                return username; // Registration successful.
            } catch (IOException e) {
                logger.error("", e);
                return "Error creating user file."; // Handle file creation error.
            }
            catch (Exception e) {
                throw new RuntimeException(e);//catch errors for password Encryption
            }
        } else {
            return "Username already exists."; // Username is already taken.
        }
    }


    public static boolean login(String username, String password) {
        /**
         @Description:
         Verifies if the entered credentials (username and password) match a previously registered user's credentials.
         If the credentials match, the user is considered logged in.

         @Parameters:
         username (String): The username to log in with.
         password (String): The password to verify.

         @Returns:
         boolean: true if the credentials are correct, indicating successful login; false otherwise.
         */
        boolean loggedIn = false; // Tracks whether the user is logged in or not.

        // Construct the filename based on the username.
        String userDataFileName = username + ".txt";
        File userFile = new File(userFolder, userDataFileName);

        try (BufferedReader br = new BufferedReader(new FileReader(userFile))) {
            String line = br.readLine();
            if (line != null) {
                String storedPassword = line;
                // Check if the provided password matches the stored password.
                if (password.equals(PasswordEncryption.decrypt(storedPassword))) {
                    loggedIn = true;
                }
            }
        } catch (FileNotFoundException e) {
            // Handle the file not found exception.
            loggedIn = false;
        } catch (IOException e) {
            // Handle other I/O exceptions.
            loggedIn = false;
        } catch (Exception e) {
            throw new RuntimeException(e);// catch errors for password decryption
        }

        return loggedIn;
    }

    /**
     * Use Cases
     *
     * @UserRegistration

     String registrationResult = Auth.register("JohnDoe", "mySecretPassword");
     if (registrationResult.equals("JohnDoe")) {
     System.out.println("Registration successful!");
     } else {
     System.out.println("Registration failed: " + registrationResult);
     }

     @UserLogin

     boolean loggedIn = Auth.login("JohnDoe", "mySecretPassword");
     if (loggedIn) {
     System.out.println("Login successful!");
     } else {
     System.out.println("Login failed. Invalid credentials.");
     }
     */
}
