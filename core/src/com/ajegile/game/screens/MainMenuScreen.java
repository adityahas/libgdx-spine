package com.ajegile.game.screens;

import com.ajegile.game.utils.Env;
import com.ajegile.game.utils.SpineObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;

/**
 * Created by adityahadi on 8/30/17.
 */

public class MainMenuScreen implements Screen, InputProcessor {

    private final String TAG = MainMenuScreen.class.getSimpleName();
    private Logger logger;
    private Stage stage;
    private SpineObject spineObject;
    private BitmapFont font_big;
    private SpriteBatch batch;
    private Actor mmActor;
    private AssetManager assetManager;
    private Music bgmMusic;

    public MainMenuScreen() {
        logger = new Logger(TAG, Env.LOG_LEVEL_INFO);
        stage = Env.getGame().getStage();
        assetManager = Env.getGame().getAssetManager();
        Gdx.input.setInputProcessor(this);

        mmActor = new Actor();
        mmActor.setBounds(Env.SCENE_WIDTH / 2, Env.SCENE_HEIGHT / 2, 1, 1);
        stage.addActor(mmActor);
        spineObject = new SpineObject();
        spineObject.loadSpine(
                assetManager.get(Env.SPINE_MAIN_MENU_BG_ATLAS),
                Env.SPINE_MAIN_MENU_BG_JSON,
                "animation", mmActor);

        bgmMusic = assetManager.get(Env.M_TITLE);

        font_big = assetManager.get(Env.FONT_BIG);

        batch = Env.getGame().getBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getViewport().getCamera().update();
        stage.act(delta);

        batch.begin();
        spineObject.drawSpine(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        bgmMusic.stop();
        bgmMusic.dispose();
        stage.clear();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void show() {
        bgmMusic.setLooping(true);
        bgmMusic.setVolume(0.5f);
        bgmMusic.play();
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Env.getGame().setScreen(Screens.getLoadingScreen(Env.SCREEN_GAME));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
