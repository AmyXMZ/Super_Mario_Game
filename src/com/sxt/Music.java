package com.sxt;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
/**
 * The class Music adds background music to the game
 */

public class Music {
    public Music () {
        Player player;
        String str = System.getProperty("user.dir") + "/src/Music/music.wav";
        try {
            //load music
            BufferedInputStream name = new BufferedInputStream(new FileInputStream(str));
            player = new Player(name);
            player.play();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JavaLayerException e) {
            throw new RuntimeException(e);
        }


    }
}
