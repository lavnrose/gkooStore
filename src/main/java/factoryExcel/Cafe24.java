package factoryExcel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import agencyEntities.BaseItem;
import agencyEntities.BaseItemCosmetic;
import agencyEntities.BaseItemSimpleCosmetic;
import util.Formatter;
import util.GrobalDefined;

public class Cafe24 {
    private static final Logger LOGGER = LogManager.getLogger();

    private String brandNameKor;
    private String categoryNumber;
    private String dirFileUploader;
    private List<BaseItemCosmetic> massItemCosmeticList;
    private List<BaseItemSimpleCosmetic> baseItemSimpleCosmetic;
    private List<BaseItem> massItemList;
    
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    
    public Cafe24(String brandNameKor, String categoryNumber, List<BaseItemCosmetic> itemList) {
        this.brandNameKor = brandNameKor;
        this.categoryNumber = categoryNumber;
        this.massItemCosmeticList = itemList;
    }
    
    public Cafe24(List<BaseItem> itemList, String brandNameKor, String categoryNumber, String dirFileUploader) {
        this.brandNameKor = brandNameKor;
        this.categoryNumber = categoryNumber;
        this.dirFileUploader = dirFileUploader;
        this.massItemList = itemList;
    }
    
    public Cafe24(String brandNameKor, String categoryNumber, String dirFileUploader, List<BaseItemSimpleCosmetic> baseItemSimpleCosmetic) {
        this.brandNameKor = brandNameKor;
        this.categoryNumber = categoryNumber;
        this.dirFileUploader = dirFileUploader;
        this.baseItemSimpleCosmetic = baseItemSimpleCosmetic;
    }

