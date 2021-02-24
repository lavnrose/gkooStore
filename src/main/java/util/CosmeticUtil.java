package util;

public class CosmeticUtil {
    private static final String FILE_UPLOADER_COSMETIC_URL = "https://moondrive81.cafe24.com/GKoo/cosmetic/";
    
    public static String convertMainImageUrlCosmetic(String dirName, String mainaImageName) {
        return FILE_UPLOADER_COSMETIC_URL + dirName + "main_images/" +  mainaImageName + ".jpg";
    }
    
    public static String convertItemOverviewCosmetic(String dirName, String brandName) {
        return FILE_UPLOADER_COSMETIC_URL + dirName  +  brandName + "_overview.jpg";
    }
}
