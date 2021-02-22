package util_test;

import org.junit.Test;
import util.ImageTransformer;

public class ImageTransformerTest {
    public static String DIR_MAIN_IMAGES = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/화장품/기타상품/";
    
    @Test
    public void createRectangleImage() {
        ImageTransformer.runWithResizingFile("herbacin kamille hautstraffende Body Lotion", DIR_MAIN_IMAGES, 500, 500);
    }
}
