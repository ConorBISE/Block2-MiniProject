package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.Auth;
import com.cd.quizwhiz.uiframework.ClickListener;
import com.cd.quizwhiz.uiframework.UI;
import com.cd.quizwhiz.uiframework.UIPage;

public class SignupPage extends UIPage<AppState> {

    public SignupPage() {
        super("signup");
    }

    @ClickListener(id="signup-button")
    public void onSignupButtonClick(UI<AppState> ui) {
        String username = ui.getInputValueById("username");
        String password = ui.getInputValueById("password");

        String registrationStatus = Auth.register(username, password);

        if (registrationStatus.equals(username)) {
            ui.loadPage(new HomePage());
        }
        
        // error :(
        System.out.println(registrationStatus);
    }

    @ClickListener(id="signin-link")
    public void onSignupLinkClick(UI<AppState> ui) {
        ui.loadPage(new LoginPage());
    }
    
}
