import exceptions.DuplicateModelNameException;
import exceptions.NoSuchModelNameException;
import interfaces.IVehicle;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Vehicles {

    public  static IVehicle createVehicle(String mark, int size, IVehicle v) throws Exception {
        try {
            //получаем объект конструктора
            Constructor<? extends IVehicle> constructor = v.getClass().getConstructor(String.class, Integer.TYPE);
            //создаем объект
            return constructor.newInstance(mark, size);
        }
        catch(NoSuchMethodException ex ){
            return null;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    //Аргемент переменной длинны это ... ,в самом методе работает как массив, подавать можно или массив или элементы поочереди
    public static double getMeans(IVehicle ... transports){
        double sum = 0;
        for (int i =0;i< transports.length; i++){
            sum += getAveragePrice(transports[i]);
        }
        return sum/transports.length;
    }

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
    public static void outputVehicle(IVehicle vehicle, OutputStream out) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(out);

        //записываем тип транспортного средвства (1-машина 2-мотоцикл)
        if (Car.class.equals(vehicle.getClass())) {
            dataOutputStream.writeInt(1);
        } else if (Motorcycle.class.equals(vehicle.getClass())) {
            dataOutputStream.writeInt(2);
        }

        byte[] name = vehicle.getBrand().getBytes();
        dataOutputStream.writeInt(name.length); //записываем количество байт в брэнде
        for (byte b : name) dataOutputStream.writeByte(b); //записываем брэнд побайтово
        int count = vehicle.getModelsSize();
        dataOutputStream.writeInt(count);
        String[] nameList = vehicle.getAllModelsNames();
        double[] priceList = vehicle.getAllModelsPrices();
        for(int i = 0; i< count;i++){
            name = nameList[i].getBytes();
            dataOutputStream.writeInt(name.length); //записываем количество байт в имени модели
            for (byte b : name) dataOutputStream.writeByte(b);//записываем модель поабйтово
            dataOutputStream.writeDouble(priceList[i]);
        }
    }

    // метод чтения из байтового потока
    //считывает в таком же порядке как и записывали
    public static IVehicle inputVehicle(InputStream in) throws IOException, ClassNotFoundException, DuplicateModelNameException, NoSuchModelNameException {
        DataInputStream inputStream = new DataInputStream(in);
        IVehicle vehicle = null;
        int len;
        byte[] bytes;
        int type = inputStream.readInt(); //считываем тип транспортного средтва

        len = inputStream.readInt();//считывает количество байт в брэнде
        bytes = new byte[len];
        for(int i = 0; i < len;i++){
            bytes[i] = inputStream.readByte(); //считываем брэнд
        }
        String brand = new String(bytes);

        int modelsCount = inputStream.readInt(); //считываем количество транспортных средств
        switch (type){
            case 1: vehicle = new Car(brand,modelsCount); break;
            case 2: vehicle = new Motorcycle(brand,modelsCount);break;
            default: return null;
        }
        double price;
        String model;
        for(int i = 0; i< modelsCount; i++){
            len = inputStream.readInt(); //считываем количество байт в модели
            bytes = new byte[len];
            for(int j = 0; j< len; j++) bytes[j] = inputStream.readByte(); //считываем модель
            model = new String(bytes);
            price = inputStream.readDouble(); //считываем цену

            try {
                vehicle.setModelName(vehicle.getAllModelsNames()[i], model);
            }
            catch (DuplicateModelNameException ignored){}
            vehicle.setModelPrice(model,price);
        }
        return vehicle;
    }

    // метод записи в символьный поток
    public static void writeVehicle(IVehicle vehicle, Writer out) throws NoSuchModelNameException {
            PrintWriter print = new PrintWriter(out);
            print.printf("%s%n",vehicle.getClass());
            print.printf("%s%n",vehicle.getBrand());
            print.printf("%s%n",vehicle.getModelsSize());
            for(int i =0; i<vehicle.getModelsSize();i++){
                print.printf("%s%n",vehicle.getAllModelsNames()[i]);
                print.printf("%s%n",vehicle.getAllModelsPrices()[i]);
            }
            print.flush();
        }



    // метод чтения из символьного потока
    public static IVehicle readVehicle(Reader in) throws DuplicateModelNameException, NoSuchModelNameException, IOException {
        IVehicle trans = null;
        Scanner buff = new Scanner(in);
        String type = buff.nextLine();
        String mark = buff.nextLine();
        switch (type) {
            case "class Car":
                trans = new Car(mark, 0);
                break;
            case "class Motorcycle":
                trans = new Motorcycle(mark, 0);
                break;
            case "class Scuter":
                trans = new Scuter(mark, 0);
                break;
            case "class Kvaadrocircle":
                trans = new Kvaadrocircle(mark, 0);
                break;
            case "class Moped":
                trans = new Moped(mark, 0);
                break;
        }
        int i = Integer.parseInt(buff.nextLine());
        String name;
        for(int j =0;j<i; j++){
            name = buff.nextLine();
            trans.addModel(name, Double.parseDouble(buff.nextLine()));
        }
        return trans;
    }

}

