package agencyEntities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.Formatter;
import util.ImageDownloader;

public interface IModeAgent {
    static final Logger LOGGER = LogManager.getLogger(IModeAgent.class);
    public static List<String> itemSameTitleTester = new ArrayList<>();
    
    default List<String> preprocessing(List<MassItem> massItemList, String htmlBrand) throws IOException {
        List<String> itemUrls = new ArrayList<>();
        File input = new File(htmlBrand);
        Document doc = Jsoup.parse(input, "UTF-8", "");
        Element body = doc.body();
        
        Elements urlElements = getUrlElements(body);
        
        for (int i=0; i<urlElements.size(); i++) {
            String url = extractUrl(urlElements.get(i));
            
            boolean validUrl = true;
            try {
                doc = Jsoup.connect(url).timeout(2000).userAgent("Chrome").get();
            } catch (IOException e) {
                LOGGER.error("url is invalid:" + url);
                validUrl = false;
            } 
            
            //filter the url
            if(itemUrls.contains(url)) {
                validUrl = false;
            }
            
            if (validUrl) {
                MassItem massItem = new MassItem();
                
                setItemUrl(massItem, itemUrls, massItemList, url);
                
                String rawItemTitle = extractItemTitle(massItem, urlElements.get(i));
                setValidItemTitle(massItem, rawItemTitle);
                
                setMainImageName(massItem);

                downloadingMainImage(massItem, urlElements.get(i));

                setCommonProperties(massItem);
            }
        }
        return itemUrls;
    };

    Elements getUrlElements(Element body);
    String extractUrl(Element urlElement);

    default void setItemUrl(MassItem massItem, List<String> itemUrls, List<MassItem> massItemList, String validUrl) {
        massItemList.add(massItem);
        itemUrls.add(validUrl);
        massItem.setItemUrl(validUrl);
    };

    String extractItemTitle(MassItem massItem, Element element);
    
    default void setValidItemTitle(MassItem massItem, String rawItemTitle) {
        String formattedItemTitle = Formatter.replaceUmlaut(rawItemTitle);
        String validItemTitle = getValidItemTitle(formattedItemTitle);
        massItem.setItemTitleDE(validItemTitle);
    };
    
    void setCommonProperties(MassItem massItem);
    
    default void setMainImageName(MassItem massItem) {
        String mainImageName = Formatter.replaceEmptySymbol(massItem.getItemTitleDE());
        
        LOGGER.info("mainImageName: " + mainImageName);
        massItem.setMainImageName(mainImageName);
    };
    
    void downloadingMainImage(MassItem massItem, Element urlElement);
    
    default void savingMainImage(final String imageName, String directory, final String imageUrl) {
        ImageDownloader.resizeImageScalrZalando(imageName, directory, imageUrl, 500);
    }
    
    default void createMassItem(String itemUrl, MassItem item) {
        Document doc = null;
        try {
            doc = Jsoup.connect(itemUrl).userAgent("Chrome").get();
        } catch (IOException e) {
            LOGGER.error("Error connection itemUrl:" + itemUrl);
            return;
        } 
        
        //1. extract the detailed images
        try {
            extractDetailImages(doc, item);
            LOGGER.info("extractDetailImages starts...");
        } catch (IOException e) {
            LOGGER.error("Error extracting detailImages:" + itemUrl);
        }
        
        //2. extract the price
        extractItemPrice(doc, item);
        LOGGER.info("extractItemPrice starts...");
        
        //3. extract the materials
        extractItemMaterials(doc, item);
        LOGGER.info("extractItemMaterials starts...");
        
        //4. Options: other properties
        setOptions(doc, item);
    };
    
    void extractDetailImages(Document doc, MassItem item) throws IOException;
    Elements extractDetailElements(Element body);
    String extractDetailImageUrl(Element detailImageElement);
    
    void extractItemPrice(Document doc, MassItem item);
    Elements extractPriceElements(Element body);
    void setOriginPrice(Elements elementsPrices, MassItem item);
    
    void extractItemMaterials(Document doc, MassItem item);
    Elements extractMaterialsElements(Element body);
    
    void setOptions(Document doc, MassItem item);
    
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
}
