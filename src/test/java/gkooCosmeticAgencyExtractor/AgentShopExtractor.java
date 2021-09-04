package gkooCosmeticAgencyExtractor;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import agencyEntities.MassItem;
import gkooCosmeticAgency.AgentEccoStarter;
import gkooCosmeticAgency.AgentShopStarter;
import util.Formatter;

public class AgentShopExtractor implements IAgentCosmeticExtractor {
    //@Test
    @Override
    public void preprocessingTest() {
        File input = new File(AgentShopStarter.HTML_BRAND);
        Document document = null;
        try {
            document = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
        }
        Element body = document.body();
        getUnitElements(body);
        
    }

    @Override
    public void getUnitElements(Element body) {
        Elements result = body.getElementsByClass("o-SearchProductListItem");
        //System.out.println(result);
        
        Elements unitElements = new Elements();
        
        for (int i=0; i<result.size(); i++) {
            Element unit = result.get(i);
            unitElements.add(unit);
        }
        
        int number = 0;
        extractUrl(unitElements.get(number));
//        for (int i=0; i<unitElements.size(); i++) {
//            extractUrl(unitElements.get(i));
//        }
        
        extractItemTitle(unitElements.get(number));
//        for (int i=0; i<unitElements.size(); i++) {
//            extractItemTitle(unitElements.get(i));
//        }
        
//        extractMainImage(unitElements.get(number));
//        for (int i=0; i<unitElements.size(); i++) {
//            extractMainImage(unitElements.get(i));
//        }
    }


    @Override
    public void extractUrl(Element unitElement) {
        String rawUrl ="https://www.shop-apotheke.com" + unitElement.attr("href");
        String url = Formatter.splitAfterWord(rawUrl, "htm").get(0);
        //System.out.println(url+"htm");
    }

    @Override
    public void extractItemTitle(Element unitElement) {
        //String itemTitle = unitElement.getElementsByClass("o-SearchProductListItem__content").get(0).getElementsByTag("p").get(0).text();
//        Elements itemTitle = unitElement.getElementsByClass("o-SearchProductListItem__content");
        String itemVolume = unitElement.getElementsByClass("o-SearchProductListItem__info-list").get(0).child(0).text();
        
        String result = Formatter.splitAfterWord(itemVolume, "\\|").get(0);
        
        System.out.println(result);
    }

    @Override
    public void extractMainImage(Element unitElement) {
        System.out.println(unitElement.getElementsByTag("img").attr("src"));
        String mainImageUrl = unitElement.getElementsByTag("img").attr("src");
        
    }

    @Test
    @Override
    public void createMassItemTest() {
        //https://www.shop-apotheke.com/beauty/16620590/eubos-sensitive-hand-repair-schutz.htm
        //String itemUrl = "https://www.shop-apotheke.com/beauty/16620590/eubos-sensitive-hand-repair-schutz.htm";
        String itemUrl = "https://www.shop-apotheke.com/beauty/4891958/eubos-creme-intensivpflege.htm";
        Document doc = null;
        try {
            doc = Jsoup.connect(itemUrl).userAgent("Chrome").get();
        } catch (IOException e) {
            return;
        }
        //extractItemTitle(doc);

        //extractItemVolume(doc);

        extractItemMainImage(doc);
        
        //extractItemPrice(doc);
        
        //extractItemDescription(doc);
        
        //extractItemIngredients(doc);
    }
    
    public void extractItemTitle(Document doc) {
        Element body = doc.body();
        Elements itemTitleElements = body.getElementsByClass("l-grid__item");
        //System.out.println(itemTitleElements.get(0).getElementsByTag("h1").text());
        String rawTitle = itemTitleElements.get(0).getElementsByTag("h1").text();
        System.out.println(rawTitle.replaceAll("®", ""));
    }
    
    public void extractItemVolume(Document doc) {
        Element body = doc.body();
        Elements itemVolumeElements = body.getElementsByClass("m-ProductVariant__checkbox");
        System.out.println(itemVolumeElements.get(0).getElementsByClass("o-ProductVariant--units").text());
    }
    
