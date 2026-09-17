public abstract class CarMod implements IUpgrade {
    protected String modName;
    protected boolean isPreInstalled;

    public CarMod(String modName, boolean isPreInstalled) {
        this.modName = modName;
        this.isPreInstalled = isPreInstalled;
    }

    @Override
    public String getModName() {
        return modName;
    }

    protected void printInstalled() {
        System.out.println(modName + " е добавено успешно.");
    }

    protected void printRemoved() {
        System.out.println(modName + " е премахнато успешно.");
    }
} 