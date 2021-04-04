package agencyEntities;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import translator.TranslateGlossary;
import util.Formatter;

public class AgentShopApo extends Agent implements ICosmeticAgent {
    static final Logger LOGGER = LogManager.getLogger(AgentShopApo.class);
    public Elements results = new Elements();
    
    public AgentShopApo(String brandNameDe, String brandNameKor, String itemCategory, String dirMainImages, String dirFileUploader) {
        super(brandNameDe, brandNameKor, itemCategory, dirMainImages, dirFileUploader);
    }
    
    public void addItemUrl(String url) {
        results.add(new Element(url));
    }
    
    @Override
    public Elements getUrlElements(Element body) {
        //results.add(new Element("https://www.shop-apotheke.com/beauty/5967445/eubos-kinder-haut-ruhe-gesichtscreme.htm"));
        //System.out.println(results.get(0).tag());
        return results;
    }

    @Override
    public String extractUrl(Element urlElement) {
        String url = urlElement.tag().toString();
        return url;
    }

    @Override
    public String extractItemTitle(MassItem massItem, Element element) {
        String itemUrl = element.tag().toString();
        Document doc = null;
        try {
            doc = Jsoup.connect(itemUrl).userAgent("Chrome").get();
        } catch (IOException e) {
            LOGGER.error("Error connection itemUrl:" + itemUrl);
        } 
        Element body = doc.body();
        
        Elements itemTitleElements = body.getElementsByClass("l-grid__item");
        String rawTitle1 = itemTitleElements.get(0).getElementsByTag("h1").text();
        String rawTitle2 = rawTitle1.replaceAll("®", "");
        String itemTitle = rawTitle2.replaceAll("/", "");
        
        Elements itemVolumeElements = body.getElementsByClass("m-ProductVariant__checkbox");
        massItem.setItemVolume(itemVolumeElements.get(0).getElementsByClass("o-ProductVariant--units").text());
        
        return itemTitle;
    }

    @Override
    public void setCommonProperties(MassItem massItem) {
        massItem.setBrandNameDE(getBrandNameDe());
        massItem.setBrandNameKor(getBrandNameKor());
        massItem.setDirFileUploader(getDirFileUploader());
        massItem.setItemCategory(getItemCategory());
    }

    @Override
    public void downloadingMainImage(MassItem massItem, Element urlElement) {
        String itemUrl = urlElement.tag().toString();
        Document doc = null;
        try {
            doc = Jsoup.connect(itemUrl).userAgent("Chrome").get();
        } catch (IOException e) {
            LOGGER.error("Error connection itemUrl:" + itemUrl);
        } 
        Element body = doc.body();
        Elements itemMainImageElements = body.getElementsByClass("o-ProductImageGallery__list");
        String mainImageUrl = itemMainImageElements.get(0).getElementsByTag("img").attr("src");
        savingMainImage(massItem.getMainImageName(), getDirMainImages(), mainImageUrl);
    }

    @Override
    public void extractItemPrice(Document doc, MassItem item) {
        Element body = doc.body();
        Elements elementsPrices = extractPriceElements(body);
        
        if(elementsPrices.size() == 0) {
            return;
        }
        
        setOriginPrice(elementsPrices, item);
        setSalePrice(elementsPrices, item);
    }

    @Override
    public Elements extractPriceElements(Element body) {
        Elements priceElements = body.getElementsByClass("m-ProductVariant__checkbox");

        String itemVolume = priceElements.get(0).getElementsByClass("o-ProductVariant--units").text();
        System.out.println(itemVolume);
        
        Elements oldpriceElements = body.getElementsByClass("m-ProductVariant__label");
        return oldpriceElements;
    }

    @Override
    public void setOriginPrice(Elements elementsPrices, MassItem item) {
        Element priceBlock = elementsPrices.get(0).child(2);
        String orgPrice = priceBlock.child(0).getElementsByClass("a-Price").text();
        String formatOrgPrice = Formatter.deleteNonDigits(orgPrice);
        item.setItemPriceEuro(Double.valueOf(formatOrgPrice));

        //String orgPrice = elementsPrices.get(0).getElementsByClass("a-PriceOld").get(0).text();
        //String formatOrgPrice = Formatter.deleteNonDigits(orgPrice);
    }
    
    public void setSalePrice(Elements elementsPrices, MassItem item) {
        Element priceBlock = elementsPrices.get(0).child(2);
        boolean hasSalePrice = priceBlock.child(1).hasText();
        String salePrice = priceBlock.child(1).text();
        String formatSalePrice = Formatter.deleteNonDigits(salePrice);
        if(hasSalePrice) {
            item.setItemSale(true);
            item.setItemSalePriceEuro(Double.valueOf(formatSalePrice));
        } else {
            item.setItemSale(false);
        }
    }

    @Override
    public Elements extractDescriptionElements(Element body) {
        Elements elements = body.getElementsByClass("o-ProductDescriptions");
        return elements;
    }

    @Override
    public void setDescription(Elements elementDescription, MassItem item) {
        boolean emptyImage = elementDescription.get(0).getElementsByClass("o-ProductAdditionalInformation").get(1).getElementsByTag("img").isEmpty();
        String description = "";
        if(emptyImage) {
            int size = elementDescription.get(0).getElementsByClass("o-ProductAdditionalInformation").size();
            if (size != 2) {
                description = elementDescription.get(0).getElementsByClass("o-ProductAdditionalInformation").get(3).getElementsByTag("p").text();
            } else {
                description = elementDescription.get(0).getElementsByClass("o-ProductAdditionalInformation").get(1).getElementsByTag("p").text();
            }
        } else {
            description = elementDescription.get(0).getElementsByClass("o-ProductAdditionalInformation").get(4).getElementsByTag("p").text();
        }
        
        String translatedDescription = "";
        try {
            translatedDescription = TranslateGlossary.translateTextWithGlossary(description);
        } catch (IOException e) {
            LOGGER.info("TranslateGlossary: " + e);
        }
        item.setItemDescriptionKor(translatedDescription);
    }

    @Override
    public Elements extractIngredientsElements(Element body) {
        Elements elements = body.getElementsByClass("o-ProductDescriptions");
        return elements;
    }

    @Override
    public void setIngredients(Elements elementIngedients, MassItem item) {
        boolean emptyImage = elementIngedients.get(0).getElementsByClass("o-ProductAdditionalInformation").get(1).getElementsByTag("img").isEmpty();
        if(emptyImage) {
            int size = elementIngedients.get(0).getElementsByClass("o-ProductAdditionalInformation").size();
            if (size != 2) {
                String igred_1 = elementIngedients.get(0).getElementsByClass("o-ProductAdditionalInformation").get(1).text();
                String igred_2 = elementIngedients.get(0).getElementsByClass("o-ProductAdditionalInformation").get(2).text();
                item.setItemIngredients(Formatter.formatWithoutComma(igred_1 + igred_2));
            } else {
                //new
                String igred_1 = elementIngedients.get(0).getElementsByClass("o-ProductAdditionalInformation").get(0).text();
                String igred_2 = elementIngedients.get(0).getElementsByClass("o-ProductAdditionalInformation").get(1).text();                
                item.setItemIngredients(Formatter.formatWithoutComma(igred_1 + igred_2));
            }
            
            
        } else {
            String igred_1 = elementIngedients.get(0).getElementsByClass("o-ProductAdditionalInformation").get(2).text();
            String igred_2 = elementIngedients.get(0).getElementsByClass("o-ProductAdditionalInformation").get(3).text();
            item.setItemIngredients(Formatter.formatWithoutComma(igred_1 + igred_2));
        }
    }
}
