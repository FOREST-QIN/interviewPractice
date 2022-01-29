package jan.twentyEight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordDistance {
  Map<String, ArrayList<Integer>> map = new HashMap<>();
  public WordDistance(String[] wordsDict) {
    for (int i = 0; i < wordsDict.length; i++) {
      String cur = wordsDict[i];
      map.putIfAbsent(cur, new ArrayList<>());
      map.get(cur).add(i);
    }
  }

  public int shortest(String word1, String word2) {
    List<Integer> one = map.get(word1);
    List<Integer> two = map.get(word2);

    int i = 0;
    int j = 0;
    int min = Integer.MAX_VALUE;

    while (i < one.size() && j < two.size()) {
      int idx1 = one.get(i);
      int idx2 = two.get(j);
      min = Math.min(min, Math.abs(idx1 - idx2));
      if (idx1 < idx2) {
        i++;
      } else {
        j++;
      }
    }
    return min;
  }

}
