package com.cd.quizwhiz.Questions;

public class Switcher {
    private Player player;

    // Constructor for the Switcher class
    public Switcher() {
        // Initialize the 'player' instance variable with the player set to player 1 intially when the switcher object is created
        this.player = Player.player1;
    }

    // Method for switching the player
    public void Switch() {
        // Check if the current player is player1
        if (this.player == Player.player1) {
            // If the current player is player1, switch to player2
            this.player = Player.player2;
        } else {
            // If the current player is not player1 (implying it's player2), switch back to player1
            this.player = Player.player1;
        }
    }

   //method to get player
    public Player getPlayer (){
        return this.player;
    }
}
