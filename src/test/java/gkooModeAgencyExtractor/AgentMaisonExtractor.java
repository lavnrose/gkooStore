package gkooModeAgencyExtractor;

import java.io.File;
import java.io.IOException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.mockito.Spy;
import gkooAgentConfig.MaisonConfig;

public class AgentMaisonExtractor implements IAgentModeExtractor {
    @Spy
    private MaisonConfig maisonConfig = new MaisonConfig();
    
    @Test
    @Override
    public void preprocessingTest() {
        File input = new File(maisonConfig.getDirectoryLocationBuilder().getHtmlFileLocation.get());
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
        Elements rawUnitElements = body.getElementsByClass("products wrapper grid products-grid").get(0).getElementsByClass("item product product-item");
        Elements unitElements = new Elements();
        for (int i=0; i<rawUnitElements.size(); i++) {
            Element unit = rawUnitElements.get(i);
            unitElements.add(unit);
        }
        //System.out.println(unitElements.get(1));
        
//        int number = 0;
        int number = 11;
        //extractUrl(unitElements.get(number));
//        for (int i=0; i<unitElements.size(); i++) {
//            extractUrl(unitElements.get(i));
//        }
        
        //extractItemTitle(unitElements.get(number));
//        for (int i=0; i<unitElements.size(); i++) {
//            //System.out.println(i);
//            extractItemTitle(unitElements.get(i));
//        }
        
//        for (int i=0; i<unitElements.size(); i++) {
//            extractItemPrice(unitElements.get(i));
//        }
        
        extractMainImage(unitElements.get(number));
//        for (int i=0; i<unitElements.size(); i++) {
//            System.out.println(i);
//            extractMainImage(unitElements.get(i));
//        }
    }

    @Override
    public void extractUrl(Element unitElement) {
        System.out.println(unitElement.getElementsByClass("product-item-info ").get(0).getElementsByTag("a").get(0).attr("href"));
        
    }

    @Override
    public void extractItemTitle(Element unitElement) {
        System.out.println(unitElement.getElementsByClass("product details product-item-details").get(0)
                .getElementsByClass("product name product-item-name").text());
        
    }
    
    public void extractItemPrice(Element unitElement) {
        System.out.println(unitElement.getElementsByClass("product details product-item-details").get(0)
                .getElementsByClass("price-box price-final_price").text());
        
    }

    @Override
    public void extractMainImage(Element unitElement) {
        //System.out.println(unitElement.getElementsByClass("product-item-info ").get(0).getElementsByTag("a").get(0).getElementsByTag("img").attr("src"));
        System.out.println(unitElement.getElementsByClass("product-item-info ").get(0).getElementsByTag("img").get(0).attr("data-src"));
        System.out.println(unitElement.getElementsByClass("product-item-info ").get(0).getElementsByTag("img").get(0).attr("src"));
    }

    //@Test
    @Override
    public void createMassItemTest() {
        String itemUrl = "https://maisonkitsune.com/fr_en/navy-fox-patch-classic-polo-grey-melange-5fd80f9f3d93d.html";
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

    }

    @Override
    public Elements extractDetailElements(Element body) {
        JSONObject jsonObj = new JSONObject(body.getElementsByClass("product-main clearer")
                .get(0).getElementsByClass("product media").get(0).getElementsByTag("script").get(1).data());
        JSONObject jsonObj2 = new JSONObject(jsonObj.get("[data-gallery-role=gallery-placeholder]").toString());
        JSONObject jsonObj3 = new JSONObject(jsonObj2.get("mage/gallery/gallery").toString());
        System.out.println(jsonObj3.get("data").toString());
        JSONArray jsonArray = new JSONArray(jsonObj3.get("data").toString()); 
        for(Object obj : jsonArray) {
            JSONObject explrObject = (JSONObject) obj;
            System.out.println(explrObject.get("img_large"));
        }
        return null;
    }

    @Override
    public String extractDetailImageUrl(Element detailImageElement) {
        return null;
    }

    @Override
    public void extractItemPrice(Document doc) {
        Element body = doc.body();
        System.out.println(body.getElementsByClass("product-main clearer").get(0)
                .getElementsByClass("price-box price-final_price").get(0).getElementsByTag("meta").attr("content"));

        
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

}
