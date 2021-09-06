package gkooCosmeticAgency;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyEntities.BaseItemSimpleCosmetic;
import agencyEntities.MassItem;
import factoryExcel.Cafe24;
import factoryExcel.XlsxFile;
import gkooAgentConfig.FlaconiConfig;

public class AgentFlaconiStarter {
    private static final Logger LOGGER = LogManager.getLogger(AgentFlaconiStarter.class);

    public static void main(String[] args) throws Exception {
        
        LOGGER.info("A mission of agentFlaconi starts ===>>> ");
        FlaconiConfig config = new FlaconiConfig();
        
        List<MassItem> massItemList = new ArrayList<>();
        XlsxFile xlsxFile = new XlsxFile();
        xlsxFile.xlsxToObject(
                config.getDirectoryLocationBuilder().getPreprocessFullPathXlsxFile.get(), massItemList);
        
        List<BaseItemSimpleCosmetic> baseItemSimpleCosmetic = new ArrayList<>();
        for(int i=0; i<massItemList.size(); i++) {
            BaseItemSimpleCosmetic massItemEcco = new BaseItemSimpleCosmetic(massItemList.get(i), config);
            baseItemSimpleCosmetic.add(massItemEcco);
        }
        
        Cafe24 cafe24 = new Cafe24(config.getBrandNameKor(), config.getCategoryIdCafe24(), config.getMainImagesCafe24Location(), baseItemSimpleCosmetic);
        cafe24.createCsvFileSimpleCosmetic(config.getDirectoryLocationBuilder().getBrandGenderCategoryDirectory.get());
        
//        SmartStore smartStore = 
//                new SmartStore(baseItemList, config.getCategoryIdSmartStore(), config.getBrandNameCategory.get());
//        smartStore.createExcelMode(config.getDirectoryLocationBuilder().getBrandGenderCategoryDirectory.get());
        
//        CoupangApi CoupangApi = new CoupangApi();
//        CoupangApi.createProducts(baseItemList, GrobalDefined.categoryCodeCoopang.get(COUPANG_CATEGORY_CODE), DIR_FILEUPLOADER, ITEM_SIZE_EU_MEN_LIST_COUPANG);
        LOGGER.info("A mission end <<<=== ");
    }
}
