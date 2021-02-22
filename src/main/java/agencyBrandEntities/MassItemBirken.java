package agencyBrandEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyEntities.BaseItem;
import agencyEntities.MassItem;
import util.Formatter;
import util.GrobalDefined.Gender;

public class MassItemBirken extends BaseItem  {
    private static final Logger LOGGER = LogManager.getLogger(MassItemBirken.class);

    private int priceWon;
    private int priceSaleWon;
    private int priceSubstractWon;
    private MassItem massItem;
    private List<String> detailImageUrlList = new ArrayList<>();
    
    public MassItemBirken(MassItem massItem) {
        this.massItem = massItem;
        this.priceWon = super.calculatePriceCommisionVATWon(massItem.getItemPriceEuro(), massItem.getModeDeiveryFee());
        if(massItem.isItemSale()) {
            this.priceSaleWon = super.calculatePriceCommisionVATWon(massItem.getItemSalePriceEuro(), massItem.getModeDeiveryFee());
            this.priceSubstractWon = priceWon - priceSaleWon;
        }
    }

    @Override
    public String getCategoryId() {
        return massItem.getCategoryId();
    }

    @Override
    public String getItemFullnameDE() {
        return massItem.getBrandNameDE() + " " + massItem.getItemTitleDE();
    }


    @Override
    public String getItemFullnameEN() {
        return massItem.getBrandNameDE() + " " + massItem.getItemTitleEN();
    }
    
    @Override
    public String getPriceWonString() {
        return String.valueOf(priceWon);
    }

    @Override
    public String getColorListString() {
        return null;
    }

    @Override
    public String getMainImageFileName() {
        return massItem.getMainImageFileName();
    }

    @Override
    public String getDetailPageCafe24() {
        String imagesCommaSeparated = null;
        if(massItem.getItemDetailImages() == null) {
            imagesCommaSeparated = "";
            LOGGER.warn("ImageLinks are empty");
            return "";
        } else {
            for(String image : massItem.getItemDetailImages()) {
                detailImageUrlList.add(transformToHtml(image));
                detailImageUrlList.add(transformToHtmlLogo(getCompanyLogoUrl()));
            }
            imagesCommaSeparated = detailImageUrlList.stream().collect(Collectors.joining(""));
            return addAlignment(addTopBottomInfo(addBuyingInfo(addWidthGuideInfo(addSizeInfo(imagesCommaSeparated)))));
        }
    }
    
    @Override
    public String getDetailPageSmart() {
        String imagesCommaSeparated = null;
        if(massItem.getItemDetailImages() == null) {
            imagesCommaSeparated = "";
            LOGGER.warn("ImageLinks are empty");
            return "";
        } else {
            for(String image : massItem.getItemDetailImages()) {
                detailImageUrlList.add(transformToHtml(image));
                detailImageUrlList.add(transformToHtmlLogo(getCompanyLogoUrl()));
            }
            imagesCommaSeparated = detailImageUrlList.stream().collect(Collectors.joining(""));
            return addAlignment(addTopBottomInfo(addBuyingInfo(addWidthGuideInfo(addSizeInfo(imagesCommaSeparated)))));
        }
    }
    
    private String addAlignment(String itemImagesHtml) {
        StringBuilder imageBuilder = new StringBuilder();
        imageBuilder.append("<p style=\"text-align:center;\">");
        imageBuilder.append(itemImagesHtml);
        imageBuilder.append("</p>");
        return imageBuilder.toString();
    }
    
    private String addItemUrlHidden(String itemImagesHtml) {
        StringBuilder imageBuilder = new StringBuilder();
        imageBuilder.append(itemImagesHtml);
        imageBuilder.append("<p hidden>");
        imageBuilder.append(massItem.getItemUrl());
        imageBuilder.append("  ");
        imageBuilder.append(massItem.getItemTitleDE());
        imageBuilder.append("</p>");
        return imageBuilder.toString();
    }
    
    private String addProductInfo(String itemImagesHtml) {
        StringBuilder bd = new StringBuilder();
        bd.append(itemImagesHtml);
        bd.append(getEmptyLineHtml());
        bd.append("<p style=\"text-align: center;\"><span style=\"font-size: 12pt;\"><strong>소재</strong></span></p>");
        bd.append(getEmptyLineHtml());
        bd.append("<p style=\"text-align: center;\">");
        bd.append(Formatter.formatWithoutComma(massItem.getMaterials()));
        bd.append("</p>");
        bd.append(getEmptyLineHtml());
        return bd.toString();
    }

