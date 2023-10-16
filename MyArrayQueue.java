import org.omg.CORBA.Object;

import java.util.NoSuchElementException;

public class MyArrayQueue<E> {
    private int size;
    private E[] data;
    private final static int INIT_CAP = 1;

    private int first, last;

    public MyArrayQueue(int initCap) {
        size = 0;
        data = (E[]) new Object[initCap];
        // last是下一次应该添加元素的索引
        // first----last, [first, last)
        // 比如first = 1, last = 3, size = 2
        first = last = 0;
    }

    public MyArrayQueue() {
        // 不传参数，默认大小为INIT_CAP
        this(INIT_CAP);
    }

    public void offer(E element) {
        if (size == data.length) {
            resize(data.length * 2);
        }

        data[last] = element;
        last++;
        if(last == data.length) {
            last = 0;
        }

        size++;
    }

    public E pull() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (size == data.length / 4) {
            resize(data.length / 2);
        }

        E deletedVal = data[last];
        data[first] = null;
        first++;
        // ?
        if(first == data.length) {
            first = 0;
        }

        size--;
        return deletedVal;
    }

    public E peekFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return data[first];
    }

    public E peekLast() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
        if (last == 0) return data[data.length-1];
        return data[last-1];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize(int newCap) {
        E[] temp = (E[]) new Object[newCap];

        for (int i = 0; i < size; i++) {
            temp[i] = data[(first + i) % data.length];
        }

        first = 0;
        last = size;
        data = temp;
    }
}
