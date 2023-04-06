package interfaces;

public interface ITransportFactory {
    IVehicle createInstance(String brand, int modelsSize);
}
