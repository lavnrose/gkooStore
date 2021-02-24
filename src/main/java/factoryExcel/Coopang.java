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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import agencyEntities.BaseItemCosmetic;
import gkooAgency.AgentEco;

//deprecated use api
public class Coopang {
    private static final Logger LOGGER = LogManager.getLogger();

    private String categoryId;
    private String itemTitlePrefix;
    private String brandName;
    private List<BaseItemCosmetic> massItemCosmeticList;
    
    /**
     * 
     * Cosmetics
     * 
     * @param categoryId
     * @param itemTitlePrefix
     * @param massItemCosmeticList
     */
    public Coopang(String categoryId, String itemTitlePrefix, List<BaseItemCosmetic> massItemCosmeticList) {
        this.categoryId = categoryId;
        this.itemTitlePrefix = itemTitlePrefix;
        this.massItemCosmeticList = massItemCosmeticList;
    }
    
    public Coopang(String brandName, List<BaseItemCosmetic> massItemCosmeticList) {
        this.setBrandName(brandName);
        this.massItemCosmeticList = massItemCosmeticList;
    }
    
    //cosmetic
    public void createExcelEcoverde() {
        LOGGER.info("Creating the excel for coopang starts...");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(itemTitlePrefix);
        
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        
        for (int i=0;i<massItemCosmeticList.size();i++) {
            Object createdItemRow[] = createItemRow(massItemCosmeticList.get(i));
            data.put(String.valueOf(i+1), createdItemRow);
        }
        
        Set<String> keyset = data.keySet();
        int rownum = 0;
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
            FileOutputStream outputStream = new FileOutputStream(AgentEco.DIR_EXCEL_FILE + itemTitlePrefix + "_coopang_ready.xlsx");
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("the excel for coopang is created");
    }
    
    public void createExcelBlockEcoverde() {
        LOGGER.info("Creating the excel for coopang starts...");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(brandName);
        
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        
        for (int i=0;i<massItemCosmeticList.size();i++) {
            Object createdItemRow[] = createBlockItemRow(massItemCosmeticList.get(i));
            data.put(String.valueOf(i+1), createdItemRow);
        }
        
        Set<String> keyset = data.keySet();
        int rownum = 0;
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
            FileOutputStream outputStream = new FileOutputStream(AgentEco.DIR_EXCEL_FILE + brandName + "_coopang_ready.xlsx");
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("the excel for coopang is created");
    }
    
    public void createExcelBlock(String dirExcelFile) {
        LOGGER.info("Creating the csv for cafe24 starts...");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(brandName);
        
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        
        for (int i=0;i<massItemCosmeticList.size();i++) {
            Object createdItemRow[] = createBlockItemRow(massItemCosmeticList.get(i));
            data.put(String.valueOf(i+1), createdItemRow);
        }
        
        Set<String> keyset = data.keySet();
        int rownum = 0;
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
            FileOutputStream outputStream = new FileOutputStream(dirExcelFile + brandName + "_coopang_ready.xlsx");
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("the csv for cafe24 is created");
    }
    
