package com.ajegile.game.utils;

import com.ajegile.game.LibGdxSpine;
import com.badlogic.gdx.utils.Logger;

/**
 * Created by adityahadi on 8/30/17.
 */

public class Env {

    private static LibGdxSpine game;

    public static void init(LibGdxSpine game) {
        Env.game = game;
    }

    public static LibGdxSpine getGame() {
        return game;
    }

    //LOGGER
    public static final int LOG_LEVEL_DEBUG = Logger.DEBUG;
    public static final int LOG_LEVEL_INFO = Logger.INFO;
    public static final int LOG_LEVEL_ERROR = Logger.ERROR;

    //Viewport
    public static final float SCENE_WIDTH = 1080;
    public static final float SCENE_HEIGHT = 1920;

    //Splash
    public static final long SPLASH_MIN_MILLIS = 3000l;

    //Loading
    public static final long LOADING_MIN_MILLIS = 5000l;

    //Screen
    public static final int SCREEN_MENU_MAIN = 0;
    public static final int SCREEN_GAME = SCREEN_MENU_MAIN + 1;

    //Music Path
    public static final String M_TITLE              = "music/bgm.mp3";

    //Sprite and fonts
    public static final String FONT_BIG             = "font/font_big.fnt";
    public static final String FONT_MED             = "font/font_med.fnt";
    public static final String FONT_SMALL           = "font/font_small.fnt";

    //Spine data
    public static final String SPINE_MAIN_MENU_BG_ATLAS = "spine/main_menu_bg/skeleton.atlas";
    public static final String SPINE_MAIN_MENU_BG_JSON = "spine/main_menu_bg/skeleton.json";

}
