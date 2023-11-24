package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.questions.Player;
import com.cd.quizwhiz.uiframework.UIEventListener;
import com.cd.quizwhiz.uiframework.UI;
import com.cd.quizwhiz.uiframework.UIPage;
import com.cd.quizwhiz.userstuff.Auth;
import com.cd.quizwhiz.userstuff.User;

public class LoginPage extends UIPage<AppState> {

    private Player playerType;
    private UIPage<AppState> nextPage;
    private String purpose;

    public LoginPage(Player loginType, UIPage<AppState> nextPage, String purpose) {
        super("login");

        this.playerType = loginType;
        this.nextPage = nextPage;
        this.purpose = purpose;
    }

    public LoginPage(Player loginType, UIPage<AppState> nextPage) {
        this(loginType, nextPage, "");
    }

    @Override
    public boolean onPreload(UI<AppState> ui) {
        ui.getContext().setVariable("purpose", this.purpose);
        return true;
    }

    @UIEventListener(type = "click", id = "login-button")
    public void onLoginButtonClick(UI<AppState> ui) {
        String username = ui.getInputValueById("username");
        String password = ui.getInputValueById("password");

        // Attempt to authenticate the user
        if (Auth.login(username, password)) {
            // Success!
            User user = new User(username);

            switch (this.playerType) {
                case Player1:
                    ui.getState().user = user;
                    break;

                case Player2:
                    // Check if the same user's just tried to log in twice - we don't want someone
                    // playing themself
                    if (ui.getState().user.getUsername().equals(username)) {
                        ui.setElementText("error-toast", "The second player must be a different user to the first!");
                        ui.setElementVisibility("error-toast", true);
                        return;
                    }

                    ui.getState().multiplayerUserTwo = user;
                    break;
            }

            ui.loadPage(nextPage);
        } else {
            ui.setElementVisibility("error-toast", true);
        }
    }

    @UIEventListener(type = "click", id = "signup-link")
    public void onSignupLinkClick(UI<AppState> ui) {
        ui.loadPage(new SignupPage(this.playerType, this.nextPage, this.purpose));
    }
}
