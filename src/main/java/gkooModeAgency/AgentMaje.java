package gkooModeAgency;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import agencyBrandEntities.MassItemMode;
import agencyEntities.BaseItem;
import agencyEntities.MassItem;
import factoryExcel.Cafe24;
import factoryExcel.SmartStore;
import gkooAgency.AgentZal;
import util.Formatter;
import util.GrobalDefined.Gender;
import util.ImageDownloader;

public class AgentMaje {
    private static final Logger LOGGER = LogManager.getLogger(AgentMaje.class);
    public final static String BRAND_NAME_KOR = "마쥬";
    public final static String BRAND_NAME_DE = "maje";
    public static String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/의류/" + BRAND_NAME_DE;
    public static String DIR_BRAND_CATEGORY = DIR_BRAND + "/top/";
    public static String HTML_BRAND = DIR_BRAND_CATEGORY + "/women_top.html";
    public static String DIR_MAIN_IMAGES = DIR_BRAND_CATEGORY + "main_images/";
    public static String DIR_EXCEL_FILE = DIR_BRAND_CATEGORY;
    
    public static final int DELIVERY_FEE= 8000;
    public static final String ITEM_CATEGORY = "여성 니트/스웨터";
    public static Gender CATEGORY_GENDER = Gender.FEMALE;
    public static final String CATEGORY_ID_SMARTSTORE = "50000804";

    private static final String [] ITEM_SIZE = {"XS", "S", "M", "L", "XL"};
    private static final String [] ITEM_SIZE_NUMBER = {"0(UK6)", "1(UK8)", "2(UK10)", "3(UK12)", "4(UK14)"};
    private static final String [] ITEM_SIZE_PRICE = {"0", "0", "0", "0", "0"};
    private static final String [] ITEM_SIZE_STOCK = {"10", "10", "10", "10", "10"};
    public static final List<String> ITEM_SIZE_LIST = Arrays.asList(ITEM_SIZE_NUMBER);
    public static final List<String> ITEM_SIZE_PRICE_LIST = Arrays.asList(ITEM_SIZE_PRICE);
    public static final List<String> ITEM_SIZE_STOCK_LIST = Arrays.asList(ITEM_SIZE_STOCK);
    
    public static final String CATEGORY_ID_COOPANG = "";
    
    public static void main(String[] args) throws Exception {
        LOGGER.info("A mission of agentMaje starts ===>>> ");
        
        /**
         * save manually html in local and gathering urls, item titles, downloading main images
         */
        List<MassItem> massItemList = new ArrayList<>();
        List<String> itemUrlList = preprocessing(massItemList);
        
        //for(int i=0; i<itemUrlList.size(); i++) { 
        // sale for(int i=16; i<17; i++) {
        for(int i=0; i<1; i++) {
            createMassItem(itemUrlList.get(i), massItemList.get(i));
            //LOGGER.info("MassItem is created:" + i);
        }
        
        List<BaseItem> baseItemList = new ArrayList<>();
        //for(int i=0; i<massItemList.size(); i++) {
        //for(int i=16; i<17; i++) {
        for(int i=0; i<1; i++) {
            //MassItemMode massItemLando = new MassItemMode(massItem);
            MassItemMode massItemLando = new MassItemMode(massItemList.get(i));
            baseItemList.add(massItemLando);
        }
        
        Cafe24 cafe24 = new Cafe24(BRAND_NAME_KOR, baseItemList);
        cafe24.createCsvFileMode(AgentMaje.DIR_EXCEL_FILE);
        
        SmartStore smartStore = new SmartStore(baseItemList, CATEGORY_ID_SMARTSTORE, BRAND_NAME_KOR);
        smartStore.createExcelMode(AgentMaje.DIR_EXCEL_FILE);
        
        LOGGER.info("A mission end <<<=== ");
    }
    
    public static void createMassItem(final String itemUrl, MassItem item) throws Exception {
        Document doc = Jsoup.connect(itemUrl).get();
        item.setItemUrl(itemUrl);
        System.out.println("itemUrl: "+itemUrl);
        //1. extract the detailed images
        try {
            extractDetailImages(doc, item);
        } catch (IOException e) {
            LOGGER.error("Error downloadingMainImage:" + itemUrl);
        }
        
        //2. extract the price
        extractItemPrice(doc, item);
        
        //3. extract the materials
        extractItemMaterials(doc, item);
        
        //3. Options: setitemSize, setitemSizePrice, setitemSizeStock
        item.setItemSizes(ITEM_SIZE_LIST);
        item.setItemSizesPrice(ITEM_SIZE_PRICE_LIST);
        item.setItemSizesStock(ITEM_SIZE_STOCK_LIST);
        
        item.setGender(CATEGORY_GENDER);
        
        item.setModeDeiveryFee(AgentMaje.DELIVERY_FEE);
    }
    
