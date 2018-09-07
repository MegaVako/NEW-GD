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
 * @author SimonSun
 */
public class Boss implements CollisionDetection {
    private static final int 
            BLOCK_1_WIDTH = 100, 
            BLOCK_1_HEIGHT = 40,
            BLOCK_2_WIDTH = 40, 
            BLOCK_2_HEIGHT = 30,
            BLOCK_3_WIDTH = 30, 
            BLOCK_3_HEIGHT = 25,
            DRAW_HP_X_POS = 100, 
            DRAW_HP_Y_POS = 20,
            RIGHT_BOUND = 610, 
            LEFT_BOUND = 0,
            DIRECTION_TURN_CO = 100, 
            RATE_OF_DIRECTION_TURN = 2;
    private boolean 
            Block1_Fire, 
            Block2L_Fire, 
            Block2R_Fire, 
            Block3L_Fire, 
            Block3R_Fire;
    static boolean 
            Boss_Level_Finished = false, 
            Exit_Finished = false;
    private int Boss_X_Center, 
            Boss_Y_Center, 
            Block1_Fire_CD = 40, 
            Block2_L_Fire_CD = 60, 
            Block2_R_Fire_CD = 70,
            Block3_L_Fire_CD = 50, 
            Block3_R_Fire_CD = 80;
    private static int 
            Block1_HP, 
            Block2_L_HP, 
            Block2_R_HP, 
            Block3_L_HP, 
            Block3_R_HP, 
            X_Vector = 3;
    private int Bullet_DMG, 
            TimeTracker = 0;
    private static final int 
            Y_Vector = 2, 
            BOSS_Y_SET_POSITION = 200, 
            BOSS_FIRE_CD = 50,
            BLOCK_3_FIRE_CD_RANGE =3;
    static boolean Initial_Status;
    
    Boss(){
        Block1_Fire = true;
        Block2L_Fire = true;
        Block2R_Fire = true;
        Block3L_Fire = true;
        Block3R_Fire = true;
        Boss_X_Center = 300;
        Boss_Y_Center = -200;
        Block1_HP = 1500;
        Block2_L_HP = Block2_R_HP = 800;
        Block3_L_HP = Block3_R_HP = 500;
    }
    
