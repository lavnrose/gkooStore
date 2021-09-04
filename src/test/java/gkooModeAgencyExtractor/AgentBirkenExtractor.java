package gkooModeAgencyExtractor;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import gkooModeAgency.AgentBirkenStarter;
import util.Formatter;

public class AgentBirkenExtractor implements IAgentModeExtractor {

    //@Test
    @Override
    public void preprocessingTest() {
        File input = new File(AgentBirkenStarter.HTML_BRAND);
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
        Element result = body.getElementsByClass("xlt-searchresults search-result-content").get(0);
        Elements rawUnitElements = result.getElementsByClass("product-tile-wrapper");
        //System.out.println(rawUnitElements.size());
        Elements unitElements = new Elements();
        
        for (int i=0; i<rawUnitElements.size(); i++) {
            Element unit = rawUnitElements.get(i);
            unitElements.add(unit);
        }
        
        int number = 0;
        //extractUrl(unitElements.get(number));
        for (int i=0; i<unitElements.size(); i++) {
            //System.out.println(i);
            extractUrl(unitElements.get(i));
        }
        
        //extractItemTitle(unitElements.get(number));
        for (int i=0; i<unitElements.size(); i++) {
            //System.out.println(i);
            extractItemTitle(unitElements.get(i));
        }
        
        //mainImage
        //extractMainImage(unitElements.get(number));
        for (int i=0; i<unitElements.size(); i++) {
        System.out.println(i);
            extractMainImage(unitElements.get(i));
        }
    }

    @Override
    public void extractUrl(Element unitElement) {
        //System.out.println(unitElement.getElementsByTag("a").attr("href"));
        String url = unitElement.getElementsByTag("a").attr("href");
    }

    @Override
    public void extractItemTitle(Element unitElement) {
        String modelName = unitElement.getElementsByClass("product-modelname").text();
        String shortName = unitElement.getElementsByClass("product-shortname").text();
        String colorName = unitElement.getElementsByClass("product-colorname").text();
        
        //System.out.println(modelName + " " + shortName + " " + colorName);
        String itemTitle = modelName + " " + shortName + " " + colorName;
    }

    @Override
    public void extractMainImage(Element unitElement) {
        String rawImageUrl = unitElement.getElementsByClass("product-image").get(0).getElementsByTag("img").attr("data-src");
        String imageUrl = Formatter.splitAfterWord(rawImageUrl, ".jpg").get(0) + ".jpg";
        System.out.println(imageUrl);
    }

    @Test
    @Override
    public void createMassItemTest() {
        String itemUrl = "https://www.birkenstock.com/de-en/milano-birko-flor/milano-core-birkoflor-0-eva-u_79.html";
        Document doc = null;
        try {
            doc = Jsoup.connect(itemUrl).userAgent("Chrome").get();
        } catch (IOException e) {
            return;
        }
        
        extractDetailImages(doc);
        
        //extractItemPrice(doc);
        
        //extractItemMaterials(doc);
    }

    @Override
    public void extractDetailImages(Document doc) {
        Element body = doc.body();
        Elements elementsDetaiImage = extractDetailElements(body);
        for(int i=0; i<elementsDetaiImage.size(); i++) {
            Element detailImageElement = elementsDetaiImage.get(i);
            String imageUrl = extractDetailImageUrl(detailImageElement);
            System.out.println("imageUrl: " + imageUrl);
        }
    }

    @Override
    public Elements extractDetailElements(Element body) {
        //Elements galleryElements = body.getElementsByClass("product-image-slick").get(0).getElementsByClass("grid-tile thumb");
        Elements galleryElements = body.getElementsByClass("thumbnail-container-slider").get(0).getElementsByClass("product-image-slick").get(0).getElementsByClass("grid-tile thumb");
        System.out.println(galleryElements);
        return galleryElements;
    }

    @Override
    public String extractDetailImageUrl(Element detailImageElement) {
        //String rawDeTailImageUrl = detailImageElement.getElementsByTag("img").attr("src");
        String rawDeTailImageUrl = detailImageElement.getElementsByTag("a").attr("href");
        //String imageUrl = Formatter.splitAfterWord(rawDeTailImageUrl, ".jpg").get(0) + ".jpg";
        String imageUrl = "https://www.birkenstock.com" + rawDeTailImageUrl;
        return imageUrl;
    }

    @Override
    public void extractItemPrice(Document doc) {
        Element body = doc.body();
        Elements elementsPrices = extractPriceElements(body);
    }

    @Override
    public Elements extractPriceElements(Element body) {
        Elements priceElements = body.getElementsByClass("product-name-and-price");
        //System.out.println(priceElements.get(0).getElementsByClass("price-standard").text());
        
        return null;
    }

    @Override
    public void setOriginPrice(Elements elementsPrices) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void extractItemMaterials(Document doc) {
        Element body = doc.body();
        Elements elementsMaterials = extractMaterialsElements(body);
        
    }

    @Override
    public Elements extractMaterialsElements(Element body) {
        Elements materialsElements = body.getElementsByClass("attribute width");
        System.out.println(materialsElements.get(0).getElementsByClass("value").get(0).getElementsByClass("swatches width").get(0).getElementsByTag("li").size());
        return null;
    }

    @Override
    public String getMaterials(Elements elementsMaterials) {
        // TODO Auto-generated method stub
        return null;
    }

}
