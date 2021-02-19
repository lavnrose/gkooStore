package factoryExcel;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import agencyEntities.BaseItem;
import agencyEntities.BaseItemCosmetic;
import gkooModeAgency.AgentMaje;
import util.Formatter;
import util.GrobalDefined;

public class Cafe24 {
    private String brandNameKor;
    private String categoryNumber;
    private String dirFileUploader;
    private List<BaseItemCosmetic> massItemCosmeticList;
    private List<BaseItem> massItemList;
    
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    
    public Cafe24(List<BaseItemCosmetic> itemList, String brandNameKor, String categoryNumber) {
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
    
//    public Cafe24(List<?> itemList, String brandNameKor, String categoryNumber, String dirFileUploader) {
//        this.brandNameKor = brandNameKor;
//        this.categoryNumber = categoryNumber;
//        this.dirFileUploader = dirFileUploader;
//        
//        if(itemList.get(0) instanceof BaseItem) {
//            this.massItemList = (List<BaseItem>) itemList;
//        } else if(itemList.get(0) instanceof BaseItemCosmetic) {
//            this.massItemCosmeticList = (List<BaseItemCosmetic>) itemList;
//        }
//    }

    public void createCsvFile(String dirCsvFile) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(dirCsvFile + brandNameKor + "_cafe24.csv");
            for (int i=0;i<massItemCosmeticList.size();i++) {
                writerCsv(fileWriter, massItemCosmeticList.get(i));
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
    
    public void createCsvFileManual(String dirCsvFile, String productName) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(dirCsvFile + brandNameKor + "_" + productName + "_cafe24.csv");
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
        String mainImageUrl = Formatter.convertMainImageUrl( "mode/" + dirFileUploader, item.getMassItem().getMainImageName());
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
        fileWriter.append(item.getItemTitleDE());
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
        String description = item.getItemFullDescriptionDE();
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
        String mainImageUrl = Formatter.convertMainImageUrl(item.getMassItem().getMainImageName());
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
    
    public static void writerCsv(FileWriter fileWriter, BaseItemCosmetic item) throws IOException{
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
        fileWriter.append(item.getItemTitleDE());
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
        String description = GrobalDefined.TRANSLATE ? item.getItemFullDescriptionKOR() : item.getItemFullDescriptionDE(); 
        fileWriter.append(Formatter.formatWithoutComma(description));

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
        //String mainImageUrl = convertMainImageUrl(item.getMainImageFileName());
        String mainImageUrl = "";
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

    public List<BaseItem> getMassItemList() {
        return massItemList;
    }

    public void setMassItemList(List<BaseItem> massItemList) {
        this.massItemList = massItemList;
    }

    public List<BaseItemCosmetic> getMassItemCosmeticList() {
        return massItemCosmeticList;
    }

    public void setMassItemCosmeticList(List<BaseItemCosmetic> massItemCosmeticList) {
        this.massItemCosmeticList = massItemCosmeticList;
    }
}