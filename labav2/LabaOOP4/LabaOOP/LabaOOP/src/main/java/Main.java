import exceptions.DuplicateModelNameException;
import exceptions.NoSuchModelNameException;
import interfaces.IVehicle;

import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws DuplicateModelNameException, FileNotFoundException, NoSuchModelNameException {
        System.out.println("-------------------------------");
        System.out.println("            LAB 4");
        System.out.println("-------------------------------");

        IVehicle vehicleCar = new Car("CarBrand", 5);
        IVehicle vehicleMoto = new Motorcycle("MotoBrand", 7);

        System.out.println("Тест - toString()");
        System.out.println(vehicleCar);
        System.out.println(vehicleMoto);
        System.out.println("--------------------------------");


        System.out.println("Тест equals()");
        System.out.println("Тесты для авто");
        System.out.println("Сравнение 2ух идентисных автомобилей");
        IVehicle car2 = new Car("CarBrand",5);
        System.out.println(vehicleCar.equals(car2));

        System.out.println("Сравнение 2ух одинаковых автомобилей, но у одного удалена 1 модель");
        car2.deleteModel("Model [4]");
        System.out.println(vehicleCar.equals(car2));

        System.out.println("Сравнение 2ух одинаковых автомобилей, но у одного заменены имя модели");
        car2 = new Car("CarBrand",5);
        car2.setModelName("Model [4]","Kia");
        System.out.println(vehicleCar.equals(car2));

        System.out.println("Сравнение 2ух одинаковых автомобилей, но у одного заменены цена модели");
        car2 = new Car("CarBrand",5);
        car2.setModelPrice("Model [4]",444);
        System.out.println(vehicleCar.equals(car2));

        System.out.println("Сравнение с null");
        System.out.println(vehicleCar.equals(null));


        System.out.println("Тесты для мотоцикла");
        System.out.println("Сравнение 2ух идентисных мотоциклов");
        IVehicle moto2 = new Motorcycle("MotoBrand",7);
        System.out.println(vehicleMoto.equals(moto2));

        System.out.println("Сравнение 2ух одинаковых мотоциклов, но у одного удалена 1 модель");
        moto2.deleteModel("4");
        System.out.println(vehicleMoto.equals(moto2));

        System.out.println("Сравнение 2ух одинаковых мотоциклов, но у одного заменены имя модели");
        moto2 = new Motorcycle("MotoBrand",7);
        moto2.setModelName("4","Kia");
        System.out.println(vehicleMoto.equals(moto2));

        System.out.println("Сравнение 2ух одинаковых мотоциклов, но у одного заменены цена модели");
        car2 = new Motorcycle("MotoBrand",7);
        car2.setModelPrice("4",444);
        System.out.println(vehicleMoto.equals(moto2));

        System.out.println("Сравнение с null");
        System.out.println(vehicleMoto.equals(null));


        System.out.println("Сравнение мотоцикла и авто с одинаковыми полями");
        IVehicle t1 = new Car("1",1);
        IVehicle t2 = new Motorcycle("1",1);
        t2.setModelName("Nissan","Model [0]");
        t2.setModelPrice("Model [0]",0);
        System.out.println(t1.equals(t2));
        System.out.println(t2.equals(t1));

        //хэш код обязан быть одинаков у одинаковых объектов, но при этом вероятность совпадения его у разных не равна 0;
        System.out.println("--------------------------------");
        System.out.println("Тест hashCode");
        System.out.println(t1.hashCode());
        System.out.println(t1.hashCode());
        System.out.println(t2.hashCode());
        t2.setModelPrice("Model [0]",3);
        System.out.println(t2.hashCode());


        System.out.println("--------------------------------");
        //Добавленны методы clone как в классы авто и мотоцикла, так и в классы модели
        //Все указанные классы реализуют интерфейс Cloneable
        //Есть 2 типа клонирования глубокое и не глубокое, при не гулобоком клонируется лишь ссылка на объект
        //Для проверки того что наша клонирование глубокое создадим клон объекта и заменим в нем какое либо поле
        //При этом клонируемый объект изменится не должен
        System.out.println("Тест clone");
        IVehicle v = new Car("brand",3);
        System.out.println("Изначальный объект");
        System.out.println(v);
        IVehicle vClone = (IVehicle)v.clone();
        System.out.println("Склонированный объект");
        System.out.println(vClone);
        vClone.setModelName("Model [1]","name");

        System.out.println("Склонированный объект после изменения");
        System.out.println(vClone);
        System.out.println("Изначальный объект после изменения склонированного");
        System.out.println(v);




        System.out.println("Повторим процедуру для мотоцикла");
        IVehicle v2 = new Motorcycle("brand",3);
        System.out.println("Изначальный объект");
        System.out.println(v2);
        IVehicle v2Clone = (IVehicle)v2.clone();
        System.out.println("Склонированный объект");
        System.out.println(v2Clone);
        v2Clone.setModelName("1","name");

        System.out.println("Склонированный объект после изменения");
        System.out.println(v2Clone);
        System.out.println("Изначальный объект после изменения склонированного");
        System.out.println(v2);
    }
}

