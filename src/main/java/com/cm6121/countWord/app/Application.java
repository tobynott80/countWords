package com.cm6121.countWord.app;


import com.cm6121.countWord.app.utilityFile.DocumentController;

import java.util.Scanner;

public class Application {
    public static void main (String[] args) {
        String documentToRead = ClassLoader.getSystemClassLoader().getResource("FolderDocumentsToRead").getPath();
        System.out.println("The counting words application");
        System.out.println(documentToRead);

        DocumentController docController = new DocumentController();
        docController.loadFolder(docController.pathToFileObj(documentToRead));
        docController.parseFolder();
        docController.countAllWords();
        docController.countAllWordsInCorpus();
        docController.createCSV();



        boolean usrQuit = false;
        while (!usrQuit){
            Scanner sc = new Scanner(System.in);
            Integer usrSelection;
            do {
                System.out.println();
                System.out.println("1. Display the names and number of documents");
                System.out.println("2. Display the number of occurrences of the words for each document");
                System.out.println("3. Search for the occurrences of a particular word in each document");
                System.out.println("4. Display the number of occurrences for each word in the whole corpus");
                System.out.println("5. Quit");
                while (!sc.hasNextInt()) { //ensuring a valid number is entered.
                    System.out.println("Please enter a number between 1 and 5");
                    sc.next();
                }
                usrSelection = sc.nextInt();
            } while (usrSelection < 1 || usrSelection > 5);
            if (usrSelection.equals(1)){
                docController.displayDocumentsParsed();
            } else if (usrSelection.equals(2)) {
                docController.displayDocumentsWordCount();
            } else if (usrSelection.equals(3)) {
                docController.searchWordCount();
            } else if (usrSelection.equals(4)) {
                docController.displayCorpusWordCount();
            } else if (usrSelection.equals(5)) {
                usrQuit = true;
            }
        }





    }
}
