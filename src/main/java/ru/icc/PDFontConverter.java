package ru.icc;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
//hello
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
    private Font font;
    private Font tempFont;
    private boolean isFirst = true;
    private ArrayList<String> fontFamilies1 = new ArrayList<>();
    private ArrayList<String> fontFamilies2 = new ArrayList<>();
    private ArrayList<String> fontFamilies3 = new ArrayList<>();
    private String f = null;
    private Pattern p;
    private Matcher m;

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
                    font = getFont(text);
                    System.out.println(font.toString());
                    isFirst = false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        Scanner scanner = new Scanner(inputStream);
        while(scanner.hasNextLine()){
            fontFamilies1.add(scanner.nextLine());
        }
    }

    private void setFontFamilies2(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        while(scanner.hasNextLine()){
            fontFamilies2.add(scanner.nextLine());
        }
    }

    private void setFontFamilies3(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
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
        if (font == null)
            return null;
        if (fontDescriptor == null)
            return null;

        if (fontDescriptor.getFontName() == null) {
            return null;
        }
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
        } else if (fontName.toLowerCase().contains("italic")) {
            isFontItalic = true;
        } else {
            isFontItalic = false;
        }

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
            if (rm == RenderingMode.FILL_STROKE) {
                isFontBold = true;
            } else {
                isFontBold = false;
            }
        }

        //font size in pt
        float fontSize = (int) textPosition.getFontSizeInPt();

        //return object java.awt.Font
        if(isFontBold){
            if(isFontItalic){
                return new Font(resultName, Font.BOLD+Font.ITALIC, (int) fontSize);
            } else {
                return new Font(resultName, Font.BOLD, (int) fontSize);
            }
        } else {
            if(isFontItalic){
                return new Font(resultName, Font.ITALIC, (int) fontSize);
            } else {
                return new Font(resultName, Font.PLAIN, (int) fontSize);
            }
        }
    }

    /**
     * Метод ищет в строке <code>font</code> совпадение названия сначала в массиве fontFamilies3, если нет совпадений,
     * то далее в массиве <code>fontFamilies2</code> и затем в <code>fontFamilies</code>.
     *
     * @param font Строка, которую получаем из <code>font.getFontName</code>
     * @return Название шрифта
     **/

    private String getFontFamily(String font) {
        f = null;

        for (int i = 0; i < fontFamilies3.size(); i++) {
            p = Pattern.compile(fontFamilies3.get(i), Pattern.CASE_INSENSITIVE);
            m = p.matcher(font);
            if (m.find()) {
                f = fontFamilies3.get(i);
                break;
            }
        }
        if (f == null) {
            for (int i = 0; i < fontFamilies2.size(); i++) {
                p = Pattern.compile(fontFamilies2.get(i), Pattern.CASE_INSENSITIVE);
                m = p.matcher(font);
                if (m.find()) {
                    f = fontFamilies2.get(i);
                    break;
                }
            }
            if (f == null) {
                for (int i = 0; i < fontFamilies3.size(); i++) {
                    p = Pattern.compile(fontFamilies1.get(i), Pattern.CASE_INSENSITIVE);
                    m = p.matcher(font);
                    if (m.find()) {
                        f = fontFamilies1.get(i);
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
