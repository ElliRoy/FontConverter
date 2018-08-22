package ru.icc;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFontConverter extends PDFTextStripper{
    private final Map<TextPosition, RenderingMode> renderingMode = new HashMap<>();
    private Font font = null;
    private Font tempFont;
    private ArrayList<String> fontFamilies1 = new ArrayList<>();
    private ArrayList<String> fontFamilies2 = new ArrayList<>();
    private ArrayList<String> fontFamilies3 = new ArrayList<>();
    private String f;
    private Pattern p;
    private Matcher m;
    private Scanner scanner;

    public PDFontConverter() throws IOException {
        super();

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("fontFamilies1.txt");
        setFontFamilies1(inputStream);

        inputStream = classLoader.getResourceAsStream("fontFamilies2.txt");
        setFontFamilies2(inputStream);

        inputStream = classLoader.getResourceAsStream("fontFamilies3.txt");
        setFontFamilies3(inputStream);

        inputStream.close();
    }

    @Override
    protected void processTextPosition(TextPosition text) {
        renderingMode.put(text, getGraphicsState().getTextState().getRenderingMode());
        super.processTextPosition(text);
    }

    public void stripPage(int pageNumber, PDDocument document) throws IOException {
        this.setStartPage(pageNumber+1);
        this.setEndPage(pageNumber+1);
        Writer writer = new OutputStreamWriter(new ByteArrayOutputStream());
        writeText(document, writer);
    }

    @Override
    protected void writeString(String string, List<TextPosition> textPositions) {
        for (TextPosition text : textPositions) {
            if(font == null) {
                font = getFont(text);
                System.out.println(font.toString());
            } else {
                tempFont = getFont(text);
                if (tempFont == null) {
                    System.out.println("null");
                } else {
                    if (!tempFont.equals(font)) {
                        System.out.println(tempFont.toString());
                        font = tempFont;
                    }
                }
            }
        }
    }

    private void setFontFamilies1(InputStream inputStream) {
        scanner = new Scanner(inputStream);
        while(scanner.hasNextLine()) {
            fontFamilies1.add(scanner.nextLine());
        }
    }

    private void setFontFamilies2(InputStream inputStream) {
        scanner = new Scanner(inputStream);
        while(scanner.hasNextLine()){
            fontFamilies2.add(scanner.nextLine());
        }
    }

    private void setFontFamilies3(InputStream inputStream) {
        scanner = new Scanner(inputStream);
        while(scanner.hasNextLine()){
            fontFamilies3.add(scanner.nextLine());
        }
    }

    private Font getFont(TextPosition textPosition) {
        if (textPosition == null) {
            return null;
        }

        PDFont font = textPosition.getFont();
        PDFontDescriptor fontDescriptor = font.getFontDescriptor();

        if (font == null || fontDescriptor == null || fontDescriptor.getFontName() == null)
            return null;

        String fontName = fontDescriptor.getFontName();

        //font name
        String resultName;
        if (fontName != null)
            resultName = getFontFamily(fontName);
        else
            return null;

        //is font italic?
        boolean isFontItalic;
        if (fontDescriptor.isItalic()) {
            isFontItalic = true;
        } else if (fontName.toLowerCase().contains("oblique")) {
            isFontItalic = true;
        } else isFontItalic = fontName.toLowerCase().contains("italic");

        //Is font bold?
        boolean isFontBold;
        if (fontDescriptor.isForceBold()) {
            isFontBold = true;
        } else if (fontDescriptor.getFontWeight() >= 700) {
            isFontBold = true;
        } else if (fontDescriptor.getFontName().toLowerCase().contains("bold")) {
            isFontBold = true;
        } else {
            RenderingMode rm = renderingMode.get(textPosition);
            isFontBold = (rm == RenderingMode.FILL_STROKE);
        }

        //font size in pt
        int fontSize = (int) textPosition.getFontSizeInPt();

        //return object java.awt.Font
        if(isFontBold){
            if(isFontItalic){
                return new Font(resultName,Font.BOLD+Font.ITALIC, fontSize);
            } else {
                return new Font(resultName, Font.BOLD, fontSize);
            }
        } else {
            if(isFontItalic){
                return new Font(resultName, Font.ITALIC, fontSize);
            } else {
                return new Font(resultName, Font.PLAIN, fontSize);
            }
        }
    }
    
    private String getFontFamily(String font) {
        f = null;

        for (String aFontFamilies3 : fontFamilies3) {
            p = Pattern.compile(aFontFamilies3, Pattern.CASE_INSENSITIVE);
            m = p.matcher(font);
            if (m.find()) {
                f = aFontFamilies3;
                break;
            }
        }
        if (f == null) {
            for (String aFontFamilies2 : fontFamilies2) {
                p = Pattern.compile(aFontFamilies2, Pattern.CASE_INSENSITIVE);
                m = p.matcher(font);
                if (m.find()) {
                    f = aFontFamilies2;
                    break;
                }
            }
            if (f == null) {
                for (String aFontFamilies1 : fontFamilies1) {
                    p = Pattern.compile(aFontFamilies1, Pattern.CASE_INSENSITIVE);
                    m = p.matcher(font);
                    if (m.find()) {
                        f = aFontFamilies1;
                        break;
                    }
                }
            }
        }
        if (f == null)
            f = font;
        return f;
    }
}
