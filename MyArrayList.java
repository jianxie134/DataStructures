import org.omg.CORBA.Object;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<E> implements Iterable<E>{
    // 真正存储数据
    private E[] data;
    // 记录当前数组中元素的个数
    private int size;
    // 默认初始容量
    private static final int INIT_CAP = 1;

    public MyArrayList() {
        this(INIT_CAP);
    };

    public MyArrayList(int initCapacity) {
        data = (E[]) new Object[initCapacity]; // Type parameter 'E' cannot be instantiated directly
        size = 0;
    }

    // 实现的时候先写边界条件
    /***** 增 *****/
    // 在数组尾部添加一个元素e
    public void addLast(E e) {
        if (data.length == size) {
            // 扩容 一般来说扩容至两倍
            resize(data.length * 2);
        }

        data[size] = e;
        size++;
    }

    public void add(int index, E element) {
        checkPositionIndex(index);

        if (data.length == size) {
            resize(data.length * 2);
        }

        // data[index..] => data[index+1..]
        System.arraycopy(data, index, data, index+1, size-index-1);
        data[index] = element;
        size++;
    }

    public void addFirst(E e) {
        add(0, e);
    }

    /***** 删 *****/

    // 删除数组的最后一个元素并返回
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (size < data.length / 4) {
            resize(data.length / 2);
        }

        E deletedVal = data[size - 1];
        data[size - 1] = null; // 如果不置空，触发不了垃圾回收，可能出现内存泄漏问题
        size--;
        return deletedVal;
    }

    // 删除 index 对应的元素并返回
    public E remove(int index) {
        checkElementIndex(index);

        if (size < data.length / 4) {
            resize(data.length / 2);
        }

        E deletedVal = data[index];
        System.arraycopy(data, index+1, data, index, size-index);
        data[size] = null;
        size--;
        return deletedVal;
    }

    /***** 查 *****/
    // 返回索引index对应的元素
    public E get(int index) {
        checkElementIndex(index);

        return data[index];
    }

    /***** 改 *****/
    // 返回索引index对应的元素
    public E set(int index, E element) {
        checkElementIndex(index);

        E oldVal = data[index];
        data[index] = element;
        return oldVal;
    }

    /***** 工具函数 *****/
    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }


    /***** 私有函数 *****/
    // ElementIndex和PositionIndex区别：Element指的是元素，Position指的是边界
    // 正常5个元素的数组，Element有0-4，Position有0-5
    // 所以在add方法里我们用PositionIndex检查，其他的都是用ElementIndex

    private boolean isElementIndex(int index) { return index >= 0 && index < size; }

    private boolean isPositionIndex(int index) { return index >= 0 && index <= size; }

    // 检查 index 索引位置是否可以存在元素
    private void checkElementIndex(int index) {
        if(!isElementIndex(index))
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
    }

    // 检查 index 索引位置是否可以存在元素
    private void checkPositionIndex(int index) {
        if(!isPositionIndex(index))
            throw new IndexOutOfBoundsException("index: " + index + ", size: " + size);
    }

    // 将数组大小扩大或缩小为newCap
    // resize
    private void resize(int newCap) {
        E[] temp = (E[]) new Object[newCap];
        // copy数据: temp[0..0+size] = data[0..0+size]
        System.arraycopy(data, 0,
                        temp, 0,
                        size);
        data = temp;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int p = 0;

            @Override
            public boolean hasNext() {
                return p != size;
            }

            @Override
            public E next() {
                return data[p++];
            }
        };
    }

    private void display() {
        System.out.println("size = " + size + " cap = " + data.length);
        System.out.println(Arrays.toString(data));
    }

    public static void main(String[] args) {
        // 初始容量设置为 3
        MyArrayList<Integer> arr = new MyArrayList<>(3);

        // 添加 5 个元素
        for (int i = 1; i <= 5; i++) {
            arr.addLast(i);
        }

        arr.remove(3);
        arr.add(1, 9);
        arr.addFirst(100);
        int val = arr.removeLast();

        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i));
        }
    }

}

