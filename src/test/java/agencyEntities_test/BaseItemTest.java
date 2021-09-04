package agencyEntities_test;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import agencyEntities.BaseItem;

public class BaseItemTest {
    @Spy
    private BaseItem baseItem;
    
    @Rule 
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    
    //@Test
    public void calculatePriceCommisionVATWon() {
        baseItem = Mockito.mock(BaseItem.class, Mockito.CALLS_REAL_METHODS);
        System.out.println("cafe24 price with delivery fee: " + baseItem.calculatePriceCommisionVATWon(100, 8000));
    }
    
    //@Test
    public void calculatePriceCommisionVATWonCoupangTest() {
        baseItem = Mockito.mock(BaseItem.class, Mockito.CALLS_REAL_METHODS);
        System.out.println("coupang price without delivery fee: " + baseItem.calculatePriceCommisionVATWonCoupang(100));
    }
    
    @Test
    public void calculatePriceCommisionVATWonTest() {
       baseItem = Mockito.mock(BaseItem.class, Mockito.CALLS_REAL_METHODS);
       System.out.println("Price: "+ baseItem.calculatePriceCommisionVATWon(200, 10000));
    }
}
