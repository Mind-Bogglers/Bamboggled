package com.bamboggled.screenreader;

import com.bamboggled.model.model.BoggleModel;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.application.Platform;

import java.util.ArrayList;

public class ScreenReader {

    public static Voice voice;

    public ScreenReader() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        voice = VoiceManager.getInstance().getVoice("kevin16");
        voice.allocate();
        voice.setRate(100);
        voice.setPitch(150);
        voice.setVolume(3);
    }

    public void speak(String text) {
        Thread.currentThread().interrupt();
        Runnable speaker = new Runnable() {
                public void run() {
                    voice.speak(text);
                }
            };
        Platform.runLater(speaker);
    }

}
