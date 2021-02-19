package gkooModeAgencyExtractor;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import gkooModeAgency.AgentSandro;
import util.Formatter;

public class AgentSandroExtractor implements IAgentModeExtractor {
    //@Test
    @Override
    public void preprocessingTest() {
        File input = new File(AgentSandro.HTML_BRAND);
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
        Element result = body.getElementsByClass("search-result-content").get(0);
        Element element = result.child(0);
        Elements rawUnitElements = element.getElementsByClass("product-tile");
        Elements unitElements = new Elements();
        
        for (int i=0; i<rawUnitElements.size(); i++) {
            Element unit = rawUnitElements.get(i);
            unitElements.add(unit);
        }
        
        //System.out.println(unitElements.size());
        int number = 0;
        for (int i=0; i<unitElements.size(); i++) {
            extractUrl(unitElements.get(i));
        }
        
        //itemTitle
        for (int i=0; i<unitElements.size(); i++) {
            extractItemTitle(unitElements.get(i));
        }
        
        //mainImage
        for (int i=0; i<unitElements.size(); i++) {
            extractMainImage(unitElements.get(i));
        }
    }

    @Override
    public void extractUrl(Element urlElement) {
        Elements urlElements = urlElement.getElementsByClass("product-image ");
        String url = urlElements.get(0).getElementsByClass("thumb-link").get(0).attr("href");
        //System.out.println(url);
    }

    @Override
    public void extractItemTitle(Element element) {
        String itemTitle = element.getElementsByClass("product-name").get(0).text();
        //System.out.println(element.getElementsByClass("product-name").get(0).text());
        
    }

    @Override
    public void extractMainImage(Element element) {
        //System.out.println(element);
        Elements productImageElements = element.getElementsByClass("product-image ").get(0).getElementsByClass("thumb-link");
        String imageUrls = productImageElements.get(0).getElementsByTag("img").attr("data-src");
        String rawImageUrl = Formatter.splitAfterWord(imageUrls, ".jpg").get(0) + ".jpg";
        //System.out.println(rawImageUrl);
    }

    @Test
    @Override
    public void createMassItemTest() {
        String itemUrl = "https://eu.sandro-paris.com/en/woman/cardi-coats/reversible-oversized-cardigan/SFPCA00278.html?dwvar_SFPCA00278_color=B168#start=1";
        Document doc = null;
        try {
            doc = Jsoup.connect(itemUrl).userAgent("Chrome").get();
        } catch (IOException e) {
            return;
        }
        
        extractDetailImages(doc);
        
        extractItemPrice(doc);
        
        extractItemMaterials(doc);
    }

    @Override
    public void extractItemMaterials(Document doc) {
        Element body = doc.body();
        Elements elementsMaterials = extractMaterialsElements(body);
        
    }

    @Override
    public Elements extractMaterialsElements(Element body) {
        Elements materialsElements = body.getElementsByClass("fixedDescr");
        //System.out.println(materialsElements.get(0).getElementsByClass("expandDesc").get(1).text());
        
        return null;
    }

    @Override
    public String getMaterials(Elements elementsMaterials) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void extractItemPrice(Document doc) {
        //System.out.println(doc);
        Element body = doc.body();
        Elements elementsPrices = extractPriceElements(body);
    }

    @Override
    public void setOriginPrice(Elements elementsPrices) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Elements extractPriceElements(Element body) {
        Elements priceElements = body.getElementsByClass("product-price");
        System.out.println(priceElements);
        System.out.println(priceElements.get(0).getElementsByClass("price-sales").text());
        System.out.println(priceElements.get(0).getElementsByClass("price-standard").text());
        //System.out.println(priceElements.get(0).hasClass("price-standard"));
        System.out.println(priceElements.get(0).getAllElements().hasClass("price-standard"));
        return null;
    }

    @Override
    public void extractDetailImages(Document doc) {
        Element body = doc.body();
        Elements elementsDetaiImage = extractDetailElements(body);
        for(int i=0; i<elementsDetaiImage.size(); i++) {
            Element detailImageElement = elementsDetaiImage.get(i);
            String imageUrl = extractDetailImageUrl(detailImageElement);
            //System.out.println(imageUrl);
        }
    }

    @Override
    public Elements extractDetailElements(Element body) {
        Elements galleryElements = body.getElementsByClass("product-images");
        //System.out.println(galleryElements.get(0).getElementsByClass("image-container"));
        return galleryElements.get(0).getElementsByClass("image-container");
    }

    @Override
    public String extractDetailImageUrl(Element detailImageElement) {
        //System.out.println(detailImageElement);
        String imageUrl = detailImageElement.getElementsByTag("img").attr("data-src");
        return imageUrl;
    }
}
