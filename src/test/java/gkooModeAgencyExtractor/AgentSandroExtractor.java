package gkooModeAgencyExtractor;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import gkooModeAgency.AgentAcne;

public class AgentSandroExtractor implements IAgentModeExtractor {
    @Test
    @Override
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
    
    @Override
    public void getUnitElements(Element body) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void extractUrl(Element urlElement) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void extractItemTitle(Element element) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void extractMainImage(Element element) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void createMassItemTest() {
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
    public void extractItemPrice(Document doc) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setOriginPrice(Elements elementsPrices) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Elements extractPriceElements(Element body) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void extractDetailImages(Document doc) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Elements extractDetailElements(Element body) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String extractDetailImageUrl(Element detailImageElement) {
        // TODO Auto-generated method stub
        return null;
    }
}
