package com.sxt.obj;

import com.sxt.Enemy;
import com.sxt.util.BackGround;
import com.sxt.util.StaticValue;

import java.awt.image.BufferedImage;

public class Mario implements Runnable {
    //用于表示坐标
    private int x;
    private int y;
    //用于表示当前状态
    private String status;
    //用于显示当前状态对应的图像
    private BufferedImage show = null;
    //用于表示马里奥移动速度
    private int xSpeed;
    //用于表示马里奥跳跃速度
    private int ySpeed;
    //定义一个索引,用于取得马里奥的运动图像
    private int index;
    //表示马里奥面朝方向
    private boolean face_to = true;
    //表示马里奥上升的时间
    private int upTime = 0;
    //定义一个BackGround对象，用于获取障碍物信息
    private BackGround backGround = new BackGround();

    //判断是否到了城堡门口
    private boolean isOK;

    //判断马里奥是否死亡
    private boolean isDeath = false;
    //表示分数
    private int score = 0; //消灭敌人+2，消灭方块+1
    //用来实现马里奥动作
    private Thread thread = null; ////Once the thread is started, it runs independently of the main program flow.

    @Override
    public void run() {
        while(true){
            //判断马里奥是否处于障碍物上
            boolean onObstacle = false;
            //判断是否可以向右走
            boolean canRight = true;
            //判断是否可以往左走
            boolean canLeft = true;
            //判断马里奥是否到达旗杆位置
            if (backGround.isFlag() && this.x >= 500){//是否到达第三关旗杆位置
                this.backGround.setReach(true);
                //判断旗子是否下落完成
                if (this.backGround.isBase()) {
                    status = "move--right";
                    if (x < 690) {
                        x += 5;
                    }else {
                        isOK = true;
                    }
                }else{
                    if (y < 395) { //马里奥在空中
                        xSpeed = 0;
                        this.y += 5;
                        status = "jump--right";
                    }
                    if (y > 395) {//当马里奥落到地上
                        this.y = 395;
                        status = "stop--right";
                    }
                }
            }

            //遍历当前场景所有障碍物
            for (int i = 0; i < backGround.getObstacleList().size(); i++){
                Obstacle ob = backGround.getObstacleList().get(i);
                //判断马里奥是否位于障碍物上 (yes)
                if(ob.getY() == this.y + 25 && (ob.getX() > this.x - 30 && ob.getX() < this.x + 25)){
                    onObstacle = true;
                }
                //判断是否跳起来顶到砖块
                if ((ob.getY() >= this.y - 30 && ob.getY() <= this.y - 20) && (ob.getX() >= this.x - 30 && ob.getX() < this.x + 25)) {
                    if (ob.getType() == 0) { //可破坏砖块
                        backGround.getObstacleList().remove(ob);
                        score += 1;
                    }
                    upTime = 0; //顶到砖块后立刻下落
                }
                //判断是否可以往右走
                if (ob.getX() == this.x + 25 && (ob.getY()) > this.y - 30 && ob.getY() < this.y + 25) {//if true,说明右侧有障碍物
                    canRight = false;
                }
                //判断是否可以往左走
                if (ob.getX() == this.x - 30 && (ob.getY() > this.y - 30 && ob.getY() < this.y + 25)) { //if true,说明左侧有障碍物
                    canLeft = false;
                }
            }
            //判断马里奥是否碰到敌人死亡或者踩死蘑菇敌人
            for (int i = 0; i < backGround.getEnemyList().size(); i++){
                Enemy e = backGround.getEnemyList().get(i); //用e存储当前敌人
                if (e.getY() == this.y + 20 && (e.getX() - 25 <= this.x && e.getX() + 35 >= this.x)){ //判断是否在敌人上方
                    if (e.getType() == 1){//蘑菇敌人
                        e.death();
                        score += 2;
                        upTime = 3;
                        ySpeed = -10;
                    }else if (e.getType() == 2){//食人花敌人
                        //马里奥死亡
                        death();
                    }
                }
                if ((e.getX() + 35 > this.x && e.getX() - 25 < this.x) && (e.getY() + 35 > this.y && e.getY() - 20 < this.y)){
                    //马里奥死亡
                    death();
                }
            }

            //进行马里奥跳跃动作
            if (onObstacle && upTime == 0){ //只要处于空中uptime就不等于0
                if (status.indexOf("left") != -1){ //contains "left"
                    if (xSpeed != 0){
                        status = "move--left";
                    }else{
                        status = "stop--left";
                    }
                } else{
                    if (xSpeed != 0){
                        status = "move--right";
                    }else {
                        status = "stop--right";
                    }
                }
            } else{ //位于空中或障碍物上
                if (upTime != 0){//当uptime=0，上升到最高点
                    upTime--;
                }else {
                    fall();
                }
                y += ySpeed;
            }
            if ((canLeft && xSpeed < 0) || (canRight && xSpeed > 0)){ //是否处于运动状态
                x += xSpeed;//通过坐标轴变化实现马里奥运动
                //判断是否到达了最左边
                if (x < 0){
                    x = 0;
                }
            }
            //判断马里奥是否移动状态
            if (status.contains("move")){
                index = index == 0 ? 1 : 0;
            }
            //判断是否向左移动 (两张图片,用index = 0 or 1 来实现)
            if ("move--left".equals(status)){
                show = StaticValue.run_L.get(index);
            }
            //判断是否向右移动(两张图片,用index = 0 or 1 来实现)
            if ("move--right".equals(status)){
                show = StaticValue.run_R.get(index);
            }
            //判断是否向左停止
            if ("stop--left".equals(status)){
                show = StaticValue.stand_L;
            }
            //判断是否向右停止
            if ("stop--right".equals(status)){
                show = StaticValue.stand_R;
            }
            //判断是否向左跳
            if ("jump--left".equals(status)){
                show = StaticValue.jump_L;
            }
            //判断是否向右跳跃
            if ("jump--right".equals(status)){
                show = StaticValue.jump_R;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Mario() {//无参数constructor
    }

    public Mario(int x, int y) {//有参数constructor,每关开始by default Mario向右
        this.x = x;
        this.y = y;
        show = StaticValue.stand_R;
        this.status = "stand--right";
        thread = new Thread(this);
        thread.start();
    }

    //马里奥向左移动
    public void leftMove() {
        //马里奥速度改变
        xSpeed = -5;
        //判断马里奥是否碰到旗子
        if (backGround.isReach()){
            xSpeed = 0;
        }
        //判断是否位于空中(处于空中无法移动)
        if(status.indexOf("jump") != -1){
            status = "jump--left";
        }else{
            status = "move--left";
        }
    }
    //马里奥向右移动
    public void rightMove() {
        //马里奥速度改变
        xSpeed = 5;
        //判断马里奥是否碰到旗子
        if (backGround.isReach()){
            xSpeed = 0;
        }
        //判断是否位于空中
        if(status.indexOf("jump") != -1){
            status = "jump--right";
        }else{
            status = "move--right";
        }
    }
    //马里奥向左停止
    public void leftStop() {
        //马里奥速度改变 (0)
        xSpeed = 0;
        //判断是否位于空中
        if(status.indexOf("jump") != -1){//含有的话处于空中
            status = "jump--left";
        }else{
            status = "stop--left";
        }
    }
    //马里奥向右停止
    public void rightStop() {
        //马里奥速度改变 (0)
        xSpeed = 0;
        //判断是否位于空中
        if(status.indexOf("jump") != -1){
            status = "jump--right";
        }else{
            status = "stop--right";
        }
    }
    //马里奥跳跃方法
    public void jump(){
        if (status.indexOf("jump") == -1){
            if (status.indexOf("left") != -1){
                status = "jump--left";
            }else {
                status = "jump--right";
            }
            ySpeed = -10;
            upTime = 10;
        }
        //判断马里奥是否碰到旗子
        if (backGround.isReach()){
            ySpeed = 0;
        }
    }
    //马里奥下落的方法
    public void fall(){
        if (status.indexOf("left") != -1){
            status = "jump-left";
        }else{
            status = "jump--right";
        }
        ySpeed = 10;
    }
    //马里奥死亡的方法
    public void death(){
        isDeath = true;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getStatus() {
        return status;
    }

    public BufferedImage getShow() {
        return show;
    }

    public BackGround getBackGround() {
        return backGround;
    }

    public Thread getThread() {
        return thread;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isFace_to() {
        return face_to;
    }

    public void setFace_to(boolean face_to) {
        this.face_to = face_to;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setShow(BufferedImage show) {
        this.show = show;
    }

    public void setBackGround(BackGround backGround) {
        this.backGround = backGround;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
    public boolean isOK() {
        return isOK;
    }
    public boolean isDeath() {
        return isDeath;
    }
    public int getScore() {
        return score;
    }


}

