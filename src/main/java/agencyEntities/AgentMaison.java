package agencyEntities;

import java.util.ArrayList;
import java.util.List;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import gkooAgentConfig.MaisonConfig;
import util.Formatter;
import util.GrobalDefined.Gender;

public class AgentMaison extends Agent implements IModeSimpleAgent {

    public AgentMaison(String brandNameDe, String brandNameKor,
            String homepageUrl, String dirMainImages, List<String> itemSizeList,
            List<String> itemSizePriceList, List<String> itemSizeStockist, Gender gender, int deliveryFee) {
        super(brandNameDe, brandNameKor, homepageUrl, dirMainImages, itemSizeList,
                itemSizePriceList, itemSizeStockist, gender, deliveryFee);
    }
    
    public AgentMaison(MaisonConfig config) {
        super(config);
    }

    @Override
    public Elements getUnitElements(Element body) {
        Elements rawUnitElements = body.getElementsByClass("products wrapper grid products-grid").get(0).getElementsByClass("item product product-item");
        Elements unitElements = new Elements();
        for (int i=0; i<rawUnitElements.size(); i++) {
            Element unit = rawUnitElements.get(i);
            unitElements.add(unit);
        }
        return unitElements;
    }

    @Override
    public String extractUrl(Element unitElement) {
        String url = unitElement.getElementsByClass("product-item-info ").get(0).getElementsByTag("a").get(0).attr("href");
        //System.out.println(url);
        return url;
    }

    @Override
    public String extractItemTitle(Element unitElement) {
        String itemTitle = unitElement.getElementsByClass("product details product-item-details").get(0)
                .getElementsByClass("product name product-item-name").text();
        //System.out.println(itemTitle);
        return itemTitle;
    }

    @Override
    public void downloadingMainImage(MassItem massItem, Element unitElement) {
        //String mainImageUrl = unitElement.getElementsByClass("product-item-info ").get(0).getElementsByTag("img").get(0).attr("data-src");
        String mainImageUrl = "";
        String mainImageCandidateFirst = unitElement.getElementsByClass("product-item-info ").get(0).getElementsByTag("img").get(0).attr("data-src");
        if(mainImageCandidateFirst == "") {
            mainImageUrl = unitElement.getElementsByClass("product-item-info ").get(0).getElementsByTag("img").get(0).attr("src");
        } else {
            mainImageUrl = mainImageCandidateFirst;
        }
        System.out.println(mainImageUrl);
        massItem.setMainImageUrl(mainImageUrl);
        savingMainImage(massItem.getMainImageName(), getDirMainImages(), mainImageUrl);
    }
    
    @Override
    public void setDetailImage(MassItem massItem) {
        List<String> detailImageList = new ArrayList<>();
        detailImageList.add(massItem.getMainImageUrl());
        massItem.setItemDetailImages(detailImageList);
    }
    
    @Override
    public void setItemPrice(MassItem massItem, Element unitElement) {
//        System.out.println(unitElement.getElementsByClass("product details product-item-details").get(0)
//                .getElementsByClass("price-box price-final_price").text());
        String formatOrgPrice = Formatter.deleteNonDigits(unitElement.getElementsByClass("product details product-item-details").get(0)
                .getElementsByClass("price-box price-final_price").text());
        massItem.setItemPriceEuro(Double.valueOf(formatOrgPrice));
    }

    @Override
    public void setCommonProperties(MassItem massItem) {
        massItem.setBrandNameDE(getBrandNameDe());
        massItem.setBrandNameKor(getBrandNameKor());
        massItem.setBrandHomepageUrl(getHomepageUrl());
        massItem.setItemSizes(getItemSizeList());
        massItem.setItemSizesPrice(getItemSizePriceList());
        massItem.setItemSizesStock(getItemSizeStockist());
        massItem.setGender(getGender());
        massItem.setModeDeiveryFee(getDeliveryFee());
    }
}
