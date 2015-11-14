package com.fighter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class BaseCharacter  {
	
    Animation                       introAnim;      
    Animation						defaultAnim;
    Animation						currentAnim;
    Animation						punchAnim;
    Animation 						walkRightAnim;
	private Animation 				walkLeftAnim;
    Animation						jumpAnim;
    Animation						kickAnim;
    Animation[]					    animList;
    Texture                         spriteSheet;    
    TextureRegion[]                 spriteFrames;   
    SpriteBatch                     spriteBatch;    
    TextureRegion                   currentFrame;   
    int count = 0;
    private boolean flip;
    
    private float 					stateTime;
	private String 					name = "";
	private String					CharName;
	private float 					x_pos = 0;
	private	float					y_pos = 0;
	private float					jump = 0;
	private int  					hp = 0;
	private int 					dmg = 0;
	private boolean 				flipRight;
	
	public BaseCharacter(String name, String CharName, int hp, int dmg, int x_pos, int y_pos, Texture tex){
		this.CharName = CharName;
		this.stateTime = 0;
		this.name = name;
		this.hp = hp;
		this.dmg = dmg;
		this.stateTime = 0f;
		this.flipRight = false;
		
	    this.spriteBatch = new SpriteBatch();
	    loadAnimations(CharName + "Animations.txt");
	    
		currentAnim = introAnim;
			
	}
	
	public void movement(SpriteBatch b){
		if(Gdx.input.isKeyPressed(Input.Keys.D)){
		
	        if(flipRight == true){
	        	flip = true;
	        	flipAnimations();
	        }
	        flipRight = false;
			
			currentAnim = walkRightAnim;
			x_pos += Gdx.graphics.getDeltaTime() * 100;
			System.out.println(x_pos);
		}else if(Gdx.input.isKeyPressed(Input.Keys.A)){
	        
			
			if(flipRight == false){
				flip = true;
				flipAnimations();
			}
			flipRight = true;
			
			currentAnim = walkRightAnim;
			x_pos -= Gdx.graphics.getDeltaTime() * 100;
		}else if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
			jump += Gdx.graphics.getDeltaTime() * 100;
			y_pos += Gdx.graphics.getDeltaTime() * 100;
			currentAnim = jumpAnim;
		}
		animation(); 
	}
	
	public void gravity(){
		
		y_pos -= Gdx.graphics.getDeltaTime() * 50;
		
	}
	
	public void punch(BaseCharacter foe){
		currentAnim = punchAnim;

	}
	
	public void kick(BaseCharacter foe){
		currentAnim = kickAnim;
		
		
	}
	
	//Returns the characters the name.
	public String getName(){
		return name;
	}
	
	//Returns the characters health points
	public int getHp(){
		return hp;
	}
	
	//Returns the characters base damage
	public int getDmg(){
		return dmg;
	}
	
	//Returns the characters x coordinate as a float
	public float getX_pos(){
		return x_pos;
	}
	
	//Returns the characters y coordinate as a float
	public float getY_pos(){
		return y_pos;
	}
	
	//Sets a Characters base health
	public void setHp(int hp){
		this.hp = hp;
	}
	
	//Sets a Characters base damage.
	public void setDmg(int dmg){
		this.dmg = dmg;
	}
	
	
	public Sprite getSprite(){
		//return sprite;
		return null;
	}
	
	public void flipAnimations(){
		
		if(flip == true){
			for(int i = 0; i < animList.length; i++){
				TextureRegion[] tmp = animList[i].getKeyFrames();
				for(int k = 0; k < tmp.length; k++){
					tmp[k].flip(true, false);
				}
			}
		}
		flip = false;
		
	}
	
	public void animation(){
                            
        stateTime += Gdx.graphics.getDeltaTime();
        //Default Animation
        if(currentAnim == defaultAnim ){
        	
		        currentFrame = currentAnim.getKeyFrame(stateTime, true);  
		        
		        spriteBatch.begin();
		        spriteBatch.draw(currentFrame, x_pos, y_pos);            
		        spriteBatch.end();
		        System.out.print(currentFrame.getTexture().getWidth()/currentAnim.getKeyFrames().length);
        }
        //Checks if we are jumping.
        if(currentAnim == jumpAnim){
        	currentFrame = currentAnim.getKeyFrame(stateTime , false);
        	spriteBatch.begin();
        	spriteBatch.draw(currentFrame, x_pos, y_pos);
        	spriteBatch.end();
        	y_pos += Gdx.graphics.getDeltaTime() * 100;
        	jump += Gdx.graphics.getDeltaTime() * 100;
        	System.out.println(jump);
        	if(jump >= 100){
        		jump = 0;
        		currentAnim = defaultAnim;
        	}
        }
        else{
            currentFrame = currentAnim.getKeyFrame(stateTime, false);  
            spriteBatch.begin();
            spriteBatch.draw(currentFrame, x_pos, y_pos);            
            spriteBatch.end();
            //Makes the Animation loop once. 
            if(currentAnim.isAnimationFinished(stateTime)){
            	stateTime = 0;
            	currentAnim = defaultAnim;
            }
        }
      
	}
	
	
	/*This Method simply opens the animation file, reads information needed, and passes it to createAnimation.
	 * Also, instantiate animList's size*/
	public void loadAnimations(String fName){
	
		try {
			Scanner in = new Scanner(new FileReader(fName));
			int numAnimations = in.nextInt();
			animList = new Animation[numAnimations];
			for(int i = 0; i < numAnimations; i++){
				
				// file format:  name path x_offset y_offset row col animSpeed, animCount
				// example:  introAnim 100 100 5 6 1.25
				createAnimation(in.next(), in.next(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextFloat(), i);
			}
		
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	
	//CreateAnimation is a simple method that instantiates the characters basic animations.
	public void createAnimation(String name, String path, int x_offSet, int y_offSet, int row, int col, float animSpeed, int animCount){
		
        spriteSheet = new Texture(Gdx.files.internal(path)); 
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, x_offSet, y_offSet);    
        spriteFrames = new TextureRegion[col * row];
        
        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                spriteFrames[index++] = tmp[i][j];
            }
        }
          Animation a = new Animation(animSpeed, spriteFrames);   
         if(name.equals("introAnim"))
        	 introAnim = a;
         else if(name.equals("jumpAnim"))
        	 jumpAnim = a;
         else if(name.equals("defaultAnim"))
      		defaultAnim = a;
         else if(name.equals("walkRightAnim"))
         	walkRightAnim = a;
         else if(name.equals("walkLeftAnim"))
        	 walkLeftAnim = a;
         else if(name.equals("punchAnim"))
         	punchAnim = a;
         else if(name.equals("kickAnim"))
         	kickAnim = a;
         
         animList[animCount] = a;
         	
         currentAnim = introAnim; //Sets the introAnimation
         	
	}
}
