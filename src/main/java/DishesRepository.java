
import java.util.*;

public class DishesRepository {
    private List<Product> listOfProducts = new ArrayList<>();
    private Map<Date, Product> breakfast = new HashMap<>();
    private Map<Date, Product> dinner = new HashMap<>();
    private Map<Date, Product> lunch = new HashMap<>();
    private Map<Date, Product> snack = new HashMap<>();


    protected void addProductToMeal(Map<Date, Product> map, Product product, Date date) {
        map.put(date, product);
    }

    public List<Product> getList() {
        return listOfProducts;
    }

    protected void addProductToLOP(Product product) {
        for (Product p: listOfProducts) {
            if (!p.getName().equals(product.getName())) {
                listOfProducts.add(product);
            } else {
                listOfProducts.add(listOfProducts.indexOf(p), product);
            }
        }
    }

    public void removeProductFromLOP(Product product) {
        if (listOfProducts.contains(product)) {
            listOfProducts.remove(product);
        }
    }

    protected void removeProduct(Map<Date, Product> map, Date date, Product product) {
        if (findProduct(map, date, product)) {
            while (map.values().remove(product));
        }
    }

    public boolean findProduct(Map<Date, Product> map, Date date, Product product) {
       if (map.containsKey(date) && map.containsValue(product)) {
           return true;
       }
       return false;
    }

    protected void editProduct(Map<Date, Product> map, Date date, Product product) {
        if (findProduct(map, date, product)) {
            map.put(date, product);
        }
    }

    public Map<Date, Product> getMap(int index) {
        if (index == 1){
            return breakfast;
        } else if (index == 2 || index == 4) {
            return snack;
        } else if (index == 3) {
            return lunch;
        } else if (index == 5) {
            return dinner;
        } else {
            System.out.println("Wrong input");
        }
        return null;
    }

}
