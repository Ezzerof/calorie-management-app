
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
                    System.out.print("\nPlease enter your username: ");
                    String input = scanner.next();
                    if (savedUsernames.contains(input)) {

                        boolean isLoginOn = true;

                        while (isLoginOn) {

                            tempUser = userRepository.getUserByUsername(input);
                            System.out.println();
                            System.out.println("1. Add to product/meal to your daily diary.\n" +
                                    "2. Remove product/meal from your daily diary.\n" +
                                    "3. Check your daily sum up.\n" +
                                    "4. Edit profile\n" +
                                    "5. Log out and go back to main menu.\n");
                            System.out.print("Please select a function from above: ");
                            int function = scanner.nextInt();

                            switch (function) {
                                case 1:
                                    boolean isMainUserMenuOn = true;
                                    while (isMainUserMenuOn) {
                                        LocalDate tempDate = getDate();

                                        System.out.print("\nChoose meal:\n1. Breakfast\n2. Snack\n3. Lunch\n4. Snack\n5. Dinner\n6. Go back\nEnter the option: ");
                                        int userChoice = scanner.nextInt();
                                        String mealName = "";
                                        if (userChoice == 6) {
                                            break;
                                        } else {
                                            mealName = getMealName(userChoice);
                                        }

                                        boolean secondUserMenuOn = true;
                                        Product userChosenProduct = null;

                                        while (secondUserMenuOn) {

                                            System.out.println("\n1. Select existing product/meal from database.\n2. Remove existing product/meal from database\n3. Add new product to database.\n4. Go back");
                                            System.out.print("Enter the option: ");
                                            int uChoice = scanner.nextInt();
                                            System.out.println();
                                            String userSelectedProd = "";

                                            switch (uChoice) {
                                                case 1:
                                                    int i = 1;
                                                    if (tempUser.getLOP().isEmpty()) {
                                                        System.out.println("List is empty\n");
                                                        break;
                                                    } else {
                                                        System.out.println();
                                                        for (Product prod : tempUser.getLOP()) {
                                                            System.out.printf("%d. %s %.2f\n", i, prod.getName(), prod.getGrams());
                                                            ++i;
                                                        }
                                                        System.out.print("\nPlease select the product number from above or enter -1 to go back: ");
                                                        int prodNum = scanner.nextInt();
                                                        if (prodNum == -1) {
                                                            break;
                                                        }
                                                        prodNum -=1;
                                                        Product chosenProduct = tempUser.getLOP().get(prodNum);
                                                        userSelectedProd = chosenProduct.getName();

                                                        System.out.println("Do you want to edit it? (yes or no): ");
                                                        String strChoice = scanner.next();
                                                        if (strChoice.equals("yes")) {

                                                            System.out.print("\nPlease enter how many grams: ");
                                                            double grams = scanner.nextDouble();

                                                            // Adding new values to the product
                                                            Product prod = gettingNewProdValies(prodNum, grams, tempUser, tempDate);
                                                            //Adding product to the Meal chosen in the listName
                                                            tempUser.addProductToMeal(prod, mealName);
                                                        } else if (strChoice.equals("no")) {
                                                            tempUser.addProductToMeal(chosenProduct, mealName);
                                                        }
                                                    }
                                                    break;
                                                case 2:
                                                    tempUser.removeProductFromLOP(userSelectedProd);
                                                    break;
                                                case 3:
                                                    System.out.println();
                                                    System.out.print("Please enter product/meal name: ");
                                                    String tempProdName = scanner.next();
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
                                                    System.out.println();

                                                    Product newProd = new Product(tempProdName, tempDate, tempProdGrams, tempProdKcal, tempProdFats, tempProdCarbs, tempProdProteins);
                                                    tempUser.addProductToMeal(newProd, mealName);
                                                    tempUser.addProductToLOP(newProd);
                                                    break;
                                                case 4:
                                                    secondUserMenuOn = false;
                                            }
                                        }
                                    }
                                    break;
                                case 2:
                                    boolean isOnMealRemoval = true;

                                    while (isOnMealRemoval) {
                                        LocalDate tempProd = getDate();

                                        System.out.print("\nChoose meal:\n1. Breakfast\n2. Snack\n3. Lunch\n4. Snack\n5. Dinner\n 6. Go back\nEnter the option: ");
                                        int userChoice = scanner.nextInt();
                                        String mealName = "";
                                        if (userChoice == 6) {
                                            isOnMealRemoval = false;
                                        } else {
                                            mealName = getMealName(userChoice);
                                        }
                                        tempUser.getAllProductsFormMeal(mealName);
                                        System.out.print("\nPlease enter number of the meal to remove it: ");
                                        int numberOfMeal = scanner.nextInt() - 1;
                                        tempUser.getMeal(mealName).remove(numberOfMeal);
                                    }
                                    break;
                                case 3:
                                    LocalDate tempDate = getDate();
                                    String[] meals = {"breakfast", "snack", "lunch", "dinner"};

                                    for (String meal: meals) {
                                        double kcalSum = 0;
                                        double fatSum = 0;
                                        double carbsSum = 0;
                                        double proteinsSum = 0;
                                        for (Product tempProd : tempUser.getMeal(meal)) {
                                            if (tempProd.getDate().equals(tempDate)) {
                                                kcalSum += tempProd.getKcal();
                                                fatSum += tempProd.getFats();
                                                carbsSum += tempProd.getCarbs();
                                                proteinsSum += tempProd.getProteins();
                                            }
                                        }
                                        System.out.printf("%s:\nKcal: %.2f\nFats: %.2f\nCarbs: %.2f\nProteins: %.2f\n", meal, kcalSum, fatSum, carbsSum, proteinsSum);
                                    }

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
                                    isLoginOn = false;
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
        System.out.print("Please enter the date(ex: 13/09/1999): ");
        String userInput = scanner.nextLine();
        System.out.println(userInput);
//        int day = Integer.parseInt(userInput.substring(0,2));
//        int month = Integer.parseInt(userInput.substring(3,5));
//        int year = Integer.parseInt(userInput.substring(6));
//        LocalDate tempDate = LocalDate.of(year,month,day);
        LocalDate tempDate = LocalDate.of(2020,12,23);
        return tempDate;
    }

    protected Product gettingNewProdValies(int prodNum, double grams, User tempUser, LocalDate tempDate) {
        double initialGrams = tempUser.getLOP().get(prodNum).getGrams();
        double initKcal = tempUser.getLOP().get(prodNum).getKcal();
        double initFats = tempUser.getLOP().get(prodNum).getFats();
        double initCarbs = tempUser.getLOP().get(prodNum).getCarbs();
        double initProteins = tempUser.getLOP().get(prodNum).getProteins();

        double newKcal = (initKcal / initialGrams) * grams;
        double newFats = (initFats / initialGrams) * grams;
        double newCarbs = (initCarbs / initialGrams) * grams;
        double newProteins = (initProteins / initialGrams) * grams;
        return new Product(tempUser.getLOP().get(prodNum).getName(), tempDate, grams, newKcal, newFats, newCarbs, newProteins);
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
