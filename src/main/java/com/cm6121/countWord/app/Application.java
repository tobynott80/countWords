package com.cm6121.countWord.app;


import com.cm6121.countWord.app.utilityFile.DocumentController;

public class Application {
    public static void main (String[] args) {
        String documentToRead = ClassLoader.getSystemClassLoader().getResource("FolderDocumentsToRead").getPath();
        System.out.println("The counting words application");
        System.out.println(documentToRead);

        DocumentController docController = new DocumentController();
        docController.loadFolder(docController.pathToFileObj(documentToRead));

        docController.parseFolder();
    }
}
