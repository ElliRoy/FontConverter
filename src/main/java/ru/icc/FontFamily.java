package ru.icc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FontFamily {

    /**
     * Всевозможные название семейств шрифтов
     */
    private final String[] fontFamilies1 = new String[]{
            "3x3", "AdLib", "AdobeJenson", "AgencyFB", "Aharoni", "AkzidenzGrotesk", "Albertus", "Aldus", "Alexandria", "Alexandria",
            "Algerian", "Allegro", "Alphabetum", "AmericanScribe", "AmericanTypewriter", "AmericanTypewriter", "AMSEuler", "AndaléMono",
            "AndaléSans", "Andreas", "Andy", "Antiqua", "AntiqueOlive", "Aparajita", "AppleChancery", "AppleSymbols", "Archer", "Arial",
            "ArialMonospaced", "ArialUnicodeMS", "ArialUnicodeMS", "ArialUnicodeMSrial", "Arno", "ArnoldBöcklin", "AsanaMath", "AshleyScript",
            "Aster", "Astur", "Athens", "Aurora", "Aurora", "Avenir", "Balloon", "Banco", "BankGothic", "Baskerville", "Bastard", "BatangandGungsuh",
            "BauerBodoni", "Bauhaus", "Bauhaus", "Bell", "BellCentennial", "BellGothic", "BelweRoman", "Bembo", "BenguiatGothic", "BerkeleyOldStyle",
            "BerlinSans", "BernhardModern", "BitstreamCyberbit", "BitstreamVera", "BitstreamVeraeraSans", "BitstreamVeraeraSansMono",
            "BitstreamVeraeraSerif", "BitstreamVeraitstreamVera", "Blackboardbold", "Bodoni", "BookAntiqua", "Bookman", "BookshelfSymbol7",
            "Braggadocio", "BrandonGrotesque", "BreitkopfFraktur", "Broadway", "BrushScript", "Bulmer", "Caledonia", "Calibri", "Calibri",
            "CalifornianFB", "CalistoMT", "Cambria", "CambriaMath", "Candida", "Capitals", "Cartier", "Casey", "Caslon", "Caslon",
            "CaslonAntique", "CaslonAntique", "CaslonAntiqueFifteenthCentury", "CaslonWyld", "Catull", "Centaur", "CenturyGothic",
            "CenturyOldStyle", "CenturySchoolbook", "CenturySchoolbook", "CenturySchoolbookInfant", "Cézanne", "Chalkboard",
            "Chandas", "Charcoal", "CharisSIL", "CharisSIL", "Charter", "Cheltenham", "Chicago", "Choc", "ChollaSlab", "City",
            "Clarendon", "Clearface", "Clearview", "CloisterBlack", "Cochin", "Code2000", "Code2001", "Code2002", "Colonna",
            "ComicSans", "ComicSansMS", "Compacta", "Compatil", "ComputerModern", "ComputerModern", "ConcreteRoman",
            "ConcreteRoman", "Consolas", "Constantia", "CooperBlack", "CooperBlack", "CopperplateGothic", "Corbel",
            "Corona", "CoronaNews705", "Coronet", "Courier", "Courier", "CourierNew", "Curlz", "Curlz", "DejaVufonts",
            "DejaVufonts", "DejaVufontsejaVuSans", "DejaVufontsejaVuSansMono", "Didone", "Didone", "Didot", "DIN1451IN",
            "DomCasual", "DomCasual", "Dotum", "Dotum", "DoulosSIL", "DroidfontsroidSans", "DroidfontsroidSansMono",
            "Droidserif", "Dyslexie", "Easyreading", "EBGaramond", "Ecofont", "Egyptienne", "Elephant", "Ellington",
            "Emerson", "Eras", "EspySans", "Esseltub", "Eurocrat", "Eurostile", "Eurostilequare721", "EversonMono",
            "EversonMono", "Excelsior", "Excelsior", "Exocet", "Fairfield", "Fallbackfont", "FetteFraktur", "FFDax",
            "FFMeta", "FFScala", "FFScalaSans", "FIGScript", "FiraSans", "Fixed", "Fixedsys", "FixedsysExcelsior",
            "FixedsysExcelsior", "Fletcher", "Folio", "Footlight", "Forte", "Forte", "Fraktur", "FranklinGothic",
            "Freefont", "FreeSans", "FreeSerif", "FreeUCSOutlineFonts", "FrenchScript", "FrizQuadrata", "Frutiger",
            "Futura", "Gabriola", "Gadugi", "Garamond", "Generis", "Geneva", "Gentium", "Gentium", "Georgia",
            "Georgia", "GillSans", "GillSansSchoolbook", "Gloucester", "GNUUnifont", "Gotham", "GoudyOldStyle",
            "Granjon", "Grassettypefacerasset", "Gravura", "Grecsduroi", "GuardianEgyptian", "Gulim", "Haettenschweiler",
            "Hanacaraka", "HandelGothic", "HarabaraHand", "HarabaraMais", "Hei", "Helvetica", "HelveticaNeue", "HighTowerText",
            "HighwayGothic", "Hobo", "HoeflerText", "Horizon", "HyperFont", "IBMPlex", "IBMPlexBMPlexMono", "IBMPlexBMPlexSans",
            "Impact", "Imprint", "Inconsolata", "Industria", "Interstate", "IonicNo.5", "IonicNo.5", "IonicNo.5News701",
            "ITCAvantGardevantGardeGothic", "ITCBenguiat", "ITCZapfChancery", "Janson", "JapaneseGothic", "JimCrow", "Joanna",
            "Johnston", "Jokerman", "Jomolhari", "Junicode", "Kabel", "Kiran", "Klavika", "Kochi", "Koren", "Korinna", "Kristen",
            "KrutiDev", "KuenstlerScript", "LastResort", "Lato", "LED", "LetterGothic", "Lexia", "Lexicon", "Liberationfonts",
            "LiberationfontsiberationMono", "LiberationfontsiberationSans", "LinuxBiolinum", "LinuxLibertine", "Literaturnaya",
            "Lithos", "LoType", "Lucida", "LucidaBlackletter", "LucidaBright", "LucidaConsole", "LucidaGrande", "LucidaGrande",
            "LucidaHandwriting", "LucidaSansTypewriter", "LucidaSansucidaSans", "LucidaSansUnicode", "LucidaSansUnicode",
            "LucidaTypewriterSerif", "Luxi", "Lydian", "MalgunGothic", "Marlett", "Meiryo", "Meiryo", "Melior", "Memphis",
            "Memphis", "Menlo", "Meta", "MICR", "Microgramma", "MicrosoftJhengHei", "MicrosoftYaHei", "Miller", "Minchō",
            "Ming", "Minion", "Mistral", "Mona", "Monaco", "MonaLisa", "Monospace", "MonotypeCorsiva", "Motorway", "MrsEaves",
            "MSGothic", "MSGothic", "MSGothic", "MSMincho", "MSMincho", "MSSansSerif", "MSSerif", "Myriad", "NastaliqNavees",
            "Neuland", "Neutraface", "NeuzeitS", "NewCenturySchoolbook", "NewGulim", "NewsGothic", "NewYork", "Nilland",
            "NimbusMonoL", "NimbusRoman", "NimbusSans", "NimbusSansL", "Nordstern", "Noto", "NPSRawlinsonRoadway", "Nyala",
            "OCRAExtended", "OCRAfontCRA", "OCRB", "OldEnglishText", "OldEnglishTextMT", "Onyx", "OpenSans", "Optima",
            "Overpass", "Palatino", "Papyrus", "Parisine", "Peignot", "Perpetua", "PerpetuaGreek", "Plantin", "Playbill",
            "Porson", "PragmataPro", "PragmataPro", "PrestigeElite", "Primer", "ProductSans", "ProFont", "Proggyprogrammingfonts",
            "ProximaNova", "PTSans", "RailAlphabet", "Renault", "Requiem", "Roboto", "RobotoSlab", "Rockwell", "RotisotisSemiSerif",
            "RotisSans", "RotisSerif", "Sabon", "SanFrancisco", "SanFranciscoanFrancisco", "Schadow", "Schwabacher", "Scripttypefacecript",
            "SegoeegoeScript", "SegoeegoeUI", "SegoeUISymbol", "Serifa", "Shruti", "SimHei", "SimSun", "SimSun", "Sistina",
            "SkeletonAntique", "Skia", "Skia", "SourceCodePro", "SourceSansPro", "Souvenir", "SquarishSansCT", "Sreda", "Stencil",
            "STIX", "STIX", "SwedenSans", "Swift", "Swiss721", "Sylfaen", "Sylfaen", "Symbol", "Syntax", "System", "Tahoma",
            "Tahoma", "TemplateGothic", "Tengwar", "Terminal", "ThesisSans", "TibetanMachineUni", "Times", "TimesNewRoman",
            "Tiresias", "TitusCyberbitBasic", "Torino", "Tower", "TradeGothic", "Trajan", "Transport", "TrebuchetMS", "Trinité",
            "Trixie", "TrumpMediaeval", "TwentiethCenturywentiethCentury", "Ubuntu", "Ubuntu", "Umbra", "Unica", "Univers",
            "Utopia", "Utopia", "Verdana", "VerdanaerdanaRef", "Webdings", "Westminster", "WideLatin", "WiesbadenSwing",
            "Willow", "WilsonGreek", "Windsor", "Windsor", "Wingdings", "Wingdings2", "Wingdings3", "XITS", "XITS",
            "ZapfDingbats", "Zapfino", "Zurich"
    };

    /**
     * Самые частые шрифты в PDF документах
     */
    private final String[] fontFamilies14 = new String[]{
            "Arial", "Times", "Courier", "CourierNew", "Helvetica"
    };

    /**
     * Самые частые шрифты в PDF документах, но здесь предпочтительнее назввания для шрифтов, чем в <code>fontFamilies14</code>
     */

    private final String[] fontFamilies14First = new String[]{
            "Arial", "TimesNewRoman", "CourierNew", "HelveticaNeue", "Symbol", "ZapfDingbats"
    };

    /**
     * Используемые далее объекты
     */

    private String f = null;
    private Pattern p;
    private Matcher m;


    public FontFamily() {
        super();
    }

    /**
     * Метод ищет в строке <code>font</code> совпадение названия сначала в массиве fontFamilies14First, если нет совпадений,
     * то далее в массиве <code>fontFamilies14</code> и затем в <code>fontFamilies</code>.
     *
     * @param font Строка, которую получаем из <code>font.getFontName</code>
     * @return Название шрифта
     **/

    public String getFontFamily(String font) {
        f = null;
        for (int i = 0; i < 6; i++) {
            p = Pattern.compile(fontFamilies14First[i], Pattern.CASE_INSENSITIVE);
            m = p.matcher(font);
            if (m.find()) {
                f = fontFamilies14First[i];
                break;
            }
        }
        if (f == null) {
            for (int i = 0; i < 5; i++) {
                p = Pattern.compile(fontFamilies14[i], Pattern.CASE_INSENSITIVE);
                m = p.matcher(font);
                if (m.find()) {
                    f = fontFamilies14[i];
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
        if(f == null)
            f = font;
        return f;
    }
}
