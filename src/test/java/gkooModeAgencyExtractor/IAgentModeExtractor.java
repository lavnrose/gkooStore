package gkooModeAgencyExtractor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface IAgentModeExtractor {
    public void preprocessingTest();
    
    public void getUnitElements(Element body);
    
    public void extractUrl(Element urlElement);
    
    public void extractItemTitle(Element element);
    
    public void extractMainImage(Element element);
    
    public void createMassItemTest();
    
    //detailImages
    public void extractDetailImages(Document doc);
    
    public Elements extractDetailElements(Element body);
    
    public String extractDetailImageUrl(Element detailImageElement);
    
    //price
    public void extractItemPrice(Document doc);
    
    public void setOriginPrice(Elements elementsPrices);
    
    public Elements extractPriceElements(Element body);
    
    //materials
    public void extractItemMaterials(Document doc);
    
    public Elements extractMaterialsElements(Element body);
    
    public String getMaterials(Elements elementsMaterials);
}
