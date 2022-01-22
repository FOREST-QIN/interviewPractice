package multiThread.sevenTeen;

import org.junit.Test;

public class Solution {

  volatile int count;

  @Test
  public void test() throws InterruptedException {
    this.count = 0;
    int threadNums = 10;
    Thread[] threads = new Thread[threadNums];
    for (int i = 0; i < threadNums; i++) {
      threads[i] = new Thread(new Runnable() {
        @Override
        public void run() {
          for (int i = 0; i < 1000; i++) {
            count++;
          }
        }
      });
    }

    for (int i = 0; i < threadNums; i++) {
      threads[i].start();
    }
    for (int i = 0; i < threadNums; i++) {
      threads[i].join();;
    }
    System.out.println("Count = :" + count);
  }


}

