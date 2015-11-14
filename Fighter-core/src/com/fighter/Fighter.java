package com.fighter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class Fighter extends ApplicationAdapter{

    private static final int        FRAME_COLS = 6;         // #1
    private static final int        FRAME_ROWS = 1;         // #2

    Animation                       walkAnimation;          // #3
    Texture                         walkSheet;              // #4
    TextureRegion[]                 walkFrames;             // #5
    SpriteBatch                     spriteBatch;            // #6
    TextureRegion                   currentFrame;           // #7
    Texture bg;

    BaseCharacter p1;
    BaseCharacter p2;
    float stateTime;                                        // #8
	
	@Override
	public void create () {
		 spriteBatch = new SpriteBatch();
		 p1 = new BaseCharacter("Garrett", "Ryu", 2, 2, 200 ,200 , walkSheet);
		 bg = new Texture(Gdx.files.internal("forestBg.gif"));
		
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);   
		spriteBatch.begin();
		spriteBatch.draw(bg, 0 , 0);
		spriteBatch.end();
		p1.movement(spriteBatch);
		//p1.gravity();
        if(Gdx.input.isKeyPressed(Input.Keys.Q) && p1.currentAnim == p1.defaultAnim){
        	p1.punch(p2);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.E) && p1.currentAnim == p1.defaultAnim){
        	p1.kick(p2);
        }	
	}
}
