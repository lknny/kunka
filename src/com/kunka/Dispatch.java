package com.kunka;

public interface Dispatch<T> {
	public void add(T t);
	public void remove(T t);
	public void dispatch(Executor<T> executor,T t);
	public void shutdown();
}
