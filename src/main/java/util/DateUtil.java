package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static void main(String[] args) {
        SimpleDateFormat today = new SimpleDateFormat("yyyy.MM.dd");
        Date date = new Date();
        //System.out.println("Today Date & Time at Now :"+today.format(date));  
    }
    
    public static String getToday() {
        SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return today.format(date);
    }
    
    public static String getStartedAt() {
        String today = getToday();
        return today + "T00:00:00";
    }
}
