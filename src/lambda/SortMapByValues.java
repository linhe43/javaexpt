package lambda;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static java.util.Map.Entry;

public class SortMapByValues {

    public static void main(String[] args) {
        Map<String, Integer> budget = new HashMap<>();
        int count = 10000000;
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < count; i++) {
            int len = rand.nextInt(10) + 5;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < len; j++) {
                sb.append((char) ('a' + rand.nextInt(26)));
            }
            budget.put(sb.toString(), rand.nextInt(count));
        }

        System.out.println("map before sorting by values: " + budget.size());

        long startTime = System.currentTimeMillis();
        // sort by values;
        Map<String, Integer> sorted = budget
                .entrySet()
                .stream()
                .sorted(Entry.comparingByValue())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> 0, LinkedHashMap::new));

        System.out.printf("sorted using %d s", (System.currentTimeMillis() - startTime) / 1000);
    }

}
