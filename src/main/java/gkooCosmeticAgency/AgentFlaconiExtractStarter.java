package gkooCosmeticAgency;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import agencyBrandEntities.MassItemSimpleCosmetic;
import agencyEntities.AgentFlaconi;
import agencyEntities.BaseItem;
import agencyEntities.BaseItemCosmetic;
import agencyEntities.BasePreItemCosmetic;
import agencyEntities.MassItem;
import factoryExcel.XlsxFile;
import gkooAgentConfig.FlaconiConfig;


/**
 * 1.extract product image, product name in german, price, volume, material
 *   translate product name to korean
 * 
 * 2.edit in xlsx file (++ usage)
 * 
 * 3.converting edited xlsx file -> generates complete xlsx, csv files 
 *   for smartstore, cafe24, coupang
 * 
 * @author sanghuncho
 *
 */
public class AgentFlaconiExtractStarter {
    private static final Logger LOGGER = LogManager.getLogger(AgentFlaconiExtractStarter.class);

    public static void main(String[] args) throws Exception {
        
        LOGGER.info("A mission of agentFlaconi starts ===>>> ");
        FlaconiConfig config = new FlaconiConfig();
        
        AgentFlaconi agent = new AgentFlaconi(config);

        List<MassItem> massItemList = 
                agent.preprocessing(config.getDirectoryLocationBuilder().getHtmlFileLocation.get());
        
        List<BasePreItemCosmetic> baseItemCosmeticList = new ArrayList<>();
        for(int i=0; i<massItemList.size(); i++) {
            MassItemSimpleCosmetic massItem = new MassItemSimpleCosmetic(massItemList.get(i));
            baseItemCosmeticList.add(massItem);
        }
        
        XlsxFile xlsxFile = new XlsxFile();
        xlsxFile.createExcelFileCosmetic(baseItemCosmeticList, config.getCategory(), 
                config.getDirectoryLocationBuilder().getBrandDirectory.get());
        
        LOGGER.info("A mission end <<<=== ");
    }
}
