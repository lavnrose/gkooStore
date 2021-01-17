package agencyEntities;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class BaseItemCosmetic {
    private static final String COMPANY_LOGO = "https://moondrive81.cafe24.com/GKoo/gkoo_comany_logo.png";
    private static final String ITEM_BRAND_INFO = "https://moondrive81.cafe24.com/GKoo/product_brand_name.png";
    private static double excahgeRateEuro = 1400;
    private static int feePercent = 7;
    private static double minimumCommision = 3000;
    //2자리리 내림 ex. 10511원? -> 10500원?
    private final int ROUNDED_DIGIT = 2;
    
    protected String getCompanyLogoUrl() {
        return COMPANY_LOGO;
    }
    
    /**
     * @return 브랜드소개 이미지
     */
    protected String getItemBrandInfoUrl() {
        return ITEM_BRAND_INFO;
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
        //imageBuilder.append(", ");
        imageBuilder.append(itemImagesHtml);
        //imageBuilder.append(", ");
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
        //bd.append(getLineHtml("contents"));
        return bd.toString();
    }
    
    public static String getItemUsageHtml(String usage) {
        StringBuilder bd = new StringBuilder();
        bd.append("<p style=\"text-align: center;\"><span style=\"font-size: 12pt;\"><strong>사용법</strong></span></p>");
        bd.append(getEmptyLineHtml());
        bd.append("<p style=\"text-align: center;\">");
        bd.append(usage);
        bd.append("</p>");
        //bd.append(getLineHtml("contents"));
        return bd.toString();
    }
    
    public static String getItemIngredientHtml(String ingredient) {
        StringBuilder bd = new StringBuilder();
        bd.append("<p style=\"text-align: center;\"><span style=\"font-size: 12pt;\"><strong>성분표</strong></span></p>");
        bd.append(getEmptyLineHtml());
        bd.append("<p style=\"text-align: center;\">");
        bd.append(ingredient);
        bd.append("</p>");
        //bd.append(getLineHtml("contents"));
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
    
    public abstract String getItemFullname();
    
    public abstract String getItemFullnameWithPrefix();
    
    public abstract String getPriceWonString();
    
    public abstract String getMainImageFileName();
    
    public abstract String getItemFullDescriptionManual();
    
    public abstract String getItemFullDescriptionDE();
    
    public abstract String getItemFullDescriptionKOR();
    
    public abstract String getItemTitleDE();
    
    public abstract MassItem getMassItem();
}
