package coupang;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import com.coupang.openapi.sdk.Hmac;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import agencyEntities.BaseItem;
import coupang.entities.CategoryPredict;
import coupang.entities.CoupangItem;
import coupang.entities.CoupangItemShoes;
import coupang.entities.SellerProduct;
import coupang.entities.SellerProductShoes;
import gkooModeAgency.AgentBirkenStarter;
import util.GrobalDefined;

public class CoupangApi {
    private static final Logger LOGGER = LogManager.getLogger(CoupangApi.class);

    private static final String HOST = "api-gateway.coupang.com";
    private static final int PORT = 443;
    private static final String SCHEMA = "https";
    private static String ACCESS_KEY;
    private static String SECRET_KEY;
    public static String VENDOR;
    public static String VENDOR_USER_ID;
    
    private static final String userDir = System.getProperty("user.dir");
    private static final String configPath = userDir + "/src/main/resources/config.properties";

    public static void main(String[] args) throws FileNotFoundException, IOException, CsvValidationException {
        String fileName = "C:/Users/sanghuncho/Documents/GKoo_Store_Project/cosmetic/ecoverde/weleda/벨레다_cafe24.csv";
        List<List<String>> records = new ArrayList<List<String>>();
        try (CSVReader csvReader = new CSVReader(new FileReader(fileName));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        }
        
//        for(List<String> cosmetic : records) {
//            
//        }
        
        for(int i=1; i< 2; i++) {
            List<String> cosmetic = records.get(i);
            int originalPrice = Integer.valueOf(cosmetic.get(22));
            int salePrice = Integer.valueOf("0");
            String contentHtml = cosmetic.get(14);
            String mainImageName = cosmetic.get(10);
            String displayProductName = cosmetic.get(7);
            String brand = "weleda";
//            CoupangApi.createProductCosmetic(GrobalDefined.categoryCodeCoopang.get(""), 
//                    originalPrice, salePrice, contentHtml, mainImageName, displayProductName, brand, DIR_FILEUPLOADER);
        }
        
        //initCoupangApi();
        
        //CoupangApi coupangApi = new CoupangApi();
        //coupangApi.getDeliveryStartPlace();
        //coupangApi.getReturnPlace();
        //coupangApi.getCategoryMetas("56213");
        //coupangApi.createProduct();
        //coupangApi.predictCategory(getCategoryPredictJson("산테 밸런스 바디로션 150ml"));
    }
    
    public void createProducts(List<BaseItem> baseItemList, int categoryCode, String dirFileUploader, String itemSizeList) {
        for(BaseItem baseItem : baseItemList) {
            int originalPrice = Integer.valueOf(baseItem.getPriceWonCoupangString());
            int salePrice = Integer.valueOf(baseItem.getPriceWonCoupangString());
            String contentHtml = baseItem.getDetailPageSmart();
            String mainImageName = baseItem.getMainImageFileName();
            String displayProductName = "[" + baseItem.getMassItem().getBrandNameKor() + " " + AgentBirkenStarter.ITEM_CATEGORY + "] " + baseItem.getMassItem().getItemTitleDE();
            String brand = baseItem.getMassItem().getBrandNameDE();
            String color = baseItem.getMassItem().getItemColors().get(0);
            readyCreatProduct(categoryCode, originalPrice, salePrice, 
                    contentHtml, mainImageName, displayProductName, brand, dirFileUploader, itemSizeList, color);
            LOGGER.info("product is create by coupang api:" + displayProductName);
        }
    }
    
    private void readyCreatProduct(int categoryCode, int originalPrice, int salePrice, 
            String contentHtml, String sellerProductName, String displayProductName, 
            String brand, String dirFileUploader, String itemSizeList, String color) {
        initCoupangApi();
        int categoryCodeCoupang;
        CoupangItemShoes coupangItem = new CoupangItemShoes(originalPrice, salePrice, contentHtml, sellerProductName, dirFileUploader, itemSizeList, color);
        if(categoryCode == 0) {
            String displayCategoryCodeStr = predictCategory(getCategoryPredictJson(displayProductName));
            categoryCodeCoupang = Integer.valueOf(displayCategoryCodeStr);
        } else {
            categoryCodeCoupang = categoryCode;
        }
        
        SellerProductShoes sellerProduct = new SellerProductShoes(categoryCodeCoupang, sellerProductName, displayProductName, brand, displayProductName, coupangItem);
        String sellerProductJjsonStr = getSellerProductShoesJson(sellerProduct);
        createProduct(sellerProductJjsonStr);
    }
    
