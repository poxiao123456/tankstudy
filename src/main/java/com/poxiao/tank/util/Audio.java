package com.poxiao.tank.util;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author qinqi
 * @date 2020/11/19
 */
public class Audio {

    private AudioInputStream audioInputStream = null;
    private AudioFormat audioFormat = null;
    private DataLine.Info dataLine_info = null;
    private SourceDataLine sourceDataLine = null;



    public Audio() {
    }

    public Audio(String fileName) {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(Audio.class.getClassLoader().getResourceAsStream(fileName));
            audioFormat = audioInputStream.getFormat();
            dataLine_info = new DataLine.Info(SourceDataLine.class, audioFormat);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLine_info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        try {
            sourceDataLine.open(audioFormat, 1024*5);
            sourceDataLine.start();

            byte[] b = new byte[1024*5];
            int len = 0;
            while ((len = audioInputStream.read(b)) > 0) {
                sourceDataLine.write(b, 0, len);
            }
            sourceDataLine.drain();
            sourceDataLine.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loop() {
        try {

            while (true) {
                sourceDataLine.open(audioFormat, 1024 * 1024 * 15);
                sourceDataLine.start();
                int len = 0;
                byte[] b = new byte[1024 * 1024 * 15];
                while ((len = audioInputStream.read(b)) > 0) {
                    sourceDataLine.write(b, 0, len);
                }
                sourceDataLine.drain();
                sourceDataLine.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void close() {
        try {
            audioInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void play2() {
        try {
            InputStream inputStream = Audio.class.getClassLoader().getResourceAsStream("audio/war1.wav");
            //得到音频文件的输入流
            audioInputStream = AudioSystem.getAudioInputStream(inputStream);
            //得到音频的格式化实体
            AudioFormat format = audioInputStream.getFormat();
            SourceDataLine sdl = null;
            //通过格式化后的数据信息
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            //通过信息得到数据
            sdl = (SourceDataLine) AudioSystem.getLine(info);
            //进行格式化数据
            sdl.open(format);
            //开启数据
            sdl.start();
            int len = 0;
            byte[] abData = new byte[512];
//            while (nBytesRead != -1) {
//                nBytesRead = audioInputStream.read(abData, 0, abData.length);
//                if (nBytesRead >= 0) {
//
//                    sdl.write(abData, 0, nBytesRead);
//                }
//            }
            while((len = audioInputStream.read(abData)) != -1) {
                sdl.write(abData, 0, len);
            }
            //关闭SourceDataLine，相当于flush
            sdl.drain();
            sdl.close();
        }catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Audio a = new Audio("audio/war1.wav");
        a.loop();

//        Audio a = new Audio();
//        a.play2();
    }
}
