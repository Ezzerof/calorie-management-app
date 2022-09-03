import java.util.ArrayList;

public class User {

    private String name;
    private String username;
    private int userAge;
    private ArrayList<Double> userWeight;
    private ArrayList<Double> userHeight;
    private ArrayList<Double> userFatPercentage;
    private String userGoal;

    private ArrayList breakfast;
    private ArrayList dinner;
    private ArrayList lunch;
    private ArrayList snackFirst;
    private ArrayList snackSecond;
    private String[] product;


    public User(String name, String username, int userAge, double userWeight, double userHeight, double userFatPercentage, String userGoal) {
        this.name = name;
        this.username = username;
        this.userAge = userAge;
        this.userWeight = new ArrayList<>();
        addUserWeight(userWeight);
        this.userHeight = new ArrayList<>();
        addUserHeight(userHeight);
        this.userFatPercentage = new ArrayList<>();
        addUserFatPercentage(userFatPercentage);
        this.userGoal = userGoal;
    }

    protected String[] setProduct(String prodName, String grams, String kcal, String fats, String carbs, String proteins) {
        return new String[]{prodName, grams, kcal, fats, carbs, proteins};
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

    public ArrayList<Double> getUserWeight() {
        return userWeight;
    }

    public void addUserWeight(double weight) {
        this.userWeight.add(weight);
    }

    public void addUserHeight(double height) {
        this.userHeight.add(height);
    }

    public ArrayList<Double> getUserHeight() {
        return userHeight;
    }

    public void addUserFatPercentage(double fat) {
        this.userFatPercentage.add(fat);
    }

    public ArrayList<Double> getUserFatPercentage() {
        return userFatPercentage;
    }


}
