public interface IUpgrade {

    void install(Car car);

    void remove(Car car);

    String getModName();

} 