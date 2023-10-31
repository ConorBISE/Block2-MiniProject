package com.cd.quizwhiz.ui;

import com.cd.quizwhiz.uiframework.ClickListener;
import com.cd.quizwhiz.uiframework.UI;
import com.cd.quizwhiz.uiframework.UIPage;

public class AboutPage extends UIPage<AppState> {
    public AboutPage() {
        super("about");        
    }

    @ClickListener(id="back-link")
    public void onBackLinkClick(UI<AppState> ui) {
        ui.loadPage(new HomePage());
    }
}
