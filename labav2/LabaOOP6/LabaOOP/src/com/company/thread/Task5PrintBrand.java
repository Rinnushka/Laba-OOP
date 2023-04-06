package com.company.thread;

import com.company.Car;
import com.company.interfaces.IVehicle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

public class Task5PrintBrand implements Runnable{
    private final FileReader file;
    private ArrayBlockingQueue que;
    public Task5PrintBrand(FileReader file, ArrayBlockingQueue que){
        this.file = file;
        this.que = que;
    }
    public void run(){
        try{
            BufferedReader buf = new BufferedReader(file);
            String str = buf.readLine();
            IVehicle car = new Car(str,0);
            que.put(car);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
