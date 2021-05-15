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
import util.Formatter;
import util.GrobalDefined.Gender;
import util.ImageDownloader;

public class AgentMaje {
    private static final Logger LOGGER = LogManager.getLogger(AgentMaje.class);
    public final static String BRAND_HOMEPAGE_URL = "https://eu.maje.com/";
    public final static String BRAND_NAME_KOR = "마쥬";
    public final static String BRAND_NAME_DE = "maje";
    public final static String ITEM_CATETOTY = "/top/";
    public final static String ITEM_CATETOTY_KOR = "블라우스";
    public static String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/의류/" + BRAND_NAME_DE;
    public static String DIR_BRAND_CATEGORY = DIR_BRAND + ITEM_CATETOTY;
    public static String HTML_BRAND = DIR_BRAND_CATEGORY + "/women_top.html";
    
    public static String DIR_FILEUPLOADER = BRAND_NAME_DE + ITEM_CATETOTY;
    public static String DIR_MAIN_IMAGES = DIR_BRAND_CATEGORY + "main_images/";
    public static String DIR_EXCEL_FILE = DIR_BRAND_CATEGORY;
    
    public static final int DELIVERY_FEE= 10000;
    public static Gender CATEGORY_GENDER = Gender.FEMALE;
    public static final String CATEGORY_ID_SMARTSTORE = "50000804";
    public static final String CATEGORY_NUMBER_CAFE24 = "271";

    private static final String [] ITEM_SIZE_US = {"XS", "S", "M", "L", "XL"};
    private static final String [] ITEM_SIZE_UK = {"UK8", "UK10", "UK12", "UK14", "UK16"};
    private static final String [] ITEM_SIZE_FR = {"0(UK6)", "1(UK8)", "2(UK10)", "3(UK12)", "4(UK14)"};
    
    private static final String [] ITEM_SIZE_PRICE = {"0", "0", "0", "0", "0"};
    private static final String [] ITEM_SIZE_STOCK = {"10", "10", "10", "10", "10"};
    
    public static final List<String> ITEM_SIZE_LIST = Arrays.asList(ITEM_SIZE_UK);
    public static final List<String> ITEM_SIZE_PRICE_LIST = Arrays.asList(ITEM_SIZE_PRICE);
    public static final List<String> ITEM_SIZE_STOCK_LIST = Arrays.asList(ITEM_SIZE_STOCK);

    public static List<String> itemSameTitleTester = new ArrayList<>();
    
    public static void main(String[] args) throws Exception {
        LOGGER.info("A mission of agentMaje starts ===>>> ");
        
        /**
         * save manually html in local and gathering urls, item titles, downloading main images
         */
        List<MassItem> massItemList = new ArrayList<>();
        List<String> itemUrlList = preprocessing(massItemList);
        
        for(int i=0; i<itemUrlList.size(); i++) { 
        // sale for(int i=16; i<17; i++) {
        //for(int i=17; i<19; i++) {
            createMassItem(itemUrlList.get(i), massItemList.get(i));
            //LOGGER.info("MassItem is created:" + i);
        }
        
        List<BaseItem> baseItemList = new ArrayList<>();
        for(int i=0; i<massItemList.size(); i++) {
        //for(int i=16; i<17; i++) {
        //for(int i=17; i<19; i++) {
            //MassItemMode massItemLando = new MassItemMode(massItem);
            MassItemMode massItemLando = new MassItemMode(massItemList.get(i));
            baseItemList.add(massItemLando);
        }
        
        Cafe24 cafe24 = new Cafe24(baseItemList, BRAND_NAME_KOR, CATEGORY_NUMBER_CAFE24, DIR_FILEUPLOADER);
        cafe24.createCsvFileMode(DIR_EXCEL_FILE);
        
        SmartStore smartStore = new SmartStore(baseItemList, CATEGORY_ID_SMARTSTORE, BRAND_NAME_KOR + " " + ITEM_CATETOTY_KOR);
        smartStore.createExcelMode(DIR_EXCEL_FILE);
        
        LOGGER.info("A mission end <<<=== ");
    }
    
