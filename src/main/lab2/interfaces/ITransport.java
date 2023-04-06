package main.lab2.interfaces;

import main.lab2.exceptions.DuplicateModelNameException;
import main.lab2.exceptions.NoSuchModelNameException;

public interface ITransport { // Общее поведение для нескольких классов
    void SetBrand(String brand);
    void SetModelName(String modelName, String newName) throws NoSuchModelNameException, DuplicateModelNameException;
    void SetModelPrice(String modelName, double price) throws NoSuchModelNameException;
    void AddNewModelToArray(String modelName, double price) throws DuplicateModelNameException;
    void DeleteModelFromArray(String modelName) throws NoSuchModelNameException;
    String GetBrand();

    int GetModelsSize();
    double GetPriceModelByName(String modelName) throws NoSuchModelNameException;

    String[] GetArrayModelsName();
    double[] GetArrayModelsPrice();
}
