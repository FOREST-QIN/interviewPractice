package practice.decem;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Anagrams
 * @Description TODO
 * @Author forest
 * @Date 2021/12/11 9:59 AM
 * @Version 1.0
 **/
public class Anagrams {

  final Integer[] primes;
  Map<Character, Integer> letterTable;
  Map<Long, ArrayList<String>> anagramTable;

  public Anagrams() {
    this.primes = new Integer[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61,67, 71, 73, 79, 83, 89, 97, 101};
    this.letterTable = new HashMap<>();
    this.anagramTable = new HashMap<>();
    buildLetterTable();
  }

  private void buildLetterTable() {
    char[] letters = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    for (int i = 0; i < letters.length; i++) letterTable.put(letters[i], primes[i]);
  }

  private void addWord(String s) {
    if (s == null || s.length() == 0) throw new IllegalArgumentException("Input String can not be null or have 0 length!");
    Long hashCode = myHashCode(s);
    anagramTable.putIfAbsent(hashCode, new ArrayList<>());
    anagramTable.get(hashCode).add(s);
  }

  private Long myHashCode(String s) {
    if (s == null || s.length() == 0) return 0L;
    long res = 1L;
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      res = res * letterTable.get(ch);
    }
    return res;
  }

  private void processFile(String s) throws IOException {
    FileInputStream fileInputStream = new FileInputStream(s);
    BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream));
    String strLine;
    while ((strLine = br.readLine()) != null) {
      this.addWord(strLine);
    }
    br.close();
  }

  private ArrayList<Map.Entry<Long, ArrayList<String>>> getMaxEntries() {
    int max = 0;
    // use a for loop to get the entry which value has the largest size
    for (Map.Entry<Long, ArrayList<String>> entry : anagramTable.entrySet()) {
      if (entry.getValue().size() > max) {
        max = entry.getValue().size();
      }
    }
    ArrayList<Map.Entry<Long, ArrayList<String>>> res = new ArrayList<>();
    for (Map.Entry<Long, ArrayList<String>> entry : anagramTable.entrySet()) {
      if (entry.getValue().size() == max) res.add(entry);
    }
    return res;
  }

  public static void main(String[] args) {
    Anagrams anagrams = new Anagrams();
    final long startTime = System.nanoTime();
    try {
      anagrams.processFile("words_alpha.txt");
    } catch (IOException e) {
      e.printStackTrace();
    }
    ArrayList<Map.Entry<Long, ArrayList<String>>> maxEntries = anagrams.getMaxEntries();
    final long estimatedTime = System.nanoTime() - startTime;
    final double seconds = ((double) estimatedTime / 1000000000);
    System.out.println("Time: " + seconds);
    System.out.println("List of max anagrams: " + maxEntries);
  }

}
