import org.omg.CORBA.Object;

import java.util.NoSuchElementException;

public class MyArrayDeque<E> {
    private int size;
    private E[] data;
    private final static int INIT_CAP = 2;

    // data的索引区间为 [first, last)
    private int first, last;

    public MyArrayDeque(int initCap) {
        size = 0;
        data = (E[]) new Object[initCap];
        // 都初始化为0，区间[0, 0)是空集
        first = last = 0;
    }

    public MyArrayDeque() {
        this(INIT_CAP);
    }


    /***** 增 *****/
    public void addFirst(E element) {
        if (size == data.length) {
            resize(data.length * 2);
        }

        if (first == 0) {
            first = data.length - 1;
        } else {
            first--;
        }
        data[first] = element;

        size++;
    }

    public void addLast(E element) {
        if (size == data.length) {
            resize(data.length * 2);
        }

        data[last] = element;
        if (last == data.length - 1) {
            last = 0;
        } else {
            last++;
        }

        size++;
    }


    /***** 删 *****/
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (size < data.length / 4) {
            resize( data.length / 2);
        }

        E deletedVal = data[first];
        data[first] = null;
        if (first == data.length - 1) {
            first = 0;
        } else {
            first++;
        }

        size--;
        return deletedVal;
    }

    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (size < data.length / 4) {
            resize( data.length / 2);
        }

        if (last == 0) {
            last = data.length - 1;
        } else {
            last--;
        }
        E deletedVal = data[last];
        data[last] = null;

        size--;
        return deletedVal;
    }


    /***** 查 *****/
    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return data[first];
    }

    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (last == 0) return data[data.length - 1];
        return data[last-1];
    }


    /***** 工具函数 *****/
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    /***** 私有函数 *****/
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
