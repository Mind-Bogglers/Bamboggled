package com.bamboggled.model.word;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.TreeSet;

/**
 * The Dictionary will contain words that are acceptable for Boggle.
 */
public class BoggleDictionary {

    /**
     * Set of legal words for Boggle
     */
    private TreeSet<String> legalWords;

    /**
     * Class constructor
     * @param filename the file containing a list of legal words.
     */
    public BoggleDictionary(String filename) {
        String line = "";
        int wordcount = 0;
        this.legalWords = new TreeSet<String>();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null)
            {
                if (line.strip().length() > 0) {
                    legalWords.add(line.strip());
                    wordcount++;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println("Initialized " + wordcount + " words in the Dictionary.");;
    }

    /**
     * Checks to see if a provided word is in the dictionary.
     * @param word The word to check
     * @return A boolean indicating if the word has been found
     */
    public boolean containsWord(String word) {
        return this.legalWords.contains(word);
    }

    /**
     * Checks to see if a provided string is a prefix of any word in the dictionary.
     * @param str The string to check
     * @return A boolean indicating if the string has been found as a prefix
     */
    public boolean isPrefix(String str) {
        if (Objects.equals(str, "")) {
            return true;
        }
        char lastCharacterIncremented = (char) (str.charAt(str.length() - 1) + 1);
        String upperBoundForSubSet = str.substring(0, str.length() - 1) + lastCharacterIncremented;
        return this.legalWords.subSet(str, upperBoundForSubSet).size() > 0;
    }

}
