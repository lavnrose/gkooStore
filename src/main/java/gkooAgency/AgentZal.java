package gkooAgency;

import java.io.File;
import java.io.FileNotFoundException;
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
import agencyBrandEntities.MassItemLando;
import agencyEntities.BaseItem;
import agencyEntities.MassItem;
import factoryExcel.SmartStore;
import translator.TranslateApi;
import util.Formatter;
import util.GrobalDefined;
import util.ImageDownloader;
import util.GrobalDefined.Gender;

public class AgentZal {
    private static final Logger LOGGER = LogManager.getLogger(AgentZal.class);
    public static String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/의류/zalando";
    public static String DIR_BRAND_CATEGORY = DIR_BRAND + "/women" + "/pullover/";
    public static String HTML_BRAND = DIR_BRAND_CATEGORY + "/women_pullover.html";
    public static String DIR_MAIN_IMAGES = DIR_BRAND_CATEGORY + "main_images/";
    public static String DIR_EXCEL_FILE = DIR_BRAND_CATEGORY;
    
    public static final String ITEM_CATEGORY = "여성 니트/스웨터";
    public static Gender CATEGORY_GENDER = Gender.FEMALE;

    private static final String [] ITEM_SIZE = {"XS", "S", "M", "L", "XL"};
    private static final String [] ITEM_SIZE_PRICE = {"0", "0", "0", "0", "0"};
    private static final String [] ITEM_SIZE_STOCK = {"10", "10", "10", "10", "10"};
    public static final List<String> ITEM_SIZE_LIST = Arrays.asList(ITEM_SIZE);
    public static final List<String> ITEM_SIZE_PRICE_LIST = Arrays.asList(ITEM_SIZE_PRICE);
    public static final List<String> ITEM_SIZE_STOCK_LIST = Arrays.asList(ITEM_SIZE_STOCK);
    
    public static final String CATEGORY_ID_SMARTSTORE = "50000805";
    public static final String CATEGORY_ID_COOPANG = "";
    public static final String DELIVERY_FEE= "10000";

    private final String googleTranslateWeb = "https://translate.google.com/";
    
    public static void main(String[] args) throws Exception {
        LOGGER.info("A mission of agentZal starts ===>>> ");
        
        /**
         * save manually html in local and gathering urls
         */
        List<MassItem> massItemList = new ArrayList<>();
        List<String> itemUrlList = getItemUrlList(massItemList);

        for(int i=0; i<itemUrlList.size(); i++) {
            createMassItem(itemUrlList.get(i), massItemList.get(i));
            LOGGER.info("MassItem is created:" + i);
        }
        
        List<BaseItem> baseItemList = new ArrayList<>();
        for(MassItem massItem : massItemList) {
            MassItemLando massItemLando = new MassItemLando(massItem);
            baseItemList.add(massItemLando);
        }
        
        SmartStore smartStore = new SmartStore(baseItemList, CATEGORY_ID_SMARTSTORE, ITEM_CATEGORY);
        smartStore.createExcelLando(AgentZal.DIR_EXCEL_FILE);
        
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
        
        //2. extract the materials
        extractItemMaterials(doc, item);
        
        //3. Options: setitemSize, setitemSizePrice, setitemSizeStock
        item.setItemSizes(ITEM_SIZE_LIST);
        item.setItemSizesPrice(ITEM_SIZE_PRICE_LIST);
        item.setItemSizesStock(ITEM_SIZE_STOCK_LIST);
        
        item.setGender(CATEGORY_GENDER);
    }
    
    public static void extractItemMaterials(Document doc, MassItem item) {
        Element bodyMat = doc.body();
        //Elements material = bodyMat.childNodeSize();
                //getElementsByClass("re-data-el-init");
        //Element elementMat = material.get(0).child(0);//.child(1);
        
        //System.out.println(bodyMat.getElementsByClass("rQP559 C3wGFf JT3_zV").get(2).getElementsByClass("_1z5_Qg").get(2).getElementsByClass("OXMl2N xVaPPo f2qidi _56Chwa"));
        //System.out.println(bodyMat.getElementsByClass("OXMl2N xVaPPo f2qidi _56Chwa").get(0).child(0).child(1).text());
        item.setMaterials(bodyMat.getElementsByClass("OXMl2N xVaPPo f2qidi _56Chwa").get(0).child(0).child(1).text());
    }
    
