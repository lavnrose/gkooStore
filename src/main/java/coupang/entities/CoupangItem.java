package coupang.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import util.Formatter;

public class CoupangItem {
    private String itemName = "item";
    private int originalPrice;
    private int salePrice;
    private int maximumBuyCount = 10;
    private int maximumBuyForPerson = 0;
    private int maximumBuyForPersonPeriod = 1;
    private int outboundShippingTimeDay = 3;
    private int unitCount = 0;
    private String adultOnly = "EVERYONE";
    private String taxType = "TAX";
    private String parallelImported = "NOT_PARALLEL_IMPORTED";
    private String overseasPurchased = "OVERSEAS_PURCHASED";
    private boolean pccNeeded = true;
    private String externalVendorSku = "";
    private String barcode = "";
    private boolean emptyBarcode = true;
    private String emptyBarcodeReason = "";
    private String modelNo = "";
    private Map<String, String> extraProperties = new HashMap<>();
    private List<Certification> certifications = new ArrayList<>();
    private List<String> searchTags = new ArrayList<>();
    private List<CoupangImage> images = new ArrayList<>();
    private List<CoupangNotice> notices = new ArrayList<>();
    private final String NOTICE_CATEGORY_NAME = "화장품";
    private List<CoupangAttribute> attributes = new ArrayList<>();
    private List<CoupangContent> contents = new ArrayList<>();
    
    public CoupangItem(int originalPrice, int salePrice, String contentHtml, String sellerProductName) {
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        setExtraProperties();
        setCertifications();
        setNotices();
        setAttributes();
        setContents(contentHtml);
        setMainImage(sellerProductName);
    }
    
    private void setMainImage(String sellerProductName) {
        CoupangImage coupangImage = new CoupangImage();
        coupangImage.setImageOrder(0);
        coupangImage.setImageType("REPRESENTATION");
        coupangImage.setVendorPath(Formatter.convertMainImageUrl(sellerProductName));
        this.images.add(coupangImage);
    }
    
    private void setExtraProperties() {
    }
    
    private void setContents(String contentHtml) {
        CoupangContent coupangContent = new CoupangContent(contentHtml);
        contents.add(coupangContent);
    }
    
    private void setAttributes() {
        CoupangAttribute coupangAttribute = new CoupangAttribute();
        coupangAttribute.setAttributeTypeName("수량");
        coupangAttribute.setAttributeValueName("개");
        this.attributes.add(coupangAttribute);
    }
    
    private void setNotices() {
        CoupangNotice CoupangNotice1 = new CoupangNotice(NOTICE_CATEGORY_NAME, "용량(중량)", "100ml");
        //CoupangNotice CoupangNotice2 = new CoupangNotice(NOTICE_CATEGORY_NAME, "제품 주요 사양", "");
        CoupangNotice CoupangNotice3 = new CoupangNotice(NOTICE_CATEGORY_NAME, "사용기한 또는 개봉 후 사용기간", "상품상세페이지참조");
        CoupangNotice CoupangNotice4 = new CoupangNotice(NOTICE_CATEGORY_NAME, "사용방법", "상품상세페이지참조");
        CoupangNotice CoupangNotice5 = new CoupangNotice(NOTICE_CATEGORY_NAME, "화장품제조업자 및 화장품책임판매업자", "상품상세페이지참조");
        CoupangNotice CoupangNotice6 = new CoupangNotice(NOTICE_CATEGORY_NAME, "제조국", "상품상세페이지참조");
        CoupangNotice CoupangNotice7 = new CoupangNotice(NOTICE_CATEGORY_NAME, "화장품법에 따라 기재, 표시하여야 하는 모든 성분", "상품상세페이지참조");
        CoupangNotice CoupangNotice8 = new CoupangNotice(NOTICE_CATEGORY_NAME, "식품의약품안전처 심사 필 유무", "상품상세페이지참조");
        CoupangNotice CoupangNotice9 = new CoupangNotice(NOTICE_CATEGORY_NAME, "사용할 때 주의사항", "상품상세페이지참조");
        CoupangNotice CoupangNotice10 = new CoupangNotice(NOTICE_CATEGORY_NAME, "품질보증기준", "상품상세페이지참조");
        CoupangNotice CoupangNotice11 = new CoupangNotice(NOTICE_CATEGORY_NAME, "소비자상담관련 전화번호", "070-4001-8993");
        notices.add(CoupangNotice1);
        //notices.add(CoupangNotice2);
        notices.add(CoupangNotice3);
        notices.add(CoupangNotice4);
        notices.add(CoupangNotice5);
        notices.add(CoupangNotice6);
        notices.add(CoupangNotice7);
        notices.add(CoupangNotice8);
        notices.add(CoupangNotice9);
        notices.add(CoupangNotice10);
        notices.add(CoupangNotice11);
    }
    
    private void setCertifications() {
        Certification certification = new Certification();
        certification.setCertificationType("NOT_REQUIRED");
        certification.setCertificationCode("");
        certifications.add(certification);
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
    
    public List<CoupangNotice> getNotices() {
        return notices;
    }

    public void setNotices(List<CoupangNotice> notices) {
        this.notices = notices;
    }

    public List<CoupangAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<CoupangAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<CoupangContent> getContents() {
        return contents;
    }

    public void setContents(List<CoupangContent> contents) {
        this.contents = contents;
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
    
    private static class CoupangAttribute {
        private String attributeTypeName;
        private String attributeValueName;
        private String exposed;
        
        public String getAttributeTypeName() {
            return attributeTypeName;
        }
        public void setAttributeTypeName(String attributeTypeName) {
            this.attributeTypeName = attributeTypeName;
        }
        public String getAttributeValueName() {
            return attributeValueName;
        }
        public void setAttributeValueName(String attributeValueName) {
            this.attributeValueName = attributeValueName;
        }
        public String getExposed() {
            return exposed;
        }
        public void setExposed(String exposed) {
            this.exposed = exposed;
        }
    }
    
    private static class CoupangContent {
        private String contentsType = "TEXT";
        private List<ContentDetail> contentDetails = new ArrayList<>();
        private String offerCondition = "NEW";
        private String offerDescription;
        
        public CoupangContent(String contentHtml) {
            ContentDetail contentDetail = new ContentDetail(contentHtml);
            contentDetails.add(contentDetail);
        }
        
        public String getContentsType() {
            return contentsType;
        }
        
        public void setContentsType(String contentsType) {
            this.contentsType = contentsType;
        }
        
        public List<ContentDetail> getContentDetails() {
            return contentDetails;
        }
        
        public void setContentDetails(List<ContentDetail> contentDetails) {
            this.contentDetails = contentDetails;
        }
        
        public String getOfferCondition() {
            return offerCondition;
        }
        
        public void setOfferCondition(String offerCondition) {
            this.offerCondition = offerCondition;
        }
        
        public String getOfferDescription() {
            return offerDescription;
        }
        
        public void setOfferDescription(String offerDescription) {
            this.offerDescription = offerDescription;
        }
    }
    
    private static class ContentDetail {
        private String content;
        private String detailType = "TEXT";
        
        public ContentDetail(String contentHtml) {
            this.content = contentHtml;
        }
        
        public String getContent() {
            return content;
        }
        
        public void setContent(String content) {
            this.content = content;
        }
        
        public String getDetailType() {
            return detailType;
        }
        
        public void setDetailType(String detailType) {
            this.detailType = detailType;
        }
    }
}