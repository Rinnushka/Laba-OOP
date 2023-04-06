import exceptions.DuplicateModelNameException;
import exceptions.NoSuchModelNameException;
import interfaces.IVehicle;

import java.io.*;
import java.util.Scanner;

public class Vehicles {
    public static double getAveragePrice(IVehicle vehicle) {
        if (vehicle.getModelsSize() == 0)
            return 0;
        double sum = 0;
        for (double price :
                vehicle.getAllModelsPrices()) {
            sum += price;
        }
        return sum / vehicle.getModelsSize();
    }

    public static void printPriceList(IVehicle vehicle) throws NoSuchModelNameException {
        String[] names = vehicle.getAllModelsNames();
        double[] prices = vehicle.getAllModelsPrices();
        for (int i = 0; i < names.length; i++)
            System.out.println("model: " + names[i] + " price: " + prices[i]);
    }

    // метод записи информации в байтовый поток
    public static void outputVehicle(Car car, OutputStream out) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(car);
    }

    // метод чтения из байтового потока
    public static Car inputVehicle(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(in);
        return (Car) ois.readObject();
    }

    // метод записи в символьный поток
    public static void writeVehicle(Car car, Writer out) throws NoSuchModelNameException {
        PrintWriter pw = new PrintWriter(out);
        pw.println(car.getBrand());
        pw.println(car.getModelsSize());
        String[] names = car.getAllModelsNames();
        for (String name : names) {
            pw.println(name + "-" + car.getModelPrice(name));
        }
        pw.close();
        //?Закрывать метод нужно в мейне
    }
    //?Написать методы байтового чтения и записи

    // метод чтения из символьного потока
    public static Car readVehicle(Reader in) throws DuplicateModelNameException, NoSuchModelNameException {
        Scanner scanner = new Scanner(in);
        //?Класс должен быть как в задании
        String brand = scanner.nextLine();
        int modelsSize = Integer.parseInt(scanner.nextLine());
        Car car = new Car(brand);
        //?За собой нужно тащить имя класса
        for (int i = 0; i < modelsSize; i++) {
            String nextModel = scanner.nextLine();
            String[] modelWithPrice = nextModel.split("-");
            car.addModel(modelWithPrice[0], Double.parseDouble(modelWithPrice[1]));
        }
        return car;
    }

}

