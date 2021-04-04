package agencyController;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import agencyBrandEntities.ItemFeelway;
import util.ImageDownloader;

public class FeelwayController extends PriceCalc {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String ___LINE_BREAKER = "\n";
    
    private ItemFeelway itemFeelway;
    private String itemTitleEng;
    private String dirBrandItem; 

    public FeelwayController(ItemFeelway itemFeelway) {
        this.itemFeelway = itemFeelway;
        this.itemTitleEng= itemFeelway.getItemTitleEng();
        this.dirBrandItem = itemFeelway.getDirBrandItem();
        itemFeelway.setItemPriceWon(calculatePriceCommisionVATWon(
                itemFeelway.getItemPriceEuro(), itemFeelway.getItemDeliveryPrice()));
    }
    
    public void createProductData() {
        doImageDownloading();
        
        showRegisterData();
        
        //createExcelFile();
    }
    
    private void showRegisterData() {
        StringBuilder registerData = new StringBuilder();
        //상품정보
        registerData.append(itemFeelway.getItemBrandEng());
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getItemBrandGender() + "/" + itemFeelway.getItemCategory());
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getItemTitleKor());
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getItemModelNumber());
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getItemOriginCountry());
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
        registerData.append(itemFeelway.getItemProductCompany());
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getDirBrandItem());
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getItemPriceWon());
        registerData.append(___LINE_BREAKER);
        registerData.append("해외배송/판매자부담/택배/배송비 0원/" + itemFeelway.getItemDeliveryDuration());
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getSellerCallNumber());
        registerData.append(___LINE_BREAKER);
        registerData.append(itemFeelway.getSellerPhoneNumber());
        registerData.append(___LINE_BREAKER);
        //registerData.append(getIntroductionHtml());
        System.out.println(registerData.toString());
    }
    
    public void doImageDownloading() {
        List<String> itemImageUrl = itemFeelway.getItemImageUrl();
        for (int i=0; i<itemImageUrl.size(); i++) {
            String imageName = itemTitleEng + "_" + Integer.valueOf(i);
            imageDownload(imageName, dirBrandItem, itemImageUrl.get(i));
        }
    }
    
    public void imageDownload(final String imageName, final String imageDir, final String imageUrl) {
        ImageDownloader.run(imageName, imageDir, imageUrl);
    }
    
    public String getIntroductionHtml() {
        StringBuilder htmlBulder = new StringBuilder();
        htmlBulder.append("<p style=\"text-align: center;\"><span style=\"font-size: 12pt;\"><strong>명품 셀렉트샵 지쿠</strong></span></p>");
        htmlBulder.append("<p style=\"text-align: center;\"><span style=\"font-size: 12pt;\"><strong>유럽바잉, 유럽 직배송</strong></span></p>");
        htmlBulder.append("<p style=\"text-align: center;\">");
        htmlBulder.append("&#11208 상품명 :" + itemFeelway.getItemBrandKor() + " " + itemFeelway.getItemTitleKor() + "<br>");
        htmlBulder.append("&#11208 상품명(Eng) : " + itemFeelway.getItemBrandEng() + itemFeelway.getItemTitleEng() + "<br>");
        htmlBulder.append("&#11208 모델명 : " + itemFeelway.getItemModelNumber() + "<br>");
        htmlBulder.append("&#11208 소재 : " + itemFeelway.getItemMaterial() + "<br>");
        htmlBulder.append("&#11208 사이즈 : " + itemFeelway.getItemSizeList() + "<br>");
        htmlBulder.append("&#11208 컬러 : " + itemFeelway.getItemColor() + "<br>");
        htmlBulder.append("<p style=\"text-align:center;\"><img style=\"padding-bottom: 30px;\" src=\"" + itemFeelway.getGkooFeelwayInfo() + "\"/></p>");
        htmlBulder.append("<br>");
        htmlBulder.append("</p>");
        return htmlBulder.toString();
    }

    public void createExcelFile() {
        LOGGER.info("Creating the excel file for feelway starts...");
        
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(itemTitleEng);
        
        Map<String, Object[]> data = new TreeMap<String, Object[]>();     
        Object createdItemRow[] = createItemRow(itemFeelway);
        data.put(String.valueOf(1), createdItemRow);

        Set<String> keyset = data.keySet();
        int rownum = 1;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(dirBrandItem + "_" + itemTitleEng + "_feelway.xlsx");
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("the excel file for feelway is created!");
    }
    
    private Object[] createItemRow(ItemFeelway itemFeelway) {
        Object itemRow[] = new Object[66];
        //상품상태
        itemRow[0] = "신상품";
       
        return itemRow;
    }
}
