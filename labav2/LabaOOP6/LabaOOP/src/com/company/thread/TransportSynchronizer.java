package com.company.thread;

import com.company.interfaces.IVehicle;

public class TransportSynchronizer {
    private IVehicle v;
    private volatile int current = 0;                                                                                                                                               //актуальные не промежуточное
    private Object lock = new Object();
    private boolean set = false;
    public TransportSynchronizer(IVehicle v) {
        this.v = v;
    }

    public double printPrice() throws InterruptedException {
        double val;
        synchronized(lock) {
            double [] p = v.getAllModelsPrices();
            if (!canPrintPrice()) throw new InterruptedException();                                                                                              //проверка на выход за пределы массива
            while (!set)
                lock.wait();
            val = p[current++];
            System.out.println("Print price: " + val);
            set = false;
            lock.notifyAll();
        }
        return val;
    }

    public void printModel() throws InterruptedException {
        synchronized(lock) {
            String [] s = v.getAllModelsNames();
            if (!canPrintModel()) throw new InterruptedException();
            while (set)
                lock.wait();
            System.out.println("Print model: " + s[current]);
            set = true;
            lock.notifyAll();
        }
    }

    public boolean canPrintModel() {
        return current < v.getModelsSize();
    }

    public boolean canPrintPrice() {
        return (!set && current < v.getModelsSize()) || (set && current < v.getModelsSize() - 1);
    }
}