    public void render (Graphics g){
        int Block1_X_Pos = Boss_X_Center-(BLOCK_1_WIDTH/2);
        int Block1_Y_Pos = Boss_Y_Center-(BLOCK_1_HEIGHT/2);
        int Block2_L_X_Pos = Boss_X_Center-(BLOCK_1_WIDTH/2)-BLOCK_2_WIDTH;
        int Block2_L_Y_Pos = Boss_Y_Center-(BLOCK_2_HEIGHT/2);
        int Block2_R_X_Pos = Boss_X_Center+(BLOCK_1_WIDTH/2);
        int Block2_R_Y_Pos = Boss_Y_Center-(BLOCK_2_HEIGHT/2);
        int Block3_L_X_Pos = Boss_X_Center-(BLOCK_1_WIDTH/2)-BLOCK_2_WIDTH - BLOCK_3_WIDTH;
        int Block3_L_Y_Pos = Boss_Y_Center-(BLOCK_3_HEIGHT/2);
        int Block3_R_X_Pos = Boss_X_Center+(BLOCK_1_WIDTH/2)+BLOCK_2_WIDTH;
        int Block3_R_Y_Pos = Boss_Y_Center-(BLOCK_3_HEIGHT/2);
        
        if(!Boss_Level_Finished && MainMenu.gw.Boss_Level){
            //Block 1
            if(Block1_HP > 0){
                if(Detection(2,Block1_X_Pos, Block1_Y_Pos, BLOCK_1_WIDTH, BLOCK_1_HEIGHT)){
                    Block1_HP -= Bullet_DMG;
                    g.setColor(Color.YELLOW);
                    Score.Receive_Boss_Level_Score(Bullet_DMG);
                }else {
                    g.setColor(Color.ORANGE);
                }
                g.fillRect(Block1_X_Pos, Block1_Y_Pos, BLOCK_1_WIDTH, BLOCK_1_HEIGHT);
            } else{
               Block1_Fire = false; 
            }

            //BLock 2 L
            if(Block2_L_HP > 0){
                if(Detection(2,Block2_L_X_Pos, Block2_L_Y_Pos, BLOCK_2_WIDTH, BLOCK_2_HEIGHT)){
                    Block2_L_HP -= Bullet_DMG;
                    g.setColor(Color.YELLOW);
                    Score.Receive_Boss_Level_Score(Bullet_DMG);
                }else {
                    g.setColor(Color.ORANGE);
                }
                g.fillRect(Block2_L_X_Pos, Block2_L_Y_Pos, BLOCK_2_WIDTH, BLOCK_2_HEIGHT);          
            } else {
                Block2L_Fire = false;
            }

            //Block 2 R
            if(Block2_R_HP > 0){
                if(Detection(2,Block2_R_X_Pos, Block2_R_Y_Pos, BLOCK_2_WIDTH, BLOCK_2_HEIGHT)){
                    Block2_R_HP -= Bullet_DMG;
                    g.setColor(Color.YELLOW);
                    Score.Receive_Boss_Level_Score(Bullet_DMG);
                }else {
                    g.setColor(Color.ORANGE);
                }
                g.fillRect(Block2_R_X_Pos, Block2_R_Y_Pos, BLOCK_2_WIDTH, BLOCK_2_HEIGHT);
            } else {
               Block2R_Fire = false;
            }

            //Block 3 L
            if(Block3_L_HP > 0){
                if(Detection(2,Block3_L_X_Pos, Block3_L_Y_Pos, BLOCK_3_WIDTH, BLOCK_3_HEIGHT)){
                    Block3_L_HP -= Bullet_DMG;
                    g.setColor(Color.YELLOW);
                    Score.Receive_Boss_Level_Score(Bullet_DMG);
                }else {
                    g.setColor(Color.ORANGE);
                }
                g.fillRect(Block3_L_X_Pos, Block3_L_Y_Pos, BLOCK_3_WIDTH, BLOCK_3_HEIGHT);
            } else {
                Block3L_Fire = false;
            }      

            //Block 3 R
            if(Block3_R_HP > 0){
                if(Detection(2,Block3_R_X_Pos, Block3_R_Y_Pos, BLOCK_3_WIDTH, BLOCK_3_HEIGHT)){
                    Block3_R_HP -= Bullet_DMG;
                    g.setColor(Color.YELLOW);
                    Score.Receive_Boss_Level_Score(Bullet_DMG);
                }else {
                    g.setColor(Color.ORANGE);
                }
                g.fillRect(Block3_R_X_Pos, Block3_R_Y_Pos, BLOCK_3_WIDTH, BLOCK_3_HEIGHT);
            } else {
                Block3R_Fire = false;
            }
        }
        
        int TotalHP = Block1_HP +
        Block2_L_HP + Block2_R_HP + 
        Block3_L_HP + Block3_R_HP;
        g.fillRect(DRAW_HP_X_POS, DRAW_HP_Y_POS, TotalHP/10, 5);
        
        if(Initial_Status && TotalHP > 0){
            Enter();
            //System.out.println("Boss Enter");
        } else if(TotalHP <= 0 && !Exit_Finished){
            TimeTracker ++;
            Boss_Level_Finished = true;
            Exit();
            g.setColor(Color.ORANGE);
            g.fillRect(Block1_X_Pos, Block1_Y_Pos, BLOCK_1_WIDTH, BLOCK_1_HEIGHT);
            g.fillRect(Block2_L_X_Pos, Block2_L_Y_Pos, BLOCK_2_WIDTH, BLOCK_2_HEIGHT);
            g.fillRect(Block2_R_X_Pos, Block2_R_Y_Pos, BLOCK_2_WIDTH, BLOCK_2_HEIGHT);
            g.fillRect(Block3_L_X_Pos, Block3_L_Y_Pos, BLOCK_3_WIDTH, BLOCK_3_HEIGHT);
            g.fillRect(Block3_R_X_Pos, Block3_R_Y_Pos, BLOCK_3_WIDTH, BLOCK_3_HEIGHT);
            if(TimeTracker%3 == 1){
                int i = ThreadLocalRandom.current().nextInt(Block3_L_X_Pos, Block3_R_X_Pos+BLOCK_3_WIDTH);
                int j = ThreadLocalRandom.current().nextInt(Block1_Y_Pos, Block1_Y_Pos+BLOCK_1_HEIGHT);
                int k = ThreadLocalRandom.current().nextInt(10, 20);
                g.setColor(Color.WHITE);
                g.fillRect(i,j,k,k);
            }
            //System.out.println("Exit Process");
        } else if(!Initial_Status && TotalHP > 0){
            Boss_Movement(); 
            //System.out.println("Boss Movement");
        }
    }

