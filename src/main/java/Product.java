public class Product {

    private String name;
    private double grams;
    private double kcals;
    private double fats;
    private double carbs;
    private double proteins;

    public Product(String name, double grams, double kcals, double fats, double carbs, double proteins) {
        this.name = name;
        this.grams = grams;
        this.kcals = kcals;
        this.fats = fats;
        this.carbs = carbs;
        this.proteins = proteins;
    }

    public String getName() {
        return name;
    }

    public double getGrams() {
        return grams;
    }

    public double getKcals() {
        return kcals;
    }

    public double getFats() {
        return fats;
    }

    public double getCarbs() {
        return carbs;
    }

    public double getProteins() {
        return proteins;
    }

    public void setGrams(double grams) {
        this.grams = grams;
    }

    public void setKcals(double kcals) {
        this.kcals = kcals;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }
}
