package com.cd.quizwhiz.ui;

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

        System.out.println("Login button click! Username: " + username + ", Password: " + password);
   
        // TODO: log in validation

        ui.getState().user = new Object();
        ui.loadPage(new HomePage());
    }
    
}
