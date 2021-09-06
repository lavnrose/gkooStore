package agencyEntities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import gkooAgentConfig.FlaconiConfig;
import util.Formatter;
import util.GrobalDefined.Gender;

public class AgentFlaconi extends Agent implements ICosmeticSimpleAgent{
    public AgentFlaconi(String brandNameDe, String brandNameKor,
            String homepageUrl, String dirMainImages, List<String> itemSizeList,
            List<String> itemSizePriceList, List<String> itemSizeStockist, Gender gender, int deliveryFee) {
        super(brandNameDe, brandNameKor, homepageUrl, dirMainImages, itemSizeList,
                itemSizePriceList, itemSizeStockist, gender, deliveryFee);
    }
    
    public AgentFlaconi(FlaconiConfig config) {
        super(config);
    }

    @Override
    public Elements getUnitElements(Element body) {
        Elements unitElements = body.getElementsByClass("e-tastic e-tastic__flaconi-product-list ").get(0).getElementsByAttributeStarting("data-product-list-id");
        return unitElements;
    }

    @Override
    public void setDetailImage(MassItem massItem) {
        List<String> detailImageList = new ArrayList<>();
        detailImageList.add(massItem.getMainImageUrl());
        massItem.setItemDetailImages(detailImageList);
    }

    @Override
    public String extractUrl(Element unitElement) {
        String url = "https://www.flaconi.de" + unitElement.getElementsByTag("a").attr("href");
        return url;
    }

    @Override
    public String extractItemTitle(Element unitElement) {
        return unitElement.getElementsByTag("a").get(0).getElementsByTag("img").attr("alt");
    }

    @Override
    public void downloadingMainImage(MassItem massItem, Element unitElement) {
        String rawImageUrl = unitElement.getElementsByTag("a").get(0).getElementsByTag("img").attr("src");
        String mainImageUrl = Formatter.getFormattedJpgUrl(rawImageUrl);
        massItem.setMainImageUrl(mainImageUrl);
        savingMainImage(massItem.getMainImageName(), getDirMainImages(), mainImageUrl);
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

    @Override
    public void setItemPrice(MassItem massItem, Element unitElement) {
        String complexData = "";
        Elements elements = unitElement.getElementsByTag("span");
        for(Element ele : elements) {
            if(ele.className().contains("ProductPrice")) {
                complexData = ele.text();
            }
        }
        String[] rawData =  complexData.split("/");
        String price = Formatter.deleteNonDigits(rawData[0]);
        massItem.setItemPriceEuro(Double.parseDouble(price));
    }

    @Override
    public void setItemVolume(MassItem massItem, Element unitElement) {
        String complexData = "";
        Elements elements = unitElement.getElementsByTag("span");
        for(Element ele : elements) {
            if(ele.className().contains("ProductPrice")) {
                complexData = ele.text();
            }
        }
        String[] rawData =  complexData.split("/");
        String volume = Formatter.deleteNonDigits(rawData[1]);
        massItem.setItemVolume(volume);
        
    }

    @Override
    public void setItemIngredients(MassItem massItem, String validUrl) {
        Document doc = null;
        try {
            doc = Jsoup.connect(validUrl).userAgent("Chrome").get();
        } catch (IOException e) {
            return;
        }
        Element body = doc.body();
        int size = body.getElementsByClass("pdp-product-info-details").size();
        massItem.setItemIngredients(body.getElementsByClass("pdp-product-info-details").get(size-1).text());
    }
}
