package functionalInterface;

import java.util.function.BiFunction;
import java.util.function.Function;

public class _BiFunction {
    public static void main(String[] args) {
        Integer increment = functionAdd.apply(1);
        System.out.println(increment);
        
        Integer result = functionAddAndMultiply.apply(10);
        System.out.println(result);
        
        String test = stringCompound.apply("A");
        System.out.println(test);

        Integer bifunc = bifunctionTest.apply(Integer.valueOf(1), Integer.valueOf(2));
        System.out.println(bifunc);
    }
    
    static Function<Integer, Integer> functionAdd = number -> number + 1;
    
    static Function<Integer, Integer> functionMultiply = number -> number*10;

    static Function<Integer, Integer> functionAddAndMultiply = functionMultiply.andThen(functionAdd);
    
    static Function<String, String> stringAdd = arg -> arg + " B";
    
    static Function<String, String> stringMulti = arg -> arg + " C";
    
    static Function<String, String> stringCompound = stringAdd.andThen(stringMulti);
    
    static BiFunction<Integer, Integer, Integer> bifunctionTest = (arg1, arg2) -> arg1 + arg2;
}
