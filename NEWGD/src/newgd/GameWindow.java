/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newgd;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JPanel;
import javax.swing.Timer;



/**
 *
 * @author ABCD
 */
public class GameWindow extends JPanel implements ActionListener, CollisionDetection{
    ArrayList<Enemy> EnemyArrayList;
    ArrayList<Bullets> PlayerBulletsArrayList;
    ArrayList<Bullets> EnemyBulletsArrayList;
    ArrayList<Item> ItemArrayList;
    ArrayList<Player> PlayerArrayList;
    private static final int 
            Maximum_Row = 10,
            Maximum_Col = 5, 
            MAXIMUM_IN_GAME_ENEMY = 5, 
            DELAY = 8, 
            Boss_Level_Rate = 20, 
            Boss_Level_Chosen_Rate = 15,
            ITEM_APPEAR_RATE = 3,
            ITEM_APPEAR_CHANCES = 10000;
            
    private int Level = 1,
            CurrentMovingEnemy, 
            TotalEnemy,
            hitPlayerID;
            
    boolean Boss_Level = false;
    private boolean 
            levelFinished = false,
            DROP_ITEM = false;
    
    Timer timer;
       
    private Message message;
    private Score score;
    Boss boss;
    
    GameWindow(){
        EnemyArrayList = new ArrayList<Enemy>();
        PlayerBulletsArrayList = new ArrayList<Bullets>();
        EnemyBulletsArrayList = new ArrayList<Bullets>();
        ItemArrayList = new ArrayList<Item>();
        PlayerArrayList = new ArrayList<Player>();
        startNewClassesInstances();
        clearAll();
        assignAllEnemy();

        System.out.println("SPGW Initilized");
        timer = new Timer(DELAY,this);
        timer.start();
    }
    
    public void paint (Graphics g){
        final double x = System.currentTimeMillis();
        g.setColor(Color.BLACK);
        g.fillRect(0,0,NEWGD.INITIAL_X_LENGTH, NEWGD.INITIAL_Y_LENGTH);
        
        if(!Boss_Level){
            //Draw Enemy
            for(Enemy enemy : EnemyArrayList){
                if(enemy.status != 0){
                   enemy.paint(g);
                }
            }
        } else{
            boss.render(g);
        }
        for (Bullets bullets : PlayerBulletsArrayList){
            bullets.paint(g);
        }
        for (Bullets bullets : EnemyBulletsArrayList){
            bullets.paint(g);
        }
        for(Player player : PlayerArrayList){
            player.paint(g);
            for(MovementAnimation ma : player.littleBlocks){
                ma.render(g);
            }
        }
        message.render(g);
        score.render(g);
        
        g.dispose();
        final double y = System.currentTimeMillis();
        //System.out.println("Paint run time: " + (y-x));
    }
    
    private void assignAllEnemy(){
        int EnemyCount = 0;
        for(int r = 0; r<Maximum_Row; r++){
            for (int c = 0; c<Maximum_Col; c++){
                Enemy enemy = new Enemy(r,c);
                EnemyArrayList.add(enemy);
                EnemyCount ++;
            }
        }
        TotalEnemy = EnemyCount;
    }

    private void runGame(){//How should i name this method? lol
        int EnemyInFormation = 0;
        int EnemyInGame = 0;
        
        //Enemy Stuff && Player Fired Bullet Collision Detection
        for(Enemy enemy : EnemyArrayList){
            setupEnemyCollisionDetection(enemy);
            moveEnemies(enemy);
            EnemyInFormation += countEnemyInFormation(enemy);
            EnemyInGame += countEnemyInGame(enemy);
        }

        //Generate new Enemy
        if (CurrentMovingEnemy<(MAXIMUM_IN_GAME_ENEMY + (Level-1)) && EnemyInFormation>0){
            generateNewMovingEnemy();
            CurrentMovingEnemy ++;
        }
        //Boss Fire Bullets
        if(Boss_Level && Boss.Boss_HP_Inqury()>0){
            boss.Fire();
        }
        //Player Stuff
        if(!Interaction.Pause_Inqury() && !Interaction.GameOver_Inqury()){
            for(Player player : PlayerArrayList){    
                player.playerFireBullets();
                player.playerMovement();
            }
        }
        checkWin(EnemyInGame);
        removeOutOfBoundBullets();
        removeMovementAnimationBlocks();
        detectCollision();
        checkGameOver();
    }
    
