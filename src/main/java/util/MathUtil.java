package util;

public class MathUtil {
    public static int mathCeilDigit(int digit, double price) {
        int power = (int) Math.pow(10, digit);
        int newPrice = (int) Math.round(price/power);
        return (newPrice*power);
    }
}
