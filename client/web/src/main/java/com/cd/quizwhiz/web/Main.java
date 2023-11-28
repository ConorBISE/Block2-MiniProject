package com.cd.quizwhiz.web;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.cd.quizwhiz.client.ui.AppState;
import com.cd.quizwhiz.client.ui.HomePage;
import com.cd.quizwhiz.client.ui.LoginPage;
import com.cd.quizwhiz.client.ui.SignupPage;
import com.cd.quizwhiz.web.ui.WebNetClient;
import com.cd.quizwhiz.web.ui.WebResourceLoader;
import com.cd.quizwhiz.web.ui.WebUI;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        //optKeeper(HomePage.class, LoginPage.class, SignupPage.class);
        
        new WebUI<>(new AppState(), new WebResourceLoader(), new WebNetClient())
            .loadPage(new HomePage());
    }

    /*static void optKeeper(Class<?>... classes) {
        for (Class<?> c : classes) {
            for (Method m : c.getMethods()) {
                System.out.println(m.getAnnotations());
            }
        }
    }*/
}
