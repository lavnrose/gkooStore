package agencyEntities;

import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BasePreItemCosmetic {
    private static final Logger LOGGER = LogManager.getLogger(BasePreItemCosmetic.class);

    private static double excahgeRateEuro = 1400;
    private static final double TAX_LIMIT= 150000;
    
    private static int feePercent = 10;
    private static double minimumCommision = 5000;
    //2자리리 내림 ex. 10511원? -> 10500원?
    private final int ROUNDED_DIGIT = 3;
    private final static int OPEN_MARKET_COMMISION_PERCENT = 9;
    
    public int calculatePriceCommisionSectionWon(double totalPriceEuro) {
        Objects.nonNull(totalPriceEuro);
        if(totalPriceEuro == 0) {
            LOGGER.error("Price must not 0");
        }
        double taxVat = 0;
        double productPriceWon = excahgeRateEuro*totalPriceEuro;
        
        if (productPriceWon >= TAX_LIMIT) {
            //통관시 부가세
            taxVat = (productPriceWon)*(0.1);
        } 
        
        double commision = getSectionCommision(productPriceWon);
        
        int ceiledCommision = mathCeilDigit(ROUNDED_DIGIT, commision);
        int ceiledProductPriceWon = mathCeilDigit(ROUNDED_DIGIT, productPriceWon);
        int ceiledTaxVat = taxVat != 0 ? mathCeilDigit(ROUNDED_DIGIT, taxVat) : 0;
        
        return (ceiledProductPriceWon + ceiledCommision + ceiledTaxVat);
    }
    
    private boolean isMinimumCommision(double currentEurToKRW, double totalPriceEuro) {
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
    
    private double getSectionCommision(double productPriceWon) {
        double commision = 0;
        
        if(productPriceWon < 10000) {
            commision = 1900;
        } else if (productPriceWon >= 10000 & productPriceWon < 20000) {
            commision = 2900;
        } else if (productPriceWon >= 20000 & productPriceWon < 30000) {
            commision = 3900;
        } else if (productPriceWon >= 30000 & productPriceWon < 40000) {
            commision = 4900;
        } else if (productPriceWon >= 40000 & productPriceWon < 50000) {
            commision = 5900;
        } else if (productPriceWon >= 50000 & productPriceWon < 60000) {
            commision = 6900;
        } else if (productPriceWon >= 60000 & productPriceWon < 70000) {
            commision = 7900;
        } else if (productPriceWon >= 70000 & productPriceWon < 80000) {
            commision = 8900;
        } else if (productPriceWon >= 80000 & productPriceWon < 90000) {
            commision = 9900;
        } else if (productPriceWon >= 90000 & productPriceWon < 100000) {
            commision = 10900;
        } else if (productPriceWon >= 100000 & productPriceWon < 110000) {
            commision = 11900;
        } else if (productPriceWon >= 110000 & productPriceWon < 120000) {
            commision = 12900;
        } else if (productPriceWon >= 120000 & productPriceWon < 130000) {
            commision = 13900;
        } else if (productPriceWon >= 130000 & productPriceWon < 140000) {
            commision = 14900;
        } else if (productPriceWon >= 140000) {
            commision = productPriceWon*0.1;
        }
        
        return commision;
    }
    
    public abstract MassItem getMassItem();
}
