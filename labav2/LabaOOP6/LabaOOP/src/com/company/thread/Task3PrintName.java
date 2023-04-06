package com.company.thread;

import com.company.interfaces.IVehicle;

import java.util.concurrent.locks.ReentrantLock;

public class Task3PrintName  implements  Runnable{
    private IVehicle v;
    private ReentrantLock r;
    public Task3PrintName(IVehicle t, ReentrantLock r){
        v = t;
        this.r = r;
    }
    public void run(){
        r.lock();
        try {
            for (int i = 0; i < v.getModelsSize(); i++)
                System.out.println(v.getAllModelsNames()[i]);
        }
        finally {
            r.unlock();
        }

    }
}
