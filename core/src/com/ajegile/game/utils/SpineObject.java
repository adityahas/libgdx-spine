package com.ajegile.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Event;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;

/**
 * Created by adityahadi on 8/16/17.
 */

public class SpineObject {
    public Skeleton skel;
    public AnimationState animState;
    public Actor actor;
    public SkeletonRenderer renderer;
    float defaultSpineScale = 1f;

    public SpineObject() {
        renderer = new SkeletonRenderer();
        renderer.setPremultipliedAlpha(true); // PMA results in correct blending without outlines.
    }

    public void loadSpine(Object atlas, String jsonLocation, String animInit, Actor act){
        SkeletonJson json = new SkeletonJson((TextureAtlas) atlas);
        json.setScale(defaultSpineScale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(jsonLocation));
        skel = new Skeleton(skeletonData);
        skel.setPosition(0, 0);
        animState = new AnimationState(new AnimationStateData(skeletonData));
        animState.setAnimation(0, animInit, true);
        actor = act;
    }

    public void loadSpine(String atlasLocation, String jsonLocation, String animInit, Actor act){
        SkeletonJson json = new SkeletonJson(new TextureAtlas(Gdx.files.internal(atlasLocation)));
        json.setScale(defaultSpineScale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(jsonLocation));
        skel = new Skeleton(skeletonData);
        skel.setPosition(0, 0);
        animState = new AnimationState(new AnimationStateData(skeletonData));
        animState.setAnimation(0, animInit, true);
        actor = act;
    }

    public void drawSpine(SpriteBatch batch){
        if(actor == null) {
            actor = new Actor();
            actor.setX(0f);
            actor.setY(0f);
        }
        skel.getRootBone().setScale(defaultSpineScale);
        skel.getRootBone().setPosition(actor.getX(), actor.getY());
        animState.update(Gdx.graphics.getDeltaTime()); // Update the animation time.
        animState.apply(skel); // Poses skeleton_main_menu_bg using current animations. This sets the bones' local SRT.
        skel.updateWorldTransform(); // Uses the bones' local SRT to compute their world SRT.

        boolean endBatchFlag = false;
        if(!batch.isDrawing()){
            batch.begin();
            endBatchFlag = true;
        }

        renderer.draw(batch, skel); // Draw the skeleton_main_menu_bg images.

        if(endBatchFlag)
            batch.end();
    }

    public void changeAnim(String animName, boolean isLoop){
        animState.setAnimation(0, animName, isLoop);
    }

    public void changeAnim(String animName){
        animState.setAnimation(0, animName, false);
    }

    public boolean isAnimOver() {
        if (animState == null || animState.getCurrent(0) == null)
            return true;

        return animState.getCurrent(0).isComplete();
    }

    public AnimationState getAnimState() {
        return animState;
    }

    public void addSoundWithEvent(final String eventKey, final Sound sound) {
        animState.getCurrent(0).setListener(new AnimationState.AnimationStateAdapter() {
            @Override
            public void event(AnimationState.TrackEntry entry, Event event) {
                if (event.getData().getName().equalsIgnoreCase(eventKey)) {
                    sound.stop();
                    sound.play();
                }
                super.event(entry, event);
            }
        });
    }
}
