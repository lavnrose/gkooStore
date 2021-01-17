package coupang.entities;

import java.util.HashMap;

public class CategoryPredict {
    private String productName;
    private String productDescription = "";
    private String brand = "";
    //private String[] attributes;
    
    private HashMap<String, String> attributes = new HashMap<>();
    private String sellerSkuCode = "";
    
    public CategoryPredict(String productName) {
        this.productName = productName;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public String getProductDescription() {
        return productDescription;
    }
    
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
//    public String[] getAttributes() {
//        return attributes;
//    }
//    
//    public void setAttributes(String[] attributes) {
//        this.attributes = attributes;
//    }
    
    public String getSellerSkuCode() {
        return sellerSkuCode;
    }
    
    public void setSellerSkuCode(String sellerSkuCode) {
        this.sellerSkuCode = sellerSkuCode;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, String> attributes) {
        this.attributes = attributes;
    }
}