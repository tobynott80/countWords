package com.cm6121.countWord.app.utilityFile;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class DocumentController {

    private HashMap<String, Integer> unsortedCorpusCount = new HashMap<>();
    private Map<String, Integer> sortedCorpusCount;

    private ArrayList<Document> DocumentsList = new ArrayList<Document>();

    //Constructor
    public DocumentController() {
        this.DocumentsList = new ArrayList<Document>();
    }

    //getters
    public ArrayList<Document> getDocumentsList() {
        return DocumentsList;
    }



    public File pathToFileObj(final String path){
        File nFile = new File(path);
        //Path pathI = Path.of(path);
        //File newFile = pathI.toFile();
        return nFile;
    }

    public void loadFolder(final File folder) {
        for (File fileI : folder.listFiles()){
            if (fileI.getName().contains(".csv")){
                DocumentsList.add(new Document(fileI));
            }
            else{
                System.out.println(" is not a csv file");
            }
        }
    }

    public void parseFolder() {
        System.out.println("The number of CSV documents in the folder is " + DocumentsList.size());
        for (Document doc : DocumentsList){
            doc.parse();
            System.out.println("The file name is " + doc.getDocName() + ", the title is " + doc.getDocTitle() + ", the creation date is " + doc.getDocCreation());
        }
    }

    public void countAllWords() {

        for (Document doc : DocumentsList){
            try {
                doc.countWords();
            } catch (Exception e) {
                System.out.println("Could not count words in " + doc.getDocName());
                System.out.println(e.getStackTrace());
            }
            //for (String key : doc.getSortedMap().keySet()){
            //    System.out.println("Word : " + key + ", Value : " + doc.getWordCount().get(key));
            //}
        }
    }

    public void countAllWordsInCorpus() {
        unsortedCorpusCount = new HashMap<>();
        for(Document doc : DocumentsList){
            for (String key : doc.getSortedMap().keySet()){
                if (unsortedCorpusCount.containsKey(key)) { //if word already occurs in hashmap
                    unsortedCorpusCount.put(key, unsortedCorpusCount.get(key) + doc.getSortedMap().get(key));  //increase count
                } else { //if word hasn't yet occurred
                    unsortedCorpusCount.put(key, doc.getSortedMap().get(key)); //add word to hashmap with amount of occurences in current doc
                }
            }
        }

        // Following code referenced
        // David Landup (Feb 2021) StackAbuse Available At: https://stackabuse.com/how-to-sort-a-hashmap-by-value-in-java/ (Accessed May 2022)
        // Sorting a hashmap into a LinkedHashMap
        sortedCorpusCount = this.unsortedCorpusCount.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> e.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));
        // reference end

        for (String key : sortedCorpusCount.keySet()){
                System.out.println("Word : " + key + ", Value : " + sortedCorpusCount.get(key));
            }
    }
}
