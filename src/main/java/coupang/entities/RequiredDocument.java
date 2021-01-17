package coupang.entities;

public class RequiredDocument {
    private String templateName;
    private String documentPath;
    private String vendorDocumentPath;
    
    public RequiredDocument() {
        this.templateName = "인보이스영수증(해외구매대행 선택시)";
        this.documentPath = "https://moondrive81.cafe24.com/GKoo/INVOICECOUPNAG.jpg";
    }
    
    public String getTemplateName() {
        return templateName;
    }
    
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    
    public String getDocumentPath() {
        return documentPath;
    }
    
    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
    
    public String getVendorDocumentPath() {
        return vendorDocumentPath;
    }
    
    public void setVendorDocumentPath(String vendorDocumentPath) {
        this.vendorDocumentPath = vendorDocumentPath;
    }
}
