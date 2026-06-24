package com.ericzzz.concurrent.juc.automic;

import static java.util.concurrent.ThreadLocalRandom.current;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceExample3 {
    static AtomicReference<DebitCard> debitCard = new AtomicReference<>(new DebitCard("Eric", 0));

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread("T-" + i) {
                @Override
                public void run() {
                    while (true) {
                        final DebitCard dc = debitCard.get();
                        DebitCard newDC = new DebitCard(dc.getAccount(), dc.getAmount() + 10);
                        if (debitCard.compareAndSet(dc, newDC)) {
                            System.out.println(newDC);
                        }
                        try {
                            TimeUnit.MILLISECONDS.sleep(current().nextInt(20));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }
}
