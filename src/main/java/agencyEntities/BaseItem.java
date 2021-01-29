package agencyEntities;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseItem {
    private static final Logger LOGGER = LogManager.getLogger(BaseItem.class);

    private static final String COMPANY_LOGO = "https://moondrive81.cafe24.com/GKoo/gkoo_comany_logo.png";
    private static double excahgeRateEuro = 1400;
    private static final double TAX_LIMIT= 150000;
    
    private static int feePercent = 7;
    private static double minimumCommision = 8000;
    //2자리리 내림 ex. 10511원? -> 10500원?
    private final int ROUNDED_DIGIT = 3;
    
    protected String getCompanyLogoUrl() {
        return COMPANY_LOGO;
    }
    
    public int calculatePriceCommisionWon(double totalPriceEuro) {
        Objects.nonNull(totalPriceEuro);
        if(totalPriceEuro == 0) {
            LOGGER.error("Price must not 0");
        }
        double commision = 0;
        double productPriceWon = excahgeRateEuro*totalPriceEuro;
        if(isMinimumCommision(excahgeRateEuro, totalPriceEuro)) {
            commision = productPriceWon*(feePercent/100.0);
        } else {
            commision = minimumCommision;
        }
        int ceiledFeeResult = mathCeilDigit(ROUNDED_DIGIT, commision);
        int ceiledProductResult = mathCeilDigit(ROUNDED_DIGIT, productPriceWon);
        return ceiledFeeResult + ceiledProductResult;
    }
    
    public int calculatePriceCommisionVATWon(double totalPriceEuro, int deliveryFee) {
        Objects.nonNull(totalPriceEuro);
        if(totalPriceEuro == 0) {
            LOGGER.error("Price must not 0");
        }
        double commision = 0;
        double taxVat = 0;
        double productPriceWon = excahgeRateEuro*totalPriceEuro;
        
        if (productPriceWon >= TAX_LIMIT) {
            //통관시 부가세
            taxVat = (productPriceWon + deliveryFee)*(0.1);
        } 
        
        if(isMinimumCommision(excahgeRateEuro, totalPriceEuro)) {
            commision = productPriceWon*(feePercent/100.0);
        } else {
            commision = minimumCommision;
        }
        
        int ceiledCommision = mathCeilDigit(ROUNDED_DIGIT, commision);
        int ceiledProductPriceWon = mathCeilDigit(ROUNDED_DIGIT, productPriceWon);
        int ceiledTaxVat = taxVat != 0 ? mathCeilDigit(ROUNDED_DIGIT, taxVat) : 0;
        
        return (ceiledProductPriceWon + ceiledCommision + ceiledTaxVat + deliveryFee);
    }
    
    public int calculatePriceNoCommisionWon(double totalPriceSaleEuro) {
        Objects.nonNull(totalPriceSaleEuro);
        if(totalPriceSaleEuro == 0) {
            LOGGER.error("Price must not 0");
        }
        double productPriceWon = excahgeRateEuro*totalPriceSaleEuro;
        int ceiledProductResult = mathCeilDigit(ROUNDED_DIGIT, productPriceWon);
        return ceiledProductResult;
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
    
    public String getListToString(List<String> list) {
        return list.stream().collect(Collectors.joining(","));
    }
    
    public static String getEmptyLineHtml() {
        StringBuilder bd = new StringBuilder();
        bd.append("<p style=\"text-align: center;\">&nbsp;</p>");
        return bd.toString();
    }
    
    public abstract String getSizeListString();
    
    public abstract String getSizeListPriceString();
    
    public abstract String getSizeListStockString();
    
    public abstract String getCategoryId();
    
    public abstract String getItemFullnameDE();
    
    public abstract String getItemFullnameEN();
    
    public abstract String getItemUrl();
    
    public abstract MassItem getMassItem();
    
    public abstract String getPriceWonString();
    
    public abstract String getPriceSaleWonString();
    
    public abstract String getPriceSubstractWonString();

    public abstract String getColorListString();
    
    public abstract String getMainImageFileName();
    
    public abstract String getSizeOptionCafe24();
    
    public abstract String getDetailPageCafe24();
    
    public abstract String getDetailPageSmart();
    
    public abstract boolean isItemSale();
    
}
