package agencyController;

import java.util.Objects;
import java.util.function.Predicate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import util.MathUtil;

public class PriceCalc {
    private static final Logger LOGGER = LogManager.getLogger(PriceCalc.class);

    private static double excahgeRateEuro = 1400;
    private static final double TAX_LIMIT= 150000;
    
    private final static int FEELWAY_COMMISION_PERCENT = 6;
    private final int GKOO_COMMISION_PERCENT = 10;
    private static double minimumCommision = 15000;

    //2자리리 내림 ex. 10511원? -> 10500원?
    private final int ROUNDED_DIGIT = 3;
    
    public int calculatePriceCommisionVATWon(double totalPriceEuro, int deliveryPrice) {
        Objects.nonNull(totalPriceEuro);
        Objects.nonNull(deliveryPrice);
        if(totalPriceEuro == 0) {
            LOGGER.error("Price must not 0");
        }
        
        double commision = 0;
        double taxVat = 0;
        double productPriceWon = excahgeRateEuro*totalPriceEuro;
        
        if (productPriceWon >= TAX_LIMIT) {
            double priceWonMinusVat = productPriceWon*0.81;
            //통관시 부가세
            taxVat = (priceWonMinusVat + deliveryPrice)*(0.1);
        } 
        
        if(isMinimumCommision(excahgeRateEuro, totalPriceEuro, GKOO_COMMISION_PERCENT)) {
            commision = productPriceWon*(GKOO_COMMISION_PERCENT/100.0);
        } else {
            commision = minimumCommision;
        }
        
        double gkooPriceWon = productPriceWon + commision + taxVat + deliveryPrice;
        double feelwayPriceWon = getFeelwayPrice(gkooPriceWon);
        return mathCeilDigit(ROUNDED_DIGIT, feelwayPriceWon);
    }

    public int calculatePriceGooxWon(double totalPriceEuro, int deliveryPrice) {
        Objects.nonNull(totalPriceEuro);
        Objects.nonNull(deliveryPrice);
        if(totalPriceEuro == 0) {
            LOGGER.error("Price must not 0");
        }
        
        double commision = 0;
        double taxVat = 0;
        double productPriceWon = excahgeRateEuro*totalPriceEuro;
        
        //if (productPriceWon >= TAX_LIMIT) {
        if (isOverTexLimit.test(productPriceWon)) {
            double priceWonMinusVat = productPriceWon*0.81;
            //통관시 부가세
            taxVat = (priceWonMinusVat + deliveryPrice)*(0.1);
        } 
        
        if(isMinimumCommision(excahgeRateEuro, totalPriceEuro, GKOO_COMMISION_PERCENT)) {
            commision = productPriceWon*(GKOO_COMMISION_PERCENT/100.0);
        } else {
            commision = minimumCommision;
        }
        
        double gkooPriceWon = productPriceWon + commision + taxVat + deliveryPrice;
        double feelwayPriceWon = getFeelwayPrice(gkooPriceWon);
        return mathCeilDigit(ROUNDED_DIGIT, feelwayPriceWon);
    }
    
    static Predicate<Double> isOverTexLimit = 
                productPriceWon -> productPriceWon >= TAX_LIMIT;
    
    private static int getFeelwayPrice(double gkooPrice) {
        double margin = 1 - (FEELWAY_COMMISION_PERCENT/100.0);
        double feelwayPrice = (gkooPrice/margin);
        int ceiledProductPriceWon = MathUtil.mathCeilDigit(3, feelwayPrice);
        return ceiledProductPriceWon;
    }
    
    private boolean isMinimumCommision(double currentEurToKRW, double totalPriceEuro, int feePercent) {
        int commision = (int) ((currentEurToKRW*totalPriceEuro)*(feePercent/100.0));
        if(commision >= minimumCommision) {
            return true;
        } else {
            return false;
        }
    }
    
    private int mathCeilDigit(int digit, double price) {
        int power = (int) Math.pow(10, digit);
        int newPrice = (int) Math.round(price/power);
        return (newPrice*power);
    }
}
