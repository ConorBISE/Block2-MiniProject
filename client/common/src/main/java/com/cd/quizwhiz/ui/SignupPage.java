package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.questions.Player;
import com.cd.quizwhiz.uiframework.UIEventListener;
import com.cd.quizwhiz.uiframework.UI;
import com.cd.quizwhiz.uiframework.UIPage;
import com.cd.quizwhiz.userstuff.Auth;
import com.cd.quizwhiz.userstuff.User;

public class SignupPage extends UIPage<AppState> {
    private Player playerType;
    private UIPage<AppState> nextPage;
    private String purpose;

    public SignupPage(Player playerType, UIPage<AppState> nextPage, String purpose) {
        super("signup");

        this.playerType = playerType;
        this.nextPage = nextPage;
        this.purpose = purpose;
    }

    public SignupPage(Player playerType, UIPage<AppState> nextPage) {
        this(playerType, nextPage, "");
    }

    @Override
    public boolean onPreload(UI<AppState> ui) { 
        ui.getContext().setVariable("purpose", this.purpose);
        return true;
    }

    @UIEventListener(type = "click", id = "signup-button")
    public void onSignupButtonClick(UI<AppState> ui) {
        String username = ui.getInputValueById("username");
        String password = ui.getInputValueById("password");

        String registrationStatus = Auth.register(username, password);

        // Auth.register returns the user's username on success
        // and a failure message otherwise.
        if (registrationStatus.equals(username)) {
            User user = new User(username);

            switch (this.playerType) {
                case Player1:
                    ui.getState().user = user;
                    break;
                case Player2:
                    ui.getState().multiplayerUserTwo = user;
                    break;
            }

            ui.loadPage(nextPage);
            return;
        }

        // error :(
        ui.setElementText("error-toast", registrationStatus);
        ui.setElementVisibility("error-toast", true);
    }

    @UIEventListener(type = "click", id = "signin-link")
    public void onSignupLinkClick(UI<AppState> ui) {
        ui.loadPage(new LoginPage(this.playerType, this.nextPage, this.purpose));
    }

}
