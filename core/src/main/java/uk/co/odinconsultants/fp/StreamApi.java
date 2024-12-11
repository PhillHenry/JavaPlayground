package uk.co.odinconsultants.fp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collection;

public class StreamApi {

    public static void main(String[] args) {
        List<List<String>> nestedList = Arrays.asList(
                Arrays.asList("a", "b"),
                Arrays.asList("c", "d"),
                Arrays.asList("e", "f")
        );

        List<String> flatList = nestedList.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        System.out.println(flatList); // Output: [a, b, c, d, e, f]
    }

}
