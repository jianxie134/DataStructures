public class MyLinkedQueue<E> {
    private MyLinkedList<E> list = new MyLinkedList<>();

    // 从队尾添加元素
    public void offer(E e) {
        list.addLast(e);
    }

    // 从队头弹出元素
    public E poll() {
        return list.removeFirst();
    }

    // 查看队头元素
    public E peekFirst() {
        return list.getFirst();
    }

    // 查看队尾元素
    public E peekLast() {
        return list.getLast();
    }
}