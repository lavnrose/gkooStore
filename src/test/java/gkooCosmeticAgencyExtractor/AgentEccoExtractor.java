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

public class AgentEccoExtractor implements IAgentCosmeticExtractor {

    //@Test
    @Override
    public void preprocessingTest() {
        File input = new File(AgentEccoStarter.HTML_BRAND);
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
        Elements result = body.getElementsByClass("grid-view ga-productlist");
        //System.out.println(result);
        Elements rawUnitElements = result.get(0).getElementsByClass("product-v2");
        //System.out.println(rawUnitElements.get(0));

        Elements unitElements = new Elements();
        
        for (int i=0; i<rawUnitElements.size(); i++) {
            Element unit = rawUnitElements.get(i);
            unitElements.add(unit);
        }
        
        int number = 0;
        //extractUrl(unitElements.get(number));
//        for (int i=0; i<unitElements.size(); i++) {
//            extractUrl(unitElements.get(i));
//        }
        
        extractItemTitle(unitElements.get(number));
//        for (int i=0; i<unitElements.size(); i++) {
//            extractItemTitle(unitElements.get(i));
//        }
        
        //extractMainImage(unitElements.get(number));
        for (int i=0; i<unitElements.size(); i++) {
            extractMainImage(unitElements.get(i));
        }
    }

    @Override
    public void extractUrl(Element unitElement) {
        String url = "https://www.ecco-verde.de" + unitElement.getElementsByClass("product__title").get(0).getElementsByTag("a").get(0).attr("href");
        //System.out.println(url);
    }

    @Override
    public void extractItemTitle(Element unitElement) {
        //String itemTitle =  unitElement.getElementsByClass("product__title").get(0).getElementsByTag("a").get(0).text();
        Element itemTitle =  unitElement.getElementsByClass("product__title").get(0).getElementsByTag("a").get(0);
        //System.out.println(itemTitle);
        Element itemVolume =  unitElement.getElementsByClass("product__title").get(0);
        
        System.out.println(itemVolume);
        System.out.println(itemVolume.getAllElements().hasClass("productVariants"));
        if(itemVolume.getAllElements().hasClass("productVariants")) {
            System.out.println(itemVolume.getElementsByClass("productVariants").text());
        }
    }

    @Override
    public void extractMainImage(Element unitElement) {
        System.out.println(unitElement.getElementsByClass("product__image").get(0).attr("src"));
    }

    @Test
    @Override
    public void createMassItemTest() {
        String itemUrl = "https://www.ecco-verde.de/weleda/hautcreme-skin-food";
        Document doc = null;
        try {
            doc = Jsoup.connect(itemUrl).userAgent("Chrome").get();
        } catch (IOException e) {
            return;
        }
                
        extractItemPrice(doc);
        
        //extractItemDescription(doc);
        
        extractItemIngredients(doc);
    }

    @Override
    public void extractItemPrice(Document doc) {
        Element body = doc.body();
        Elements elementsPrices = extractPriceElements(body);
        
    }

    @Override
    public Elements extractPriceElements(Element body) {
        Elements priceElements = body.getElementsByClass("main-price");
        
        boolean saledPrice = priceElements.get(0).getElementsByClass("price").get(0).hasClass("price instead-price");
        System.out.println(priceElements.get(0).getElementsByClass("price").size());
        System.out.println(priceElements.get(0).getElementsByClass("price").get(1).text());
        
        //not formatted euro, comma
        String price = priceElements.get(0).getElementsByClass("price").text();
        return null;
    }

    @Override
    public void setOriginPrice(Elements elementsPrices) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void extractItemIngredients(Document doc) {
        Element body = doc.body();
        Elements elementsMaterials = extractIngredientsElements(body);
    }

    @Override
    public Elements extractIngredientsElements(Element body) {
        Elements elements = body.getElementsByClass("product-ingredients inci");
        System.out.println(elements.get(0).text());
        String ingredients = elements.get(0).text();
        return null;
    }
    
    @Override
    public void extractItemDescription(Document doc) {
        Element body = doc.body();
        Elements elementsMaterials = extractDescriptionElements(body);
    }

    @Override
    public Elements extractDescriptionElements(Element body) {
        Elements elements = body.getElementsByClass("product-description-content");
        Element orgDescription = elements.get(0);
        System.out.println(orgDescription.getElementsByTag("p").text());
        
        String description = orgDescription.getElementsByTag("p").text();
        return null;
    }

    @Override
    public void setDescription(Elements elementDescription, MassItem item) {
        // TODO Auto-generated method stub
        
    }

}
