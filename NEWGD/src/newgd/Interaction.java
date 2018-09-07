/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newgd;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author ABCD
 */
public class Interaction implements KeyListener{ 
    private static boolean 
            Pause = false, 
            GameOver = false;
    private MainMenu mainmenu;
    static enum GameState{
        Menu, Single_Player, Two_Player
    }
    
    static GameState state = GameState.Menu;
    
    public Interaction (){
        System.out.println("New Game Generated");
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!Pause){
            //Always have Single Player Keys
            if(state != GameState.Menu){
                Player player =  MainMenu.gw.PlayerArrayList.get(0);
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    player.setFireBoolean(true);
                    if(GameOver){
                        resetAll();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_A) {
                    player.setMovingBoolean("L",true);
                } else if (e.getKeyCode() == KeyEvent.VK_D) {
                    player.setMovingBoolean("R",true);
                } else if (e.getKeyCode() == KeyEvent.VK_W) {
                    player.setMovingBoolean("U",true);
                } else if (e.getKeyCode() == KeyEvent.VK_S) {
                    player.setMovingBoolean("D",true);
                }
            }
            //Switch bullet types
            if(state == GameState.Single_Player){
                Player player =  MainMenu.gw.PlayerArrayList.get(0);
                int PlayerBulletType = player.PlayerBulletType_Inqury();
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if(PlayerBulletType == 0){
                        player.changeBulletType(6);
                    }
                    else if (PlayerBulletType == 4){
                        player.changeBulletType(0);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if(PlayerBulletType == 6){
                        player.changeBulletType(0);
                    }
                    if(PlayerBulletType == 0){
                        player.changeBulletType(4);
                    }
                }
            }
            else if(state == GameState.Two_Player) {
                Player player = MainMenu.gw.PlayerArrayList.get(1);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    player.setFireBoolean(true);
                    if(GameOver){
                        resetAll();
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player.setMovingBoolean("L",true);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player.setMovingBoolean("R",true);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    player.setMovingBoolean("U",true);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    player.setMovingBoolean("D",true);
                }
            }
        }
        if(state != GameState.Menu){
            if(e.getKeyCode() == KeyEvent.VK_TAB){
                Pause = !Pause;
                System.out.println(Pause  + " TAB PRESSED");
            } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){   
                resetAll();
                destoryPlayerGameWindow();
                backtoMainMenu();
                System.out.println("Escape Pressed");
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        //Single Player Release Control
        if(!Pause && state != GameState.Menu){
            Player player =  MainMenu.gw.PlayerArrayList.get(0);
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                player.setFireBoolean(false);
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                player.setMovingBoolean("L",false);
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                player.setMovingBoolean("R",false);
            } else if (e.getKeyCode() == KeyEvent.VK_W) {
                player.setMovingBoolean("U",false);
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                player.setMovingBoolean("D",false);
            }
            if(state == GameState.Two_Player){
                Player player2 = MainMenu.gw.PlayerArrayList.get(1);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    player2.setFireBoolean(false);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    player2.setMovingBoolean("L",false);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    player2.setMovingBoolean("R",false);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    player2.setMovingBoolean("U",false);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    player2.setMovingBoolean("D",false);
                }
            }
        }
    }
    
    static void changeGameState(String s){
        switch(s){
            case "Single":
                state = GameState.Single_Player;
                break;
            case "Two":
                state = GameState.Two_Player;
                break;
            case "Menu":
                state = GameState.Menu;
                break;
        }
    }
    private void resetAll(){
        for(Player player : MainMenu.gw.PlayerArrayList){
            player.resetAll();
        }
        MainMenu.gw.clearAll();
        MainMenu.gw.resetLevel();
    }
    private void destoryPlayerGameWindow(){// EXCEPTIONS!!!!!
        MainMenu.gw.clearAll();
        NEWGD.obj.remove(MainMenu.gw);
        setGameOver();
        
        //Problem starts here
        MainMenu.gw.timer.stop();
        MainMenu.gw.timer = null;
        MainMenu.interaction = null;
        MainMenu.gw = null;
        
        System.out.println(MainMenu.gw);
    }
    private void backtoMainMenu(){
        mainmenu = new MainMenu();
        NEWGD.obj.add(mainmenu);
        NEWGD.obj.revalidate();
        state = GameState.Menu;
    }
    public static void setGameOver(){
        GameOver = true;
    }
    public void resetKeyFunctions(){ //Private? or Non-static?
        GameOver = false;
        Pause = false;
    }
    
    //Inquriessssss
    public static boolean GameOver_Inqury(){
        return GameOver;
    }
    public static boolean Pause_Inqury(){
        return Pause;
    }
}
