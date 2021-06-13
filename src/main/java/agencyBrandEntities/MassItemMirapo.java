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

public class MassItemMirapo extends BaseItem {
    private static final Logger LOGGER = LogManager.getLogger(MassItemBirken.class);

    private int priceWon;
    private int priceWonCoupang;
    private int priceSaleWon;
    private int priceSubstractWon;
    private MassItem massItem;
    private List<String> detailImageUrlList = new ArrayList<>();
    
    public MassItemMirapo(MassItem massItem) {
        this.massItem = massItem;
        this.priceWon = super.calculatePriceCommisionVATWon(massItem.getItemPriceEuro(), massItem.getModeDeiveryFee());
        if(massItem.isItemSale()) {
            this.priceSaleWon = super.calculatePriceCommisionVATWon(massItem.getItemSalePriceEuro(), massItem.getModeDeiveryFee());
            this.priceSubstractWon = priceWon - priceSaleWon;
        }
        this.priceWonCoupang = super.calculatePriceCommisionVATWonCoupang(massItem.getItemPriceEuro());
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
            return addAlignment(addTopBottomInfo(addBuyingInfo(addVariablePriceInfo(addSizeInfo(imagesCommaSeparated)))));
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
            return addAlignment(addTopBottomInfo(addBuyingInfo(addVariablePriceInfo(addSizeInfo(imagesCommaSeparated)))));
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
    
    private String addVariablePriceInfo(String itemImagesHtml) {
        Objects.requireNonNull(massItem.getBrandHomepageUrl());
        StringBuilder bd = new StringBuilder();
        if(massItem.isPriceVariable()) {
            bd.append(itemImagesHtml);
            bd.append("<p style=\"text-align: center;\"><span style=\"font-size: 12pt;\"><strong>사이즈별 가격 변동 안내 및 재고 문의부탁드립니다</strong></span></p>");
            bd.append(getEmptyLineHtml());        
            bd.append("<p style=\"text-align: center;\">");
            bd.append("1.) 위 상품은 사이즈별로 가격이 다르니 구매전에 재고여부와 함께 가격여부 문의주시기 바랍니다.<br>");
            bd.append("2.) 재고를 문의해주시지 않고 주문하셔서 품절로 확인이 될 경우 상품품절로 인한 취소가 아닌 잘못 주문할걸로 취소처리됩니다.<br>");
            bd.append("</p>");
            bd.append(getEmptyLineHtml());     
        } else {
            bd.append(itemImagesHtml);
            bd.append("<p style=\"text-align: center;\"><span style=\"font-size: 12pt;\"><strong>재고문의 부탁드립니다</strong></span></p>");
            bd.append(getEmptyLineHtml());        
            bd.append("<p style=\"text-align: center;\">");
            bd.append("1.) 재고를 문의해주시지 않고 주문하셔서 품절로 확인이 될 경우 상품품절로 인한 취소가 아닌 잘못 주문할걸로 취소처리됩니다.<br>");
            bd.append("</p>");
            bd.append(getEmptyLineHtml());     
        }
          
        return bd.toString();
    }
    
    private String addSizeInfo(String itemImagesHtml) {
        StringBuilder imageBuilder = new StringBuilder();
        imageBuilder.append(itemImagesHtml);
        imageBuilder.append("<center><img style=\"padding-bottom: 10px;\" src=\"https://moondrive81.cafe24.com/GKoo/mode/mirapodo/gabor/gabor_size_guide.jpg\"/></center>");
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
        htmlBulder.append("<center><img width=\"500\" style=\"padding-bottom: 5px;\" src=\"");
        //htmlBulder.append("<center><img style=\"padding-bottom: 5px;\" src=\"");
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
        return "사이즈{" + massItem.getItemSizes().stream().collect(Collectors.joining("|")) + "}";
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

    @Override
    public String getPriceWonCoupangString() {
        return String.valueOf(priceWonCoupang);
    }
}
