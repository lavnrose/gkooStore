package gkooCosmeticAgencyExtractor;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import agencyEntities.MassItem;

public class AgentDougExtractor implements IAgentCosmeticExtractor {

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

    @Override
    public void createMassItemTest() {
        // TODO Auto-generated method stub
        
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
    public void extractItemDescription(Document doc) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Elements extractDescriptionElements(Element body) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDescription(Elements elementDescription, MassItem item) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void extractItemIngredients(Document doc) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Elements extractIngredientsElements(Element body) {
        // TODO Auto-generated method stub
        return null;
    }

}
