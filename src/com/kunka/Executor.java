package com.kunka;

public interface Executor<T> {
	public void execute(T t);
	public void shutdown();
}
