
import java.time.LocalDate;
import java.util.*;

public class MainPage {

    private UserRepository userRepository = new UserRepository();

    private static Set<String> savedUsernames = new HashSet<>();
    Scanner scanner = new Scanner(System.in);

    public void startApp() {
        boolean isOn = true;
        User tempUser = null;
        Product userSelectedProd = null;

        //Main menu starts
        while (isOn) {
            System.out.print("1. Sign in\n2. Log in\n3. Quit\nPlease select a function from above: ");
            int choice = scanner.nextInt();

            switch (choice) {
                // Creating account
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
                    // End of creating account
                case 2:
                    // Log in to the app
                    System.out.print("\nPlease enter your username: ");
                    String input = scanner.next();
                    if (savedUsernames.contains(input)) {

                        boolean isLoginOn = true;

                        while (isLoginOn) {

                            tempUser = userRepository.getUserByUsername(input);
                            System.out.println();
                            System.out.println("1. Add to product/dish to your daily diary.\n" +
                                    "2. Remove product/dish from your daily diary.\n" +
                                    "3. Check your daily sum up.\n" +
                                    "4. Edit profile\n" +
                                    "5. Log out and go back to main menu.\n");
                            System.out.print("Please select a function from above: ");
                            int function = scanner.nextInt();

                            switch (function) {
                                case 1:
                                    // Adding product or dish to the diary
                                    boolean isMainUserMenuOn = true;
                                    while (isMainUserMenuOn) {
                                        LocalDate tempDate = getDate();

                                        String mealName = gettingMealType();

                                        if (mealName.equals("out")) {
                                            isMainUserMenuOn = false;
                                        }

                                        boolean secondUserMenuOn = true;
                                        while (secondUserMenuOn) {

                                            System.out.println("\n1. Select existing product/dish from database.\n2. Remove existing product/dish from database\n3. Add new product/dish to database.\n4. Go back");
                                            System.out.print("Enter the option: ");
                                            int uChoice = scanner.nextInt();
                                            System.out.println();

                                            switch (uChoice) {
                                                // Selecting existing products or dishes from the database
                                                case 1:
                                                    int prodNum = choosingProdFromDatabaseList(tempUser);
                                                    if (prodNum == -1) {
                                                        break;
                                                    } else {
                                                        prodNum -= 1;
                                                        Product chosenProduct = tempUser.getLOP().get(prodNum);

                                                        System.out.println("Do you want to edit it? (yes or no): ");
                                                        String strChoice = scanner.next();

                                                        if (strChoice.equals("yes")) {

                                                            System.out.print("\nPlease enter how many grams: ");
                                                            double grams = scanner.nextDouble();

                                                            // Adding new values to the product
                                                            gettingNewProdValues(prodNum, grams, tempUser);

                                                        } else if (strChoice.equals("no")) {
                                                            tempUser.addProductToMeal(chosenProduct, mealName);
                                                        }
                                                    }

                                                    break;
                                                case 2:
                                                    // Removing products or dishes from the database
                                                    int prodIndex= choosingProdFromDatabaseList(tempUser);
                                                    if (prodIndex == -1) {
                                                        break;
                                                    } else {
                                                        prodIndex -= 1;
                                                        Product chosenProduct = tempUser.getLOP().get(prodIndex);
                                                        tempUser.getLOP().remove(chosenProduct);
                                                    }

                                                    break;
                                                case 3:
                                                    // Adding a new product to dish to the database
                                                    System.out.print("Please enter product/dish name: ");
                                                    String tempProdName = scanner.next();
                                                    System.out.print("Please enter how many grams: ");
                                                    double tempProdGrams = scanner.nextDouble();
                                                    System.out.print("Please enter how many kcal: ");
                                                    double tempProdKcal = scanner.nextDouble();
                                                    System.out.print("Please enter how many fats: ");
                                                    double tempProdFats = scanner.nextDouble();
                                                    System.out.print("Please enter how many carbs: ");
                                                    double tempProdCarbs = scanner.nextDouble();
                                                    System.out.print("Please enter how many proteins: ");
                                                    double tempProdProteins = scanner.nextDouble();

                                                    Product newProd = new Product(tempProdName, tempDate, tempProdGrams, tempProdKcal, tempProdFats, tempProdCarbs, tempProdProteins);
                                                    tempUser.addProductToMeal(newProd, mealName);
                                                    tempUser.addProductToLOP(newProd);
                                                    break;
                                                case 4:
                                                    // Going back to the previous menu
                                                    secondUserMenuOn = false;
                                                    isMainUserMenuOn = false;
                                                    break;
                                            }
                                        }
                                    }
                                    break;
                                case 2:
                                    // Removing product or meal from the diary
                                    boolean isOnMealRemoval = true;

                                    while (isOnMealRemoval) {
                                        LocalDate tempProd = getDate();
                                        String mealName = gettingMealType();
                                        if (mealName.equals("out")) {
                                            isOnMealRemoval = false;
                                        }

                                        if (tempUser.getAllProductsFormMeal(mealName)) {
                                            System.out.print("\nPlease enter number of the products/dishes to remove it: ");
                                            int numberOfMeal = scanner.nextInt() - 1;
                                            tempUser.getMeal(mealName).remove(numberOfMeal);
                                        } else {
                                            break;
                                        }
                                    }
                                    break;
                                case 3:
                                    // Getting sums
                                    boolean isGettingSumOn = true;
                                    while (isGettingSumOn) {
                                        LocalDate tempDate = getDate();
                                        String mealName = gettingMealType();

                                        if (mealName.equals("out")) {
                                            isGettingSumOn = false;
                                        }

                                        double kcalSum = 0;
                                        double fatSum = 0;
                                        double carbsSum = 0;
                                        double proteinsSum = 0;

                                        for (Product product : tempUser.getMeal(mealName)) {
                                            kcalSum += product.getKcal();
                                            fatSum += product.getFats();
                                            carbsSum += product.getCarbs();
                                            proteinsSum += product.getProteins();
                                        }
                                        System.out.printf("\n%s:\nKcal: %.2f\nFats: %.2f\nCarbs: %.2f\nProteins: %.2f\n", mealName, kcalSum, fatSum, carbsSum, proteinsSum);
                                    }
                                    break;
                                case 4:
                                    // Editing user profile
                                    boolean userProfileMenuOn = true;
                                    while (userProfileMenuOn) {

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
                                                userProfileMenuOn = false;
                                        }
                                    }
                                    break;
                                case 5:
                                    // Log out
                                    tempUser = null;
                                    isLoginOn = false;
                                    break;
                            }
                        }
                    }
                    break;
                case 3:
                    // Quiting app
                    System.out.println("App is quiting...");
                    isOn = false;
                    break;
                default:
                    System.out.println("Wrong input, please select again.");
            }
        }
    }

    protected int choosingProdFromDatabaseList(User user) {
        int i = 1;
        int prodNum = -1;
        if (user.getLOP().isEmpty()) {
            System.out.println("List is empty\n");
        } else {
            for (Product prod : user.getLOP()) {
                System.out.printf("%d. %s %.2f\n", i, prod.getName(), prod.getGrams());
                ++i;
            }
            System.out.print("\nPlease select the product/dish number from above or enter -1 to go back: ");
            prodNum = scanner.nextInt();
        }
        return prodNum;
    }

    protected String getMealName(int userChoice) {
        String listName = "";
        if (userChoice == 1) {
            listName = "breakfast";
        } else if (userChoice == 2 || userChoice == 4) {
            listName = "snack";
        } else if (userChoice == 3) {
            listName = "lunch";
        } else if (userChoice == 5) {
            listName = "dinner";
        }
        return listName;
    }

    protected LocalDate getDate() {
        Scanner inp = new Scanner(System.in);
        System.out.print("Please enter the date(ex: 13/09/1999): ");
        String userInput = inp.nextLine();
        int day = Integer.parseInt(userInput.substring(0,2));
        int month = Integer.parseInt(userInput.substring(3,5));
        int year = Integer.parseInt(userInput.substring(6));
        return LocalDate.of(year,month,day);
    }

    protected String gettingMealType() {
        Scanner inp = new Scanner(System.in);
        System.out.print("\nChoose a meal:\n1. Breakfast\n2. Snack\n3. Lunch\n4. Snack\n5. Dinner\n6. Go back\nEnter the option: ");
        int userChoice = inp.nextInt();
        String mealName = "";

        switch (userChoice) {
            case 1:
                mealName = "breakfast";
                break;
            case 2:
            case 4:
                mealName = "snack";
                break;
            case 3:
                mealName = "lunch";
                break;
            case 5:
                mealName = "dinner";
                break;
            case 6:
                mealName = "out";
                break;
        }
        return mealName;
    }

    protected void gettingNewProdValues(int prodNum, double grams, User tempUser) {
        Product existingProduct = tempUser.getLOP().get(prodNum);

        double initialGrams = existingProduct.getGrams();
        double initKcal = existingProduct.getKcal();
        double initFats = existingProduct.getFats();
        double initCarbs = existingProduct.getCarbs();
        double initProteins = existingProduct.getProteins();

        double newKcal = (initKcal / initialGrams) * grams;
        double newFats = (initFats / initialGrams) * grams;
        double newCarbs = (initCarbs / initialGrams) * grams;
        double newProteins = (initProteins / initialGrams) * grams;

        existingProduct.setGrams(grams);
        existingProduct.setKcal(newKcal);
        existingProduct.setFats(newFats);
        existingProduct.setCarbs(newCarbs);
        existingProduct.setProteins(newProteins);
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
