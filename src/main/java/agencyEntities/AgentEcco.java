package agencyEntities;

import java.io.IOException;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import translator.TranslateGlossary;
import util.Formatter;

public class AgentEcco extends Agent implements ICosmeticAgent {
    static final Logger LOGGER = LogManager.getLogger(AgentEcco.class);

    public AgentEcco(String brandNameDe, String brandNameKor, String itemCategory, String dirMainImages, String dirFileUploader) {
        super(brandNameDe, brandNameKor, itemCategory, dirMainImages, dirFileUploader);
    }

    @Override
    public Elements getUrlElements(Element body) {
        Elements result = body.getElementsByClass("grid-view ga-productlist");
        Elements rawUnitElements = result.get(0).getElementsByClass("product-v2");
        return rawUnitElements;
    }

    @Override
    public String extractUrl(Element urlElement) {
        String url = "https://www.ecco-verde.de" + urlElement.getElementsByClass("product__title").get(0).getElementsByClass("productVariants").attr("href");
        return url;
    }

    @Override
    public String extractItemTitle(MassItem massItem, Element unitElement) {
        Element itemTitleElement =  unitElement.getElementsByClass("product__title").get(0).getElementsByTag("a").get(0);
        String itemTitleDe = itemTitleElement.text();
        
        Element itemVolumeElement =  unitElement.getElementsByClass("product__title").get(0);
        
        if(itemVolumeElement.getAllElements().hasClass("productVariants")) {
            String itemVolume = itemVolumeElement.getElementsByClass("productVariants").text();
            String formattedVolume = Formatter.formatWithoutComma(itemVolume);
            massItem.setItemVolume(formattedVolume);
        } else {
            massItem.setItemVolume("");
        }
        
        return itemTitleDe;
    }

    @Override
    public void downloadingMainImage(MassItem massItem, Element unitElement) {
        //String imageUrl = unitElement.getElementsByClass("product__image").get(0).attr("data-src");
        String imageUrl = unitElement.getElementsByClass("product__image").get(0).attr("src");
        savingMainImage(massItem.getMainImageName(), getDirMainImages(), imageUrl);
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
    public Elements extractPriceElements(Element body) {
        Elements priceElements = body.getElementsByClass("main-price");
        return priceElements;
    }

    @Override
    public void setOriginPrice(Elements elementsPrices, MassItem item) {
        int priceSize = elementsPrices.get(0).getElementsByClass("price").size();
        if(priceSize == 2) {
            String formatOrgPrice = Formatter.deleteNonDigits(elementsPrices.get(0).getElementsByClass("price").get(1).text());
            item.setItemPriceEuro(Double.valueOf(formatOrgPrice));
        } else {
            String formatOrgPrice = Formatter.deleteNonDigits(elementsPrices.get(0).getElementsByClass("price").text());
            item.setItemPriceEuro(Double.valueOf(formatOrgPrice));
        }
    }
    
//    @Override
//    public void extractItemDescription(Document doc, MassItem item) {
//        Element body = doc.body();
//        Elements elementDescription = extractDescriptionElements(body);
//        setDescription(elementDescription, item);
//    }

    @Override
    public Elements extractDescriptionElements(Element body) {
        Elements elements = body.getElementsByClass("product-description-content");
        return elements;
    }
    
    @Override
    public void setDescription(Elements elementDescription, MassItem item) {
        if(elementDescription.size() != 0) {
            String description = elementDescription.get(0).getElementsByTag("p").text();
            item.setItemDescription(description);
            
            String translatedDescription = "";
            try {
                translatedDescription = TranslateGlossary.translateTextWithGlossary(description);
            } catch (IOException e) {
                LOGGER.info("TranslateGlossary: " + e);
            }
            item.setItemDescriptionKor(translatedDescription);
            
        } else {
            LOGGER.error(item.getItemTitleDE() + ": no description");
            item.setItemDescription("");
        }
    }

    //@Override
//    public void extractItemIngredients(Document doc, MassItem item) {
//        Element body = doc.body();
//        Elements elementIngredients = extractIngredientsElements(body);
//        setIngredients(elementIngredients, item);
//    }

    @Override
    public Elements extractIngredientsElements(Element body) {
        Elements elements = body.getElementsByClass("product-ingredients inci");
        return elements;
    }

    @Override
    public void setIngredients(Elements elementIngedients, MassItem item) {
        if(elementIngedients.size() != 0) {
            String ingredients = elementIngedients.get(0).text();
            item.setItemIngredients(ingredients);
        } else {
            LOGGER.error(item.getItemTitleDE() + ": no ingredients");
            item.setItemIngredients("");
        }
    }
    
    @Override
    public void setCommonProperties(MassItem massItem) {
        massItem.setBrandNameDE(getBrandNameDe());
        massItem.setBrandNameKor(getBrandNameKor());
        massItem.setDirFileUploader(getDirFileUploader());
        massItem.setItemCategory(getItemCategory());
    }
}
