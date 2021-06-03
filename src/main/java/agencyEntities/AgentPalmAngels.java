package agencyEntities;

import java.io.IOException;
import java.util.List;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.GrobalDefined.Gender;

public class AgentPalmAngels extends Agent implements IModeAgent {
    public AgentPalmAngels(String brandNameDe, String brandNameKor,
            String homepageUrl, String dirMainImages, List<String> itemSizeList,
            List<String> itemSizePriceList, List<String> itemSizeStockist, Gender gender, int deliveryFee) {
        super(brandNameDe, brandNameKor, homepageUrl, dirMainImages, itemSizeList,
                itemSizePriceList, itemSizeStockist, gender, deliveryFee);
    }
    @Override
    public Elements getUrlElements(Element body) {
        Elements unitElements = body.getElementsByClass("css-u7k64p");
        return unitElements;
    }

    @Override
    public String extractUrl(Element urlElement) {
        String url = urlElement.getElementsByClass("css-1kcohcr").attr("href");
        return url;
    }

    @Override
    public String extractItemTitle(MassItem massItem, Element element) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setCommonProperties(MassItem massItem) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void downloadingMainImage(MassItem massItem, Element urlElement) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void extractDetailImages(Document doc, MassItem item) throws IOException {
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

    @Override
    public void extractItemPrice(Document doc, MassItem item) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Elements extractPriceElements(Element body) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setOriginPrice(Elements elementsPrices, MassItem item) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void extractItemMaterials(Document doc, MassItem item) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Elements extractMaterialsElements(Element body) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setOptions(Document doc, MassItem item) {
        // TODO Auto-generated method stub
        
    }

}
