import java.util.ArrayList;
import java.util.Optional;

public class UserRepository {

    private static ArrayList<User> existingUsers = new ArrayList<>();

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

    public void removeExistingUser(final String name) {

        if (getExistingUsers(name).isPresent()) {
            existingUsers.remove(name);
        }
    }
}
