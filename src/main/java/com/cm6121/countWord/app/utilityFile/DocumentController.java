package com.cm6121.countWord.app.utilityFile;


import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class DocumentController {

    private Map<String, Integer> sortedCorpusCount;

    private final ArrayList<Document> DocumentsList;

    //Constructor
    public DocumentController() {
        this.DocumentsList = new ArrayList<Document>();
    }


    public File pathToFileObj(final String path) {
        File nFile = new File(path);
        return nFile;
    }

    public void loadFolder(final File folder) {
        for (File fileI : folder.listFiles()) {
            if (fileI.getName().contains(".csv")) {
                DocumentsList.add(new Document(fileI));
            } else {
                System.out.println(" is not a csv file");
            }
        }
    }

    public void parseFolder() {
        for (Document doc : DocumentsList) {
            doc.parse();
        }
    }

    public void displayDocumentsParsed() {
        System.out.println("The number of CSV documents in the folder is " + DocumentsList.size());
        for (Document doc : DocumentsList) {
            System.out.println("The file name is " + doc.getDocName() + ", the title is " + doc.getDocTitle() + ", the creation date is " + doc.getDocCreation());
        }
    }

    public void displayDocumentsWordCount() {
        System.out.println("Please select which document you wish to find the word count of");
        Scanner sc = new Scanner(System.in);
        Integer usrSelection;
        do {
            System.out.println();
            for (int i = 0; i < DocumentsList.toArray().length; i++) {
                System.out.println(i + ". " + DocumentsList.get(i).getDocName() + "  (" + DocumentsList.get(i).getDocTitle() + ")");
            }
            while (!sc.hasNextInt()) { //ensuring a valid number is entered.
                System.out.println("Please enter a valid number");
                sc.next();
            }
            usrSelection = sc.nextInt();
        } while (usrSelection < 0 || usrSelection > DocumentsList.toArray().length);
        for (String key : DocumentsList.get(usrSelection).getSortedMap().keySet()) {
            System.out.println("Word : " + key + ", Value : " + DocumentsList.get(usrSelection).getSortedMap().get(key));
        }

    }

    public void countAllWords() {
        for (Document doc : DocumentsList) {
            try {
                doc.countWords();
            } catch (Exception e) {
                System.out.println("Could not count words in " + doc.getDocName());
                System.out.println(e.getStackTrace());
            }
        }
    }

    public void countAllWordsInCorpus() {
        HashMap<String, Integer> unsortedCorpusCount = new HashMap<>();
        for (Document doc : DocumentsList) {
            for (String key : doc.getSortedMap().keySet()) {
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
        sortedCorpusCount = unsortedCorpusCount.entrySet().stream()
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

    public void displayCorpusWordCount() {
        for (int i = sortedCorpusCount.size() - 1; i > sortedCorpusCount.size() - 20; i--) {
            String[] key = sortedCorpusCount.keySet().toArray(new String[0]);
            System.out.println("Word : " + key[i] + ", Value : " + sortedCorpusCount.get(key[i]));
        }

    }

    public void searchWordCount() {
        System.out.println("Please enter the search word");
        Scanner inputObj = new Scanner(System.in);
        String usrInput = inputObj.nextLine().toLowerCase();
        usrInput = usrInput.replaceAll("\\p{P}", " "); //remove all punctuation
        usrInput = usrInput.replaceAll("\\s+", " ").trim(); // remove instances of double spaces

        if (sortedCorpusCount.containsKey(usrInput)) {
            System.out.println("The number of times the word " + usrInput + " appears in the whole corpus is " + sortedCorpusCount.get(usrInput));

            for (Document doc : DocumentsList) {
                if (doc.getSortedMap().containsKey(usrInput)) {
                    System.out.println("The number of times the word " + usrInput + " appears in " + doc.getDocTitle() + " is " + doc.getSortedMap().get(usrInput));
                } else {
                    System.out.println("The word " + usrInput + " doesn't occur in " + doc.getDocTitle());
                }
            }

        } else {
            System.out.println("The word " + usrInput + " doesn't occur in any documents - perhaps check your spelling");
        }

    }

    public void createCSV() {
        WriterCSV csvWriter = new WriterCSV();
        for (Document doc : DocumentsList) {
            csvWriter.createAllWordsFileFromDoc(doc);
        }
        csvWriter.createWholeCorpusCSV(this.sortedCorpusCount);

    }
}
