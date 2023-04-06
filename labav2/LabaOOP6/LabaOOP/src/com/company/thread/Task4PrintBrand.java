package com.company.thread;

import com.company.interfaces.IVehicle;

public class Task4PrintBrand implements Runnable{
    private IVehicle v;
    public  Task4PrintBrand(IVehicle v){
        this.v = v;
    }
    public  void run(){
        System.out.println(v.getBrand());
    }
}
