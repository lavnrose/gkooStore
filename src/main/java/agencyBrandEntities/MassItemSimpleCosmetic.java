package agencyBrandEntities;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyEntities.BasePreItemCosmetic;
import agencyEntities.MassItem;
import translator.TranslateGlossary;
import util.GrobalDefined;

public class MassItemSimpleCosmetic extends BasePreItemCosmetic {
    private static final Logger LOGGER = LogManager.getLogger(MassItemBirken.class);
    private int priceWon;
    
    private MassItem massItem;
    
    public MassItemSimpleCosmetic(MassItem massItem) {
        this.priceWon = super.calculatePriceCommisionSectionWon(massItem.getItemPriceEuro());
        massItem.setItemPriceWon(priceWon);
        if(GrobalDefined.TRANSLATE) {
            invokeTranslateItemTitleKor(massItem);
        }
    }
    
    private void invokeTranslateItemTitleKor(MassItem massItem) {
        String itemTitleDe = massItem.getItemTitleDE();
        if (itemTitleDe != null) {
            String translatedItemTiteKor = "";
            try {
                translatedItemTiteKor = TranslateGlossary.translateTextWithGlossary(itemTitleDe);
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

    public MassItem getMassItem() {
        return massItem;
    }

    public void setMassItem(MassItem massItem) {
        this.massItem = massItem;
    }
}
