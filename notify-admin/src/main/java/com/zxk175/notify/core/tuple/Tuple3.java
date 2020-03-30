package com.zxk175.notify.core.tuple;

/**
 * @author zxk175
 * @since 2019-08-26 16:35
 */
public final class Tuple3<A, B, C> extends BaseTuple {

    public final A first;
    public final B second;
    public final C third;

    private Tuple3(final A first, final B second, final C third) {
        super(first, second, third);
        this.first = first;
        this.second = second;
        this.third = third;
    }

    /**
     * 创建一个包含3个元素的元组
     *
     * @param first  第一个元素
     * @param second 第二个元素
     * @param third  第三个元素
     * @param <A>    第一个元素类型
     * @param <B>    第二个元素类型
     * @param <C>    第三个元素类型
     * @return 元组
     * @see Tuples#tuple(Object, Object, Object)
     */
    public static <A, B, C> Tuple3<A, B, C> with(final A first, final B second, final C third) {
        return new Tuple3<>(first, second, third);
    }
}
