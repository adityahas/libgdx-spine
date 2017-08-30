package com.ajegile.game.screens;

import com.badlogic.gdx.Screen;

/**
 * Created by adityahadi on 8/30/17.
 */

public class Screens {

    public static final Screen getSplashScreen() {
        return new SplashScreen();
    }

    public static final Screen getMainMenuScreen() {
        return new MainMenuScreen();
    }

    public static final Screen getLoadingScreen(final int nextScreen) {
        return new LoadingScreen(nextScreen);
    }


}
