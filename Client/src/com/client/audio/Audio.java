/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.audio;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

/**
 *
 * @author ngao
 */
public class Audio {

    public byte[] getDataFileAudio(String path) throws FileNotFoundException, IOException {
//        File file = new File("/home/ngao/Downloads/audio_odyssey.wav");
        File file = new File(path);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

        int read;
        byte[] buff = new byte[1024];
        while ((read = in.read(buff)) > 0) {
            out.write(buff, 0, read);
        }
        out.flush();
        return out.toByteArray();
    }

    public void playAudio(byte[] audioBytes) {
        AudioData audiodata = new AudioData(audioBytes);
        // Create an AudioDataStream to play back
        AudioDataStream audioStream = new AudioDataStream(audiodata);
        // Play the sound
        AudioPlayer.player.start(audioStream);
    }
    
//    public static void main(String[] args) {
//        Audio audio = new Audio();
//        try {
//            audio.playAudio(audio.getDataFileAudio());
//        } catch (IOException ex) {
//            Logger.getLogger(Audio.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception e) {  
//            e.printStackTrace();
//        }
//    }

}
