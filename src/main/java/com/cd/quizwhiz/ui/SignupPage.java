package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.Questions.Player;
import com.cd.quizwhiz.UserStuff.Auth;
import com.cd.quizwhiz.UserStuff.User;
import com.cd.quizwhiz.uiframework.ClickListener;
import com.cd.quizwhiz.uiframework.UI;
import com.cd.quizwhiz.uiframework.UIPage;

public class SignupPage extends UIPage<AppState> {
    private Player playerType;
    private UIPage<AppState> nextPage;

    public SignupPage(Player playerType, UIPage<AppState> nextPage) {
        super("signup");

        this.playerType = playerType;
        this.nextPage = nextPage;
    }

    @ClickListener(id="signup-button")
    public void onSignupButtonClick(UI<AppState> ui) {
        String username = ui.getInputValueById("username");
        String password = ui.getInputValueById("password");

        String registrationStatus = Auth.register(username, password);

        if (registrationStatus.equals(username)) {
            User user = new User(username);

            switch (this.playerType) {
                case player1:
                    ui.getState().user = user;
                    break;
                case player2:
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

    @ClickListener(id="signin-link")
    public void onSignupLinkClick(UI<AppState> ui) {
        ui.loadPage(new LoginPage(this.playerType, this.nextPage));
    }
    
}
