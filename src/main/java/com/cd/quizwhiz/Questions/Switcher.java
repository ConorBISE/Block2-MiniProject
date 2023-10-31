package com.cd.quizwhiz.questions;

/**
 * The `Switcher` class is responsible for switching between players in a quiz
 * game.
 */
public class Switcher {
    private Player player;

    /**
     * Initializes a new Switcher with the first player set to Player 1.
     */
    public Switcher() {
        this.player = Player.Player1; // Set the initial player to Player 1
    }

    /**
     * Switches the current player. If the current player is Player 1, it switches
     * to Player 2,
     * and if the current player is Player 2, it switches back to Player 1.
     */
    public void switchCurrentPlayer() {
        if (this.player == Player.Player1) {
            this.player = Player.Player2; // Switch to Player 2
        } else {
            this.player = Player.Player1; // Switch back to Player 1
        }
    }

    /**
     * Gets the current player.
     *
     * @return The current player (either Player 1 or Player 2).
     */
    public Player getPlayer() {
        return this.player; // Return the current player
    }
}
