package com.sxt.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * This class, StaticValue, reads the images of all the objects including backgrounds, enemies, obstacles, and Mario in all
 * kinds of different actions. And then all these images are initialized
 */

public class StaticValue {

    //background image
    public static BufferedImage bg = null;
    public static BufferedImage bg2 = null;
    //Mario jumps left
    public static BufferedImage jump_L = null;
    //Mario jumps right
    public static BufferedImage jump_R = null;
    //Mario to_the_left image
    public static BufferedImage stand_L = null;
    //Mario to_the_right image
    public static BufferedImage stand_R = null;
    //the image of castle
    public static BufferedImage tower = null;
    //flagpole
    public static BufferedImage gan = null;
    //obstacle (bricks)
    public static List<BufferedImage> obstacle = new ArrayList<>();
    //Mario runs to the left
    public static List<BufferedImage> run_L = new ArrayList<>();
    //runs to the right
    public static List<BufferedImage> run_R = new ArrayList<>();
    //mushroom images
    public static List<BufferedImage> mogu = new ArrayList<>();
    //Piranha flower images
    public static List<BufferedImage> flower = new ArrayList<>();
    //the dic path where all the images are stored
    public static String path = System.getProperty("user.dir") + "/src/images/";
    //to initialize images
    public static void init() {
        try {
            //load single image
            //load background image
            bg = ImageIO.read(new File(path + "bg.png"));
            bg2 = ImageIO.read(new File(path + "bg2.png"));
            //load Mario to_the_left image
            stand_L = ImageIO.read(new File(path + "s_mario_stand_L.png"));
            //load Mario to_the_right image
            stand_R = ImageIO.read(new File(path + "s_mario_stand_R.png"));
            //load image of Mario jumps to the left
            jump_L = ImageIO.read(new File(path + "s_mario_jump1_L.png"));
            //load image of Mario jumps to the right
            jump_R = ImageIO.read(new File(path + "s_mario_jump1_R.png"));
            //load the image of castle
            tower = ImageIO.read(new File(path + "tower.png"));
            //load the image of flagpole
            gan = ImageIO.read(new File(path + "gan.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //load multiple images
        //load images of Mario running to the left
        for (int i = 1; i <= 2; i++) {
            try {
                run_L.add(ImageIO.read(new File(path + "s_mario_run" + i + "_L.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //load images of Mario running to the right
        for (int i = 1; i <= 2; i++) {
            try {
                run_R.add(ImageIO.read(new File(path + "s_mario_run" + i + "_R.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //load the images of obstacles (three different types of bricks)
        try {
            obstacle.add(ImageIO.read(new File(path + "brick.png")));//destroyable brick: type 0
            obstacle.add(ImageIO.read(new File(path + "soil_base.png"))); //lower ground brick: type 1
            obstacle.add(ImageIO.read(new File(path + "soil_up.png"))); //upper ground brick: type 2
        } catch (IOException e) {
            e.printStackTrace();
        }
        //load the image of pipe
        for (int i = 1; i <=4; i++){
            try {
                obstacle.add(ImageIO.read(new File(path + "pipe" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //load images of brick that cannot be destroyed, as well as the flagpole
        try {
            obstacle.add(ImageIO.read(new File(path + "brick2.png")));
            obstacle.add(ImageIO.read(new File(path + "flag.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //load the images of mushroom enemy
        for (int i = 1; i <= 3; i++) {
            try {
                mogu.add(ImageIO.read(new File(path + "fungus" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //load the images of Piranha flower
        for (int i = 1; i <= 2; i++) {
            try {
                flower.add(ImageIO.read(new File(path + "flower1." + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
