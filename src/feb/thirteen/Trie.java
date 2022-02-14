package feb.thirteen;

import java.util.HashMap;
import java.util.Map;

public class Trie {

  TrieNode root;

  public Trie() {
    this.root = new TrieNode();
  }

  public void insert(String word) {
    TrieNode cur = root;
    for (int i = 0; i < word.length(); i++) {
      char ch = word.charAt(i);
      TrieNode next = cur.child.get(ch);
      if (next == null) {
        cur.child.put(ch, new TrieNode());
        next = cur.child.get(ch);
      }
      cur = next;
    }
    cur.isWord = true;
  }

  public boolean search(String word) {
    TrieNode cur = root;
    for (int i = 0; i < word.length(); i++) {
      char ch = word.charAt(i);
      TrieNode next = cur.child.get(ch);
      if (next == null) return false;
      cur = next;
    }
    return cur.isWord;
  }

  public boolean startsWith(String prefix) {
    TrieNode cur = root;
    for (int i = 0; i < prefix.length(); i++) {
      char ch = prefix.charAt(i);
      TrieNode next = cur.child.get(ch);
      if (next == null) return false;
      cur = next;
    }
    return true;
  }

  static class TrieNode {

    Map<Character, TrieNode> child = new HashMap<>();
    boolean isWord = false;
    public TrieNode() {}
  }

}
