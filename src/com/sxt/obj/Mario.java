package com.sxt.obj;

import com.sxt.Enemy;
import com.sxt.util.BackGround;
import com.sxt.util.StaticValue;

import java.awt.image.BufferedImage;
/*
* This class, Mario, enables the player to control the motion of Mario within the game frame. This class contains the methods to enable
* Mario's movements, including runnning (left or right), jumping, destroying bricks or enemies, and its death
* */

public class Mario implements Runnable {
    //the x-axis location of Mario
    private int x;
    //the y-axis location of Mario
    private int y;
    //represents the current status of Mario
    private String status;
    //represents the image of Mario that matches with its current status
    private BufferedImage show = null;
    //how fast Mario can run (speed in the horizontal direction)
    private int xSpeed;
    //how fast Mario can jump (the speed in its vertical direction)
    private int ySpeed;
    //Mario's running images
    private int index;
    //the direction that Mario faces to
    private boolean face_to = true;
    //the time it takes Mario to rise (jump upward)
    private int upTime = 0;
    //new an object in the class BackGround, to obtain information about obstacles
    private BackGround backGround = new BackGround();

    //to see if Mario has arrived at the castle
    private boolean isOK;

    //to see if Mario dies
    private boolean isDeath = false;
    //represents current score (defeat an enemy + 2 points, destroy a brick + 1 point)
    private int score = 0;
    //enable Mario to move on the frame
    private Thread thread = null; ////Once the thread is started, it runs independently of the main program flow.

