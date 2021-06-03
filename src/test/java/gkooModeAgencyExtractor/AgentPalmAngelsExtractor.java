package gkooModeAgencyExtractor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import gkooModeAgency.AgentPalmAngelsStarter;
import util.Formatter;

public class AgentPalmAngelsExtractor implements IAgentModeExtractor {
    
    //@Test
    @Override
    public void preprocessingTest() {
        File input = new File(AgentPalmAngelsStarter.HTML_BRAND);
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
        Elements rawUnitElements = body.getElementsByClass("css-u7k64p");
        
        Elements unitElements = new Elements();
        for (int i=0; i<rawUnitElements.size(); i++) {
            Element unit = rawUnitElements.get(i);
            unitElements.add(unit);
        }
        
        int number = 0;
        extractUrl(unitElements.get(number));
        //System.out.println(rawUnitElements.get(0).getElementsByClass("css-1kcohcr").attr("href"));
        
        extractItemTitle(unitElements.get(number));
        
        extractMainImage(unitElements.get(number));
    }

    @Override
    public void extractUrl(Element unitElement) {
        String url = unitElement.getElementsByClass("css-1kcohcr").attr("href");
        System.out.println(url);
    }

    @Override
    public void extractItemTitle(Element unitElement) {
        System.out.println(unitElement.getElementsByClass("css-1kcohcr").attr("aria-label"));
    }

    @Override
    public void extractMainImage(Element unitElement) {
        String rawImageUrl = unitElement.getElementsByTag("img").get(0).attr("srcset");
        System.out.println(Formatter.splitAfterWord(rawImageUrl, ".jpg").get(0) + ".jpg");
    }

    @Test
    @Override
    public void createMassItemTest() {
        File input = new File(AgentPalmAngelsStarter.HTML_BRAND);
        Document document = null;
        try {
            document = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
        }
        Element body = document.body();
        System.out.println(body);

//        extractDetailImages(doc);
        
//        extractItemPrice(doc);
//        
//        extractItemMaterials(doc);
        
    }
    
    @Override
    public void extractDetailImages(Document doc) {
        Element body = doc.body();
        //Elements elementsDetaiImage = extractDetailElements(body);
        System.out.println(body);
    }

    @Override
    public Elements extractDetailElements(Element body) {
        System.out.println(body);
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
