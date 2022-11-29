package com.bamboggled.model.dice;

public class BoardLetterGeneratorSmall extends BoardLetterGenerator {

    /**
     * Constructor for the BoardLetterGeneratorSmall class. The dice used to generate the letters are already set.
     */
    public BoardLetterGeneratorSmall() {
        super(new Die[]{
                new Die("AAEEGN"),
                new Die("ABBJOO"),
                new Die("ACHOPS"),
                new Die("AFFKPS"),
                new Die("AOOTTW"),
                new Die("CIMOTU"),
                new Die("DEILRX"),
                new Die("DELRVY"),
                new Die("DISTTY"),
                new Die("EEGHNW"),
                new Die("EEINSU"),
                new Die("EHRTVW"),
                new Die("EIOSST"),
                new Die("ELRTTY"),
                new Die("HIMNQU"),
                new Die("HLNNRZ")
        });
    }


}
