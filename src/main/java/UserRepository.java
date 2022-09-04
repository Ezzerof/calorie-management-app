import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    private static List<User> existingUsers = new ArrayList<>();

    public void addUser(final User name) {
        existingUsers.add(name);
    }

    public boolean findExistingUser(final User name) {
        return existingUsers.contains(name);
    }

    public Optional<User> getExistingUsers(final String name) {
        return existingUsers
                .stream()
                .filter(user -> user.getName().equals(name)).findFirst();

    }

    public User getUserByUsername(final String username) {
        for (User u : existingUsers) {
            if (u.getUsername().equals(username))
                return u;
        }

        return null;
    }

    public void removeExistingUser(final String name) {

        if (getExistingUsers(name).isPresent()) {
            existingUsers.remove(name);
        }
    }
}
