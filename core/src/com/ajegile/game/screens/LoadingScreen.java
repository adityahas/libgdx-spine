package com.ajegile.game.screens;

import com.ajegile.game.utils.Env;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;

/**
 * Created by adityahadi on 8/30/17.
 */

public class LoadingScreen implements Screen, AssetErrorListener {

    private final String TAG = LoadingScreen.class.getSimpleName();
    private Logger logger = new Logger(TAG, Env.LOG_LEVEL_INFO);

    private Stage stage;
    private AssetManager assetManager;
    private BitmapFont font_small;

    SpriteBatch batch;
    private int next_screen = -1;

    private long start;
    private long elapsed = 0;

    public LoadingScreen(final int screenGoTo) {
        logger.info("Init loading screen");
        batch = Env.getGame().getBatch();
        next_screen = screenGoTo;
        stage = Env.getGame().getStage();
        assetManager = Env.getGame().getAssetManager();
        loadAssets(screenGoTo);
    }

    @Override
    public void show() {
        start = System.currentTimeMillis();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        elapsed = System.currentTimeMillis() - start;

        stage.getViewport().getCamera().update();

        if (assetManager.update() && elapsed >= Env.LOADING_MIN_MILLIS) {
            logger.info("Assets loaded");
            switchScreen(next_screen);
        }

        if (assetManager.isLoaded(Env.FONT_SMALL)) {
            font_small = assetManager.get(Env.FONT_SMALL);
            batch.begin();
            font_small.draw(
                    batch,
                    "Loading... " + ((int) assetManager.getProgress() * 100),
                    Env.SCENE_WIDTH / 2,
                    Env.SCENE_HEIGHT / 2, 1f, Align.center, false);
            batch.end();
        }
    }

    private void loadAssets(final int screenGoTo) {
        switch (screenGoTo) {
            case Env.SCREEN_MENU_MAIN:
                assetManager.load(Env.FONT_BIG, BitmapFont.class);
                assetManager.load(Env.FONT_MED, BitmapFont.class);
                assetManager.load(Env.FONT_SMALL, BitmapFont.class);
                assetManager.load(Env.M_TITLE, Music.class);
                assetManager.load(Env.SPINE_MAIN_MENU_BG_ATLAS, TextureAtlas.class);
                break;
            case Env.SCREEN_GAME:
                break;
            default:
                break;

        }
    }

    private void switchScreen(final int screenGoTo) {
        switch (screenGoTo) {
            case Env.SCREEN_MENU_MAIN:
                Env.getGame().setScreen(Screens.getMainMenuScreen());
                break;
            case Env.SCREEN_GAME:
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        logger.error("error loading " + asset.fileName + " message: " + throwable.getMessage());
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }
}
