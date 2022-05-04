package com.cm6121.countWord.app.utilityFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Document {

    private String docName;

    private String docPath;

    private File docFile;

    private String docCreation;

    private String docTitle;

    private String docText;


    public Document(final File fileI) {
        this.docFile = fileI;
        this.docName = fileI.getName();
        this.docPath = fileI.getPath();
    }

    public void parse() {
        FileReader fileReader = new FileReader();

        String[] list = fileReader.readCSVMethod1(docFile).get(0);
        this.docTitle = list[0];
        this.docCreation = list[2];
        this.docText = list[1];

    }
}