    public static List<String> getItemUrlList(List<MassItem> massItemList) throws IOException {
        List<String> itemUrls = new ArrayList<>();

        File input = new File(HTML_BRAND);
        Document doc = Jsoup.parse(input, "UTF-8", "");
        
        Elements articles = doc.select("article");
        //Document doc = Jsoup.connect(categotyUrl).get();
        
        for(int i=0; i<articles.size(); i++) {
        //for(int i=0; i<10; i++) {
            String url = "https://www.zalando.de/" + articles.get(i).childNode(0).attr("href");
            //System.out.println(url);
            boolean validItem = true;
            MassItem massItem = new MassItem();
            validItem = extractItemNames(articles.get(i), massItem);
            if(validItem) {
                itemUrls.add(url);
                extractItemPrices(articles.get(i), massItem);
                downloadingMainImage(articles.get(i), massItem);
                massItemList.add(massItem);
                LOGGER.info(i +") "+ "ItemUrl: " + url);
            }
        }
        
        return itemUrls;
    }
    
    private static String getFormattedJpgUrl(String rawUrl) {
        return Formatter.splitAfterWord(rawUrl, ".jpg").get(0) + ".jpg";
    }
    
    public static void downloadingMainImage(Element article, MassItem item) throws IOException {
        String formattedBrandName = Formatter.replaceAndSymbol(item.getBrandNameDE());
        String mainImageName = formattedBrandName + " " + item.getItemTitleDE();
        //System.out.println("main image url: " + article.childNode(0).childNode(0).childNode(1).childNode(0).childNode(0).attr("src"));
        //String rawMainImageUrl = article.childNode(0).childNode(0).childNode(1).childNode(0).childNode(0).attr("src");
        
        Element rawImageElement = (Element) article.childNode(0).childNode(0);
        String rawMainImageUrl = null;
        if(rawImageElement.childNodeSize() == 1) {
            //System.out.println(rawImageElement);
            Element mainElement = (Element) article.childNode(0).childNode(0).childNode(0).childNode(0).childNode(0);
            rawMainImageUrl = mainElement.attr("src");
        } else if(rawImageElement.childNodeSize() == 2) {
            Element mainElement = (Element) article.childNode(0).childNode(0).childNode(1).childNode(0).childNode(0);
            rawMainImageUrl = mainElement.attr("src");
        }
        
        String mainImageUrl = getFormattedJpgUrl(rawMainImageUrl);
        
        savingMainImage(mainImageName, DIR_MAIN_IMAGES, mainImageUrl);
        
        item.setMainImageName(mainImageName);
    }
    
    private static void savingMainImage(final String imageName, String directory, final String imageUrl) {
        ImageDownloader.resizeImageScalrZalando(imageName, directory, imageUrl, 500);
    }
    
    /**
     * extract the sale price, orig price
     * 
     * @param article
     * @param item
     */
    private static boolean extractItemNames(Element article, MassItem item) {
        if(article.getElementsByClass("_0xLoFW _78xIQ- EJ4MLB JT3_zV").size() == 0) {
            item.setBrandNameDE("No Brand");
            item.setItemTitleDE("No Title");
            LOGGER.warn("No brand and No Title");
            return false;
        } else {
            Element rawElementBrandName = article.getElementsByClass("_0xLoFW _78xIQ- EJ4MLB JT3_zV").get(0).getElementsByClass("hPWzFB").get(0);
            String brandName = null;
            String itemName = null;
            if(rawElementBrandName.childNodeSize() == 0) {
                LOGGER.error("No itemName");
            } else if(rawElementBrandName.childNodeSize() == 1){
                //System.out.println("");
                //System.out.println(article.getElementsByClass("_0xLoFW _78xIQ- EJ4MLB JT3_zV").get(0).getElementsByClass("hPWzFB").get(0).child(0));
            } else {
                brandName = article.getElementsByClass("_0xLoFW _78xIQ- EJ4MLB JT3_zV").get(0).getElementsByClass("hPWzFB").get(0).child(0).text();
                itemName = article.getElementsByClass("_0xLoFW _78xIQ- EJ4MLB JT3_zV").get(0).getElementsByClass("hPWzFB").get(0).child(1).text();
            }

            //System.out.println("brand: " + brandName);
            item.setBrandNameDE(brandName);
            //System.out.println("item name: " + itemName);
            String formattedItemName = Formatter.replaceForwardSlash(itemName); 
            item.setItemTitleDE(formattedItemName);
            if(formattedItemName != null && formattedItemName != "") {
                if(GrobalDefined.TRANSLATE) {
                    try {
                        item.setItemTitleEN(TranslateApi.doTranslateDEtoEng(formattedItemName));
                    } catch (FileNotFoundException e) {
                        LOGGER.error("Error translation:" + item.getItemUrl());
                    } catch (IOException e) {
                        LOGGER.error("Error translation:" + item.getItemUrl());
                    }
                }
            }
            return true;
        }
    }
    
