package com.cm6121.countWord.app.utilityFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class Document {

    private final String docName;

    private final File docFile;

    private String docCreation;

    private String docTitle;

    private String docText;

    private Map<String, Integer> sortedMap;

    //getters

    public Map<String, Integer> getSortedMap() {
        return sortedMap;
    }


    public String getDocName() {
        return docName;
    }

    public String getDocCreation() {
        return docCreation;
    }

    public String getDocTitle() {
        return docTitle;
    }


    public Document(final File fileI) {
        this.docFile = fileI;
        this.docName = fileI.getName();
    }

    public void parse() {
        FileReader fileReader = new FileReader();

        String[] list = fileReader.readCSVMethod1(docFile).get(0);
        this.docTitle = list[0].trim();
        this.docCreation = list[2].trim();
        this.docText = list[1];
    }

    public void cleanText() {
        docText = docText.toLowerCase(); //make every character lowercase
        docText = docText.replaceAll("\\p{P}", " "); //remove all punctuation
        docText = docText.replaceAll("\\s+", " ").trim(); // remove instances of double spaces
        docTitle = docTitle.replaceAll("\\s+", " ").trim(); // remove instances of double spaces in the title

    }

    public void countWords() {
        HashMap<String, Integer> wordCount = new HashMap<>(); //clear word count hash map
        try { //try to clean document text
            this.cleanText();
        } catch (Exception e) {
            System.out.println("Could not clean document text");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        String[] splitText = this.docText.split(" "); //split text into array along spaced characters
        for (String word : splitText) {  // for each word in the list
            if (word.length() > 1) { //ensure that only words longer than 1 character are counted
                if (wordCount.containsKey(word)) { //if word already occurs in hashmap
                    wordCount.put(word, wordCount.get(word) + 1);  //increase count
                } else { //if word hasn't yet occurred
                    wordCount.put(word, 1); //add word to hashmap with count 1
                }
            }
        }

        // Following code referenced
        // David Landup (Feb 2021) StackAbuse Available At: https://stackabuse.com/how-to-sort-a-hashmap-by-value-in-java/ (Accessed May 2022)
        // Sorting a hashmap into a LinkedHashMap
        sortedMap = wordCount.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> e.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> {
                            throw new AssertionError();
                        },
                        LinkedHashMap::new
                ));
        // reference end

    }
}
