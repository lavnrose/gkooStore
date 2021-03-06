package util;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.StringUtils;

public class Formatter {
    public static String convertKoreaCurrency(int value) {
        return value + "원";
    }
    
    //1.550,00
    // £129.5 point deleteted!
    public static String deleteNonDigits(String priceEuro) {
        Objects.nonNull(priceEuro);
        String editedPrice1 = priceEuro.replaceAll("[^0-9\\,\\.]","");
        String editedPrice2  = editedPrice1.replaceAll("[\\,]",".");
        return editedPrice2;
    }
    
    public static List<String> splitAfterWord(String sentence, String word) {
        List<String> convertedStringList = Stream.of(sentence.split(word, -1))
                .collect(Collectors.toList());
        return convertedStringList;
    }
    
    public static String setLinebreakAfterPunct(String sentences) {
        return sentences.replaceAll("\\.\\s?","\\.\n");
    }
    
    public static String setLinebreakAfterPunctHtml(String sentences) {
        if (sentences == null) {
            return "";
        }
        return sentences.replaceAll("\\.\\s?","\\.<br>");
    }
    
    public static String formatWithoutComma(String sentences) {
        return sentences.replaceAll(",","");
    }
    
    public static String formatWithoutCommaBar(String sentences) {
        return sentences.replaceAll("[\\|\\,]","");
    }
    
    public static String abbreviateStringLeft(String str, int left) {
        return StringUtils.left(str, left);
    }
    
    public static String replaceForwardSlash(String str) {
        return str.replace("/", "-");
    }
    
    public static String replaceAndSymbol(String str) {
        return str.replace("&", " ");
    }
    
    public static String replaceEmptySymbol(String str) {
        return str.replace(" ", "_");
    }
    
    public static String replaceSymbols(String str) {
        return str.replaceAll("[\\,\\.\\/]", "_");
    }
    
    public static String replaceSymbolsToEmpty(String str) {
        return str.replaceAll("[\\,\\.\\/]", " ");
    }
    
    public static String removeEmptySymbol(String str) {
        return str.replace(" ", "");
    }
    
//    public static String convertMainImageUrl(String mainaImageName) {
//        return "https://moondrive81.cafe24.com/GKoo/cosmetic/main_images/" + mainaImageName + ".jpg";
//    }
    
    /**
     * @param mainaImageName
     * @param dirName cafe24 directory of main images
     * @return
     */
    public static String convertMainImageUrlMode(String dirName, String mainaImageName) {
        return "https://moondrive81.cafe24.com/GKoo/mode/" + dirName + mainaImageName + ".jpg";
    }
    
    //use CosmeticUtil
    public static String convertMainImageUrlCosmetic(String dirName, String mainaImageName) {
        return "https://moondrive81.cafe24.com/GKoo/cosmetic/" + dirName + "main_images/" +  mainaImageName + ".jpg";
    }
    
    public static String convertItemOverviewCosmetic(String dirName, String brandName) {
        return "https://moondrive81.cafe24.com/GKoo/cosmetic/" + dirName  +  brandName + "_overview.jpg";
    }
    
    public static String getFormattedJpgUrl(String rawUrl) {
        return Formatter.splitAfterWord(rawUrl, ".jpg").get(0) + ".jpg";
    }
    
    public static String getFormattedHtmUrl(String rawUrl) {
        return Formatter.splitAfterWord(rawUrl, ".html").get(0) + ".html";
    }
    
    public static String replaceUmlaut(String input) {
        
        //replace all lower Umlauts
        String output = input.replace("ü", "ue")
                             .replace("ö", "oe")
                             .replace("ä", "ae")
                             .replace("ß", "ss");
    
        //first replace all capital umlaute in a non-capitalized context (e.g. Übung)
        output = output.replaceAll("Ü(?=[a-zäöüß ])", "Ue")
                       .replaceAll("Ö(?=[a-zäöüß ])", "Oe")
                       .replaceAll("Ä(?=[a-zäöüß ])", "Ae");
    
        //now replace all the other capital umlaute
        output = output.replace("Ü", "UE")
                       .replace("Ö", "OE")
                       .replace("Ä", "AE");
    
        return output;
    }
    
    public static String keepOnlyAlphabet(String sentence) {
        String newstr = sentence.replaceAll("[^A-Za-z]+", "");
        return newstr;
    }
}
