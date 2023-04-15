import exceptions.DuplicateModelNameException;
import exceptions.ModelPriceOutOfBoundsException;
import exceptions.NoSuchModelNameException;
import interfaces.IVehicle;


import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws DuplicateModelNameException {
        IVehicle cars = new Car("Brand", 2);
        System.out.println(Arrays.toString(cars.getAllModelsNames()));
        System.out.println(Arrays.toString(cars.getAllModelsPrices()));

        try {
            cars.setModelName("Model [0]", "Toyota");
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
            cars.deleteModel("Suzuki");
            cars.deleteModel("Model [1]");
        } catch (NoSuchModelNameException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(cars.getAllModelsNames()));
        System.out.println(Arrays.toString(cars.getAllModelsPrices()));
    }
}
