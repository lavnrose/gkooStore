package gkooModeAgency;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyBrandEntities.MassItemMode;
import agencyEntities.AgentMaison;
import agencyEntities.BaseItem;
import agencyEntities.MassItem;
import factoryExcel.Cafe24;
import factoryExcel.SmartStore;
import gkooAgentConfig.MaisonConfig;

public class AgentMaisonStarter {
    private static final Logger LOGGER = LogManager.getLogger(AgentMaisonStarter.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info("A mission of agentMaison starts ===>>> ");
        MaisonConfig config = new MaisonConfig();
        
        AgentMaison agent = new AgentMaison(config);
        /**
         * save manually html in local and gathering urls, item titles, downloading main images
         */
        List<MassItem> massItemList = 
                agent.preprocessing(config.getDirectoryLocationBuilder().getHtmlFileLocation.get());
        
        List<BaseItem> baseItemList = new ArrayList<>();
        for(int i=0; i<massItemList.size(); i++) {
        //for(int i=0; i<1; i++) {
            //MassItemMode massItemLando = new MassItemMode(massItem);
            MassItemMode massItem = new MassItemMode(massItemList.get(i));
            baseItemList.add(massItem);
        }
        
        Cafe24 cafe24 = new Cafe24(baseItemList, config.getBrandNameKor(), config.getCategoryIdCafe24(), config.getMainImagesCafe24Location());
        cafe24.createCsvFileMode(config.getDirectoryLocationBuilder().getBrandGenderCategoryDirectory.get());
        
        SmartStore smartStore = 
                new SmartStore(baseItemList, config.getCategoryIdSmartStore(), config.getBrandNameCategory.get());
        smartStore.createExcelMode(config.getDirectoryLocationBuilder().getBrandGenderCategoryDirectory.get());
        
//        CoupangApi CoupangApi = new CoupangApi();
//        CoupangApi.createProducts(baseItemList, GrobalDefined.categoryCodeCoopang.get(COUPANG_CATEGORY_CODE), DIR_FILEUPLOADER, ITEM_SIZE_EU_MEN_LIST_COUPANG);
        LOGGER.info("A mission end <<<=== ");
    }
}
