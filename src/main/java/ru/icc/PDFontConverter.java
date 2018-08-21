package ru.icc;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import java.awt.*;
import java.io.*;
import java.util.List;

public class PDFontConverter extends PDFTextStripper{
    private Font font;
    private Font tempFont;
    private boolean isFirst = true;
    private FontFamily ff = new FontFamily();


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
    protected void writeString(String string, List<TextPosition> textPositions) {
        for (TextPosition text : textPositions) {
            if(isFirst) {
                try {
                    font = ff.getFont(text);
                    System.out.println(font.toString());
                    isFirst = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                tempFont = ff.getFont(text);
                if(!tempFont.equals(font)){
                    System.out.println(tempFont.toString());
                    font = tempFont;
                }
            }
        }
    }
}
