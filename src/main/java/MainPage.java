import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainPage {

    private UserRepository userRepository = new UserRepository();

    private static Set<String> savedUsernames = new HashSet<>();
    Scanner scanner = new Scanner(System.in);

    public void startApp() {
        boolean isOn = true;

        //Outter while
        while (isOn) {
            System.out.print("1. Sign in\n2. Log in\n3. Quit\nPlease select a function from above: ");
            int choice = scanner.nextInt();
            User tempUser;

            switch (choice) {
                case 1:
                    String name = isNameValid();
                    System.out.println("Welcome, " + name);
                    String username = isUsernameValid();
                    int age = isAgeValid();
                    double weight = isWeightValid();
                    double height = isHeightValid();
                    double fatPercentage = isFatPercentageValid();
                    String userGoal = userGoalValidation();

                    User user = new User(name, username, age, weight, height, fatPercentage, userGoal);
                    userRepository.addUser(user);
                    break;
                case 2:
                    System.out.print("Please enter your username: ");
                    String input = scanner.next();
                    if (savedUsernames.contains(input)) {

                        boolean isOn2 = true;

                        while (isOn2) {

                            tempUser = userRepository.getUserByUsername(input);
                            System.out.println("1. Add to product/meal to your daily diary.\n" +
                                    "2. Remove product/meal from your daily diary.\n" +
                                    "3. Check your daily sum up.\n" +
                                    "4. Edit profile\n" +
                                    "5. Log out and go back to main menu.\n");
                            System.out.print("Please select a function from above: ");
                            int function = scanner.nextInt();

                            switch (function) {
                                case 1:
                                    boolean isOn3 = true;
                                    while (isOn3) {
                                        System.out.print("Please enter the date(ex: 13/09/1999): ");
                                        String tempDate = scanner.nextLine();
                                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                        Date date = null;
                                        try {
                                            //Parsing the String
                                            date = sdf.parse(tempDate);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        System.out.print("\nChoose meal:\n1. Breakfast\n2. Snack\n3. Lunch\n4. Snack\n5. Dinner\nEnter the option: ");
                                        int userChoice = scanner.nextInt();

                                        boolean isOn5 = true;
                                        while (isOn5) {
                                            System.out.println("1. Select existing product/meal.\n2. Remove existing product/meal\n3. Add new product to database.");
                                            System.out.print("Enter the option: ");
                                            int uChoice = scanner.nextInt();

                                            switch (uChoice) {
                                                case 1:
                                                    int i = 1;
                                                    for (Product p : user.getDishesRepository().getList())
                                                    
                                                    break;
                                                case 2:
                                                    break;
                                                case 3:
                                                    break;
                                            }

                                        }


                                    }
                                    break;
                                case 2:
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    boolean isOn4 = true;
                                    while (isOn4) {
                                        System.out.print("\nEdit profile:\n1. Edit weight\n2. Edit height\n3. Fat percentage\n4. Quit" +
                                                "\nPlease select from above: ");
                                        int select = scanner.nextInt();
                                        switch (select) {
                                            case 1:
                                                System.out.println("Your current weight is: " + tempUser.getUserWeight());
                                                System.out.print("\nPlease enter your new weight: ");
                                                double newWeight = isWeightValid();
                                                tempUser.addUserWeight(newWeight);
                                                System.out.println("Your new weight is: " + tempUser.getUserWeight());
                                                break;
                                            case 2:
                                                System.out.println("Your current height is: " + tempUser.getUserHeight());
                                                System.out.print("\nPlease enter your new height: ");
                                                double newHeight = isHeightValid();
                                                tempUser.addUserHeight(newHeight);
                                                System.out.println("Your new height is: " + tempUser.getUserHeight());
                                                break;
                                            case 3:
                                                System.out.println("Your current fat percentage is: " + tempUser.getUserFatPercentage());
                                                System.out.print("\nPlease enter your new fat percentage: ");
                                                double newFatPercentage = isFatPercentageValid();
                                                tempUser.addUserFatPercentage(newFatPercentage);
                                                System.out.println("Your new fat percentage is: " + tempUser.getUserFatPercentage());
                                                break;
                                            case 4:
                                                isOn4 = false;
                                        }
                                    }

                                    break;
                                case 5:
                                    tempUser = null;
                                    isOn2 = false;
                                    break;
                            }
                        }
                    }
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
            if (savedUsernames.contains(username)) {
                System.out.println("Username already exist!");
            } else {
                savedUsernames.add(username);
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

    private double isWeightValid() {
        boolean isOn = true;
        double weight = 0;

        while (isOn) {
            System.out.print("Enter your weight: ");
            double input = scanner.nextDouble();

            if (input < 20 || input > 200) {
                System.out.println("Invalid weight.");
            } else {
                weight = input;
                isOn = false;
            }
        }
        return weight;
    }

    private double isHeightValid() {
        boolean isOn = true;
        double height = 0;

        while (isOn) {
            System.out.print("Enter your height(example 1.87): ");
            double input = scanner.nextDouble();
            if (input < 1 || input > 2.3) {
                System.out.println("Invalid height.");
            } else {
                height = input;
                isOn = false;
            }
        }
        return height;
    }

    private double isFatPercentageValid() {
        boolean isOn = true;
        double fat = 0;

        while (isOn) {
            System.out.print("Enter your fat percentage: ");
            double input = scanner.nextDouble();
            if (input < 1 || input > 90) {
                System.out.println("Invalid fat percentage.");
            } else {
                fat = input;
                isOn = false;
            }
        }
        return fat;
    }

    private String userGoalValidation() {
        boolean isOn = true;
        String goal = "";

        while (isOn) {
            System.out.print("\n1)Loose weight \n2)Gain weight \n3)Maintain weight\nPlease select what is your goal: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    goal = "Loose weight";
                    isOn = false;
                    break;
                case 2:
                    goal = "Gain weight";
                    isOn = false;
                    break;
                case 3:
                    goal = "Maintain weight";
                    isOn = false;
                    break;
                default:
                    System.out.println("Invalid input.");
            }
            System.out.println();
        }

        return goal;
    }

}
