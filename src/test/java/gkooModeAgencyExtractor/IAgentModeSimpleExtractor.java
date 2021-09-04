package gkooModeAgencyExtractor;

import org.jsoup.nodes.Element;

public interface IAgentModeSimpleExtractor {
 public void preprocessingTest();
    
    public void getUnitElements(Element body);
    
    public void extractUrl(Element unitElement);
    
    public void extractItemTitle(Element unitElement);
    
    public void extractItemPrice(Element unitElement);

    public void extractMainImage(Element unitElement);
}
