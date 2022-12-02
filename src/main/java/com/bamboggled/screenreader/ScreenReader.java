package com.bamboggled.screenreader;

import com.bamboggled.model.model.BoggleModel;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class ScreenReader {

    public static Voice voice;

    private BoggleModel model;


    public ScreenReader(BoggleModel model) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        this.model = model;
        voice = VoiceManager.getInstance().getVoice("kevin16");
        voice.allocate();
        voice.setRate(100);
        voice.setPitch(150);
        voice.setVolume(10);
    }

    public void speak() {

    }

}
