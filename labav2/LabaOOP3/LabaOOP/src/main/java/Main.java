import exceptions.DuplicateModelNameException;
import exceptions.NoSuchModelNameException;

import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws DuplicateModelNameException, FileNotFoundException {
        Car cars = new Car("Brand", 2);
        System.out.println(Arrays.toString(cars.getAllModelsNames()));
        System.out.println(Arrays.toString(cars.getAllModelsPrices()));

        try {
            cars.setModelName("Model [0]", "Toyota");
            cars.setModelPrice("Toyota", 500);
            cars.setModelPrice("Model [1]", 500);
        } catch (NoSuchModelNameException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(cars.getAllModelsNames()));
        System.out.println(Arrays.toString(cars.getAllModelsPrices()));

        try {
            cars.addModel("Suzuki", 1000);
        } catch (DuplicateModelNameException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(cars.getAllModelsNames()));
        System.out.println(Arrays.toString(cars.getAllModelsPrices()));

        try {
            cars.addModel("Suzuki", 2000);
        } catch (DuplicateModelNameException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(cars.getAllModelsNames()));
        System.out.println(Arrays.toString(cars.getAllModelsPrices()));

        try {
            cars.deleteModel("Model [1]");
        } catch (NoSuchModelNameException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(cars.getAllModelsNames()));
        System.out.println(Arrays.toString(cars.getAllModelsPrices()));

        System.out.println("-------------------------------");
        System.out.println("            LAB 3");
        System.out.println("-------------------------------");

        String pathByte = "cars.bin";
        String pathSymbol = "cars.txt";

        DataOutputStream dos = new DataOutputStream(new FileOutputStream(pathByte));
        try {
            Vehicles.outputVehicle(cars, dos);
            dos.close();
            System.out.println(cars.getBrand()+" was written by DataOutputStream!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        DataInputStream dis = new DataInputStream(new FileInputStream(pathByte));
        try {
            Car car = Vehicles.inputVehicle(dis);
            System.out.println("Car data by DataInputStream:");
            System.out.println(Arrays.toString(car.getAllModelsNames()));
            System.out.println(Arrays.toString(car.getAllModelsPrices()));
            dis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("-------------------------------");
        try {
            FileWriter writer = new FileWriter(pathSymbol);
            Vehicles.writeVehicle(cars, writer);
            writer.close();
            System.out.println(cars.getBrand()+" was written by FileWriter!");
        } catch (IOException | NoSuchModelNameException e) {
            e.printStackTrace();
        }

        Car car = null;
        BufferedReader reader = new BufferedReader(new FileReader(pathSymbol));
        try {
            car = Vehicles.readVehicle(reader);
            System.out.println("Car data by BufferedReader:");
            System.out.println(Arrays.toString(car.getAllModelsNames()));
            System.out.println(Arrays.toString(car.getAllModelsPrices()));
            reader.close();
        } catch (NoSuchModelNameException | IOException e) {
            e.printStackTrace();
        }

        System.out.println("-------------------------------");

        if (car != null) {
            String filename = "serializable.bin";

            try {
                FileOutputStream file = new FileOutputStream(filename);
                ObjectOutputStream out = new ObjectOutputStream(file);
                out.writeObject(car);
                out.close();
                file.close();
                System.out.println("Car has been serialized");
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            Car car1 = null;
            try {
                FileInputStream file = new FileInputStream(filename);
                ObjectInputStream in = new ObjectInputStream(file);
                car1 = (Car) in.readObject();
                in.close();
                file.close();
                System.out.println("Car1 has been deserialized");
                System.out.println("Brand: " + car1.getBrand());
                System.out.println("Model's size: " + car1.getModelsSize());
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }

    }
}
//Дописать тесты на System.in System.out только с символьной парой