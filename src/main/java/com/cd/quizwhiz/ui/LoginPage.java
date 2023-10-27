package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.Questions.Player;
import com.cd.quizwhiz.UserStuff.Auth;
import com.cd.quizwhiz.UserStuff.User;
import com.cd.quizwhiz.uiframework.ClickListener;
import com.cd.quizwhiz.uiframework.UI;
import com.cd.quizwhiz.uiframework.UIPage;

public class LoginPage extends UIPage<AppState> {

    private Player playerType;
    private UIPage<AppState> nextPage;

    public LoginPage(Player loginType, UIPage<AppState> nextPage) {
        super("login");

        this.playerType = loginType;
        this.nextPage = nextPage;
    }

    @ClickListener(id="login-button")
    public void onLoginButtonClick(UI<AppState> ui) {
        String username = ui.getInputValueById("username");
        String password = ui.getInputValueById("password");

        // Attempt to authenticate the user
        if (Auth.login(username, password)) {
            // Success!
            User user = new User(username);

            switch (this.playerType) {
                case player1:
                    ui.getState().user = user;
                    break;

                case player2:
                    // Check if the same user's just tried to log in twice - we don't want someone playing themself
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

    @ClickListener(id="signup-link")
    public void onSignupLinkClick(UI<AppState> ui) {
        ui.loadPage(new SignupPage(this.playerType, this.nextPage));
    }
}
