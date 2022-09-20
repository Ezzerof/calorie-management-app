
import java.time.LocalDate;
import java.util.*;

public class MainPage {

    private UserRepository userRepository = new UserRepository();
    private static Set<String> savedUsernames = new HashSet<>();
    Scanner scanner = new Scanner(System.in);
    User tempUser = null;

    public void startApp() {
        boolean isOn = true;

        //Main menu starts
        while (isOn) {
            System.out.print("1. Sign in\n2. Log in\n3. Quit\nPlease select a function from above: ");
            int choice = intValidation();

            isOn = userChoice(true, choice);
        }
    }

    private boolean userChoice(boolean isOn, int choice) {
        switch (choice) {
            // Creating account
            case 1:
                accountCreation();
                break;
            case 2:
                // Log in to the app
                logInToAccount();
                break;
            case 3:
                // Quiting app
                System.out.println("App is quiting...");
                isOn = false;
                break;
            default:
                System.out.println("Wrong input, please select again.");
        }
        return isOn;
    }

    protected void accountCreation() {
        String name = isNameValid();
        System.out.println("Welcome, " + name);
        String username = isUsernameValid();
        int age = isAgeValid();
        double weight = isWeightValid();
        double height = isHeightValid();
        double fatPercentage = isFatPercentageValid();
        int exercisesTime = exercisesTimeValidation();
        String userGoal = userGoalValidation();

        User user = new User(name, username, age, weight, height, fatPercentage, exercisesTime, userGoal);
        userRepository.addUser(user);
    }

    protected void logInToAccount() {
        System.out.print("\nPlease enter your username: ");
        String input = scanner.next();
        if (savedUsernames.contains(input)) {

            boolean isLoginOn = true;
            while (isLoginOn) {

                tempUser = userRepository.getUserByUsername(input);
                System.out.println();
                System.out.println("1. Add product/dish to your daily diary.\n" +
                        "2. Remove product/dish from your daily diary.\n" +
                        "3. Check your daily sum up.\n" +
                        "4. Edit profile\n" +
                        "5. Log out and go back to main menu.\n");
                System.out.print("Please select a function from above: ");
                int function = intValidation();

                switch (function) {
                    case 1:
                        // Adding product or dish to the diary
                        addingProductToMeal();
                        break;
                    case 2:
                        // Removing product or meal from the diary
                        removingProductFromMeal();
                        break;
                    case 3:
                        // Getting sums
                        getSum();
                        break;
                    case 4:
                        // Editing user profile
                        editCurrentUser();
                        break;
                    case 5:
                        // Log out
                        tempUser = null;
                        isLoginOn = false;
                        break;
                    default:
                        System.out.println("You have entered a wrong number.");
                        break;
                }
            }
        }
    }

