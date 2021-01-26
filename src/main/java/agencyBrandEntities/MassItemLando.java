package agencyBrandEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyEntities.BaseItem;
import agencyEntities.MassItem;
import util.GrobalDefined.Gender;

public class MassItemLando extends BaseItem {
    private static final Logger LOGGER = LogManager.getLogger(MassItemLando.class);

    private int priceWon;
    private int priceSubstractWon;
    private MassItem massItem;
    private List<String> detailImageUrlList = new ArrayList<>();
    
    public MassItemLando(MassItem massItem) {
        this.massItem = massItem;
        this.priceWon = super.calculatePriceCommisionWon(massItem.getItemPriceEuro());
        this.priceSubstractWon = massItem.isItemSale() ? super.calculatePriceNoCommisionWon(massItem.getItemPriceEuro() - massItem.getItemSalePriceEuro()) : 0;
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
            return addItemUrlHidden(addAlignment(addTopBottomInfo(addBuyingInfo(addSizeInfo(addProductInfo(imagesCommaSeparated))))));
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
        bd.append("<p style=\"text-align: center;\"><span style=\"font-size: 12pt;\"><strong>소재</strong></span></p>");
        bd.append(getEmptyLineHtml());
        bd.append("<p style=\"text-align: center;\">");
        bd.append(massItem.getMaterials());
        bd.append("</p>");
        //bd.append(getLineHtml("contents"));
        return bd.toString();
    }
    
    private String addSizeInfo(String itemImagesHtml) {
        StringBuilder imageBuilder = new StringBuilder();
        imageBuilder.append(itemImagesHtml);
        if (massItem.getGender().equals(Gender.MALE)) {
            imageBuilder.append("<center><img style=\"padding-bottom: 10px;\" src=\"https://moondrive81.cafe24.com/GKoo/size_guide/men_size_guide_zal.jpg\"/></center>");
            imageBuilder.append("<center><img style=\"padding-bottom: 10px;\" src=\"https://moondrive81.cafe24.com/GKoo/size_guide/men_body_guide_zal.jpg\"/></center>");
        } else if (massItem.getGender().equals(Gender.FEMALE)){
            imageBuilder.append("<center><img style=\"padding-bottom: 10px;\" src=\"https://moondrive81.cafe24.com/GKoo/size_guide/women_size_guide_zal.jpg\"/></center>");
        }
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
    
    //https://moondrive81.cafe24.com/GKoo/gkooStoreInfo.png
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
        //String result = itemImage.split(".jpg")[0];
        //String itemImageFullname = result + ".jpg";
        
        StringBuilder htmlBulder = new StringBuilder();
        htmlBulder.append("<center><img width=\"800\" style=\"padding-bottom: 5px;\" src=\"");
        //htmlBulder.append("<img src=\"");
        htmlBulder.append(itemImage);
        htmlBulder.append("\" /></center>");
        return htmlBulder.toString();
    }

    @Override
    public String getSizeListString() {
        return getListToString(massItem.getItemSizes());
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
    public String getDetailPageCafe24() {
        return "";
    }

    @Override
    public String getSizeOptionCafe24() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPriceSaleWonString() {
        // TODO Auto-generated method stub
        return null;
    }
}