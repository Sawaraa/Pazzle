import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PuzzleSolver {

    public static void main(String[] args) {
        String filePath = "source.txt";

        try {
            List<String> fragments = readFragmentsFromFile(filePath);
            String longestSequence = findLongestSequence(fragments);
            System.out.println("Найдовша послідовність: " + longestSequence);
        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
        }
    }

    private static List<String> readFragmentsFromFile(String filePath) throws IOException {
        List<String> fragments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.matches("\\d+")) {
                    fragments.add(line);
                }
            }
        }
        return fragments;
    }

    private static String findLongestSequence(List<String> fragments) {
        if (fragments.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        Set<String> usedFragments = new HashSet<>();

        for (String startFragment : fragments) {
            StringBuilder currentSequence = new StringBuilder(startFragment);
            usedFragments.add(startFragment);

            while (true) {
                String nextFragment = findNextFragment(currentSequence.toString(), fragments, usedFragments);
                if (nextFragment == null) {
                    break;
                }
                currentSequence.append(nextFragment.substring(2));
                usedFragments.add(nextFragment);
            }

            if (currentSequence.length() > result.length()) {
                result = currentSequence;
            }

            usedFragments.clear();
        }

        return result.toString();
    }

    private static String findNextFragment(String currentSequence, List<String> fragments, Set<String> usedFragments) {
        String lastTwoDigits = currentSequence.substring(currentSequence.length() - 2);

        for (String fragment : fragments) {
            if (!usedFragments.contains(fragment) && fragment.startsWith(lastTwoDigits)) {
                return fragment;
            }
        }

        return null;
    }
}
