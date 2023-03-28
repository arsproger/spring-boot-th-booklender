import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;
public class StreamAPI {
    public static void main(String[] args) {
        ArrayList<String> cities = new ArrayList<>();
        Collections.addAll(cities, "Париж", "Лондон");
        cities.stream().filter(a -> a.length() >= 5).forEach(a -> System.out.println(a));
        System.out.println("----------------------------------------");
        Stream.of("3", "4", "5").map(Integer::parseInt).map(x -> x + 10).forEach(System.out::println);
//        ArrayList<Integer> numbers = new ArrayList<>();
//        numbers.addAll(ArrayList.numbers(new Integer[]{1,2,3,4,5,6,7,8,9}));
    }

}