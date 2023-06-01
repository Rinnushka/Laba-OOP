package com.company;


import java.io.Serializable;

public class Moto implements ITransport, Serializable {
    private String mark;

    public String getMark() {
        return mark;
    }

    public void setMark(String newMark) {
        mark = newMark;
    }

    private class Model implements Serializable{
        private String modelName;
        private Double modelPrice;
        Model prev = null;
        Model next = null;

        private String getModelName() {
            return modelName;
        }

//        private void setModelName(String newModelName) {
//            modelName = newModelName;
//        }

        private Double getModelPrice() {
            return modelPrice;
        }

//        private void setModelPrice(Double newModelPrice) {
//            modelPrice = newModelPrice;
//        }

        public Model(String modelName, Double modelPrice) {
            this.modelName = modelName;
            this.modelPrice = modelPrice;
        }
    }

    private Model head = new Model("Model", 100d);

    {
        size = 1;
        head.prev = head;
        head.next = head;
    }

    private int size;

    private transient long lastModified;

    {
        this.lastModified = System.currentTimeMillis();
    }

    public void setModelName(String oldName, String newName) throws DuplicateModelNameException, NoSuchModelNameException {
        if (getModelByName(oldName) != null) {
            if (getModelByName(newName) == null) getModelByName(oldName).modelName = newName;
            else throw new DuplicateModelNameException(newName);
        } else {
            throw new NoSuchModelNameException(oldName);
        }
    }

    public String[] getArrayModelsNames() {
        Model m = head;
        String[] NamesArray = new String[size - 1];
        for (int i = 1; i < size; i++) {
            NamesArray[i - 1] = m.next.getModelName();
            m = m.next;
        }
        return NamesArray;
    }

    public Double getPriceByName(String modelName) throws NoSuchModelNameException {
        if (getModelByName(modelName) != null) {
            for (int i = 1; i < size; i++) {
                if (getModelByIndex(i).getModelName().equals(modelName))
                    return getModelByIndex(i).modelPrice;
            }
            throw new NoSuchModelNameException(modelName);
        } else {
            throw new NoSuchModelNameException(modelName);
        }
    }

    public Model getModelByIndex(int index) {
        Model m;
        m = head;
        int i = 1;
        while (i <= index) {
            m = m.next;
            ++i;
        }
        return m;
    }

    public void setPriceByName(String modelName, Double newModelPriceByName) throws NoSuchModelNameException {
        if (newModelPriceByName > 0) {
            if (getModelByName(modelName) != null) {
                getModelByName(modelName).modelPrice = newModelPriceByName;
            } else {
                throw new NoSuchModelNameException(modelName);
            }
        } else {
            throw new ModelPriceOutOfBoundsException("invalid price " + newModelPriceByName);
        }
    }

    public Double[] getArrayModelsPrice() {
        Double[] pricesArray = new Double[size - 1];
        for (int i = 1; i < size; i++)
            pricesArray[i - 1] = getModelByIndex(i).getModelPrice();
        return pricesArray;
    }

    public void addModelNameAndModelPrice(String modelName, Double modelPrice) throws DuplicateModelNameException {
        if (modelPrice <= 0.0d) {
            throw new ModelPriceOutOfBoundsException("invalid price " + modelPrice);
        }
        if (getModelByName(modelName) == null) {
            Model model = new Model(modelName, modelPrice);
            model.next = head;
            model.prev = head.prev;
            model.prev.next = model;
            head.prev = model;
            size++;
        } else {
            throw new DuplicateModelNameException(modelName);
        }
        this.lastModified = System.currentTimeMillis();
    }

    public Model getModelByName(String name) {
        Model model = head.next;
        while (model != head) {
            if (model.getModelName().equals(name)) {
                return model;
            }
            model = model.next;
        }
        return null;
    }

    public void delModelsByName(String modelName) throws NoSuchModelNameException {
        if (getModelByName(modelName) != null) {
            Model m;
            m = head;
            for (int i = 1; i < size; i++) {
                if (getModelByIndex(i).getModelName().equals(modelName)) {
                    m = getModelByIndex(i);
                    break;
                }
            }
            m.prev.next = m.next;
            m.next.prev = m.prev;
            m.next = null;
            m.prev = null;
            size--;
        } else {
            throw new NoSuchModelNameException(modelName);
        }
        this.lastModified = System.currentTimeMillis();
    }

    public int getCount() {
        return size - 1;
    }

    public Moto(String mark, int modelsCount){
        this.mark = mark;
        for (int i = 0;i < modelsCount; i++) {
            String modelName = "moto_model" + (i + 1);
            Double modelPrice = Math.round(Math.random() * 1000000) / 100.0;
            try {
                addModelNameAndModelPrice(modelName, modelPrice);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    public long getLastModified() {
        return lastModified;
    }


    public int getSizeModelArray() {
        return size;
    }
}
