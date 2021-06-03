package agencyBrandEntities;

import java.util.ArrayList;
import java.util.List;

public class ItemFeelway {
    //상품정보
    private String itemBrandEng;
    private String itemBrandKor;
    private String itemBrandGender;
    private String itemCategory;
    private String itemTitleEng;
    private String itemTitleKor;
    private String itemModelNumber;
    private String itemOriginCountry;
    private String itemBuyingYear;
    private String itemBuyingPlace;
    private String itemStockVolume;
    private String itemSizeTableUrl;
    private String itemSizeList;
    private String dirBrandItem;
    
    //상품고시정보
    private String itemMaterial;
    private String itemColor;
    private String itemProductCompany;
    private String itemUsingCaution;
    private String itemQualityGuaranty;

    //상품사진
    private List<String> itemImageUrl = new ArrayList<>();
    private List<String> imageUploadUrl = new ArrayList<>();
    
    //기타정보
    private String smartStoreCategoryId;
    private String dirItem;
    private String introductionHtml;
    private String itemMainImageName;
    
    //판매가격
    private int itemPriceEuro;
    private int itemPriceWon;
    private int itemCoupon;
    private int itemDeliveryPrice;
    private final String itemDeliveryDuration = "7~15일정도 소요예정";
    
    //판매자 정보
    private final String sellerCallNumber = "070-4001-8993";
    private final String sellerPhoneNumber = "010-7348-1193";
    
    private final String gkooLogo = "https://moondrive81.cafe24.com/GKoo/mode/gkoo_comany_logo.png";
    private final String gkooFeelwayInfo = "https://moondrive81.cafe24.com/GKoo/mode/gkooFeelwayInfo.jpg";
    
    public ItemFeelway() { }
    
    public String getItemBrandEng() {
        return itemBrandEng;
    }

    public void setItemBrandEng(String itemBrandEng) {
        this.itemBrandEng = itemBrandEng;
    }

    public String getItemBrandKor() {
        return itemBrandKor;
    }

    public void setItemBrandKor(String itemBrandKor) {
        this.itemBrandKor = itemBrandKor;
    }

    public String getItemTitleEng() {
        return itemTitleEng;
    }

    public void setItemTitleEng(String itemTitleEng) {
        this.itemTitleEng = itemTitleEng;
    }

    public String getItemTitleKor() {
        return itemTitleKor;
    }

    public void setItemTitleKor(String itemTitleKor) {
        this.itemTitleKor = itemTitleKor;
    }

    public String getItemModelNumber() {
        return itemModelNumber;
    }

    public void setItemModelNumber(String itemModelNumber) {
        this.itemModelNumber = itemModelNumber;
    }

    public String getItemOriginCountry() {
        return itemOriginCountry;
    }

    public void setItemOriginCountry(String itemOriginCountry) {
        this.itemOriginCountry = itemOriginCountry;
    }

    public String getItemBuyingYear() {
        return itemBuyingYear;
    }

    public void setItemBuyingYear(String itemBuyingYear) {
        this.itemBuyingYear = itemBuyingYear;
    }

    public String getItemBuyingPlace() {
        return itemBuyingPlace;
    }

    public void setItemBuyingPlace(String itemBuyingPlace) {
        this.itemBuyingPlace = itemBuyingPlace;
    }

    public String getItemStockVolume() {
        return itemStockVolume;
    }

    public void setItemStockVolume(String itemStockVolume) {
        this.itemStockVolume = itemStockVolume;
    }

    public String getItemMaterial() {
        return itemMaterial;
    }

    public void setItemMaterial(String itemMaterial) {
        this.itemMaterial = itemMaterial;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public String getItemProductCompany() {
        return itemProductCompany;
    }

    public void setItemProductCompany(String itemProductCompany) {
        this.itemProductCompany = itemProductCompany;
    }

    public String getItemUsingCaution() {
        return itemUsingCaution;
    }

    public void setItemUsingCaution(String itemUsingCaution) {
        this.itemUsingCaution = itemUsingCaution;
    }

    public String getItemQualityGuaranty() {
        return itemQualityGuaranty;
    }

    public void setItemQualityGuaranty(String itemQualityGuaranty) {
        this.itemQualityGuaranty = itemQualityGuaranty;
    }

    public List<String> getItemImageUrl() {
        return itemImageUrl;
    }

    public void setItemImageUrl(List<String> itemImageUrl) {
        this.itemImageUrl = itemImageUrl;
    }

    public int getItemPriceEuro() {
        return itemPriceEuro;
    }

    public void setItemPriceEuro(int itemPriceEuro) {
        this.itemPriceEuro = itemPriceEuro;
    }
    
    public int getItemPriceWon() {
        return itemPriceWon;
    }

    public void setItemPriceWon(int itemPriceWon) {
        this.itemPriceWon = itemPriceWon;
    }

    public int getItemCoupon() {
        return itemCoupon;
    }

    public void setItemCoupon(int itemCoupon) {
        this.itemCoupon = itemCoupon;
    }

    public int getItemDeliveryPrice() {
        return itemDeliveryPrice;
    }

    public void setItemDeliveryPrice(int itemDeliveryPrice) {
        this.itemDeliveryPrice = itemDeliveryPrice;
    }

    public String getItemDeliveryDuration() {
        return itemDeliveryDuration;
    }

    public String getSellerCallNumber() {
        return sellerCallNumber;
    }

    public String getSellerPhoneNumber() {
        return sellerPhoneNumber;
    }

    public String getItemBrandGender() {
        return itemBrandGender;
    }

    public void setItemBrandGender(String itemBrandGender) {
        this.itemBrandGender = itemBrandGender;
    }

    public String getDirBrandItem() {
        return dirBrandItem;
    }

    public void setDirBrandItem(String dirBrandItem) {
        this.dirBrandItem = dirBrandItem;
    }

    public String getItemSizeTableUrl() {
        return itemSizeTableUrl;
    }

    public void setItemSizeTableUrl(String itemSizeTableUrl) {
        this.itemSizeTableUrl = itemSizeTableUrl;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemSizeList() {
        return itemSizeList;
    }

    public void setItemSizeList(String itemSizeList) {
        this.itemSizeList = itemSizeList;
    }

    public String getGkooFeelwayInfo() {
        return gkooFeelwayInfo;
    }

    public List<String> getImageUploadUrl() {
        return imageUploadUrl;
    }

    public void setImageUploadUrl(List<String> imageUploadUrl) {
        this.imageUploadUrl = imageUploadUrl;
    }

    public String getGkooLogo() {
        return gkooLogo;
    }

    public String getSmartStoreCategoryId() {
        return smartStoreCategoryId;
    }

    public void setSmartStoreCategoryId(String smartStoreCategoryId) {
        this.smartStoreCategoryId = smartStoreCategoryId;
    }

    public String getDirItem() {
        return dirItem;
    }

    public void setDirItem(String dirItem) {
        this.dirItem = dirItem;
    }

    public String getIntroductionHtml() {
        return introductionHtml;
    }

    public void setIntroductionHtml(String introductionHtml) {
        this.introductionHtml = introductionHtml;
    }

    public String getItemMainImageName() {
        return itemMainImageName;
    }

    public void setItemMainImageName(String itemMainImageName) {
        this.itemMainImageName = itemMainImageName;
    }
}
