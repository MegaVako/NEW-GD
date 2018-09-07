/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newgd;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author SimonSun
 */
public class MovementAnimation {
    private static final int 
            DEFAULT_X_LENGTH = 17, 
            DEFAULT_Y_LENGTH = 17,
            DEFAULT_TIME_TRACKER = 100,
            GET_SMALLER_SPEED = 2;
    private int 
            xCenter,
            yCenter,
            xPos,
            yPos,
            xLength,
            yLength,
            timeTracker,
            playerID;
    public MovementAnimation(int xCenter, int yCenter, int playerID){
        this.xCenter = xCenter;
        this.yCenter = yCenter;
        this.playerID = playerID;
        initializeXYLength();
        timeTracker = DEFAULT_TIME_TRACKER;
    }

    public void render (Graphics g){
        setXYPosAndRect();
        getSmaller(); // Prob moving it to some where else
        Player player = MainMenu.gw.PlayerArrayList.get(playerID);
        if(!player.playerSpawn_Inqury()){
            if(playerID == 0){
                g.setColor(new Color(0,213,255));
            } else {
                g.setColor(Color.GREEN);
            }
        } else{
            if(playerID == 0){
                g.setColor(Color.ORANGE);
            } else {
                g.setColor(Color.RED);
            }
        }
        g.fillRect(xPos, yPos, xLength, yLength);
    }
    private void initializeXYLength(){
        xLength = DEFAULT_X_LENGTH;
        yLength = DEFAULT_Y_LENGTH;
    }
    private void setXYPosAndRect(){
        xPos = xCenter - (xLength/2);
        yPos = yCenter - (yLength/2);
    }
    private void getSmaller(){
        if(timeTracker%GET_SMALLER_SPEED == 1){
            if(xLength>0){
                xLength -= 2;
            } 
            if(yLength>0){
                yLength -= 2;
            }
        }
        if(timeTracker == 0){
            timeTracker = DEFAULT_TIME_TRACKER;
        }
        timeTracker --;
    }
    public int xLength_Inqury(){
        return xLength;
    }
    public int yLength_Inqury(){
        return yLength;
    }
}
