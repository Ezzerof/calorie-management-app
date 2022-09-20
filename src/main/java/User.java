
import java.util.*;

public class User {

    private String name;
    private String username;
    private int userAge;
    private double userWeight;
    private double userHeight;
    private double userFatPercentage;
    private int movementDuration;
    private String userGoal;
    private List<Product> breakfast;
    private List<Product> snack;
    private List<Product> lunch;
    private List<Product> dinner;
    private List<Product> listOfProducts;


    public User(String name, String username, int userAge, double userWeight, double userHeight, double userFatPercentage, int movementDuration, String userGoal) {
        this.name = name;
        this.username = username;
        this.userAge = userAge;
        addUserWeight(userWeight);
        addUserHeight(userHeight);
        addUserFatPercentage(userFatPercentage);
        this.movementDuration = movementDuration;
        this.userGoal = userGoal;
        listOfProducts = new ArrayList<>();
        breakfast = new ArrayList<>();
        snack = new ArrayList<>();
        lunch = new ArrayList<>();
        dinner = new ArrayList<>();
    }



    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
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
    public int getUserMovementDuration() {
        return movementDuration;
    }

    public List<Product> getLOP() {
        return listOfProducts;
    }

    public void addProductToMeal(Product product, String listName) {
        getMeal(listName).add(product);
    }

    public List<Product> getMeal(String mealName) {
        switch (mealName) {
            case "breakfast":
                return breakfast;
            case "snack":
                return snack;
            case "lunch":
                return lunch;
            case "dinner":
                return dinner;
        }
        return null;
    }

    public boolean getAllProductsFormMeal(String mealName) {
        if (getMeal(mealName).isEmpty()) {
            System.out.printf("%s list is empty", mealName);
            return false;
        } else {
            int i = 1;
            for (Product p : getMeal(mealName)) {
                System.out.printf("%d. %s\n", i, p.getName());
                ++i;
            }
        }
        return true;
    }

    protected boolean isProductOnList(Product product) {
        for (Product p: listOfProducts) {
            if (p.getName().equals(product.getName())) {
                listOfProducts.remove(p);
                return true;
            }
        }
        return false;
    }
    protected void addProductToLOP(Product product) {
        isProductOnList(product);
        listOfProducts.add(product);
    }

}
