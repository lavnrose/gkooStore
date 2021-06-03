package functionalInterface;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class _Consumer {
    public static void main(String[] args) {
        greetCustomer(new Customer("Tom", "9999"));
        
        greetCustomerConsumer.accept(new Customer("Brat","1111"));

        greetCustomerConsumerBiFunction.accept(new Customer("Brat","1111"), false);
    }

    static void greetCustomer(Customer cs) {
        System.out.println("Hello " + cs.customerName + ", " + cs.customerPhoneNumber);
    }
    
    static Consumer<Customer> greetCustomerConsumer = customer -> 
        System.out.println("Hello " + customer.customerName + ", " + customer.customerPhoneNumber);
    
    static BiConsumer<Customer, Boolean> greetCustomerConsumerBiFunction = (customer, show) -> 
        System.out.println("Hello " + customer.customerName + ", " + (show ? customer.customerPhoneNumber: "******"));
    
    static class Customer {
        private final String customerName;
        private final String customerPhoneNumber;
        
        public Customer(String customerName, String customerPhoneNumber) {
            this.customerName = customerName;
            this.customerPhoneNumber = customerPhoneNumber;
        }
        
        
    }
}
