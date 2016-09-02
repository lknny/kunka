package com.kunka;

public interface Executor<T> {
	final int defaultCapacity=1*1000;
	final int defaultAliveTime=30;
	void execute(T t);
	void close();
	void add(T t);
	void remove(T t);
	void finished(T t);
}
