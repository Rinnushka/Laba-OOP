package main.lab2;

import main.lab2.exceptions.DuplicateModelNameException;
import main.lab2.exceptions.ModelPriceOutOfBoundsException;
import main.lab2.exceptions.NoSuchModelNameException;
import main.lab2.interfaces.ITransport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Car implements ITransport {
    private String brand; // 1 создаем приватное поле хранящее марку
    private Model[] models; // 4 массив моделей

    public Car(String brand, int modelsSize) { // 12 конструктор класса Машина, для создания объекта с переданными параметрами
        this.brand = brand;
        this.models = new Model[modelsSize];
        for (int i = 0; i < modelsSize; i++) {
            models[i] = new Model(brand + Integer.toString(i), 123); // заполняем брендом+порядковым его номером и ценой равной 123
        }
    }

    public String GetBrand() { // 2 создаем публичный метод получения марки
        return this.brand; //функция отдачи марки текущей сущности
    }

    public void SetBrand(String NewBrand) { // 3 создаем метод модификации без получения
        this.brand = NewBrand; //присваиваем новое имя
    }

    public double GetModelPrice(String modelName) throws NoSuchModelNameException {
        Model model = FindModelByName(modelName);
        return model.GetModelPrice();
    }

    public void SetModelPrice(String modelName, double price) throws NoSuchModelNameException {
        Model model = FindModelByName(modelName);
        model.SetModelPrice(price);
    }

    public void SetModelName(String modelName, String newName) throws DuplicateModelNameException, NoSuchModelNameException { //метод модифкации цены по ее названию
        Model model = FindModelByName(modelName);
        if (GetModelsSize() != 0 && IndexOfModel(newName) != -1)
            throw new DuplicateModelNameException(newName);
        model.SetModelName(newName);
    }

    public String[] GetArrayModelsName() { // 6 получаем массив названий моделей
        String[] modelsName = new String[models.length]; //создаем массив со списком имен
        for (int i = 0; i < models.length; i++) { // столько же раз сколько и моделей мы добавляем названия моделей
            modelsName[i] = models[i].GetModelName(); // текущему индексу массива названий присваиваем название модели с таким же индексом
        }
        return modelsName; //возвращаем массив названий моделей
    }

    public double GetPriceModelByName(String nameOfModel) { // 7 Получение цены по имени модели
        double price = 0; // изначально возвращаемая цена = 0
        for (int i = 0; i < models.length; i++) { // столько же раз сколько и моделей мы ищем модель с нужным именем
            if (models[i].GetModelName() == nameOfModel) { // если у модели нужное имя то
                price = models[i].GetModelPrice(); // мы присваиваем нашему ответу её цену
            }
        }
        return price; // возвращаем цену, если не нашли модель с таким именем возвращаем 0
    }

    public double[] GetArrayModelsPrice() { // 8 получаем массив цен моделей
        double[] modelsPrice = new double[models.length]; //создаем переменную со списком цен
        for (int i = 0; i < models.length; i++) { // столько же раз сколько и моделей мы добавляем цен моделей
            modelsPrice[i] = models[i].GetModelPrice(); // текущему индексу массива цен присваиваем цену модели с таким же индексом
        }
        return modelsPrice; //возвращаем массив цен моделей
    }

    public void AddNewModelToArray(String modelName, double price) throws DuplicateModelNameException { // 9 добавление новой модели в уже существующий массив моделей
        if (GetModelsSize() != 0 && IndexOfModel(modelName) != -1)
            throw new DuplicateModelNameException(modelName);
        if (price <= 0)
            throw new ModelPriceOutOfBoundsException("Model price must be > 0 ");
        Model[] newArrayWithNewModel = Arrays.copyOf(this.models, this.models.length + 1); // Новый массив это копия старого плюс одна ячейка для новой модели
        newArrayWithNewModel[this.models.length + 1] = new Model(modelName, price); // в эту последнюю ячейку записываем нашу новую модель убрали +1 потому что пиндекс элемента и кол-во элементов начинаются с разных цифр
        this.models = newArrayWithNewModel; // меняем массив старый на новый с добавленной моделью
    }

    public void DeleteModelFromArray(String deleteModelName) throws NoSuchModelNameException{ // 10 удаление модели в уже существующим массиве моделей
        int size = GetModelsSize(); // Получаем размер массива
        if (size == 0)
            return; // Если в массиве 0 элементов просто ничего не делаем, удалять нечего
        int index = IndexOfModel(deleteModelName); // находим под каким номером наша модель в массиву
        if (index == -1)
            throw new NoSuchModelNameException(deleteModelName);
        Model[] newModels = new Model[size - 1]; // создаем новый массив на 1 меньше, ведь одной модели не будет
        System.arraycopy(this.models, 0, newModels, 0, index); // копируем часть массива до номера этой моедли
        if (size != index) {
            System.arraycopy(this.models, index + 1, newModels, index, size - index - 1); // если номер модели был не последний, то копируем оставшуюся часть массива
        }
        this.models = newModels; // в итоге мы скопировали старый массив без этой модели и обновили массив
    }

    public int GetModelsSize() { // 11 получение размера массива моделей
        return this.models.length;
    } //узнаем размер массив моделей

    private Model FindModelByName(String modelName) throws NoSuchModelNameException { //метод поиска модели по имени
        int index = IndexOfModel(modelName);
        if (index == -1)
            throw new NoSuchModelNameException(modelName);
        return models[index];
    }

    public int IndexOfModel(String modelName) { // Получение индекса(порядкового номера) модели в массиве по его имени
        for (int i = 0; i < GetModelsSize(); i++) {
            if (Objects.equals(this.models[i].GetModelName(), modelName)) // если имя совпадает с нужным, то передаем индекс
                return i;
        }
        return -1; // если ничего не нашли возвращаем несуществующий номер -1
    }

    private class Model { // 4 создаем внутренний класс модель
        private String modelName; // 4 поле для названия модели
        private double price; // 4 поле цены

        public Model(String modelNameNew, double priceNew) { //создаем конструктор
            this.modelName = modelNameNew; //внутри конструктора присваиваем поля текущей сущности из параметров конструктора
            this.price = priceNew;
        }

        public void SetModelName(String NewModelName) { // 5 метод модификации названия модели
            this.modelName = NewModelName; //модифицируем имя внутри класса Модель
        }

        public String GetModelName() { //создаем публичный метод получения названия модели
            return this.modelName; //функция отдачи названия модели текущей сущности
        }

        public double GetModelPrice() { //создаем публичный метод получения цены модели
            return this.price; //функция отдачи названия цены текущей сущности
        }

        public void SetModelPrice(double newPrice) { //создаем публичный метод изменения цены модели
            if (price <= 0)
                throw new ModelPriceOutOfBoundsException("Model price must be > 0");
            else
                this.price = price; // отдача цены текущей сущности
        }
    }
}
