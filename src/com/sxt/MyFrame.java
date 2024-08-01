package com.sxt;

import com.sxt.obj.Mario;
import com.sxt.obj.Obstacle;
import com.sxt.util.BackGround;
import com.sxt.util.StaticValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;


/**
 * The class MyFrame extends JFrame to combine two interfaces to be able to run in a separate thread and react to key activities.
 * This class creates a fixed frame allowing the game to run.
*/
public class MyFrame extends JFrame implements KeyListener, Runnable {

    //store all background images
    private List<BackGround> allBg = new ArrayList<>();
    //store the current background image
    private BackGround nowBg = new BackGround(); //use constructor with no parameters
    //used for double caching
    private Image offScreenImage = null;

    //create a new Mario class object
    private Mario mario = new Mario();
    //allow the object Mario to move on the frame
    private Thread thread = new Thread(this);
    public MyFrame() {
        //setting the size of the frame
        this.setSize(800,600);
        //set the frame to be centered on the screen
        this.setLocationRelativeTo(null);
        //set the visibility of the screen
        this.setVisible(true);
        //allow the player to exit the game when they click the close button on the upper right hand corner
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //disable the frame resizability
        this.setResizable(false);
        //add to the frame the keylisterner (so that Mario can react to the keys pressed by the player)
        this.addKeyListener(this);
        //set the frame title
        this.setTitle("SuperMario");

        //after setting the game frame, initialize all object images
        StaticValue.init();
        //initialize Mario on the frame
        mario = new Mario(10,385);
        //initialize all the necessary background images
        for (int i = 1; i <= 3; i++) {
            allBg.add(new BackGround(i, i==3 ? true:false));
        }
        //set level 1 background to be the current frame background
        nowBg = allBg.get(0);
        mario.setBackGround(nowBg);//allowing Mario to be able to "stay" on top of the ground
        //paint the images onto the frame
        repaint();//used to request that a component or container be redrawn
        thread.start();
        //set the background music for the game
        new Music();
    }


    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame();
    }

    @Override
    public void paint(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = createImage(800,600);
        }
        Graphics graphics = offScreenImage.getGraphics();
        graphics.fillRect(0,0,800,600);
        //The fillRect(int x, int y, int width, int height) method
        //is used to draw a filled rectangle with the specified dimensions and location.
        //x: The x-coordinate of the upper-left corner of the rectangle.
        //y: The y-coordinate of the upper-left corner of the rectangle.
        //width: The width of the rectangle.
        //height: The height of the rectangle.

        //draw the background image onto the frame
        graphics.drawImage(nowBg.getBgImage(),0,0,this);
        //draw the enemy (have to draw the enemy, Piranha flower before the pipe so that it can be covered when it goes down the pipe)
        for (Enemy e : nowBg.getEnemyList()){
            graphics.drawImage(e.getShow(),e.getX(),e.getY(),this);
        }
        //draw the current scores
        Color c = graphics.getColor();
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("BOLD",Font.BOLD,25));
        graphics.drawString("Current Scores: " + mario.getScore(),300,100);
        graphics.setColor(c);

        //draw obstacles
        for (Obstacle ob : nowBg.getObstacleList()){
            graphics.drawImage(ob.getShow(),ob.getX(),ob.getY(),this);
        }

        //draw the castle (for level 3)
        graphics.drawImage(nowBg.getTower(),620,270,this);

        //draw flagpole (for level 3)
        graphics.drawImage(nowBg.getGan(),500,220,this);

        //draw Mario
        graphics.drawImage(mario.getShow(),mario.getX(),mario.getY(),this);

        //add the drawn images to the frame
        g.drawImage(offScreenImage,0,0,this);
    }

    @Override
    public void run() { //runnable interface
        // whenever Mario enters the next level, its image will be redrawn and added to the frame at the entry position
        while (true){
            repaint();
            try {
                Thread.sleep(50);
                //redraw Mario if it reaches 775 on the x-axis on the frame (the max size on the x-axis of the frame is 800)
                if (mario.getX() >= 775){
                    nowBg = allBg.get(nowBg.getSort());//！
                    mario.setBackGround(nowBg);
                    mario.setX(10);
                    mario.setY(395);
                }
                //if Mario dies, exit the game
                if (mario.isDeath()){
                    JOptionPane.showMessageDialog(this, "Game Over");
                    System.exit(0);
                }
                //if Mario clears the game successfully (all 3 levels), then exit the game
                if (mario.isOK()) {
                    JOptionPane.showMessageDialog(this, "Congradulations! You have reached the goal!");
                    System.exit(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) { //called when the key is pressed
        //control Mario and move it to the right
        if (e.getKeyCode() == 39){
            mario.rightMove();;
        }
        //move Mario to the left
        if (e.getKeyCode() == 37){
            mario.leftMove();
        }
        //Mario jumps
        if (e.getKeyCode() == 38){
            mario.jump();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {//called when the keys have been released
        //stop to the left
        if (e.getKeyCode() == 37){
            mario.leftStop();
        }
        //stop to the right
        if (e.getKeyCode() == 39){
            mario.rightStop();
        }

    }
}
