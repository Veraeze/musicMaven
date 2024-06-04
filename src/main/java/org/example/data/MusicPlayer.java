package org.example.data;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MusicPlayer {

    Long currentFrame;
    Clip clip;
    String status;
    AudioInputStream audioInputStream;
    static String filepath;

    public MusicPlayer() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
        clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void play() {
        //start the clip
        clip.start();

        status = "play";
    }

    public void pause() {
        if (status.equals("paused")) {
            System.out.println("audio is already paused");
            return;
        }
        this.currentFrame =
                this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    public void resumeAudio() throws UnsupportedAudioFileException,
            IOException, LineUnavailableException {
        if (status.equals("play")) {
            System.out.println("Audio is already " +
                    "being played");
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }

    public void restart() throws IOException, LineUnavailableException,
            UnsupportedAudioFileException {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }

    public void stop() {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }

    public void resetAudioStream() throws UnsupportedAudioFileException, IOException,
            LineUnavailableException {
        audioInputStream = AudioSystem.getAudioInputStream(
                new File(filepath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public static void main(String[] args) {
        try {
            filepath = "C:\\Users\\USER\\IdeaProjects\\music\\Music\\Burna_Boy_-_City_Boys.mp3";
            music();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }
    }

    public static void music() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        MusicPlayer musicPlayer = new MusicPlayer();

        musicPlayer.play();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. pause");
            System.out.println("2. resume");
            System.out.println("3. restart");
            System.out.println("4. stop");
            int c = sc.nextInt();
            musicPlayer.gotoChoice(c);
            if (c == 4)
                break;
        }
        sc.close();
    }

    public void gotoChoice(int c)
            throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        switch (c) {
            case 1:
                pause();
                break;
            case 2:
                resumeAudio();
                break;
            case 3:
                restart();
                break;
            case 4:
                stop();
                break;
        }
    }
}

//    public static void playMusic(String filepath){
//        InputStream music;
//        try {
//            music = new FileInputStream(new File(filepath));
//
//        } catch (FileNotFoundException e) {
//            JOptionPane.showMessageDialog(null,"Error");
//        }
//    }

