package multiThread.sevenTeen;

public class BlockingQueue<T> {
  T[] array;
  int size = 0;
  int capacity;
  int head = 0;
  int tail = 0;

  public BlockingQueue(int capacity) {
    this.array = (T[]) new Object[capacity];
    this.capacity = capacity;
  }

  private synchronized void enqueue(T item) throws InterruptedException {
    /*
    notify/all
    yield: 当前的让步，
    join: 先执行，其他都等着



    enqueue:
    size++
    append it to tail
    tail++
    00000
    i
     */
    while (size == capacity) wait();
    if (tail == capacity) tail = 0;
    array[tail] = item;
    size++;
    tail++;
    notifyAll();
  }

  private synchronized T dequeue() throws InterruptedException {
    T item = null;
    while (size == 0) wait();
    if (head == capacity) head = 0;
    item = array[head];
    array[head] = null;
    head++;
    size--;
    notifyAll();
    return item;
  }

}
