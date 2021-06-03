package factoryExcel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import agencyEntities.MassItem;

public class CsvFile {
    private static final String NEW_LINE_SEPARATOR = "\n";

    private String fileName;
    private String dirCsvFile;

    public CsvFile(String fileName, String dirCsvFile) {
        this.fileName = fileName;
        this.dirCsvFile = dirCsvFile;
    }
    
    public static void main(String[] args) throws Exception {
        String dirName = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/cosmetic/ecoverde/lavera/hair/";
        String fileName = "라베라_cosmetic";
        File myFile = new File(dirName + fileName + ".xlsx");
        int sheetIdx = 0; // 0 for first sheet
 
        convertSelectedSheetInXLXSFileToCSV(myFile, sheetIdx, dirName, fileName);

    }
    
    private static void convertSelectedSheetInXLXSFileToCSV(File xlsxFile, int sheetIdx, String dirName, String fileName) throws Exception {
        FileWriter fileWriter = new FileWriter(dirName + fileName + ".csv");

        FileInputStream fileInStream = new FileInputStream(xlsxFile);
 
        // Open the xlsx and get the requested sheet from the workbook
        XSSFWorkbook workBook = new XSSFWorkbook(fileInStream);
        XSSFSheet selSheet = workBook.getSheetAt(sheetIdx);
 
        // Iterate through all the rows in the selected sheet
        Iterator<Row> rowIterator = selSheet.iterator();
        while (rowIterator.hasNext()) {
 
            Row row = rowIterator.next();
 
            // Iterate through all the columns in the row and build ","
            // separated string
            Iterator<Cell> cellIterator = row.cellIterator();
            StringBuffer sb = new StringBuffer();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if (sb.length() != 0) {
                    sb.append(",");
                }
                 
                // If you are using poi 4.0 or over, change it to
                // cell.getCellType
                switch (cell.getCellType()) {
                case STRING:
                    sb.append(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    sb.append(cell.getNumericCellValue());
                    break;
                case BOOLEAN://BLANK
                    sb.append(cell.getBooleanCellValue());
                    break;
                case BLANK:
                    sb.append(" " + ",");
                    break;
                default:
                    sb.append(cell + ",");
                }
            }
            System.out.println(sb.toString());
            fileWriter.append(sb.toString());
            fileWriter.append(NEW_LINE_SEPARATOR);
        }
        workBook.close();
        
        fileWriter.flush();
        fileWriter.close();
    }
    
    public void createPreCsvFileCosmetic(List<MassItem> massItemList) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(dirCsvFile + fileName + "_pre.csv");
            //fileWriter.append("상품코드,자체 상품코드,진열상태,판매상태,상품분류 번호,상품분류 신상품영역,상품분류 추천상품영역,상품명,영문 상품명,상품명(관리용),공급사 상품명,모델명,상품 요약설명,상품 간략설명,상품 상세설명,모바일 상품 상세설명 설정,모바일 상품 상세설명,검색어설정,과세구분,소비자가,공급가,상품가,판매가,판매가 대체문구 사용,판매가 대체문구,주문수량 제한 기준,최소 주문수량(이상),최대 주문수량(이하),적립금,적립금 구분,공통이벤트 정보,성인인증,옵션사용,품목 구성방식,옵션 표시방식,옵션세트명,옵션입력,옵션 스타일,버튼이미지 설정,색상 설정,필수여부,품절표시 문구,추가입력옵션,추가입력옵션 명칭,추가입력옵션 선택/필수여부,입력글자수(자),이미지등록(상세),이미지등록(목록),이미지등록(작은목록),이미지등록(축소),이미지등록(추가),제조사,공급사,브랜드,트렌드,자체분류 코드,제조일자,출시일자,유효기간 사용여부,유효기간,원산지,상품부피(cm),상품결제안내,상품배송안내,교환/반품안내,서비스문의/안내,배송정보,배송방법,국내/해외배송,배송지역,배송비 선결제 설정,배송기간,배송비 구분,배송비입력,스토어픽업 설정,상품 전체중량(kg),HS코드,상품 구분(해외통관),상품소재,영문 상품소재(해외통관),옷감(해외통관),검색엔진최적화(SEO) 검색엔진 노출 설정,검색엔진최적화(SEO) Title,검색엔진최적화(SEO) Author,검색엔진최적화(SEO) Description,검색엔진최적화(SEO) Keywords,검색엔진최적화(SEO) 상품 이미지 Alt 텍스트,개별결제수단설정,상품배송유형 코드,메모");
            //fileWriter.append(NEW_LINE_SEPARATOR);
            for (int i=0;i<massItemList.size();i++) {
                final CsvMapper mapper = new CsvMapper();
                final CsvSchema schema = mapper.schemaFor(MassItem.class);
                final String csv = mapper.writer(schema.withUseHeader(false)).writeValueAsString(massItemList.get(i));
                fileWriter.append(csv);
                fileWriter.append(NEW_LINE_SEPARATOR);
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
}