    public void createCsvFileManual(String dirCsvFile, String productName) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(dirCsvFile + productName + "_cafe24.csv");
            fileWriter.append("상품코드,자체 상품코드,진열상태,판매상태,상품분류 번호,상품분류 신상품영역,상품분류 추천상품영역,상품명,영문 상품명,상품명(관리용),공급사 상품명,모델명,상품 요약설명,상품 간략설명,상품 상세설명,모바일 상품 상세설명 설정,모바일 상품 상세설명,검색어설정,과세구분,소비자가,공급가,상품가,판매가,판매가 대체문구 사용,판매가 대체문구,주문수량 제한 기준,최소 주문수량(이상),최대 주문수량(이하),적립금,적립금 구분,공통이벤트 정보,성인인증,옵션사용,품목 구성방식,옵션 표시방식,옵션세트명,옵션입력,옵션 스타일,버튼이미지 설정,색상 설정,필수여부,품절표시 문구,추가입력옵션,추가입력옵션 명칭,추가입력옵션 선택/필수여부,입력글자수(자),이미지등록(상세),이미지등록(목록),이미지등록(작은목록),이미지등록(축소),이미지등록(추가),제조사,공급사,브랜드,트렌드,자체분류 코드,제조일자,출시일자,유효기간 사용여부,유효기간,원산지,상품부피(cm),상품결제안내,상품배송안내,교환/반품안내,서비스문의/안내,배송정보,배송방법,국내/해외배송,배송지역,배송비 선결제 설정,배송기간,배송비 구분,배송비입력,스토어픽업 설정,상품 전체중량(kg),HS코드,상품 구분(해외통관),상품소재,영문 상품소재(해외통관),옷감(해외통관),검색엔진최적화(SEO) 검색엔진 노출 설정,검색엔진최적화(SEO) Title,검색엔진최적화(SEO) Author,검색엔진최적화(SEO) Description,검색엔진최적화(SEO) Keywords,검색엔진최적화(SEO) 상품 이미지 Alt 텍스트,개별결제수단설정,상품배송유형 코드,메모");
            fileWriter.append(NEW_LINE_SEPARATOR);
            for (int i=0;i<massItemCosmeticList.size();i++) {
                writerCsvManual(fileWriter, massItemCosmeticList.get(i));
            }
        } catch (IOException e) {
        } finally {
            
           try {
               fileWriter.flush();
               fileWriter.close();
           } catch (IOException e) {
               System.out.println("Error while flushing/closing fileWriter !!!");
               e.printStackTrace();
           }
       }
    }
    
    public void createCsvFileMode(String dirCsvFile) {
        
        //for test
        createCsvFileModeTest(dirCsvFile);
        
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(dirCsvFile + brandNameKor + "_cafe24.csv");
            fileWriter.append("상품코드,자체 상품코드,진열상태,판매상태,상품분류 번호,상품분류 신상품영역,상품분류 추천상품영역,상품명,영문 상품명,상품명(관리용),공급사 상품명,모델명,상품 요약설명,상품 간략설명,상품 상세설명,모바일 상품 상세설명 설정,모바일 상품 상세설명,검색어설정,과세구분,소비자가,공급가,상품가,판매가,판매가 대체문구 사용,판매가 대체문구,주문수량 제한 기준,최소 주문수량(이상),최대 주문수량(이하),적립금,적립금 구분,공통이벤트 정보,성인인증,옵션사용,품목 구성방식,옵션 표시방식,옵션세트명,옵션입력,옵션 스타일,버튼이미지 설정,색상 설정,필수여부,품절표시 문구,추가입력옵션,추가입력옵션 명칭,추가입력옵션 선택/필수여부,입력글자수(자),이미지등록(상세),이미지등록(목록),이미지등록(작은목록),이미지등록(축소),이미지등록(추가),제조사,공급사,브랜드,트렌드,자체분류 코드,제조일자,출시일자,유효기간 사용여부,유효기간,원산지,상품부피(cm),상품결제안내,상품배송안내,교환/반품안내,서비스문의/안내,배송정보,배송방법,국내/해외배송,배송지역,배송비 선결제 설정,배송기간,배송비 구분,배송비입력,스토어픽업 설정,상품 전체중량(kg),HS코드,상품 구분(해외통관),상품소재,영문 상품소재(해외통관),옷감(해외통관),검색엔진최적화(SEO) 검색엔진 노출 설정,검색엔진최적화(SEO) Title,검색엔진최적화(SEO) Author,검색엔진최적화(SEO) Description,검색엔진최적화(SEO) Keywords,검색엔진최적화(SEO) 상품 이미지 Alt 텍스트,개별결제수단설정,상품배송유형 코드,메모");
            fileWriter.append(NEW_LINE_SEPARATOR);
            for (int i=0;i<massItemList.size();i++) {
                writerCsvMode(fileWriter, massItemList.get(i));
            }
        } catch (IOException e) {
        } finally {
            
           try {
               fileWriter.flush();
               fileWriter.close();
           } catch (IOException e) {
               System.out.println("Error while flushing/closing fileWriter !!!");
               e.printStackTrace();
           }
       }
    }
    
    public void createCsvFileCosmetic(String dirCsvFile) {
        
        //for test
        //createCsvFileCosmeticTest(dirCsvFile);
        
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(dirCsvFile + brandNameKor + "_cafe24.csv");
            fileWriter.append("상품코드,자체 상품코드,진열상태,판매상태,상품분류 번호,상품분류 신상품영역,상품분류 추천상품영역,상품명,영문 상품명,상품명(관리용),공급사 상품명,모델명,상품 요약설명,상품 간략설명,상품 상세설명,모바일 상품 상세설명 설정,모바일 상품 상세설명,검색어설정,과세구분,소비자가,공급가,상품가,판매가,판매가 대체문구 사용,판매가 대체문구,주문수량 제한 기준,최소 주문수량(이상),최대 주문수량(이하),적립금,적립금 구분,공통이벤트 정보,성인인증,옵션사용,품목 구성방식,옵션 표시방식,옵션세트명,옵션입력,옵션 스타일,버튼이미지 설정,색상 설정,필수여부,품절표시 문구,추가입력옵션,추가입력옵션 명칭,추가입력옵션 선택/필수여부,입력글자수(자),이미지등록(상세),이미지등록(목록),이미지등록(작은목록),이미지등록(축소),이미지등록(추가),제조사,공급사,브랜드,트렌드,자체분류 코드,제조일자,출시일자,유효기간 사용여부,유효기간,원산지,상품부피(cm),상품결제안내,상품배송안내,교환/반품안내,서비스문의/안내,배송정보,배송방법,국내/해외배송,배송지역,배송비 선결제 설정,배송기간,배송비 구분,배송비입력,스토어픽업 설정,상품 전체중량(kg),HS코드,상품 구분(해외통관),상품소재,영문 상품소재(해외통관),옷감(해외통관),검색엔진최적화(SEO) 검색엔진 노출 설정,검색엔진최적화(SEO) Title,검색엔진최적화(SEO) Author,검색엔진최적화(SEO) Description,검색엔진최적화(SEO) Keywords,검색엔진최적화(SEO) 상품 이미지 Alt 텍스트,개별결제수단설정,상품배송유형 코드,메모");
            fileWriter.append(NEW_LINE_SEPARATOR);
            for (int i=0;i<massItemCosmeticList.size();i++) {
                writerCsvCosmetic(fileWriter, massItemCosmeticList.get(i));
            }
        } catch (IOException e) {
        } finally {
            
           try {
               fileWriter.flush();
               fileWriter.close();
           } catch (IOException e) {
               System.out.println("Error while flushing/closing fileWriter !!!");
               e.printStackTrace();
           }
       }
    }
    
    public void createCsvFileSimpleCosmetic(String dirCsvFile) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(dirCsvFile + brandNameKor + "_cafe24.csv");
            fileWriter.append("상품코드,자체 상품코드,진열상태,판매상태,상품분류 번호,상품분류 신상품영역,상품분류 추천상품영역,상품명,영문 상품명,상품명(관리용),공급사 상품명,모델명,상품 요약설명,상품 간략설명,상품 상세설명,모바일 상품 상세설명 설정,모바일 상품 상세설명,검색어설정,과세구분,소비자가,공급가,상품가,판매가,판매가 대체문구 사용,판매가 대체문구,주문수량 제한 기준,최소 주문수량(이상),최대 주문수량(이하),적립금,적립금 구분,공통이벤트 정보,성인인증,옵션사용,품목 구성방식,옵션 표시방식,옵션세트명,옵션입력,옵션 스타일,버튼이미지 설정,색상 설정,필수여부,품절표시 문구,추가입력옵션,추가입력옵션 명칭,추가입력옵션 선택/필수여부,입력글자수(자),이미지등록(상세),이미지등록(목록),이미지등록(작은목록),이미지등록(축소),이미지등록(추가),제조사,공급사,브랜드,트렌드,자체분류 코드,제조일자,출시일자,유효기간 사용여부,유효기간,원산지,상품부피(cm),상품결제안내,상품배송안내,교환/반품안내,서비스문의/안내,배송정보,배송방법,국내/해외배송,배송지역,배송비 선결제 설정,배송기간,배송비 구분,배송비입력,스토어픽업 설정,상품 전체중량(kg),HS코드,상품 구분(해외통관),상품소재,영문 상품소재(해외통관),옷감(해외통관),검색엔진최적화(SEO) 검색엔진 노출 설정,검색엔진최적화(SEO) Title,검색엔진최적화(SEO) Author,검색엔진최적화(SEO) Description,검색엔진최적화(SEO) Keywords,검색엔진최적화(SEO) 상품 이미지 Alt 텍스트,개별결제수단설정,상품배송유형 코드,메모");
            fileWriter.append(NEW_LINE_SEPARATOR);
            for (int i=0;i<baseItemSimpleCosmetic.size();i++) {
                writerCsvSimpleCosmetic(fileWriter, baseItemSimpleCosmetic.get(i));
            }
        } catch (IOException e) {
        } finally {
            
           try {
               fileWriter.flush();
               fileWriter.close();
           } catch (IOException e) {
               System.out.println("Error while flushing/closing fileWriter !!!");
               e.printStackTrace();
           }
       }
    }
    
    public void createExcelFileCosmetic(String dirExcelFile) {
        LOGGER.info("Creating the excel for cosmetic starts...");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("cosmetic");
        
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put(String.valueOf(0), createItemHeader());
        for (int i=0;i< massItemCosmeticList.size();i++) {
            Object createdItemRow[] = createItemRowCosmetic(massItemCosmeticList.get(i));
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
            FileOutputStream outputStream = new FileOutputStream(dirExcelFile + brandNameKor + "_cosmetic_cafe24.xlsx");
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("the excel for smartstore is created");
    }
    
    private Object[] createItemHeader() {
        String header = "상품코드,자체 상품코드,진열상태,판매상태,상품분류 번호,상품분류 신상품영역,상품분류 추천상품영역,상품명,영문 상품명,상품명(관리용),공급사 상품명,모델명,상품 요약설명,상품 간략설명,상품 상세설명,모바일 상품 상세설명 설정,모바일 상품 상세설명,검색어설정,과세구분,소비자가,공급가,상품가,판매가,판매가 대체문구 사용,판매가 대체문구,주문수량 제한 기준,최소 주문수량(이상),최대 주문수량(이하),적립금,적립금 구분,공통이벤트 정보,성인인증,옵션사용,품목 구성방식,옵션 표시방식,옵션세트명,옵션입력,옵션 스타일,버튼이미지 설정,색상 설정,필수여부,품절표시 문구,추가입력옵션,추가입력옵션 명칭,추가입력옵션 선택/필수여부,입력글자수(자),이미지등록(상세),이미지등록(목록),이미지등록(작은목록),이미지등록(축소),이미지등록(추가),제조사,공급사,브랜드,트렌드,자체분류 코드,제조일자,출시일자,유효기간 사용여부,유효기간,원산지,상품부피(cm),상품결제안내,상품배송안내,교환/반품안내,서비스문의/안내,배송정보,배송방법,국내/해외배송,배송지역,배송비 선결제 설정,배송기간,배송비 구분,배송비입력,스토어픽업 설정,상품 전체중량(kg),HS코드,상품 구분(해외통관),상품소재,영문 상품소재(해외통관),옷감(해외통관),검색엔진최적화(SEO) 검색엔진 노출 설정,검색엔진최적화(SEO) Title,검색엔진최적화(SEO) Author,검색엔진최적화(SEO) Description,검색엔진최적화(SEO) Keywords,검색엔진최적화(SEO) 상품 이미지 Alt 텍스트,개별결제수단설정,상품배송유형 코드,메모";
        List<String> headerList= Stream.of(header.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
        
        String[] stockArr = new String[headerList.size()];
        stockArr = headerList.toArray(stockArr);
        
//        Object itemRow[] = new Object[90];
        Object itemRow[] = stockArr;
        
        return itemRow;
    }
    
    private Object[] createItemRowCosmetic(BaseItemCosmetic item) {
        Object itemRow[] = new Object[90];
        
        //상품코드
        itemRow[0] = " ";
        
        //자체 상품코드
        itemRow[1] = " ";
        
        //진열상태
        itemRow[2] = "Y";
        
        //판매상태
        itemRow[3] = "Y";
        
        //상품분류 번호
        itemRow[4] = categoryNumber;
        
        //상품분류 신상품영역
        itemRow[5] = "Y";
        
        //상품분류 추천상품영역
        itemRow[6] = " ";
        
        //상품명
        itemRow[7] = item.getItemFullnameWithPrefix();
        
        //영문 상품명
        //itemRow[0] = "";
        itemRow[8] = Formatter.abbreviateStringLeft(item.getMassItem().getItemUrl(), 250);
        
        //상품명(관리용)
        itemRow[9] = "";
        
        //공급사 상품명
        itemRow[10] = item.getMassItem().getItemTitleDE();
        
        //모델명
        itemRow[11] = " ";
        
        //상품 요약설명
        itemRow[12] = " ";
        
        //상품 간략설명
        itemRow[13] = " ";
        
        // 상품 상세설명
        // comma in description bring error 
        //String description = GrobalDefined.TRANSLATE ? item.getItemFullDescriptionKOR() : item.getItemFullDescriptionDE(;
        String description = item.getItemFullDescriptionKOR();
        itemRow[14] = description != "" ? Formatter.formatWithoutComma(description) : "";
        //itemRow[0] =Formatter.formatWithoutComma(item.getMassItem().getItemDescription());

        //모바일 상품 상세설명 설정
        itemRow[15] = "A";
        
        //모바일 상품 상세설명
        itemRow[16] = " ";
        
        //검색어설정
        itemRow[17] = " ";
        
        //과세구분
        itemRow[18] = "A|10";
        
        //소비자가
        itemRow[19] =item.getPriceWonString();
        
        //공급가
        itemRow[20] =Double.toString(item.getMassItem().getItemPriceEuro());
        
        //상품가
        itemRow[21] = " ";
        
        //판매가
        itemRow[22] =item.getMassItem().isItemSale() ? item.getPriceSaleWonString() : item.getPriceWonString();
        
        //판매가 대체문구 사용
        itemRow[23] = " ";
        
        //판매가 대체문구
        itemRow[24] = " ";
        
        //주문수량 제한 기준
        itemRow[25] = " ";
        
        //최소 주문수량(이상)
        itemRow[26] = " ";
        
        //최대 주문수량(이하)
        itemRow[27] = " ";
        
        //적립금
        itemRow[28] = " ";
        
        //적립금 구분
        itemRow[29] = " ";
        
        //공통이벤트 정보
        itemRow[30] = " ";
        
        //성인인증
        itemRow[31] = " ";
        
        //옵션사용
        itemRow[32] = " ";
        
        //품목 구성방식
        itemRow[33] = " ";
        
        //옵션 표시방식
        itemRow[34] = " ";
        
        //옵션세트명
        itemRow[35] = " ";
        
        //옵션입력
        itemRow[36] = " ";
        
        //옵션 스타일
        itemRow[37] = " ";
        
        //버튼이미지 설정
        itemRow[38] = " ";
        
        //색상 설정
        itemRow[39] = " ";
        
        //필수여부
        itemRow[40] = " ";
        
        //품절표시 문구
        itemRow[41] = " ";
        
        //추가입력옵션
        itemRow[42] = " ";
        
        //추가입력옵션 명칭
        itemRow[43] = " ";
        
        //추가입력옵션 선택/필수여부
        itemRow[44] = " ";
        
        //입력글자수(자)
        itemRow[45] = " ";
        
        //before csv file upload, mainImage should be uploaded on the fileUploader in cafe24.
        String mainImageUrl = Formatter.convertMainImageUrlCosmetic(item.getMassItem().getDirFileUploader(), item.getMassItem().getMainImageName());
        //String mainImageUrl = "";
        //이미지등록(상세)
        itemRow[46] = mainImageUrl;
        
        //이미지등록(목록)
        itemRow[47] = mainImageUrl;
        
        //이미지등록(작은목록)
        itemRow[48] = mainImageUrl;
        
        //이미지등록(축소)
        itemRow[49] = mainImageUrl;
        
        //이미지등록(추가)
        itemRow[50] = " ";
        
        //제조사
        itemRow[51] = " ";
        
        //공급사
        itemRow[52] = " ";
        
        //브랜드
        itemRow[53] = " ";
        
        //트렌드
        itemRow[54] = " ";
        
        //자체분류 코드
        itemRow[55] = " ";
        
        //제조일자
        itemRow[56] = " ";
        
        //출시일자
        itemRow[57] = " ";
        
        //유효기간 사용여부
        itemRow[58] = " ";
        
        //유효기간
        itemRow[59] = " ";
        
        //원산지
        itemRow[60] = " ";
        
        //상품부피(cm)
        itemRow[61] = " ";
        
        //상품결제안내
        itemRow[62] = " ";
        
        //상품배송안내
        itemRow[63] = " ";
        
        //교환/반품안내
        itemRow[64] = " ";
        
        //서비스문의/안내
        itemRow[65] = " ";
        
        //배송정보
        itemRow[66] = "F";
        
        //배송방법
        itemRow[67] = "B";
        
        //국내/해외배송
        itemRow[68] = "B";
        
        //배송지역
        itemRow[69] = "전국";
        
        //배송비 선결제 설정
        itemRow[70] = "C";
        
        //배송기간
        itemRow[71] = "10";
        
        //배송비 구분
        itemRow[72] = "D";
        
        //배송비입력
        itemRow[73] = "8000";
        //스토어픽업 설정
        itemRow[74] = " ";
        //상품 전체중량(kg)
        itemRow[75] = " ";
        //HS코드
        itemRow[76] = " ";
        //상품 구분(해외통관)
        itemRow[77] = " ";
        //상품소재
        itemRow[78] = " ";
        //영문 상품소재(해외통관)
        itemRow[79] = " ";
        //옷감(해외통관)
        itemRow[80] = " ";
        //검색엔진최적화(SEO) 검색엔진 노출 설정
        itemRow[81] = " ";
        //검색엔진최적화(SEO) Title
        itemRow[82] = " ";
        //검색엔진최적화(SEO) Author
        itemRow[83] = " ";
        //검색엔진최적화(SEO) Description
        itemRow[84] = " ";
        //검색엔진최적화(SEO) Keywords
        itemRow[85] = " ";
        //검색엔진최적화(SEO) 상품 이미지 Alt 텍스트
        itemRow[86] = " ";
        //개별결제수단설정
        itemRow[87] = " ";
        //상품배송유형 코드
        itemRow[88] = " ";
        //메모
        itemRow[89] = " ";
        
        return itemRow;
    }
    
    private void createCsvFileCosmeticTest(String dirCsvFile) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(dirCsvFile + brandNameKor + "_test_cafe24.csv");
            fileWriter.append("상품코드,자체 상품코드,진열상태,판매상태,상품분류 번호,상품분류 신상품영역,상품분류 추천상품영역,상품명,영문 상품명,상품명(관리용),공급사 상품명,모델명,상품 요약설명,상품 간략설명,상품 상세설명,모바일 상품 상세설명 설정,모바일 상품 상세설명,검색어설정,과세구분,소비자가,공급가,상품가,판매가,판매가 대체문구 사용,판매가 대체문구,주문수량 제한 기준,최소 주문수량(이상),최대 주문수량(이하),적립금,적립금 구분,공통이벤트 정보,성인인증,옵션사용,품목 구성방식,옵션 표시방식,옵션세트명,옵션입력,옵션 스타일,버튼이미지 설정,색상 설정,필수여부,품절표시 문구,추가입력옵션,추가입력옵션 명칭,추가입력옵션 선택/필수여부,입력글자수(자),이미지등록(상세),이미지등록(목록),이미지등록(작은목록),이미지등록(축소),이미지등록(추가),제조사,공급사,브랜드,트렌드,자체분류 코드,제조일자,출시일자,유효기간 사용여부,유효기간,원산지,상품부피(cm),상품결제안내,상품배송안내,교환/반품안내,서비스문의/안내,배송정보,배송방법,국내/해외배송,배송지역,배송비 선결제 설정,배송기간,배송비 구분,배송비입력,스토어픽업 설정,상품 전체중량(kg),HS코드,상품 구분(해외통관),상품소재,영문 상품소재(해외통관),옷감(해외통관),검색엔진최적화(SEO) 검색엔진 노출 설정,검색엔진최적화(SEO) Title,검색엔진최적화(SEO) Author,검색엔진최적화(SEO) Description,검색엔진최적화(SEO) Keywords,검색엔진최적화(SEO) 상품 이미지 Alt 텍스트,개별결제수단설정,상품배송유형 코드,메모");
            fileWriter.append(NEW_LINE_SEPARATOR);
            
            writerCsvCosmetic(fileWriter, massItemCosmeticList.get(0));
        } catch (IOException e) {
        } finally {
            
           try {
               fileWriter.flush();
               fileWriter.close();
           } catch (IOException e) {
               System.out.println("Error while flushing/closing fileWriter !!!");
               e.printStackTrace();
           }
       }
    }
    
    private void createCsvFileModeTest(String dirCsvFile) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(dirCsvFile + brandNameKor + "_test_cafe24.csv");
            fileWriter.append("상품코드,자체 상품코드,진열상태,판매상태,상품분류 번호,상품분류 신상품영역,상품분류 추천상품영역,상품명,영문 상품명,상품명(관리용),공급사 상품명,모델명,상품 요약설명,상품 간략설명,상품 상세설명,모바일 상품 상세설명 설정,모바일 상품 상세설명,검색어설정,과세구분,소비자가,공급가,상품가,판매가,판매가 대체문구 사용,판매가 대체문구,주문수량 제한 기준,최소 주문수량(이상),최대 주문수량(이하),적립금,적립금 구분,공통이벤트 정보,성인인증,옵션사용,품목 구성방식,옵션 표시방식,옵션세트명,옵션입력,옵션 스타일,버튼이미지 설정,색상 설정,필수여부,품절표시 문구,추가입력옵션,추가입력옵션 명칭,추가입력옵션 선택/필수여부,입력글자수(자),이미지등록(상세),이미지등록(목록),이미지등록(작은목록),이미지등록(축소),이미지등록(추가),제조사,공급사,브랜드,트렌드,자체분류 코드,제조일자,출시일자,유효기간 사용여부,유효기간,원산지,상품부피(cm),상품결제안내,상품배송안내,교환/반품안내,서비스문의/안내,배송정보,배송방법,국내/해외배송,배송지역,배송비 선결제 설정,배송기간,배송비 구분,배송비입력,스토어픽업 설정,상품 전체중량(kg),HS코드,상품 구분(해외통관),상품소재,영문 상품소재(해외통관),옷감(해외통관),검색엔진최적화(SEO) 검색엔진 노출 설정,검색엔진최적화(SEO) Title,검색엔진최적화(SEO) Author,검색엔진최적화(SEO) Description,검색엔진최적화(SEO) Keywords,검색엔진최적화(SEO) 상품 이미지 Alt 텍스트,개별결제수단설정,상품배송유형 코드,메모");
            fileWriter.append(NEW_LINE_SEPARATOR);
            //create one item for test 
            writerCsvMode(fileWriter, massItemList.get(0));
        } catch (IOException e) {
        } finally {
            
           try {
               fileWriter.flush();
               fileWriter.close();
           } catch (IOException e) {
               System.out.println("Error while flushing/closing fileWriter !!!");
               e.printStackTrace();
           }
       }
    }
    
    //comma caution for register
    public void writerCsvMode(FileWriter fileWriter, BaseItem item) throws IOException{
        //상품코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //자체 상품코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //진열상태
        fileWriter.append("Y");
        fileWriter.append(COMMA_DELIMITER);
        //판매상태
        fileWriter.append("Y");
        fileWriter.append(COMMA_DELIMITER);
        //상품분류 번호
        fileWriter.append(categoryNumber);
        fileWriter.append(COMMA_DELIMITER);
        //상품분류 신상품영역
        fileWriter.append("N");
        fileWriter.append(COMMA_DELIMITER);
        //상품분류 추천상품영역
        fileWriter.append("N");
        fileWriter.append(COMMA_DELIMITER);
        //상품명
        fileWriter.append("[" + brandNameKor + "] " + item.getItemFullnameDE());
        fileWriter.append(COMMA_DELIMITER);
        //영문 상품명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품명(관리용)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //공급사 상품명
        fileWriter.append(Formatter.abbreviateStringLeft(item.getMassItem().getItemUrl(), 250));
        fileWriter.append(COMMA_DELIMITER);
        //모델명
        fileWriter.append(item.getMassItem().getItemTitleDE());
        fileWriter.append(COMMA_DELIMITER);
        //상품 요약설명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품 간략설명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        
        //상품 상세설명
        String description = item.getDetailPageCafe24();
        fileWriter.append(description);
        fileWriter.append(COMMA_DELIMITER);

        //모바일 상품 상세설명 설정
        fileWriter.append("A");
        fileWriter.append(COMMA_DELIMITER);
        //모바일 상품 상세설명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색어설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //과세구분
        fileWriter.append("A|10");
        fileWriter.append(COMMA_DELIMITER);
        //소비자가
        fileWriter.append(item.getPriceWonString());
        fileWriter.append(COMMA_DELIMITER);
        //공급가
        fileWriter.append(Double.toString(item.getMassItem().getItemPriceEuro()));
        fileWriter.append(COMMA_DELIMITER);
        //상품가
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //판매가
        fileWriter.append(item.isItemSale() ? item.getPriceSaleWonString() : item.getPriceWonString());
        fileWriter.append(COMMA_DELIMITER);
        //판매가 대체문구 사용
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //판매가 대체문구
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //주문수량 제한 기준
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //최소 주문수량(이상)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //최대 주문수량(이하)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //적립금
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //적립금 구분
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //공통이벤트 정보
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //성인인증
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옵션사용
        fileWriter.append("Y");
        fileWriter.append(COMMA_DELIMITER);
        //품목 구성방식
        fileWriter.append("T");
        fileWriter.append(COMMA_DELIMITER);
        //옵션 표시방식
        fileWriter.append("C");
        fileWriter.append(COMMA_DELIMITER);
        //옵션세트명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옵션입력
        //fileWriter.append("");
        fileWriter.append(item.getSizeOptionCafe24());
        fileWriter.append(COMMA_DELIMITER);
        //옵션 스타일
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //버튼이미지 설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //색상 설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //필수여부
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //품절표시 문구
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //추가입력옵션
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //추가입력옵션 명칭
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //추가입력옵션 선택/필수여부
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //입력글자수(자)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        
        //before csv file upload, mainImage should be uploaded on the fileUploader in cafe24.
        //mainImage-directory is not used any more. Let modify the path for mainImage
        //String mainImageUrl = Formatter.convertMainImageUrlMode(item.getMassItem().getDirFileUploader(), item.getMassItem().getMainImageName());
        String mainImageUrl = Formatter.convertMainImageUrlMode(dirFileUploader, item.getMassItem().getMainImageName());
        
        //String mainImageUrl = "";
        //이미지등록(상세)
        fileWriter.append(mainImageUrl);
        fileWriter.append(COMMA_DELIMITER);
        //이미지등록(목록)
        fileWriter.append(mainImageUrl);
        fileWriter.append(COMMA_DELIMITER);
        //이미지등록(작은목록)
        fileWriter.append(mainImageUrl);
        fileWriter.append(COMMA_DELIMITER);
        //이미지등록(축소)
        fileWriter.append(mainImageUrl);
        fileWriter.append(COMMA_DELIMITER);
        //이미지등록(추가)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        
        //제조사
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //공급사
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //브랜드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //트렌드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //자체분류 코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //제조일자
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //출시일자
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //유효기간 사용여부
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //유효기간
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //원산지
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품부피(cm)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품결제안내
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품배송안내
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //교환/반품안내
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //서비스문의/안내
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        
        //배송정보
        fileWriter.append("F"); // 개별배송
        //fileWriter.append("T"); //기본배송
        fileWriter.append(COMMA_DELIMITER);
        
        //배송방법
        fileWriter.append("A");
        fileWriter.append(COMMA_DELIMITER);
        //국내/해외배송
        fileWriter.append("B");
        fileWriter.append(COMMA_DELIMITER);
        //배송지역
        fileWriter.append("전국");
        fileWriter.append(COMMA_DELIMITER);
        //배송비 선결제 설정
        fileWriter.append("B");
        fileWriter.append(COMMA_DELIMITER);
        //배송기간
        fileWriter.append("10|21");
        fileWriter.append(COMMA_DELIMITER);
        
        //배송비 구분 - 구매금액별 구간
        fileWriter.append("D");
        fileWriter.append(COMMA_DELIMITER);
        
        //배송비입력 - 무료배송
        fileWriter.append("0|10000000|0");
        fileWriter.append(COMMA_DELIMITER);
        
        //스토어픽업 설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품 전체중량(kg)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //HS코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품 구분(해외통관)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품소재
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //영문 상품소재(해외통관)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옷감(해외통관)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) 검색엔진 노출 설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) Title
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) Author
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) Description
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) Keywords
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) 상품 이미지 Alt 텍스트
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //개별결제수단설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품배송유형 코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //메모
        fileWriter.append("");
        fileWriter.append(NEW_LINE_SEPARATOR);
    }
    
    public void writerCsvCosmetic(FileWriter fileWriter, BaseItemCosmetic item) throws IOException{
        //상품코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //자체 상품코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //진열상태
        fileWriter.append("Y");
        fileWriter.append(COMMA_DELIMITER);
        //판매상태
        fileWriter.append("Y");
        fileWriter.append(COMMA_DELIMITER);
        //상품분류 번호
        fileWriter.append(categoryNumber);
        fileWriter.append(COMMA_DELIMITER);
        //상품분류 신상품영역
        fileWriter.append("Y");
        fileWriter.append(COMMA_DELIMITER);
        //상품분류 추천상품영역
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품명
        fileWriter.append(item.getItemFullnameWithPrefix());
        fileWriter.append(COMMA_DELIMITER);
        //영문 상품명
        //fileWriter.append("");
        fileWriter.append(Formatter.abbreviateStringLeft(item.getMassItem().getItemUrl(), 250));
        fileWriter.append(COMMA_DELIMITER);
        //상품명(관리용)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //공급사 상품명
        fileWriter.append(item.getMassItem().getItemTitleDE());
        fileWriter.append(COMMA_DELIMITER);
        //모델명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품 요약설명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품 간략설명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        
        // 상품 상세설명
        // comma in description bring error 
        //String description = GrobalDefined.TRANSLATE ? item.getItemFullDescriptionKOR() : item.getItemFullDescriptionDE();
        String description = item.getItemFullDescriptionKOR();
        fileWriter.append(description != "" ? Formatter.formatWithoutComma(description) : "");
        //fileWriter.append(Formatter.formatWithoutComma(item.getMassItem().getItemDescription()));

        fileWriter.append(COMMA_DELIMITER);
        //모바일 상품 상세설명 설정
        fileWriter.append("A");
        fileWriter.append(COMMA_DELIMITER);
        //모바일 상품 상세설명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색어설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //과세구분
        fileWriter.append("A|10");
        fileWriter.append(COMMA_DELIMITER);
        
        //소비자가
        fileWriter.append(item.getPriceWonString());
        fileWriter.append(COMMA_DELIMITER);
        
        //공급가
        fileWriter.append(Double.toString(item.getMassItem().getItemPriceEuro()));
        fileWriter.append(COMMA_DELIMITER);
        //상품가
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //판매가
        fileWriter.append(item.getMassItem().isItemSale() ? item.getPriceSaleWonString() : item.getPriceWonString());
        fileWriter.append(COMMA_DELIMITER);
        //판매가 대체문구 사용
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //판매가 대체문구
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //주문수량 제한 기준
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //최소 주문수량(이상)fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //최대 주문수량(이하)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //적립금
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //적립금 구분
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //공통이벤트 정보
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //성인인증
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옵션사용
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //품목 구성방식
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옵션 표시방식
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옵션세트명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옵션입력
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옵션 스타일
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //버튼이미지 설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //색상 설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //필수여부
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //품절표시 문구
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //추가입력옵션
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //추가입력옵션 명칭
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //추가입력옵션 선택/필수여부
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //입력글자수(자)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        
        //before csv file upload, mainImage should be uploaded on the fileUploader in cafe24.
        String mainImageUrl = Formatter.convertMainImageUrlCosmetic(item.getMassItem().getDirFileUploader(), item.getMassItem().getMainImageName());
        //String mainImageUrl = "";
        //이미지등록(상세)
        fileWriter.append(mainImageUrl);
        fileWriter.append(COMMA_DELIMITER);
        //이미지등록(목록)
        fileWriter.append(mainImageUrl);
        fileWriter.append(COMMA_DELIMITER);
        //이미지등록(작은목록)
        fileWriter.append(mainImageUrl);
        fileWriter.append(COMMA_DELIMITER);
        //이미지등록(축소)
        fileWriter.append(mainImageUrl);
        fileWriter.append(COMMA_DELIMITER);
        //이미지등록(추가)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        
        //제조사
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //공급사
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //브랜드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //트렌드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //자체분류 코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //제조일자
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //출시일자
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //유효기간 사용여부
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //유효기간
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //원산지
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품부피(cm)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품결제안내
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품배송안내
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //교환/반품안내
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //서비스문의/안내
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //배송정보
        fileWriter.append("F");
        fileWriter.append(COMMA_DELIMITER);
        //배송방법
        fileWriter.append("B");
        fileWriter.append(COMMA_DELIMITER);
        //국내/해외배송
        fileWriter.append("B");
        fileWriter.append(COMMA_DELIMITER);
        //배송지역
        fileWriter.append("전국");
        fileWriter.append(COMMA_DELIMITER);
        //배송비 선결제 설정
        fileWriter.append("C");
        fileWriter.append(COMMA_DELIMITER);
        //배송기간
        fileWriter.append("10");
        fileWriter.append(COMMA_DELIMITER);
        //배송비 구분
        fileWriter.append("D");
        fileWriter.append(COMMA_DELIMITER);
        //배송비입력
        fileWriter.append("8000");
        fileWriter.append(COMMA_DELIMITER);
        //스토어픽업 설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품 전체중량(kg)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //HS코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품 구분(해외통관)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품소재
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //영문 상품소재(해외통관)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옷감(해외통관)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) 검색엔진 노출 설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) Title
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) Author
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) Description
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) Keywords
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) 상품 이미지 Alt 텍스트
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //개별결제수단설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품배송유형 코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //메모
        fileWriter.append("");
        fileWriter.append(NEW_LINE_SEPARATOR);
    }
    
    public void writerCsvSimpleCosmetic(FileWriter fileWriter, BaseItemSimpleCosmetic item) throws IOException{
        //상품코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //자체 상품코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //진열상태
        fileWriter.append("Y");
        fileWriter.append(COMMA_DELIMITER);
        //판매상태
        fileWriter.append("Y");
        fileWriter.append(COMMA_DELIMITER);
        //상품분류 번호
        fileWriter.append(categoryNumber);
        fileWriter.append(COMMA_DELIMITER);
        //상품분류 신상품영역
        fileWriter.append("Y");
        fileWriter.append(COMMA_DELIMITER);
        //상품분류 추천상품영역
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품명
        fileWriter.append(item.getItemFullnameWithPrefix());
        fileWriter.append(COMMA_DELIMITER);
        //영문 상품명
        //fileWriter.append("");
        fileWriter.append(Formatter.abbreviateStringLeft(item.getMassItem().getItemUrl(), 250));
        fileWriter.append(COMMA_DELIMITER);
        //상품명(관리용)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //공급사 상품명
        fileWriter.append(item.getMassItem().getItemTitleDE());
        fileWriter.append(COMMA_DELIMITER);
        //모델명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품 요약설명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품 간략설명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        
        // 상품 상세설명
        // comma in description bring error 
        //String description = GrobalDefined.TRANSLATE ? item.getItemFullDescriptionKOR() : item.getItemFullDescriptionDE();
        String description = item.getItemFullDescription();
        fileWriter.append(description != "" ? Formatter.formatWithoutComma(description) : "");
        //fileWriter.append(Formatter.formatWithoutComma(item.getMassItem().getItemDescription()));

        fileWriter.append(COMMA_DELIMITER);
        //모바일 상품 상세설명 설정
        fileWriter.append("A");
        fileWriter.append(COMMA_DELIMITER);
        //모바일 상품 상세설명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색어설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //과세구분
        fileWriter.append("A|10");
        fileWriter.append(COMMA_DELIMITER);
        
        //소비자가
        fileWriter.append(String.valueOf(item.getMassItem().getItemPriceWon()));
        fileWriter.append(COMMA_DELIMITER);
        
        //공급가
        fileWriter.append(Double.toString(item.getMassItem().getItemPriceEuro()));
        fileWriter.append(COMMA_DELIMITER);
        //상품가
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //판매가
        fileWriter.append(String.valueOf(item.getMassItem().getItemPriceWon()));
        fileWriter.append(COMMA_DELIMITER);
        //판매가 대체문구 사용
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //판매가 대체문구
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //주문수량 제한 기준
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //최소 주문수량(이상)fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //최대 주문수량(이하)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //적립금
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //적립금 구분
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //공통이벤트 정보
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //성인인증
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옵션사용
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //품목 구성방식
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옵션 표시방식
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옵션세트명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옵션입력
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옵션 스타일
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //버튼이미지 설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //색상 설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //필수여부
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //품절표시 문구
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //추가입력옵션
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //추가입력옵션 명칭
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //추가입력옵션 선택/필수여부
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //입력글자수(자)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        
        //before csv file upload, mainImage should be uploaded on the fileUploader in cafe24.
        String mainImageUrl = Formatter.convertMainImageUrlCosmetic(item.getConfig().getMainImagesCafe24Location(), item.getMassItem().getMainImageName());
        //String mainImageUrl = "";
        //이미지등록(상세)
        fileWriter.append(mainImageUrl);
        fileWriter.append(COMMA_DELIMITER);
        //이미지등록(목록)
        fileWriter.append(mainImageUrl);
        fileWriter.append(COMMA_DELIMITER);
        //이미지등록(작은목록)
        fileWriter.append(mainImageUrl);
        fileWriter.append(COMMA_DELIMITER);
        //이미지등록(축소)
        fileWriter.append(mainImageUrl);
        fileWriter.append(COMMA_DELIMITER);
        //이미지등록(추가)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        
        //제조사
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //공급사
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //브랜드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //트렌드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //자체분류 코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //제조일자
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //출시일자
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //유효기간 사용여부
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //유효기간
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //원산지
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품부피(cm)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품결제안내
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품배송안내
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //교환/반품안내
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //서비스문의/안내
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //배송정보
        fileWriter.append("F");
        fileWriter.append(COMMA_DELIMITER);
        //배송방법
        fileWriter.append("B");
        fileWriter.append(COMMA_DELIMITER);
        //국내/해외배송
        fileWriter.append("B");
        fileWriter.append(COMMA_DELIMITER);
        //배송지역
        fileWriter.append("전국");
        fileWriter.append(COMMA_DELIMITER);
        //배송비 선결제 설정
        fileWriter.append("C");
        fileWriter.append(COMMA_DELIMITER);
        //배송기간
        fileWriter.append("10");
        fileWriter.append(COMMA_DELIMITER);
        //배송비 구분
        fileWriter.append("D");
        fileWriter.append(COMMA_DELIMITER);
        //배송비입력
        fileWriter.append("8000");
        fileWriter.append(COMMA_DELIMITER);
        //스토어픽업 설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품 전체중량(kg)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //HS코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품 구분(해외통관)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품소재
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //영문 상품소재(해외통관)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옷감(해외통관)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) 검색엔진 노출 설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) Title
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) Author
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) Description
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) Keywords
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) 상품 이미지 Alt 텍스트
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //개별결제수단설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품배송유형 코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //메모
        fileWriter.append("");
        fileWriter.append(NEW_LINE_SEPARATOR);
    }
    
    public static void writerCsvManual(FileWriter fileWriter, BaseItemCosmetic item) throws IOException{
        //상품코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //자체 상품코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //진열상태
        fileWriter.append("Y");
        fileWriter.append(COMMA_DELIMITER);
        //판매상태
        fileWriter.append("Y");
        fileWriter.append(COMMA_DELIMITER);
        //상품분류 번호
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품분류 신상품영역
        fileWriter.append("Y");
        fileWriter.append(COMMA_DELIMITER);
        //상품분류 추천상품영역
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품명
        fileWriter.append(item.getItemFullnameWithPrefix());
        fileWriter.append(COMMA_DELIMITER);
        //영문 상품명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품명(관리용)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //공급사 상품명
        fileWriter.append(item.getMassItem().getItemTitleDE());
        fileWriter.append(COMMA_DELIMITER);
        //모델명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품 요약설명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품 간략설명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        
        //상품 상세설명
        // comma in description bring error 
        //String description = GrobalDefined.TRANSLATE ? item.getItemFullDescriptionKOR() : item.getItemFullDescriptionDE();
        String description = item.getItemFullDescriptionManual();
        fileWriter.append(Formatter.formatWithoutComma(description));
        //fileWriter.append(Formatter.formatWithoutComma(item.getMassItem().getItemDescription()));

        fileWriter.append(COMMA_DELIMITER);
        //모바일 상품 상세설명 설정
        fileWriter.append("A");
        fileWriter.append(COMMA_DELIMITER);
        //모바일 상품 상세설명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색어설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //과세구분
        fileWriter.append("A|10");
        fileWriter.append(COMMA_DELIMITER);
        //소비자가
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //공급가
        fileWriter.append(Double.toString(item.getMassItem().getItemPriceEuro()));
        fileWriter.append(COMMA_DELIMITER);
        //상품가
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //판매가
        fileWriter.append(item.getPriceWonString());
        fileWriter.append(COMMA_DELIMITER);
        //판매가 대체문구 사용
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //판매가 대체문구
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //주문수량 제한 기준
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //최소 주문수량(이상)fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //최대 주문수량(이하)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //적립금
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //적립금 구분
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //공통이벤트 정보
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //성인인증
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옵션사용
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //품목 구성방식
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옵션 표시방식
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옵션세트명
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옵션입력
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옵션 스타일
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //버튼이미지 설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //색상 설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //필수여부
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //품절표시 문구
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //추가입력옵션
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //추가입력옵션 명칭
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //추가입력옵션 선택/필수여부
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //입력글자수(자)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        
        //before csv file upload, mainImage should be uploaded on the fileUploader in cafe24.
        String mainImageUrl = Formatter.convertMainImageUrlCosmetic(item.getMassItem().getDirFileUploader(), item.getMassItem().getMainImageName());
        
        //String mainImageUrl = "";
        //이미지등록(상세)
        fileWriter.append(mainImageUrl);
        fileWriter.append(COMMA_DELIMITER);
        //이미지등록(목록)
        fileWriter.append(mainImageUrl);
        fileWriter.append(COMMA_DELIMITER);
        //이미지등록(작은목록)
        fileWriter.append(mainImageUrl);
        fileWriter.append(COMMA_DELIMITER);
        //이미지등록(축소)
        fileWriter.append(mainImageUrl);
        fileWriter.append(COMMA_DELIMITER);
        //이미지등록(추가)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        
        //제조사
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //공급사
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //브랜드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //트렌드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //자체분류 코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //제조일자
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //출시일자
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //유효기간 사용여부
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //유효기간
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //원산지
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품부피(cm)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품결제안내
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품배송안내
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //교환/반품안내
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //서비스문의/안내
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //배송정보
        fileWriter.append("F");
        fileWriter.append(COMMA_DELIMITER);
        //배송방법
        fileWriter.append("B");
        fileWriter.append(COMMA_DELIMITER);
        //국내/해외배송
        fileWriter.append("B");
        fileWriter.append(COMMA_DELIMITER);
        //배송지역
        fileWriter.append("전국");
        fileWriter.append(COMMA_DELIMITER);
        //배송비 선결제 설정
        fileWriter.append("C");
        fileWriter.append(COMMA_DELIMITER);
        //배송기간
        fileWriter.append("10");
        fileWriter.append(COMMA_DELIMITER);
        //배송비 구분
        fileWriter.append("D");
        fileWriter.append(COMMA_DELIMITER);
        //배송비입력
        fileWriter.append("8000");
        fileWriter.append(COMMA_DELIMITER);
        //스토어픽업 설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품 전체중량(kg)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //HS코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품 구분(해외통관)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품소재
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //영문 상품소재(해외통관)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //옷감(해외통관)
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) 검색엔진 노출 설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) Title
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) Author
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) Description
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) Keywords
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //검색엔진최적화(SEO) 상품 이미지 Alt 텍스트
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //개별결제수단설정
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //상품배송유형 코드
        fileWriter.append("");
        fileWriter.append(COMMA_DELIMITER);
        //메모
        fileWriter.append("");
        fileWriter.append(NEW_LINE_SEPARATOR);
    }
    
  //deprecated 23.02.2021
