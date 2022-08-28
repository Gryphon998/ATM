import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class ATMSystem {

    static final ArrayList<Acct> ALL_ACCOUNTS = new ArrayList<>();

    static final Logger LOGGER = Logger.getLogger(ATMSystem.class.getName());

    static final Scanner SC = new Scanner(System.in);

    public static Acct loginUser;
    public static void main(String[] args) {

        while (true) {
            LOGGER.info("This is a bank system");
            LOGGER.info("1. User login");
            System.out.println("2. Didn't have a account? Register now");

            int command = SC.nextInt();

            switch (command) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                default:
                    System.out.println("Error");
            }
        }
    }

    private static void login() {
        while (true) {
            System.out.println("Please enter your account ID");
            String acctID = SC.next();
            if (Operation.isDuplicate(acctID)){
                loginUser = Operation.searchAcctWithAcctID(acctID);

                while (true) {
                    System.out.println("Please enter your password");
                    String password = SC.next();
                    if (loginUser.getPassword().equals(password)){
                        System.out.println("Successful Login, Welcome " + loginUser.getUserName());
                        userLoginPage();
                        return;
                    } else {
                        System.out.println("The user name and password provided do not correspond");
                    }
                }
            } else {
                System.out.println("Account ID doesn't exist, Plz renter");
            }
        }
    }

    private static void userLoginPage() {
        while (true) {
            System.out.println("Welcome, " + loginUser.getUserName());
            System.out.println("1. Account information");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Change password");
            System.out.println("6. Cancel account");
            System.out.println("7. Log Out");

            int command = SC.nextInt();

            switch (command){
                case 1:
                    Operation.accountInfo();
                    break;
                case 2:
                    Operation.deposit();
                    break;
                case 3:
                    Operation.withdraw();
                    break;
                case 4:
                    Operation.transfer();
                    break;
                case 5:
                    Operation.changePasswd();
                    break;
                case 6:
                    boolean result = Operation.cancelAcct();
                    if (result == true){
                        return;
                    } else {
                        break;
                    }
                case 7:
                    System.out.println("you are successfully log out");
                    return;
                default:
                    System.out.println("Plz select the right option");
                    break;
            }
        }
    }

    private static void register() {
        System.out.println("Customer Register");
        Acct account = new Acct();

        System.out.println("Plz enter your name");
        String userName = SC.next();
        account.setUserName(userName);

        while (true) {
            System.out.println("Plz enter your password");
            String password = SC.next();
            System.out.println("Plz confirm your password");
            String passwordConfirmed = SC.next();

            if (password.equals(passwordConfirmed)) {
                account.setPassword(passwordConfirmed);
                break;
            } else {
                System.out.println("Password doesn't match, Plz reenter");
            }
        }

        System.out.println("Plz set your Maximum withdrawal amount");
        double maximumWithdrawal = SC.nextDouble();
        account.setWithdrawal(maximumWithdrawal);

        String acctID = Operation.getAccountID();
        account.setAcctID(acctID);
        ALL_ACCOUNTS.add(account);
        System.out.println("Successful register, your account ID is " + acctID);

    }
}


