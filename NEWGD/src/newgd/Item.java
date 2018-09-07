/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newgd;

import java.awt.Graphics;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author SimonSun
 */
public class Item {
    private static final int 
            ITEM_APPEAR_X_LEFT_BOUND = 100,
            ITEM_APPEAR_X_RIGHT_BOUND = 500,
            DEFAULT_Y_POSITION = -20;
    private int 
            xPos,
            yPos;
    public Item(){
        xPos = ThreadLocalRandom.current().
            nextInt(ITEM_APPEAR_X_LEFT_BOUND, ITEM_APPEAR_X_RIGHT_BOUND);
        yPos = DEFAULT_Y_POSITION;
    }
    public void render(Graphics g){
        
    }
    
}
