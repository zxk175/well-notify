package com.zxk175.notify.core.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zxk175
 * @since 2020-03-29 13:38
 */
public class ThreadUtil {
	
	/**
	 * 新建一个线程池
	 *
	 * @param threadSize 同时执行的线程数大小
	 * @param threadName 线程名字
	 * @return ignore
	 */
	public static ExecutorService newExecutor(int threadSize, String threadName) {
		ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
				.setNameFormat(threadName + "-%d")
				.build();
		
		return new ThreadPoolExecutor(
				threadSize,
				threadSize,
				0L,
				TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(1024),
				namedThreadFactory,
				new ThreadPoolExecutor.AbortPolicy());
	}
	
}
