import java.util.Iterator;
import java.util.NoSuchElementException;

// 底层为单链表
public class MyLinkedList<E> implements Iterable<E>{

    //双链表节点
    private static class Node<E> {
        E val;
        Node<E> next;
        Node<E> prev;

        Node(E val) { this.val = val; }
    }

    private final Node<E> head, tail;
    private int size;

    // 构造函数初始化头尾节点
    public MyLinkedList() {
        // 哨兵节点/占位符
        this.head = new Node<>(null); // val随便写
        this.tail = new Node<>(null);
        head.next = tail;
        tail.prev = head;

        this.size = 0;
    }

    /***** 增 *****/
    public void addFirst(E element) {
        Node<E> x = new Node<>(element);

        Node<E> temp = head.next;
        // temp head
        x.next = temp;
        x.prev = head;

        head.next = x;
        temp.prev = x;

        size++;
    }

    public void addLast(E element) {
        Node<E> x = new Node<>(element);

        Node<E> temp = tail.prev;
        // temp tail
        x.next = tail;
        x.prev = temp;

        temp.next = x;
        tail.prev = x;

        size++;
    }

    public void add(int index, E element) {
        // ??
        checkPositionIndex(index);

        Node<E> x = new Node<>(element);
        // temp -> x -> p
        Node<E> p = getNode(index);
        Node<E> temp = p.prev;

        // 改指向
        x.next = p;
        x.prev = temp;

        temp.next = x;
        p.prev = x;

        size++;
    }


    /***** 删 *****/
    public E removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        Node<E> x = head.next;
        Node<E> temp = x.next;
        // head -> x -> temp

        head.next = temp;
        temp.prev = head;

        // 不置空也会被回收，因为没有被指向
        x.next = x.prev = null;

        size--;
        return x.val;
    }

    public E removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        Node<E> x = tail.prev;
        Node<E> temp = x.prev;
        // temp -> x -> tail

        temp.next = tail;
        tail.prev = temp;

        x.next = x.prev = null;

        size--;
        return x.val;
    }

    public E remove(int index) {

        checkElementIndex(index);

        Node<E> p = getNode(index);
        Node<E> prev = p.prev;
        Node<E> next = p.next;
        // prev -> p -> next

        prev.next = next;
        next.prev = prev;

        p.next = p.prev = null;

        size--;
        return p.val;
    }


    /***** 查 *****/
    public E getFirst() {
        if (isEmpty())
            throw new NoSuchElementException();
        return head.next.val;
    }

    public E getLast() {
        if (isEmpty())
            throw new NoSuchElementException();
        return tail.prev.val;
    }

    public E get(int index) {
        checkElementIndex(index);
        return getNode(index).val;
    }


    /***** 改 *****/
    public E set(int index, E element) {
        checkElementIndex(index);

        Node<E> p = getNode(index);

        E deletedVal = p.val;
        p.val = element;

        return deletedVal;
    }


    /***** 工具函数 *****/
    public int size() { return size; }

    public boolean isEmpty() { return size == 0; }


    /***** 私有函数 *****/
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

    // 返回Index对应的Node
    private Node<E> getNode(int index) {
        Node<E> p = head.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p;
    }

    private void display() {
        System.out.println("size = " + size);
        for (Node<E> p = head.next; p != tail; p = p.next) {
            System.out.print(p.val + " -> ");
        }
        System.out.println("null");
        System.out.println();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            Node<E> p = head.next;

            @Override
            public boolean hasNext() {
                return p != tail;
            }

            @Override
            public E next() {
                E val = p.val;
                p = p.next;
                return val;
            }
        };
    }

}
