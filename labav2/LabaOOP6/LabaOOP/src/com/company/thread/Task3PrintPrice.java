package com.company.thread;

import com.company.interfaces.IVehicle;

import java.util.concurrent.locks.ReentrantLock;

public class Task3PrintPrice implements Runnable{
    private IVehicle v;
    private ReentrantLock r;
    public Task3PrintPrice(IVehicle t, ReentrantLock r){
        v = t;
        this.r = r;
    }
    public void run(){
        r.lock();
        try {
            for (int i = 0; i < v.getModelsSize(); i++)
                System.out.println(v.getAllModelsPrices()[i]);
        }
        finally {
            r.unlock();
        }

    }
}
