package agencyEntities;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseItemCosmetic {
    private static final Logger LOGGER = LogManager.getLogger(BaseItemCosmetic.class);

    private static final String COMPANY_LOGO = "https://moondrive81.cafe24.com/GKoo/gkoo_comany_logo.png";
    private static double excahgeRateEuro = 1400;
    private static final double TAX_LIMIT= 150000;
    
    private static int feePercent = 10;
    private static double minimumCommision = 2000;
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
    
    public int calculatePriceWon(double totalPriceEuro) {
        Objects.nonNull(totalPriceEuro);
        double commision = 0;
        final double productPriceWon = excahgeRateEuro*totalPriceEuro;
        if(isMinimumCommision(excahgeRateEuro, totalPriceEuro)) {
            commision = productPriceWon*(feePercent/100.0);
        } else {
            commision = minimumCommision;
        }
        int ceiledFeeResult = mathCeilDigit(ROUNDED_DIGIT, commision);
        int ceiledProductResult = mathCeilDigit(ROUNDED_DIGIT, productPriceWon);
        return ceiledFeeResult + ceiledProductResult;
    }
    
    public int calculatePriceWonWithExtraFee(double totalPriceEuro, int extraFee) {
        Objects.nonNull(totalPriceEuro);
        double commision = 0;
        final double productPriceWon = excahgeRateEuro*totalPriceEuro;
        if(isMinimumCommision(excahgeRateEuro, totalPriceEuro)) {
            commision = productPriceWon*(feePercent/100.0);
        } else {
            commision = minimumCommision;
        }
        int ceiledFeeResult = mathCeilDigit(ROUNDED_DIGIT, commision);
        int ceiledProductResult = mathCeilDigit(ROUNDED_DIGIT, productPriceWon);
        return ceiledFeeResult + ceiledProductResult + extraFee*3;
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
    
    public String addAlignment(String itemImagesHtml) {
        StringBuilder imageBuilder = new StringBuilder();
        imageBuilder.append("<p style=\"text-align:center;\">");
        imageBuilder.append(itemImagesHtml);
        imageBuilder.append("</p>");
        return imageBuilder.toString();
    }
    
    public String addTopBottomInfo(String itemImagesHtml) {
        StringBuilder imageBuilder = new StringBuilder();
        imageBuilder.append("<p style=\"text-align:center;\"><img style=\"padding-bottom: 30px;\" src=\"https://moondrive81.cafe24.com/GKoo/gkoo_info_top.png\"/></p>");
        imageBuilder.append(itemImagesHtml);
        imageBuilder.append("<p style=\"text-align:center;\"><img src=\"https://moondrive81.cafe24.com/GKoo/gkoo_info_bottom.jpg\"/></p>");
        return imageBuilder.toString();
    }
    
    public static String getItemFullNameHtml(String itemFullname) {
        StringBuilder bd = new StringBuilder();
        bd.append("<p style=\"text-align: center;\"><span style=\"font-size: 14pt;\"><strong>");
        bd.append(itemFullname);
        bd.append("</strong></span></p>");
        return bd.toString();
    }
    
    public static String getItemBrandNameHtml(String itemBrandname) {
        StringBuilder bd = new StringBuilder();
        bd.append("<p style=\"text-align: center;\"><span style=\"font-size: 12pt;\"><strong>");
        bd.append(itemBrandname);
        bd.append("</strong></span></p>");
        return bd.toString();
    }
    
    public static String getItemBrandOverview(String brandOverviewUrl) {
        StringBuilder bd = new StringBuilder();
        bd.append("<p style=\"text-align:center;\"><img style=\"padding-bottom: 30px;\" src=\"" + brandOverviewUrl + "\"/></p>");
        return bd.toString();
    }
    
    public static String getEmptyLineHtml() {
        StringBuilder bd = new StringBuilder();
        bd.append("<p style=\"text-align: center;\">&nbsp;</p>");
        return bd.toString();
    }
    
    public static String getItemDescriptionHtml(String description) {
        StringBuilder bd = new StringBuilder();
        bd.append("<p style=\"text-align: center;\"><span style=\"font-size: 12pt;\"><strong>상품 </strong><strong>설명</strong></span></p>");
        bd.append(getEmptyLineHtml());
        bd.append("<p style=\"text-align: center;\">");
        bd.append(description);
        bd.append("</p>");
        return bd.toString();
    }
    
    public static String getItemUsageHtml(String usage) {
        StringBuilder bd = new StringBuilder();
        bd.append("<p style=\"text-align: center;\"><span style=\"font-size: 12pt;\"><strong>사용법</strong></span></p>");
        bd.append(getEmptyLineHtml());
        bd.append("<p style=\"text-align: center;\">");
        bd.append(usage);
        bd.append("</p>");
        return bd.toString();
    }
    
    public static String getItemIngredientHtml(String ingredient) {
        StringBuilder bd = new StringBuilder();
        bd.append("<p style=\"text-align: center;\"><span style=\"font-size: 12pt;\"><strong>성분표</strong></span></p>");
        bd.append(getEmptyLineHtml());
        bd.append("<p style=\"text-align: center;\">");
        bd.append(ingredient);
        bd.append("</p>");
        return bd.toString();
    }
    
    public static String getTranslateInfoHtml() {
        StringBuilder bd = new StringBuilder();
        bd.append("<p style=\"text-align: center;\">");
        bd.append("지쿠스토어에서 번역한 상품설명이나 상품사용법중에는 오류를 포함할수 있습니다.");
        bd.append("</p>");
        return bd.toString();
    }
    
    public static String getLineHtml(String content) {
        StringBuilder bd = new StringBuilder();
        bd.append("<p style=\"text-align: center;\">");
        bd.append(content);
        bd.append("</p>");
        return bd.toString();
    }
    
    public abstract String getCategoryId();
    
    public abstract String getItemFullnameDE();
    
    public abstract String getItemFullnameKor();
    
    public abstract String getItemFullnameWithPrefix();
    
    public abstract String getPriceWonString();
    
    public abstract String getMainImageFileName();
    
    public abstract String getItemFullDescriptionManual();
    
    public abstract String getItemFullDescriptionDE();
    
    public abstract String getItemFullDescriptionKOR();
    
    public abstract MassItem getMassItem();
    
    public abstract String getPriceSaleWonString();
    
}
