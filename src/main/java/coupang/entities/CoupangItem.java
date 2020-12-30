package coupang.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class CoupangItem {
    private String itemName;
    private int originalPrice;
    private int salePrice;
    private int maximumBuyCount;
    private int maximumBuyForPerson;
    private int maximumBuyForPersonPeriod;
    private int outboundShippingTimeDay;
    private int unitCount;
    private String adultOnly;
    private String taxType;
    private String parallelImported;
    private String overseasPurchased;
    private boolean pccNeeded;
    private String externalVendorSku;
    private String barcode;
    private boolean emptyBarcode;
    private String emptyBarcodeReason;
    private String modelNo;
    private Map<String, String> extraProperties = new HashMap<>();
    private List<Certification> certifications = new ArrayList<>();
    private List<String> searchTags = new ArrayList<>();
    private List<CoupangImage> images = new ArrayList<>();
    
    public CoupangItem() {
        
    }
    
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(int originalPrice) {
        this.originalPrice = originalPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public int getMaximumBuyCount() {
        return maximumBuyCount;
    }

    public void setMaximumBuyCount(int maximumBuyCount) {
        this.maximumBuyCount = maximumBuyCount;
    }

    public int getMaximumBuyForPerson() {
        return maximumBuyForPerson;
    }

    public void setMaximumBuyForPerson(int maximumBuyForPerson) {
        this.maximumBuyForPerson = maximumBuyForPerson;
    }

    public int getMaximumBuyForPersonPeriod() {
        return maximumBuyForPersonPeriod;
    }

    public void setMaximumBuyForPersonPeriod(int maximumBuyForPersonPeriod) {
        this.maximumBuyForPersonPeriod = maximumBuyForPersonPeriod;
    }

    public int getOutboundShippingTimeDay() {
        return outboundShippingTimeDay;
    }

    public void setOutboundShippingTimeDay(int outboundShippingTimeDay) {
        this.outboundShippingTimeDay = outboundShippingTimeDay;
    }

    public int getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(int unitCount) {
        this.unitCount = unitCount;
    }

    public String getAdultOnly() {
        return adultOnly;
    }

    public void setAdultOnly(String adultOnly) {
        this.adultOnly = adultOnly;
    }

    public String getTaxType() {
        return taxType;
    }

    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }

    public String getParallelImported() {
        return parallelImported;
    }

    public void setParallelImported(String parallelImported) {
        this.parallelImported = parallelImported;
    }

    public String getOverseasPurchased() {
        return overseasPurchased;
    }

    public void setOverseasPurchased(String overseasPurchased) {
        this.overseasPurchased = overseasPurchased;
    }

    public boolean isPccNeeded() {
        return pccNeeded;
    }

    public void setPccNeeded(boolean pccNeeded) {
        this.pccNeeded = pccNeeded;
    }

    public String getExternalVendorSku() {
        return externalVendorSku;
    }

    public void setExternalVendorSku(String externalVendorSku) {
        this.externalVendorSku = externalVendorSku;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public boolean isEmptyBarcode() {
        return emptyBarcode;
    }

    public void setEmptyBarcode(boolean emptyBarcode) {
        this.emptyBarcode = emptyBarcode;
    }

    public String getEmptyBarcodeReason() {
        return emptyBarcodeReason;
    }

    public void setEmptyBarcodeReason(String emptyBarcodeReason) {
        this.emptyBarcodeReason = emptyBarcodeReason;
    }

    public String getModelNo() {
        return modelNo;
    }

    public void setModelNo(String modelNo) {
        this.modelNo = modelNo;
    }

    public Map<String, String> getExtraProperties() {
        return extraProperties;
    }

    public void setExtraProperties(Map<String, String> extraProperties) {
        this.extraProperties = extraProperties;
    }

    public List<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<Certification> certifications) {
        this.certifications = certifications;
    }

    public List<String> getSearchTags() {
        return searchTags;
    }

    public void setSearchTags(List<String> searchTags) {
        this.searchTags = searchTags;
    }

    public List<CoupangImage> getImages() {
        return images;
    }

    public void setImages(List<CoupangImage> images) {
        this.images = images;
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Certification certification = new Certification();
        certification.setCertificationCode("test");
        certification.setCertificationType("test");
        String json = ow.writeValueAsString(certification);
        System.out.println(json);
    }
    
    private static class Certification {
        private String certificationType;
        private String certificationCode;
        
        public Certification() {
        }

        public String getCertificationType() {
            return certificationType;
        }

        public void setCertificationType(String certificationType) {
            this.certificationType = certificationType;
        }

        public String getCertificationCode() {
            return certificationCode;
        }

        public void setCertificationCode(String certificationCode) {
            this.certificationCode = certificationCode;
        }
    }
}
