package com.bamboggled.model.dice;

public class BoardLetterGeneratorBig extends BoardLetterGenerator{

    /**
     * Constructor for the BoardLetterGeneratorBig class. The dice used to generate the letters are already set.
     */
    public BoardLetterGeneratorBig() {
        super(new Die[]{
                new Die("AAAFRS"),
                new Die("AAEEEE"),
                new Die("AAFIRS"),
                new Die("ADENNN"),
                new Die("AEEEEM"),
                new Die("AEEGMU"),
                new Die("AEGMNN"),
                new Die("AFIRSY"),
                new Die("BJKQXZ"),
                new Die("CCNSTW"),
                new Die("CEIILT"),
                new Die("CEILPT"),
                new Die("CEIPST"),
                new Die("DDLNOR"),
                new Die("DDHNOT"),
                new Die("DHHLOR"),
                new Die("DHLNOR"),
                new Die("EIIITT"),
                new Die("EMOTTT"),
                new Die("ENSSSU"),
                new Die("FIPRSY"),
                new Die("GORRVW"),
                new Die("HIPRRY"),
                new Die("NOOTUW"),
                new Die("OOOTTU")
        });
    }
}
