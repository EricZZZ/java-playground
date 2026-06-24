package com.ericzzz.concurrent.juc.automic;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TryLockExample {
    private final static Object VAL_OBJ = new Object();

    public static void main(String[] args) {
        final TryLock tryLock = new TryLock();
        final List<Object> validation = new ArrayList<>();
        for (int i=0;i<10;i++){
            new Thread(() -> {
                while(true){
                    try{
                        if (tryLock.tryLock()){
                            System.out.println(currentThread()+": get the lock.");
                            if(validation.size() > 1){
                                throw new IllegalStateException("validation failed.");
                            }
                            validation.add(VAL_OBJ);
                            TimeUnit.MILLISECONDS.sleep(current().nextInt(10));
                        }else{
                           TimeUnit.MILLISECONDS.sleep(current().nextInt(10)); 
                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }finally{
                        if (tryLock.release()){
                            System.out.println(currentThread() + ": release the lock.");
                            validation.remove(VAL_OBJ);
                        }
                    }
                }
            }).start();
        }
    }
}
