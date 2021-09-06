package gkooCosmeticAgencyExtractor;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.mockito.Spy;
import gkooAgentConfig.FlaconiConfig;
import util.Formatter;

public class AgentFlaconiExtractor implements IAgentCosmeticSimpleExtractor {
    @Spy
    private FlaconiConfig flaconiConfig = new FlaconiConfig();
    
    @Test
    @Override
    public void preprocessingTest() {
        File input = new File(flaconiConfig.getDirectoryLocationBuilder().getHtmlFileLocation.get());
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
        Elements unitElements = body.getElementsByClass("e-tastic e-tastic__flaconi-product-list ").get(0).getElementsByAttributeStarting("data-product-list-id");
        int number = 11;
        
        for(Element ele : unitElements) {
            extractUrl(ele);
//            extractItemTitle(ele);
//            extractItemPrice(ele);
//            extractMainImage(ele);
        }
      
        //extractItemTitle(unitElements.get(number));
        
        //extractItemPrice(unitElements.get(number));
        
        //extractMainImage(unitElements.get(number));
        
    }

    @Override
    public void extractUrl(Element unitElement) {
        String url = "https://www.flaconi.de" + unitElement.getElementsByTag("a").attr("href");
        //System.out.println(url);
        //String formattedUrl = Formatter.getFormattedHtmUrl(url);
        createMassItemTest(url);
    }

    @Override
    public void extractItemTitle(Element unitElement) {
        //System.out.println(unitElement.getElementsByTag("a").get(0).getElementsByTag("img").attr("alt"));
    }

    @Override
    public void extractItemPrice(Element unitElement) {
        String complexData = "";
        Elements elements = unitElement.getElementsByTag("span");
        for(Element ele : elements) {
            if(ele.className().contains("ProductPrice")) {
                complexData = ele.text();
            }
        }
        
        String[] rawPrice =  complexData.split("/");
//        System.out.println("rawPrice:" +Formatter.deleteNonDigits(rawPrice[0]));
//        System.out.println("volume:" + Formatter.removeEmptySymbol(rawPrice[1]));
    }

    @Override
    public void extractMainImage(Element unitElement) {
        String rawImageUrl = unitElement.getElementsByTag("a").get(0).getElementsByTag("img").attr("src");
        //System.out.println(rawImageUrl);
        String imageUrl = Formatter.getFormattedJpgUrl(rawImageUrl);
        //System.out.println(imageUrl);
    }
    
    //@Test
    public void createMassItemTest(String itemUrl) {
        //String itemUrl = "https://www.flaconi.de/make-up/dr-hauschka/reinigung/dr-hauschka-reinigung-augenmake-up-entferner.html";
        Document doc = null;
        try {
            doc = Jsoup.connect(itemUrl).userAgent("Chrome").get();
        } catch (IOException e) {
            return;
        }
        
        extractItemIngredients(doc);
    }
    
    public void extractItemIngredients(Document doc) {
        Element body = doc.body();
        int size = body.getElementsByClass("pdp-product-info-details").size();
        System.out.println(body.getElementsByClass("pdp-product-info-details").get(size-1).text());
    }
}
