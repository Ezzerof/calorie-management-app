import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class User {

    private String name;
    private String username;
    private int userAge;
    private double userWeight;
    private double userHeight;
    private double userFatPercentage;
    private String userGoal;

    private Map<String, List<Double>> breakfast;
    private Map<String, List<Double>> dinner;
    private Map<String, List<Double>> lunch;
    private Map<String, List<Double>> snackFirst;
    private Map<String, List<Double>> snackSecond;
    private List<Double> product;


    public User(String name, String username, int userAge, double userWeight, double userHeight, double userFatPercentage, String userGoal) {
        this.name = name;
        this.username = username;
        this.userAge = userAge;
        addUserWeight(userWeight);
        addUserHeight(userHeight);
        addUserFatPercentage(userFatPercentage);
        this.userGoal = userGoal;
    }

    protected void setProduct(Double grams, Double kcal, Double fats, Double carbs, Double proteins) {
        product.add(grams);
        product.add(kcal);
        product.add(fats);
        product.add(carbs);
        product.add(proteins);
    }

    public List<Double> getProduct() {
        return this.product;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public int getUserAge() {
        return userAge;
    }

    public double getUserWeight() {
        return userWeight;
    }

    public void addUserWeight(double weight) {
        this.userWeight = weight;
    }

    public void addUserHeight(double height) {
        this.userHeight = height;
    }

    public double getUserHeight() {
        return userHeight;
    }

    public void addUserFatPercentage(double fat) {
        this.userFatPercentage = fat;
    }

    public double getUserFatPercentage() {
        return userFatPercentage;
    }


}