    private Object[] createBlockItemRow(BaseItemCosmetic item) {
        Object itemRow[] = new Object[66];
        //카테고리
        itemRow[0] = "";
        //등록상품명 -- 상품노출명은 브랜드 + 아이템이름
        //itemRow[1] = item.getMassItem().getItemFullname();
        
        //판매시작일
        itemRow[2] = "";
        //판매종료일
        itemRow[3] = "";
        //상품상태
        itemRow[4] = "새상품";
        //상태설명
        itemRow[5] = "";
        //브랜드
        itemRow[6] = "[" + item.getMassItem().getBrandNameDE() + "]";
        //제조사
        itemRow[7] = item.getMassItem().getBrandNameDE();
        //검색어
        itemRow[8] = "";
        
        //옵션유형1
        itemRow[9] = "수량";
        //옵션값1 - 구매옵션
        itemRow[10] = "1개";
        //옵션유형2 - 구매옵션
        itemRow[11] = "";
        //옵션값2 - 구매옵션
        itemRow[12] = "";
        //옵션유형3 - 구매옵션
        itemRow[13] = "";
        //옵션값3 - 구매옵션
        itemRow[14] = "";
        //옵션유형4 - 구매옵션
        itemRow[15] = "";
        //옵션값4 - 구매옵션
        itemRow[16] = "";
        
        //옵션유형1 - 검색옵션
        itemRow[17] = "";
        //옵션값1 - 검색옵션
        itemRow[18] = "";
        //옵션유형2 - 검색옵션
        itemRow[19] = "";
        //옵션값2 - 검색옵션
        itemRow[20] = "";
        //옵션유형3 - 검색옵션
        itemRow[21] = "";
        //옵션값3 - 검색옵션
        itemRow[22] = "";
        //옵션유형4 - 검색옵션
        itemRow[23] = "";
        //옵션값4 - 검색옵션
        itemRow[24] = "";
        
        //판매가격
        itemRow[25] = Integer.valueOf(item.getPriceWonString());
        //할인율기준가
        itemRow[26] = 0;
        //재고수량
        itemRow[27] = 10;
        //출고리드타임
        itemRow[28] = 10;
        //인당최대구매수량
        itemRow[29] = "";
        //최대구매수량기간(일)
        itemRow[30] = "";
        //성인상품(19)
        itemRow[31] = "";
        //과세여부
        itemRow[32] = "";
        //병행수입여부
        itemRow[33] = "";
        //해외구매대행
        itemRow[34] = "Y";
        //업체상품코드
        itemRow[35] = "";
        //모델번호
        itemRow[36] = "";
        //바코드
        itemRow[37] = "[바코드없음]국내외 표준 바코드가 아닌 상품임";
        //인증∙신고 등 정보유형
        itemRow[38] = "인증∙신고 등 대상아님";
        //인증∙신고 등 정보값
        itemRow[39] = "";
        //인증∙신고 등 정보유형
        itemRow[40] = "인증∙신고 등 대상아님";
        //인증∙신고 등 정보값
        itemRow[41] = "";
        //인증∙신고 등 정보유형
        itemRow[42] = "인증∙신고 등 대상아님";
        //인증∙신고 등 정보값
        itemRow[43] = "";
        //주문 추가메시지
        itemRow[44] = "";
        
        //상품고시정보 카테고리
        itemRow[45] = "화장품";
        //상품고시정보값1
        itemRow[46] = item.getMassItem().getItemVolume();
        //상품고시정보값2
        itemRow[47] = "상품상세페이지참조";
        //상품고시정보값3
        itemRow[48] = "제품 또는 제품포장 겉면의 사용기한참조, 개별 사용기한 확인방법 구매전 문의요망";
        //상품고시정보값4
        itemRow[49] = "상품상세페이지참조";
        //상품고시정보값5
        itemRow[50] = item.getMassItem().getBrandNameDE();
        //상품고시정보값6
        itemRow[51] = "독일, 기타유럽국가";
        //상품고시정보값7
        itemRow[52] = "상품상세페이지참조";
        //상품고시정보값8
        itemRow[53] = "식품의약품안전처 심사 필 무";
        //상품고시정보값9
        itemRow[54] = "상품상세페이지참조";
        //상품고시정보값10
        itemRow[55] = "상품상세페이지참조";
        //상품고시정보값11
        itemRow[56] = "070-4001-8993";
        //상품고시정보값12
        itemRow[57] = "";
        //상품고시정보값13
        itemRow[58] = "";
        //상품고시정보값14
        itemRow[59] = "";
        
        //대표이미지(정사각형)
        itemRow[60] = "";
        //대표이미지(직사각형)
        itemRow[61] = "";
        //기타이미지
        itemRow[62] = "";
        //상태이미지
        itemRow[63] = "";
        //상세 설명
        itemRow[64] = "";
        
        //구비서류값1
        itemRow[65] = "";
        //구비서류값2
        itemRow[65] = "";
        //구비서류값3
        itemRow[65] = "";
        //구비서류값4
        itemRow[65] = "";
        //구비서류값5
        itemRow[65] = "";
        //구비서류값6
        itemRow[65] = "";
        //구비서류값7
        itemRow[65] = "";
        
        return itemRow;
    }

