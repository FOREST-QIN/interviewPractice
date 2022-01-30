package jan.twentyNine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordDistance {
  Map<String, List<Integer>> map;
  public WordDistance(String[] wordsDict) {
    this.map = new HashMap<>();
    for (int i = 0; i < wordsDict.length; i++) {
      map.putIfAbsent(wordsDict[i], new ArrayList<>());
      map.get(wordsDict[i]).add(i);
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
