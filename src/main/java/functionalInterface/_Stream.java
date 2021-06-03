package functionalInterface;

import java.util.List;
import java.util.stream.Collectors;
import functionalInterface._Stream.Person.Gender;

public class _Stream {
    public static void main(String[] args) {
        List<Person> people = List.of(
                new Person("John", Gender.MALE),
                new Person("Alex", Gender.MALE),
                new Person("Alice", Gender.FEMALE),
                new Person("Tom", Gender.NOT_GIVEN)
                );
        
        people.stream().map(person -> person.getGender()).collect(Collectors.toSet())
            .forEach(gender -> System.out.println(gender));
        
        people.stream().map(person -> person.getGender()).collect(Collectors.toSet())
        .forEach(System.out::println);
        
        //people.stream().map(person -> person.getName()).mapToInt(name -> name.length()).forEach(length -> System.out.println(length));
        people.stream().map(person -> person.getName())
            .mapToInt(String::length)
            .forEach(System.out::println);
        
        boolean containOnlyFemale = people.stream().allMatch(person -> Gender.FEMALE.equals(person.getGender()));
        System.out.println(containOnlyFemale);
        
        boolean containFemale = people.stream().anyMatch(person -> Gender.FEMALE.equals(person.getGender()));
        System.out.println(containFemale);
    }
    
    static class Person {
        private final String name;
        private final Gender gender;
        
        public Person(String name, Gender gender) {
            this.name = name;
            this.gender = gender;
        }
        
        static enum Gender {
            MALE, FEMALE, NOT_GIVEN
        }

        public String getName() {
            return name;
        }

        public Gender getGender() {
            return gender;
        }
    }
}
