package main.lab2;

import main.lab2.exceptions.NoSuchModelNameException;
import main.lab2.interfaces.ITransport;

import java.util.HashMap;

public class PersonalTransport{
    public static double GetAveragePrice(ITransport PersonalTransport) { //функция возврата средней цены у транспорта моделей
        if (PersonalTransport.GetModelsSize() == 0)
            return 0;
        double sum = 0;
        for (double price :
                PersonalTransport.GetArrayModelsPrice()) { //складываем все цены в массиве моделей
            sum += price;
        }
        return sum / PersonalTransport.GetModelsSize();
    }

    public static HashMap<ITransport,Double> GetPersonalTransportAveragePrices(ITransport[] PersonalTransports) {
        HashMap<ITransport, Double> averagePriceByPersonalTransport = new HashMap<>();
        for (ITransport personalTransport :
                PersonalTransports) {
            averagePriceByPersonalTransport.put(personalTransport, PersonalTransport.GetAveragePrice(personalTransport));
        }
        return averagePriceByPersonalTransport;
    }

    public static void WritePersonalTransport(ITransport PersonalTransport){ //пишет в консоль имена всех моделей транспортного средства и их цены
        System.out.println(PersonalTransport.GetBrand());
        try {
            for (String modelName : PersonalTransport.GetArrayModelsName())
                System.out.println("model: " + modelName + " price: " + PersonalTransport.GetPriceModelByName(modelName));
        } catch (NoSuchModelNameException e) {
            e.printStackTrace();
        }
    }

}

