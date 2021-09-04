package agencyEntities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.Formatter;
import util.GrobalDefined.Gender;

public class AgentBirken extends Agent implements IModeAgent {
    static final Logger LOGGER = LogManager.getLogger(AgentBirken.class);

    public AgentBirken(String brandNameDe, String brandNameKor,
            String homepageUrl, String dirMainImages, List<String> itemSizeList,
            List<String> itemSizePriceList, List<String> itemSizeStockist, Gender gender, int deliveryFee) {
        super(brandNameDe, brandNameKor, homepageUrl, dirMainImages, itemSizeList,
                itemSizePriceList, itemSizeStockist, gender, deliveryFee);
    }

    @Override
    public Elements getUrlElements(Element body) {
        Element result = body.getElementsByClass("xlt-searchresults search-result-content").get(0);
        Elements unitElements = result.getElementsByClass("product-tile-wrapper");
        return unitElements;
    }

    @Override
    public String extractUrl(Element urlElement) {
        String url = urlElement.getElementsByTag("a").attr("href");
        return url;
    }

    @Override
    public String extractItemTitle(MassItem massItem, Element unitElement) {
        String modelName = unitElement.getElementsByClass("product-modelname").get(0).text();
        String shortName = unitElement.getElementsByClass("product-shortname").get(0).text();
        String colorName = unitElement.getElementsByClass("product-colorname").get(0).text();
        
        String itemTitle = modelName + " " + shortName + " " + colorName;
        
        List<String> itemColors = new ArrayList<>();
        itemColors.add(colorName);
        massItem.setItemColors(itemColors);
        
        return itemTitle;
    }

    @Override
    public void setCommonProperties(MassItem massItem) {
        massItem.setBrandNameDE(getBrandNameDe());
        massItem.setBrandNameKor(getBrandNameKor());
        massItem.setBrandHomepageUrl(getHomepageUrl());
        massItem.setItemSizes(getItemSizeList());
        massItem.setItemSizesPrice(getItemSizePriceList());
        massItem.setItemSizesStock(getItemSizeStockist());
        massItem.setGender(getGender());
        massItem.setModeDeiveryFee(getDeliveryFee());
    }

    @Override
    public void downloadingMainImage(MassItem massItem, Element urlElement) {
        String rawImageUrl = urlElement.getElementsByClass("product-image").get(0).getElementsByTag("img").attr("data-src");
        String imageUrl = Formatter.splitAfterWord(rawImageUrl, ".jpg").get(0) + ".jpg";
        savingMainImage(massItem.getMainImageName(), getDirMainImages(), imageUrl);
    }

    @Override
    public void extractDetailImages(Document doc, MassItem item) throws IOException {
        Element body = doc.body();
        Elements elementsDetaiImage = extractDetailElements(body);
        
        if(elementsDetaiImage != null) {
            List<String> formattedImageList = new ArrayList<>();
            
            for(int i=0; i<elementsDetaiImage.size(); i++) {
                Element detailImageElement = elementsDetaiImage.get(i);
                String imageUrl = extractDetailImageUrl(detailImageElement);
                //System.out.println(imageUrl);
                formattedImageList.add(imageUrl);
            }
            item.setItemDetailImages(formattedImageList);
        }
    }
    
    @Override
    public Elements extractDetailElements(Element body) {
        Elements galleryElements = null;
        
        //if(body.getElementsByClass("product-image-slick").size() != 0) {
        //    galleryElements = body.getElementsByClass("product-image-slick").get(0).getElementsByClass("grid-tile thumb");
        //}
        
        galleryElements = body.getElementsByClass("thumbnail-container-slider").get(0).getElementsByClass("product-image-slick").get(0).getElementsByClass("grid-tile thumb");

        return galleryElements;
    }
    
    @Override
    public String extractDetailImageUrl(Element detailImageElement) {
        //String rawDeTailImageUrl = detailImageElement.getElementsByTag("img").attr("src");
        //String imageUrl = Formatter.splitAfterWord(rawDeTailImageUrl, ".jpg").get(0) + ".jpg";
        
        String rawDeTailImageUrl = detailImageElement.getElementsByTag("a").attr("href");
        String imageUrl = "https://www.birkenstock.com" + rawDeTailImageUrl;
        return imageUrl;
    }

    @Override
    public void extractItemPrice(Document doc, MassItem item) {
        Objects.requireNonNull(doc);
        Element body = doc.body();
        Elements elementsPrices = extractPriceElements(body);

        if(elementsPrices.size() == 0) {
            return;
        }
        
        setOriginPrice(elementsPrices, item);
    }
    
    @Override
    public void setOriginPrice(Elements elementsPrices, MassItem item) {
        String formatOrgPrice = Formatter.deleteNonDigits(elementsPrices.get(0).getElementsByClass("price-standard").text());
        item.setItemPriceEuro(Double.valueOf(formatOrgPrice));
    }
    
    @Override
    public Elements extractPriceElements(Element body) {
        Elements priceElements = body.getElementsByClass("product-name-and-price");
        return priceElements;
    }

    @Override
    public void extractItemMaterials(Document doc, MassItem item) {
    }
    
    @Override
    public Elements extractMaterialsElements(Element body) {
        return null;
    }

    @Override
    public void setOptions(Document doc, MassItem item) {
        Element body = doc.body();
        List<String> itemOptions = new ArrayList<>();
        Elements materialsElements = body.getElementsByClass("attribute width");
        if(materialsElements.size() != 0) {
            int artWidth = materialsElements.get(0).getElementsByClass("value").get(0).getElementsByClass("swatches width").get(0).getElementsByTag("li").size();
            if (artWidth == 1) {
                itemOptions.add("regular");   
                item.setItemOptions(itemOptions); 
            } else {
                itemOptions.add("narrow");
                item.setItemOptions(itemOptions); 
            }
        } else {
            LOGGER.debug("Error setOption: " + item.getItemUrl());
        }
    }
}
