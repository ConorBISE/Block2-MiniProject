package com.cd.quizwhiz.ui;

import org.thymeleaf.context.Context;

import com.cd.quizwhiz.uiframework.UI;

public class HeadToHeadStatsPage extends StatsPage {
    
    public HeadToHeadStatsPage() {
        super(true);
    }
    
    @Override
    public boolean onPreload(UI<AppState> ui) {
        super.onPreload(ui);
        
        Context context = ui.getContext();
        context.setVariable("multiplayer", true);
        context.setVariable("multiplayerUserTwo", ui.getState().multiplayerUserTwo);        
        
        int userTwoFinalScore = ui.getState().multiplayerUserTwo.FinalScore();
        context.setVariable("multiplayerUserTwoScore", Integer.toString(userTwoFinalScore));
        
        return true;
    }

}