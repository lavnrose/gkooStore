package util_test;

import org.junit.Test;
import util.ImageDownloader;

public class ImageDownloaderTest {
    public static String DIR_BRAND = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/화장품/피트네";
    public final static String BRAND_NAME_KOR = "라베라";

    public static String DIR_BRAND_CATEGORY = DIR_BRAND + "/";
    public static String DIR_MAIN_IMAGES = DIR_BRAND_CATEGORY + "main_images/";
    public static String DIR_IMAGE = "";
    public static String DIR_IMAGE_URL = "";

    //@Test
    public void getRectangleImage() {
        //ImageDownloader.runWithResizing("cropTest", DIR_MAIN_IMAGES, "https://cdn-ec.niceshops.com/upload/image/product/large/default/sante-energy-duschgel-bio-zitrone-quitte-200-ml-1308631-de.jpg", 500, 500);
    }
    
    //@Test
    public void createRectangleImage() {
        ImageDownloader.runWithResizingFile("피트네 칼렌듈라 잘베크림", DIR_MAIN_IMAGES, 500, 500);
    }
    
    @Test
    public void downloadImage() {
        ImageDownloader.run("피트네 칼렌듈라 잘베크림", DIR_IMAGE, DIR_IMAGE_URL);
    }
}
