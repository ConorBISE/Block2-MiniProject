package com.cd.quizwhiz.client.ui;

import com.cd.quizwhiz.client.user.UserSession;

/**
 * Quizwhiz-specific application state.
 * Any information that needs to be tracked through the app as a whole is stored
 * here.
 */
public class AppState {
    /** The primary logged-in user.
     * We show their stats, any single player quizzes are perfomed as them, and they
     * act
     * as player one in any multiplayer quizzes
     */
    public UserSession user;

    /**
     * The secondary user that may be authenticated.
     * Whenever the primary user wants to play a multiplayer game, this is who
     * they're doing it with.
     */
    public UserSession multiplayerUserTwo;
}