//    public static void writerCsv(FileWriter fileWriter, BaseItemCosmetic item) throws IOException{
//        //상품코드
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //자체 상품코드
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //진열상태
//        fileWriter.append("Y");
//        fileWriter.append(COMMA_DELIMITER);
//        //판매상태
//        fileWriter.append("Y");
//        fileWriter.append(COMMA_DELIMITER);
//        //상품분류 번호
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //상품분류 신상품영역
//        fileWriter.append("Y");
//        fileWriter.append(COMMA_DELIMITER);
//        //상품분류 추천상품영역
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //상품명
//        fileWriter.append(item.getItemFullnameWithPrefix());
//        fileWriter.append(COMMA_DELIMITER);
//        //영문 상품명
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //상품명(관리용)
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //공급사 상품명
//        fileWriter.append(item.getMassItem().getItemTitleDE());
//        fileWriter.append(COMMA_DELIMITER);
//        //모델명
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //상품 요약설명
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //상품 간략설명
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        
//        //상품 상세설명
//        // comma in description bring error 
//        String description = GrobalDefined.TRANSLATE ? item.getItemFullDescriptionKOR() : item.getItemFullDescriptionDE(); 
//        fileWriter.append(Formatter.formatWithoutComma(description));
//
//        fileWriter.append(COMMA_DELIMITER);
//        //모바일 상품 상세설명 설정
//        fileWriter.append("A");
//        fileWriter.append(COMMA_DELIMITER);
//        //모바일 상품 상세설명
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //검색어설정
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //과세구분
//        fileWriter.append("A|10");
//        fileWriter.append(COMMA_DELIMITER);
//        //소비자가
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //공급가
//        fileWriter.append(Double.toString(item.getMassItem().getItemPriceEuro()));
//        fileWriter.append(COMMA_DELIMITER);
//        //상품가
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //판매가
//        fileWriter.append(item.getPriceWonString());
//        fileWriter.append(COMMA_DELIMITER);
//        //판매가 대체문구 사용
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //판매가 대체문구
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //주문수량 제한 기준
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //최소 주문수량(이상)fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //최대 주문수량(이하)
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //적립금
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //적립금 구분
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //공통이벤트 정보
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //성인인증
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //옵션사용
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //품목 구성방식
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //옵션 표시방식
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //옵션세트명
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //옵션입력
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //옵션 스타일
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //버튼이미지 설정
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //색상 설정
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //필수여부
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //품절표시 문구
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //추가입력옵션
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //추가입력옵션 명칭
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //추가입력옵션 선택/필수여부
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //입력글자수(자)
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        
//        //before csv file upload, mainImage should be uploaded on the fileUploader in cafe24.
//        //String mainImageUrl = convertMainImageUrl(item.getMainImageFileName());
//        String mainImageUrl = "";
//        //이미지등록(상세)
//        fileWriter.append(mainImageUrl);
//        fileWriter.append(COMMA_DELIMITER);
//        //이미지등록(목록)
//        fileWriter.append(mainImageUrl);
//        fileWriter.append(COMMA_DELIMITER);
//        //이미지등록(작은목록)
//        fileWriter.append(mainImageUrl);
//        fileWriter.append(COMMA_DELIMITER);
//        //이미지등록(축소)
//        fileWriter.append(mainImageUrl);
//        fileWriter.append(COMMA_DELIMITER);
//        //이미지등록(추가)
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        
//        //제조사
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //공급사
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //브랜드
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //트렌드
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //자체분류 코드
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //제조일자
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //출시일자
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //유효기간 사용여부
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //유효기간
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //원산지
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //상품부피(cm)
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //상품결제안내
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //상품배송안내
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //교환/반품안내
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //서비스문의/안내
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //배송정보
//        fileWriter.append("F");
//        fileWriter.append(COMMA_DELIMITER);
//        //배송방법
//        fileWriter.append("B");
//        fileWriter.append(COMMA_DELIMITER);
//        //국내/해외배송
//        fileWriter.append("B");
//        fileWriter.append(COMMA_DELIMITER);
//        //배송지역
//        fileWriter.append("전국");
//        fileWriter.append(COMMA_DELIMITER);
//        //배송비 선결제 설정
//        fileWriter.append("C");
//        fileWriter.append(COMMA_DELIMITER);
//        //배송기간
//        fileWriter.append("10");
//        fileWriter.append(COMMA_DELIMITER);
//        //배송비 구분
//        fileWriter.append("D");
//        fileWriter.append(COMMA_DELIMITER);
//        //배송비입력
//        fileWriter.append("8000");
//        fileWriter.append(COMMA_DELIMITER);
//        //스토어픽업 설정
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //상품 전체중량(kg)
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //HS코드
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //상품 구분(해외통관)
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //상품소재
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //영문 상품소재(해외통관)
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //옷감(해외통관)
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //검색엔진최적화(SEO) 검색엔진 노출 설정
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //검색엔진최적화(SEO) Title
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //검색엔진최적화(SEO) Author
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //검색엔진최적화(SEO) Description
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //검색엔진최적화(SEO) Keywords
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //검색엔진최적화(SEO) 상품 이미지 Alt 텍스트
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //개별결제수단설정
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //상품배송유형 코드
//        fileWriter.append("");
//        fileWriter.append(COMMA_DELIMITER);
//        //메모
//        fileWriter.append("");
//        fileWriter.append(NEW_LINE_SEPARATOR);
//    }

}