    private void generateNewMovingEnemy(){
        boolean FormationCheck = false;
        int ArrayPositionMark = 0;
        do{
            int i = ThreadLocalRandom.current().nextInt(0, EnemyArrayList.size());
            if(!EnemyArrayList.get(i).Moving && EnemyArrayList.get(i).status == 1){
                FormationCheck = true;
                ArrayPositionMark = i; 
            }
        }while (!FormationCheck);
        EnemyArrayList.get(ArrayPositionMark).Moving = true;
        EnemyArrayList.get(ArrayPositionMark).status = 2;        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        final double x = System.currentTimeMillis();
        if(!Interaction.Pause_Inqury()){
            runGame();
        }
        repaint();
        
        final double y = System.currentTimeMillis();
        //System.out.println("Game_Stuff run time: " + (y-x));
    }

    @Override
    public boolean Detection(int CollisionType, int XPos, int YPos, int X_Width, int Y_Height) {
        boolean Hit = false;
        //Player x Enemy Bullet
        if(CollisionType == 1){    
            Rectangle BulletRect = new Rectangle(XPos, YPos, X_Width, Y_Height);
            for(Player player : PlayerArrayList){
                if(BulletRect.intersects(
                        setupPlayerRectangle(player.ID_Inqury())
                )){
                    if(!player.playerSpawn_Inqury()){
                        Hit = true;
                        System.out.println("HITTED BY ENEMY BULLET");
                        hitPlayerID = player.ID_Inqury();
                        break;
                    }
                }
            }
            
        } else if(CollisionType == 0){
            Rectangle enemyRect = new Rectangle(XPos, YPos, X_Width, Y_Height);
            //Player Bullets x Enemy
            for (Bullets bullets : PlayerBulletsArrayList){
                Rectangle playerBulletRect = new Rectangle(bullets.XPos, bullets.YPos, bullets.Width, bullets.Height);
                if(playerBulletRect.intersects(enemyRect)){
                    Hit = true;
                    PlayerBulletsArrayList.remove(bullets);
                    System.out.println("HITTED ENEMY");
                    break;
                }
            }
            //Enemy x Player
            for(Player player: PlayerArrayList){
                if(enemyRect.intersects(setupPlayerRectangle(player.ID_Inqury()))){
                    if(!player.playerSpawn_Inqury()){
                        Hit = true;
                        hitPlayerID = player.ID_Inqury();
                        System.out.println("HITTED BY ENEMY");
                        break;
                    }
                }
            }
        }
        return Hit;
    }
    
    private void detectCollision(){
        // ... && Enemy Bullet Collision Detection
        for(Bullets bullets : EnemyBulletsArrayList){
            if (bullets.YPos>810){
                EnemyBulletsArrayList.remove(bullets);
                System.out.println("Removed Bullet");
                break;
            }
            //Player . Enemy Bullet Collision Detection
            if(Detection(1,bullets.XPos, bullets.YPos, bullets.Width, bullets.Height)){
                if(!PlayerArrayList.get(hitPlayerID).playerSpawn_Inqury()){
                    hitPlayer(hitPlayerID);
                    EnemyBulletsArrayList.remove(bullets);
                    break;
                }
            }
        }        
    }
    private void removeOutOfBoundBullets(){
        //Remove Out of Bound Player Bullets
        for(Bullets bullets : PlayerBulletsArrayList){
            if (bullets.YPos<-7){
                PlayerBulletsArrayList.remove(bullets);
                break;
            }
        }
    }
    private void removeMovementAnimationBlocks(){
        for(Player player : PlayerArrayList){
            for(MovementAnimation ma : player.littleBlocks){
                if(ma.xLength_Inqury() == 0 || ma.yLength_Inqury() == 0){
                    player.littleBlocks.remove(ma);
                    break;
                }
            }
        }
    }
    void clearAll(){ //private?
        PlayerBulletsArrayList.clear();
        EnemyBulletsArrayList.clear();
        EnemyArrayList.clear();
        MainMenu.interaction.resetKeyFunctions();
        message.resetTimeTracker();
        boss.Reset();
        
        CurrentMovingEnemy = 0;
        TotalEnemy = 0;
        levelFinished = false;
        Boss_Level = false;
    }
    
