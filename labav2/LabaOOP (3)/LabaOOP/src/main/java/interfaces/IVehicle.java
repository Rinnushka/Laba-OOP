package interfaces;

import exceptions.DuplicateModelNameException;
import exceptions.NoSuchModelNameException;


public interface IVehicle {
    void setBrand(String brand);
    void setModelName(String modelName, String newName) throws NoSuchModelNameException, DuplicateModelNameException;
    void setModelPrice(String modelName, double price) throws NoSuchModelNameException;
    void addModel(String modelName, double price) throws DuplicateModelNameException;
    void deleteModel(String modelName) throws NoSuchModelNameException;

    String getBrand();

    int getModelsSize();
    double getModelPrice(String modelName) throws NoSuchModelNameException;

     String[] getAllModelsNames();


    double[] getAllModelsPrices();
}