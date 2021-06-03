package functionalInterface;

import java.util.function.Supplier;

public class _Supplier {
    public static void main(String[] args) {
        System.out.println(getDBConnectionUrlSupplier.get());
    }
    
    static Supplier<String> getDBConnectionUrlSupplier = () -> "jbbc://localhost:5432/users";
    
}