    void setNewLevel(){
        clearAll();
        assignAllEnemy();
        System.out.println("NEW LEVEL");
        Level++;
    }
    void resetLevel(){
        clearAll();
        assignAllEnemy();
        Level = 1;
        score.resetScore();
    }
    private void startNewClassesInstances(){
        addPlayerCondition();
        message = new Message();
        score = new Score();
        boss = new Boss();
    }
    private void moveEnemies(Enemy enemy){
        if(!Interaction.Pause_Inqury()){
            if(enemy.status == 2){
                if(enemy.Y_Pos_Inqury()< enemy.COL_Inqury()* enemy.Y_Space_Inqury() 
                        || enemy.Y_Pos_Inqury() > enemy.ENEMY_BOTTOM_BOUND 
                        || enemy.X_Pos_Inqury() > enemy.ENEMY_RIGHT_BOUND 
                        || enemy.X_Pos_Inqury() < enemy.ENEMY_LEFT_BOUND){
                    enemy.returnToMap();
                }
                else {
                    enemy.ReturnToPosition = false;
                    enemy.setMoving();
                    enemy.fireBullet();
                }
            } else if(enemy.status == 1){
                enemy.moveLR();
            }
        }
    }
    //Count Enemies
    private int countEnemyInFormation(Enemy enemy){
        int count = 0;
        if(enemy.status == 1){
            count ++;
        } 
        return count;
    }
    private int countEnemyInGame(Enemy enemy){
        int count = 0;
        if (enemy.status != 0){
            count ++;
        }
        return count;
    }
    
    private void hitPlayer(int playerID){
        Player player = PlayerArrayList.get(playerID);
        player.spawn();
        player.gotHit();
    }
    
    //Setups...
    private void setupEnemyCollisionDetection(Enemy enemy){
        if(enemy.status != 0){
            if(Detection(0, enemy.X_Pos_Inqury(), enemy.Y_Pos_Inqury(), 
                    enemy.X_Width_Inqury(), enemy.Y_Width_Inqury())){
                //Send Score
                if(enemy.Moving){
                    Score.Receive_Score("Moving", enemy.COL_Inqury());
                } else {
                    Score.Receive_Score("Formation", enemy.COL_Inqury());
                }
                if(enemy.status == 2){
                    CurrentMovingEnemy--;    
                }
                enemy.status = 0;
            }
        }
    }
    private Rectangle setupPlayerRectangle(int playerID){
        Player player = PlayerArrayList.get(playerID);
        Rectangle PlayerRect = new Rectangle(player.X_Inqury(), player.Y_Inqury(), 
                     player.PLAYER_X_LENGTH_Inqury(), player.PLAYER_Y_LENGTH_Inqury());
        return PlayerRect;
    }
    private void checkWin(int inGameEnemy){
        if(inGameEnemy == 0 && !Boss_Level){
            int i = ThreadLocalRandom.current().nextInt(0, Boss_Level_Rate);
            if(i>Boss_Level_Chosen_Rate){
                levelFinished = true;
                System.out.println("Level Finished");
            } else { 
                //Generate Boss
                if(!levelFinished){
                    Boss_Level = true;
                    boss.Reset();
                    System.out.println("Boss!!!");
                }
            }
        }
    }
    private void dropItem(){
        int i = ThreadLocalRandom.current().
                nextInt(0, ITEM_APPEAR_CHANCES);
        if(i<=ITEM_APPEAR_RATE){
            ItemArrayList.add(new Item());
        }
    }    
    private void addPlayer(int playerID){
        Player player = new Player(playerID);
        PlayerArrayList.add(player);
        System.out.println("NEW PLAYER GENERATED");
    }
    private void addPlayerCondition(){
        if(Interaction.state == Interaction.GameState.Two_Player){
            for(int playerID = 0; playerID<=1; playerID ++){
                addPlayer(playerID);
                System.out.println(playerID + " player");
            }
            System.out.println("Finish adding two players");
        } else if(Interaction.state == Interaction.GameState.Single_Player){
            addPlayer(0);
            System.out.println("Finish adding one player");
        } 
    }
    private void checkGameOver(){
        int deadCount = 0;
        for(Player player : PlayerArrayList){
            if(player.Dead_Inqury()){
                deadCount++;
            }
        }
        if(deadCount>=PlayerArrayList.size()){
            Interaction.setGameOver();
        }
    }
    public boolean Level_Finished_Inqury(){
        return levelFinished;
    }
    public int Level_Inqury(){
        return Level;
    }
}
