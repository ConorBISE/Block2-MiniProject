package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.UserStuff.Auth;
import com.cd.quizwhiz.uiframework.ClickListener;
import com.cd.quizwhiz.uiframework.UI;
import com.cd.quizwhiz.uiframework.UIPage;

public class LoginPage extends UIPage<AppState> {

    public LoginPage() {
        super("login");
    }

    @ClickListener(id="login-button")
    public void onLoginButtonClick(UI<AppState> ui) {
        String username = ui.getInputValueById("username");
        String password = ui.getInputValueById("password");

        // Attempt to authenticate the user
        if (Auth.login(username, password)) {
            // Success!
            // Bounce the user through to the home page
            ui.getState().user = new Object();
            ui.loadPage(new HomePage());
        } else {
            System.out.println("Unsuccesful login!");
        }
    }

    @ClickListener(id="signup-link")
    public void onSignupLinkClick(UI<AppState> ui) {
        ui.loadPage(new SignupPage());
    }
}
