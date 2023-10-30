package com.cd.quizwhiz.Questions;

/**
 * The `Switcher` class is responsible for switching between players in a quiz game.
 */
public class Switcher {
    private Player player;

    /**
     * Initializes a new Switcher with the first player set to Player 1.
     */
    public Switcher() {
        this.player = Player.player1; // Set the initial player to Player 1
    }

    /**
     * Switches the current player. If the current player is Player 1, it switches to Player 2,
     * and if the current player is Player 2, it switches back to Player 1.
     */
    public void Switch() {
        if (this.player == Player.player1) {
            this.player = Player.player2; // Switch to Player 2
        } else {
            this.player = Player.player1; // Switch back to Player 1
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
