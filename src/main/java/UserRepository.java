import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private static List<User> existingUsers = new ArrayList<>();

    public void addUser(final User name) {
        existingUsers.add(name);
    }

    public User getUserByUsername(final String username) {

        return existingUsers.stream()
                .filter(user -> user.getName().equals(username))
                .findAny()
                .orElse(null);
    }

}
