package tests.lab2;

import main.lab2.Car;
import main.lab2.PersonalTransport;
import main.lab2.exceptions.DuplicateModelNameException;
import main.lab2.exceptions.NoSuchModelNameException;
import main.lab2.interfaces.ITransport;
import org.junit.Test;

public class Lab2Tests {

    public static void main(String[] args) throws NoSuchModelNameException, DuplicateModelNameException {
        //
    }

    @Test
    public void StartTests(){
        ITransport car1 = new Car("LADA", 3);
        ITransport motorcycle1 = new Car("Veterok", 4);

        System.out.println("All models name in " + car1.GetBrand() + " =" );
        for(String name:car1.GetArrayModelsName()){
            System.out.println(name);
        }

        System.out.println("All models name in " + motorcycle1.GetBrand() + " =" );
        for(String name:motorcycle1.GetArrayModelsName()){
            System.out.println(name);
        }
    }

    @Test
    public void WriteBrandsAndPrices(){
        ITransport car1 = new Car("Suzuki", 6);

        System.out.println("List models and prices:");
        PersonalTransport.WritePersonalTransport(car1);
    }



}
