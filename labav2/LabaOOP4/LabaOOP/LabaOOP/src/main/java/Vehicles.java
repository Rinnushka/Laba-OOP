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
        PrintWriter pw = new PrintWriter(out);
        if (Car.class.equals(vehicle.getClass())) {
           pw.println(1);
        } else if (Motorcycle.class.equals(vehicle.getClass())) {
            pw.println(2);
        }
        pw.println(vehicle.getBrand());
        pw.println(vehicle.getModelsSize());
        String[] names = vehicle.getAllModelsNames();
        for (String name : names) {
            pw.println(name);
            pw.println(vehicle.getModelPrice(name));

        }
        pw.flush();
    }


    // метод чтения из символьного потока
    public static IVehicle readVehicle(Reader in) throws DuplicateModelNameException, NoSuchModelNameException, IOException {
        BufferedReader bufferedReader = new BufferedReader(in);
        IVehicle vehicle = null;
        int type = Integer.parseInt(bufferedReader.readLine());
        String brand = bufferedReader.readLine();
        int modelsSize = Integer.parseInt(bufferedReader.readLine());
        switch (type){
            case 1: vehicle = new Car(brand,modelsSize); break;
            case 2: vehicle = new Motorcycle(brand,modelsSize);break;
            default: return null;
        }
        String model;
        double price;
        for (int i = 0; i < modelsSize; i++) {
            model = bufferedReader.readLine();
            price = Double.parseDouble(bufferedReader.readLine());
            try {
                vehicle.setModelName(vehicle.getAllModelsNames()[i], model);
            }
            catch (DuplicateModelNameException ignored){}
            vehicle.setModelPrice(vehicle.getAllModelsNames()[i], price);
        }
        return vehicle;
    }

}

