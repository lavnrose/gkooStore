package coupang.entities;

import java.util.HashMap;

public class CategoryPredictResponse {
    private int code;
    private String message;
    private HashMap<String, String> data = new HashMap<>();
    
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public HashMap<String, String> getData() {
        return data;
    }
    
    public void setData(HashMap<String, String> data) {
        this.data = data;
    }
}