    @Override
    public boolean Detection(int CollisionType, int XPos, int YPos, int X_Width, int Y_Height) {
        boolean Hit = false;
        Rectangle BossBlock = new Rectangle(XPos, YPos, X_Width, Y_Height);
        for (Bullets bullets : MainMenu.gw.PlayerBulletsArrayList){
            Rectangle BulletRect = new Rectangle(bullets.XPos, bullets.YPos, bullets.Width, bullets.Height);
            if(BulletRect.intersects(BossBlock)){
                Hit = true;
                Bullet_DMG = bullets.DMG_Inqury();
                MainMenu.gw.PlayerBulletsArrayList.remove(bullets);
                break;
            }
        }
        return Hit;
    }
    
    private void Enter(){
        if(Boss_Y_Center < BOSS_Y_SET_POSITION){
            Boss_Y_Center += Y_Vector;
        } else{
            Initial_Status = false;
            Exit_Finished = false;
        }
    }
    void Exit(){
        if(Boss_Y_Center > -200){
            Boss_Y_Center -= Y_Vector/2;
        } else{
            Exit_Finished = true;
        }       
    }
    static void resetHP(){
        Block1_HP = 1500;
        Block2_L_HP = Block2_R_HP= 800;
        Block3_L_HP = Block3_R_HP = 500;
    }
    public static int Boss_HP_Inqury(){
        int HP = Block1_HP +
        Block2_L_HP + Block2_R_HP + 
        Block3_L_HP + Block3_R_HP;
        return HP;
    }
    private void Boss_Movement(){
        TimeTracker ++;
        if((Boss_X_Center + (BLOCK_1_WIDTH/2) + 
                BLOCK_2_WIDTH + BLOCK_3_WIDTH) > RIGHT_BOUND){
            X_Vector = -X_Vector;
        } else if((Boss_X_Center - (BLOCK_1_WIDTH/2) - BLOCK_2_WIDTH 
                - BLOCK_3_WIDTH)< LEFT_BOUND){
            X_Vector = -X_Vector;
        } 
        if(TimeTracker%4 == 1){
            if(ThreadLocalRandom.current().nextInt(0, DIRECTION_TURN_CO)
                    <RATE_OF_DIRECTION_TURN){
                X_Vector = -X_Vector;
            } 
            if(TimeTracker>100){
                TimeTracker = 0;
            }
        }
        Boss_X_Center += X_Vector;
    }
    void Fire(){
        fireBlock1();
        fireBlock2L();
        fireBlock2R();
        fireBlock3L();
        fireBlock3R();
    }
    void Reset(){
        Initial_Status = true;
        resetPosition();
        resetHP();
        resetFireStatus();
        resetStatus();
    }
    private void resetFireStatus(){
        Block1_Fire = true;
        Block2L_Fire = true;
        Block2R_Fire = true;
        Block3L_Fire = true;
        Block3R_Fire = true;
    }
    private void resetPosition(){
        Boss_X_Center = 300;
        Boss_Y_Center = -200;
    }
    void resetStatus(){
        Boss.Boss_Level_Finished = false;
        Boss.Initial_Status = true;
    }
    private void fireBlock1(){
        if(Block1_Fire){
            if(Block1_Fire_CD == BOSS_FIRE_CD){
                Bullets bullets = new Bullets(1,Boss_X_Center, Boss_Y_Center+(BLOCK_1_HEIGHT/2));
                MainMenu.gw.EnemyBulletsArrayList.add(bullets);
               Block1_Fire_CD --; 
            } else {
               Block1_Fire_CD --;
            } 
            if(Block1_Fire_CD == 0){
                Block1_Fire_CD = BOSS_FIRE_CD;
            }
        }
    }
    private void fireBlock2L(){
        if(Block2L_Fire){
            int Block2_L_X_Pos = Boss_X_Center-(BLOCK_1_WIDTH/2)-(BLOCK_2_WIDTH/2);
            int Block2_L_Y_Pos = Boss_Y_Center+(BLOCK_2_HEIGHT/2);
            if(Block2_L_Fire_CD == BOSS_FIRE_CD*4){
                    Bullets bullets1 = new Bullets(1,Block2_L_X_Pos, Block2_L_Y_Pos);
                MainMenu.gw.EnemyBulletsArrayList.add(bullets1);
                    Bullets bullets2 = new Bullets(5,Block2_L_X_Pos, Block2_L_Y_Pos);
                MainMenu.gw.EnemyBulletsArrayList.add(bullets2);
                    Bullets bullets3 = new Bullets(7,Block2_L_X_Pos, Block2_L_Y_Pos);
                MainMenu.gw.EnemyBulletsArrayList.add(bullets3);
                Block2_L_Fire_CD --; 
            } else {
               Block2_L_Fire_CD --;
            } 
            if(Block2_L_Fire_CD == 0){
                Block2_L_Fire_CD = BOSS_FIRE_CD*4;
            }
        }
    }
    private void fireBlock2R(){
        if(Block2R_Fire){
            int Block2_R_X_Pos = Boss_X_Center+(BLOCK_1_WIDTH/2)+(BLOCK_2_WIDTH/2);
            int Block2_R_Y_Pos = Boss_Y_Center+(BLOCK_2_HEIGHT/2);
            if(Block2_R_Fire_CD == BOSS_FIRE_CD*4){
                    Bullets bullets1 = new Bullets(1,Block2_R_X_Pos, Block2_R_Y_Pos);
                MainMenu.gw.EnemyBulletsArrayList.add(bullets1);
                    Bullets bullets2 = new Bullets(5,Block2_R_X_Pos, Block2_R_Y_Pos);
                MainMenu.gw.EnemyBulletsArrayList.add(bullets2);
                    Bullets bullets3 = new Bullets(7,Block2_R_X_Pos, Block2_R_Y_Pos);
                MainMenu.gw.EnemyBulletsArrayList.add(bullets3);
                Block2_R_Fire_CD --; 
            } else {
               Block2_R_Fire_CD --;
            } 
            if(Block2_R_Fire_CD == 0){
                Block2_R_Fire_CD = BOSS_FIRE_CD*4;
            }
        }
    }
    private void fireBlock3L(){
        if(Block3L_Fire){
            int Block3_L_X_Pos = 
                Boss_X_Center-(BLOCK_1_WIDTH/2)-BLOCK_2_WIDTH-(BLOCK_3_WIDTH/2);
            int Block3_L_Y_Pos = Boss_Y_Center+(BLOCK_3_HEIGHT/2);
            if(Block3_L_Fire_CD <= BLOCK_3_FIRE_CD_RANGE){
                MainMenu.gw.EnemyBulletsArrayList.
                    add(new Bullets(1,Block3_L_X_Pos, Block3_L_Y_Pos));
                Block3_L_Fire_CD --;
            } else {
                Block3_L_Fire_CD--;
            }
            if(Block3_L_Fire_CD == 0){
                Block3_L_Fire_CD = BOSS_FIRE_CD;
            }
        }
    }
    private void fireBlock3R(){
        if(Block3R_Fire){
            int Block3_R_X_Pos = 
                Boss_X_Center+(BLOCK_1_WIDTH/2)+BLOCK_2_WIDTH+(BLOCK_3_WIDTH/2);
            int Block3_R_Y_Pos = Boss_Y_Center+(BLOCK_3_HEIGHT/2);
            if(Block3_R_Fire_CD <= BLOCK_3_FIRE_CD_RANGE){
                MainMenu.gw.EnemyBulletsArrayList.
                    add(new Bullets(1,Block3_R_X_Pos, Block3_R_Y_Pos));
                Block3_R_Fire_CD --;
            } else {
                Block3_R_Fire_CD--;
            }
            if(Block3_R_Fire_CD == 0){
                Block3_R_Fire_CD = BOSS_FIRE_CD;
            }
        }
    }
    public static boolean Exit_Inqury(){
        return Exit_Finished;
    }
}
