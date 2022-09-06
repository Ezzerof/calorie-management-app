public class Meal extends Product {

    private String name;
    private double grams;
    private double kcal;
    private double fats;
    private double carbs;
    private double proteins;

    public Meal(String name, double grams, double kcal, double fats, double carbs, double proteins) {
        super(name, grams, kcal, fats, carbs, proteins);
        this.name = name;
        this.grams = grams;
        this.kcal = kcal;
        this.fats = fats;
        this.carbs = carbs;
        this.proteins = proteins;
    }


}
