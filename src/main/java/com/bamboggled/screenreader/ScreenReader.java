package com.bamboggled.screenreader;

import com.bamboggled.model.model.BoggleModel;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.application.Platform;

public class ScreenReader {

    public static Voice voice;

    private final BoggleModel model;


    public ScreenReader() {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        this.model = BoggleModel.getInstance();
        voice = VoiceManager.getInstance().getVoice("kevin16");
        voice.allocate();
        voice.setRate(100);
        voice.setPitch(150);
        voice.setVolume(10);
    }

    public void speak(String text) {
        if (this.model.visImpaired) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    ScreenReader.voice.speak(text);;
                }
            });
        }
    }

}
