package com.cm6121.countWord.app.utilityFile;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Method that will create a directory in your root folder and return the directory as a String.
 * The CSV files that you are saving are expected to be in this path.
 */

public class WriterCSV {

    public String createDirectory() {
        String pathFile = System.getProperty("user.home") + "/StudentCSVSaved/";
        File dir = new File(pathFile);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return pathFile;
    }

    public void createAllWordsFileFromDoc(Document doc) {

        this.createDirectory();

        String pathFile = System.getProperty("user.home") + "/StudentCSVSaved/";
        String docTitle = doc.getDocTitle().replaceAll("\\s", "_").trim();
        File file = new File(pathFile + docTitle + "_allWords.csv");

        Map<String, Integer> sortedHashMap = null;
        try {
            sortedHashMap = doc.getSortedMap();
        } catch (Exception e) {

            System.out.println("Could not find sorted hashmap of word count for file " + doc.getDocTitle());
        }
        try {
            FileOutputStream outputStream = new FileOutputStream(file, false);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(doc.getDocTitle());
            bufferedWriter.write(",");
            bufferedWriter.write(doc.getDocCreation());
            bufferedWriter.newLine();
            for (String key : sortedHashMap.keySet()) {
                bufferedWriter.write(key);
                bufferedWriter.write(",");
                bufferedWriter.write(sortedHashMap.get(key).toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Could not write file " + doc.getDocTitle());
            e.printStackTrace();
        }
    }


    public void createWholeCorpusCSV(Map<String, Integer> sortedHashMap) {
        this.createDirectory();

        String pathFile = System.getProperty("user.home") + "/StudentCSVSaved/";
        File file = new File(pathFile + "CSVAllDocuments_allWords.csv");

        try {
            FileOutputStream outputStream = new FileOutputStream(file, false);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write("Word, Number of Occurrences");
            bufferedWriter.newLine();

            for (int i = sortedHashMap.size() - 1; i > 0; i--) {
                String[] key = sortedHashMap.keySet().toArray(new String[0]);

                bufferedWriter.write(key[i]);
                bufferedWriter.write(",");
                bufferedWriter.write(sortedHashMap.get(key[i]).toString());
                bufferedWriter.newLine();

            }
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Could not write all corpus file");
            e.printStackTrace();
        }
    }
}
