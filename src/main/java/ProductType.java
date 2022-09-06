import java.util.ArrayList;

public enum ProductType {
    BREAKFAST,
    SNACK,
    LUNCH,
    DINNER;

    private ArrayList<Product> breakfast;
    private ArrayList<Product> snack;
    private ArrayList<Product> lunch;
    private ArrayList<Product> dinner;

    ProductType(User user) {
        this.breakfast = new ArrayList<>();
        this.snack = new ArrayList<>();
        this.lunch = new ArrayList<>();
        this.dinner = new ArrayList<>();
    }

    public ArrayList<Product> getProductType() {
        switch (this) {
            case BREAKFAST:
                return breakfast;
            case SNACK:
                return snack;
            case LUNCH:
                return lunch;
            case DINNER:
                return dinner;
        }
    }

}
