package mar.five;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class TimeMap {
  Map<String, TreeMap<Integer, String>> map;
  public TimeMap() {
    this.map = new HashMap<>();
  }

  public void set(String key, String value, int timestamp) {
    map.computeIfAbsent(key, k -> new TreeMap<>()).put(timestamp, value);
  }

  public String get(String key, int timestamp) {
    if (!map.containsKey(key)) return "";

    Integer floorKey = map.get(key).floorKey(timestamp);
    if (floorKey == null) return "";
    String res = map.get(key).get(floorKey);
    return res == null ? "" : res;
  }

}
