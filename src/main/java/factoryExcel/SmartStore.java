package factoryExcel;

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
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import agencyBrandEntities.ItemFeelway;
import agencyEntities.BaseItem;
import agencyEntities.BaseItemCosmetic;
import gkooAgency.AgentZal;
import util.Formatter;
import util.GrobalDefined;

public class SmartStore {
    private static final Logger LOGGER = LogManager.getLogger();

    private String categoryId;
    private String itemTitlePrefix;
    private String brandName;
    private List<BaseItem> massItemList;
    private List<BaseItemCosmetic> massItemCosmeticList;
    
    public SmartStore(List<BaseItem> massItemList, String categoryId, String itemTitlePrefix,  List<BaseItemCosmetic> massItemCosmeticList) {
        this.massItemList = massItemList;
        this.categoryId = categoryId;
        this.itemTitlePrefix = itemTitlePrefix;
        this.massItemCosmeticList = massItemCosmeticList;
    }
    
    /**
     * Clothes
     * 
     * @param massItemList
     * @param categoryId
     * @param itemTitlePrefix
     */
    public SmartStore(List<BaseItem> massItemList, String categoryId, String itemTitlePrefix) {
        this(massItemList, categoryId, itemTitlePrefix, null);
    }
    
    /**
     * clothes for SmartStore and Feelway
     */
    public SmartStore() { }
    
    /**
     * 
     * Cosmetics
     * 
     * @param categoryId
     * @param itemTitlePrefix
     * @param massItemCosmeticList
     */
    public SmartStore(String categoryId, String itemTitlePrefix, List<BaseItemCosmetic> massItemCosmeticList) {
       this(null, categoryId, itemTitlePrefix, massItemCosmeticList);
    }
    
    /**
     * 
     * block Cosmetics
     * 
     * @param massItemCosmeticList
     */
    public SmartStore(String brandName, List<BaseItemCosmetic> massItemCosmeticList) {
        this.brandName = brandName;
        this.massItemCosmeticList = massItemCosmeticList;
    }
    
    public static void main(String [] args) {
        //createExcel
    }
    