    private String addSizeCheckButton(String itemImagesHtml) {
        StringBuilder bd = new StringBuilder();
        bd.append(itemImagesHtml);
        bd.append("<center><form target=\"_blank\" action=\"");
        bd.append(massItem.getItemUrl() + "\">");
        bd.append("<input type=\"submit\" value=\"사이즈 및 재고 체크\"></form></center>");
        bd.append(getEmptyLineHtml());
        return bd.toString();
    }
    
    private String addWidthGuideInfo(String itemImagesHtml) {
        Objects.requireNonNull(massItem.getBrandHomepageUrl());
        StringBuilder bd = new StringBuilder();
        bd.append(itemImagesHtml);
        bd.append("<p style=\"text-align: center;\"><span style=\"font-size: 12pt;\"><strong>발 넓이 선택</strong></span></p>");
        bd.append(getEmptyLineHtml());        
        bd.append("<p style=\"text-align: center;\">");
        bd.append("1.) 상품에 따라  보통발 또는 좁은발로 발넓이 선택이 가능합니다. <br>");
        bd.append("2.) 대부분 보통발 넓이를 주문하셔서 좁은발을 주문하실려면 안내문 보시고 주문 가능여부 문의주시길 부탁드립니다.<br>");
        bd.append("</p>");
        bd.append(getEmptyLineHtml());        
        return bd.toString();
    }
    
    private String addSizeInfo(String itemImagesHtml) {
        StringBuilder imageBuilder = new StringBuilder();
        imageBuilder.append(itemImagesHtml);
        imageBuilder.append("<center><img style=\"padding-bottom: 10px;\" src=\"https://moondrive81.cafe24.com/GKoo/mode/birkenstock/birkenstock_size_guide.jpg\"/></center>");
        imageBuilder.append("<center><img style=\"padding-bottom: 10px;\" src=\"https://moondrive81.cafe24.com/GKoo/mode/birkenstock/birkenstock_width_guide.jpg\"/></center>");
        imageBuilder.append(transformToHtmlLogo(getCompanyLogoUrl()));
        return imageBuilder.toString();
    }
    
    private String addTopBottomInfo(String itemImagesHtml) {
        StringBuilder imageBuilder = new StringBuilder();
        imageBuilder.append("<center><img style=\"padding-bottom: 30px;\" width=\"800\" src=\"https://moondrive81.cafe24.com/GKoo/gkoo_info_top.png\"/></center>");
        imageBuilder.append(itemImagesHtml);
        imageBuilder.append("<center><img src=\"https://moondrive81.cafe24.com/GKoo/gkoo_info_bottom.jpg\"/></center>");
        return imageBuilder.toString();
    }
    
    private String addBuyingInfo(String itemImagesHtml) {
        StringBuilder imageBuilder = new StringBuilder();
        imageBuilder.append(itemImagesHtml);
        imageBuilder.append("<center><img style=\"padding-bottom: 10px;\" src=\"https://moondrive81.cafe24.com/GKoo/gkooStoreModeInfo.png\"/></center>");
        return imageBuilder.toString();
    }
    
    private String transformToHtmlLogo(String itemImage) {
        StringBuilder htmlBulder = new StringBuilder();
        htmlBulder.append("<center><img width=\"500\" src=\"");
        htmlBulder.append(itemImage);
        htmlBulder.append("\" /></center>");
        return htmlBulder.toString();
    }
    
    private String transformToHtml(String itemImage) {
        StringBuilder htmlBulder = new StringBuilder();
        htmlBulder.append("<center><img width=\"800\" style=\"padding-bottom: 5px;\" src=\"");
        htmlBulder.append(itemImage);
        htmlBulder.append("\" /></center>");
        return htmlBulder.toString();
    }

    @Override
    public String getSizeListString() {
        return getListToString(massItem.getItemSizes());
    }
    
    @Override
    public String getSizeOptionCafe24() {
        return "사이즈{" + massItem.getItemSizes().stream().collect(Collectors.joining("|")) + "}" + "//발넓이{보통발|좁은발}";
    }

    @Override
    public String getSizeListPriceString() {
        return getListToString(massItem.getItemSizesPrice());
    }

    @Override
    public String getSizeListStockString() {
        return getListToString(massItem.getItemSizesStock());
    }

    @Override
    public String getItemUrl() {
        return massItem.getItemUrl();
    }

    @Override
    public MassItem getMassItem() {
        return massItem;
    }

    @Override
    public String getPriceSubstractWonString() {
        return String.valueOf(priceSubstractWon);
    }

    @Override
    public boolean isItemSale() {
        return massItem.isItemSale();
    }

    @Override
    public String getPriceSaleWonString() {
        return String.valueOf(priceSaleWon);
    }
}
