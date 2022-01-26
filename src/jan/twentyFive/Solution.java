package jan.twentyFive;

import java.util.*;

public class Solution {

  public int ladderLength(String beginWord, String endWord, List<String> wordList) {
    if (beginWord.equals(endWord)) return 0;
    HashSet<String> set = new HashSet<>(wordList);
    if (!set.contains(endWord)) return 0;

    Queue<String> queOne = new LinkedList<>();
    Set<String> visitedOne = new HashSet<>();
    queOne.offer(beginWord);
    visitedOne.add(beginWord);

    Queue<String> queTwo = new LinkedList<>();
    Set<String> visitedTwo = new HashSet<>();
    queTwo.offer(endWord);
    visitedTwo.add(endWord);

    int steps = 0;

    while (!queOne.isEmpty() && !queTwo.isEmpty()) {
      steps++;
      if (queOne.size() > queTwo.size()) {
        Queue<String> tmp = queOne;
        queOne = queTwo;
        queTwo = tmp;

        Set<String> vTmp = visitedOne;
        visitedOne = visitedTwo;
        visitedTwo = vTmp;
      }
      int size = queOne.size();

      for (int i = 0; i < size; i++) {
        String cur = queOne.poll();
        char[] array = cur.toCharArray();
        for (int j = 0; j < cur.length(); j++) {
          for (char ch = 'a'; ch <= 'z'; ch++) {
            char tmp = array[j];
            array[j] = ch;
            String newWord = String.valueOf(array);
            if (set.contains(newWord) && !visitedOne.contains(newWord)) {
              visitedOne.add(newWord);
              queOne.offer(newWord);
            }
            if (visitedTwo.contains(newWord)) return steps + 1;
            array[j] = tmp;
          }
        }
      }
    }
    return 0;
  }


}
