import exceptions.DuplicateModelNameException;
import exceptions.ModelPriceOutOfBoundsException;
import exceptions.NoSuchModelNameException;
import interfaces.IVehicle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Moped implements IVehicle {
    private String marka;
    private LinkedList<Model> model;
    int size;
    public Moped(String marka, int count ){
        model = new LinkedList<>();
        this.marka = marka;
        size = count;
        for(int i =0;i<count;i++){
            model.add(new Model("Unknown"+i, 0.0));
        }
    }

    public Object clone(){
        try{
            Moped kv = (Moped) super.clone();
            kv.model = (LinkedList<Model>) model.clone();
            kv.model.clear();
            for(int i =0; i<model.size(); i++){
                kv.model.add(model.get(i).clone());
            }
            return kv;
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
        if(obj instanceof Moped){
            Moped kvadrocircle = (Moped) obj;//получаем доступ к полям класса
            if(kvadrocircle.marka.equals(marka)){
                if(kvadrocircle.model.size() == model.size()){
                    for(int i = 0; i<model.size();i++){
                        if(!kvadrocircle.getAllModelsNames()[i].equals(getAllModelsNames()[i])||kvadrocircle.getAllModelsPrices()[i] != getAllModelsPrices()[i]){
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
        buff.append("Тип - Мопед").append("\n").append("Marka: ").append(marka).append("\n");
        for(int i =0; i<model.size();i++){
            buff.append(" модель ").append(getAllModelsNames()[i]).append(" цена ").append(getAllModelsPrices()[i]).append("\n");
        }
        return buff.toString();
    }
    public int getModelsSize(){
        return model.size();
    }
    public void deleteModel(String nameModel)throws NoSuchModelNameException {
        int pos=-1;
        int i =0;
        while(i<model.size()&&!model.get(i).getNameModel().equals(nameModel)){
            i++;
        }
        if(model.get(i).getNameModel().equals(nameModel)){
            pos = i;
        }
        if(pos==-1){
            throw new NoSuchModelNameException("Модель не найдена");
        }
        model.remove(pos);
    }
    public void addModel(String nameNewModel, double priceNewModel)throws DuplicateModelNameException {
        if(priceNewModel<0){
            throw new ModelPriceOutOfBoundsException("Недопустимая цена модели!");}
        int i =0;
        while(i<model.size() ){
            if(model.get(i).getNameModel().equals(nameNewModel)){
                throw new DuplicateModelNameException("Модель "+nameNewModel+" уже существует!");
            }
            i++;
        }
        if(i==model.size()){
            model.add(new Moped.Model(nameNewModel,priceNewModel));
        }
    }
    public double[] getAllModelsPrices(){
        double[] massiv = new double[model.size()];
        for(int i = 0; i<model.size(); i++){
            massiv[i]=model.get(i).getPriceModel();
        }
        return massiv;
    }
    public void setModelPrice(String name, double priceNew)throws NoSuchModelNameException{
        if(priceNew<0){
            throw new ModelPriceOutOfBoundsException("Недопустимая цена модели!");
        }
        int a = -1;
        for(int i =0;i<model.size();i++)
        {
            if (model.get(i).getNameModel().equals(name)) a = i;
        }
        if(a != -1)
            model.get(a).setPriceModel(priceNew);
        else throw new NoSuchModelNameException(name);

    }
    public double getModelPrice(String name)throws NoSuchModelNameException {
        double price = Double.NaN;
        boolean bool = true;
        int i = 0;
        while(i<model.size()&&!model.get(i).getNameModel().equals(name)){
            i++;
        }
        if(model.get(i).getNameModel().equals(name)){
            price = model.get(i).getPriceModel();
            bool = false;
        }
        if(bool){
            throw new NoSuchModelNameException("Нет модели с данным именем!");
        }
        return price;
    }
    public String[] getAllModelsNames(){
        String[] massiv = new String[model.size()];
        for(int i = 0; i<model.size(); i++){
            massiv[i]=model.get(i).getNameModel();
        }
        return massiv;
    }
    public void setModelName(String prevName, String newName)throws NoSuchModelNameException, DuplicateModelNameException{

        int a = -1;
        for(int i =0;i<model.size();i++)
        {
            if (model.get(i).getNameModel().equals(prevName)) a = i;
            if (model.get(i).getNameModel().equals(newName)) throw new DuplicateModelNameException(newName);
        }
        if(a != -1)
            model.get(a).setNameModel(newName);
        else throw new NoSuchModelNameException(prevName);
    }
    public void setBrand(String marka){
        this.marka = marka;
    }
    public String getBrand(){
        return marka;
    }
    private class Model implements Serializable,Cloneable {
        private String nameModel;
        private double priceModel;

        private Model(String name, double price) {
            nameModel = name;
            priceModel = price;
        }

        public void setNameModel(String name) {
            nameModel = name;
        }

        public void setPriceModel(double price) {
            priceModel = price;
        }

        public String getNameModel() {
            return nameModel;
        }

        public double getPriceModel() {
            return priceModel;
        }

        public Model clone() {
            try {
                Model mod = (Model) super.clone();
                mod.priceModel = priceModel;
                mod.nameModel = nameModel;
                return mod;
            } catch (CloneNotSupportedException c) {
                return null;
            }
        }
    }
}
