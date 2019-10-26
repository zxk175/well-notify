package com.zxk175.core.common.tuple;

import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;

/**
 * @author zxk175
 * @since 2019-08-26 16:39
 */
@Data
abstract class BaseTuple implements Iterable<Object>, Serializable {

    private final List<Object> valueList;


    BaseTuple(final Object... objects) {
        // 其实就是简单的数组，只是包装成List，方便使用List的api进行元素操作
        this.valueList = Arrays.asList(objects);
    }


    @Override
    public final Iterator<Object> iterator() {
        return this.valueList.iterator();
    }

    @Override
    public final Spliterator<Object> spliterator() {
        return this.valueList.spliterator();
    }
}