    public static void createProductCosmetic(int categoryCode, int originalPrice, int salePrice, String contentHtml, String sellerProductName, String displayProductName, String brand, String dirFileUploader) {
        initCoupangApi();
        CoupangApi coupangApi = new CoupangApi();
        int categoryCodeCoupang;
        CoupangItem coupangItem = new CoupangItem(originalPrice, salePrice, contentHtml, sellerProductName, dirFileUploader);
        if(categoryCode == 0) {
            String displayCategoryCodeStr = coupangApi.predictCategory(getCategoryPredictJson(displayProductName));
            categoryCodeCoupang = Integer.valueOf(displayCategoryCodeStr);
        } else {
            categoryCodeCoupang = categoryCode;
        }
        SellerProduct sellerProduct = new SellerProduct(categoryCodeCoupang, sellerProductName, displayProductName, brand, displayProductName, coupangItem);
        String sellerProductJjsonStr = getSellerProductJson(sellerProduct);
        coupangApi.createProduct(sellerProductJjsonStr);
    }
    
    private static String getCategoryPredictJson(String productName) {
        ObjectMapper objectMapper = new ObjectMapper();
        String categoryPredictAsString = "";
        try {
            categoryPredictAsString = objectMapper.writeValueAsString(new CategoryPredict(productName));
        } catch (JsonProcessingException e) {
            
        }
        return categoryPredictAsString;
    }
    
    private static String getSellerProductJson(SellerProduct sellerProduct) {
        ObjectMapper objectMapper = new ObjectMapper();
        String sellerProductAsString = "";
        try {
            sellerProductAsString = objectMapper.writeValueAsString(sellerProduct);
        } catch (JsonProcessingException e) {
            
        }
        return sellerProductAsString;
    }
    
    private static String getSellerProductShoesJson(SellerProductShoes sellerProduct) {
        ObjectMapper objectMapper = new ObjectMapper();
        String sellerProductAsString = "";
        try {
            sellerProductAsString = objectMapper.writeValueAsString(sellerProduct);
        } catch (JsonProcessingException e) {
            
        }
        return sellerProductAsString;
    }
    
