package gkooCosmeticAgencyExtractor;

import org.jsoup.nodes.Element;

public interface IAgentCosmeticSimpleExtractor {
    public void preprocessingTest();
    
    public void getUnitElements(Element body);
    
    public void extractUrl(Element unitElement);
    
    public void extractItemTitle(Element unitElement);
    
    public void extractItemPrice(Element unitElement);

    public void extractMainImage(Element unitElement);
}
