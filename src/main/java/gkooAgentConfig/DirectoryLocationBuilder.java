package gkooAgentConfig;

import java.util.function.Supplier;
import util.GrobalDefined.Gender2;

public class DirectoryLocationBuilder {
    private String root_drectory;
    private String brandName;
    private String gender;
    private String category;
    private String htmlFileName;
    
    private final String SLASH = "/";
    
    public DirectoryLocationBuilder(String rootDir, String brandName, 
            Gender2 gender, String category, String htmlFileName) {
        this.root_drectory = rootDir;
        this.brandName = brandName;
        this.gender = gender.name().toLowerCase();
        this.category = category;
        this.htmlFileName = htmlFileName;
    }
    
    public Supplier<String> getBrandDirectory = () -> 
        root_drectory.concat(brandName).concat(SLASH);
        
    public Supplier<String> getBrandDirectoryCafe24 = () -> 
        brandName.concat(SLASH);
    
    public Supplier<String> getBrandGenderCategoryDirectory = 
            () -> getBrandDirectory.get().concat(gender).concat(SLASH).concat(category).concat(SLASH);
            
    public Supplier<String> getPreprocessFullPathXlsxFile = 
                    () -> getBrandDirectory.get().concat(gender).concat(SLASH).concat(category).concat(SLASH).concat(category + "_preprocess.xlsx");

    public Supplier<String> getBrandGenderCategoryDirectoryCafe24 = 
            () -> getBrandDirectoryCafe24.get().concat(gender).concat(SLASH).concat(category).concat(SLASH);
                    
    public Supplier<String> getHtmlFileLocation = () ->
        getBrandGenderCategoryDirectory.get().concat(htmlFileName);
        
    public Supplier<String> getMainImagesLocation = () ->
            getBrandGenderCategoryDirectory.get().concat("main_images").concat(SLASH);
            
    public Supplier<String> getMainImagesCafe24Location = () ->
        getBrandGenderCategoryDirectoryCafe24.get().concat("main_images").concat(SLASH);
    
    public Supplier<String> getHtmlFileName = () -> htmlFileName;
    
}
