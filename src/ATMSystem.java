import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class ATMSystem {
    public static void main(String[] args) {
        ArrayList<Acct> accounts = new ArrayList<>();

        Scanner sc = new Scanner(System.in);

        Logger log = Logger.getLogger(ATMSystem.class.getName());

        while (true) {
            log.info("This is a bank system");
            log.info("1. User login");
            System.out.println("2. Didn't have a account? Register now");

            int command = sc.nextInt();

            switch (command) {
                case 1:
                    login(accounts, sc);
                    break;
                case 2:
                    register(accounts, sc);
                    break;
                default:
                    System.out.println("Error");

            }
        }


    }

    private static void login(ArrayList<Acct> accounts, Scanner sc) {
        while (true) {
            System.out.println("Please enter your account ID");
            String acctID = sc.next();
            if (isDuplicate(accounts, acctID)){
                Acct acct = searchAcctWithAcctID(acctID, accounts);
                while (true) {
                    System.out.println("Please enter your password");
                    String password = sc.next();
                    if (acct.getPassword().equals(password)){
                        System.out.println("Successful Login, Welcome " + acct.getUserName());
                        userLoginPage(acct, sc, accounts);
                        return;
                    }else {
                        System.out.println("The user name and password provided do not correspond");
                    }
                }

            }else {
                System.out.println("Account ID doesn't exist, Plz renter");
            }
        }
    }

    private static void userLoginPage(Acct acct, Scanner sc, ArrayList<Acct> accounts) {
        while (true) {
            System.out.println("Welcome, " + acct.getUserName());
            System.out.println("1. Account information");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Change password");
            System.out.println("6. Cancel account");
            System.out.println("7. Log Out");

            int command = sc.nextInt();

            switch (command){
                case 1:
                    accountInfo(acct);
                    break;
                case 2:
                    deposit(acct, sc);
                    break;
                case 3:
                    withdraw(acct, sc);
                    break;
                case 4:
                    transfer(accounts, acct, sc);
                    break;
                case 5:
                    changePasswd(acct, sc);
                    break;
                case 6:
                    boolean result = cancelAcct(accounts, acct, sc);
                    if (result == true){
                        return;
                    }else {
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

    private static boolean cancelAcct(ArrayList<Acct> accounts, Acct acct, Scanner sc) {
        System.out.println("Account Cancellation");
        while (true) {
            System.out.println("Please confirm your password");
            String password = sc.next();
            if (acct.getPassword().equals(password)){
                while (true) {
                    System.out.println("You have " + acct.getAcctBalance() + " in your account, are you sure to cancel account?");
                    System.out.println("1. Sure, cancel it");
                    System.out.println("2. No, I want to think about it later");
                    int command = sc.nextInt();

                    switch (command){
                        case 1:
                            accounts.remove(acct);
                            System.out.println("Congratulate, you have successfully cancel your account");
                            return true;
                        case 2:
                            System.out.println("Sure, take your time");
                            return false;
                        default:
                            System.out.println("Please select the correct command");
                            break;

                    }
                }
            }else {
                System.out.println("Your password doesn't match, plz renter");
            }
        }
    }

    private static void changePasswd(Acct acct, Scanner sc) {
        System.out.println("User setting");
        while (true) {
            System.out.println("Plz confirm your password");
            String password = sc.next();
            if (acct.getPassword().equals(password)){
                while (true) {
                    System.out.println("Plz set your new password");
                    String newPassword = sc.next();
                    System.out.println("Plz confirm your new password");
                    String newPasswordConfirmed = sc.next();
                    if (newPassword.equals(newPasswordConfirmed)){
                        acct.setPassword(newPasswordConfirmed);
                        System.out.println("You are all set!");
                        return;
                    }else {
                        System.out.println("password doesn't match, please reenter");
                    }
                }
            }else {
                System.out.println("Wrong password, please renter");
            }
        }
    }

    private static void transfer(ArrayList<Acct> accounts, Acct acct, Scanner sc) {
        System.out.println("System transfer page");
        if (accounts.size() <= 1){
            System.out.println("There's no other account in the system");
            return;
        }
        if (acct.getAcctBalance() == 0){
            System.out.println("No balance, please make a deposit");
            return;
        }
        while (true) {
            System.out.println("Plz enter your transfer account number");
            String transferAcctID = sc.next();
            if (searchAcctWithAcctID(transferAcctID, accounts) == null){
                System.out.println("Can't find account number in the system, plz check");
            } else if (transferAcctID.equals(acct.getAcctID())) {
                System.out.println("Sorry, you cant transfer to your own account");
            } else {
                Acct transferAcct = searchAcctWithAcctID(transferAcctID, accounts);
                while (true) {
                    System.out.println("Plz confirm your transfer account name");
                    String transferAcctName = sc.next();
                    if (transferAcctName.equals(transferAcct.getUserName())){
                        while (true) {
                            System.out.println("Plz input your transfer amount");
                            double transferAmount = sc.nextDouble();
                            if (transferAmount > acct.getAcctBalance()){
                                System.out.println("Your transfer amount exceeds your current balance");
                            }else {
                                acct.setAcctBalance(acct.getAcctBalance() - transferAmount);
                                transferAcct.setAcctBalance(transferAcct.getAcctBalance() + transferAmount);
                                System.out.println("Transfer succeeded, Here is your current account information: ");
                                accountInfo(acct);
                                return;
                            }
                        }
                    }else {
                        System.out.println("Transfer account name doesn't match");
                    }
                }
            }
        }
    }

    private static void withdraw(Acct acct, Scanner sc) {
        if (acct.getAcctBalance() < 20){
            System.out.println("Insufficient balance");
            return;
        }
        while (true) {
            System.out.println("Plz input your withdraw amount");
            double withdrawAmount = sc.nextDouble();
            if (withdrawAmount > acct.getWithdrawal()){
                System.out.println("Your withdraw amount is higher than your maximum withdraw");
            } else if (withdrawAmount > acct.getAcctBalance()) {
                System.out.println("Your withdraw amount is higher than your balance");
            } else {
                acct.setAcctBalance(acct.getAcctBalance() - withdrawAmount);
                System.out.println("Withdrawal succeeded, Here is your current account information: ");
                accountInfo(acct);
                return;
            }
        }
    }

    private static void deposit(Acct acct, Scanner sc) {
        System.out.println("Please input your deposit amount");
        double depositAmount = sc.nextDouble();
        acct.setAcctBalance(acct.getAcctBalance() + depositAmount);
        System.out.println("Deposit received, Here is your current account information: ");
        accountInfo(acct);

    }

    private static void accountInfo(Acct acct) {
        System.out.println("Account Information");
        System.out.println("Your account number is " + acct.getAcctID());
        System.out.println("Your total balance is " + acct.getAcctBalance());
        System.out.println("Your maximum withdraw amount is " + acct.getWithdrawal());
    }

    private static void register(ArrayList<Acct> accounts, Scanner sc) {
        System.out.println("Customer Register");
        Acct account = new Acct();

        System.out.println("Plz enter your name");
        String userName = sc.next();
        account.setUserName(userName);

        while (true) {
            System.out.println("Plz enter your password");
            String password = sc.next();
            System.out.println("Plz confirm your password");
            String passwordConfirmed = sc.next();

            if (password.equals(passwordConfirmed)) {
                account.setPassword(passwordConfirmed);
                break;
            } else {
                System.out.println("Password doesn't match, Plz reenter");
            }
        }

        System.out.println("Plz set your Maximum withdrawal amount");
        double maximumWithdrawal = sc.nextDouble();
        account.setWithdrawal(maximumWithdrawal);

        String acctID = getAccountID(accounts);
        account.setAcctID(acctID);
        accounts.add(account);
        System.out.println("Successful register, your account ID is " + acctID);
        System.out.println(accounts);

    }

    private static String getAccountID(ArrayList<Acct> accounts) {
        while (true) {
            String accountID = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
            if (!isDuplicate(accounts, accountID)) {
                return accountID;
            }
        }

    }

    private static boolean isDuplicate(ArrayList<Acct> accounts, String accountID) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accountID.equals(accounts.get(i).getAcctID())) {
                return true;
            }
        }
        return false;
    }

    private static Acct searchAcctWithAcctID(String accountID, ArrayList<Acct> accounts){
        for (int i = 0; i < accounts.size(); i ++){
            Acct acct = accounts.get(i);
            if (acct.getAcctID().equals(accountID)){
                return acct;
            }
        }
        return null;
    }
}


