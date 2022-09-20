import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    UserRepository ur = new UserRepository();
    User user = new User("Dan", "ananas", 23, 55, 1.90, 21, 20, "Sport");

    @Test
    @DisplayName("Adding user")
    void addUser() {
        ur.addUser(user);
        assertNotNull(ur);
    }

    @Test
    @DisplayName("Getting user by username")
    void getUserByUsername() {

    }
}