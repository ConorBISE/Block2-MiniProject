package com.cd.quizwhiz.client.ui;

import com.cd.quizwhiz.client.uiframework.UI;
import com.cd.quizwhiz.client.uiframework.UIPage;

public class AboutPage extends UIPage<AppState> {
    public AboutPage() {
        super("about");
    }

    @Override
    public void onStart(UI<AppState> ui) {
        ui.addListener("back-link", "click", e -> this.onBackLinkClick(ui));
    }

    public void onBackLinkClick(UI<AppState> ui) {
        ui.loadPage(new HomePage());
    }
}
