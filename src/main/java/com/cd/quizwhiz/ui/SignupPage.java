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
            ui.getState().user = new Object(); // TODO: user object
            ui.loadPage(new HomePage());
            return;
        }
        
        // error :(
        ui.setElementText("error-toast", registrationStatus);
        ui.setElementVisibility("error-toast", true);
    }

    @ClickListener(id="signin-link")
    public void onSignupLinkClick(UI<AppState> ui) {
        ui.loadPage(new LoginPage());
    }
    
}
