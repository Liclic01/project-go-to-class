package finder.impl;

import finder.ClassNameFinder;
import util.Matcher;
import util.impl.MatcherImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * @author aburulev on 15.12.2019.
 */
public class ClassNameFinderImpl implements ClassNameFinder {

    @Override
    public void findClassNames(String filePath, String pattern) {
        Matcher nameMatcher = MatcherImpl.buildMatcher(pattern).build();
        Map<String, String> classes = new TreeMap<>();
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(line -> {
                String name = line.substring(line.lastIndexOf("."));
                if (nameMatcher.matches(name)) {
                    classes.put(name, line);
                }
            });
        } catch (IOException e) {
            System.out.println("Error reading file");
            e.printStackTrace();
        }

        classes.forEach((key, value) -> System.out.println(value));
    }
}
