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
import util.ImageDownloader;
import util.GrobalDefined.Gender;

public class AgentAcne {
    private static final Logger LOGGER = LogManager.getLogger(AgentAcne.class);
    public final static String BRAND_HOMEPAGE_URL = "https://www.acnestudios.com";
    public final static String BRAND_NAME_KOR = "아크네";
    public final static String BRAND_NAME_DE = "acne";
    public final static String ITEM_CATETOTY = "/t-shirt/";
    public static String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/의류/" + BRAND_NAME_DE;
    public static String DIR_BRAND_CATEGORY = DIR_BRAND + ITEM_CATETOTY;
    public static String HTML_BRAND = DIR_BRAND_CATEGORY + "/women_t-shirt.html";
    
    public static String DIR_FILEUPLOADER = BRAND_NAME_DE + ITEM_CATETOTY + "main_images/";
    public static String DIR_MAIN_IMAGES = DIR_BRAND_CATEGORY + "main_images/";
    public static String DIR_EXCEL_FILE = DIR_BRAND_CATEGORY;
    
    public static final int DELIVERY_FEE= 8000;
    public static Gender CATEGORY_GENDER = Gender.FEMALE;
    public static final String CATEGORY_ID_SMARTSTORE = "50000803";
    public static final String CATEGORY_NUMBER_CAFE24 = "273";

    private static final String [] ITEM_SIZE_US = {"XXS","XS", "S", "M", "L"};
    private static final String [] ITEM_SIZE_UK = {"UK8", "UK10", "UK12", "UK14", "UK16"};
    private static final String [] ITEM_SIZE_FR = {"0(UK6)", "1(UK8)", "2(UK10)", "3(UK12)", "4(UK14)"};
    
    private static final String [] ITEM_SIZE_PRICE = {"0", "0", "0", "0", "0"};
    private static final String [] ITEM_SIZE_STOCK = {"10", "10", "10", "10", "10"};
    
    public static final List<String> ITEM_SIZE_LIST = Arrays.asList(ITEM_SIZE_US);
    public static final List<String> ITEM_SIZE_PRICE_LIST = Arrays.asList(ITEM_SIZE_PRICE);
    public static final List<String> ITEM_SIZE_STOCK_LIST = Arrays.asList(ITEM_SIZE_STOCK);

    public static List<String> itemSameTitleTester = new ArrayList<>();
    
    public static void main(String[] args) throws Exception {
        LOGGER.info("A mission of " + BRAND_NAME_DE + " starts ===>>> ");
        
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
        
        Cafe24 cafe24 = new Cafe24(baseItemList, BRAND_NAME_KOR, CATEGORY_NUMBER_CAFE24);
        cafe24.createCsvFileMode(DIR_EXCEL_FILE);
        
        SmartStore smartStore = new SmartStore(baseItemList, CATEGORY_ID_SMARTSTORE, BRAND_NAME_KOR);
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
        } catch (IOException e) {
            LOGGER.error("Error extracting detailImages:" + itemUrl);
        }
        
        //2. extract the price
        extractItemPrice(doc, item);
        
        //3. extract the materials
        extractItemMaterials(doc, item);
        