    public void extractItemMainImage(Document doc) {
        Element body = doc.body();
        Elements itemMainImageElements = body.getElementsByClass("o-ProductImageGallery__main-image");
        //System.out.println(itemMainImageElements.get(0).getElementsByTag("img").attr("src"));
        System.out.println(itemMainImageElements.get(0).getElementsByTag("li").get(0).getElementsByTag("a").attr("href"));
    }

    @Override
    public void extractItemPrice(Document doc) {
        Element body = doc.body();
        Elements elementsPrices = extractPriceElements(body);
    }

    @Override
    public Elements extractPriceElements(Element body) {
        Elements priceElements = body.getElementsByClass("m-ProductVariant__checkbox");

        //String itemVolume = priceElements.get(0).getElementsByClass("o-ProductVariant--units").text();
        //System.out.println(itemVolume);
        
        Elements oldpriceElements = body.getElementsByClass("m-ProductVariant__label");
        Element priceBlock = oldpriceElements.get(0).child(2);
        //System.out.println(priceBlock);
        Element oldPrice = priceBlock.child(0);
        System.out.println(oldPrice.getElementsByClass("a-Price").text());
        Element aPrice = priceBlock.child(1);
        System.out.println(aPrice.text());
        
//        String price = oldpriceElements.get(0).getElementsByClass("a-PriceOld").get(0).text();
//        System.out.println(Formatter.deleteNonDigits(price));
//        String aPrice = oldpriceElements.get(0).getElementsByClass("a-Price").get(0).text();
//        System.out.println(Formatter.deleteNonDigits(aPrice));
        
        return null;
    }

    @Override
    public void setOriginPrice(Elements elementsPrices) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void extractItemDescription(Document doc) {
        Element body = doc.body();
        Elements elementsMaterials = extractDescriptionElements(body);
        
    }

    @Override
    public Elements extractDescriptionElements(Element body) {
        Elements elements = body.getElementsByClass("o-ProductDescriptions");
        
        boolean emptyImage = elements.get(0).getElementsByClass("o-ProductAdditionalInformation").get(1).getElementsByTag("img").isEmpty();
        if(emptyImage) {
            System.out.println(elements.get(0).getElementsByClass("o-ProductAdditionalInformation").get(3).getElementsByTag("p").text());
        } else {
            System.out.println(elements.get(0).getElementsByClass("o-ProductAdditionalInformation").get(4).getElementsByTag("p").text());
        }
        
        //System.out.println(Formatter.formatWithoutCommaBar(elements.get(0).getElementsByClass("o-ProductAdditionalInformation").get(3).getElementsByTag("p").text()));
        //System.out.println((elements.get(0).getElementsByClass("o-ProductAdditionalInformation").get(4).getElementsByTag("p").text()));
        //System.out.println((elements.get(0).getElementsByClass("o-ProductAdditionalInformation").get(1).getElementsByTag("img").isEmpty()));
        return null;
    }

    @Override
    public void setDescription(Elements elementDescription, MassItem item) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void extractItemIngredients(Document doc) {
        Element body = doc.body();
        Elements elementsMaterials = extractIngredientsElements(body);
    }

    @Override
    public Elements extractIngredientsElements(Element body) {
        //Elements elements = body.getElementsByClass("o-ProductAdditionalInformation");
        Elements elements = body.getElementsByClass("o-ProductDescriptions");
        
        boolean emptyImage = elements.get(0).getElementsByClass("o-ProductAdditionalInformation").get(1).getElementsByTag("img").isEmpty();
        if(emptyImage) {
            String igred_1 = elements.get(0).getElementsByClass("o-ProductAdditionalInformation").get(1).text();
            String igred_2 = elements.get(0).getElementsByClass("o-ProductAdditionalInformation").get(2).text();
            System.out.println(Formatter.formatWithoutComma(igred_1 + igred_2));
        } else {
            String igred_1 = elements.get(0).getElementsByClass("o-ProductAdditionalInformation").get(2).text();
            String igred_2 = elements.get(0).getElementsByClass("o-ProductAdditionalInformation").get(3).text();
            System.out.println(Formatter.formatWithoutComma(igred_1 + igred_2));
        }
        //String ingredients = elements.get(0).text();
        return null;
    }

}
