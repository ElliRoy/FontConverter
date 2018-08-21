package ru.icc;

import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception{
        doIt();
    }

    public static void doIt() throws Exception {
        //Документ для примера
        File file = new File("PDFontsDoc.pdf");
        PDDocument document = PDDocument.load(file);

        PDFontConverter pdFontConverter = new PDFontConverter();

        for(int i = 0; i <document.getNumberOfPages(); i++){
            pdFontConverter.stripPage(i, document);
        }
    }
}
