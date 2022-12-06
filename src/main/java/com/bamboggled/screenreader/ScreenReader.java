package com.bamboggled.screenreader;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.application.Platform;

/**
 * Screen reader class that utilizes Java freeTTS API to read out text
 * */

public class ScreenReader {

    /**
     * Refers to Voice object that is used for speaking
     * */
    public static Voice voice;

    /**
     * Initializes ScreenReader object
     * */
    public ScreenReader() {
        // initializing what voice to use
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        // initializing properties of voice
        voice = VoiceManager.getInstance().getVoice("kevin16");
        voice.allocate();
        voice.setRate(100);
        voice.setPitch(150);
        voice.setVolume(3);
    }

    /**
     * Method used to speak text.  Platform.runLater() is used so that voice loads after GUI elements are loaded
     * @param text the text that will be spoken
     * */
    public void speak(String text) {
        Runnable speaker = new Runnable() {
                public void run() {
                    voice.speak(text);
                }
            };
        Platform.runLater(speaker);
    }

}
