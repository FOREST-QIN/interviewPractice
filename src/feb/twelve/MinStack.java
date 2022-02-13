package feb.twelve;

import java.util.ArrayDeque;
import java.util.Deque;

public class MinStack {

  /*
                1 1 1
     minStack[ 5 4 3 2 <1, 5> <0, >
     Node <val, the size after I first time offer to my data>
     case one: push elem is larger than my stack.peekFirst() current min element, just push it into stack
     case two: push elem is smaller than cur min or equals to current min, push it into my minStack

     pop:
         case one: popped element is equal to min  pop data elem and pop min stack element
         case two: is not, just pop data elemengt


   */
  Deque<Integer> stack;
  Deque<Node> min;
  public MinStack() {
    this.stack = new ArrayDeque<>();
    this.min = new ArrayDeque<>();
  }

  public void push(int val) {
    stack.offerFirst(val);
    // case 1: min is empty, case 2: current min is larger then vale
    if (min.isEmpty() || min.peekFirst().val > val) {
      min.offerFirst(new Node(val, stack.size()));
    }
  }

  public void pop() {
    // case: current element is the min
    if (!min.isEmpty() && min.peekFirst().size == stack.size()) {
      min.pollFirst();
    }
    // case: not
    stack.pollFirst();
  }

  public int top() {
    return stack.peekFirst();
  }

  public int getMin() {
    return min.peekFirst().val;
  }

  static class Node {
    // val
    int val;
    // size: the first meet the current value from data stack
    int size;

    public Node(int val, int size) {
      this.val = val;
      this.size = size;
    }
  }

}
