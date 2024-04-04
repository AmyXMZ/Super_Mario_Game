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

//创建一个游戏frame，然后把各种图片光标加载进来
public class MyFrame extends JFrame implements KeyListener, Runnable {

    //存储所有背景
    private List<BackGround> allBg = new ArrayList<>();
    //存储当前背景
    private BackGround nowBg = new BackGround(); //use constructor with no parameters
    //用于双缓存
    private Image offScreenImage = null;

    //马里奥对象
    private Mario mario = new Mario();
    //实现马里奥移动
    private Thread thread = new Thread(this);
    public MyFrame() {
        //设置窗口的大小为800 * 600
        this.setSize(800,600);
        //设置窗口居中显示
        this.setLocationRelativeTo(null);
        //设置窗口的可见性
        this.setVisible(true);
        //设置点击窗口上的关闭键,结束程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口大小不可变
        this.setResizable(false);
        //向窗口对象添加键盘监听器
        this.addKeyListener(this);
        //设置窗口名称
        this.setTitle("SuperMario");

        //创建完游戏frame之后，初始化各种小图片光标 （但还没有paint上去）
        StaticValue.init();
        //初始化马里奥
        mario = new Mario(10,385);
        //且创建全部场景
        for (int i = 1; i <= 3; i++) { //i带进sort
            allBg.add(new BackGround(i, i==3 ? true:false));//直接构建class BackGround的有参数constructor
        }
        //将第一个场景设置为当前背景
        nowBg = allBg.get(0);
        mario.setBackGround(nowBg);//!使得马里奥能够站在“障碍物”地面上，以及站在砖块这些
        //绘制图像
        repaint();//used to request that a component or container be redrawn
        thread.start();
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

        //绘制背景
        graphics.drawImage(nowBg.getBgImage(),0,0,this);
        //绘制敌人
        for (Enemy e : nowBg.getEnemyList()){//!先绘制敌人再绘制障碍物，因为水管要把食人花盖住
            graphics.drawImage(e.getShow(),e.getX(),e.getY(),this);
        }
        //绘制分数
        Color c = graphics.getColor();
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("BOLD",Font.BOLD,25));
        graphics.drawString("Current Scores: " + mario.getScore(),300,100);
        graphics.setColor(c);

        //绘制障碍物
        for (Obstacle ob : nowBg.getObstacleList()){
            graphics.drawImage(ob.getShow(),ob.getX(),ob.getY(),this);
        }

        //绘制城堡 (for 第三关)
        graphics.drawImage(nowBg.getTower(),620,270,this);

        //绘制旗杆 (for 第三关)
        graphics.drawImage(nowBg.getGan(),500,220,this);

        //绘制马里奥
        graphics.drawImage(mario.getShow(),mario.getX(),mario.getY(),this);

        //将绘制的背景图添加到窗口中
        g.drawImage(offScreenImage,0,0,this);
    }

    @Override
    public void run() { //runnable interface
        //马里奥进入下一关并且重复绘制出马里奥
        while (true){
            repaint();
            try {
                Thread.sleep(50);
                //判断马里奥是否进入下一关,if yes, 重制
                if (mario.getX() >= 775){ //窗口大小为800，马里奥25，775
                    nowBg = allBg.get(nowBg.getSort());//！
                    mario.setBackGround(nowBg);
                    mario.setX(10);
                    mario.setY(395);
                }
                //判断马里奥是否死亡
                if (mario.isDeath()){
                    JOptionPane.showMessageDialog(this, "Game Over");
                    System.exit(0);
                }
                //判断游戏是否结束
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
    public void keyPressed(KeyEvent e) { //当键盘按下时调用
        //实现向右移动
        if (e.getKeyCode() == 39){
            mario.rightMove();;
        }
        //向左移动
        if (e.getKeyCode() == 37){
            mario.leftMove();
        }
        //跳跃
        if (e.getKeyCode() == 38){
            mario.jump();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {//当松开键盘时调用
        //向左停止
        if (e.getKeyCode() == 37){
            mario.leftStop();
        }
        //向右停止
        if (e.getKeyCode() == 39){
            mario.rightStop();
        }

    }
}
