package com.cd.quizwhiz.UserStuff;

import java.io.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Auth class handles user registration and login.
public class Auth {
    // Directory where user data files are stored.
    public static String userFolder = "users";

    private static final Logger logger = LoggerFactory.getLogger(Auth.class);

    // The register method checks if a file with the username already exists,
    // and if not, it creates a new file with the username and password.
    public static String register(String username, String password) {
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

    // Login method verifies if the entered credentials match a previously registered user's credentials.
    public static boolean login(String username, String password) {
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
}