    public String predictCategory(String predictJson) {
        //product registration json data
        String strjson = predictJson;
        String result = "";
        //params
        String method = "POST";
        String path = "/v2/providers/openapi/apis/api/v1/categorization/predict";
        
        CloseableHttpClient client = null;
        try {
            //create client
            client = HttpClients.createDefault();
            //build uri
            URIBuilder uriBuilder = new URIBuilder()
                    .setPath(path);

            /********************************************************/
            //authorize, demonstrate how to generate hmac signature here
            String authorization = Hmac.generate(method, uriBuilder.build().toString(), SECRET_KEY, ACCESS_KEY);
            //print out the hmac key
            System.out.println(authorization);
            /********************************************************/

            uriBuilder.setScheme(SCHEMA).setHost(HOST).setPort(PORT);
            HttpPost requestPost = new HttpPost(uriBuilder.build().toString());

            StringEntity params = new StringEntity(strjson,"UTF-8");
            
            /********************************************************/
            // set header, demonstarte how to use hmac signature here
            requestPost.addHeader("Authorization", authorization);
            /********************************************************/
            requestPost.addHeader("content-type", "application/json");
            requestPost.setEntity(params);
            CloseableHttpResponse response = null;
            try {
                //execute post request
                response = client.execute(requestPost);
                //print result
                System.out.println("status code:" + response.getStatusLine().getStatusCode());
                System.out.println("status message:" + response.getStatusLine().getReasonPhrase());
                HttpEntity entity = response.getEntity();
                
                if (entity != null) {
                    String retSrc = EntityUtils.toString(entity); 
                    // parsing JSON
                    JSONObject jsobObject = new JSONObject(retSrc); //Convert String to JSON Object
                    Object obj = jsobObject.get("data");
                    JSONObject jobj = (JSONObject) obj;
                    result = jobj.get("predictedCategoryId").toString();
                    //JSONArray tokenList = jsobObject.getJSONArray("data");
                    //result = tokenList.getString("predictedCategoryId"); 
                 }
                
                //System.out.println("result:" + EntityUtils.toString(entity));
//                ObjectMapper objectMapper = new ObjectMapper();
//                CategoryPredictResponse car = objectMapper.readValue(EntityUtils.toString(entity), CategoryPredictResponse.class);
//                result = car.getData().get("predictedCategoryId");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    
    public void createProduct(String productJson) {
        //product registration json data
        String strjson = productJson;

        //params
        String method = "POST";
        String path = "/v2/providers/seller_api/apis/api/v1/marketplace/seller-products";
        
        CloseableHttpClient client = null;
        try {
            //create client
            client = HttpClients.createDefault();
            //build uri
            URIBuilder uriBuilder = new URIBuilder()
                    .setPath(path);

            /********************************************************/
            //authorize, demonstrate how to generate hmac signature here
            String authorization = Hmac.generate(method, uriBuilder.build().toString(), SECRET_KEY, ACCESS_KEY);
            //print out the hmac key
            //System.out.println(authorization);
            /********************************************************/

            uriBuilder.setScheme(SCHEMA).setHost(HOST).setPort(PORT);
            HttpPost requestPost = new HttpPost(uriBuilder.build().toString());

            StringEntity params =new StringEntity(strjson,"UTF-8");
            
            /********************************************************/
            // set header, demonstarte how to use hmac signature here
            requestPost.addHeader("Authorization", authorization);
            /********************************************************/
            requestPost.addHeader("content-type", "application/json");
            requestPost.setEntity(params);
            CloseableHttpResponse response = null;
            try {
                //execute post request
                response = client.execute(requestPost);
                //print result
                System.out.println("status code:" + response.getStatusLine().getStatusCode());
                System.out.println("status message:" + response.getStatusLine().getReasonPhrase());
                HttpEntity entity = response.getEntity();
                System.out.println("result:" + EntityUtils.toString(entity));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private static void initCoupangApi() {
        try (InputStream input = new FileInputStream(configPath)) {

            Properties prop = new Properties();

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
            //System.out.println(prop.getProperty("coupang.accessKey"));
            ACCESS_KEY = prop.getProperty("coupang.accessKey");
            SECRET_KEY = prop.getProperty("coupang.secretKey");
            VENDOR = prop.getProperty("coupang.vendor");
            VENDOR_USER_ID = prop.getProperty("coupang.vendorUserId");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void getCategoryMetas(String categoryId) {
        //params
        String method = "GET";
        String path = "/v2/providers/seller_api/apis/api/v1/marketplace/meta/category-related-metas/display-category-codes/" + categoryId;

        CloseableHttpClient client = null;
        try {
            //create client
            client = HttpClients.createDefault();
            //build uri
            URIBuilder uriBuilder = new URIBuilder()
                    .setPath(path);

            /********************************************************/
            //authorize, demonstrate how to generate hmac signature here
            String authorization = Hmac.generate(method, uriBuilder.build().toString(), SECRET_KEY, ACCESS_KEY);
            //print out the hmac key
            //System.out.println(authorization);
            /********************************************************/

            uriBuilder.setScheme(SCHEMA).setHost(HOST).setPort(PORT);
            uriBuilder.setScheme(SCHEMA).setHost(HOST);
            HttpGet get = new HttpGet(uriBuilder.build().toString());
            /********************************************************/
            // set header, demonstarte how to use hmac signature here
            get.addHeader("Authorization", authorization);
            /********************************************************/
            get.addHeader("content-type", "application/json");
            CloseableHttpResponse response = null;
            try {
                //execute get request
                response = client.execute(get);
                //print result
                System.out.println("status code:" + response.getStatusLine().getStatusCode());
                System.out.println("status message:" + response.getStatusLine().getReasonPhrase());
                HttpEntity entity = response.getEntity();
                System.out.println("result:" + EntityUtils.toString(entity));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void getDeliveryStartPlace() {
        //params
        String method = "GET";
        String path = "/v2/providers/marketplace_openapi/apis/api/v1/vendor/shipping-place/outbound";

        CloseableHttpClient client = null;
        try {
            //create client
            client = HttpClients.createDefault();
            //build uri
            URIBuilder uriBuilder = new URIBuilder()
                    .setPath(path)
                    .addParameter("pageSize", "1")     
                    .addParameter("pageNum", "1") ;

            /********************************************************/
            //authorize, demonstrate how to generate hmac signature here
            String authorization = Hmac.generate(method, uriBuilder.build().toString(), SECRET_KEY, ACCESS_KEY);
            //print out the hmac key
            //System.out.println(authorization);
            /********************************************************/

            //uriBuilder.setScheme(SCHEMA).setHost(HOST).setPort(PORT);
            uriBuilder.setScheme(SCHEMA).setHost(HOST);
            HttpGet get = new HttpGet(uriBuilder.build().toString());
            /********************************************************/
            // set header, demonstarte how to use hmac signature here
            get.addHeader("Authorization", authorization);
            /********************************************************/
            get.addHeader("content-type", "application/json");
            CloseableHttpResponse response = null;
            try {
                //execute get request
                response = client.execute(get);
                //print result
                System.out.println("status code:" + response.getStatusLine().getStatusCode());
                System.out.println("status message:" + response.getStatusLine().getReasonPhrase());
                HttpEntity entity = response.getEntity();
                System.out.println("result:" + EntityUtils.toString(entity));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void getReturnPlace() {
        //params
        String method = "GET";
        String path = "/v2/providers/openapi/apis/api/v4/vendors/A00334178/returnShippingCenters";

        CloseableHttpClient client = null;
        try {
            //create client
            client = HttpClients.createDefault();
            //build uri
            URIBuilder uriBuilder = new URIBuilder()
                    .setPath(path)
                    .addParameter("pageSize", "10")     
                    .addParameter("pageNum", "1") ;

            /********************************************************/
            //authorize, demonstrate how to generate hmac signature here
            String authorization = Hmac.generate(method, uriBuilder.build().toString(), SECRET_KEY, ACCESS_KEY);
            //print out the hmac key
                //System.out.println(authorization);
            /********************************************************/

            //uriBuilder.setScheme(SCHEMA).setHost(HOST).setPort(PORT);
            uriBuilder.setScheme(SCHEMA).setHost(HOST);
            HttpGet get = new HttpGet(uriBuilder.build().toString());
            /********************************************************/
            // set header, demonstarte how to use hmac signature here
            get.addHeader("Authorization", authorization);
            /********************************************************/
            get.addHeader("content-type", "application/json");
            CloseableHttpResponse response = null;
            try {
                //execute get request
                response = client.execute(get);
                //print result
                System.out.println("status code:" + response.getStatusLine().getStatusCode());
                System.out.println("status message:" + response.getStatusLine().getReasonPhrase());
                HttpEntity entity = response.getEntity();
                System.out.println("result:" + EntityUtils.toString(entity));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void post() {
        //params
        String method = "POST";
        String path = "/v2/providers/seller_api/apis/api/v1/marketplace/seller-products";
        
        //replace with your own product registration json data
        String strjson="{}";
        
        CloseableHttpClient client = null;
        try {
            //create client
            client = HttpClients.createDefault();
            //build uri
            URIBuilder uriBuilder = new URIBuilder()
                    .setPath(path);

            /********************************************************/
            //authorize, demonstrate how to generate hmac signature here
            String authorization = Hmac.generate(method, uriBuilder.build().toString(), SECRET_KEY, ACCESS_KEY);
            //print out the hmac key
                //System.out.println(authorization);
            /********************************************************/

            uriBuilder.setScheme(SCHEMA).setHost(HOST).setPort(PORT);
            HttpPost requestPost = new HttpPost(uriBuilder.build().toString());

            StringEntity params =new StringEntity(strjson,"UTF-8");
            
            /********************************************************/
            // set header, demonstarte how to use hmac signature here
            requestPost.addHeader("Authorization", authorization);
            /********************************************************/
            requestPost.addHeader("content-type", "application/json");
            requestPost.setEntity(params);
            CloseableHttpResponse response = null;
            try {
                //execute post request
                response = client.execute(requestPost);
                //print result
                System.out.println("status code:" + response.getStatusLine().getStatusCode());
                System.out.println("status message:" + response.getStatusLine().getReasonPhrase());
                HttpEntity entity = response.getEntity();
                System.out.println("result:" + EntityUtils.toString(entity));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (response != null) {
                    try {
                        response.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
