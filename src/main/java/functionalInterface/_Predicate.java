package functionalInterface;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class _Predicate {

    public static void main(String[] args) {
        //System.out.println(isPhoneNumberValid("070"));
        //System.out.println(isPhoneNumberValid("700"));
        
        System.out.println(isPhoneNumberValidPredicate.test("700"));
        System.out.println(isPhoneNumberValidPredicate.test("070"));
        
        System.out.println(isPhoneNumberValidPredicate.and(containsNumber3).test("0730"));
        System.out.println(isPhoneNumberValidPredicate.or(containsNumber3).test("340"));
        
        boolean result = filter.test("mkyong", 6);
        System.out.println(result);
    }
    
    static boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.startsWith("07");
    }
    
    static Predicate<String> isPhoneNumberValidPredicate = phoneNumber -> phoneNumber.startsWith("07");
    
    static Predicate<String> containsNumber3 = number -> number.contains("3");
    
    //https://mkyong.com/java8/java-8-bipredicate-examples/
    static BiPredicate<String, Integer> filter = (x, y) -> {
        return x.length() == y;
    };
}
