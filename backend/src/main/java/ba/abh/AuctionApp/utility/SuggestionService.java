package ba.abh.AuctionApp.utility;

import ba.abh.AuctionApp.dictionary.DictionaryEntry;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SuggestionService {
    public static int levenshteinDistance (CharSequence lhs, CharSequence rhs) {
        int len0 = lhs.length() + 1;
        int len1 = rhs.length() + 1;
        int[] cost = new int[len0];
        int[] newcost = new int[len0];

        for (int i = 0; i < len0; i++) cost[i] = i;
        for (int j = 1; j < len1; j++) {
            newcost[0] = j;
            for(int i = 1; i < len0; i++) {
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;

                int cost_replace = cost[i - 1] + match;
                int cost_insert  = cost[i] + 1;
                int cost_delete  = newcost[i - 1] + 1;

                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }
            int[] swap = cost; cost = newcost; newcost = swap;
        }
        return cost[len0 - 1];
    }

    private static int getThreshold(String word, int delta) {
        if (word.length() <= delta) {
            return word.length() - delta + 1;
        }

        return delta;
    }

    public static String  suggest(String search, List<DictionaryEntry> words) {
        Map<String, Integer> dictionary = new HashMap<>();
        words.forEach(entry -> {
            String[] separateWords = entry.getEntry().split(" ");
            dictionary.put(entry.getEntry(), levenshteinDistance(entry.getEntry(), search));
            Arrays.stream(separateWords).forEach(word -> dictionary.put(word, levenshteinDistance(word, search)));
        });

        final int levDelta = 3;
        Optional<Map.Entry<String, Integer>> min = dictionary
                .entrySet()
                .stream()
                .min(Comparator.comparingInt(Map.Entry::getValue));

        if(min.isPresent() && min.get().getValue() <= getThreshold(min.get().getKey(), levDelta)){
            return min.get().getKey().toLowerCase();
        }

        return null;
    }
}