    private Object[] createItemRow(BaseItemCosmetic item) {
        Object itemRow[] = new Object[66];
        //카테고리
        itemRow[0] = this.categoryId;
        //등록상품명
        itemRow[1] = item.getItemFullnameWithPrefix();
        
        //판매시작일
        itemRow[2] = "";
        //판매종료일
        itemRow[3] = "";
        //상품상태
        itemRow[4] = "새상품";
        //상태설명
        itemRow[5] = "";
        //브랜드
        itemRow[6] = item.getMassItem().getBrandNameDE();
        //제조사
        itemRow[7] = item.getMassItem().getBrandNameDE();
        //검색어
        itemRow[8] = "";
        
        //옵션유형1
        itemRow[9] = "수량";
        //옵션값1 - 구매옵션
        itemRow[10] = "1개";
        //옵션유형2 - 구매옵션
        itemRow[11] = "";
        //옵션값2 - 구매옵션
        itemRow[12] = "";
        //옵션유형3 - 구매옵션
        itemRow[13] = "";
        //옵션값3 - 구매옵션
        itemRow[14] = "";
        //옵션유형4 - 구매옵션
        itemRow[15] = "";
        //옵션값4 - 구매옵션
        itemRow[16] = "";
        
        //옵션유형1 - 검색옵션
        itemRow[17] = "";
        //옵션값1 - 검색옵션
        itemRow[18] = "";
        //옵션유형2 - 검색옵션
        itemRow[19] = "";
        //옵션값2 - 검색옵션
        itemRow[20] = "";
        //옵션유형3 - 검색옵션
        itemRow[21] = "";
        //옵션값3 - 검색옵션
        itemRow[22] = "";
        //옵션유형4 - 검색옵션
        itemRow[23] = "";
        //옵션값4 - 검색옵션
        itemRow[24] = "";
        
        //판매가격
        itemRow[25] = Integer.valueOf(item.getPriceWonString());
        //할인율기준가
        itemRow[26] = 0;
        //재고수량
        itemRow[27] = 10;
        //출고리드타임
        itemRow[28] = 10;
        //인당최대구매수량
        itemRow[29] = "";
        //최대구매수량기간(일)
        itemRow[30] = "";
        //성인상품(19)
        itemRow[31] = "";
        //과세여부
        itemRow[32] = "";
        //병행수입여부
        itemRow[33] = "";
        //해외구매대행
        itemRow[34] = "Y";
        //업체상품코드
        itemRow[35] = "";
        //모델번호
        itemRow[36] = "";
        //바코드
        itemRow[37] = "[바코드없음]국내외 표준 바코드가 아닌 상품임";
        //인증∙신고 등 정보유형
        itemRow[38] = "인증∙신고 등 대상아님";
        //인증∙신고 등 정보값
        itemRow[39] = "";
        //인증∙신고 등 정보유형
        itemRow[40] = "인증∙신고 등 대상아님";
        //인증∙신고 등 정보값
        itemRow[41] = "";
        //인증∙신고 등 정보유형
        itemRow[42] = "인증∙신고 등 대상아님";
        //인증∙신고 등 정보값
        itemRow[43] = "";
        //주문 추가메시지
        itemRow[44] = "";
        
        //상품고시정보 카테고리
        itemRow[45] = "화장품";
        //상품고시정보값1
        itemRow[46] = "상품상세페이지참조";
        //상품고시정보값2
        itemRow[47] = "상품상세페이지참조";
        //상품고시정보값3
        itemRow[48] = "상품상세페이지참조";
        //상품고시정보값4
        itemRow[49] = "상품상세페이지참조";
        //상품고시정보값5
        itemRow[50] = "상품상세페이지참조";
        //상품고시정보값6
        itemRow[51] = "상품상세페이지참조";
        //상품고시정보값7
        itemRow[52] = "상품상세페이지참조";
        //상품고시정보값8
        itemRow[53] = "식품의약품안전처 심사 필 무";
        //상품고시정보값9
        itemRow[54] = "상품상세페이지참조";
        //상품고시정보값10
        itemRow[55] = "상품상세페이지참조";
        //상품고시정보값11
        itemRow[56] = "상품상세페이지참조";
        //상품고시정보값12
        itemRow[57] = "";
        //상품고시정보값13
        itemRow[58] = "";
        //상품고시정보값14
        itemRow[59] = "";
        
        //대표이미지(정사각형)
        itemRow[60] = "";
        //대표이미지(직사각형)
        itemRow[61] = "";
        //기타이미지
        itemRow[62] = "";
        //상태이미지
        itemRow[63] = "";
        //상세 설명
        itemRow[64] = "";
        
        //구비서류값1
        itemRow[65] = "";
        //구비서류값2
        itemRow[65] = "";
        //구비서류값3
        itemRow[65] = "";
        //구비서류값4
        itemRow[65] = "";
        //구비서류값5
        itemRow[65] = "";
        //구비서류값6
        itemRow[65] = "";
        //구비서류값7
        itemRow[65] = "";
        
        return itemRow;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
