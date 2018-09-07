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
public class Message {

    private static final int 
            LEVEL_COMPLETED_MESSAGE_X_POS = 200, 
            LEVEL_COMPLETED_MESSAGE_Y_POS = 470,
            PAUSE_X_POS = 220, 
            PAUSE_Y_POS = 400,
            MESSAGE_FLASH_RATE = 1000, 
            MESSAGE_APPEAR_RATE = 700;
    private int TimeTracker;
    void render (Graphics g){
        //Next Level Message
        if(MainMenu.gw.Level_Finished_Inqury()){
            if(!Interaction.Pause_Inqury()){
                TimeTracker++;
                int CurrentTime = TimeTracker*8;
                g.setColor(Color.WHITE);

                if(CurrentTime%MESSAGE_FLASH_RATE < MESSAGE_APPEAR_RATE){
                    g.setFont(new Font("serif", Font.BOLD, 25));
                    g.drawString(
                            "Level " + MainMenu.gw.Level_Inqury() + " Completed", 
                            LEVEL_COMPLETED_MESSAGE_X_POS, LEVEL_COMPLETED_MESSAGE_Y_POS);
                    g.setFont(new Font("serif", Font.BOLD, 15));
                    g.drawString("Next game begins in " + 
                            (4 - CurrentTime/1000) + " second", 
                            LEVEL_COMPLETED_MESSAGE_X_POS+5, LEVEL_COMPLETED_MESSAGE_Y_POS + 30);                
                }
                if (CurrentTime == 4000){
                    MainMenu.gw.setNewLevel();
                }
            }
        }
        //Pause
        if(Interaction.Pause_Inqury()){
            TimeTracker ++;
            if((TimeTracker*8)%MESSAGE_FLASH_RATE< MESSAGE_APPEAR_RATE){
                g.setColor(Color.WHITE);
                g.setFont(new Font("serif", Font.BOLD, 25));
                g.drawString("Paused",PAUSE_X_POS, PAUSE_Y_POS);
                g.setFont(new Font("serif", Font.BOLD, 15));
                g.drawString("Press TAB to continue", PAUSE_X_POS, PAUSE_Y_POS + 30);
                g.drawString("Press ESC to back to main menu", PAUSE_X_POS, PAUSE_Y_POS + 50);
            }
            if(TimeTracker>450){
                TimeTracker = 0;
            }
        }
        //Game Over
        if(Interaction.GameOver_Inqury()){
            TimeTracker ++;
            if((TimeTracker*8)%MESSAGE_FLASH_RATE< MESSAGE_APPEAR_RATE){
                g.setColor(Color.WHITE);
                g.setFont(new Font("serif", Font.BOLD, 25));
                g.drawString("Game Over!",PAUSE_X_POS, PAUSE_Y_POS); 
                g.setFont(new Font("serif", Font.BOLD, 15));
                g.drawString("Press SPACE to play again", PAUSE_X_POS, PAUSE_Y_POS + 30);
                g.drawString("Press ESC to back to main menu", PAUSE_X_POS, PAUSE_Y_POS + 50);
            }
            if(TimeTracker>450){
                TimeTracker = 0;
            }
        }
        //Boss Finished
        if(Boss.Boss_HP_Inqury()<=0 && Boss.Exit_Inqury()){
            if(!Interaction.Pause_Inqury()){
                TimeTracker++;
                int CurrentTime = TimeTracker*8;
                g.setColor(Color.WHITE);

                if(CurrentTime%MESSAGE_FLASH_RATE < MESSAGE_APPEAR_RATE){
                    g.setFont(new Font("serif", Font.BOLD, 25));
                    g.drawString(
                            "Boss Level Completed", 
                            LEVEL_COMPLETED_MESSAGE_X_POS, LEVEL_COMPLETED_MESSAGE_Y_POS);
                    g.setFont(new Font("serif", Font.BOLD, 15));
                    g.drawString("Next game begins in " + 
                            (4 - CurrentTime/1000) + " second", 
                            LEVEL_COMPLETED_MESSAGE_X_POS+5, LEVEL_COMPLETED_MESSAGE_Y_POS + 30);                
                }
                if (CurrentTime == 4000){
                    MainMenu.gw.boss.Reset();
                    MainMenu.gw.setNewLevel();
                }
            }
        }
    }
    public void resetTimeTracker(){
        TimeTracker = 0;
    }
}