    public static void createMassItem(final String itemUrl, MassItem item) throws Exception {
        Document doc = null;
        try {
            doc = Jsoup.connect(itemUrl).userAgent("Chrome").get();
        } catch (IOException e) {
            LOGGER.error("Error connection itemUrl:" + itemUrl);
            return;
        } 
        
        //1. extract the detailed images
        try {
            extractDetailImages(doc, item);
            LOGGER.info("extractDetailImages starts...");
        } catch (IOException e) {
            LOGGER.error("Error extracting detailImages:" + itemUrl);
        }
        
        //2. extract the price
        extractItemPrice(doc, item);
        LOGGER.info("extractItemPrice starts...");
        
        //3. extract the materials
        extractItemMaterials(doc, item);
        LOGGER.info("extractItemMaterials starts...");
        
        //4. Options: setitemSize, setitemSizePrice, setitemSizeStock
        setOptions(item);
    }
    
    private static void setOptions(MassItem item) {
        item.setItemSizes(ITEM_SIZE_LIST);
        item.setItemSizesPrice(ITEM_SIZE_PRICE_LIST);
        item.setItemSizesStock(ITEM_SIZE_STOCK_LIST);
        item.setGender(CATEGORY_GENDER);
        item.setModeDeiveryFee(DELIVERY_FEE);
        item.setDirFileUploader(DIR_FILEUPLOADER);
    }
    
    private static void extractItemMaterials(Document doc, MassItem item) {
        Objects.requireNonNull(doc);
        Element body = doc.body();
        Elements elementsMaterials = extractMaterialsElements(body);
        if (elementsMaterials.size()==0) {
            return;
        }
        
        String materials = getMaterials(elementsMaterials);
        
        item.setMaterials(materials);
    }
    
    private static Elements extractMaterialsElements(Element body) {
        
        Elements materialsElements = body.getElementsByClass("tabs-menu");
        
        return materialsElements;
    }
    
    private static String getMaterials(Elements elementsMaterials) {
        String materials = elementsMaterials.get(0).child(1).getElementsByClass("block-content").text();
        
        return materials;
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
        Elements elementsPrices = extractPriceElements(body);

        if(elementsPrices.size() == 0) {
            return;
        }
        
        if(hasSalePrice(elementsPrices)){
            setSalesPrice(elementsPrices, item);
        } else {
            setOriginPrice(elementsPrices, item);
        }
    }
    
    private static void setOriginPrice(Elements elementsPrices, MassItem item) {
        String formatOrgPrice = Formatter.deleteNonDigits(elementsPrices.get(0).text());
        
        item.setItemPriceEuro(Double.valueOf(formatOrgPrice));
    }

    private static void setSalesPrice(Elements elementsPrices, MassItem item) {
        Elements prices = elementsPrices.get(0).getAllElements();
        String orgPrice = prices.get(0).getElementsByClass("price-standard ").text();
        String salesPrice = prices.get(0).getElementsByClass("price-sales ").text();
        //System.out.println(Formatter.deleteNonDigits(orgPrice));
        //System.out.println(Formatter.deleteNonDigits(salesPrice));
        item.setItemSale(true);
        item.setItemPriceEuro(Double.valueOf(Formatter.deleteNonDigits(orgPrice)));
        item.setItemSalePriceEuro(Double.valueOf(Formatter.deleteNonDigits(salesPrice)));
    }
    
    private static boolean hasSalePrice(Elements elementsPrices) {
        
        Elements prices = elementsPrices.get(0).getAllElements();
        boolean hasSalesPrice = prices.hasClass("price-sales ");
        
        return hasSalesPrice;
    }
    
    private static Elements extractPriceElements(Element body) {
        
        Elements priceElements = body.getElementsByClass("product-price");
        
        return priceElements;
    }
    
    public static void extractDetailImages(Document doc, MassItem item) throws IOException {
        Objects.requireNonNull(doc);
        Element body = doc.body();
        Elements elementsDetaiImage = extractDetailElements(body);
        
        List<String> formattedImageList = new ArrayList<>();
        
        for(int i=0; i<elementsDetaiImage.size(); i++) {
            Element detailImageElement = elementsDetaiImage.get(i);
            String imageUrl = extractDetailImageUrl(detailImageElement);
            formattedImageList.add(imageUrl);
        }
        item.setItemDetailImages(formattedImageList);
    }
    
    private static String extractDetailImageUrl(Element detailImageElement) {
        
        String detailImageUrl = detailImageElement.getElementsByTag("img").attr("data-src");
        
        return detailImageUrl;
    }
    
    private static Elements extractDetailElements(Element body) {
        
        Elements detailImageElements = body.getElementsByClass("img-container");
        
        return detailImageElements;
    }

