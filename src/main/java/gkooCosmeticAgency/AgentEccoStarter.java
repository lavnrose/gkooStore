package gkooCosmeticAgency;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import agencyBrandEntities.MassItemEcco;
import agencyEntities.AgentEcco;
import agencyEntities.BaseItemCosmetic;
import agencyEntities.MassItem;
import coupang.CoupangApi;
import factoryExcel.Cafe24;
import factoryExcel.CsvFile;
import util.Formatter;
import util.GrobalDefined;
import util.MathUtil;

public class AgentEccoStarter {
    private static final Logger LOGGER = LogManager.getLogger(AgentEccoStarter.class);
    
    public final static String BRAND_HOMEPAGE_URL = "https://www.ecco-verde.de/";
    public static final String BRAND_NAME_KOR = "라베라";
    public static final String BRAND_NAME_DE = "lavera";
    public static final String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/cosmetic/ecoverde/" + BRAND_NAME_DE;
    public final static String ITEM_CATEGORY = "hair";
    public final static String DIR_ITEM_CATETOTY = "/" + ITEM_CATEGORY + "/";
    public static final String DIR_BRAND_CATEGORY = DIR_BRAND + DIR_ITEM_CATETOTY;
    public static final String HTML_BRAND = DIR_BRAND_CATEGORY + "lavera_hair.html";
    
    public static String DIR_FILEUPLOADER = BRAND_NAME_DE + DIR_ITEM_CATETOTY;
    public static String DIR_MAIN_IMAGES = DIR_BRAND_CATEGORY + "main_images/";
    public static String DIR_EXCEL_FILE = DIR_BRAND_CATEGORY;

    public static final String CATEGORY_ID_COOPANG = "";
    public static final String CATEGORY_NUMBER_CAFE24 = "306";
    
    public static void main(String[] args) throws Exception {
        //1.
        createCafe24();
        
        //2. check csv file!!
        createCoupang();
        
        //3. other volume with price
        //createOtherPrice();
    }
    
    private static void createOtherPrice() {
        List<String> itemPriceList = Arrays.asList("14.99","18.99","19.99");
        
        for (int i = 0; i< itemPriceList.size(); i++) {
            MassItem item = new MassItem();
            String price = itemPriceList.get(i);
                item.setItemPriceEuro(Double.valueOf(price));
                MassItemEcco ecco = new MassItemEcco(Double.valueOf(price));
                String priceWon = ecco.getPriceWonString();
                System.out.println(priceWon);
        }
    }
    
    private static void createCafe24() throws IOException {
        LOGGER.info("the csv file for cafe24 starts ===>>> " + BRAND_NAME_KOR);
        AgentEcco agent = getConfiguredAgent();
        
        /**
         * save manually html in local and gathering urls, item titles, downloading main images
         */
        List<MassItem> massItemList = new ArrayList<>();
        List<String> itemUrlList = agent.preprocessing(massItemList, HTML_BRAND);
        
        int size = itemUrlList.size();
        //int size = 1;
        
        for(int i=0; i<size; i++) { 
            agent.createMassItem(itemUrlList.get(i), massItemList.get(i));
            LOGGER.info("MassItem is created:" + i);
        }

        List<BaseItemCosmetic> baseItemCosmeticList = new ArrayList<>();
        for(int i=0; i<size; i++) {
            MassItemEcco massItemEcco = new MassItemEcco(massItemList.get(i));
            baseItemCosmeticList.add(massItemEcco);
        }
        
        Cafe24 cafe24 = new Cafe24(BRAND_NAME_KOR, CATEGORY_NUMBER_CAFE24, baseItemCosmeticList);
        //cafe24.createCsvFileCosmetic(DIR_EXCEL_FILE);
        cafe24.createExcelFileCosmetic(DIR_EXCEL_FILE);
        LOGGER.info("the csv file for cafe24 is finished <<<=== ");
    }
    
    private static void createCoupang() throws FileNotFoundException, IOException, CsvValidationException {
        LOGGER.info("Coupang API starts ===>>> " + BRAND_NAME_KOR);
        String fileName = DIR_BRAND_CATEGORY + "/라베라_hair_cafe24.csv";
        List<List<String>> records = new ArrayList<List<String>>();
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        }
        
        int start = 1;
        int size = records.size();
        for(int i=start; i< size; i++) {
            List<String> cosmetic = records.get(i);
            int originalPrice = getCoupangPrice(cosmetic.get(22));
            int salePrice = getCoupangPrice(cosmetic.get(22));
            String contentHtml = cosmetic.get(14);
            String mainImageName = cosmetic.get(10);
            String displayProductName = cosmetic.get(7);
            String brand = "weleda";
            CoupangApi.createProductCosmetic(GrobalDefined.categoryCodeCoopang.get(""), 
                    originalPrice, salePrice, contentHtml, mainImageName, displayProductName, brand, DIR_FILEUPLOADER);
        }
        LOGGER.info("Coupang API is finished <<<=== ");
    }
    
    private static int getCoupangPrice(String price) {
        int cafe24Price = Integer.valueOf(price);
        double coupangPrice = (cafe24Price/0.9);
        int ceiledProductPriceWon = MathUtil.mathCeilDigit(3, coupangPrice);
        return ceiledProductPriceWon;
    }
    
    public static AgentEcco getConfiguredAgent() {
        AgentEcco agent = new AgentEcco(BRAND_NAME_DE, BRAND_NAME_KOR, ITEM_CATEGORY, DIR_MAIN_IMAGES, DIR_FILEUPLOADER);
        return agent;
    }
}
