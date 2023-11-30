package com.cd.quizwhiz.client.ui;

import java.util.function.Consumer;

import com.cd.quizwhiz.client.questions.Player;
import com.cd.quizwhiz.client.uiframework.UI;
import com.cd.quizwhiz.client.uiframework.UIPage;
import com.cd.quizwhiz.client.user.Auth;
import com.cd.quizwhiz.client.user.UserSession;

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
    public void onPreload(UI<AppState> ui, Consumer<Boolean> callback) {
        ui.getContext().put("purpose", this.purpose);
        callback.accept(true);
    }

    @Override
    public void onStart(UI<AppState> ui) {
        ui.addListener("login-button", "click", e -> this.onLoginButtonClick(ui));
        ui.addListener("signup-link", "click", e -> this.onSignupLinkClick(ui));
    }

    public void onLoginButtonClick(UI<AppState> ui) {
        String username = ui.getInputValueById("username");
        String password = ui.getInputValueById("password");

        // Attempt to authenticate the user
        Auth.login(ui.getNetClient(), username, password, (success, token) -> {
            if (success) {
                // Success!
                UserSession user = new UserSession(username, token);

                switch (this.playerType) {
                    case Player1:
                        ui.getState().user = user;
                        break;

                    case Player2:
                        // Check if the same user's just tried to log in twice - we don't want someone
                        // playing themself
                        if (ui.getState().user.getUsername().equals(username)) {
                            ui.setElementText("error-toast",
                                    "The second player must be a different user to the first!");
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
        });
    }

    public void onSignupLinkClick(UI<AppState> ui) {
        ui.loadPage(new SignupPage(this.playerType, this.nextPage, this.purpose));
    }
}
