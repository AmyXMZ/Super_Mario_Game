package com.sxt.util;

import com.sxt.Enemy;
import com.sxt.obj.Obstacle;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
/**
 * This class prepares the background for each game level. For each level, draws the image of Mario, obstacles, enemies, and different
 * background images and background objects.
 */

public class BackGround {
    //current background image
    private BufferedImage bgImage = null;
    //indicates the current level (each level has different background as well)
    private int sort;
    //to see if Mario has arrived at the last level
    private boolean flag;
    //creating a list to store all images of obstacles
    private List<Obstacle> obstacleList = new ArrayList<>();
    //flagpole image
    private BufferedImage gan = null;
    //castle image
    private BufferedImage tower = null;

    //to see if Mario has arrived at the flagpole
    private boolean isReach = false;

    //to see if the flag has been dragged down to the floor
    private boolean isBase = false;

    //creating a list to store all images of enemies
    private List<Enemy> enemyList = new ArrayList<>();
    public BackGround() { //constructor with no parameters
    }

    public BackGround(int sort, boolean flag) { //constructor with parameters of int and boolean type
        this.sort = sort;
        this.flag = flag;
        if (flag) { //if flag equals to true, shows the last background (for level 3)
            bgImage = StaticValue.bg2;
        }else {
            bgImage = StaticValue.bg;
        }
        //to see if it's the first level
        if(sort == 1) {
            //draw the ground, the bricks on the top if type 2, the ones on the bottom is type 1
            for(int i = 0; i < 27; i++){ //27 bricks,each: 30*30
                obstacleList.add(new Obstacle(i*30,420,2,this));
            }
            for (int j = 0; j <= 120; j += 30){ //4 layers
                for (int i = 0; i < 27; i++) {
                    obstacleList.add(new Obstacle(i*30, 570-j,1,this));
                }
            }
            //draw different types of bricks-1
            for (int i = 120; i <= 150; i+= 30) {
                obstacleList.add(new Obstacle(i,300,7,this));
            }
            //draw different types of bricks-2
            for(int i = 300; i <= 570; i += 30){
                if(i == 360 || i == 390 || i == 480 || i == 510 || i == 540){
                    obstacleList.add(new Obstacle(i, 300,7,this));
                }else{
                    obstacleList.add(new Obstacle(i,300,0,this));
                }
            }
            //draw different types of bricks-3
            for (int i = 420; i <= 450; i += 30) {
                obstacleList.add(new Obstacle(i, 240,7,this));
            }
            //draw the pipe
            for (int i = 360; i <= 600; i += 25) {
                if (i == 360){ //fist layer of pipe
                    obstacleList.add(new Obstacle(620,i,3,this));
                    obstacleList.add(new Obstacle(645,i,4,this));
                }else {
                    obstacleList.add(new Obstacle(620,i,5,this));
                    obstacleList.add(new Obstacle(645,i,6,this));
                }
            }
            //draw the mushroom enemy for level 1
            enemyList.add(new Enemy(580,385,true,1,this));
            //draw Piranha flower for level 1
            enemyList.add(new Enemy(635,420,true,2,328,418,this));
        }
        //if Mario enters level 2
        if (sort == 2) {
            //draw the ground, the bricks on the top if type 2, the ones on the bottom is type 1 (same as level 1)
            for(int i = 0; i < 27; i++){ //27bricks,with each 30*30
                obstacleList.add(new Obstacle(i*30,420,2,this));
            }
            for (int j = 0; j <= 120; j += 30){ //4 layers
                for (int i = 0; i < 27; i++) {
                    obstacleList.add(new Obstacle(i*30, 570-j,1,this));
                }
            }
            //draw the first layer of pipe
            for (int i = 360; i <= 600; i += 25) {
                if (i == 360) { //to see if there are two pipe bricks on top
                    obstacleList.add(new Obstacle(60,i,3,this));
                    obstacleList.add(new Obstacle(85,i,4,this));
                } else {
                    obstacleList.add(new Obstacle(60,i,5,this));
                    obstacleList.add(new Obstacle(85,i,6,this));
                }
            }
            //draw the second layer of pipe
            for (int i = 330; i <= 600; i += 25) {
                if (i == 330) {
                    obstacleList.add(new Obstacle(620,i,3,this));
                    obstacleList.add(new Obstacle(645,i,4,this));
                }else {
                    obstacleList.add(new Obstacle(620,i,5,this));
                    obstacleList.add(new Obstacle(645,i,6,this));
                }
            }
            //draw different types of bricks-4
            obstacleList.add(new Obstacle(300,330,0,this));
            //draw different types of bricks-5
            for(int i =270; i <= 330; i += 30) {
                if (i == 270 || i == 330) {
                    obstacleList.add(new Obstacle(i,360,0,this));
                }else{
                    obstacleList.add(new Obstacle(i,360,7,this));
                }
            }
            //draw different types of bricks-6
            for(int i = 240; i <= 360; i += 30) {
                if (i==240 || i == 360){
                    obstacleList.add(new Obstacle(i,390,0,this));
                }else {
                    obstacleList.add(new Obstacle(i,390,7,this));
                }
            }
            //draw different types of bricks-7
            obstacleList.add(new Obstacle(240,300,0,this));

            for (int i =360; i <= 540; i += 60) {
                obstacleList.add(new Obstacle(i,270,7,this));
            }
            //draw the first Piranha flower for level 2
            enemyList.add(new Enemy(75,420,true,2,328,418,this));
            //draw the second Piranha flower for level 2
            enemyList.add(new Enemy(635,420,true,2,298,388,this));
            //draw the first mushroom for level 2
            enemyList.add(new Enemy(200,385,true,1,this));
            //draw the second mushroom for level 2
            enemyList.add(new Enemy(500,385,true,1,this));
        }
        //to see if Mario enters level 3
        if(sort == 3){
            //draw the ground, the bricks on the top if type 2, the ones on the bottom is type 1 (same as level 1)
            for(int i = 0; i < 27; i++){ ///27 bricks (same as above)
                obstacleList.add(new Obstacle(i*30,420,2,this));
            }
            for (int j = 0; j <= 120; j += 30){ //4 layers
                for (int i = 0; i < 27; i++) {
                    obstacleList.add(new Obstacle(i*30, 570-j,1,this));
                }
            }
            //draw obstacles
            int temp = 290;
            for (int i = 390; i >= 270; i -= 30){ //layers
                for (int j = temp; j <= 410; j += 30){ //number of bricks
                    obstacleList.add(new Obstacle(j,i,7,this));
                }
                temp += 30;
            }
            //draw obstacles
            temp = 60;
            for (int i = 390; i >= 360; i -=30){
                for (int j = temp; j <= 90; j += 30){
                    obstacleList.add(new Obstacle(j,i,7,this));
                }
                temp += 30;
            }
            //draw flagpole
            gan = StaticValue.gan;

            //draw castle
            tower = StaticValue.tower;

            //to add the flag onto the pole
            obstacleList.add(new Obstacle(515,220,8,this));
            //draw the mushroom enemies for level 3
            enemyList.add(new Enemy(150,385,true,1,this));
        }
    }

    public BufferedImage getBgImage() {
        return bgImage;
    }

    public int getSort() {
        return sort;
    }

    public boolean isFlag() {
        return flag;
    }

    public List<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public BufferedImage getGan() {
        return gan;
    }

    public BufferedImage getTower() {
        return tower;
    }
    public boolean isReach() {
        return isReach;
    }

    public void setReach(boolean reach) {
        isReach = reach;
    }
    public boolean isBase() {
        return isBase;
    }

    public void setBase(boolean base) {
        isBase = base;
    }
    public List<Enemy> getEnemyList() {
        return enemyList;
    }
}
