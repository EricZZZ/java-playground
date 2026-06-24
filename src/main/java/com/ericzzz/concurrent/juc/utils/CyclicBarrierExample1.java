package com.ericzzz.concurrent.juc.utils;

import static java.util.concurrent.ThreadLocalRandom.current;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CyclicBarrierExample1 {
    public static void main(String[] args) throws InterruptedException {
        final int[] products = getProductsByCategoryId();

        List<ProductPrice> list = Arrays.stream(products).mapToObj(ProductPrice::new).collect(toList());

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(products.length);
        final List<Thread> threadList = new ArrayList<>();
        list.forEach(pp -> {
            Thread thread = new Thread(() -> {
                System.out.println(pp.getProdID() + "start calculate price.");
                try {
                    TimeUnit.SECONDS.sleep(current().nextInt(10));
                    if (pp.prodID % 2 == 0) {
                        pp.setPrice(pp.prodID * 0.9D);
                    } else {
                        pp.setPrice(pp.prodID * 0.71D);
                    }
                    System.out.println(pp.getProdID() + "-> price calculate completed.");
                    
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
            threadList.add(thread);
            thread.start();
        });
        threadList.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("all product price calculate completed.");
        list.forEach(System.out::println);
    }

    private static int[] getProductsByCategoryId() {
        return IntStream.rangeClosed(1, 10).toArray();
    }

    private static class ProductPrice {
        private final int prodID;
        private double price;

        private ProductPrice(int prodID) {
            this(prodID, -1);
        }

        private ProductPrice(int prodID, double price) {
            this.prodID = prodID;
            this.price = price;
        }

        int getProdID() {
            return prodID;
        }

        void setPrice(double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "ProductPrice [prodID=" + prodID + ", price=" + price + "]";
        }

    }
}
