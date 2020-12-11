package org.csc133.a3.sound;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

import java.io.InputStream;

public class Sound {
    private Media media;

    public Sound(String filename) {
        try {
            InputStream stream = Display.getInstance().getResourceAsStream(getClass(), filename);
            media = MediaManager.createMedia(stream, "audio/wav");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Filename: " + filename);
        }
    }

    public void setVolume(int volume) {
        media.setVolume(volume);
    }

    public void play() {
        media.setTime(0);
        media.play();
    }
}
