package agencyController_test;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import agencyController.PriceCalc;

public class PriceCalcTest {
    @Spy
    private PriceCalc priceCalc;
    
    @Rule 
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    
    @Test
    public void calculatePriceCommisionVATWonTest() {
        priceCalc = Mockito.mock(PriceCalc.class, Mockito.CALLS_REAL_METHODS);
        System.out.println(priceCalc.calculatePriceCommisionVATWon(116, 10000));
    }
}
