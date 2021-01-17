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
    public static String deleteNonDigits(String priceEuro) {
        Objects.nonNull(priceEuro);
        String editedPrice1 = priceEuro.replaceAll("[^0-9\\,]","");
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
        return sentences.replaceAll("\\.\\s?","\\.<br>");
    }
    
    public static String formatWithoutComma(String sentences) {
        return sentences.replaceAll(",","");
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
    
    public static String convertMainImageUrl(String mainaImageName) {
        return "https://moondrive81.cafe24.com/GKoo/main_image/" + mainaImageName + ".jpg";
    }
}
