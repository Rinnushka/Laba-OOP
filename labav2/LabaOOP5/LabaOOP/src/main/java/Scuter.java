import exceptions.DuplicateModelNameException;
import exceptions.ModelPriceOutOfBoundsException;
import exceptions.NoSuchModelNameException;
import interfaces.IVehicle;

import java.util.Arrays;
import java.util.HashMap;


//В хэшмапе информация хранится в фармате ключ - значение
//ключи уникальны,  private HashMap<String, Double> map; первый параметр это ключ 2ой значение
//Такой вид хранение ускоряет доступ к элементу по ключу

public class Scuter implements IVehicle {
    private String marka;
    private HashMap<String, Double> map;
    public Scuter(String marka, int count ){
        this.marka = marka;
        map = new HashMap<>();
        for(int i =0;i<count;i++){
            map.put("Unknown"+i,0.0);
        }
    }
    public Object clone(){
        try{
            Scuter scuter = (Scuter) super.clone();
            scuter.map = (HashMap<String, Double>) map.clone();
            return scuter;
        }
        catch(CloneNotSupportedException c){
            return null;
        }
    }
    public int hashCode(){
        return (marka.hashCode()*3 + Arrays.hashCode(getAllModelsNames()) )*3 + Arrays.hashCode(getAllModelsPrices());
    }
    public boolean equals(Object obj){
        if(obj == null){
            return  false;
        }
        if(obj == this){
            return  true;
        }
        if(obj instanceof Scuter){
            Scuter skuter = (Scuter)obj;//получаем доступ к полям класса
            if(skuter.marka.equals(marka)){
                if(skuter.map.size() == map.size()){
                    for(int i = 0; i< map.size(); i++){
                        if(!skuter.getAllModelsNames()[i].equals(getAllModelsNames()[i])||skuter.getAllModelsPrices()[i] != getAllModelsPrices()[i]){
                            return false;
                        }

                    }
                    return true;

                }
            }
        }
        return false;
    }
    public String toString(){
        StringBuffer buff = new StringBuffer();
        buff.append("Тип - скутер"+"\n"+" Marka: ").append(marka).append("\n");
        for(int i = 0; i< map.size(); i++){
            buff.append("Модель ").append(getAllModelsNames()[i]).append(" цена ").append(getAllModelsPrices()[i]).append("\n");
        }
        return buff.toString();
    }
    public int getModelsSize(){
        return map.size();
    }
    public void deleteModel(String nameModel)throws NoSuchModelNameException {
        if(!map.containsKey(nameModel)){
            throw new NoSuchModelNameException("Модель не найдена");
        }
        map.remove(nameModel);
    }
    public void addModel(String nameNewModel, double priceNewModel) throws DuplicateModelNameException, NoSuchModelNameException {
        if(priceNewModel<0){
            throw new ModelPriceOutOfBoundsException("Недопустимая цена модели!");}
        if(map.containsKey(nameNewModel)){
            throw new DuplicateModelNameException("Модель "+nameNewModel+" уже существует!");
        }
            map.put(nameNewModel,priceNewModel);

    }
    public double[] getAllModelsPrices(){
        double[] mas = new double[map.size()];
        int i = 0;
        for (double prices : map.values())
        {
            mas[i] = prices;
            i++;
        }
        return mas;
    }
    public void setModelPrice(String name, double priceNew)throws NoSuchModelNameException{
        if(priceNew<0){
            throw new ModelPriceOutOfBoundsException("Недопустимая цена модели!");
        }
        if(!map.containsKey(name)){
            throw new NoSuchModelNameException("Нет модели с данным именем!");
        }
        map.put(name,priceNew);

    }
    public double getModelPrice(String name)throws NoSuchModelNameException {
        if(!map.containsKey(name)){
            throw new NoSuchModelNameException("Нет модели с данным именем!");
        }
        return map.get(name);
    }
    public String[] getAllModelsNames(){
        String[] mas = new String[map.size()];
        int i = 0;
        for (String marks : map.keySet())
        {
            mas[i] = marks;
            i++;
        }
        return mas;
    }
    public void setModelName(String prevName, String newName)throws NoSuchModelNameException, DuplicateModelNameException{

        if(map.containsKey(newName)){
            throw new DuplicateModelNameException("Модель "+newName+" уже существует!");
        }

        if(!map.containsKey(prevName)){
            throw new NoSuchModelNameException("Модель "+prevName+" не найдена!");
        }
        map.put(newName, map.get(prevName));
        map.remove(prevName);
    }
    public void setBrand(String brand){
        this.marka = brand;
    }
    public String getBrand(){
        return marka;
    }
}
