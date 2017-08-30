package com.ajegile.game.screens;

import com.ajegile.game.LibGdxSpine;
import com.ajegile.game.utils.Env;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Timer;

/**
 * Created by adityahadi on 8/30/17.
 */

public class SplashScreen implements Screen {
    private Logger logger;
    private final String TAG = LibGdxSpine.class.getSimpleName();

    private SpriteBatch batch;
    private Texture ttrSplash;
    private Sprite sprSplash;

    private Stage stage;

    private long startSplashTime = 0l;
    private Float fadeSpeed = 0.05f;
    private Float fadeTime = 0.0f;
    private Float alpha = 0.0f;

    private boolean fadeIn = true;
    private boolean fadeOut = false;
    private boolean isAllowToFade = false;

    private Music m_splash;

    public SplashScreen() {
        logger = new Logger(TAG, Env.LOG_LEVEL_INFO);

        stage = Env.getGame().getStage();
        batch = Env.getGame().getBatch();

        m_splash = Gdx.audio.newMusic(Gdx.files.internal("music/m_splash.mp3"));
        m_splash.setLooping(false);

        ttrSplash = new Texture("splash/splash.png");
        ttrSplash.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprSplash = new Sprite(ttrSplash);

        startSplashTime = System.currentTimeMillis();
    }

    @Override
    public void show() {
        long splashElapsed = System.currentTimeMillis() - startSplashTime;
        if (splashElapsed < Env.SPLASH_MIN_MILLIS) {
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    fadeOut = true;
                    fadeIn = false;
                }
            }, (float) (Env.SPLASH_MIN_MILLIS - splashElapsed) / 1000f);
        } else {
            fadeOut = true;
            fadeIn = false;
        }
        m_splash.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        fadeTime += delta;

        if (fadeIn) {
            if (fadeTime >= fadeSpeed && alpha != 1) {
                alpha = MathUtils.clamp(alpha + 0.025f, 0, 1);
                fadeTime = 0f;
            }
        }

        if (fadeOut && isAllowToFade) {
            if (fadeTime >= fadeSpeed && alpha != 0) {
                alpha = MathUtils.clamp(alpha - 0.025f, 0, 1);
                fadeTime = 0f;
            }
        }

        batch.begin();
        sprSplash.setAlpha(alpha);
        sprSplash.draw(batch);
        batch.end();

        goToNextScreen();
    }

    private void goToNextScreen() {
//        if (!m_splash.isPlaying())
        {
            isAllowToFade = true;
        }

        if (fadeOut && alpha == 0) {
            Env.getGame().setScreen(Screens.getLoadingScreen(Env.SCREEN_MENU_MAIN));
        }
    }


    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        startSplashTime = 0;
        fadeTime = 0f;
        isAllowToFade = false;
        m_splash.stop();
        stage.clear();
    }

    @Override
    public void dispose() {
        ttrSplash.dispose();
        m_splash.dispose();
        batch.dispose();
    }
}
