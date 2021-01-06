package coupang.entities;

import java.util.ArrayList;
import java.util.List;
import coupang.CoupangApi;

public class SellerProduct {
    private int displayCategoryCode;
    private String sellerProductName;
    private String vendorId;
    private String saleStartedAt;
    private String saleEndedAt;
    private String displayProductName;
    private String brand;
    private String generalProductName;
    private String productGroup;
    private final String deliveryMethod = "AGENT_BUY";
    private final String deliveryCompanyCode = "CJGLS";
    private String deliveryChargeType = "NOT_FREE";
    private int deliveryCharge = 8000;
    private int freeShipOverAmount;
    private int deliveryChargeOnReturn;
    private String remoteAreaDeliverable = "N";
    private String unionDeliveryType = "UNION_DELIVERY";
    private String returnCenterCode = "1000723147";
    private String returnChargeName = "지쿠사무실";
    private String companyContactNumber = "070-4001-8993";
    private String returnZipCode = "42454";
    private String returnAddress = "대구광역시 남구 양지로 16";
    private String returnAddressDetail = "조영호 내과 3층 (대명 4동)";
    private int returnCharge = 50000;
    private String returnChargeVendor;
    private String outboundShippingPlaceCode = "2916379";
    private String vendorUserId = CoupangApi.VENDOR_USER_ID;
    private boolean requested = true;
    private List<CoupangItem> items = new ArrayList<>();
    private List<RequiredDocument> requiredDocuments = new ArrayList<>();
    private String extraInfoMessage;
    private String manufacture;
    
    public SellerProduct() {}
    
    public int getDisplayCategoryCode() {
        return displayCategoryCode;
    }
    
    public void setDisplayCategoryCode(int displayCategoryCode) {
        this.displayCategoryCode = displayCategoryCode;
    }
    
    public String getSellerProductName() {
        return sellerProductName;
    }
    
    public void setSellerProductName(String sellerProductName) {
        this.sellerProductName = sellerProductName;
    }
    
    public String getVendorId() {
        return vendorId;
    }
    
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
    
    public String getSaleStartedAt() {
        return saleStartedAt;
    }
    
    public void setSaleStartedAt(String saleStartedAt) {
        this.saleStartedAt = saleStartedAt;
    }
    
    public String getSaleEndedAt() {
        return saleEndedAt;
    }
    
    public void setSaleEndedAt(String saleEndedAt) {
        this.saleEndedAt = saleEndedAt;
    }
    
    public String getDisplayProductName() {
        return displayProductName;
    }
    
    public void setDisplayProductName(String displayProductName) {
        this.displayProductName = displayProductName;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }
    
    public String getGeneralProductName() {
        return generalProductName;
    }
    
    public void setGeneralProductName(String generalProductName) {
        this.generalProductName = generalProductName;
    }
    
    public String getProductGroup() {
        return productGroup;
    }
    
    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }
    
    public String getDeliveryMethod() {
        return deliveryMethod;
    }
    
    public String getDeliveryCompanyCode() {
        return deliveryCompanyCode;
    }
    
    public String getDeliveryChargeType() {
        return deliveryChargeType;
    }
    
    public void setDeliveryChargeType(String deliveryChargeType) {
        this.deliveryChargeType = deliveryChargeType;
    }
    
    public int getDeliveryCharge() {
        return deliveryCharge;
    }
    
    public void setDeliveryCharge(int deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }
    
    public int getFreeShipOverAmount() {
        return freeShipOverAmount;
    }
    
    public void setFreeShipOverAmount(int freeShipOverAmount) {
        this.freeShipOverAmount = freeShipOverAmount;
    }
    
    public int getDeliveryChargeOnReturn() {
        return deliveryChargeOnReturn;
    }
    
    public void setDeliveryChargeOnReturn(int deliveryChargeOnReturn) {
        this.deliveryChargeOnReturn = deliveryChargeOnReturn;
    }
    
    public String getRemoteAreaDeliverable() {
        return remoteAreaDeliverable;
    }
    
    public void setRemoteAreaDeliverable(String remoteAreaDeliverable) {
        this.remoteAreaDeliverable = remoteAreaDeliverable;
    }
    
    public String getUnionDeliveryType() {
        return unionDeliveryType;
    }
    
    public void setUnionDeliveryType(String unionDeliveryType) {
        this.unionDeliveryType = unionDeliveryType;
    }
    
    public String getReturnCenterCode() {
        return returnCenterCode;
    }
    
    public void setReturnCenterCode(String returnCenterCode) {
        this.returnCenterCode = returnCenterCode;
    }
    
    public String getReturnChargeName() {
        return returnChargeName;
    }
    
    public void setReturnChargeName(String returnChargeName) {
        this.returnChargeName = returnChargeName;
    }
    
    public String getCompanyContactNumber() {
        return companyContactNumber;
    }
    
    public void setCompanyContactNumber(String companyContactNumber) {
        this.companyContactNumber = companyContactNumber;
    }
    
    public String getReturnZipCode() {
        return returnZipCode;
    }
    
    public void setReturnZipCode(String returnZipCode) {
        this.returnZipCode = returnZipCode;
    }
    
    public String getReturnAddress() {
        return returnAddress;
    }
    
    public void setReturnAddress(String returnAddress) {
        this.returnAddress = returnAddress;
    }
    
    public String getReturnAddressDetail() {
        return returnAddressDetail;
    }
    
    public void setReturnAddressDetail(String returnAddressDetail) {
        this.returnAddressDetail = returnAddressDetail;
    }
    
    public int getReturnCharge() {
        return returnCharge;
    }
    
    public void setReturnCharge(int returnCharge) {
        this.returnCharge = returnCharge;
    }
    
    public String getReturnChargeVendor() {
        return returnChargeVendor;
    }
    
    public void setReturnChargeVendor(String returnChargeVendor) {
        this.returnChargeVendor = returnChargeVendor;
    }
    
    public String getOutboundShippingPlaceCode() {
        return outboundShippingPlaceCode;
    }
    
    public void setOutboundShippingPlaceCode(String outboundShippingPlaceCode) {
        this.outboundShippingPlaceCode = outboundShippingPlaceCode;
    }
    
    public List<CoupangItem> getItems() {
        return items;
    }
    
    public void setItems(List<CoupangItem> items) {
        this.items = items;
    }
    
    public List<RequiredDocument> getRequiredDocuments() {
        return requiredDocuments;
    }
    
    public void setRequiredDocuments(List<RequiredDocument> requiredDocuments) {
        this.requiredDocuments = requiredDocuments;
    }
    
    public String getExtraInfoMessage() {
        return extraInfoMessage;
    }
    
    public void setExtraInfoMessage(String extraInfoMessage) {
        this.extraInfoMessage = extraInfoMessage;
    }
    
    public String getManufacture() {
        return manufacture;
    }
    
    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getVendorUserId() {
        return vendorUserId;
    }

    public boolean isRequested() {
        return requested;
    }

    public void setRequested(boolean requested) {
        this.requested = requested;
    }
}
