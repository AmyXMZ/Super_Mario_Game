package com.sxt;

import com.sxt.obj.Obstacle;
import com.sxt.util.BackGround;
import com.sxt.util.StaticValue;

import java.awt.image.BufferedImage;

public class Enemy implements Runnable {

    //储存当前坐标
    private int x,y;

    //储存敌人类型
    private int type;
    //判断敌人运动方向
    private boolean face_to = true;
    //用于显示敌人的当前图像
    private BufferedImage show;
    //定义一个背景对象
    private BackGround bg;
    //食人花运动极限范围
    private int max_up = 0;
    private int max_down = 0;
    //当前图片状态
    private int image_type = 0;
    private Thread thread = new Thread(this);
    //蘑菇敌人constructor
    public Enemy (int x, int y, boolean face_to, int type, BackGround bg) {
        this.x = x;
        this.y = y;
        this.face_to = face_to;
        this.type = type;
        this.bg = bg;
        show = StaticValue.mogu.get(0);
        thread.start();
    }
    //食人花敌人constructor
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
            //判断是否是蘑菇敌人
            if (type == 1) { //1是蘑菇敌人
                if (face_to) { //true: move left
                    this.x -= 2;
                }else {
                    this.x += 2;
                }
                image_type = image_type == 1 ? 0 : 1;
                show = StaticValue.mogu.get(image_type);
            }
            //定义两个布尔变量
            boolean canLeft = true;
            boolean canRight = true;
            for (int i = 0; i < bg.getObstacleList().size(); i++) {
                Obstacle ob1 = bg.getObstacleList().get(i);
                //判断是否可以向右走
                if (ob1.getX() == this.x + 36 && (ob1.getY() + 65 > this.y && ob1.getY() - 35 < this.y)) {//if yes: 右侧有障碍物
                    canRight = false;
                }
                //判断是否可以向左走
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
            //判断是否是食人花敌人
            if (type == 2) {
                if (face_to){
                    this.y -= 2;
                }else{
                    this.y += 2;
                }
                image_type = image_type == 1 ? 0 : 1;
                //食人花是否移动到上极限位置
                if (face_to && (this.y == max_up)){
                    face_to = false; //将其向下移动
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
    //死亡方法：蘑菇可杀死，食人花不能
    public void death () {
        show = StaticValue.mogu.get(2);//蘑菇死亡时图片
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
