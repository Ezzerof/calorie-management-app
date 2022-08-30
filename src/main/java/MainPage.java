import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MainPage extends AllUsers {

    private static Set<String> SavedUsernames = new HashSet<>();
    Scanner scanner = new Scanner(System.in);

    public void startApp() {
        boolean isOn = true;

        while (isOn) {
            System.out.print("1. Sign in\n2. Log in\n3. Quit\nPlease select a function from above: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    String name = isNameValid();
                    System.out.println("Welcome, " + name);
                    String username = isUsernameValid();
                    int age = isAgeValid();

                    User user = new User(name, username, age, 23, 21, 23, "Ananas");
                    existingUsers.add(user);

                    break;
                case 2:
                    break;
                case 3:
                    System.out.println("App is quiting...");
                    isOn = false;
                    break;
                default:
                    System.out.println("Wrong input, please select again.");
            }
        }
    }


    protected String isNameValid() {
        String name = "";
        boolean isOn = true;

        while (isOn) {
            System.out.print("\nEnter your name: ");
            String tempName = scanner.next();
            if (tempName.isEmpty()) {
                System.out.println("You forgot to write your name.");
            } else if (tempName.matches(".*\\d.*")) {
                System.out.println("Your name is invalid, it contains digits.");
            } else {
                name = tempName.substring(0,1).toUpperCase() + tempName.substring(1);
                isOn = false;
            }

//            char[] nameInChar = name.toCharArray();
//            for (char c : nameInChar) {
//                if (Character.isDigit(c)) {
//                    System.out.println("Name is invalid, symbols are invalid.");
//                    break;
//                }
//            }
        }
        return name;
    }

    private String isUsernameValid() {

        boolean validUsername = true;
        String tempUsername = "";

        while (validUsername) {
            System.out.print("Enter your username: ");
            String username = scanner.next();
            if (SavedUsernames.contains(username)) {
                System.out.println("Username already exist!");
            } else {
                SavedUsernames.add(username);
                tempUsername = username;
                validUsername = false;
            }
        }

        return tempUsername;
    }

    private int isAgeValid() {
        int tempAge = 0;
        boolean isOn = true;
        while (isOn) {
            System.out.print("Please enter your age using numbers: ");
            int age = scanner.nextInt();
            if (age < 15 || age > 90) {
                System.out.println("Your age is invalid.");
            } else {
                tempAge = age;
                isOn = false;
            }
        }

        return tempAge;
    }

}
