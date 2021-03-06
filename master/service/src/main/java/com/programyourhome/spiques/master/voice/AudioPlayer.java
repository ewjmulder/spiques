package com.programyourhome.spiques.master.voice;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

@Component
public class AudioPlayer {

    public void playMp3(final InputStream inputStream) throws IOException {
        final InputStream audioInputStream = new BufferedInputStream(inputStream);
        try {
            // Play the mp3 using the javazoom library.
            new Player(audioInputStream).play();
        } catch (final JavaLayerException e) {
            throw new IOException("JavaLayerException while playing stream.", e);
        } finally {
            audioInputStream.close();
        }
    }

}