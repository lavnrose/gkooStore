package factoryExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import agencyBrandEntities.MassItemSimpleCosmetic;
import agencyEntities.BaseItemCosmetic;
import agencyEntities.BasePreItemCosmetic;
import agencyEntities.MassItem;

public class XlsxFile {
    private static final Logger LOGGER = LogManager.getLogger();

    private Object[] createItemHeader() {
        String header = "상품 url, 상품명 DE, 상품명 kor+용량, 상품가격유로, 상품가격원화, 상품원료, 이미지명, 이미지 url, 사용법";
        List<String> headerList= Stream.of(header.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        
        String[] stockArr = new String[headerList.size()];
        stockArr = headerList.toArray(stockArr);
        
        Object itemRow[] = stockArr;
        return itemRow;
    }
    
    public void createExcelFileCosmetic(List<BasePreItemCosmetic> baseItemCosmeticList, String filename, String dirExcelFile) {
        LOGGER.info("Creating the preprocess file starts...");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("cosmetic");
        
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put(String.valueOf(0), createItemHeader());
        for (int i=0;i< baseItemCosmeticList.size();i++) {
            Object createdItemRow[] = createItemRowCosmetic(baseItemCosmeticList.get(i));
            data.put(String.valueOf(i+1), createdItemRow);
        }

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
            FileOutputStream outputStream = new FileOutputStream(dirExcelFile + filename + "_preprocess.xlsx");
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("the preprocess file is created");
    }
    
    private Object[] createItemRowCosmetic(BasePreItemCosmetic baseItemCosmetic) {
        MassItem item = baseItemCosmetic.getMassItem();
        
        Object itemRow[] = new Object[9];
        
        //상품 url
        itemRow[0] = item.getItemUrl();
        
        //상품명 DE
        itemRow[1] = item.getItemTitleDE();
        
        //상품명 kor + 용량
        itemRow[2] = item.getItemTitleKor() + " " + item.getItemVolume();
        
        //상품가격유로
        itemRow[3] = String.valueOf(item.getItemPriceEuro());
        
        //상품가격원화
        int itemPrice = (int) item.getItemPriceWon();
        itemRow[4] = String.valueOf(itemPrice);
        
        //상품원료
        itemRow[5] = item.getItemIngredients();
        
        //이미지명
        itemRow[6] = item.getMainImageName();
        
        //이미지 url
        itemRow[7] = item.getMainImageUrl();

        //사용법 추가해야함
        itemRow[8] = "";
        return itemRow;
    }
    
    public void xlsxToObject(String fullPath_fileName, List<MassItem> massItemList) throws IOException {
        FileInputStream file = new FileInputStream(new File(fullPath_fileName));

        //Create Workbook instance holding reference to .xlsx file
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        //Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);
        
        for(int i=sheet.getFirstRowNum()+1;i<=sheet.getLastRowNum();i++){
            MassItem massItem = new MassItem();
            Row ro=sheet.getRow(i);
            for(int j=ro.getFirstCellNum();j<=ro.getLastCellNum();j++){
                Cell ce = ro.getCell(j);
              if(j==0){  
                  //If you have Header in text It'll throw exception because it won't get NumericValue
                  massItem.setItemUrl(ce.getStringCellValue());
              }
              if(j==1){
                  massItem.setItemTitleDE(ce.getStringCellValue());
              }
              if(j==2){
                  massItem.setItemTitleKor(ce.getStringCellValue());
              }  
              if(j==3){
                  String priceEuro = ce.getStringCellValue();
                  massItem.setItemPriceEuro(Double.valueOf(priceEuro));
              } 
              if(j==4){
                  String priceWonStr = ce.getStringCellValue();
                  int priceWonInt = Integer.valueOf(priceWonStr);
                  massItem.setItemPriceWon(priceWonInt);
              }
              if(j==5){
                  massItem.setItemIngredients(ce.getStringCellValue());
              }
              if(j==6){
                  massItem.setMainImageName(ce.getStringCellValue());
              }
              if(j==7){
                  massItem.setMainImageUrl(ce.getStringCellValue());
              }
              if(j==8){
                  massItem.setItemUsage(ce.getStringCellValue());
              }
            }
            massItemList.add(massItem);
        }
        file.close();
    }
}
