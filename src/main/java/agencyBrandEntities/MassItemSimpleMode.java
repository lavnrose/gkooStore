package agencyBrandEntities;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyEntities.BaseItem;
import agencyEntities.MassItem;

public class MassItemSimpleMode extends BaseItem {
    private static final Logger LOGGER = LogManager.getLogger(MassItemSimpleMode.class);

    private int priceWon;
    private int priceSaleWon;
    private int priceSubstractWon;
    private MassItem massItem;
    private List<String> detailImageUrlList = new ArrayList<>();
    
    public MassItemSimpleMode(MassItem massItem) {
        this.massItem = massItem;
        //this.priceWon = super.calculatePriceCommisionWon(massItem.getItemPriceEuro());
        //this.priceSubstractWon = massItem.isItemSale() ? super.calculatePriceNoCommisionWon(massItem.getItemPriceEuro() - massItem.getItemSalePriceEuro()) : 0;
        this.priceWon = super.calculatePriceCommisionVATWon(massItem.getItemPriceEuro(), massItem.getModeDeiveryFee());
        if(massItem.isItemSale()) {
            this.priceSaleWon = super.calculatePriceCommisionVATWon(massItem.getItemSalePriceEuro(), massItem.getModeDeiveryFee());
            this.priceSubstractWon = priceWon - priceSaleWon;
        }
    }

    @Override
    public String getSizeListString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getSizeListPriceString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getSizeListStockString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getCategoryId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getItemFullnameDE() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getItemFullnameEN() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getItemUrl() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MassItem getMassItem() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPriceWonString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPriceWonCoupangString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPriceSaleWonString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPriceSubstractWonString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getColorListString() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getMainImageFileName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getSizeOptionCafe24() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDetailPageCafe24() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDetailPageSmart() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isItemSale() {
        // TODO Auto-generated method stub
        return false;
    }
}
