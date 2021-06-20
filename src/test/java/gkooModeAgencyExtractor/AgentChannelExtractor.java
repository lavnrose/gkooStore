package gkooModeAgencyExtractor;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class AgentChannelExtractor implements IAgentModeExtractor {

    @Override
    public void preprocessingTest() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void getUnitElements(Element body) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void extractUrl(Element unitElement) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void extractItemTitle(Element unitElement) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void extractMainImage(Element unitElement) {
        // TODO Auto-generated method stub
        
    }
    
    @Test
    @Override
    public void createMassItemTest() {
        //String itemUrl = "https://www.chanel.com/de/mode/p/AB6476B06095ND015/guertel-metall-kalbsleder-glasperlen-strass/";
        String itemUrl = "https://www.chanel.com/de/mode/p/G37594Y5535094305/offene-schuhe-pailleten-rips/";
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
    public void extractDetailImages(Document doc) {
        Element body = doc.body();
        Elements elementsDetaiImage = extractDetailElements(body);
    }

    @Override
    public Elements extractDetailElements(Element body) {
        Element detailsElement = body.getElementsByClass("product-details").get(0);
        //System.out.println(detailsElement.getElementsByClass("carousel__outer").get(0).getElementsByTag("li").get(0));
        Elements imageElements = detailsElement.getElementsByClass("carousel__outer").get(0).getElementsByTag("li");
        int size = imageElements.size();
        for (int i=0;i<size;i++) {
            System.out.println(imageElements.get(i).getElementsByTag("img").get(1).attr("async-src"));
        }
        //System.out.println(detailsElement.getElementsByClass("carousel__outer").get(0).getElementsByTag("li").get(1));
        return null;
    }

    @Override
    public String extractDetailImageUrl(Element detailImageElement) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void extractItemPrice(Document doc) {
        // TODO Auto-generated method stub
        
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
