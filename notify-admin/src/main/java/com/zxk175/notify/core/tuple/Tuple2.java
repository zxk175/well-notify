package com.zxk175.notify.core.tuple;

/**
 * @author zxk175
 * @since 2019-08-26 16:35
 */
public final class Tuple2<A, B> extends BaseTuple {
	
	public final A first;
	public final B second;
	
	private Tuple2(final A first, final B second) {
		super(first, second);
		this.first = first;
		this.second = second;
	}
	
	/**
	 * 创建一个包含2个元素的元组
	 *
	 * @param first  第一个元素
	 * @param second 第二个元素
	 * @param <A>    第一个元素类型
	 * @param <B>    第二个元素类型
	 * @return 元组
	 * @see Tuples#tuple(Object, Object)
	 */
	public static <A, B> Tuple2<A, B> with(final A first, final B second) {
		return new Tuple2<>(first, second);
	}
}
