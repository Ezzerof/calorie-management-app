import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    UserRepository ur = new UserRepository();

    @Test
    @DisplayName("Adding user")
    void addUser() {
        User createdUser = new User("Dan", "ananas", 23, 55, 1.90, 21, 20, "Sport");
        ur.addUser(createdUser);
        User foundUser = ur.getUserByUsername("ananas");
        assertEquals(createdUser.getUsername(), foundUser.getUsername());
    }

    @Test
    @DisplayName("Getting user by username")
    void getUserByUsername() {
        User createdUser = new User("Dan", "ananas", 23, 55, 1.90, 21, 20, "Sport");
        User createdUser2 = new User("Vasea", "abricot", 23, 55, 1.90, 21, 20, "Sport");
        User createdUser3 = new User("Dan", "morcov", 23, 55, 1.90, 21, 20, "Sport");
        ur.addUser(createdUser2);
        ur.addUser(createdUser);
        ur.addUser(createdUser3);
        User abricot = ur.getUserByUsername("abricot");
        assertNotNull(abricot);
        assertTrue(abricot.getUsername().equals("abricot"));
    }
}