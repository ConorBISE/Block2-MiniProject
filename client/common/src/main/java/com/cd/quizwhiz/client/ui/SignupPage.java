package com.cd.quizwhiz.client.ui;

import java.util.function.Consumer;

import com.cd.quizwhiz.client.questions.Player;
import com.cd.quizwhiz.client.uiframework.UI;
import com.cd.quizwhiz.client.uiframework.UIPage;
import com.cd.quizwhiz.client.user.Auth;
import com.cd.quizwhiz.client.user.UserSession;

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
    public void onPreload(UI<AppState> ui, Consumer<Boolean> callback) {
        ui.getContext().put("purpose", this.purpose);
        callback.accept(true);
    }

    @Override
    public void onStart(UI<AppState> ui) {
        ui.addListener("signup-button", "click", e -> this.onSignupButtonClick(ui));
        ui.addListener("signin-link", "click", e -> this.onSigninLinkClick(ui));
    }

    public void onSignupButtonClick(UI<AppState> ui) {
        String username = ui.getInputValueById("username");
        String password = ui.getInputValueById("password");

        Auth.register(ui.getNetClient(), username, password, (registrationStatus, token) -> {
            // Auth.register returns the user's username on success
            // and a failure message otherwise.
            if (registrationStatus.equals(username)) {
                UserSession user = new UserSession(username, token);

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
        });
    }

    public void onSigninLinkClick(UI<AppState> ui) {
        ui.loadPage(new LoginPage(this.playerType, this.nextPage, this.purpose));
    }
}