    /**
     * extract the sale price, orig price
     * 
     * @param artical
     * @param item
     */
    private static void extractItemPrices(Element article, MassItem item) {
        if(article.getElementsByClass("_0xLoFW _78xIQ- EJ4MLB JT3_zV").size() == 0) {
            item.setItemPriceEuro(Double.valueOf(10000));
            LOGGER.warn("No price");
        } else {
            Element priceParent = article.getElementsByClass("_0xLoFW _78xIQ- EJ4MLB JT3_zV").get(0).getElementsByClass("_0xLoFW u9KIT8 _7ckuOK").get(0);
            String formatSalePrice = null;
            String formatOrgPrice = null;
            if(priceParent != null) {
                if(priceParent.childNodeSize() == 1) {
                    Element rawElementOrigPrice = article.getElementsByClass("_0xLoFW _78xIQ- EJ4MLB JT3_zV").get(0).getElementsByClass("_0xLoFW u9KIT8 _7ckuOK").get(0).child(0);
                    if(rawElementOrigPrice != null) {
                        formatOrgPrice = Formatter.deleteNonDigits(rawElementOrigPrice.text());
                    }
                    item.setItemPriceEuro(Double.valueOf(formatOrgPrice));
    
                } else if(priceParent.childNodeSize() == 2) {
                    Element rawElementSalePrice = article.getElementsByClass("_0xLoFW _78xIQ- EJ4MLB JT3_zV").get(0).getElementsByClass("_0xLoFW u9KIT8 _7ckuOK").get(0).child(0);
                    Element rawElementOrigPrice = article.getElementsByClass("_0xLoFW _78xIQ- EJ4MLB JT3_zV").get(0).getElementsByClass("_0xLoFW u9KIT8 _7ckuOK").get(0).child(1);
                    if(rawElementSalePrice != null) {
                        formatSalePrice = Formatter.deleteNonDigits(rawElementSalePrice.text());
                        item.setItemSale(true);
                    }
                    
                    if(rawElementOrigPrice != null) {
                        formatOrgPrice = Formatter.deleteNonDigits(rawElementOrigPrice.text());
                    }
                    
                    item.setItemSalePriceEuro(Double.valueOf(formatSalePrice));
                    item.setItemPriceEuro(Double.valueOf(formatOrgPrice));
                } else {
                    LOGGER.error("Error price is not found");
                }
            }
        }
        
        //System.out.println(article.getElementsByClass("_0xLoFW _78xIQ- EJ4MLB JT3_zV").get(0).getElementsByClass("_0xLoFW u9KIT8 _7ckuOK").get(0));
        
        //String formatOrigPrice = Formatter.deleteNonDigits(elementOrigPrice.text());
        
        //System.out.println("sale price: "+elementSalePrice.text());
        //System.out.println("orig price: "+elementOrigPrice.text());
    }
    
    public static void extractDetailImages(Document doc, MassItem item) throws IOException {
        Objects.requireNonNull(doc);

        Elements elementsImg = doc.select("img");
        List<String> formattedImageList = new ArrayList<>();
        
        String brandName = item.getBrandNameDE();
        //System.out.println(elements.get(0).getElementsByAttributeValue("property", "og:title"));
        //System.out.println(elementsImg);
        for(int i=0; i<elementsImg.size(); i++) {
          Element ele = elementsImg.get(i);
          if (ele.hasAttr("alt")) {
              if (ele.attr("alt").contains(brandName)) {
                  //System.out.println(ele.attr("src"));
                  String detailImageUrl = getFormattedJpgUrl(ele.attr("src"));
                  if(!formattedImageList.contains(detailImageUrl)) {
                      formattedImageList.add(detailImageUrl);
                  }
              }
          }
        }
        
        item.setItemDetailImages(formattedImageList);
        
        //String mainImageUrl = elements.get(0).attr("href");
        //String itemFullName = itemBrand + " " + itemTitle;
        //item.setMainImageName(itemTitle);
        //savingMainImage(itemTitle, DIR_MAIN_IMAGES, mainImageUrl);
        //Element bodyMat = doc.body();
        //Elements material = bodyMat.childNodeSize();
                //getElementsByClass("re-data-el-init");
        //Element elementMat = material.get(0).child(0);//.child(1);
        
        //System.out.println(bodyMat.getElementsByClass("rQP559 C3wGFf JT3_zV").get(2).getElementsByClass("_1z5_Qg").get(2).getElementsByClass("OXMl2N xVaPPo f2qidi _56Chwa"));
        //System.out.println(bodyMat.getElementsByClass("OXMl2N xVaPPo f2qidi _56Chwa"));
        //System.out.println(bodyMat.getElementsByClass("_1z5_Qg"));
    }
}
