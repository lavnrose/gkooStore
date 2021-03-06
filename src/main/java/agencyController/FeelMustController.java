package agencyController;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyBrandEntities.ItemFeelway;
import factoryExcel.ExcelFile;
import gkooModeAgency.AgentFeelMustStarter;
import util.Formatter;
import util.ImageDownloader;

public class FeelMustController extends PriceCalc {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String ___LINE_BREAKER = "\n";
    
    private ItemFeelway itemFeelway;
    private String itemTitleEng;
    private String dirBrandItem; 

    public FeelMustController(ItemFeelway itemFeelway) {
        this.itemFeelway = itemFeelway;
        this.itemTitleEng= itemFeelway.getItemTitleEng();
        this.dirBrandItem = itemFeelway.getDirBrandItem();
        itemFeelway.setItemPriceWon(calculatePriceCommisionVATWon(
                itemFeelway.getItemPriceEuro(), itemFeelway.getItemDeliveryPrice()));
    }
    
    public void createProductData(boolean stock) {
        if (stock)
            doImageDownloading();
        else
            setImageUrl();
        
        showRegisterData();
        
        ExcelFile.createExcelFeelMust(itemFeelway);
    }
    
    private void showRegisterData() {
        StringBuilder registerData = new StringBuilder();
        //상품정보
        registerData.append(itemFeelway.getItemBrandEng());
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getItemBrandGender() + "/" + itemFeelway.getItemCategory());
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getItemBrandKor() + " " + itemFeelway.getItemTitleKor());
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getItemModelNumber());
        registerData.append(___LINE_BREAKER);
        String origConuntry = itemFeelway.getItemOriginCountry();
        registerData.append(origConuntry == null ? "원산지(제조국): " : origConuntry);
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getItemColor());
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getItemSizeList());
        registerData.append(___LINE_BREAKER);
        //상품고시정보
        registerData.append(itemFeelway.getItemMaterial());
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getItemColor());
        registerData.append(___LINE_BREAKER);
        String prdCompanay = itemFeelway.getItemProductCompany();
        registerData.append(prdCompanay == null ? "제조사/수입사: " : prdCompanay);
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getDirBrandItem());
        registerData.append(___LINE_BREAKER);
        registerData.append("보증서 없음/택");
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getItemPriceWon());
        registerData.append(___LINE_BREAKER);
        registerData.append("해외배송/판매자부담/택배/배송비 0원/" + itemFeelway.getItemDeliveryDuration());
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getSellerCallNumber());
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getSellerPhoneNumber());
        registerData.append(___LINE_BREAKER);
        registerData.append(getIntroductionHtml());
        System.out.println(registerData.toString());
    }
    
    public void doImageDownloading() {
        List<String> itemImageUrl = itemFeelway.getItemImageUrl();
        for (int i=0; i<itemImageUrl.size(); i++) {
            String imageName = Formatter.replaceEmptySymbol(itemTitleEng) + "_" + Integer.valueOf(i);
            if (i==0) {
                itemFeelway.setItemMainImageName(imageName + ".jpg");
            }
            setImageUploadUrl(imageName);
            imageDownload(imageName, dirBrandItem, itemImageUrl.get(i));
        }
    }
    
    public void setImageUrl() {
        List<String> itemImageUrl = itemFeelway.getItemImageUrl();
        for (int i=0; i<itemImageUrl.size(); i++) {
            String imageName = Formatter.replaceEmptySymbol(itemTitleEng) + "_" + Integer.valueOf(i);
            if (i==0) {
                itemFeelway.setItemMainImageName(imageName + ".jpg");
            }
            //setImageUploadUrl(imageName);
            itemFeelway.getImageUploadUrl().add(itemImageUrl.get(i));
            imageDownload(imageName, dirBrandItem, itemImageUrl.get(i));
        }
    }
    
    private void setImageUploadUrl(String imageName) {
        String imageUploadUrl = AgentFeelMustStarter.DIR_CAFE24_UPLOAD + "/" + imageName + ".jpg";
        itemFeelway.getImageUploadUrl().add(imageUploadUrl);
    }
    
    public void imageDownload(final String imageName, final String imageDir, final String imageUrl) {
        ImageDownloader.run(imageName, imageDir, imageUrl);
    }
    
    public String getIntroductionHtml() {
        StringBuilder htmlBulder = new StringBuilder();
        htmlBulder.append(getImageUrlHtml());
        htmlBulder.append("<p style=\"text-align: center;\"><span style=\"font-size: 14pt;\"><strong>명품 셀렉트샵 지쿠</strong></span><br><br></p>");
        htmlBulder.append("<p style=\"text-align: center;\"><span style=\"font-size: 14pt;\"><strong>유럽바잉, 유럽 직배송</strong></span><br><br></p>");
        htmlBulder.append("<p style=\"text-align: center;\">상품명 : " + itemFeelway.getItemBrandKor() + " " + itemFeelway.getItemTitleKor() + "<br><br></p>");
        htmlBulder.append("<p style=\"text-align: center;\">상품명(Eng) : " + itemFeelway.getItemBrandEng() + " " + itemFeelway.getItemTitleEng() + "<br><br></p>");
        htmlBulder.append("<p style=\"text-align: center;\">모델명 : " + itemFeelway.getItemModelNumber() + "<br><br></p>");
        htmlBulder.append("<p style=\"text-align: center;\">소재 : " + itemFeelway.getItemMaterial() + "<br><br></p>");
        htmlBulder.append("<p style=\"text-align: center;\">사이즈 : " + itemFeelway.getItemSizeList() + "<br><br></p>");
        htmlBulder.append("<p style=\"text-align: center;\">컬러 : " + itemFeelway.getItemColor() + "<br><br></p>");
        htmlBulder.append("<p style=\"text-align:center;\"><img style=\"padding-bottom: 30px;\" src=\"" + itemFeelway.getGkooFeelwayInfo() + "\"></p>");
        htmlBulder.append("<br>");
        htmlBulder.append("</p>");
        String introductionHtml = htmlBulder.toString(); 
        itemFeelway.setIntroductionHtml(introductionHtml);
        return introductionHtml;
    }
    
    private String getImageUrlHtml() {
        StringBuilder htmlBulder = new StringBuilder();
        for (String imageUrl : itemFeelway.getImageUploadUrl()) {
            htmlBulder.append("<p style=\"text-align:center;\"><img style=\"padding-bottom: 30px;\" src=\"" + imageUrl + "\"></p>");
            htmlBulder.append("<p style=\"text-align:center;\"><img style=\"padding-bottom: 30px;\" src=\"" + itemFeelway.getGkooLogo() + "\"></p>");
        }
        return htmlBulder.toString();
    }
    
    public ItemFeelway getItemFeelway() {
        return itemFeelway;
    }
}
