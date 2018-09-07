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
 * @author ABCD
 */
public class Bullets {
    int BulletType, XPos, YPos, Y_Vector, X_Vector;
    int Width = 3, Height = 7;
    private final int  
            BULLET_2_X_Vector = 1,BULLET_2_Y_Vector = 6, 
            BULLET_3_X_Vector = 2,BULLET_3_Y_Vector = 7,
            BULLET_5_X_Vector = 1,BULLET_5_Y_Vector = 4,
            BULLET_7_X_Vector = -1, BULLET_7_Y_Vector = 4,
            
            BULLET_0_Vector = -7, BULLET_1_Vector = 4,
            BULLET_4_Vector = -10, BULLET_6_Vector = -5;
    private final int BULLET_4_WIDTH = 10, BULLET_4_HEIGHT = 20, // 1/4
            BULLET_6_WIDTH = 6, BULLET_6_HEIGHT = 14, 
            TOP_BOUND = -10, BOT_BOUND = 800,
            BULLET_1_DMG = 50, BULLET_4_DMG = 50, BULLET_6_DMG = 150;
    private int DMG;
    
    public Bullets(int BulletType, int XPos, int YPos){
        this.BulletType = BulletType;
        this.XPos = XPos;
        this.YPos = YPos;
        X_Vector = 0;
        switch(BulletType){
            case 0:
                Y_Vector = BULLET_0_Vector;
                DMG = BULLET_1_DMG;
                break;
            case 1:
                Y_Vector = BULLET_1_Vector;
                break;
            case 2:
                Y_Vector = BULLET_2_Y_Vector;
                X_Vector = BULLET_2_X_Vector;
                break;
            case 3:
                Y_Vector = BULLET_3_Y_Vector;
                X_Vector = BULLET_3_X_Vector;
                break; 
            case 4: 
                Y_Vector = BULLET_4_Vector;
                DMG = BULLET_4_DMG;
                break;
            case 5:
                Y_Vector = BULLET_5_Y_Vector;
                X_Vector = BULLET_5_X_Vector;
                break;
            case 6:
                Y_Vector = BULLET_6_Vector;
                DMG = BULLET_6_DMG;
                break;
            case 7:
                Y_Vector = BULLET_7_Y_Vector;
                X_Vector = BULLET_7_X_Vector;
                break;
        }
    }
    public void paint (Graphics g) {
        switch(BulletType){
            case 0:
                g.setColor(Color.GREEN);
                break;
            case 1:
            case 5:
            case 7:
                g.setColor(Color.RED);
                break;
            case 2:
                g.setColor(Color.YELLOW);
                break;
            case 3:
                g.setColor(Color.ORANGE);
                break;
            case 4:
                g.setColor(Color.GREEN);
                Width = BULLET_4_WIDTH;
                Height = BULLET_4_HEIGHT;
                break;

            case 6:
                g.setColor(Color.GREEN);
                Width = BULLET_6_WIDTH;
                Height = BULLET_6_HEIGHT;
                break;

        }
        g.fillRect(XPos, YPos, Width, Height);
        if(!Interaction.Pause_Inqury()){
            moveBullet();
        }
    }
    public void moveBullet(){
        YPos += Y_Vector;
        XPos += X_Vector;
    }

    public int DMG_Inqury(){
        return DMG;
    }
}
