package com.cd.quizwhiz;

import java.io.*;

// Auth class handles user registration and login.
public class Auth {
    // Directory where user data files are stored.
    private static String userFolder = "users";

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
                // Write the user's password into the file.
                writer.write(password);
                writer.close();
                return username; // Registration successful.
            } catch (IOException e) {
                return "Error creating user file."; // Handle file creation error.
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
                if (password.equals(storedPassword)) {
                    loggedIn = true;
                }
            }
        } catch (FileNotFoundException e) {
            // Handle the file not found exception.
            loggedIn = false;
        } catch (IOException e) {
            // Handle other I/O exceptions.
            loggedIn = false;
        }

        return loggedIn;
    }
}
