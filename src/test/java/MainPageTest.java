import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainPageTest {

    User user1 = new User("Dan", "ananas", 23, 55, 1.90, 21, 20, "Sport");
    User user2 = null;
    MainPage mp = new MainPage();

    @Test
    void nameValidation() {
        mp.isNameValid();
    }

    @Test
    void accountCreation() {

    }

    @Test
    void logInToAccount() {
    }

    @Test
    void addingProductToMeal() {
    }

    @Test
    void removingProductFromMeal() {
    }

    @Test
    void choosingProdFromDatabaseList() {
    }

    @Test
    void editCurrentUser() {
    }

    @Test
    void getSum() {
    }
}