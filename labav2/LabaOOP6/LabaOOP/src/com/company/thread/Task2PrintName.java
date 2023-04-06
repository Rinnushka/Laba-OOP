package com.company.thread;

import com.company.interfaces.IVehicle;

public class Task2PrintName implements  Runnable{
    private IVehicle v;
    private TransportSynchronizer sync;
    public  Task2PrintName(IVehicle t, TransportSynchronizer ts){
        v = t;
        sync = ts;
    }
    @Override
    public void run() {
        for(int i=0; i<v.getModelsSize(); i++) {
            try {
                sync.printModel();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
