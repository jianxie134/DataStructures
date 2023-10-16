public class RingBuffer<E> {
    private byte[] buffer;
    // mask 用于防止索引越界
    private int mask;
    // 读写指针的位置
    private int r, w;
    // 记录可读取的字节数
    private int size;
    // 默认初始化的 buffer 大小
    private static final int INIT_CAP = 1024;

    public RingBuffer() { this(INIT_CAP); }

    public RingBuffer(int cap) {
        // mask的作用：加速 求模% 运算
        // 将输入的 cap 变成 2 的指数
        cap = ceilToPowerOfTwo(cap);
        // 如果保证capacity是2的指数，则：(i + n) % capacity 等价于 (i + n) & mask，效率大大提高
        mask = cap - 1;
        buffer = new byte[cap];

        // 读/写指针初始化在索引0
        r = w = 0;
        // 还没有写入任何数据，可读取字节数为0
        size = 0;
    }

    // 从 RingBuffer 中读取元素到 out 中，返回读取的字节数
    public int read(byte[] out) {
        if (out == null || out.length == 0 || isEmpty()) {
            return 0;
        }

        int n = Math.min(size, out.length);

        // 情况1：r----w
        if (r < w) {
            System.arraycopy(buffer, 0, out, 0, n);
            r += n;
            size -= n;
            return n;
        }

        // 情况2：---w  r---
        // 2.1 ---w  ***r-
        if (r + n <= buffer.length) {
            System.arraycopy(buffer, 0, out, 0, n);
        } else {
            // 2.2 **r---w  ****
            int n1 = buffer.length - r;
            int n2 = n - n1;
            System.arraycopy(buffer, r, out, 0, n);
            System.arraycopy(buffer, 0, out, n1, n2);
        }

        // 移动r指针
        r = (r + n) & mask;

        size -= n;
        return n;
    }

    // 将 in 中的数据写入 RingBuffer 中，返回写入的字节数
    public int write(byte[] in) {
        if (in == null || in.length == 0) {
            return 0;
        }

        final int n = in.length;
        int free = buffer.length - size;
        // 保证buffer中有足够空间
        if (n > free) {
            resize(size + n);
        }

        // for循环效率低，数组复制效率最高的是标准库arraycopy() （但是要分情况）
        for (int i = 0; i < n; i++) {
            buffer[(w + i) & mask] = in[i];
        }
//        System.arraycopy();

        // w指针要向前移动
        w = (w + n) & mask;
        size += n;
        return n;
    }

    // 返回可读的字节数
    public int length() { return size; }

    // 没有可读的数据
    public boolean isEmpty() { return size == 0; }

    private void resize(int newCap) {
        newCap = ceilToPowerOfTwo(newCap);
        // 将 data 中的数据读入新 temp buffer 中
        byte[] temp = new byte[newCap];
        int n = read(temp);
        // 更新其他字段的值
        this.buffer = temp;
        this.r = 0;
        this.w = n;
        this.mask = newCap - 1;
    }

    // 将输入的n转化为2的指数，比如输入12，返回16
    private static int ceilToPowerOfTwo(int n) {
        if (n < 0) {
            // 肯定不能小于 0
            n = 2;
        }

        if (n > (1 << 30)) {
            // int 型最大值为 2^31 - 1
            // 所以无法向上取整到 2^31
            n = 1 << 30;
        }

        // O(logn)
//        int res = 1;
//        while (res < n) {
//            res = res * 2;
//        }
//        return res;

        // 位运算技巧，参考如下链接：
        // http://graphics.stanford.edu/~seander/bithacks.html#RoundUpPowerOf2
        // O(1)
        n--;
        n |= n >> 1;
        n |= n >> 2;
        n |= n >> 4;
        n |= n >> 8;
        n |= n >> 16;
        n++;

        return n;
    }

    public static void main(String[] args) {
        RingBuffer rb = new RingBuffer();
        // 9个字节，每个是 char 类型
        String s = "123456789";

        int nwrite = rb.write(s.getBytes());
        System.out.println("write " + nwrite + " bytes " + s);
        // write 9 bytes 123456789

        byte[] out1 = new byte[6];
        int nread = rb.read(out1);
        System.out.println("read " + nread + " bytes " + new String(out1));
        // read 6 bytes 123456

        byte[] out2 = new byte[6];
        nread = rb.read(out1);
        System.out.println("read " + nread + " bytes " + new String(out2));
        // read 3 bytes 789NullNullNull
    }

}
