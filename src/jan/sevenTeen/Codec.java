package jan.sevenTeen;

import java.util.ArrayList;
import java.util.List;

public class Codec {


  // Encodes a list of strings to a single string.
  public String encode(List<String> strs) {
    StringBuilder prefix = new StringBuilder();
    for (String s : strs) {
      prefix.append(s.length()).append('/').append(s);
    }
    return prefix.toString();
  }

  // Decodes a single string to a list of strings.
  public List<String> decode(String s) {
    List<String> res = new ArrayList<>();
    int i = 0;
    while (i < s.length()) {
      int slash = s.indexOf('/', i);
      int size = Integer.parseInt(s.substring(i, slash));
      i = slash + size + 1;
      res.add(s.substring(slash + 1, i));
    }
    return res;
  }
}