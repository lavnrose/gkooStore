package agencyEntities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.Formatter;
import util.ImageDownloader;

public interface ICosmeticSimpleAgent {
    static final Logger LOGGER = LogManager.getLogger(ICosmeticSimpleAgent.class);
    public static List<String> itemSameTitleTester = new ArrayList<>();
    
    default List<MassItem> preprocessing(String htmlBrand) throws IOException, InterruptedException {
        List<MassItem> massItemList = new ArrayList<>();
        List<String> itemUrls = new ArrayList<>();
        File input = new File(htmlBrand);
        Document doc = Jsoup.parse(input, "UTF-8", "");
        Element body = doc.body();
        
        Elements unitElements = getUnitElements(body);
        
        for (int i=0; i<unitElements.size(); i++) {
            String url = extractUrl(unitElements.get(i));
            
            boolean validUrl = true;
            try {
                doc = Jsoup.connect(url).timeout(2000).userAgent("Chrome").get();
            } catch (IOException e) {
                LOGGER.error("url is invalid:" + url);
                validUrl = false;
            } 
            
            if(itemUrls.contains(url)) {
                validUrl = false;
            }
            
            if (validUrl) {
                MassItem massItem = new MassItem();
                massItemList.add(massItem);
                
                setItemUrl(massItem, itemUrls, massItemList, url);
                
                String rawItemTitle = extractItemTitle(unitElements.get(i));
                setValidItemTitle(massItem, rawItemTitle);
                
                setMainImageName(massItem);

                downloadingMainImage(massItem, unitElements.get(i));
                
                setDetailImage(massItem);
                
                setItemPrice(massItem, unitElements.get(i));
                setItemVolume(massItem, unitElements.get(i));
                setItemIngredients(massItem, url);

                setCommonProperties(massItem);
                
                //TimeUnit.SECONDS.sleep(2);
            }
        }
        return massItemList;
    };

    Elements getUnitElements(Element body);
    
    default void setItemUrl(MassItem massItem, List<String> itemUrls, List<MassItem> massItemList, String validUrl) {
        itemUrls.add(validUrl);
        massItem.setItemUrl(validUrl);
    };
    
    default void setValidItemTitle(MassItem massItem, String rawItemTitle) {
        String formattedItemTitle1 = Formatter.replaceUmlaut(rawItemTitle);
        String formattedItemTitle = Formatter.replaceSymbolsToEmpty(formattedItemTitle1);
        String validItemTitle = getValidItemTitle(formattedItemTitle);
        massItem.setItemTitleDE(validItemTitle);
    };
    
    default String getValidItemTitle(String itemTitle) {
        Objects.requireNonNull(itemTitle);
        
        String validTitle = "";
        List<String> matchedItems = new ArrayList<>();
        for(String title : itemSameTitleTester) {
            if(title.contains(itemTitle)) {
                matchedItems.add(title);
            }
        }
        int matchedItemSize = matchedItems.size();
        if(matchedItemSize>0) {
            validTitle = itemTitle + "_" + String.valueOf(matchedItemSize+1);
        } else {
            validTitle = itemTitle;
        }
        
        itemSameTitleTester.add(validTitle);
        return validTitle;
    }
    
    default void setMainImageName(MassItem massItem) {
        String mainImageName = Formatter.replaceEmptySymbol(massItem.getItemTitleDE());
        LOGGER.info("mainImageName: " + mainImageName);
        massItem.setMainImageName(mainImageName);
    };
    
    void setDetailImage(MassItem massItem);
    
    String extractUrl(Element unitElement);
    String extractItemTitle(Element unitElement);
    
    void downloadingMainImage(MassItem massItem, Element unitElement);
    
    void setCommonProperties(MassItem massItem);
    
    default void savingMainImage(final String imageName, String directory, final String imageUrl) {
        ImageDownloader.resizeImageScalrZalando(imageName, directory, imageUrl, 500);
    }

    void setItemPrice(MassItem massItem, Element unitElement);
    void setItemVolume(MassItem massItem, Element unitElement);
    void setItemIngredients(MassItem massItem, String validUrl);
}