    protected void addingProductToMeal() {
        boolean isMainUserMenuOn = true;
        while (isMainUserMenuOn) {

            LocalDate tempDate = getDate(tempUser);
            String mealName = gettingMealType();

            if (mealName.equals("out")) {
                isMainUserMenuOn = false;
            } else if (mealName.equals("error")) {
                System.out.println("Wrong input.");
                isMainUserMenuOn = false;
            }

            boolean secondUserMenuOn = true;
            while (secondUserMenuOn) {

                System.out.println("\n1. Select existing product/dish from database.\n2. Remove existing product/dish from database\n3. Add new product/dish to database.\n4. Go back");
                System.out.print("Enter the option: ");
                int uChoice = intValidation();

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
                                double grams = doubleValidation();

                                // Adding new values to the product
                                gettingNewProdValues(prodNum, grams, tempUser);

                            } else if (strChoice.equals("no")) {
                                tempUser.addProductToMeal(chosenProduct, mealName);
                            } else {
                                System.out.println("Wrong input.");
                                break;
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
                        String tempProdName = scanner.next();                           // need validation
                        System.out.print("Please enter how many grams: ");
                        double tempProdGrams = doubleValidation();
                        System.out.print("Please enter how many kcal: ");
                        double tempProdKcal = doubleValidation();
                        System.out.print("Please enter how many fats: ");
                        double tempProdFats = doubleValidation();
                        System.out.print("Please enter how many carbs: ");
                        double tempProdCarbs = doubleValidation();
                        System.out.print("Please enter how many proteins: ");
                        double tempProdProteins = doubleValidation();

                        Product newProd = new Product(tempProdName, tempDate, tempProdGrams, tempProdKcal, tempProdFats, tempProdCarbs, tempProdProteins);
                        tempUser.addProductToLOP(newProd);
                        break;
                    case 4:
                        // Going back to the previous menu
                        secondUserMenuOn = false;
                        isMainUserMenuOn = false;
                        break;
                    default:
                        System.out.println("Wrong input.");
                        secondUserMenuOn = false;
                        break;
                }
            }
        }
    }

    protected void removingProductFromMeal() {
        boolean isOnMealRemoval = true;

        while (isOnMealRemoval) {
            LocalDate tempProd = getDate(tempUser);
            String mealName = gettingMealType();
            if (mealName.equals("out")) {
                isOnMealRemoval = false;
            }

            if (tempUser.getAllProductsFormMeal(mealName)) {
                System.out.print("\nPlease enter number of the products/dishes to remove it: ");
                int numberOfMeal = intValidation();
                numberOfMeal -= 1;
                tempUser.getMeal(mealName).remove(numberOfMeal);
            } else {
                break;
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

    protected void editCurrentUser() {
        boolean userProfileMenuOn = true;
        while (userProfileMenuOn) {

            System.out.print("\nEdit profile:\n1. Edit weight\n2. Edit height\n3. Fat percentage\n4. Edit movement time\n 5. Go back" +
                    "\nPlease select from above: ");
            int select = 0;
            try {
                select = scanner.nextInt();

            } catch (InputMismatchException e) {
                scanner.nextLine();
            }

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
                    System.out.println("Your current fat percentage is: " + tempUser.getUserFatPercentage() + "%");
                    System.out.print("\nPlease enter your new fat percentage: ");
                    double newFatPercentage = isFatPercentageValid();
                    tempUser.addUserFatPercentage(newFatPercentage);
                    System.out.println("Your new fat percentage is: " + tempUser.getUserFatPercentage() + "%");
                    break;
                case 4:
                    System.out.println("Your current movement time is: " + tempUser.getUserMovementDuration());
                    System.out.print("\nPlease enter your new movement time: ");
                    double newMovementTime = exercisesTimeValidation();
                    tempUser.addUserFatPercentage(newMovementTime);
                    System.out.println("Your new movement time is: " + tempUser.getUserMovementDuration());
                    break;
                case 5:
                    userProfileMenuOn = false;
                default:
                    System.out.println("Wrong input.");
                    break;
            }
        }
    }

    protected void getSum() {
        boolean isGettingSumOn = true;
        while (isGettingSumOn) {
            LocalDate tempDate = getDate(tempUser);

            System.out.println("1. Get sum for all day.\n2. Get sum for every meal.\n3. Go back.");
            int userSumChoice = scanner.nextInt();

            switch (userSumChoice) {
                case 1:
                    gettingDailySum(tempUser, tempDate);
                    break;
                case 2:
                    String mealName = gettingMealType();

                    if (mealName.equals("out")) {
                        isGettingSumOn = false;
                        break;
                    }

                    gettingDailySum(tempUser, mealName, tempDate);

                    isGettingSumOn = false;
                    break;
                case 3:
                    isGettingSumOn = false;
                    break;
                default:
                    System.out.println("Wrong input");
                    break;
            }
        }
    }

    protected void gettingDailySum(User tempUser, String mealName, LocalDate date) {

        double kcalSum = 0;
        double fatSum = 0;
        double carbsSum = 0;
        double proteinsSum = 0;

        for (Product product : tempUser.getMeal(mealName)) {
            if (product.getDate().equals(date)) {
                kcalSum += product.getKcal();
                fatSum += product.getFats();
                carbsSum += product.getCarbs();
                proteinsSum += product.getProteins();
            }
        }
        System.out.printf("\n%s:\nKcal: %.2f\nFats: %.2f\nCarbs: %.2f\nProteins: %.2f\n", mealName, kcalSum, fatSum, carbsSum, proteinsSum);
    }

    protected void gettingDailySum(User tempUser, LocalDate date) {

        double kcalSum = 0;
        double fatSum = 0;
        double carbsSum = 0;
        double proteinsSum = 0;

        String[] meals = {"breakfast", "snack", "lunch", "dinner"};

        for (String meal: meals) {
            for (Product product : tempUser.getMeal(meal)) {
                if (product.getDate().equals(date)) {
                    kcalSum += product.getKcal();
                    fatSum += product.getFats();
                    carbsSum += product.getCarbs();
                    proteinsSum += product.getProteins();
                }
            }
        }
        System.out.printf("\n%s:\nKcal: %.2fg\nFats: %.2fg\nCarbs: %.2fg\nProteins: %.2fg\n","Daily sum", kcalSum, fatSum, carbsSum, proteinsSum);
    }

    protected int intValidation() {
        int number = 0;
        boolean isOn = true;
        while (isOn) {
            try {
                number = scanner.nextInt();
                isOn = false;

            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.print("Wrong input. Please enter again: ");
            }
        }
        return number;
    }

    protected int exercisesTimeValidation() {
        int exercisesTime = 0;
        int minMovementTime = 0;
        int maxMovementTime = 10080; // (24hours x 60min ) x 7 days
        boolean isOn = true;
        while (isOn) {
            try {
                System.out.print("Enter your movement time in minutes: ");
                exercisesTime = scanner.nextInt();
                if (exercisesTime < minMovementTime || exercisesTime > maxMovementTime) {
                    System.out.println("Invalid input.");
                } else {
                    isOn = false;
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.print("Wrong input. Please enter again: ");
            }
        }
        return exercisesTime;
    }

   protected double doubleValidation() {
       double tempProdGrams = 0;
       boolean isOn = true;
       while (isOn) {
            try {
                tempProdGrams = scanner.nextDouble();
                isOn = false;

            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.print("Wrong input. Please enter again: ");
            }
        }
       return tempProdGrams;
   }

    protected LocalDate getDate(User user) {

        boolean isOn = true;
        Scanner inp = new Scanner(System.in);
        while (isOn) {
            System.out.print("Please enter the date(ex: 13/09/1999): ");
            String userInput = inp.nextLine();
            if ((userInput.compareTo(user.getStringUserRegisterDate()) > 0)) {
                int day = Integer.parseInt(userInput.substring(0, 2));
                int month = Integer.parseInt(userInput.substring(3, 5));
                int year = Integer.parseInt(userInput.substring(6));
                return LocalDate.of(year, month, day);
            } else {
                System.out.println("Wrong date");
            }
        }
        return user.getUserRegisterDate();
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
            default:
                mealName = "error";
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
            try {
                System.out.print("Please enter your age using numbers: ");
                int age = scanner.nextInt();
                if (age < 15 || age > 90) {
                    System.out.println("Your age is invalid.");
                } else {
                    tempAge = age;
                    isOn = false;
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Wrong input");
            }
        }

        return tempAge;
    }

    private double isWeightValid() {
        boolean isOn = true;
        double weight = 0;

        while (isOn) {
            try {
                System.out.print("Enter your weight: ");
                double input = scanner.nextDouble();

                if (input < 20 || input > 200) {
                    System.out.println("Invalid weight.");
                } else {
                    weight = input;
                    isOn = false;
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Wrong input");
            }
        }
        return weight;
    }

    private double isHeightValid() {
        boolean isOn = true;
        double height = 0;

        while (isOn) {
            try {
                System.out.print("Enter your height(example 1.87): ");
                double input = scanner.nextDouble();
                if (input < 1 || input > 2.3) {
                    System.out.println("Invalid height.");
                } else {
                    height = input;
                    isOn = false;
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Wrong input");
            }
        }
        return height;
    }

    private double isFatPercentageValid() {
        boolean isOn = true;
        double fat = 0;

        while (isOn) {
            try {
                System.out.print("Enter your fat percentage: ");
                double input = scanner.nextDouble();
                if (input < 1 || input > 90) {
                    System.out.println("Invalid fat percentage.");
                } else {
                    fat = input;
                    isOn = false;
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Wrong input");
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
