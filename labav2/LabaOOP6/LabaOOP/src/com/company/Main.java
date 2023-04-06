package com.company;

import com.company.interfaces.IVehicle;
import com.company.thread.*;

import java.io.*;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("-------------------------------");
        System.out.println("            LAB 6");
        System.out.println("-------------------------------");

        IVehicle car = new Car("Marka1", 10000);

        //todo Задание 1
        //Потоки запускаются методом start
        //Приоритет не означает что сначала выполнится 1 нить а потом вторая, а лишь выделяет больше ресурсов компа на тот или иной поток
        /*
        Task1PrintName name = new Task1PrintName(car);
        Task1PrintPrice price = new Task1PrintPrice(car);
        price.setPriority(Thread.MIN_PRIORITY);
        name.start();
        price.start();*/

        //todo Задание 2
      /*  TransportSynchronizer tr = new TransportSynchronizer(car);
        Task2PrintName name = new Task2PrintName(car,tr);
        Task2PrintPrice price = new Task2PrintPrice(car,tr);
        new Thread(name).start();
        new Thread(price).start();*/


        //todo Задание 3
      /*ReentrantLock re = new ReentrantLock();
        Task3PrintPrice price = new Task3PrintPrice(car,re);
        Task3PrintName name = new Task3PrintName(car,re);
        new Thread(name).start();
        new Thread(price).start();*/


        //todo Задание 4
        //В пуле поток есть выполняющиеся потоки и ожидающиеся
        //Когда слот выполняющегося Становится свободным выбирается СЛУЧАЙНЫЙ из ожидающих
        //Иногда первым вторым выведенным брэндом могут быть не брэнды 1-2 ибо они не успели начать выполнятся до того как в пул попали следующие потоки
       /* IVehicle car2 = new Car("1",0);
        IVehicle car3 = new Car("2",0);
        IVehicle moto = new Car("3",0);
        IVehicle moto2 = new Car("4",0);
        ExecutorService exe = Executors.newFixedThreadPool(2);
        IVehicle[] vMas = {car2,car3,moto,moto2};
        for (IVehicle vMa : vMas) {
            Task4PrintBrand t = new Task4PrintBrand(vMa);
            exe.submit(t);                                                                                                                                              //добавляем в пул
        }
        exe.shutdown();*/

        //todo Задание 5
        ArrayBlockingQueue que = new ArrayBlockingQueue(2);                                                                                                                                                                                            //создаём очередь
        String[] str = {"1.txt", "2.txt", "3.txt", "4.txt", "5.txt"};
        for(int i =0; i<str.length; i++){
            FileReader file = new FileReader(str[i]);
            Task5PrintBrand pr = new Task5PrintBrand(file,que);
            new Thread(pr).start();
        }
        Thread.sleep(100);
        while(que.size()!=0){
            System.out.println(que.take());
        }

































































      /*  //todo Задание 1
        System.out.println("Задание 1");
        //В параметрах командной строки приложения указывается полное имя класса, имя метода,
        // который следует вызвать у класса (метод нестатический) и числовые параметры для этого метода (типа int).
        //для этого в идее надо зайти в Run - edit configuration
        //Car setModelPrice 9999

        //получение класса по имени
        Class<?> clas = Class.forName(args[0]);
        //получение конструктора по типам параметров
        Constructor<?> constr = clas.getConstructor(String.class,Integer.TYPE);
        //создаем новый объект через конструктор
        Object obj = constr.newInstance("Brand1", 3);
        //получение метода по имени и типам параметров параметрам
        Method metod = clas.getMethod(args[1], String.class, Double.TYPE);
        //вызов метода
        metod.invoke(obj, "Model [2]", Double.parseDouble(args[2]));
        System.out.println(obj);

        //todo Задание 2
        System.out.println();
        System.out.println("Задание 2");

        IVehicle v = new Motorcycle("1",1);
        IVehicle tr = Vehicles.createVehicle("mark", 3, v);
        System.out.println(tr);

        //todo Задание 3
        System.out.println();
        System.out.println("Задание 3");

        IVehicle v2 = new Scuter("Sc",2);
        v2.addModel("Model0",100);
        v2.addModel("Model1",999);
        v2.addModel("Model2",2000);
        v2.addModel("Model3",23000);
        v2.addModel("Model4",4000);
        v2.setModelName("Model0","NewModel");
        v2.deleteModel("Model4");
        IVehicle v3 = (IVehicle) v2.clone();
        v3.setModelPrice("Model2",9999999);

        System.out.println(v2);
        System.out.println(v3);


        //todo Задание 4
        System.out.println();
        System.out.println("Задание 4");
        v2 = new Kvaadrocircle("Sc",2);
        v2.addModel("Model0",100);
        v2.addModel("Model1",999);
        v2.addModel("Model2",2000);
        v2.addModel("Model3",23000);
        v2.addModel("Model4",4000);
        v2.setModelName("Model0","NewModel");
        v2.deleteModel("Model4");
        v3 = (IVehicle) v2.clone();
        v3.setModelPrice("Model2",9999999);

        System.out.println(v2);
        System.out.println(v3);



        //todo Задание 5
        //linkedList по факту тот же arrayList отличается только то как они хранят в себе элемент(двусвязные спиоск и массив соответственно) Разница по факту в скорости выполнения нектрых операций
        System.out.println();
        System.out.println("Задание 5");
        v2 = new Moped("Sc",2);
        v2.addModel("Model0",100);
        v2.addModel("Model1",999);
        v2.addModel("Model2",2000);
        v2.addModel("Model3",23000);
        v2.addModel("Model4",4000);
        v2.setModelName("Model0","NewModel");
        v2.deleteModel("Model4");
        v3 = (IVehicle) v2.clone();
        v3.setModelPrice("Model2",9999999);

        System.out.println(v2);
        System.out.println(v3);


        //todo Задание 6
        System.out.println("Задание 6");
        System.out.println("Средняя цена транспортного средства 1");
        System.out.println(Vehicles.getAveragePrice(v2));
        System.out.println("Средняя цена транспортного средства 2");
        System.out.println(Vehicles.getAveragePrice(v3));
        System.out.println("Средняя цена обоих транспортных средств");
        System.out.println(Vehicles.getMeans(v2,v3));


        //todo Задание 7
        System.out.println("Задание 7");


        Vehicles.writeVehicle(v2, new FileWriter("test.txt"));
        v3 =Vehicles.readVehicle(new FileReader("test.txt"));
        System.out.println(v3);

*/



































      /*  IVehicle vehicleCar = new Car("CarBrand", 5);
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
        System.out.println(v2);*/
    }
}

