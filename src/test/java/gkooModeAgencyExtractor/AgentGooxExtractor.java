package gkooModeAgencyExtractor;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import gkooModeAgency.AgentGooxStarter;
import gkooModeAgency.AgentPalmAngelsStarter;
import util.Formatter;

public class AgentGooxExtractor implements IAgentModeExtractor {

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
        File input = new File(AgentGooxStarter.HTML_BRAND);
        Document document = null;
        try {
            document = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
        }
        Element body = document.body();
        
        extractDetailImages(document);
        
    }

    @Override
    public void extractDetailImages(Document doc) {
        Element body = doc.body();  
        Elements elementsDetaiImage = extractDetailElements(body);
    }

    @Override
    public Elements extractDetailElements(Element body) {
        Elements galleryElements = body.getElementsByClass("jss1");
        
        String rawImageUrl = galleryElements.get(0).getElementsByTag("img").get(0).attr("src");
        System.out.println(galleryElements.get(0).getElementsByTag("img"));
        //System.out.println(Formatter.splitAfterWord(rawImageUrl, ".jpg").get(0) + ".jpg");
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
