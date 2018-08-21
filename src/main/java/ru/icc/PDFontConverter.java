package ru.icc;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
import org.apache.pdfbox.pdmodel.graphics.state.RenderingMode;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;
import java.awt.*;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PDFontConverter extends PDFTextStripper{
    private Font font;
    private Font tempFont;
    private boolean isFirst = true;
    private final Map<TextPosition, RenderingMode> renderingMode = new HashMap<>();

    @Override
    protected void processTextPosition(TextPosition text) {
        renderingMode.put(text, getGraphicsState().getTextState().getRenderingMode());
        super.processTextPosition(text);
    }


    public PDFontConverter() throws IOException {
        super();
        try {
            inputStream = getClass().getResourceAsStream("/fontFamilies1.ser");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            this.fontFamilies1 = (String[]) objectInputStream.readObject();

            inputStream = getClass().getResourceAsStream("/fontFamilies2.ser");
            objectInputStream = new ObjectInputStream(inputStream);
            this.fontFamilies2 = (String[]) objectInputStream.readObject();

            inputStream = getClass().getResourceAsStream("/fontFamilies3.ser");
            objectInputStream = new ObjectInputStream(inputStream);
            this.fontFamilies3 = (String[]) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private String[] fontFamilies1;
    private String[] fontFamilies2;
    private String[] fontFamilies3;
    private String f = null;
    private Pattern p;
    private Matcher m;
    private InputStream inputStream;
    
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

            isFontBold = false;
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
        for (int i = 0; i < 6; i++) {
            p = Pattern.compile(fontFamilies3[i], Pattern.CASE_INSENSITIVE);
            m = p.matcher(font);
            if (m.find()) {
                f = fontFamilies3[i];
                break;
            }
        }
        if (f == null) {
            for (int i = 0; i < 5; i++) {
                p = Pattern.compile(fontFamilies2[i], Pattern.CASE_INSENSITIVE);
                m = p.matcher(font);
                if (m.find()) {
                    f = fontFamilies2[i];
                    break;
                }
            }
            if (f == null) {
                for (int i = 0; i < 467; i++) {
                    p = Pattern.compile(fontFamilies1[i], Pattern.CASE_INSENSITIVE);
                    m = p.matcher(font);
                    if (m.find()) {
                        f = fontFamilies1[i];
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
