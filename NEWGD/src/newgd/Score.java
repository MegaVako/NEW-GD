/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newgd;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author SimonSun
 */
public class Score {
    private static int Player_Score = 0;
    private static final int 
            DRAW_SCORE_X_POS = 250, 
            DRAW_SCORE_Y_POS = 15,
            SCORE_FONT = 15, 
            MOVING_RED_SCORE = 50,
            MOVING_ORANGE_SCORE = 40, 
            MOVING_PINK_SCORE = 30,
            MOVING_BLUE_SCORE = 20,
            FORMATION_RED_SCORE = 20, 
            FORMATION_ORANGE_SCORE = 15, 
            FORMATION_PINK_SCORE = 10, 
            FORMATION_BLUE_SCORE = 5;
    Score (){
        Player_Score = 0;
    }
    public void render(Graphics g){
        g.setColor(new Color(0, 255, 255));
        g.setFont(new Font("serif", Font.BOLD, SCORE_FONT));
        g.drawString("Score: " + Integer.toString(Player_Score), 
                DRAW_SCORE_X_POS, DRAW_SCORE_Y_POS);
    }
    static void Receive_Score(String Condition, int Col){
        if(Condition == "Moving"){
            switch(Col){
                case 0:
                    Player_Score += MOVING_RED_SCORE;
                    break;
                case 1:
                    Player_Score += MOVING_ORANGE_SCORE;
                    break;
                case 2:
                case 3:
                    Player_Score += MOVING_PINK_SCORE;
                    break;
                case 4:
                    Player_Score += MOVING_BLUE_SCORE;
                    break;
            }
        }else{
            switch(Col){
                case 0:
                    Player_Score += FORMATION_RED_SCORE;
                    break;
                case 1:
                    Player_Score += FORMATION_ORANGE_SCORE;
                    break;
                case 2:
                case 3:
                    Player_Score += FORMATION_PINK_SCORE;
                    break;
                case 4:
                    Player_Score += FORMATION_BLUE_SCORE; 
                    break;
            }
        }
    }
    static void Receive_Boss_Level_Score(int DMG){
        Player_Score += DMG;
    }
    void resetScore(){
        Player_Score = 0;
    }
}
