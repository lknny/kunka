package com.kunka;

import com.kunka.dispatcher.Dispatcher;
import com.kunka.executor.SerialExecutor;

public class Test extends Task {

	protected Test(long taskNo) {
		super(taskNo);
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 final Dispatcher dispatcher=new Dispatcher("test", new SerialExecutor());
				for (int i = 1; i < 100; i++) {
					dispatcher.add(new Test(i));
			}
		
				new Thread(){
					public void run(){
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						for (int i = 100; i < 200; i++) {
							dispatcher.add(new Test(i));
							System.out.println("111111111111");
					}
					}
				}.start();
}

	@Override
	public void runTask() {
		// TODO Auto-generated method stub
		System.out.println("task:---------"+this.getTaskNo());
	}

	@Override
	public void timeOutAction() {
		// TODO Auto-generated method stub
	}
}