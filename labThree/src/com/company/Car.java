package com.company;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;


public class Car implements ITransport, Serializable {
    private String mark;
    private Model[] models;

    @Override
    public String getMark(){
        return mark;
    }
    @Override
    public void setMark(String newMark){
        mark = newMark;
    }


    private class Model implements Serializable{
        private String modelName;
        private Double modelPrice;

        public Model(String modelName, Double modelPrice) {
            this.modelName = modelName;
            this.modelPrice = modelPrice;
        }
        private String getModelName() {
            return modelName;
        }
        private void setModelName(String newModelName) {
            modelName = newModelName;
        }
        private Double getModelPrice() {
            return modelPrice;
        }
        private void setModelPrice(Double newModelPrice) {
            modelPrice = newModelPrice;
        }
    }

    @Override
    public String[] getArrayModelsNames(){
        String[] modelsNames = new String[models.length];
        for (int i = 0; i < models.length; i++){
            Model model = models[i];
            modelsNames[i] = model.getModelName();
        }
        return modelsNames;
    }
    @Override
    public Double[] getArrayModelsPrice(){
        Double[] modelsPrice = new Double[models.length];
        for (int i = 0; i < models.length; i++){
            Model model = models[i];
            modelsPrice[i] = model.getModelPrice();
        }
        return modelsPrice;
    }
    @Override
    //Добавить проверку на дубликат
    public void setModelName(String oldModelName, String newModelName) throws NoSuchModelNameException, DuplicateModelNameException {
        for (int i = 0; i < models.length; i++) {
            Model model = models[i];
            if (model.getModelName().equals(newModelName)) {
                throw new DuplicateModelNameException("model " + newModelName + " already exists");
            }
        }

        for (int i = 0; i < models.length; i++) {
            Model model = models[i];
            if (model.getModelName().equals(oldModelName)) {
                model.setModelName(newModelName);
                return;
            }
        }
        throw new NoSuchModelNameException("model " + oldModelName + " not found");
    }
    @Override
    public Double getPriceByName(String modelName) throws NoSuchModelNameException {
        for (int i = 0; i < models.length; i++){
            Model model = models[i];
            if (model.getModelName().equals(modelName)){
                return model.getModelPrice();
            }
        }
        throw new NoSuchModelNameException("model " + modelName + " not found");
    }
    @Override
    public void setPriceByName(String modelName, Double newModelPriceByName) throws
            NoSuchModelNameException {
        if (newModelPriceByName <= 0.0d) {
            throw new ModelPriceOutOfBoundsException("invalid price " + newModelPriceByName);
        }

        for (int i = 0; i < models.length; i++){
            Model model = models[i];
            if (model.getModelName().equals(modelName)){
                model.setModelPrice(newModelPriceByName);
                return;
            }
        }
        throw new NoSuchModelNameException("model " + modelName + " not found");
    }
    @Override
    public void addModelNameAndModelPrice(String modelName, Double modelPrice) throws DuplicateModelNameException{
        if (modelPrice <= 0.0d) {
            throw new ModelPriceOutOfBoundsException("invalid price " + modelPrice);
        }

        for (int i = 0; i < models.length; i++) {
            Model model = models[i];
            if (model.getModelName().equals(modelName)) {
                throw new DuplicateModelNameException("model " + modelName + " already exists");
            }
        }

        models = Arrays.copyOf(models, models.length +1);
        Model newModel = new Model(modelName, modelPrice);
        models[models.length - 1] = newModel;
    }
    @Override
//    public void delModelsByName(String modelName) throws NoSuchModelNameException {
//        for (int i = 0; i < models.length; i++){
//            Model model = models[i];
//            if (model.getModelName().equals(modelName)){
//                Model[] newModels = Arrays.copyOf(models, models.length - 1);
//                System.arraycopy(models, i + 1, newModels, i, models.length - i - 1);
//                models = newModels;
//                return;
//            }
//        }
//        throw new NoSuchModelNameException("model " + modelName + " not found");
//    }

    public void delModelsByName(String modelName) throws NoSuchModelNameException {
        boolean flug = true;
        for (int i = 0; i < models.length; i++)
            if (Objects.equals(models[i].getModelName(), modelName)) {
                flug = false;
                System.arraycopy(models, i + 1, models, i, models.length - i - 1);
                models = Arrays.copyOf(models, models.length - 1);
            }
        if (flug) throw new NoSuchModelNameException(modelName);
    }

        @Override
    public int getCount() {
        return models.length;
    }

    public Car(String mark, int modelsCount){
        this.mark = mark;
        models = new Model[0];
        for (int i = 0;i < modelsCount; i++) {
            String modelName = "model" + (i + 1);
            Double modelPrice = Math.round(Math.random() * 1000000) / 100.0;
            try {
                addModelNameAndModelPrice(modelName, modelPrice);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public int getSizeModelArray() {
        return models.length;
    }
}