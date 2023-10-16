package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.uiframework.ClickListener;
import com.cd.quizwhiz.uiframework.UI;
import com.cd.quizwhiz.uiframework.UIPage;

public class SignupPage extends UIPage<AppState> {

    public SignupPage() {
        super("signup");
    }

    @ClickListener(id="signup-button")
    public void onSignupButtonClick(UI<AppState> ui) {
        // TODO
    }
    
}
