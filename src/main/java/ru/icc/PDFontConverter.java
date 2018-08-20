package ru.icc;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.*;
import java.util.List;

public class PDFontConverter extends PDFTextStripper{
    private TextPosition firstLetter;
    private TextPosition lastLetter;
    private PDFont font = null;
    private int i = 0;



    public PDFontConverter() throws IOException {
        super();
    }


    public void stripPage(int pageNr, PDDocument document) throws IOException {
        this.setStartPage(pageNr+1);
        this.setEndPage(pageNr+1);
        Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
        writeText(document, dummy);
    }

    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {
        for (TextPosition text : textPositions) {
            PDFont tempFont = text.getFont();
            if(i==0) {
                System.out.println("String[" + text.getXDirAdj() + "," + text.getYDirAdj() + " fs=" + text.getFontSizeInPt() +
                        " xscale=" + text.getXScale() + " height=" + text.getHeightDir() + " space=" + text.getWidthOfSpace() +
                        " width=" + text.getWidthDirAdj() + " ] " + text.getUnicode()+ " " +             text.getFont().getName());
                i++;
                font = tempFont;


            } else if (!font.equals(tempFont)){
                System.out.println("String[" + text.getXDirAdj() + "," + text.getYDirAdj() + " fs=" + text.getFontSizeInPt() +
                        " xscale=" + text.getXScale() + " height=" + text.getHeightDir() + " space=" + text.getWidthOfSpace() +
                        " width=" + text.getWidthDirAdj() + " ] " + text.getUnicode());
                font = tempFont;
            }
        }
    }
}
