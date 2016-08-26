package com.kunka;

import com.kunka.dispatcher.Dispatcher;
import com.kunka.executor.ParallelExecutor;
import com.kunka.executor.SerialExecutor;

public class Test extends Task {
	
	public Test(String s){
		super(s);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 final Dispatcher dispatcher=new Dispatcher(new ParallelExecutor(5,"pThread"));
				for (int i = 1; i < 100; i++) {
					dispatcher.add(new Test(String.valueOf(i)));
			}
		
				new Thread(){
					public void run(){
						try {
							Thread.sleep(7000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						for (int i = 100; i < 200; i++) {
							dispatcher.add(new Test(String.valueOf(i)));
					}
					}
				}.start();

}

	@Override
	public void runTask() {
		// TODO Auto-generated method stub
		System.out.println("task:---------"+this.getTaskId());
	}

	@Override
	public void timeOutAction() {
		// TODO Auto-generated method stub
	}

	@Override
	public void finishTask() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTask() {
		// TODO Auto-generated method stub
		
	}
}