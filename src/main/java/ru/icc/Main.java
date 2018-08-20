package ru.icc;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception{
        doIt();
    }

    public static void doIt() throws Exception {
        //Документ для примера
        File file = new File("/Users/EgorRo/Desktop/Test2.pdf");
        PDDocument document = PDDocument.load(file);
        //Создаём объект FontFamily
        FontFamily ff = new FontFamily();
        //Здесь просто вытаскиваем шрифты
        for (int i = 0; i < document.getNumberOfPages(); ++i)
        {
            PDPage page = document.getPage(i);
            PDResources res = page.getResources();

            for (COSName fontName : res.getFontNames())
            {
                PDFont font = res.getFont(fontName);
                PDFontDescriptor fontDescriptor = font.getFontDescriptor();

                String str = ff.getFontFamily(fontDescriptor.getFontName()); //Здесь мы и получаем FontFamily

                System.out.println(str + " " + fontDescriptor.getFontName()); //Выводим и сравниваем
            }
        }
    }
}
