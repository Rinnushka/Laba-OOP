package com.company.thread;

import com.company.interfaces.IVehicle;

public class Task1PrintPrice extends Thread {
    private IVehicle v;
    public Task1PrintPrice(IVehicle v){
        this.v = v;
    }
    public void run(){
        for(int i=0; i<v.getModelsSize(); i++)
            System.out.println(v.getAllModelsPrices()[i]);
    }
}
