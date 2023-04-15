
import exceptions.NoSuchModelNameException;
import interfaces.IVehicle;

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
            for (int i = 0; i < names.length; i ++)
            System.out.println("model: " + names [i] + " price: " +prices [i] );
    }
}