    @Override
    public void run() {
        while(true){
            //to see if Mario is on an obstacle
            boolean onObstacle = false;
            //to see if Mario can move to the right
            boolean canRight = true;
            //to see if Mario can move to the left
            boolean canLeft = true;
            //to see if Mario has arrived at the flagpole in level 3 (flagpole x-axis = 500)
            if (backGround.isFlag() && this.x >= 500){
                this.backGround.setReach(true);
                //to see if the flag has been dragged to the ground
                if (this.backGround.isBase()) {
                    status = "move--right";
                    if (x < 690) {
                        x += 5;
                    }else {
                        isOK = true;
                    }
                }else{
                    if (y < 395) { //Mario is in the air
                        xSpeed = 0;
                        this.y += 5;
                        status = "jump--right";
                    }
                    if (y > 395) {//when Mario has fallen to the ground
                        this.y = 395;
                        status = "stop--right";
                    }
                }
            }

            //iterate through all the obstacles in the current background
            for (int i = 0; i < backGround.getObstacleList().size(); i++){
                Obstacle ob = backGround.getObstacleList().get(i);
                //if Mario is on an obstacle
                if(ob.getY() == this.y + 25 && (ob.getX() > this.x - 30 && ob.getX() < this.x + 25)){
                    onObstacle = true;
                }
                //to see if Mario can jump and touch a brick
                if ((ob.getY() >= this.y - 30 && ob.getY() <= this.y - 20) && (ob.getX() >= this.x - 30 && ob.getX() < this.x + 25)) {
                    if (ob.getType() == 0) { //if the brick is destroyable, score += 1
                        backGround.getObstacleList().remove(ob);
                        score += 1;
                    }
                    upTime = 0; //when Mario hits a brick, it immediately falls to the ground
                }
                //to see if Mario can move to the right
                if (ob.getX() == this.x + 25 && (ob.getY()) > this.y - 30 && ob.getY() < this.y + 25) {//if true,it cannot move to the right because of the presence of obstacles
                    canRight = false;
                }
                //to see if Mario can move to the left
                if (ob.getX() == this.x - 30 && (ob.getY() > this.y - 30 && ob.getY() < this.y + 25)) { //if true,it cannot move to the left because of the presence of obstacles
                    canLeft = false;
                }
            }
            //to see if Mario destroys an enemy or gets destroyed by an enemy and dies
            for (int i = 0; i < backGround.getEnemyList().size(); i++){
                Enemy e = backGround.getEnemyList().get(i); //e refers to the current enemy
                if (e.getY() == this.y + 20 && (e.getX() - 25 <= this.x && e.getX() + 35 >= this.x)){ //to see if Mario is above enemy
                    if (e.getType() == 1){//type 1 == mushroom enemy
                        e.death();
                        score += 2;
                        upTime = 3;
                        ySpeed = -10;
                    }else if (e.getType() == 2){//type 2 == Piranha flower
                        //Mario dies if touches a Piranha flower
                        death();
                    }
                }
                if ((e.getX() + 35 > this.x && e.getX() - 25 < this.x) && (e.getY() + 35 > this.y && e.getY() - 20 < this.y)){
                    //Mario dies
                    death();
                }
            }

            //Mario jumps
            //Mario is on an obstacle
            if (onObstacle && upTime == 0){ //as long as Mario is in the air, the upTime != 0
                if (status.indexOf("left") != -1){
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
            } else{ //Mario is in the air
                if (upTime != 0){
                    upTime--;
                }else {
                    fall();
                }
                y += ySpeed;
            }
            if ((canLeft && xSpeed < 0) || (canRight && xSpeed > 0)){ //to see if Mario is in motion
                x += xSpeed;//enable movement of Mario by changing its x-axis
                //Mario cannot go beyond the frame, it will stop at x=0
                if (x < 0){
                    x = 0;
                }
            }
            //to see if Mario's current status is "move"
            if (status.contains("move")){
                index = index == 0 ? 1 : 0;
            }
            //if Mario moves to the left, its running images will be shown by two images (index = 0 or 1)
            if ("move--left".equals(status)){
                show = StaticValue.run_L.get(index);
            }
            //if Mario moves to the right, its running images will be shown by two images (index = 0 or 1)
            if ("move--right".equals(status)){
                show = StaticValue.run_R.get(index);
            }
            //if Mario stops while facing left
            if ("stop--left".equals(status)){
                show = StaticValue.stand_L;
            }
            //if Mario stops while facing right
            if ("stop--right".equals(status)){
                show = StaticValue.stand_R;
            }
            //show image of Mario jumping to its left
            if ("jump--left".equals(status)){
                show = StaticValue.jump_L;
            }
            //show image of Mario jumping to its right
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

    public Mario() {//default constructor for Mario class
    }

    public Mario(int x, int y) {//constructor with 2 parameters, and by default Mario faces to its right at the start of each level
        this.x = x;
        this.y = y;
        show = StaticValue.stand_R;
        this.status = "stand--right";
        thread = new Thread(this);
        thread.start();
    }

    //Mario runs to the left
    public void leftMove() {

        xSpeed = -5;
        //Mario will stop if it reaches the flagpole
        if (backGround.isReach()){
            xSpeed = 0;
        }
        //to see if Mario is in the air, because in this status it cannot move to any direction until it returns back to the ground
        if(status.indexOf("jump") != -1){
            status = "jump--left";
        }else{
            status = "move--left";
        }
    }
    //Mario move to the right
    public void rightMove() {

        xSpeed = 5;
        //Mario stops if it reaches the flagpole
        if (backGround.isReach()){
            xSpeed = 0;
        }
        //similar as leftMove(), if Mario is in the air it cannot move to any direction until returns to the ground
        if(status.indexOf("jump") != -1){
            status = "jump--right";
        }else{
            status = "move--right";
        }
    }
    //Mario stops and faces left
    public void leftStop() {

        xSpeed = 0;
        //to see if it's in the air
        if(status.indexOf("jump") != -1){
            status = "jump--left";
        }else{
            status = "stop--left";
        }
    }
    //Mario stops and faces right
    public void rightStop() {

        xSpeed = 0;

        if(status.indexOf("jump") != -1){
            status = "jump--right";
        }else{
            status = "stop--right";
        }
    }
    //enabling Mario to jump (left or right)
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
        //stop jumping up if Mario reaches the flagpole
        if (backGround.isReach()){
            ySpeed = 0;
        }
    }
    //Mario falls to the ground after jumping
    public void fall(){
        if (status.indexOf("left") != -1){
            status = "jump-left";
        }else{
            status = "jump--right";
        }
        ySpeed = 10;
    }
    //to see if Mario dies
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

