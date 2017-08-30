package com.ajegile.game;

import com.ajegile.game.screens.Screens;
import com.ajegile.game.utils.Env;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by adityahadi on 8/30/17.
 */

public class LibGdxSpine extends Game {
    private Logger logger;
    private final String TAG = LibGdxSpine.class.getSimpleName();
    private AssetManager assetManager;
    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private InputMultiplexer inputMultiplexer = new InputMultiplexer();

    @Override
    public void create() {
        logger = new Logger(TAG, Env.LOG_LEVEL_INFO);
        logger.info("Init game");

        Env.init(this);
        assetManager = new AssetManager();

        // Set up camera and viewport
        camera = new OrthographicCamera();
        viewport = new FillViewport(Env.SCENE_WIDTH, Env.SCENE_HEIGHT, camera);
        // Center camera
        viewport.getCamera().position.set(
                viewport.getCamera().position.x + Env.SCENE_WIDTH * 0.5f,
                viewport.getCamera().position.y + Env.SCENE_HEIGHT * 0.5f,
                0);
        viewport.getCamera().update();
        viewport.update((int) Env.SCENE_WIDTH, (int) Env.SCENE_HEIGHT);

        // Create stage
        stage = new Stage(viewport);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        // Create batch
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);


        // Set screen to Splash
        setScreen(Screens.getSplashScreen());
    }

    @Override
    public void render() {
        camera.update();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float deltaTime = Gdx.graphics.getDeltaTime();
        getScreen().render(deltaTime);
    }

    @Override
    public void dispose() {
        getScreen().dispose();
        stage.dispose();
        assetManager.dispose();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
        getScreen().resize(width, height);
        super.resize(width, height);
    }

    @Override
    public void setScreen(Screen screen) {
        stage.clear();
        super.setScreen(screen);
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public Stage getStage() {
        return stage;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
