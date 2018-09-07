/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newgd;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;


/**
 *
 * @author ABCD
 */
public class Player {
    ArrayList<MovementAnimation> littleBlocks;
    private int 
            playerXCenter,
            playerYCenter,
            playerXPos,
            playerYPos,
            fireCoolDown, 
            playerBulletType, 
            playerXLength = 20, 
            PLAYER_Y_LENGTH = 20,
            coolDownTime = 20,
            spawnTimeProtection = 200,
            timeTracker;

    private static final int 
            PLAYER_MOVING_SPEED = 3, 
            LIFE_RECT_WIDTH = 5, 
            LIFE_RECT_HEIGHT = 5,
            DRAW_LIFE_X_SPACE = 20,
            
            PLAYER_LEFT_BOUND = 0, 
            PLAYER_RIGHT_BOUND = 580,
            PLAYER_TOP_BOUND = 0, 
            PLAYER_BOT_BOUND = 743, 
            
            MOVE_ANIMATION_FACTOR = 5,            
            MINIMUM_X_LENGTH = 15,
            MINIMUM_Y_LENGTH = 15,
    
            INITIAL_X_LENGTH = 20,
            INITIAL_Y_LENGTH = 20,
            
            DEFAULT_TIME_TRACKER = 100,
            MAXIMUM_LITTLE_BLOCK_DEVIATION = 2,
            LITTLE_BLOCKS_APPEAR_RATE = 4;
    private boolean playerSpawn = false,
            moveL, 
            moveR, 
            fire, 
            moveD, 
            moveU,
            dead = false;
    private int Life = 3,
                playerID = 0;
    private final int             
            DRAW_LIFE_X_POSITION = 500, 
            DRAW_LIFE_1_Y_POSITION = 750,
            DRAW_LIFE_2_Y_POSITION = 720;    
    
    public Player(int playerID){
        this.playerID = playerID;
        resetAll();
        fireCoolDown = coolDownTime;
        littleBlocks = new ArrayList<MovementAnimation>();
        timeTracker = DEFAULT_TIME_TRACKER;
    }
    
    public void paint (Graphics g){
        setPos();
        if(!Interaction.GameOver_Inqury() && !dead){
            if(playerSpawn){
                if(playerID == 0){
                    g.setColor(Color.ORANGE);
                } else {
                    g.setColor(Color.RED);
                }
                if(spawnTimeProtection%4 == 1){
                    g.fillRect(playerXPos, playerYPos, playerXLength, PLAYER_Y_LENGTH); 
                }
                countDownSpawnTimeProtection();
            } else{
                    if(playerID == 0){
                        g.setColor(new Color(0,213,255));
                    } else {
                        g.setColor(Color.GREEN);
                    }
                    g.fillRect(playerXPos, playerYPos, playerXLength, PLAYER_Y_LENGTH);
            }
            for(int i = 0; i<Life; i++){
                if(playerID == 0){
                    g.setColor(new Color(0,213,255));
                    g.fillRect(DRAW_LIFE_X_POSITION+i*DRAW_LIFE_X_SPACE, 
                            DRAW_LIFE_1_Y_POSITION, LIFE_RECT_WIDTH, LIFE_RECT_HEIGHT);
                } else{
                    g.setColor(Color.GREEN);
                    g.fillRect(DRAW_LIFE_X_POSITION+i*DRAW_LIFE_X_SPACE, 
                            DRAW_LIFE_2_Y_POSITION, LIFE_RECT_WIDTH, LIFE_RECT_HEIGHT);
                }
            }
        }
    }

    void playerMovement(){
        if (moveL && playerXPos > PLAYER_LEFT_BOUND){
            playerXCenter -= PLAYER_MOVING_SPEED;
            moveAnimation("L");
            setMovementAnimation(playerXCenter, playerYCenter);
        }
        else if (moveR && playerXPos< PLAYER_RIGHT_BOUND){
            playerXCenter += PLAYER_MOVING_SPEED;
            moveAnimation("R");
            setMovementAnimation(playerXCenter, playerYCenter);
        } 
        if (moveU && playerYPos > PLAYER_TOP_BOUND){
            playerYCenter -= PLAYER_MOVING_SPEED;
            moveAnimation("U");
            setMovementAnimation(playerXCenter, playerYCenter);
        }
        else if (moveD && playerYPos< PLAYER_BOT_BOUND){
            playerYCenter += PLAYER_MOVING_SPEED;
            moveAnimation("D");
            setMovementAnimation(playerXCenter, playerYCenter);
        }
        if(!moveD && !moveU && !moveR && !moveL){
            moveAnimation("default");
        }
    }
    
