package com.cm6121.countWord.app.utilityFile;

import java.io.File;
import java.util.List;

public class Document {

    private String docName;

    private String docPath;

    private File docFile;

    private int docCreationYear;

    private String docTitle;

    private String docText;


    public Document(final File fileI) {
        this.docFile = fileI;
        this.docName = fileI.getName();
        this.docPath = fileI.getPath();
    }

    public void parse() {
        FileReader fileReader = new FileReader();
        List<String[]> list = fileReader.readCSVMethod1(docFile);
        for (String[] str : list) {
            System.out.println(str);
        }

    }
}
