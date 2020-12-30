package coupang.entities;

import java.util.ArrayList;
import java.util.List;

public class CoupangImage {
    private int imageOrder;
    private String imageType;
    private String cdnPath;
    private String vendorPath;
    private List<Notice> notices = new ArrayList<>();
    private List<attribute> attributes = new ArrayList<>();
    private List<Content> contents = new ArrayList<>();
    private String offerCondition;
    private String offerDescription;
    
    public CoupangImage() { }

    public int getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(int imageOrder) {
        this.imageOrder = imageOrder;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getCdnPath() {
        return cdnPath;
    }

    public void setCdnPath(String cdnPath) {
        this.cdnPath = cdnPath;
    }

    public String getVendorPath() {
        return vendorPath;
    }

    public void setVendorPath(String vendorPath) {
        this.vendorPath = vendorPath;
    }

    public List<Notice> getNotices() {
        return notices;
    }

    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }

    public List<attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<attribute> attributes) {
        this.attributes = attributes;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
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
    
    private static class Notice {
        private String noticeCategoryName;
        private String noticeCategoryDetailName;
        private String content;
        
        public String getNoticeCategoryName() {
            return noticeCategoryName;
        }
        
        public void setNoticeCategoryName(String noticeCategoryName) {
            this.noticeCategoryName = noticeCategoryName;
        }
        
        public String getNoticeCategoryDetailName() {
            return noticeCategoryDetailName;
        }
        
        public void setNoticeCategoryDetailName(String noticeCategoryDetailName) {
            this.noticeCategoryDetailName = noticeCategoryDetailName;
        }
        
        public String getContent() {
            return content;
        }
        
        public void setContent(String content) {
            this.content = content;
        }
        
    }

    private static class attribute {
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
    
    private static class Content {
        private String contentsType;
        private String attributeValueName;
        private List<ContentDetail> contentDetails = new ArrayList<>();
        
        public String getContentsType() {
            return contentsType;
        }
        
        public void setContentsType(String contentsType) {
            this.contentsType = contentsType;
        }
        
        public String getAttributeValueName() {
            return attributeValueName;
        }
        
        public void setAttributeValueName(String attributeValueName) {
            this.attributeValueName = attributeValueName;
        }
        
        public List<ContentDetail> getContentDetails() {
            return contentDetails;
        }
        
        public void setContentDetails(List<ContentDetail> contentDetails) {
            this.contentDetails = contentDetails;
        }
    }
    
    private static class ContentDetail {
        private String content;
        private String detailType;
        
        public ContentDetail() {}

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