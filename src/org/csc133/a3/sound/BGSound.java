package org.csc133.a3.sound;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

import java.io.IOException;
import java.io.InputStream;

public class BGSound implements Runnable {
    private Media media;

    public BGSound(String filename) {
        try {
            InputStream stream = Display.getInstance().getResourceAsStream(getClass(), filename);
            media = MediaManager.createMedia(stream, "audio/wav", this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        media.pause();
    }

    public void play() {
        media.play();
    }

    public void setVolume(int volume) {
        media.setVolume(volume);
    }

    @Override
    public void run() {
        media.setTime(0);
        media.play();
    }
}