        //4. Options: setitemSize, setitemSizePrice, setitemSizeStock
        setOptions(item);
    }
    
    private static void setOptions(MassItem item) {
        item.setItemSizes(ITEM_SIZE_LIST);
        item.setItemSizesPrice(ITEM_SIZE_PRICE_LIST);
        item.setItemSizesStock(ITEM_SIZE_STOCK_LIST);
        item.setGender(CATEGORY_GENDER);
        item.setModeDeiveryFee(DELIVERY_FEE);
    }
    
    private static void extractItemMaterials(Document doc, MassItem item) {
        Objects.requireNonNull(doc);
        Element body = doc.body();
        Elements elementsMaterials = extractMaterialsElements(body);
        if (elementsMaterials.size()==0) {
            return;
        }
        
        String materials = getMaterials(elementsMaterials);

        LOGGER.info("itemMaterials: " + materials);
        item.setMaterials(materials);
    }
    
    private static Elements extractMaterialsElements(Element body) {
        
        Elements materialsElements = body.getElementsByClass("custom-composition");
        
        return materialsElements;
    }
    
    private static String getMaterials(Elements elementsMaterials) {
        String materials = elementsMaterials.get(0).text();        
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
        
        setOriginPrice(elementsPrices, item);
    }
    
    private static void setOriginPrice(Elements elementsPrices, MassItem item) {
        String formatOrgPrice = Formatter.deleteNonDigits(elementsPrices.get(0).text());
        
        LOGGER.info("itemPrice: " + formatOrgPrice);
        item.setItemPriceEuro(Double.valueOf(formatOrgPrice));
    }
    
    private static Elements extractPriceElements(Element body) {
        
        Elements priceElements = body.getElementsByClass("product-price");
        
        return priceElements;
    }
    
    public static void extractDetailImages(Document doc, MassItem item) throws IOException {
        Objects.requireNonNull(doc);
        Element body = doc.body();
        Elements elementsDetaiImage = extractDetailElements(body);
        
        //inclusive homepage url
        List<String> formattedImageList = new ArrayList<>();
        
        for(int i=0; i<elementsDetaiImage.size(); i++) {
            Element detailImageElement = elementsDetaiImage.get(i);
            String imageUrl = extractDetailImageUrl(detailImageElement);
            formattedImageList.add(BRAND_HOMEPAGE_URL + imageUrl);
        }
        item.setItemDetailImages(formattedImageList);
    }
    
    private static String extractDetailImageUrl(Element detailImageElement) {
        
        //not include homepage url
        String detailImageUrl = detailImageElement.attr("data-zoom-src");
        
        LOGGER.info("itemDetailImageUrl: " + detailImageUrl);
        return detailImageUrl;
    }
    
    private static Elements extractDetailElements(Element body) {
        
        Elements galleryElements = body.getElementsByClass("product-item__gallery-container");
        Elements detailImageElements = galleryElements.get(0).getElementsByTag("img");

        return detailImageElements;
    }

    // extract url, itemTitle, image downloading, brandname kor ,DE
    public static List<String> preprocessing(List<MassItem> massItemList) throws IOException {
        List<String> itemUrls = new ArrayList<>();
        File input = new File(HTML_BRAND);
        Document doc = Jsoup.parse(input, "UTF-8", "");
        Element body = doc.body();
        
        Elements unitElements = getUnitElements(body);
        
        for (int i=0; i<unitElements.size(); i++) {
            String url = extractUrl(unitElements.get(i));
            
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
                
                extractItemTitle(massItem, unitElements.get(i));
                
                setBrandName(massItem);
                
                setMainImageName(massItem);
                
                extractMainImage(massItem, unitElements.get(i));
            }
        }
        return itemUrls;
    }
    
    private static Elements getUnitElements(Element body) {
        
        Element result = body.getElementsByClass("search-result-container").get(0);
        Element element = result.child(0);
        Elements rawUnitElements = element.getElementsByTag("li");
        Elements unitElements = new Elements();
        for (int i=0; i<rawUnitElements.size(); i++) {
            Element unit = rawUnitElements.get(i);
            if(unit.className().contains("grid-tile product-list__item-tile")) {
                unitElements.add(unit);
            }
        }
        
        return unitElements;
    }
    
    private static String extractUrl(Element urlElement) {
        
        Elements urlElements = urlElement.getElementsByClass("product-image ");
        String url = BRAND_HOMEPAGE_URL + urlElements.get(0).getElementsByClass("thumb-link").get(0).attr("href");
        
        LOGGER.info("itemUrl:" + url);
        return url;
    }
    
    private static void setItemUrl(MassItem massItem, List<String> itemUrls, List<MassItem> massItemList, String validUrl) {
        massItemList.add(massItem);
        itemUrls.add(validUrl);
        massItem.setItemUrl(validUrl);
    }
    
    private static void extractItemTitle(MassItem massItem, Element element) {
        
        Elements productInfoElements = element.getElementsByClass("product-info product-list__item-info");
        Elements productNameElements = productInfoElements.get(0).getElementsByClass("product-name");
        Elements itemTitleElements = productNameElements.get(0).getElementsByTag("a");
        String itemTitle = itemTitleElements.text();
        
        String validItemTitle = getValidItemTitle(itemTitle);

        LOGGER.info("itemTitle:" + validItemTitle);
        massItem.setItemTitleDE(validItemTitle);
    }
    
    private static void setBrandName(MassItem massItem) {
        massItem.setBrandNameDE(BRAND_NAME_DE);
        massItem.setBrandNameKor(BRAND_NAME_KOR);
        massItem.setBrandHomepageUrl(BRAND_HOMEPAGE_URL);
    }
    
    private static void setMainImageName(MassItem massItem) {
        String mainImageName = Formatter.replaceUmlaut(Formatter.replaceEmptySymbol(massItem.getItemTitleDE()));
        massItem.setMainImageName(mainImageName);
    }
    
    public static void extractMainImage(MassItem item,Element urlElement) throws IOException {
        Elements productImageElements = urlElement.getElementsByClass("product-image ").get(0).getElementsByClass("thumb-link");
        String imageUrls = productImageElements.get(0).getElementsByTag("img").attr("srcset");
        String rawImageUrl = Formatter.splitAfterWord(imageUrls, ".jpg").get(0);
        String mainImageUrl = BRAND_HOMEPAGE_URL + Formatter.removeEmptySymbol(rawImageUrl) + ".jpg";
        
        savingMainImage(item.getMainImageName(), DIR_MAIN_IMAGES, mainImageUrl);
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
