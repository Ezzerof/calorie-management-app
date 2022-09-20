import java.time.LocalDate;

public class Product {

    private String name;
    private LocalDate date;
    private double grams;
    private double kcal;
    private double fats;
    private double carbs;
    private double proteins;

    public Product(String name, LocalDate date, double grams, double kcal, double fats, double carbs, double proteins) {
        this.name = name.toLowerCase();
        this.date = date;
        this.grams = grams;
        this.kcal = kcal;
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
    public LocalDate getDate() {
        return date;
    }

    public double getKcal() {
        return kcal;
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

    public void setKcal(double kcal) {
        this.kcal = kcal;
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
