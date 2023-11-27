package com.cd.quizwhiz.client.ui;

import com.cd.quizwhiz.client.uiframework.UIEventListener;
import com.cd.quizwhiz.client.uiframework.UI;
import com.cd.quizwhiz.client.uiframework.UIPage;

public class AboutPage extends UIPage<AppState> {
    public AboutPage() {
        super("about");
    }

    @UIEventListener(type = "click", id = "back-link")
    public void onBackLinkClick(UI<AppState> ui) {
        ui.loadPage(new HomePage());
    }
}
