package gkooCosmeticAgencyExtractor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import agencyEntities.MassItem;

public interface IAgentCosmeticExtractor {
    public void preprocessingTest();
    
    public void getUnitElements(Element body);
    
    public void extractUrl(Element unitElement);
    
    public void extractItemTitle(Element unitElement);
    
    public void extractMainImage(Element unitElement);
    
    public void createMassItemTest();
    
    //price
    public void extractItemPrice(Document doc);
    public Elements extractPriceElements(Element body);
    public void setOriginPrice(Elements elementsPrices);
    
    public void extractItemDescription(Document doc);
    public Elements extractDescriptionElements(Element body);
    void setDescription(Elements elementDescription, MassItem item);

    //ingredients
    public void extractItemIngredients(Document doc);
    public Elements extractIngredientsElements(Element body);
}