    public void playerFireBullets(){
        if(fire){
            if(fireCoolDown == coolDownTime){
                Bullets bullets = new Bullets
                    (playerBulletType,playerXPos+playerXLength/2,playerYPos);
                MainMenu.gw.PlayerBulletsArrayList.add(bullets);
                fireCoolDown --;
            }
            else {
                fireCoolDown --;
            }
        } else{
            if (fireCoolDown != coolDownTime){
                fireCoolDown --;
                fire = false;
            }
        }
        if (fireCoolDown == 0){
            fireCoolDown = coolDownTime;
        }
    }
    
    void spawn(){ //private?
        playerSpawn = true;
        spawnTimeProtection = 200;
    }
    
    void gotHit(){ //private?
        if(Life>=1){
            Life --;
        } else{
            dead = true;
        }
    }
    
    //Reset sssss
    void resetAll(){//private?
        resetMovingStatus();
        resetLife();
        resetPlayerCenter();
        resetBulletType();
    }
    private void resetMovingStatus(){
        moveL = moveR =  fire = moveD = moveU = false; 
    }
    private void resetLife(){
        Life = 3;
        dead = false;
    }
    void resetPlayerCenter(){
        if(playerID != 0){
            playerXCenter = 250;
            playerYCenter = 700;
        } else {
            playerXCenter = 450;
            playerYCenter = 700;            
        }
    }
    void resetBulletType(){
        playerBulletType = 0;
        coolDownTime = 20;
    }
    
    //Changesssss
    public void changeBulletType(int type){
        playerBulletType = type;
        changeCoolDownTime(type);
    }
    private void changeCoolDownTime(int type){
        switch(type){
            case 0:
                coolDownTime = 20;
                break;
            case 4:
                coolDownTime = 10;
                break;
            case 6:
                coolDownTime = 50;
        }
    }
    private void moveAnimation(String Singal){
        setDeaultXYLength();
        if(playerXLength>MINIMUM_X_LENGTH && PLAYER_Y_LENGTH>MINIMUM_Y_LENGTH){
            if(Singal == "L" || Singal == "R"){
                playerXLength += MOVE_ANIMATION_FACTOR;
                PLAYER_Y_LENGTH -= MOVE_ANIMATION_FACTOR;
            }
            else if(Singal == "U" || Singal == "D"){
                
                playerXLength -= MOVE_ANIMATION_FACTOR;
                PLAYER_Y_LENGTH += MOVE_ANIMATION_FACTOR;
            }
        } 
        if(Singal == "default"){
            setDeaultXYLength();
        }
    }
    private void setDeaultXYLength(){
        playerXLength = INITIAL_X_LENGTH;
        PLAYER_Y_LENGTH = INITIAL_Y_LENGTH;
    }
    private void setPos(){
        playerXPos = playerXCenter - (playerXLength/2);
        playerYPos = playerYCenter - (PLAYER_Y_LENGTH/2);
    }
    private void setMovementAnimation(int X, int Y){
        if(timeTracker%LITTLE_BLOCKS_APPEAR_RATE == 1){
            int i = ThreadLocalRandom.current().nextInt
                (-MAXIMUM_LITTLE_BLOCK_DEVIATION,MAXIMUM_LITTLE_BLOCK_DEVIATION);
            i = 0;
            littleBlocks.add(new MovementAnimation(X+i, Y+i, playerID));
        } 
        if(timeTracker == 0){
            timeTracker = DEFAULT_TIME_TRACKER;
        }
        timeTracker --;
    }
    void setFireBoolean(boolean fire){
        this.fire = fire;
    }
    void setMovingBoolean(String direction, boolean moving){
        switch(direction){
            case "L":
                moveL = moving;
                break;
            case "R":
                moveR = moving;
                break;
            case "U":
                moveU = moving;
                break;
            case "D":
                moveD = moving;
                break;
        }
    }
    //Inqury ssssss
    public int X_Inqury(){
        return playerXPos;
    }
    public int Y_Inqury(){
        return playerYPos;
    }
    public int PLAYER_X_LENGTH_Inqury(){
        return playerXLength;
    }
    public int PLAYER_Y_LENGTH_Inqury(){
        return PLAYER_Y_LENGTH;
    }
    public int PlayerBulletType_Inqury(){
        return playerBulletType;
    }
    public boolean playerSpawn_Inqury(){
        return playerSpawn;
    }
    public int ID_Inqury(){
        return playerID;
    }
    public boolean Dead_Inqury(){
        return dead;
    }
    private void countDownSpawnTimeProtection(){
        spawnTimeProtection --;
        if(spawnTimeProtection<=0){
            playerSpawn = false;
        }
    }
   
}
