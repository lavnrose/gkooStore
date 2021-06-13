package agencyEntities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import gkooModeAgency.AgentMirapoStarter;
import util.Formatter;
import util.ImageDownloader;
import util.GrobalDefined.Gender;

public class AgentMirapo extends Agent implements IModeMirapo {
    static final Logger LOGGER = LogManager.getLogger(AgentBirken.class);

    public AgentMirapo(String brandNameDe, String brandNameKor,
            String homepageUrl, String dirMainImages, List<String> itemSizeList,
            List<String> itemSizePriceList, List<String> itemSizeStockist, Gender gender, int deliveryFee) {
        super(brandNameDe, brandNameKor, homepageUrl, dirMainImages, itemSizeList,
                itemSizePriceList, itemSizeStockist, gender, deliveryFee);
    }
    
    @Override
    public Elements getUrlElements(Element body) {
        Elements results = body.getElementsByTag("li");
        Elements urlElements = new Elements();
        for (Element item : results) {
            String classname = item.className();
            if (classname.contains("prod-grid_item")) {
                String rawItemUrl = item.getElementsByClass("prod-tile__link").get(0).attr("data-base64");
                if(!rawItemUrl.isBlank()) {
                    urlElements.add(item);
                }
            }
        }
        return urlElements;
    }

    @Override
    public String extractUrl(Element urlElement) {
        String rawItemUrl = urlElement.getElementsByClass("prod-tile__link").get(0).attr("data-base64");
        byte[] result = Base64.getDecoder().decode(rawItemUrl);
        return AgentMirapoStarter.BRAND_HOMEPAGE_URL + new String(result);
    }

    @Override
    public String extractItemTitle(Document doc, MassItem massItem) {
        Element body = doc.body();
        
        Elements itemName = body.getElementsByClass("prod-info__header");
        Elements h1class = itemName.get(0).getElementsByTag("h1");
        //translate to eng
        String preItemTitle = h1class.text();
        
        Element modelName = body.getElementById("prod-details");
        String rawTitle = modelName.getElementsByTag("p").get(0).text();
        String postItemNumber = Formatter.splitAfterWord(rawTitle, ": ").get(1);
        
        String itemTitle = preItemTitle + " " + postItemNumber;
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
    public void extractDetailImages(Document doc, MassItem item) throws IOException {
        Element body = doc.body();
        Optional<Elements> elementsDetaiImage = extractDetailElements(body);
        
        if(elementsDetaiImage.isPresent()) {
            List<String> formattedImageList = new ArrayList<>();
            
            for(int i=0; i<elementsDetaiImage.get().size(); i++) {
                Element detailImageElement = elementsDetaiImage.get().get(i);
                String imageUrl = extractDetailImageUrl(detailImageElement);
                //System.out.println(imageUrl);
                formattedImageList.add("https:" + imageUrl);
            }
            item.setItemDetailImages(formattedImageList);
        }
    }

    //@Override
    public Optional<Elements> extractDetailElements(Element body) {
        Element galleryElement = body.getElementsByClass("grid-gap prod-view").get(0);
        Elements detailImages = galleryElement.getElementsByClass("prod-slider-thumbnails__list");
        if(detailImages.isEmpty()) {
            return Optional.ofNullable(null);
        }
        Optional<Elements> detailImagesLinks = Optional.of(detailImages.get(0).getElementsByTag("img"));
        return detailImagesLinks;
    }

    @Override
    public String extractDetailImageUrl(Element detailImageElement) {
        String rawDetailImage = detailImageElement.attr("data-src");
        String imageUrl = Formatter.splitAfterWord(rawDetailImage, ".jpg").get(0) + ".jpg";
        return imageUrl;
    }

    @Override
    public void extractItemPrice(Document doc, MassItem item) {
        Element body = doc.body();
        
        Elements priceElements = extractPriceElements(body);
        Elements priceElementRegular = 
                priceElements.get(0).getElementsByClass("grid-gap__m--first").get(0).getElementsByClass("prod-info__price-regular");
        
        if (priceElementRegular.size() == 1) {
            setOriginPrice(priceElementRegular, item);
        } else {
            setSalesPrice(priceElements, item);
        }
    }

    @Override
    public Elements extractPriceElements(Element body) {
        Elements priceElements = body.getElementsByClass("prod-info__box");
        return priceElements;
    }

    @Override
    public void setOriginPrice(Elements priceElementRegular, MassItem item) {
        System.out.println(priceElementRegular.get(0).text());
        String regularPrice = priceElementRegular.get(0).text();
        String formatOrgPrice = Formatter.deleteNonDigits(regularPrice);
        item.setItemPriceEuro(Double.valueOf(formatOrgPrice));
    }
    
    public void setSalesPrice(Elements priceElements, MassItem item) {
        Elements priceOrigElements = 
                priceElements.get(0).getElementsByClass("grid-gap__m--first").get(0).getElementsByTag("span");
        
        System.out.println("origin price:" + priceOrigElements.get(0).text());
        System.out.println(item.getItemUrl());
        
        String originPrice = priceOrigElements.get(0).text();
        
        Element priceSaleElement = priceElements.get(0).getElementsByClass("grid-gap__m--first").get(0);
        String reducedPrice = priceSaleElement.getElementsByClass("prod-info__price-reduced").text();
        System.out.println(reducedPrice);
        System.out.println(isPriceVariable.test(reducedPrice) ? "various price" : "fixed price");
        
        item.setItemSale(true);
        item.setItemPriceEuro(Double.valueOf(Formatter.deleteNonDigits(originPrice)));
        item.setItemSalePriceEuro(Double.valueOf(Formatter.deleteNonDigits(reducedPrice)));
        
        item.setPriceVariable(isPriceVariable.test(reducedPrice));  
    }
    
    public Predicate<String> isPriceVariable = price -> price.contains("ab");

    @Override
    public void extractItemMaterials(Document doc, MassItem item) {
        // TODO Auto-generated method stub
    }

    @Override
    public Elements extractMaterialsElements(Element body) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setOptions(Document doc, MassItem item) {
        
    }

    @Override
    public void downloadingMainImage(Document doc, MassItem massItem) {
        Element body = doc.body();
        Optional<Elements> elementsDetaiImage = extractDetailElements(body);
        
        if(elementsDetaiImage.isPresent()) {
            Element detailImageElement = elementsDetaiImage.get().get(0);
            String rawiImageUrl = extractDetailImageUrl(detailImageElement);
            String imageUrl = "https:" + rawiImageUrl;
            ImageDownloader.runWithResizing(massItem.getMainImageName(), getDirMainImages(), imageUrl, 500, 500);
        }
    }
}
