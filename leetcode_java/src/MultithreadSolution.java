import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class MultithreadSolution {

	//Q1114
	class Foo {		
		
		Semaphore twolock = new Semaphore(1);;
		Semaphore threelock = new Semaphore(1);
		
		public Foo() {
			try {			
				twolock.acquire();
				threelock.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		public void first(Runnable printFirst) throws InterruptedException {
			printFirst.run();
			twolock.release();
		}
		
		public void second(Runnable printSecond) throws InterruptedException {
			twolock.acquire();
			printSecond.run();
	        threelock.release();
	    }

	    public void third(Runnable printThird) throws InterruptedException {
	    	threelock.acquire();
	    	printThird.run();
	    }
	}
    
	public void printInOrderMain() {
		Foo foo = new Foo();
		
		Runnable r1 = new Runnable() {
			@Override
			public void run() {
				System.out.println("one");
			}
		};
		Thread t1 = new Thread(() -> {
            try {
                foo.first(r1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
		
		Runnable r2 = new Runnable() {
			@Override
			public void run() {
				System.out.println("two");
			}
		};
		Thread t2 = new Thread(() -> {
            try {
                foo.second(r2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
		
		Runnable r3 = new Runnable() {
			@Override
			public void run() {
				System.out.println("three");
			}
		};
		Thread t3 = new Thread(() -> {
            try {
                foo.third(r3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
		
		try {
			t1.start();
			t2.start();
			t3.start();
			
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Q1115
	class FooBar {
	    private int n;
	    Semaphore fooLock = new Semaphore(1);
	    Semaphore barLock = new Semaphore(0);
	    public FooBar(int n) {
	        this.n = n;
	    }

	    public void foo(Runnable printFoo) throws InterruptedException {
	        
	        for (int i = 0; i < n; i++) {
	        	fooLock.acquire();
	        	// printFoo.run() outputs "foo". Do not change or remove this line.
	        	printFoo.run();
	        	barLock.release();
	        }
	    }

	    public void bar(Runnable printBar) throws InterruptedException {
	        
	        for (int i = 0; i < n; i++) {
	        	barLock.acquire();
	            // printBar.run() outputs "bar". Do not change or remove this line.
	        	printBar.run();
	        	fooLock.release();
	        }
	    }
	}
	
	public void printFooBarMain(int i) {
		FooBar fb = new FooBar(i);
		Thread foo = new Thread(() -> {
            try {
                fb.foo(() -> System.out.print("foo "));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
		Thread bar = new Thread(() -> {
            try {
                fb.bar(() -> System.out.print("bar "));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
		try {
			foo.start();
			bar.start();
			
			foo.join();
			bar.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Q1116
	class ZeroEvenOdd {
	    private int n;
	    
	    public ZeroEvenOdd(int n) {
	        this.n = n;
	    }

	    Semaphore z = new Semaphore(1);
	    Semaphore e = new Semaphore(0);
	    Semaphore o = new Semaphore(0);
		
	    public void zero(IntConsumer printNumber) throws InterruptedException {
	        for(int i=0; i<n;i++) {
	        	z.acquire();
	        	printNumber.accept(0);
	        	if((i&1)==0) {
	        		o.release();
	        	}else {
	        		e.release();
	        	}
	        }
	    }

	    public void even(IntConsumer printNumber) throws InterruptedException {
	        for(int i=2; i<=n; i+=2) {
	        	e.acquire();
	        	printNumber.accept(i);
	        	z.release();
	        }
	    }

	    public void odd(IntConsumer printNumber) throws InterruptedException {
	        for(int i=1; i<=n; i+=2) {
	        	o.acquire();
	        	printNumber.accept(i);
	        	z.release();
	        }
	    }
	}	
	
	class IntConsumer {
		public void accept(int i) {
			System.out.print(i+" ");
		}
	}
	
	public void printZeroEvenOddMain(int i) {
		IntConsumer ic = new IntConsumer();
		ZeroEvenOdd zeo = new ZeroEvenOdd(i);
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					zeo.zero(ic);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					zeo.odd(ic);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					zeo.even(ic);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		try {
			t1.start();
			t2.start();
			t3.start();
			
			t1.join();
			t2.join();
			t3.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	//Q1117 
	class H2O {

		Semaphore olock = new Semaphore(1);
		Semaphore hlock = new Semaphore(2);
		CyclicBarrier barrier = new CyclicBarrier(3, new Runnable() {
			@Override
			public void run() {
				System.out.print(" ");
				olock.release();
				hlock.release(2);
			}
		});
		
	    public H2O() {
	        
	    }

	    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
	    	hlock.acquire();
	        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
	        releaseHydrogen.run();
	        try {
				barrier.await();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
	    }

	    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
	        olock.acquire();
	        // releaseOxygen.run() outputs "O". Do not change or remove this line.
			releaseOxygen.run();
			try {
				barrier.await();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
	    }
	}
	
	public void BuildingH2OMain(String s) {
		char[] clist = s.toCharArray();
		H2O h2o = new H2O();
		Thread[] tlist = new Thread[clist.length];
		Random random = new Random();
		
		for(int i = 0; i<clist.length; i++) {
			if (clist[i] =='H') {
				Thread h = new Thread(() -> {
		            try {
		            	Thread.sleep(random.nextInt(3));
		            	h2o.hydrogen(() -> System.out.print("H"));
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        });
				tlist[i] = h;
			} else if (clist[i] == 'O') {
				Thread o = new Thread(() -> {
		            try {
		            	Thread.sleep(random.nextInt(3));
		            	h2o.oxygen(() -> System.out.print("O"));
		            } catch (InterruptedException e) {
		                e.printStackTrace();
		            }
		        });
				tlist[i] = o;
			}
		}
		
		for(int i = 0; i<tlist.length; i++) {
			tlist[i].start();
		}
		
		try {
			for(int i = 0; i<tlist.length; i++) {
				tlist[i].join();
			}
		} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}
	
	//Q1195
	class FizzBuzz {
	    private int n;
	    private volatile int state = -1;
	    
	    public FizzBuzz(int n) {
	        this.n = n;
	    }

	    // printFizz.run() outputs "fizz".
	    public void fizz(Runnable printFizz) throws InterruptedException {
	    	//repeat waiting for i times
	    	for (int i = 3; i<=n; i+=3) {
	    		//pass to 15 if the number is divisible by both 3 and 5
	    		if (i % 15 == 0) {
	    			 continue;
	    		}
	    		while(state != 3) {
	    			Thread.sleep(1);
	    		}
	    		printFizz.run();
	    		state = -1;
	    	}
	    }

	    // printBuzz.run() outputs "buzz".
	    public void buzz(Runnable printBuzz) throws InterruptedException {
	    	//repeat waiting for i times
	    	for (int i = 5; i<=n; i+=5) {
	    		//pass to 15 if the number is divisible by both 3 and 5
	    		if (i % 15 == 0) {
	    			 continue;
	    		}
	    		while(state != 5) {
	    			Thread.sleep(1);
	    		}
	    		printBuzz.run();
	    		state = -1;
	    	}
	    }

	    // printFizzBuzz.run() outputs "fizzbuzz".
	    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
	    	for (int i = 15; i<=n; i+=15) {
	    		while(state != 15) {
	    			Thread.sleep(1);
	    		}
	    		printFizzBuzz.run();
	    		state = -1;
	    	}
	    }

	    // printNumber.accept(x) outputs "x", where x is an integer.
	    public void number(IntConsumer printNumber) throws InterruptedException {
	    	for (int i = 1; i<=n; i++) {
	    		while(state != -1) {
	    			Thread.sleep(1);
	    		}
	    		if (i % 3 == 0 && i % 5 != 0) {
	    			state = 3;
	    		} else if (i % 3 != 0 && i % 5 == 0) {
	    			state = 5;
	    		} else if (i % 3 == 0 && i % 5 == 0) {
	    			state = 15;
	    		} else {
	    			printNumber.accept(i);
	    		}
	    	}
	    }
	}
	
	public void FizzBuzzMain(int i) {
		FizzBuzz fb = new FizzBuzz(i);
		IntConsumer ic = new IntConsumer();
		Thread t1 = new Thread(() -> {
            try {
            	fb.fizz(() -> System.out.print("fizz "));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
		Thread t2 = new Thread(() -> {
            try {
            	fb.buzz(() -> System.out.print("buzz "));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
		Thread t3 = new Thread(() -> {
            try {
            	fb.fizzbuzz(() -> System.out.print("fizzbuzz "));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
		Thread t4 = new Thread(() -> {
            try {
            	fb.number(ic);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
		
		try {
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			
			t1.join();
			t2.join();
			t3.join();
			t4.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//Q1226
	class DiningPhilosophers {

	    public DiningPhilosophers() {
	        
	    }

	    // call the run() method of any runnable to execute its code
	    public void wantsToEat(int philosopher,
	                           Runnable pickLeftFork,
	                           Runnable pickRightFork,
	                           Runnable eat,
	                           Runnable putLeftFork,
	                           Runnable putRightFork) throws InterruptedException {
	        
	    }
	}
	
	
	//Automicity Test
	class AutomicityTest {
		public int count = 0;
        public volatile int countVolatile = 0;
        public AtomicInteger atomicInteger = new AtomicInteger(0);

        public void increase() {
                count++;
                countVolatile++;
                atomicInteger.incrementAndGet();
        }
	}
	
	public void automicityTest() {
		AutomicityTest test = new AutomicityTest();
        Thread[] tlist = new Thread[10];
        for(int i=0; i<tlist.length; i++) {
                Thread t = new Thread(() -> {
                     for(int j=0; j<1000; j++){
                        test.increase();
                     };
                });
                tlist[i] = t;
                tlist[i].start();
        }
        try {
        	for(int i = 0; i<tlist.length; i++) {
        		tlist[i].join();
        	}
        } catch (InterruptedException e) {
        	e.printStackTrace();
        }
        System.out.println(test.count);
        System.out.println(test.countVolatile);
        System.out.println(test.atomicInteger.get());
	}
	
    public static void main(String[] args) {
    	MultithreadSolution m = new MultithreadSolution();
//    	m.printInOrderMain();
//    	m.printZeroEvenOddMain(4);
//    	m.printFooBarMain(4);
//    	m.BuildingH2OMain("HHOOH");
//    	m.FizzBuzzMain(16);
    	m.automicityTest();
	}

}
