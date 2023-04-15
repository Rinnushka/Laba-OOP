import exceptions.DuplicateModelNameException;
import exceptions.ModelPriceOutOfBoundsException;
import exceptions.NoSuchModelNameException;
import interfaces.IVehicle;


import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws DuplicateModelNameException, NoSuchModelNameException, ModelPriceOutOfBoundsException {
        IVehicle car = new Motorcycle("LADA", 3);


        System.out.println(car.getModelsSize());
        System.out.println(car.getBrand());
        Vehicles.printPriceList(car);

        car.setBrand("Priora");
        System.out.println(car.getBrand());



        car.setModelName("Nissan", "0");
        Vehicles.printPriceList(car);


        car.setModelPrice("Model 00", 670000);
        System.out.println(car.getModelPrice("Model 00"));

        car.deleteModel("0");
        Vehicles.printPriceList(car);

      /*  car.deleteModel("Model [000]");
        System.out.println(Arrays.toString(car.getAllModelsNames()));

        //car.setModelName("Model [00]", "Model [00]");
        //System.out.println(Arrays.toString(car.getAllModelsNames()));

        car.setModelPrice("Model [00]", -1);
        System.out.println(car.getModelPrice("Model [10]"));*/







    }
}
