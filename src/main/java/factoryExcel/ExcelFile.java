package factoryExcel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import agencyBrandEntities.ItemFeelway;

public class ExcelFile {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void createExcelFeelMust(ItemFeelway itemFeelway) {
        LOGGER.info("Creating the excel for smartstore starts...");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(itemFeelway.getItemBrandEng());
        
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        int i = 0;
        Object createdItemRow[] = createItemRow(itemFeelway);
        data.put(String.valueOf(i+1), createdItemRow);

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
            FileOutputStream outputStream = new FileOutputStream(itemFeelway.getDirBrandItem() + "/" + itemFeelway.getItemTitleEng() + ".xlsx");
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("the excel for smartstore is created");
    }
    
    private static Object[] createItemRow(ItemFeelway itemFeelway) {
        Object itemRow[] = new Object[66];
        //상품상태
        itemRow[0] = itemFeelway.getItemBrandEng();
        itemRow[1] = itemFeelway.getItemBrandGender() + "/" + itemFeelway.getItemCategory();
        itemRow[2] = itemFeelway.getItemTitleKor();
        itemRow[3] = itemFeelway.getItemModelNumber();
        String origConuntry = itemFeelway.getItemOriginCountry();
        itemRow[4] = origConuntry == null ? "원산지(제조국): " : origConuntry;
        itemRow[5] = itemFeelway.getItemColor();
        itemRow[6] = itemFeelway.getItemSizeList();
        itemRow[7] = itemFeelway.getItemMaterial();
        itemRow[8] = itemFeelway.getItemMaterial();
        String prdCompanay = itemFeelway.getItemProductCompany();
        itemRow[9] = prdCompanay == null ? "제조사/수입사: " : prdCompanay;
        itemRow[10] = itemFeelway.getDirBrandItem();
        itemRow[11] = "보증서 없음/택";
        itemRow[12] = itemFeelway.getItemPriceWon();
        itemRow[13] = "해외배송/판매자부담/택배/배송비 0원/" + itemFeelway.getItemDeliveryDuration();
        itemRow[14] = itemFeelway.getSellerCallNumber();
        itemRow[15] = itemFeelway.getSellerPhoneNumber();
        itemRow[16] = itemFeelway.getIntroductionHtml();
        return itemRow;
    }
}
