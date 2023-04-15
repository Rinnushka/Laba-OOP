import exceptions.DuplicateModelNameException;
import exceptions.ModelPriceOutOfBoundsException;
import exceptions.NoSuchModelNameException;
import interfaces.IVehicle;

import java.util.Arrays;
import java.util.Objects;

public class Car implements IVehicle {
    //поле типа String, хранящее марку автомобиля,
    private String brand;

    public Car(String brand) {
        this(brand, 0);
    }

    private Model[] models;


    public Car(String brand, int modelsSize) {
        this.brand = brand;
        this.models = new Model[modelsSize];
        // Создание моделей
        for (int i = 0; i < modelsSize; i++) {
            models[i] = new Model("Model [" + i + "]", 0);
        }
    }

    //метод для модификации марки автомобиля,
    public void setBrand(String brand) {
        this.brand = brand;
    }


    //метод для получения марки автомобиля,
    public String getBrand() {
        return brand;
    }


    //метод для модификации значения названия модели если не пустой то ищет new
    public void setModelName(String modelName, String newName) throws DuplicateModelNameException, NoSuchModelNameException {
        if (getModelsSize() != 0 && indexOfModel(newName) != -1)
            throw new DuplicateModelNameException(newName);
        Model model = findModelByName(modelName);
        model.setName(newName);
    }

    //метод, возвращающий массив названий всех моделей,
    public String[] getAllModelsNames() {
        String[] allNames = new String[models.length];
        for (int i = 0; i < models.length; i++)
            allNames[i] = models[i].getName();
        return allNames;
    }

    //метод для получения значения цены модели по её названию,
    public double getModelPrice(String modelName) throws NoSuchModelNameException {
        Model model = findModelByName(modelName);
        return model.getPrice();
    }

    //метод для модификации значения цены модели по её названию,
    public void setModelPrice(String modelName, double price) throws NoSuchModelNameException {
        Model model = findModelByName(modelName);
        model.setPrice(price);
    }

    //метод, возвращающий массив значений цен моделей,
    public double[] getAllModelsPrices() {
        double[] prices = new double[models.length];
        for (int i = 0; i < models.length; i++) {
            prices[i] = models[i].getPrice();
        }
        return prices;
    }

    ///метод добавления названия модели и её цены (путем создания нового массива Моделей), использовать метод Arrays.copyOf(),
    public void addModel(String modelName, double price) throws DuplicateModelNameException {
        if (getModelsSize() != 0 && indexOfModel(modelName) != -1)
            throw new DuplicateModelNameException(modelName);
        if (price <= 0)
            throw new ModelPriceOutOfBoundsException("Model price must be greater than zero!");
        int size = getModelsSize();
        this.models = Arrays.copyOf(this.models, size + 1);
        this.models[size] = new Model(modelName, price);
    }

    //метод удаления модели по заданному имени, использовать методы System.arraycopy, Arrays.copyOf(),
    public void deleteModel(String Model) throws NoSuchModelNameException {
        boolean flug = true;
        for (int i = 0; i < models.length; i++)
            if (Objects.equals (models[i].getName(), Model)){
                flug = false;
                System.arraycopy(models, i + 1, models, i, models.length -i-1);
                models=Arrays.copyOf(models, models.length-1);
            }
        if (flug) throw new NoSuchModelNameException(Model);
    }

    // метод для получения размера массива Моделей.
    public int getModelsSize() {
        return models.length;
    }

    // метод поиска моделей по названию
    private Model findModelByName(String modelName) throws NoSuchModelNameException {
        int index = indexOfModel(modelName);
        if (index == -1)
            throw new NoSuchModelNameException(modelName);
        return models[index];
    }

    // метод поиска по индекса модели
    private int indexOfModel(String modelName) {
            for (int i = 0; i < getModelsSize(); i++) {
                if (this.models[i].getName().equals(modelName))
                    return i;
            }
        return -1;
        }


    // метод поиска модели с индексом
    public Model getByIndex(int index) {
        if (index < 0 || index >= getModelsSize())
            throw new IndexOutOfBoundsException();
        return models[index];
    }

// Класс модели с инициализацией названия и цены
    private class Model {
        //поля название модели (уникальное
        private String name;
        //поле цена
        private double price;

        //конструктор
        public Model(String name, double price) {
            this.name = name;
            this.price = price;
        }

    //  для получения цены модели
        public double getPrice() {
            return price;
        }

        // для установки цены
        public void setPrice(double price) {
            if (price <= 0)
                throw new ModelPriceOutOfBoundsException("Model price must be greater than zero!");
            else
                this.price = price;
        }

    // для получения названий модели
        public String getName() {
            return name;
        }

    // для установки названий модели
        public void setName(String name) {
            this.name = name;
        }

    }
}

