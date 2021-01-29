package gkooModeAgencyExtractor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import gkooModeAgency.AgentAcne;
import util.Formatter;

public class AgentAcneExtractor {
    
    @Test
    public void preprocessingTest() {
        File input = new File(AgentAcne.HTML_BRAND);
        Document document = null;
        try {
            document = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
        }
        Element body = document.body();
        getUnitElements(body);
    }
    
    @Test
    public void createMassItemTest() {
        String itemUrl = "https://www.acnestudios.com/de/de/logo-t-shirt-dusty-orange/AL0149-AC9.html?categid=woman-t-shirts";
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
    
    public void extractItemMaterials(Document doc) {
        Element body = doc.body();
        Elements elementsMaterials = extractMaterialsElements(body);
        if (elementsMaterials.size()==0) {
            return;
        }
        
        String materials = getMaterials(elementsMaterials);
        System.out.println(materials);
    }
    
    public Elements extractMaterialsElements(Element body) {
        
        Elements materialsElements = body.getElementsByClass("custom-composition");
        //System.out.println(materialsElements);
        return materialsElements;
    }
    
    public String getMaterials(Elements elementsMaterials) {
        String materials = elementsMaterials.get(0).text();
        //System.out.println(materials);
        return materials;
    }
    
    public void extractItemPrice(Document doc) {
        Element body = doc.body();
        Elements elementsPrices = extractPriceElements(body);

        if(elementsPrices.size() == 0) {
            return;
        }
        
        setOriginPrice(elementsPrices);
    }
    
    public void setOriginPrice(Elements elementsPrices) {
        String formatOrgPrice = Formatter.deleteNonDigits(elementsPrices.get(0).text());
        
        //System.out.println(formatOrgPrice);
    }
    
    public Elements extractPriceElements(Element body) {
        Elements priceElements = body.getElementsByClass("product-price");
        //System.out.println(priceElements);
        return priceElements;
    }
    
    public void extractDetailImages(Document doc) {
        Element body = doc.body();
        Elements elementsDetaiImage = extractDetailElements(body);
        
        List<String> formattedImageList = new ArrayList<>();
        
        for(int i=0; i<elementsDetaiImage.size(); i++) {
            Element detailImageElement = elementsDetaiImage.get(i);
            String imageUrl = extractDetailImageUrl(detailImageElement);
            formattedImageList.add("https://www.acnestudios.com"+imageUrl);
        }
        //System.out.println(formattedImageList);
    }
    
    public Elements extractDetailElements(Element body) {
        
        Elements galleryElements = body.getElementsByClass("product-item__gallery-container");
        Elements detailImageElements = galleryElements.get(0).getElementsByTag("img");
        
        return detailImageElements;
    }
    
    public String extractDetailImageUrl(Element detailImageElement) {
        
        String detailImageUrl = detailImageElement.attr("data-zoom-src");
        
        return detailImageUrl;
    }
    
    //unit: itemUrl, itemTitle, mainImage
    public void getUnitElements(Element body) {
        //unit
        Element result = body.getElementsByClass("search-result-container").get(0);
        Element element = result.child(0);
        Elements rawUnitElements = element.getElementsByTag("li");
        Elements unitElements = new Elements();
        for (int i=0; i<rawUnitElements.size(); i++) {
            Element unit = rawUnitElements.get(i);
            if(unit.className().contains("grid-tile product-list__item-tile")) {
                unitElements.add(unit);
            }
        }

        int number = 1;
        for (int i=0; i<unitElements.size(); i++) {
            //itemUrl
            extractUrl(unitElements.get(i));
        }
        
        //itemTitle
        extractItemTitle(unitElements.get(number));
        //mainImage
        extractMainImage(unitElements.get(number));
        
    }
    
    public void extractUrl(Element urlElement) {
        Elements urlElements = urlElement.getElementsByClass("product-image ");
        String url = urlElements.get(0).getElementsByClass("thumb-link").get(0).attr("href");
        
        System.out.println("https://www.acnestudios.com"+ url);
    }
    
    public void extractItemTitle(Element element) {
        Elements productInfoElements = element.getElementsByClass("product-info product-list__item-info");
        Elements productNameElements = productInfoElements.get(0).getElementsByClass("product-name");
        Elements itemTitleElements = productNameElements.get(0).getElementsByTag("a");
        
        //System.out.println(itemTitleElements.text());
    }
    
    public void extractMainImage(Element element) {
        Elements productImageElements = element.getElementsByClass("product-image ").get(0).getElementsByClass("thumb-link");
        String imageUrls = productImageElements.get(0).getElementsByTag("img").attr("srcset");
        String rawImageUrl = Formatter.splitAfterWord(imageUrls, ".jpg").get(0);
        String mainImageUrl = "https://www.acnestudios.com"+ Formatter.removeEmptySymbol(rawImageUrl) + ".jpg";
        
        //System.out.println(mainImageUrl);
    }
}