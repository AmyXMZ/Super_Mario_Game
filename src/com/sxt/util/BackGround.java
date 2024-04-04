package com.sxt.util;

import com.sxt.Enemy;
import com.sxt.obj.Obstacle;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class BackGround {
    //当前场景要显示的图像
    private BufferedImage bgImage = null;
    //记录当前是第几个场景(第几关）
    private int sort;
    //判断是否到达最后一个场景
    private boolean flag;
    //建立一个集合用于存放障碍物
    private List<Obstacle> obstacleList = new ArrayList<>();
    //用于存放旗杆
    private BufferedImage gan = null;
    //用于存放城堡
    private BufferedImage tower = null;

    //判断马里奥是否到达旗杆位置
    private boolean isReach = false;

    //判断旗子是否落地
    private boolean isBase = false;

    //存放所有敌人
    private List<Enemy> enemyList = new ArrayList<>();
    public BackGround() { //constructor with no parameters
    }

    public BackGround(int sort, boolean flag) { //constructor with parameters
        this.sort = sort;
        this.flag = flag;
        if (flag) { //meaning if flag equals to true, it's the last background
            bgImage = StaticValue.bg2;
        }else {
            bgImage = StaticValue.bg;
        }
        //判断是否是第一关
        if(sort == 1) {
            //绘制地面：上地面type=2，下地面type=1；
            for(int i = 0; i < 27; i++){ //27个地板块,每个30*30
                obstacleList.add(new Obstacle(i*30,420,2,this));
            }
            for (int j = 0; j <= 120; j += 30){ //一共四层
                for (int i = 0; i < 27; i++) {
                    obstacleList.add(new Obstacle(i*30, 570-j,1,this));
                }
            }
            //绘制砖块A
            for (int i = 120; i <= 150; i+= 30) {
                obstacleList.add(new Obstacle(i,300,7,this));
            }
            //绘制砖块B-F
            for(int i = 300; i <= 570; i += 30){
                if(i == 360 || i == 390 || i == 480 || i == 510 || i == 540){
                    obstacleList.add(new Obstacle(i, 300,7,this));
                }else{
                    obstacleList.add(new Obstacle(i,300,0,this));
                }
            }
            //绘制砖块G
            for (int i = 420; i <= 450; i += 30) {
                obstacleList.add(new Obstacle(i, 240,7,this));
            }
            //绘制水管
            for (int i = 360; i <= 600; i += 25) {
                if (i == 360){ //第一层水管
                    obstacleList.add(new Obstacle(620,i,3,this));
                    obstacleList.add(new Obstacle(645,i,4,this));
                }else {
                    obstacleList.add(new Obstacle(620,i,5,this));
                    obstacleList.add(new Obstacle(645,i,6,this));
                }
            }
            //绘制第一关蘑菇敌人
            enemyList.add(new Enemy(580,385,true,1,this));
            //绘制第一关食人花敌人
            enemyList.add(new Enemy(635,420,true,2,328,418,this));
        }
        //判断是否是第二关
        if (sort == 2) {
            //绘制地面：上地面type=2，下地面type=1 (第一二关地面一样）
            for(int i = 0; i < 27; i++){ //27个地板块,每个30*30
                obstacleList.add(new Obstacle(i*30,420,2,this));
            }
            for (int j = 0; j <= 120; j += 30){ //一共四层
                for (int i = 0; i < 27; i++) {
                    obstacleList.add(new Obstacle(i*30, 570-j,1,this));
                }
            }
            //绘制第一根水管
            for (int i = 360; i <= 600; i += 25) {
                if (i == 360) { //是否是上面两个水管方块
                    obstacleList.add(new Obstacle(60,i,3,this));
                    obstacleList.add(new Obstacle(85,i,4,this));
                } else {
                    obstacleList.add(new Obstacle(60,i,5,this));
                    obstacleList.add(new Obstacle(85,i,6,this));
                }
            }
            //绘制第二根水管
            for (int i = 330; i <= 600; i += 25) {
                if (i == 330) {
                    obstacleList.add(new Obstacle(620,i,3,this));
                    obstacleList.add(new Obstacle(645,i,4,this));
                }else {
                    obstacleList.add(new Obstacle(620,i,5,this));
                    obstacleList.add(new Obstacle(645,i,6,this));
                }
            }
            //绘制障碍物C
            obstacleList.add(new Obstacle(300,330,0,this));
            //障碍物BEG
            for(int i =270; i <= 330; i += 30) {
                if (i == 270 || i == 330) {
                    obstacleList.add(new Obstacle(i,360,0,this));
                }else{
                    obstacleList.add(new Obstacle(i,360,7,this));
                }
            }
            //绘制障碍物ADFHI
            for(int i = 240; i <= 360; i += 30) {
                if (i==240 || i == 360){
                    obstacleList.add(new Obstacle(i,390,0,this));
                }else {
                    obstacleList.add(new Obstacle(i,390,7,this));
                }
            }
            //其余障碍物
            obstacleList.add(new Obstacle(240,300,0,this));
            //空砖块
            for (int i =360; i <= 540; i += 60) {
                obstacleList.add(new Obstacle(i,270,7,this));
            }
            //绘制第二关第一个食人花
            enemyList.add(new Enemy(75,420,true,2,328,418,this));
            //绘制第二关第二个食人花
            enemyList.add(new Enemy(635,420,true,2,298,388,this));
            //绘制第三关第一个蘑菇敌人
            enemyList.add(new Enemy(200,385,true,1,this));
            //绘制第二关第二个蘑菇敌人
            enemyList.add(new Enemy(500,385,true,1,this));
        }
        //判断是否为第三关
        if(sort == 3){
            //绘制地面：上地面type=2，下地面type=1 (第一二关地面一样）
            for(int i = 0; i < 27; i++){ //27个地板块,每个30*30
                obstacleList.add(new Obstacle(i*30,420,2,this));
            }
            for (int j = 0; j <= 120; j += 30){ //一共四层
                for (int i = 0; i < 27; i++) {
                    obstacleList.add(new Obstacle(i*30, 570-j,1,this));
                }
            }
            //绘制砖块障碍物A-O
            int temp = 290;
            for (int i = 390; i >= 270; i -= 30){ //层数
                for (int j = temp; j <= 410; j += 30){ //个数
                    obstacleList.add(new Obstacle(j,i,7,this));
                }
                temp += 30;
            }
            //绘制砖块P-R
            temp = 60;
            for (int i = 390; i >= 360; i -=30){
                for (int j = temp; j <= 90; j += 30){
                    obstacleList.add(new Obstacle(j,i,7,this));
                }
                temp += 30;
            }
            //绘制旗杆
            gan = StaticValue.gan;

            //绘制城堡
            tower = StaticValue.tower;

            //添加旗子到旗杆上
            obstacleList.add(new Obstacle(515,220,8,this));
            //绘制第三关蘑菇敌人
            enemyList.add(new Enemy(150,385,true,1,this));
        }
    }
    //需要get函数因为objects都为private
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
