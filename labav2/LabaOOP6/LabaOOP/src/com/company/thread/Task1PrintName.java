package com.company.thread;


import com.company.interfaces.IVehicle;

public class Task1PrintName extends Thread {
    private final IVehicle v;

    public Task1PrintName(IVehicle v) {
        this.v = v;
    }

    public void run() {
        for (int i = 0; i < v.getModelsSize(); i++)
            System.out.println(v.getAllModelsNames()[i]);
    }
}