    // extract url, itemTitle, image downloading, brandname kor ,DE
    public static List<String> preprocessing(List<MassItem> massItemList) throws IOException {
        List<String> itemUrls = new ArrayList<>();
        File input = new File(HTML_BRAND);
        Document doc = Jsoup.parse(input, "UTF-8", "");
        Element body = doc.body();
        
        Elements urlElements = getUrlElements(body);
        
        for (int i=0; i<urlElements.size(); i++) {
            String url = extractUrl(urlElements.get(i));
            
            boolean validUrl = true;
            try {
                doc = Jsoup.connect(url).timeout(2000).userAgent("Chrome").get();
            } catch (IOException e) {
                LOGGER.error("url is invalid:" + url);
                validUrl = false;
            } 
            
            //filter the url
            if(itemUrls.contains(url)) {
                validUrl = false;
            }
            
            if (validUrl) {
                MassItem massItem = new MassItem();
                
                setItemUrl(massItem, itemUrls, massItemList, url);
                
                setItemTitle(massItem, urlElements.get(i));
                
                setBrandName(massItem);
                
                setMainImageName(massItem);
                
                downloadingMainImage(massItem, urlElements.get(i));
            }
        }
        return itemUrls;
    }
    
    private static Elements getUrlElements(Element body) {
        
        Element result = body.getElementsByClass("search-result-content visible").get(0);
        Elements urlElements = result.getElementsByClass("product-tile ");
        
        return urlElements;
    }
    
    private static String extractUrl(Element urlElement) {
        
        Elements items = urlElement.getElementsByClass("product-image");
        Elements thumbLink = items.get(0).getElementsByClass("thumb-link");
        String url = thumbLink.attr("href");
        
        return url;
    }
    
    private static void setItemUrl(MassItem massItem, List<String> itemUrls, List<MassItem> massItemList, String validUrl) {
        massItemList.add(massItem);
        itemUrls.add(validUrl);
        massItem.setItemUrl(validUrl);
    }
    
    private static void setItemTitle(MassItem massItem, Element element) {
        
        Element infos = element.getElementsByClass("infosProduct").get(0);
        String itemTitle = Formatter.formatWithoutComma(infos.getElementsByClass("product-name").get(0).getElementsByClass("name-link").text());
        
        //format title
        String formattedItemTitle = Formatter.replaceUmlaut(itemTitle);
        
        String validItemTitle = getValidItemTitle(formattedItemTitle);
        
        massItem.setItemTitleDE(validItemTitle);
    }
    
    private static void setBrandName(MassItem massItem) {
        massItem.setBrandNameDE(BRAND_NAME_DE);
        massItem.setBrandNameKor(BRAND_NAME_KOR);
        massItem.setBrandHomepageUrl(BRAND_HOMEPAGE_URL);
    }
    
    private static void setMainImageName(MassItem massItem) {
        String mainImageName = Formatter.replaceEmptySymbol(massItem.getItemTitleDE());
        
        LOGGER.info("mainImageName: " + mainImageName);
        massItem.setMainImageName(mainImageName);
    }
    
    public static void downloadingMainImage(MassItem item,Element urlElement) throws IOException {
        Elements items = urlElement.getElementsByClass("product-image").get(0).getElementsByClass("thumb-link");
        String imageUrl = items.get(0).getAllElements().get(0).getElementsByTag("img").attr("data-src");
        savingMainImage(item.getMainImageName(), DIR_MAIN_IMAGES, imageUrl);
    }
     
    private static void savingMainImage(final String imageName, String directory, final String imageUrl) {
        ImageDownloader.resizeImageScalrZalando(imageName, directory, imageUrl, 500);
    }
    
    private static String getValidItemTitle(String itemTitle) {
        Objects.requireNonNull(itemTitle);
        
        String validTitle = "";
        List<String> matchedItems = new ArrayList<>();
        for(String title : itemSameTitleTester) {
            if(title.contains(itemTitle)) {
                matchedItems.add(title);
            }
        }
        int matchedItemSize = matchedItems.size();
        if(matchedItemSize>0) {
            validTitle = itemTitle + "_" + String.valueOf(matchedItemSize+1);
        } else {
            validTitle = itemTitle;
        }
        
        itemSameTitleTester.add(validTitle);
        return validTitle;
    }
}
