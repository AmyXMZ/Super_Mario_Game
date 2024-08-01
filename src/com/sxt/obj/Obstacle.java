package com.sxt.obj;

import com.sxt.util.BackGround;
import com.sxt.util.StaticValue;

import java.awt.image.BufferedImage;
/*
* This class, Obstacle, stores all types of obstacles and selectively display them on the frame with specific background. Thread
* was used to enable movement of specific obstacles on the frame
* */
public class Obstacle implements Runnable{
    //represents the x-axis of the obstacle
    private int x;
    //represents the y-axis of the obstacle
    private int y;
    //represents the various types of obstacles
    private int type;
    //show the images of the obstacles
    private BufferedImage show = null;
    //show the current background
    private BackGround bg = null;
    //enable the movement of obstacles on the frame
    private Thread thread = new Thread(this);

    public Obstacle() { //constructor with no parameters
    }
    //constructor with 4 parameters, specifying its location on the frame (x,y), its type, and the level (background) its at
    public Obstacle(int x, int y, int type, BackGround bg) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.bg = bg;
        show = StaticValue.obstacle.get(type);
        //if type == 8, the obstacle is a flag, then enable its motion by starting thread
        if (type == 8) {
            thread.start();
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }


    public BackGround getBg() {
        return bg;
    }

    public BufferedImage getShow() {
        return show;
    }

    @Override
    public void run() {
        while (true) {
            if (this.bg.isReach()) {
                if (this.y < 374) {
                    this.y += 5; //allowing the flag to slowly descend to the ground
                }else {
                    this.bg.setBase(true); //stop descending the flag if it's already at the ground level
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
