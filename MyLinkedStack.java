public class MyLinkedStack<E> {
    private MyLinkedList<E> list = new MyLinkedList<>();

    // 向栈顶加入元素
    public void push(E e) {
        list.addLast(e);
    }

    // 从栈顶弹出元素
    public E pop() {
        return list.removeLast();
    }

    // 查看栈顶元素
    public E peek() {
        return list.getLast();
    }
}