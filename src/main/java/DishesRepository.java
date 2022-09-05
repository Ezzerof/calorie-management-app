
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DishesRepository {
    private Map<Date, Product> breakfast = new HashMap<>();
    private Map<Date, Product> dinner = new HashMap<>();
    private Map<Date, Product> lunch = new HashMap<>();
    private Map<Date, Product> snack = new HashMap<>();


    protected void addProduct(Map<Date, Product> map, Product product, Date date) {
        map.put(date, product);
    }

    protected void removeProduct(Map<Date, Product> map, Date date, Product product) {
        if (findProduct(map, date, product)) {
            while (map.values().remove(product));
        }
    }

    protected boolean findProduct(Map<Date, Product> map, Date date, Product product) {
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

}