    private static void extractItemMaterials(Document doc, MassItem item) {
        Objects.requireNonNull(doc);
        Element body = doc.body();
        Elements elementsMaterials = body.getElementsByClass("tabs-menu");
        if (elementsMaterials.size()==0) {
            return;
        }
        System.out.println(elementsMaterials.get(0).child(1).getElementsByClass("block-content").text());
        item.setMaterials(elementsMaterials.get(0).child(1).getElementsByClass("block-content").text());
    }
    
    /**
     * extract the sale price, orig price
     * 
     * @param article
     * @param item
     */
    private static void extractItemPrice(Document doc, MassItem item) {
        Objects.requireNonNull(doc);
        Element body = doc.body();
        Elements elementsPrices = body.getElementsByClass("product-price");
        //System.out.println(elementsPrices.get(0).text());
        //System.out.println(elementsPrices.get(0).getAllElements().hasClass("price-sales "));
        if(elementsPrices.size() == 0) {
            return;
        }
        Elements prices = elementsPrices.get(0).getAllElements();
        if (prices.hasClass("price-sales ")) {
            String orgPrice = prices.get(0).getElementsByClass("price-standard ").text();
            String salesPrice = prices.get(0).getElementsByClass("price-sales ").text();
            System.out.println(Formatter.deleteNonDigits(orgPrice));
            System.out.println(Formatter.deleteNonDigits(salesPrice));
            item.setItemPriceEuro(Double.valueOf(Formatter.deleteNonDigits(orgPrice)));
            item.setItemSalePriceEuro(Double.valueOf(Formatter.deleteNonDigits(salesPrice)));
        } else {
            String formatOrgPrice = Formatter.deleteNonDigits(elementsPrices.get(0).text());
            item.setItemPriceEuro(Double.valueOf(formatOrgPrice));
        }
    }
    
    public static void extractDetailImages(Document doc, MassItem item) throws IOException {
        Objects.requireNonNull(doc);
        Element body = doc.body();
        Elements elementsImg = body.getElementsByClass("img-container");
        List<String> formattedImageList = new ArrayList<>();
        
        for(int i=0; i<elementsImg.size(); i++) {
            Element ele = elementsImg.get(i);
            String imageUrl = ele.getElementsByTag("img").attr("data-src");
            formattedImageList.add(imageUrl);
        }
        item.setItemDetailImages(formattedImageList);
    }

    public static List<String> preprocessing(List<MassItem> massItemList) throws IOException {
        List<String> itemUrls = new ArrayList<>();

        File input = new File(HTML_BRAND);
        Document doc = Jsoup.parse(input, "UTF-8", "");
        Element body = doc.body();
        Element result = body.getElementsByClass("search-result-content visible").get(0);
        Elements productTiles = result.getElementsByClass("product-tile ");
        for (int i=0; i<productTiles.size(); i++) {
        
            Elements items = productTiles.get(i).getElementsByClass("product-image");
            MassItem massItem = new MassItem();
            massItemList.add(massItem);
            Elements thumbLink = items.get(0).getElementsByClass("thumb-link");
            String url = thumbLink.attr("href");
            itemUrls.add(url);
            massItem.setItemUrl(url);
            Element infos = productTiles.get(i).getElementsByClass("infosProduct").get(0);
            String itemTitle = Formatter.formatWithoutComma(infos.getElementsByClass("product-name").get(0).getElementsByClass("name-link").text());
            massItem.setItemTitleDE(itemTitle);
            massItem.setBrandNameDE(AgentMaje.BRAND_NAME_DE);
            massItem.setBrandNameKor(AgentMaje.BRAND_NAME_KOR);
            String mainImageName = Formatter.replaceUmlaut(Formatter.replaceEmptySymbol(itemTitle));
            massItem.setMainImageName(mainImageName);
            downloadingMainImage(thumbLink, massItem);
        }
//        System.out.println(result.getElementById("search-result-items").getElementsByClass("infosProduct").get(18).getElementsByClass("product-name").get(0).getElementsByClass("name-link").text());
//        System.out.println(result.getElementById("search-result-items").getElementsByClass("infosProduct").get(18).getElementsByClass("product-pricing").get(0).getElementsByClass("product-pricing-data").text());
//        System.out.println(result.getElementById("search-result-items").getElementsByClass("infosProduct").get(18).getElementsByClass("product-pricing").get(0).getElementsByClass("product-pricing-data").get(0).getAllElements().hasClass("reducePercent"));
//        System.out.println(result.getElementById("search-result-items").getElementsByClass("infosProduct").get(17).getElementsByClass("product-pricing").get(0).getElementsByClass("product-pricing-data").get(0));
        return itemUrls;
    }
    
   public static void downloadingMainImage(Elements thumbLink, MassItem item) throws IOException {
       System.out.println(thumbLink.get(0).getAllElements().get(0).getElementsByTag("img").attr("src"));
       String imageUrl = thumbLink.get(0).getAllElements().get(0).getElementsByTag("img").attr("src");
       savingMainImage(item.getMainImageName(), DIR_MAIN_IMAGES, imageUrl);
   }
    
    private static void savingMainImage(final String imageName, String directory, final String imageUrl) {
        ImageDownloader.resizeImageScalrZalando(imageName, directory, imageUrl, 500);
    }
}
