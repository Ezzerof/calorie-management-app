
import java.time.LocalDate;
import java.util.*;

public class MainPage {

    private UserRepository userRepository = new UserRepository();

    private static Set<String> savedUsernames = new HashSet<>();
    Scanner scanner = new Scanner(System.in);

    public void startApp() {
        boolean isOn = true;
        User tempUser = null;

        //Outter while
        while (isOn) {
            System.out.print("1. Sign in\n2. Log in\n3. Quit\nPlease select a function from above: ");
            int choice = scanner.nextInt();

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
                                        String userInput = scanner.nextLine();
                                        int day = Integer.parseInt(userInput.substring(0,2));
                                        int month = Integer.parseInt(userInput.substring(3,5));
                                        int year = Integer.parseInt(userInput.substring(6));
                                        LocalDate tempDate = LocalDate.of(year,month,day); // add

                                        System.out.print("\nChoose meal:\n1. Breakfast\n2. Snack\n3. Lunch\n4. Snack\n5. Dinner\n 6. Go back\nEnter the option: ");
                                        int userChoice = scanner.nextInt();
                                        String listName = "";
                                        if (userChoice == 1) {
                                            listName = "breakfast";
                                        } else if (userChoice == 2 || userChoice == 4) {
                                            listName = "snack";
                                        } else if (userChoice == 3) {
                                            listName = "lunch";
                                        } else if (userChoice == 5) {
                                            listName = "dinner";
                                        } else if (userChoice == 6) {
                                            isOn3 = false;
                                        }

                                        boolean isOn5 = true;
                                        Product p = null;
                                        while (isOn5) {
                                            System.out.println("1. Select existing product/meal.\n2. Remove existing product/meal\n3. Add new product to database.\n4. Go back");
                                            System.out.print("Enter the option: ");
                                            int uChoice = scanner.nextInt();

                                            switch (uChoice) {
                                                case 1:
                                                    int i = 1;
                                                    if (tempUser.getList().isEmpty()) {
                                                        System.out.println("List is empty");
                                                        break;
                                                    } else {
                                                        for (Product prod : tempUser.getList()) {
                                                            System.out.printf("%d. %s", i, prod.getName());
                                                            ++i;
                                                        }
                                                        System.out.print("\nPlease select the product number from above: ");
                                                        int prodNum = scanner.nextInt() - 1;
                                                        System.out.print("\nPlease enter how many grams: ");
                                                        double grams = scanner.nextDouble();
                                                        tempUser.getList().get(prodNum).setGrams(grams);
                                                        p = tempUser.getList().get(prodNum);
                                                    }
                                                    break;
                                                case 2:
                                                    tempUser.removeProductFromLOP(p);
                                                    break;
                                                case 3:
                                                    System.out.print("Please enter product/meal name: ");
                                                    String tempProdName = scanner.nextLine();
                                                    System.out.print("\nPlease enter how many grams: ");
                                                    double tempProdGrams = scanner.nextDouble();
                                                    System.out.print("\nPlease enter how many kcal: ");
                                                    double tempProdKcal = scanner.nextDouble();
                                                    System.out.print("\nPlease enter how many fats: ");
                                                    double tempProdFats = scanner.nextDouble();
                                                    System.out.print("\nPlease enter how many carbs: ");
                                                    double tempProdCarbs = scanner.nextDouble();
                                                    System.out.print("\nPlease enter how many proteins: ");
                                                    double tempProdProteins = scanner.nextDouble();

                                                    Product newProd = new Product(tempProdName, tempDate, tempProdGrams, tempProdKcal, tempProdFats, tempProdCarbs, tempProdProteins);
                                                    tempUser.addProductToMeal(newProd, listName);
                                                    tempUser.addProductToLOP(newProd);
                                                    break;
                                                case 4:
                                                    isOn5 = false;
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
