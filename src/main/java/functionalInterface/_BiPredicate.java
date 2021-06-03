package functionalInterface;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class _BiPredicate {
    public static void main(String[] args) {
        List<Domain> domains = Arrays.asList(new Domain("google.com", 1),
                new Domain("i-am-spammer.com", 10),
                new Domain("mkyong.com", 0),
                new Domain("microsoft.com", 2));
                                                        // function
        List<Domain> result2 = filterBadDomain(domains, (domain, score) -> score == 0);
        System.out.println(result2); // mkyong.com, microsoft.com
        
//        BiPredicate<String, Integer> bi = (domain, score) -> {
//            return (domain.equalsIgnoreCase("google.com") || score == 0);
//        };
//
//        List<Domain> result = filterBadDomain(domains, bi);
//        System.out.println(result.toString());
    }
    
    public static <T extends Domain> List<T> filterBadDomain(
            List<T> list, BiPredicate<String, Integer> biPredicate) {

        return list.stream()
                .filter(x -> biPredicate.test(x.getName(), x.getScore()))
                .collect(Collectors.toList());

    }
}

