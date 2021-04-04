package gkooModeAgencyExtractor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import gkooModeAgency.AgentFeelwayStarter;

public class AgentMoncExtractor {
    @Test
    public void createMassItemTest() {
        File input = new File(AgentFeelwayStarter.HTML_BRAND);
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
           
        }
        
        //extractDetailImages(doc);
        
        extractItemDescription(doc);
    }
    
    public void extractDetailImages(Document doc) {
        Element body = doc.body();
        Elements elementsDetaiImage = extractDetailElements(body);
        
//        for(int i=0; i<elementsDetaiImage.size(); i++) {
//            Element detailImageElement = elementsDetaiImage.get(i);
//            String imageUrl = extractDetailImageUrl(detailImageElement);
//            System.out.println(imageUrl);
//        }
    }
    
    public Elements extractDetailElements(Element body) {
        Elements galleryElements = body.getElementsByClass("image");
        System.out.println(galleryElements.get(0).getElementsByTag("img").attr("srcset"));
        String imageSet = galleryElements.get(0).getElementsByTag("img").attr("srcset");
        List<String> imageList = Arrays.asList(imageSet.split(",", -1));
        List<String> formattedImageList = new ArrayList<>();
        
        String frontImage = imageList.get(0).split(" ")[0];
        String baseImage = frontImage.split("_1")[0];
            
        formattedImageList.add(baseImage+"_13_f.jpg");
        formattedImageList.add(baseImage+"_13_r.jpg");
        formattedImageList.add(baseImage+"_13_d.jpg");

        return galleryElements;
    }
    
    public String extractDetailImageUrl(Element detailImageElement) {
        Elements productsImages = detailImageElement.getElementsByTag("a");
        String imageUrl = productsImages.get(0).getElementsByClass("product-images__link").attr("href");
        return imageUrl;
    }
    
    public void extractItemDescription(Document doc) {
        Element body = doc.body();
        Elements elementsMaterials = body.getElementsByClass("pdp__details__accordion__panel");
        System.out.println(elementsMaterials.get(0).text());
    }
}
