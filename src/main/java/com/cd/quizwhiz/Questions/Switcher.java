package com.cd.quizwhiz.Questions;

public class Switcher {
    private Player player;

    public Switcher(Player player){
        this.player = player;
        player = Player.player1;
    }

    public void Switch(){
        if (this.player ==Player.player1){ this.player = Player.player2;}
        if (this.player ==Player.player2){ this.player = Player.player1;}
    }

}
