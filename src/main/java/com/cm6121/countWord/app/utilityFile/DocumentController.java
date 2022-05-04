package com.cm6121.countWord.app.utilityFile;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class DocumentController {

    private ArrayList<Document> DocumentsList = new ArrayList<Document>();

    public DocumentController() {
        this.DocumentsList = new ArrayList<Document>();
    }

    public File pathToFileObj(final String path){
        File nFile = new File(path);
        //Path pathI = Path.of(path);
        //File newFile = pathI.toFile();
        return newFile;
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
}
