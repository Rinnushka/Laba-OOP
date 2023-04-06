package main.lab2;

import main.lab2.exceptions.DuplicateModelNameException;
import main.lab2.exceptions.ModelPriceOutOfBoundsException;
import main.lab2.exceptions.NoSuchModelNameException;
import main.lab2.interfaces.ITransport;

import java.util.Arrays;

public class Motorcycle implements ITransport { // аналогично с Car, только на двусвязном циклическом списке вместо массива моделей
    private String brand; // 1 создаем приватное поле хранящее марку
    private Model head = null; // Первая модель из двухсвязного списка мы не задали при старте
    private int size; // размер этого списка
    // Поля со списком моделей тут нету, как в Car - models, все будет реализовано через разные методы дальше
    // у нас есть модели которые знают кто предыдущий и кто следующий, отдельное поле не нужно

    public Motorcycle(String brand2, int modelsSize) { // 12 конструктор класса мотоцикл, для создания объекта с переданными параметрами
        this.brand = brand2;
        for (int i = 0; i < modelsSize; i++) {
            try {
                AddNewModelToArray(brand+Integer.toString(i), i+1); // заполняем брендом+порядковым его номером и ценой
            }
            catch (DuplicateModelNameException e) {
               e.printStackTrace();
            }
        }
    }

    public String GetBrand() { // 2 создаем публичный метод получения марки
        return this.brand; //функция отдачи марки текущей сущности
    }

    public void SetBrand(String NewBrand) { // 3 создаем метод модификации без получения
        this.brand = NewBrand; //присваиваем новое имя
    }

    public int GetModelsSize() {
        return size;
    }

    private Model GetModelByName(String modelName){ // получить модель по имени
        Model result = null;
            if (this.head.GetModelName() == modelName) { // Если наш головной элемент в списке не так называется как надо, то идем дальше
                result = this.head;
            } else {
                for (Model node = this.head.next; node != this.head; node = node.next) // для каждой из модели мы перебираем пока не возвратимся обратно к головному элементу (у нас последний элемент ссылается на первый, т.е. зациклен список)
                    if (node.GetModelName() == modelName) { // если находим нужный элемент возвращаем
                        result = node;
                        break;
                    }
        }
        return result; // если не находим возвращаем пустоту
    }

    public String[] GetArrayModelsName() { // 6 получаем массив названий моделей
        String[] names = new String[size];//создаем массив со списком имен
        if (!IsEmpty()) { // Не пустой ли у нас список проверяем
            Model node = this.head;
            for (int i = 0; i < size; node = node.next, i++)// столько же раз сколько и моделей мы добавляем названия моделей
                names[i] = node.GetModelName();
        }
        return names;
    }

    public double GetPriceModelByName(String nameOfModel) { // 7 Получение цены по имени модели
        Model model = GetModelByName(nameOfModel); // существует существующиий поиск по имени модели
        return model.GetModelPrice(); // возвращаем цену
    }

    public double[] GetArrayModelsPrice() { // 8 получаем массив цен моделей
        double[] prices = new double[size]; // все то же самое как и в GetArrayModelsName, только с ценами
        Model node = this.head;
        for (int i = 0; i < size; node = node.next, i++)
            prices[i] = node.price;
        return prices;
    }

    public void AddNewModelToArray(String newModelName, double price) throws DuplicateModelNameException { // 9 добавление новой модели в уже существующий двусвязный список моделей
        if (IsModelExists(newModelName))
            throw new DuplicateModelNameException(newModelName);
        if (price <= 0)
            throw new ModelPriceOutOfBoundsException("Model price must be greater than zero!");
        Model node = new Model(newModelName, price);
        if (head == null) {
            head = node;
            head.next = node;
            head.prev = node;
        } else {
            node.prev = head.prev;
            node.next = head;
            head.prev.next = node;
            head.prev = node;
        }
        size++;
    }

    public void DeleteModelFromArray(String deleteModelName) throws NoSuchModelNameException { // 10 удаление модели в уже существующим массиве моделей
        if (head == null)
            return;
        Model modelToDelete = GetModelByName(deleteModelName); // у нас список моделей хранит список на предыдущее и на следующее

        if (modelToDelete.next == modelToDelete.prev)
            head = null;
        else {
            modelToDelete.prev.next = modelToDelete.next; // если мы поменяем ссылку на текущий элемент на через одного, то у нас как бы сотрется текущий ненужный
            modelToDelete.next.prev = modelToDelete.prev;
        }
        size--;
    }

    private boolean IsModelExists(String modelName){ // Существует ли модель с таким именем
        return GetModelByName(modelName) != null; // если наш метод поиска по имени вернул не пустой объект то существует
    }

    private boolean IsEmpty() { // Пустой ли наш массив моделей
        return this.size == 0; // если размер = 0 то да
    }

    public void SetModelName(String modelName, String newName) throws NoSuchModelNameException, DuplicateModelNameException {
        Model model = FindModelByName(modelName);
        if (IsModelExists(newName))
            throw new DuplicateModelNameException(newName);
        model.SetModelName(newName);
    }

    public double GetModelPrice(String modelName) throws NoSuchModelNameException {
        Model model = FindModelByName(modelName);
        return model.GetModelPrice();
    }

    public void SetModelPrice(String modelName, double price) throws NoSuchModelNameException {
        Model model = FindModelByName(modelName);
        model.SetModelPrice(price);
    }

    private Model FindModelByName(String modelName) throws NoSuchModelNameException {
        Model result = GetModelByName(modelName);
        if (result == null)
            throw new NoSuchModelNameException(modelName);
        return result;
    }

    public class Model { // 4 создаем внутренний класс модель
        private String modelName; // 4 поле для названия модели
        private double price; // 4 поле цены
        Model prev = null; // предыдущая модель нету, но потом мы зададим
        Model next = null; // следующей модели нету, но потом мы зададим
        private int size; // размер этого двухсвязного списка
        private long lastModified; // храним дату последнего изменения

        public Model(Model next, Model prev, String name, double price) { //создаем конструктор
            this.modelName = name; //внутри конструктора присваиваем поля текущей сущности из параметров конструктора
            this.price = price;
            this.prev = prev;
            this.next = next;
        }

        public Model(String name, double price) { //создаем конструктор, их может быть несколько
            this.modelName = name; //внутри конструктора присваиваем поля текущей сущности из параметров конструктора
            this.price = price;
        }

        public void ChangeModelName(String NewModelName) { // 5 модификация названия модели
            this.modelName = NewModelName; //модифицируем имя внутри класса Модель
        }

        public String GetModelName() { //создаем публичный метод получения названия модели
            return this.modelName; //функция отдачи названия модели текущей сущности
        }

        public double GetModelPrice() { //создаем публичный метод получения цены модели
            return this.price; //функция отдачи названия цены текущей сущности
        }

        public void SetModelPrice(double newPrice){
            if (price <= 0)
                throw new ModelPriceOutOfBoundsException("Model price must be > 0");
            else
                this.price = newPrice;
    }

        public void SetModelName(String newName) {this.modelName = newName;}
    }
}
