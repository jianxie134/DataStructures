import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;


// 数据结构 其实就是一个抽象的接口 提供一个API的集合 我们要做的就是 尽可能要高效的设计这些数据结构式的底层实现 来写API

public class General {

    public static void main(String[] args) {


        // 3.
//        ListNode head = new ListNode(0);
//        head.next = new ListNode(1);

        // 1. Array
        int m = 5, n = 10;
        int[] nums = new int[n]; // 初始化一个大小为 10 的 int 数组，其中的值默认初始化为 0
        boolean[][] visited = new boolean[m][n]; // 初始化一个 m * n 的二维布尔数组，其中的元素默认初始化为 false
        // 作为原始类型，这就类似 C 语言的 int 数组，操作起来比较麻烦，所以我们更多地使用 MyArrayList 动态数组，后面的视频课会详细讲解。



        // 2. String
        // Java 的字符串处理起来挺麻烦的，因为它不支持用 [] 直接访问其中的字符，而且不能直接修改，要转化成 char[] 类型才能修改。
        // 主要说下 String 在本课程中会用到的一些特性

        String s1 = "hello world";
        char c = s1.charAt(2);
        char[] chars = s1.toCharArray();
        chars[1] = 'a';

        String s2 = new String(chars);
        System.out.println(s2);

        // 一定要用equals方法判断字符串是否相同。不要用 == 比较，否则可能出现不易察觉的 bug。
        if (s1.equals(s2)) {

        } else {

        }

        // 字符串可以用加号进行拼接
        String s3 = s1 + "!";
        System.out.println(s3);

//        Java 的字符串不能直接修改，要用 toCharArray 转化成 char[] 的数组进行修改，然后再转换回 String 类型。
//        另外，虽然字符串支持用 + 进行拼接，但是效率并不高，并不建议在 for 循环中使用。如果需要进行频繁的字符串拼接，推荐使用 StringBuilder：

        StringBuilder sb = new StringBuilder();

        for (char c1 = 'a'; c1 <= 'f'; c1++) {
            sb.append(c1);
        }

        // append 方法支持拼接字符、字符串、数字等类型
        sb.append('g').append("hij").append(123);

        String res = sb.toString();
        // 输出：abcdefghij123
        System.out.println(res);



        // 3. MyArrayList 动态数组
        // 相当于把Java内置的数组类型做了包装

        ArrayList<Integer> nums1 = new ArrayList<>();
        ArrayList<String> strings1 = new ArrayList<>();

        //常用的方法

        // nums1.isEmpty(); boolean
        // nums1.size();
        // nums1.get(2);
        // nums1.add(5);

        // 本课程精选的例题只会用到这些最简单的方法，你应该看一眼就能明白，另外我也会带你手把手写一个 MyArrayList 类，实现上述方法。



        // 4. LinkedList
        // ArrayList列表底层是数组实现的，而LinkedList底层是双链表实现的，初始化方法也是类似的

        LinkedList<Integer> nums2 = new LinkedList<>();
        LinkedList<String> strings2 = new LinkedList<>();

        nums2.isEmpty();
        nums2.size();
        nums2.contains(2);
        nums2.add(5);
        nums2.addFirst(1);
        nums2.addLast(10);
        nums2.removeFirst();
        nums2.removeLast();

        // 这些都是最简单的方法。和ArrayList不同的是，我们更多地使用了LinkedList对于头部和尾部元素地操作：因为底层数据结构为链表，直接操作头尾地元素效率较高
        // 其中只有contains(object o) 方法地时间复杂度是O(n)，因为必须遍历链表才能判断元素是否存在
        // 在本课程中，我们亲自实现了MyLinkedList类之后你就会理解每个方法的时间复杂度了



        // 5. HashMap
        // 哈希表是非常常用的数据结构，用来存储键值对映射，初始化方法：
        HashMap<Integer, String> map1 = new HashMap<>();
        HashMap<String, int[]> map2 = new HashMap<>();

        map1.containsKey(5);
        map1.get(5);
        map1.put(5,"c");
        map1.remove(5);

        // 本课程会实现MyHashMap类，深入理解两种处理哈希冲突的原理



        // 6. HashSet
        HashSet<String> set = new HashSet<>();

        set.add("b");
        set.contains("a");
        set.remove("c");

        // 实现了MyHashMap之后，就知道HashSet和HashMap其实是一样的


    }

}



//class ListNode {
//    int val;
//    ListNode next;
//    // 初始化
//    public ListNode(int val) {
//        this.val = val;
//    }
//}