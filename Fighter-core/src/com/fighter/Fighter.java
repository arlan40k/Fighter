package com.fighter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Fighter extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
    private SpriteBatch batch2;
    private Texture texture;
    private Sprite sprite;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		
		batch2 = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("kickBox.png"));
        sprite = new Sprite(texture);
	}
	
    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		sprite.draw(batch);
		batch.end();

	}
}
