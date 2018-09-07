/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newgd;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author ABCD
 */
public class NEWGD {
    
    private static final int INITIAL_X_POSITION = 300;
    private static final int INITIAL_Y_POSITION = 10;
    static final int INITIAL_X_LENGTH = 600;
    static final int INITIAL_Y_LENGTH = 800;
    static JFrame obj;
    private OnMenuItemSelected onMenuItemSelected;
    
    public static void main(String[] args){
        obj = new JFrame("Test");

        MainMenu mainmenu = new MainMenu();
        obj.setBounds(INITIAL_X_POSITION, INITIAL_Y_POSITION, INITIAL_X_LENGTH, INITIAL_Y_LENGTH);
        obj.setTitle("Galactic Desctruction");
        obj.setResizable(false);

        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        obj.setFocusable(true);
        obj.setFocusTraversalKeysEnabled(false);
        obj.add(mainmenu);
        obj.setVisible(true);
        System.out.println("main run check");
    }
}
