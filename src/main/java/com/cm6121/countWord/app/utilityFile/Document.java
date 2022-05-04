package com.cm6121.countWord.app.utilityFile;

import java.io.File;

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
}
