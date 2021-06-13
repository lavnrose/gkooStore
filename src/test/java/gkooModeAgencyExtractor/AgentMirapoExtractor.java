package gkooModeAgencyExtractor;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import gkooModeAgency.AgentMirapoStarter;
import util.Formatter;

public class AgentMirapoExtractor implements IAgentModeExtractor {

    //@Test
    @Override
    public void preprocessingTest() {
        File input = new File(AgentMirapoStarter.HTML_BRAND);
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
        Elements results = body.getElementsByTag("li");
        for (Element item : results) {
            String classname = item.className();
            if (classname.contains("prod-grid_item")) {
                Elements elements = new Elements();
                elements.add(item);
                //System.out.println(item);
                String rawItemUrl = item.getElementsByClass("prod-tile__link").get(0).attr("data-base64");
                //Element rawItemUrl = item;
                //System.out.println(rawItemUrl);

                //String originalInput = "dGVzdCBpbnB1dA==";
                byte[] result = Base64.getDecoder().decode(rawItemUrl);
                System.out.println("https://www.mirapodo.de" + new String(result));
                
                //System.out.println(item.getElementsByClass("prod-tile__img").get(0).getElementsByTag("img").get(0).attr("data-src"));
                String rawImageUrl = item.getElementsByClass("prod-tile__img").get(0).getElementsByTag("img").get(0).attr("data-src");
                
                String imageUrl = Formatter.splitAfterWord(rawImageUrl, ".jpg").get(0) + ".jpg";
                //System.out.println(imageUrl);
            }
          }
        //Elements rawUnitElements = result.getElementsByClass("product-tile-wrapper");
        Elements unitElements = new Elements();
        
        
    }

    @Override
    public void extractUrl(Element unitElement) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void extractMainImage(Element unitElement) {
        // TODO Auto-generated method stub
        
    }
    
    @Test
    @Override
    public void createMassItemTest() {
        //String itemUrl = "https://www.mirapodo.de/puma-smash-v2-l-sneakers-low-weiss-kombi-16163477.html";
        //String itemUrl = "https://www.mirapodo.de/gabor-klassische-sandalen-weiss-8348463.html";
        //String itemUrl = "https://www.mirapodo.de/adidas-performance-fluidstreet-laufschuhe-schwarz-14747217.html";
        String itemUrl = "https://www.mirapodo.de/gabor-sandalen-klassische-sandalen-blau-12218656.html";
        
        Document doc = null;
        try {
            doc = Jsoup.connect(itemUrl).userAgent("Chrome").get();
        } catch (IOException e) {
            return;
        }
        
        // 
        //extractItemMainImage(doc);
        
        //ok
        //extractItemTitle(doc);

        //ok
        //extractDetailImages(doc);
        
        //ok
        extractItemPrice(doc);
        
        //extractItemColor(doc);
        
        //extractItemMaterials(doc);
        
    }
    
    public void extractItemColor(Document doc) {
        Element body = doc.body();
        Elements prodInfoBox = body.getElementsByClass("prod-info__box");
        
        Elements colorElement = 
                prodInfoBox.get(0).getElementsByClass("grid-gap__m prod-info__color");
        String rawColor = colorElement.text();
        String itemColor = Formatter.splitAfterWord(rawColor, ": ").get(1);
        System.out.println(itemColor);
    }

    public void extractItemTitle(Document doc) {
        Element body = doc.body();
        
        Elements itemName = body.getElementsByClass("prod-info__header");
        Elements h1class = itemName.get(0).getElementsByTag("h1");
        System.out.println(h1class.text());
        //System.out.println(itemName);
        
        Element modelName = body.getElementById("prod-details");
        //System.out.println(modelName.getElementsByTag("p").get(0).text());
        String rawTitle = modelName.getElementsByTag("p").get(0).text();
        String itemNumber = Formatter.splitAfterWord(rawTitle, ": ").get(1);
        System.out.println(itemNumber);

    }

    @Override
    public void extractDetailImages(Document doc) {
        Element body = doc.body();
        Elements elementsDetaiImage = extractDetailElements(body);
    }

    @Override
    public Elements extractDetailElements(Element body) {
        //System.out.println(body);
        Element galleryElement = body.getElementsByClass("grid-gap prod-view").get(0);
        Elements detailImages = galleryElement.getElementsByClass("prod-slider-thumbnails__list");
        //System.out.println(detailImages.get(0));
        Elements detailImagesLinks = detailImages.get(0).getElementsByTag("img");
        //System.out.println(detailImagesLinks);
        for (Element detailImage : detailImagesLinks) {
            String rawDetailImage = detailImage.attr("data-src");
            String imageUrl = Formatter.splitAfterWord(rawDetailImage, ".jpg").get(0) + ".jpg";
            System.out.println(imageUrl);
        }
        return null;
    }

    @Override
    public String extractDetailImageUrl(Element detailImageElement) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void extractItemPrice(Document doc) {
        Element body = doc.body();
        Elements priceElements = body.getElementsByClass("prod-info__box");

        Elements priceElementRegular = 
                priceElements.get(0).getElementsByClass("grid-gap__m--first").get(0).getElementsByClass("prod-info__price-regular");
        
        System.out.println(priceElementRegular.size() == 1);
        if (priceElementRegular.size() == 1) {
            System.out.println(priceElementRegular.get(0).text());
            String regularPrice = priceElementRegular.get(0).text();
        } else {
            Elements priceOrigElements = 
                    priceElements.get(0).getElementsByClass("grid-gap__m--first").get(0).getElementsByTag("span");
            System.out.println(priceOrigElements.get(0).text());
            
            Element priceSaleElement = priceElements.get(0).getElementsByClass("grid-gap__m--first").get(0);
            //System.out.println(priceSaleElement);
            String reducedPrice = priceSaleElement.getElementsByClass("prod-info__price-reduced").text();
            System.out.println(reducedPrice);
            System.out.println(reducedPrice.contains("ab") ? "various price" : "fixed price");
        }
    }

    @Override
    public Elements extractPriceElements(Element body) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setOriginPrice(Elements elementsPrices) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void extractItemMaterials(Document doc) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Elements extractMaterialsElements(Element body) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getMaterials(Elements elementsMaterials) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void extractItemTitle(Element unitElement) {
        // TODO Auto-generated method stub
        
    }
}
