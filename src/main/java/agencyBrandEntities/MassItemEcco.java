package agencyBrandEntities;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyEntities.BaseItemCosmetic;
import agencyEntities.MassItem;
import translator.TranslateApi;
import util.CosmeticUtil;
import util.Formatter;

public class MassItemEcco extends BaseItemCosmetic {
    private static final Logger LOGGER = LogManager.getLogger(MassItemBirken.class);

    private int priceWon;
    private int priceSaleWon;
    private int priceSubstractWon;
    private String itemDescriptionKor;
    private MassItem massItem;
    
    public MassItemEcco(MassItem massItem) {
        this.massItem = massItem;
        this.priceWon = super.calculatePriceCommisionVATWon(massItem.getItemPriceEuro(), massItem.getModeDeiveryFee());
        if(massItem.isItemSale()) {
            this.priceSaleWon = super.calculatePriceCommisionVATWon(massItem.getItemSalePriceEuro(), massItem.getModeDeiveryFee());
            this.priceSubstractWon = priceWon - priceSaleWon;
        }
        invokeTranslateDescription(massItem);
        invokeTranslateItemTitleKor(massItem);
    }
    
    private void invokeTranslateDescription(MassItem massItem) {
        String description = massItem.getItemDescription();
        if (description != null) {
            String translatedDescription = "";
            try {
                translatedDescription = TranslateApi.doTranslateDEtoKor(description);
            } catch (FileNotFoundException e) {
                LOGGER.error("TranslatedDescription has error:" + description, e);
            } catch (IOException e) {
                LOGGER.error("TranslatedDescription has error:" + description, e);
            }
            setItemDescriptionKor(translatedDescription);
        } else {
            setItemDescriptionKor("");
            LOGGER.error("Description is not found! No transation:" + massItem.getItemTitleDE());
        }
    }
    
    private void invokeTranslateItemTitleKor(MassItem massItem) {
        String itemTitleDe = massItem.getItemTitleDE();
        if (itemTitleDe != null) {
            String translatedItemTiteKor = "";
            try {
                translatedItemTiteKor = TranslateApi.doTranslateDEtoKor(itemTitleDe);
            } catch (FileNotFoundException e) {
                LOGGER.error("TranslatedDescription has error:" + itemTitleDe, e);
            } catch (IOException e) {
                LOGGER.error("TranslatedDescription has error:" + itemTitleDe, e);
            }
            massItem.setItemTitleKor(translatedItemTiteKor);
        } else {
            massItem.setItemTitleKor("");
            LOGGER.error("ItemTileDe is not found! No transation:" + massItem.getItemTitleDE());
        }
    }
    
    public void setItemDescriptionKor(String itemDescriptionKor) {
        this.itemDescriptionKor = itemDescriptionKor;
    }
    
    @Override
    public String getCategoryId() {
        return massItem.getCategoryId();
    }

    @Override
    public String getItemFullnameDE() {
        return massItem.getBrandNameDE() + " " + massItem.getItemTitleDE();
    }

    @Override
    public String getItemFullnameKor() {
        return massItem.getBrandNameKor() + " " + massItem.getItemTitleKor() + " " + massItem.getItemVolume();
    }

    @Override
    public MassItem getMassItem() {
        return massItem;
    }

    @Override
    public String getPriceWonString() {
        return String.valueOf(priceWon);
    }

    @Override
    public String getPriceSaleWonString() {
        return null;
    }

    @Override
    public String getMainImageFileName() {
        return massItem.getMainImageFileName();
    }

    @Override
    public String getItemFullDescriptionKOR() {
        StringBuilder result = new StringBuilder();
        result.append(getItemFullNameHtml(getItemFullnameKor()));
        result.append(getItemBrandOverview(CosmeticUtil.convertItemOverviewCosmetic(massItem.getDirFileUploader(), massItem.getBrandNameDE())));
        result.append(getEmptyLineHtml());
        result.append(getItemBrandNameHtml(massItem.getBrandNameDE()));
        result.append(getEmptyLineHtml());
        result.append(getItemDescriptionHtml(Formatter.setLinebreakAfterPunctHtml(getItemDescriptionKor())));
        result.append(getEmptyLineHtml());
        //result.append(getItemUsageHtml(Formatter.setLinebreakAfterPunctHtml(massItem.getItemUsage())));
        //https://www.ecco-verde.de/avril/organic-moisturizing-face-mask
        //result.append(getEmptyLineHtml());
        result.append(getItemIngredientHtml(massItem.getItemIngredients()));
        result.append(getEmptyLineHtml());
        result.append(getTranslateInfoHtml());
        return addTopBottomInfo(result.toString());
    }
    
    public String getItemDescriptionKor() {
        return itemDescriptionKor;
    }
    
    public String getItemDescriptionDE() {
        return massItem.getItemDescription();
    }

    @Override
    public String getItemFullnameWithPrefix() {
        return "[" + massItem.getBrandNameDE() + "] " + getItemFullnameKor();
    }

    @Override
    public String getItemFullDescriptionManual() {
        return null;
    }

    @Override
    public String getItemFullDescriptionDE() {
        StringBuilder result = new StringBuilder();
        result.append(getItemFullNameHtml(getItemFullnameKor()));
        result.append(getItemBrandOverview(CosmeticUtil.convertItemOverviewCosmetic(massItem.getDirFileUploader(), massItem.getBrandNameDE())));
        result.append(getEmptyLineHtml());
        result.append(getItemBrandNameHtml(massItem.getBrandNameDE()));
        result.append(getEmptyLineHtml());
        result.append(getItemDescriptionHtml(Formatter.setLinebreakAfterPunctHtml(getItemDescriptionDE())));
        result.append(getEmptyLineHtml());
        //result.append(getItemUsageHtml(Formatter.setLinebreakAfterPunctHtml(massItem.getItemUsage())));
        //result.append(getEmptyLineHtml());
        result.append(getItemIngredientHtml(massItem.getItemIngredients()));
        result.append(getEmptyLineHtml());
        result.append(getTranslateInfoHtml());
        return addTopBottomInfo(result.toString());
    }
}
