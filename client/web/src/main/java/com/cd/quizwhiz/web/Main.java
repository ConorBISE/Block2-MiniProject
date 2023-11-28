package com.cd.quizwhiz.web;

import com.cd.quizwhiz.client.ui.AppState;
import com.cd.quizwhiz.client.ui.HomePage;
import com.cd.quizwhiz.web.ui.WebNetClient;
import com.cd.quizwhiz.web.ui.WebResourceLoader;
import com.cd.quizwhiz.web.ui.WebUI;

public class Main {
    public static void main(String[] args) {
        new WebUI<>(new AppState(), new WebResourceLoader(), new WebNetClient())
            .loadPage(new HomePage());
    }
}
