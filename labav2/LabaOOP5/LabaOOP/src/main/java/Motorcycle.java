
import exceptions.DuplicateModelNameException;
import exceptions.ModelPriceOutOfBoundsException;
import exceptions.NoSuchModelNameException;
import interfaces.IVehicle;

import java.io.Serializable;
import java.util.Arrays;


public class Motorcycle implements IVehicle {


    private class Model implements Serializable,Cloneable {
        private String name;
        private double price;
        private Model prev = null;
        private Model next = null;

        Model(Model next, Model prev, String name, double price) {
            this.name = name;
            this.price = price;
            this.prev = prev;
            this.next = next;
        }

        public  Model clone(){
            try{
                Model mod = (Model)super.clone();
                mod.price = price;
                mod.name = name;
                return mod;
            }
            catch(CloneNotSupportedException c){
                return null;
            }

        }

        public String toString() {
            return "model: " + name + " price: " + price;
        }

        Model(String name, double price) {
            this(null, null, name, price);
        }

        double getPrice() {
            return this.price;
        }

        void setPrice(double price) {
            if (price < 0)
                throw new ModelPriceOutOfBoundsException("Model price must be greater than zero!");
            else
                this.price = price;
        }

        public String getName() {
            return this.name;
        }

        void setName(String name) {
            this.name = name;
        }
    }


    private Model head = new Model("Nissan", 100);

    {
        head.prev = head;
        head.next = head;
    }

    private String brand;
    private int size;

    public Object clone() {
        try {
            Motorcycle moto = (Motorcycle) super.clone();
            moto.head = head.clone();  //копируем голову
            Model p = head.next;
            Model q = moto.head;
            while (p != head) {
                q.next = p.clone();  //привязываем следующий элемент
                q.next.prev = q;  //даем следующему элементу ссылку на нынешний
                q = q.next;
                p = p.next;
            }
            q.next = moto.head; //Привязываем все к голове
            moto.head.prev = q;
            return moto;
        } catch (CloneNotSupportedException c) {
            return null;
        }
    }

    @Override
    public int hashCode(){
        int code;
        code = brand.hashCode();
        //домножается на любое простое нечетное число для уменьшения вероятности коллизии(колизия =2 разных объекта имеют одинаковый хэшкод)
        code = code * 3 + getClass().getName().hashCode();
        code = code*3+Arrays.hashCode(getAllModelsNames());
        code = code*3+Arrays.hashCode(getAllModelsPrices());
        return code;
    }

    @Override
    public  String toString(){
        StringBuffer buff = new StringBuffer();
        buff.append("Тип - Мотоцикл").append("\n").append("Брэнд - ").append(brand).append("\n");
        Model p = head;
        for(int i =0; i<size;i++){
            buff.append("Модель ").append(p.getName()).append(" Цена ").append(p.getPrice()).append("\n");
            p=p.next;
        }
        return buff.toString();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof Motorcycle) {
            Motorcycle ts = (Motorcycle) obj;
            if (!ts.getBrand().equals(getBrand())) {
                return false;
            }
            if (ts.getModelsSize() != getModelsSize())
            {
                return false;
            }

            Model p = head;
            Model q = ts.head;
            while (p != head.prev && q != ts.head.prev) {
                if (!q.name.equals(p.name) || q.price != p.price) {

                    return false;
                }
                p = p.next;
                q = q.next;
            }
            return true;
        }
        return false;
    }



    public void setModelsSize(int size) {
        this.size = size;
    }

    public int getModelsSize() {
        return size;
    }


    public Motorcycle(String brand) {
        this.brand = brand;
    }

    public Motorcycle(String brand, int size) {
        this(brand);
        for (int i = 0; i < size; i++) {
            try {
                addModel(Integer.toString(i), i + 1);
            } catch (DuplicateModelNameException e) {
                e.printStackTrace();
            }
        }
    }


    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return this.brand;
    }


    public void setModelName(String modelName, String newName) throws NoSuchModelNameException, DuplicateModelNameException {
        Model model = findModelByName(modelName);
        if (isModelExists(newName))
            throw new DuplicateModelNameException(newName);
        model.setName(newName);
    }


    public String[] getAllModelsNames() {
        String[] names = new String[size];
        if (!isEmpty()) {
            Model node = this.head;
            for (int i = 0; i < size; node = node.next, i++)
                names[i] = node.name;
        }
        return names;
    }

    public double getModelPrice(String modelName) throws NoSuchModelNameException {
        Model model = findModelByName(modelName);
        return model.getPrice();
    }


    public void setModelPrice(String modelName, double price) throws NoSuchModelNameException {
        Model model = findModelByName(modelName);
        model.setPrice(price);
    }


    public double[] getAllModelsPrices() {
        double[] prices = new double[size];
        Model node = this.head;
        for (int i = 0; i < size; node = node.next, i++)
            prices[i] = node.price;
        return prices;
    }

    private Model findModelByName(String modelName) throws NoSuchModelNameException {
        Model result = getModelByName(modelName);
        if (result == null)
            throw new NoSuchModelNameException(modelName);
        return result;
    }

    private Model getModelByName(String modelName) {
        Model result = null;
        if (!StringUtils.stringIsNullOrEmpty(modelName) && !isEmpty()) {
            if (this.head.name.equals(modelName)) {
                result = this.head;
            } else {
                for (Model node = this.head.next; node != this.head; node = node.next)
                    if (node.name.equals(modelName)) {
                        result = node;
                        break;
                    }
            }
        }
        return result;
    }


    private boolean isModelExists(String modelName) {
        return getModelByName(modelName) != null;
    }

    private boolean isEmpty() {
        return this.size == 0;
    }


    public void addModel(String modelName, double price) throws DuplicateModelNameException {
        if (price <= 0) throw new ModelPriceOutOfBoundsException(modelName);
        if (getModelByName(modelName) == null) {
            Model node = new Model(modelName, price);
            node.prev = head.prev;
            node.next = head;
            head.prev.next = node;
            head.prev = node;
            size++;
        } else {
            throw new DuplicateModelNameException(modelName);
        }
    }


    public void deleteModel(String modelName) throws NoSuchModelNameException {
        if (head == null)
            return;
        Model modelToDelete = findModelByName(modelName);

        if (modelToDelete.next == modelToDelete.prev)
            head = null;
        else {
            modelToDelete.prev.next = modelToDelete.next;
            modelToDelete.next.prev = modelToDelete.prev;
        }
        size--;
    }
}