    //Zalando
    public void createExcelLando(String dirExcelFile) {
        LOGGER.info("Creating the excel for smartstore starts...");
        //excel 2007
        //XSSFWorkbook workbook = new XSSFWorkbook();
        //XSSFSheet sheet = workbook.createSheet(brandName);
       
        //excel 2007, https://www.codejava.net/coding/how-to-write-excel-files-in-java-using-apache-poi
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("zalando");
        
        Map<String, Object[]> data = new TreeMap<String, Object[]>();     
        for (int i=0;i<massItemList.size();i++) {
            Object createdItemRow[] = createItemRowLando(massItemList.get(i));
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
            //FileOutputStream outputStream = new FileOutputStream(CrawlerEcoverde.DIR_EXCEL_FILE + brandName + "_smartstore_ready.xlsx");
            FileOutputStream outputStream = new FileOutputStream(dirExcelFile + "_smartstore_ready.xls");
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("the excel for smartstore is created");
    }
    
    //Mode
    public void createExcelMode(String dirExcelFile) {
        //for test excel file
        LOGGER.info("Creating the test excel for smartstore starts...");
        createExcelModeTest(dirExcelFile);
        
        LOGGER.info("Creating the excel for smartstore starts...");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("maje");
        
        Map<String, Object[]> data = new TreeMap<String, Object[]>();     
        for (int i=0;i<massItemList.size();i++) {
            Object createdItemRow[] = createItemRowMode(massItemList.get(i));
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
            FileOutputStream outputStream = new FileOutputStream(dirExcelFile + itemTitlePrefix + "_smartstore.xlsx");
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("the excel for smartstore is created");
    }
    
    //Mode
    public void createCsvMode(ItemFeelway itemFeelway) {
        LOGGER.info("Creating the excel for smartstore starts...");
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet(itemFeelway.getItemTitleEng());
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(itemFeelway.getItemTitleEng());
        
        Map<String, Object[]> data = new TreeMap<String, Object[]>();     
        Object createdItemRow[] = createItemCsvMode(itemFeelway);
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
            FileOutputStream outputStream = new FileOutputStream(itemFeelway.getDirBrandItem() + "/" + itemFeelway.getItemTitleKor() + "_smartstore.xlsx");
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("the excel for smartstore is created");
    }
    
    //one item for test
    private void createExcelModeTest(String dirExcelFile) {
        LOGGER.info("Creating the excel for smartstore starts...");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("maje");
        
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        int i = 0;
        Object createdItemRow[] = createItemRowMode(massItemList.get(i));
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
            FileOutputStream outputStream = new FileOutputStream(dirExcelFile + itemTitlePrefix + "_smartstore_test.xlsx");
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("the excel for smartstore is created");
    }
    
    //block cosmetic
    public void createExcelBlock(String dirExcelFile) {
        LOGGER.info("Creating the excel for smartstore starts...");
        //excel 2007
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(brandName);
       
        //excel 2007, https://www.codejava.net/coding/how-to-write-excel-files-in-java-using-apache-poi
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        HSSFSheet sheet = workbook.createSheet(brandName);
        
        Map<String, Object[]> data = new TreeMap<String, Object[]>();     
        for (int i=0;i<massItemCosmeticList.size();i++) {
            Object createdItemRow[] = createBlockItemRow(massItemCosmeticList.get(i));
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
            //FileOutputStream outputStream = new FileOutputStream(CrawlerEcoverde.DIR_EXCEL_FILE + brandName + "_smartstore_ready.xlsx");
            FileOutputStream outputStream = new FileOutputStream(dirExcelFile + brandName + "_smartstore_ready.xls");
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("the excel for smartstore is created");
    }
    
    private Object[] createBlockItemRow(BaseItemCosmetic item) {
        Object itemRow[] = new Object[66];
        //상품상태
        itemRow[0] = "신상품";
        //카태고리 id
        itemRow[1] = item.getCategoryId();
        //상품명
        itemRow[2] = item.getItemFullnameWithPrefix();
        //판매가
        itemRow[3] = item.getPriceWonString();
        //재고수량
        itemRow[4] = 10;
        //A/S안내내용
        itemRow[5] = "구매대행 특성상 AS는 불가합니다";
        //A/S 전화번호
        itemRow[6] = "070-4001-8993";
        //대표 이미지 파일명
        itemRow[7] = item.getMainImageFileName();
        //추가 이미지 파일명
        itemRow[8] = "";
        
        //상품 상세정보
        itemRow[9] = GrobalDefined.TRANSLATE ? item.getItemFullDescriptionKOR() : item.getItemFullDescriptionDE();
        
        //판매자 상품코드
        itemRow[10] = "";
        //판매자 바코드
        itemRow[11] = "";
        //제조사
        itemRow[12] = "";
        //브랜드
        itemRow[13] = "";
        //제조일자
        //itemRow[14] = "";
        //유효일자
        //itemRow[15] = "";
        //부가세
        itemRow[16] = "과세상품";
        //미성년자
        itemRow[17] = "Y";
        //구매평 노출여부
        itemRow[18] = "Y";
        //원산지 코드
        itemRow[19] = "04";
        //수입사
        itemRow[20] = "";
        //복수원산지 여부
        itemRow[21] = "";
        //원산지 직접입력
        itemRow[22] = "독일구매대행";
        //배송방법
        itemRow[23] = "";
        //배송비 유형
        itemRow[24] = "유료";
        //기본배송비
        itemRow[25] = "8000";
        //배송비 결제방식
        itemRow[26] = "선결제";
        //조건부무료-상품판매가합계
        itemRow[27] = "";
        //수량별부과-수량
        //itemRow[28] = "";
        //반품배송비
        itemRow[29] = "80000";
        //교환배송비
        itemRow[30] = "160000";
        //지역별 차등배송비 정보
        itemRow[31] = "";
        //별도설치비
        itemRow[32] = "N";
        //판매자 특이사항
        itemRow[33] = "";
        //즉시할인 값
        //itemRow[34] = "";
        //즉시할인 단위
        //itemRow[35] = "원";
        //복수구매할인 조건 값
        itemRow[36] = "2";
        //복수구매할인 조건 단위
        itemRow[37] = "개";
        //복수구매할인 값
        itemRow[38] = "500";
        //복수구매할인 단위
        itemRow[39] = "원";
        //상품구매시 포인트 지급 값
        itemRow[40] = "100";
        //상품구매시 포인트 지급 단위
        itemRow[41] = "원";
        //텍스트리뷰 작성시 지급 포인트
        itemRow[42] = "100";
        //포토/동영상 리뷰 작성시 지급 포인트
        itemRow[43] = "500";
        //한달사용 텍스트리뷰 작성시 지급 포인트
        itemRow[44] = "100";
        //한달사용포토/동영상리뷰 작성시 지급 포인트
        itemRow[45] = "500";
        //톡톡친구/스토어찜고객
        itemRow[46] = "100";
        //무이자 할부 개월
        //itemRow[47] = "";
        //사은품
        itemRow[48] = "";
        //옵션형태
        itemRow[49] = "";
        //옵션명
        itemRow[50] = "";
        //옵션값
        itemRow[51] = "";
        //옵션가
        itemRow[52] = "";
        //옵션 재고수량
        itemRow[53] = "";
        //추가상품명
        itemRow[54] = "";
        //추가상품값
        itemRow[55] = "";
        //추가상품가
        itemRow[56] = "";
        //추가상품 재고수량
        itemRow[57] = "";
        //상품정보제공고시 품명
        itemRow[58] = 
        //상품정보제공고시 모델명
        itemRow[59] = "";
        //상품정보제공고시 인증허가사항
        itemRow[60] = "";
        //상품정보제공고시 제조자
        itemRow[61] = "";
        //스토어찜회원 전용여부
        itemRow[62] = "N";
        //문화비 소득공제
        itemRow[63] = "";
        //ISBN
        itemRow[64] = "";
        //독립출판
        itemRow[65] = "";
        return itemRow;
    }
    
    private Object[] createItemRowLando(BaseItem item) {
        Object itemRow[] = new Object[66];
        //상품상태
        itemRow[0] = "신상품";
        //카태고리 id
        itemRow[1] = this.categoryId;
        //상품명
        String itemFullName = GrobalDefined.TRANSLATE ? item.getItemFullnameEN() : item.getItemFullnameDE();
        itemRow[2] = "[" + itemTitlePrefix + "] " + itemFullName;
        //판매가
        itemRow[3] = item.getPriceWonString();
        //재고수량
        itemRow[4] = 10;
        //A/S안내내용
        itemRow[5] = "구매대행 특성상 AS는 불가합니다";
        //A/S 전화번호
        itemRow[6] = "070-4001-8993";
        //대표 이미지 파일명
        itemRow[7] = item.getMainImageFileName();
        //추가 이미지 파일명
        itemRow[8] = "";
        
        //상품 상세정보
        itemRow[9] = item.getDetailPageSmart();
        
        //판매자 상품코드
        itemRow[10] = "";
        //판매자 바코드
        itemRow[11] = "";
        //제조사
        itemRow[12] = "";
        //브랜드
        itemRow[13] = "";
        //제조일자
        //itemRow[14] = "";
        //유효일자
        //itemRow[15] = "";
        //부가세
        itemRow[16] = "과세상품";
        //미성년자
        itemRow[17] = "Y";
        //구매평 노출여부
        itemRow[18] = "Y";
        //원산지 코드
        itemRow[19] = "04";
        //수입사
        itemRow[20] = "";
        //복수원산지 여부
        itemRow[21] = "";
        //원산지 직접입력
        itemRow[22] = "독일구매대행";
        //배송방법
        itemRow[23] = "택배‚ 소포‚ 등기";
        //배송비 유형
        itemRow[24] = "유료";
        //기본배송비
        itemRow[25] = AgentZal.DELIVERY_FEE;
        //배송비 결제방식
        itemRow[26] = "선결제";
        //조건부무료-상품판매가합계
        itemRow[27] = "";
        //수량별부과-수량
        //itemRow[28] = "";
        //반품배송비
        itemRow[29] = "50000";
        //교환배송비
        itemRow[30] = "100000";
        //지역별 차등배송비 정보
        itemRow[31] = "";
        //별도설치비
        itemRow[32] = "N";
        //판매자 특이사항
        itemRow[33] = "";
        //즉시할인 값
        itemRow[34] = item.isItemSale() ? item.getPriceSubstractWonString() : "";
        //즉시할인 단위
        itemRow[35] = "원";
        //복수구매할인 조건 값
        itemRow[36] = "2";
        //복수구매할인 조건 단위
        itemRow[37] = "개";
        //복수구매할인 값
        itemRow[38] = "500";
        //복수구매할인 단위
        itemRow[39] = "원";
        //상품구매시 포인트 지급 값
        itemRow[40] = "100";
        //상품구매시 포인트 지급 단위
        itemRow[41] = "원";
        //텍스트리뷰 작성시 지급 포인트
        itemRow[42] = "100";
        //포토/동영상 리뷰 작성시 지급 포인트
        itemRow[43] = "500";
        //한달사용 텍스트리뷰 작성시 지급 포인트
        itemRow[44] = "100";
        //한달사용포토/동영상리뷰 작성시 지급 포인트
        itemRow[45] = "500";
        //톡톡친구/스토어찜고객
        itemRow[46] = "100";
        //무이자 할부 개월
        //itemRow[47] = "";
        //사은품
        itemRow[48] = "";
        //옵션형태
        itemRow[49] = "조합형";
        //옵션명
        itemRow[50] = "사이즈";
        //옵션값
        itemRow[51] = item.getSizeListString();
        //옵션가
        itemRow[52] = item.getSizeListPriceString();
        //옵션 재고수량
        itemRow[53] = item.getSizeListStockString();
        //추가상품명
        itemRow[54] = "";
        //추가상품값
        itemRow[55] = "";
        //추가상품가
        itemRow[56] = "";
        //추가상품 재고수량
        itemRow[57] = "";
        //상품정보제공고시 품명
        itemRow[58] = item.getMassItem().getBrandNameDE();
        //상품정보제공고시 모델명
        itemRow[59] = Formatter.abbreviateStringLeft(item.getMassItem().getItemTitleDE(), 50);
        //상품정보제공고시 인증허가사항
        itemRow[60] = "";
        //상품정보제공고시 제조자
        itemRow[61] = "";
        //스토어찜회원 전용여부
        itemRow[62] = "N";
        //문화비 소득공제
        itemRow[63] = "";
        //ISBN
        itemRow[64] = "";
        //독립출판
        itemRow[65] = "";
        return itemRow;
    }
    
    private Object[] createItemRow(BaseItemCosmetic item) {
        Object itemRow[] = new Object[66];
        //상품상태
        itemRow[0] = "신상품";
        //카태고리 id
        itemRow[1] = this.categoryId;
        //상품명
        itemRow[2] = item.getItemFullnameWithPrefix();
        //판매가
        itemRow[3] = item.getPriceWonString();
        //재고수량
        itemRow[4] = 10;
        //A/S안내내용
        itemRow[5] = "구매대행 특성상 AS는 불가합니다";
        //A/S 전화번호
        itemRow[6] = "010-6591-1193";
        //대표 이미지 파일명
        itemRow[7] = item.getMainImageFileName();
        //추가 이미지 파일명
        itemRow[8] = "";
        
        //상품 상세정보
        //itemRow[9] = item.getItemFullDescriptionDE();
        itemRow[9] = item.getItemFullDescriptionKOR();
        
        //판매자 상품코드
        itemRow[10] = "";
        //판매자 바코드
        itemRow[11] = "";
        //제조사
        itemRow[12] = "";
        //브랜드
        itemRow[13] = "";
        //제조일자
        //itemRow[14] = "";
        //유효일자
        //itemRow[15] = "";
        //부가세
        itemRow[16] = "과세상품";
        //미성년자
        itemRow[17] = "Y";
        //구매평 노출여부
        itemRow[18] = "Y";
        //원산지 코드
        itemRow[19] = "04";
        //수입사
        itemRow[20] = "";
        //복수원산지 여부
        itemRow[21] = "";
        //원산지 직접입력
        itemRow[22] = "독일구매대행";
        //배송방법
        itemRow[23] = "";
        //배송비 유형
        itemRow[24] = "유료";
        //기본배송비
        itemRow[25] = "8000";
        //배송비 결제방식
        itemRow[26] = "선결제";
        //조건부무료-상품판매가합계
        itemRow[27] = "";
        //수량별부과-수량
        //itemRow[28] = "";
        //반품배송비
        itemRow[29] = "80000";
        //교환배송비
        itemRow[30] = "160000";
        //지역별 차등배송비 정보
        itemRow[31] = "";
        //별도설치비
        itemRow[32] = "N";
        //판매자 특이사항
        itemRow[33] = "";
        //즉시할인 값
        itemRow[34] = "";
        //즉시할인 단위
        itemRow[35] = "원";
        //복수구매할인 조건 값
        itemRow[36] = "2";
        //복수구매할인 조건 단위
        itemRow[37] = "개";
        //복수구매할인 값
        itemRow[38] = "500";
        //복수구매할인 단위
        itemRow[39] = "원";
        //상품구매시 포인트 지급 값
        itemRow[40] = "100";
        //상품구매시 포인트 지급 단위
        itemRow[41] = "원";
        //텍스트리뷰 작성시 지급 포인트
        itemRow[42] = "100";
        //포토/동영상 리뷰 작성시 지급 포인트
        itemRow[43] = "500";
        //한달사용 텍스트리뷰 작성시 지급 포인트
        itemRow[44] = "100";
        //한달사용포토/동영상리뷰 작성시 지급 포인트
        itemRow[45] = "500";
        //톡톡친구/스토어찜고객
        itemRow[46] = "100";
        //무이자 할부 개월
        //itemRow[47] = "";
        //사은품
        itemRow[48] = "";
        //옵션형태
        itemRow[49] = "";
        //옵션명
        itemRow[50] = "";
        //옵션값
        itemRow[51] = "";
        //옵션가
        itemRow[52] = "";
        //옵션 재고수량
        itemRow[53] = "";
        //추가상품명
        itemRow[54] = "";
        //추가상품값
        itemRow[55] = "";
        //추가상품가
        itemRow[56] = "";
        //추가상품 재고수량
        itemRow[57] = "";
        //상품정보제공고시 품명
        itemRow[58] = "";
        //상품정보제공고시 모델명
        itemRow[59] = "";
        //상품정보제공고시 인증허가사항
        itemRow[60] = "";
        //상품정보제공고시 제조자
        itemRow[61] = "";
        //스토어찜회원 전용여부
        itemRow[62] = "N";
        //문화비 소득공제
        itemRow[63] = "";
        //ISBN
        itemRow[64] = "";
        //독립출판
        itemRow[65] = "";
        return itemRow;
    }
    
    private Object[] createItemRowMode(BaseItem item) {
        Object itemRow[] = new Object[66];
        //상품상태
        itemRow[0] = "신상품";
        //카태고리 id
        itemRow[1] = this.categoryId;
        //상품명
        itemRow[2] = "[" + itemTitlePrefix + "] " + item.getItemFullnameDE();
        //판매가
        itemRow[3] = item.getPriceWonString();
        //재고수량
        itemRow[4] = 10;
        //A/S안내내용
        itemRow[5] = "구매대행 특성상 AS는 불가합니다";
        //A/S 전화번호
        itemRow[6] = "070-4001-8993";
        //대표 이미지 파일명
        itemRow[7] = item.getMainImageFileName();
        //추가 이미지 파일명
        itemRow[8] = "";
        
        //상품 상세정보 exclude size checking button
        itemRow[9] = item.getDetailPageSmart();
        
        //판매자 상품코드
        itemRow[10] = "";
        //판매자 바코드
        itemRow[11] = "";
        //제조사
        itemRow[12] = "";
        //브랜드
        itemRow[13] = "";
        //제조일자
        //itemRow[14] = "";
        //유효일자
        //itemRow[15] = "";
        //부가세
        itemRow[16] = "과세상품";
        //미성년자
        itemRow[17] = "Y";
        //구매평 노출여부
        itemRow[18] = "Y";
        //원산지 코드
        itemRow[19] = "04";
        //수입사
        itemRow[20] = "";
        //복수원산지 여부
        itemRow[21] = "";
        //원산지 직접입력
        itemRow[22] = "독일구매대행";
        //배송방법
        itemRow[23] = "택배‚ 소포‚ 등기";
        //배송비 유형
        itemRow[24] = "무료";
        //기본배송비
        itemRow[25] = "";
        //배송비 결제방식
        itemRow[26] = "선결제";
        //조건부무료-상품판매가합계
        itemRow[27] = "";
        //수량별부과-수량
        //itemRow[28] = "";
        //반품배송비
        itemRow[29] = "50000";
        //교환배송비
        itemRow[30] = "100000";
        //지역별 차등배송비 정보
        itemRow[31] = "";
        //별도설치비
        itemRow[32] = "N";
        //판매자 특이사항
        itemRow[33] = "";
        //즉시할인 값
        itemRow[34] = item.isItemSale() ? item.getPriceSubstractWonString() : "";
        //즉시할인 단위
        itemRow[35] = "원";
        //복수구매할인 조건 값
        itemRow[36] = "2";
        //복수구매할인 조건 단위
        itemRow[37] = "개";
        //복수구매할인 값
        itemRow[38] = "500";
        //복수구매할인 단위
        itemRow[39] = "원";
        //상품구매시 포인트 지급 값
        itemRow[40] = "100";
        //상품구매시 포인트 지급 단위
        itemRow[41] = "원";
        //텍스트리뷰 작성시 지급 포인트
        itemRow[42] = "100";
        //포토/동영상 리뷰 작성시 지급 포인트
        itemRow[43] = "500";
        //한달사용 텍스트리뷰 작성시 지급 포인트
        itemRow[44] = "100";
        //한달사용포토/동영상리뷰 작성시 지급 포인트
        itemRow[45] = "500";
        //톡톡친구/스토어찜고객
        itemRow[46] = "100";
        //무이자 할부 개월
        //itemRow[47] = "";
        //사은품
        itemRow[48] = "";
        //옵션형태
        itemRow[49] = "조합형";
        //옵션명
        itemRow[50] = "사이즈";
        //itemRow[50] = "사이즈\n발 넓이"; //shoes
        
        //옵션값
        itemRow[51] = item.getSizeListString();//mode
        //itemRow[51] = item.getSizeListString() + "\n보통발, 좁은발";//shoes
        
        //옵션가
        itemRow[52] = item.getSizeListPriceString();
        //옵션 재고수량
        itemRow[53] = item.getSizeListStockString();
        //추가상품명
        itemRow[54] = "";
        //추가상품값
        itemRow[55] = "";
        //추가상품가
        itemRow[56] = "";
        //추가상품 재고수량
        itemRow[57] = "";
        //상품정보제공고시 품명
        itemRow[58] = item.getMassItem().getBrandNameDE();
        //상품정보제공고시 모델명
        itemRow[59] = Formatter.abbreviateStringLeft(item.getMassItem().getItemTitleDE(), 50);
        //상품정보제공고시 인증허가사항
        itemRow[60] = "";
        //상품정보제공고시 제조자
        itemRow[61] = "";
        //스토어찜회원 전용여부
        itemRow[62] = "N";
        //문화비 소득공제
        itemRow[63] = "";
        //ISBN
        itemRow[64] = "";
        //독립출판
        itemRow[65] = "";
        return itemRow;
    }
    
    private Object[] createItemCsvMode(ItemFeelway item) {
        Object itemRow[] = new Object[66];
        //상품상태
        itemRow[0] = "신상품";
        //카태고리 id
        itemRow[1] = item.getSmartStoreCategoryId();
        //상품명
        itemRow[2] = "[" + item.getItemBrandKor() + "] " + item.getItemTitleKor();
        //판매가
        itemRow[3] = String.valueOf(item.getItemPriceWon());
        //재고수량
        itemRow[4] = 10;
        //A/S안내내용
        itemRow[5] = "구매대행 특성상 AS는 불가합니다";
        //A/S 전화번호
        itemRow[6] = "070-4001-8993";
        //대표 이미지 파일명
        itemRow[7] = item.getItemMainImageName();
        //추가 이미지 파일명
        itemRow[8] = "";
        
        //상품 상세정보 exclude size checking button
        itemRow[9] = item.getIntroductionHtml();
        
        //판매자 상품코드
        itemRow[10] = "";
        //판매자 바코드
        itemRow[11] = "";
        //제조사
        itemRow[12] = "";
        //브랜드
        itemRow[13] = "";
        //제조일자
        //itemRow[14] = "";
        //유효일자
        //itemRow[15] = "";
        //부가세
        itemRow[16] = "과세상품";
        //미성년자
        itemRow[17] = "Y";
        //구매평 노출여부
        itemRow[18] = "Y";
        //원산지 코드
        itemRow[19] = "04";
        //수입사
        itemRow[20] = "";
        //복수원산지 여부
        itemRow[21] = "";
        //원산지 직접입력
        itemRow[22] = "독일구매대행";
        //배송방법
        itemRow[23] = "택배‚ 소포‚ 등기";
        //배송비 유형
        itemRow[24] = "무료";
        //기본배송비
        itemRow[25] = "";
        //배송비 결제방식
        itemRow[26] = "선결제";
        //조건부무료-상품판매가합계
        itemRow[27] = "";
        //수량별부과-수량
        //itemRow[28] = "";
        //반품배송비
        itemRow[29] = "50000";
        //교환배송비
        itemRow[30] = "100000";
        //지역별 차등배송비 정보
        itemRow[31] = "";
        //별도설치비
        itemRow[32] = "N";
        //판매자 특이사항
        itemRow[33] = "";
        //즉시할인 값
        itemRow[34] = "";
        //즉시할인 단위
        itemRow[35] = "원";
        //복수구매할인 조건 값
        itemRow[36] = "2";
        //복수구매할인 조건 단위
        itemRow[37] = "개";
        //복수구매할인 값
        itemRow[38] = "500";
        //복수구매할인 단위
        itemRow[39] = "원";
        //상품구매시 포인트 지급 값
        itemRow[40] = "100";
        //상품구매시 포인트 지급 단위
        itemRow[41] = "원";
        //텍스트리뷰 작성시 지급 포인트
        itemRow[42] = "100";
        //포토/동영상 리뷰 작성시 지급 포인트
        itemRow[43] = "500";
        //한달사용 텍스트리뷰 작성시 지급 포인트
        itemRow[44] = "100";
        //한달사용포토/동영상리뷰 작성시 지급 포인트
        itemRow[45] = "500";
        //톡톡친구/스토어찜고객
        itemRow[46] = "100";
        //무이자 할부 개월
        //itemRow[47] = "";
        //사은품
        itemRow[48] = "";
        //옵션형태
        itemRow[49] = "조합형";
        //옵션명
        itemRow[50] = "사이즈";
        //itemRow[50] = "사이즈\n발 넓이"; //shoes
        
        //옵션값
        itemRow[51] = item.getItemSizeList();//mode
        //itemRow[51] = item.getSizeListString() + "\n보통발, 좁은발";//shoes
        
        //옵션가
        itemRow[52] = "";
        //옵션 재고수량
        itemRow[53] = "5, 5, 5, 5, 5";
        //추가상품명
        itemRow[54] = "";
        //추가상품값
        itemRow[55] = "";
        //추가상품가
        itemRow[56] = "";
        //추가상품 재고수량
        itemRow[57] = "";
        //상품정보제공고시 품명
        itemRow[58] = item.getItemBrandEng();
        //상품정보제공고시 모델명
        itemRow[59] = Formatter.abbreviateStringLeft(item.getItemTitleEng(), 50);
        //상품정보제공고시 인증허가사항
        itemRow[60] = "";
        //상품정보제공고시 제조자
        itemRow[61] = "";
        //스토어찜회원 전용여부
        itemRow[62] = "N";
        //문화비 소득공제
        itemRow[63] = "";
        //ISBN
        itemRow[64] = "";
        //독립출판
        itemRow[65] = "";
        return itemRow;
    }
    
    private Object[] createItemRowShoes(BaseItem item) {
        Object itemRow[] = new Object[66];
        //상품상태
        itemRow[0] = "신상품";
        //카태고리 id
        itemRow[1] = this.categoryId;
        //상품명
        itemRow[2] = "[" + itemTitlePrefix + "] " + item.getItemFullnameDE();
        //판매가
        itemRow[3] = item.getPriceWonString();
        //재고수량
        itemRow[4] = 10;
        //A/S안내내용
        itemRow[5] = "구매대행 특성상 AS는 불가합니다";
        //A/S 전화번호
        itemRow[6] = "070-4001-8993";
        //대표 이미지 파일명
        itemRow[7] = item.getMainImageFileName();
        //추가 이미지 파일명
        itemRow[8] = "";
        
        //상품 상세정보 exclude size checking button
        itemRow[9] = item.getDetailPageSmart();
        
        //판매자 상품코드
        itemRow[10] = "";
        //판매자 바코드
        itemRow[11] = "";
        //제조사
        itemRow[12] = "";
        //브랜드
        itemRow[13] = "";
        //제조일자
        //itemRow[14] = "";
        //유효일자
        //itemRow[15] = "";
        //부가세
        itemRow[16] = "과세상품";
        //미성년자
        itemRow[17] = "Y";
        //구매평 노출여부
        itemRow[18] = "Y";
        //원산지 코드
        itemRow[19] = "04";
        //수입사
        itemRow[20] = "";
        //복수원산지 여부
        itemRow[21] = "";
        //원산지 직접입력
        itemRow[22] = "독일구매대행";
        //배송방법
        itemRow[23] = "택배‚ 소포‚ 등기";
        //배송비 유형
        itemRow[24] = "무료";
        //기본배송비
        itemRow[25] = "";
        //배송비 결제방식
        itemRow[26] = "선결제";
        //조건부무료-상품판매가합계
        itemRow[27] = "";
        //수량별부과-수량
        //itemRow[28] = "";
        //반품배송비
        itemRow[29] = "50000";
        //교환배송비
        itemRow[30] = "100000";
        //지역별 차등배송비 정보
        itemRow[31] = "";
        //별도설치비
        itemRow[32] = "N";
        //판매자 특이사항
        itemRow[33] = "";
        //즉시할인 값
        itemRow[34] = item.isItemSale() ? item.getPriceSubstractWonString() : "";
        //즉시할인 단위
        itemRow[35] = "원";
        //복수구매할인 조건 값
        itemRow[36] = "2";
        //복수구매할인 조건 단위
        itemRow[37] = "개";
        //복수구매할인 값
        itemRow[38] = "500";
        //복수구매할인 단위
        itemRow[39] = "원";
        //상품구매시 포인트 지급 값
        itemRow[40] = "100";
        //상품구매시 포인트 지급 단위
        itemRow[41] = "원";
        //텍스트리뷰 작성시 지급 포인트
        itemRow[42] = "100";
        //포토/동영상 리뷰 작성시 지급 포인트
        itemRow[43] = "500";
        //한달사용 텍스트리뷰 작성시 지급 포인트
        itemRow[44] = "100";
        //한달사용포토/동영상리뷰 작성시 지급 포인트
        itemRow[45] = "500";
        //톡톡친구/스토어찜고객
        itemRow[46] = "100";
        //무이자 할부 개월
        //itemRow[47] = "";
        //사은품
        itemRow[48] = "";
        //옵션형태
        itemRow[49] = "조합형";
        //옵션명
        itemRow[50] = "사이즈\n발 넓이";
        //옵션값
        itemRow[51] = item.getSizeListString() + "\n보통발, 좁은발";
        //옵션가
        itemRow[52] = item.getSizeListPriceString();
        //옵션 재고수량
        itemRow[53] = item.getSizeListStockString();
        //추가상품명
        itemRow[54] = "";
        //추가상품값
        itemRow[55] = "";
        //추가상품가
        itemRow[56] = "";
        //추가상품 재고수량
        itemRow[57] = "";
        //상품정보제공고시 품명
        itemRow[58] = item.getMassItem().getBrandNameDE();
        //상품정보제공고시 모델명
        itemRow[59] = Formatter.abbreviateStringLeft(item.getMassItem().getItemTitleDE(), 50);
        //상품정보제공고시 인증허가사항
        itemRow[60] = "";
        //상품정보제공고시 제조자
        itemRow[61] = "";
        //스토어찜회원 전용여부
        itemRow[62] = "N";
        //문화비 소득공제
        itemRow[63] = "";
        //ISBN
        itemRow[64] = "";
        //독립출판
        itemRow[65] = "";
        return itemRow;
    }
}
