package com.sxt;

import com.sxt.obj.Obstacle;
import com.sxt.util.BackGround;
import com.sxt.util.StaticValue;

import java.awt.image.BufferedImage;

public class Enemy implements Runnable {

    //current location on the frame
    private int x,y;

    //the enemy types
    private int type;
    //the direction (left or right, up or down) that the enemy moves
    private boolean face_to = true;
    //display the image of the current enemy
    private BufferedImage show;
    //background
    private BackGround bg;
    //the upper bound limit that the Piranha flower can move
    private int max_up = 0;
    //the lower bound limit that the Piranha flower can move
    private int max_down = 0;

    private int image_type = 0;
    private Thread thread = new Thread(this);
    //the constructor of mushroom enemy
    public Enemy (int x, int y, boolean face_to, int type, BackGround bg) {
        this.x = x;
        this.y = y;
        this.face_to = face_to;
        this.type = type;
        this.bg = bg;
        show = StaticValue.mogu.get(0);
        thread.start();
    }
    //the constructor of Piranha flower
    public Enemy (int x, int y, boolean face_to, int type, int max_up, int max_down,BackGround bg){
        this.x = x;
        this.y = y;
        this.face_to = face_to;
        this.type = type;
        this.max_up = max_up;
        this.max_down = max_down;
        this.bg = bg;
        show = StaticValue.flower.get(0);
        thread.start();
    }


    @Override
    public void run() {
        while (true) {
            //to see if the enemy is the mushroom
            if (type == 1) { //type 1 indicates mushroom enemy
                if (face_to) { //true: move left
                    this.x -= 2;
                }else {//move right
                    this.x += 2;
                }
                image_type = image_type == 1 ? 0 : 1;
                show = StaticValue.mogu.get(image_type);
            }

            boolean canLeft = true;
            boolean canRight = true;
            for (int i = 0; i < bg.getObstacleList().size(); i++) {
                Obstacle ob1 = bg.getObstacleList().get(i);
                //to see if it can move to the right
                if (ob1.getX() == this.x + 36 && (ob1.getY() + 65 > this.y && ob1.getY() - 35 < this.y)) {//if yes, it means there is obstacle on its right, cannot further move right
                    canRight = false;
                }
                //to see if it can move to the left
                if (ob1.getX() == this.x - 36 && (ob1.getY() + 65 > this.y && ob1.getY() - 35 < this.y)){
                    canLeft = false;
                }
            }
            if (face_to && !canLeft || this.x == 0) {
                face_to = false;
            }
            else if ((!face_to) && (!canRight || this.x == 764)) {
                face_to = true;
            }
            //if type 2, the enemy is Piranha flower
            if (type == 2) {
                if (face_to){
                    this.y -= 2;
                }else{
                    this.y += 2;
                }
                image_type = image_type == 1 ? 0 : 1;
                //to see if the Piranha flower has moved to its upper bound location
                if (face_to && (this.y == max_up)){
                    face_to = false; //if yes, moves downward
                }
                if ((!face_to) && (this.y == max_down)){
                    face_to = true;
                }
                show = StaticValue.flower.get(image_type);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    //the ways that the enemies die (note: mushroom enemy can be killed, but Piranha flower can't)
    public void death () {
        show = StaticValue.mogu.get(2);//show the image when the mushroom dies
        this.bg.getEnemyList().remove(this);
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getShow() {
        return show;
    }
    public int getType() {
        return type;
    }
}
