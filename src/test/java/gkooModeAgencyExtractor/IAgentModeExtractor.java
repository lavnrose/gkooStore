package gkooModeAgencyExtractor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public interface IAgentModeExtractor {
    public void preprocessingTest();
    
    public void getUnitElements(Element body);
    
    public void extractUrl(Element unitElement);
    
    public void extractItemTitle(Element unitElement);
    
    public void extractMainImage(Element unitElement);
    
    public void createMassItemTest();
    
    //detailImages
    public void extractDetailImages(Document doc);
    
    public Elements extractDetailElements(Element body);
    
    public String extractDetailImageUrl(Element detailImageElement);
    
    //price
    public void extractItemPrice(Document doc);
    
    public Elements extractPriceElements(Element body);
    
    public void setOriginPrice(Elements elementsPrices);
    
    //materials
    public void extractItemMaterials(Document doc);
    
    public Elements extractMaterialsElements(Element body);
    
    public String getMaterials(Elements elementsMaterials);
}
