import java.util.ArrayList;

public class AllUsers {

    protected ArrayList<User> existingUsers = new ArrayList<>();

    public void addUser(User name) {
        existingUsers.add(name);
    }

    public boolean findExistingUser(User name) {
        return existingUsers.contains(name);
    }

    public User getExistingUsers(String name) {
        int index = existingUsers.indexOf(name);
        return existingUsers.get(index);
    }

    public boolean removeExistingUser(User name) {

        if (findExistingUser(name)) {
            existingUsers.remove(name);
            System.out.println(name + " was removed!");
            return true;
        } else {
            System.out.println("User does not exist!");
            return false;
        }
    }
}
