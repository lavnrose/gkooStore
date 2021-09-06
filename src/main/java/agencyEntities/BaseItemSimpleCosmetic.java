package agencyEntities;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import gkooAgentConfig.FlaconiConfig;

public class BaseItemSimpleCosmetic {
    private static final Logger LOGGER = LogManager.getLogger(BaseItemSimpleCosmetic.class);

    private static final String COMPANY_LOGO = "https://moondrive81.cafe24.com/GKoo/gkoo_comany_logo.png";
    private MassItem massItem;
    private FlaconiConfig config;
    
    public BaseItemSimpleCosmetic(MassItem massItem, FlaconiConfig config) {
        this.massItem = massItem;
        this.setConfig(config);
    }
    
    protected String getCompanyLogoUrl() {
        return COMPANY_LOGO;
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
    
    private String getImageUrlHtml(String imageUrl) {
        StringBuilder htmlBulder = new StringBuilder();
        htmlBulder.append("<p style=\"text-align:center;\"><img style=\"padding-bottom: 30px;\" src=\"" + imageUrl + "\"></p>");
        return htmlBulder.toString();
    }
    
    public String getItemFullDescription() {
        StringBuilder result = new StringBuilder();
        result.append(getItemBrandNameHtml(massItem.getBrandNameDE()));
        result.append(getEmptyLineHtml());
        result.append(getItemBrandNameHtml(massItem.getBrandNameKor()));
        result.append(getEmptyLineHtml());
        result.append(getImageUrlHtml(massItem.getMainImageUrl()));
        result.append(getEmptyLineHtml());
        result.append(getItemUsageHtml(massItem.getItemUsage()));
        result.append(getEmptyLineHtml());
        result.append(getItemIngredientHtml(massItem.getItemIngredients()));
        result.append(getEmptyLineHtml());
        result.append(getTranslateInfoHtml());
        return addTopBottomInfo(result.toString());
    }
    
    public MassItem getMassItem() {
        return massItem;
    }
    
    public String getItemFullnameWithPrefix() {
        String fullName = massItem.getBrandNameKor() + " "+ massItem.getItemTitleKor();
        return fullName;
    }

    public FlaconiConfig getConfig() {
        return config;
    }

    public void setConfig(FlaconiConfig config) {
        this.config = config;
    }
}
