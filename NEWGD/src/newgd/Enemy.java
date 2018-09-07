/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newgd;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author ABCD
 */
public class Enemy{
    private int 
            ROW, 
            COL, 
            LRMovementTrack = 0,
            Bullet_Fired = 0, 
            Enemy_Bullet_Type = 1;    
    private final int 
            X_Width = 10, 
            Y_Height = 10, 
            X_Space = 30, 
            Y_Space = 30;
    public final int
            ENEMY_LEFT_BOUND = -10, 
            ENEMY_RIGHT_BOUND = 630, 
            ENEMY_BOTTOM_BOUND = 830,
            MAXIMUM_FIRED_BULLET = 2;
    private final double 
            Vector_Co = 0.4, 
            X_Vector_Increase_By = 1;
    private double 
            X_Vector, 
            Y_Vector;
    private int 
            XPos, 
            YPos, 
            MovementTracker, 
            FormationVector;
    boolean Moving = false,
            ReturnToPosition = false;
    int status = 1;
    
    public Enemy(int row, int col){
        this.ROW = row;
        this.COL = col;
        XPos = ROW * X_Space;
        YPos = COL * Y_Space + 30;
        Y_Vector = 5;
        FormationVector = 1;
    }
    public void paint(Graphics g){
        switch(COL){
            case 0: g.setColor(Color.RED);
                break;
            case 1: g.setColor(Color.ORANGE);
                break;
            case 2: g.setColor(Color.PINK);
                break;
            case 3: g.setColor(Color.PINK);
                break;
            case 4: g.setColor(Color.BLUE);
                break;  
        }

        g.fillRect(XPos, YPos, X_Width, Y_Height);
    }
    public void moveLR(){
        if(XPos == ROW * X_Space + 320 || XPos == ROW * X_Space - 5){
            FormationVector = -FormationVector;
        }
        XPos += FormationVector;
    }
    void setMoving(){
        MovementTracker ++;
        if (MovementTracker % 5 == 1){
            for(Player player : MainMenu.gw.PlayerArrayList){
                if (XPos > player.X_Inqury()){
                    X_Vector -= X_Vector_Increase_By;
                }
                else {
                    X_Vector += X_Vector_Increase_By;
                }
            }
        }
        if (MovementTracker > 100){
            MovementTracker = 0;
        }
        if(MovementTracker%2 == 0){
            XPos += X_Vector;
            YPos += Y_Vector;
        }
    }
    
    void returnToMap(){
        XPos = ROW * X_Space + 20;
        if(!ReturnToPosition){
            ReturnToPosition = true;
            YPos = -20; 
            X_Vector = 0;
            Bullet_Fired = 0;
        }
        YPos += Y_Vector;
    }
    void fireBullet(){
        if(validateFire()){
            Bullets bullets = new Bullets(Enemy_Bullet_Type, XPos, YPos);
            MainMenu.gw.EnemyBulletsArrayList.add(bullets);
            Bullet_Fired++;
            System.out.println("Enemy Fired");
        }
    }
    private boolean validateFire(){
        boolean fire = false;
        if(Bullet_Fired<MAXIMUM_FIRED_BULLET){
            for(Player player : MainMenu.gw.PlayerArrayList){
                if(Math.abs(XPos - player.X_Inqury()) < 5){
                    int i = ThreadLocalRandom.current().nextInt(0, 15);
                    if(i<2){
                        fire = true;
                        break;
                    }
                }
            }
        }
        return fire;
    }
    public int X_Pos_Inqury(){
        return XPos;
    }
    public int Y_Pos_Inqury(){
        return YPos;
    }
    public int X_Width_Inqury(){
        return X_Width;
    }
    public int Y_Width_Inqury(){
        return Y_Height;
    }
    public int COL_Inqury(){
        return COL;
    }
    public int Y_Space_Inqury(){
        return Y_Space;
    }
}
