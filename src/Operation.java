public class Operation {

    static void accountInfo() {

        System.out.println("Account Information");
        System.out.println("Your account number is " + ATMSystem.loginUser.getAcctID());
        System.out.println("Your total balance is " + ATMSystem.loginUser.getAcctBalance());
        System.out.println("Your maximum withdraw amount is " + ATMSystem.loginUser.getWithdrawal());
    }

    static boolean cancelAcct() {

        System.out.println("Account Cancellation");

        while (true) {
            System.out.println("Please confirm your password");
            String password = ATMSystem.SC.next();

            if (ATMSystem.loginUser.getPassword().equals(password)){
                while (true) {
                    System.out.println("You have " + ATMSystem.loginUser.getAcctBalance() + " in your account, are you sure to cancel account?");
                    System.out.println("1. Sure, cancel it");
                    System.out.println("2. No, I want to think about it later");
                    int command = ATMSystem.SC.nextInt();

                    switch (command){
                        case 1:
                            ATMSystem.ALL_ACCOUNTS.remove(ATMSystem.loginUser);
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

    static void changePasswd() {
        System.out.println("User setting");
        while (true) {
            System.out.println("Plz confirm your password");
            String password = ATMSystem.SC.next();
            if (ATMSystem.loginUser.getPassword().equals(password)){
                while (true) {
                    System.out.println("Plz set your new password");
                    String newPassword = ATMSystem.SC.next();
                    System.out.println("Plz confirm your new password");
                    String newPasswordConfirmed = ATMSystem.SC.next();
                    if (newPassword.equals(newPasswordConfirmed)){
                        ATMSystem.loginUser.setPassword(newPasswordConfirmed);
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

    static void transfer() {
        System.out.println("System transfer page");
        if (ATMSystem.ALL_ACCOUNTS.size() <= 1){
            System.out.println("There's no other account in the system");
            return;
        }
        if (ATMSystem.loginUser.getAcctBalance() == 0){
            System.out.println("No balance, please make a deposit");
            return;
        }
        while (true) {
            System.out.println("Plz enter your transfer account number");
            String transferAcctID = ATMSystem.SC.next();
            if (searchAcctWithAcctID(transferAcctID) == null){
                System.out.println("Can't find account number in the system, plz check");
            } else if (transferAcctID.equals(ATMSystem.loginUser.getAcctID())) {
                System.out.println("Sorry, you cant transfer to your own account");
            } else {
                Acct transferAcct = searchAcctWithAcctID(transferAcctID);
                while (true) {
                    System.out.println("Plz confirm your transfer account name");
                    String transferAcctName = ATMSystem.SC.next();
                    if (transferAcctName.equals(transferAcct.getUserName())){
                        while (true) {
                            System.out.println("Plz input your transfer amount");
                            double transferAmount = ATMSystem.SC.nextDouble();
                            if (transferAmount > ATMSystem.loginUser.getAcctBalance()){
                                System.out.println("Your transfer amount exceeds your current balance");
                            }else {
                                ATMSystem.loginUser.setAcctBalance(ATMSystem.loginUser.getAcctBalance() - transferAmount);
                                transferAcct.setAcctBalance(transferAcct.getAcctBalance() + transferAmount);
                                System.out.println("Transfer succeeded, Here is your current account information: ");
                                accountInfo();
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

    static void withdraw() {
        if (ATMSystem.loginUser.getAcctBalance() < 20){
            System.out.println("Insufficient balance");
            return;
        }
        while (true) {
            System.out.println("Plz input your withdraw amount");
            double withdrawAmount = ATMSystem.SC.nextDouble();
            if (withdrawAmount > ATMSystem.loginUser.getWithdrawal()){
                System.out.println("Your withdraw amount is higher than your maximum withdraw");
            } else if (withdrawAmount > ATMSystem.loginUser.getAcctBalance()) {
                System.out.println("Your withdraw amount is higher than your balance");
            } else {
                ATMSystem.loginUser.setAcctBalance(ATMSystem.loginUser.getAcctBalance() - withdrawAmount);
                System.out.println("Withdrawal succeeded, Here is your current account information: ");
                accountInfo();
                return;
            }
        }
    }

    static void deposit() {
        System.out.println("Please input your deposit amount");
        double depositAmount = ATMSystem.SC.nextDouble();
        ATMSystem.loginUser.setAcctBalance(ATMSystem.loginUser.getAcctBalance() + depositAmount);
        System.out.println("Deposit received, Here is your current account information: ");
        accountInfo();

    }

    static String getAccountID() {
        while (true) {
            String accountID = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
            if (!isDuplicate(accountID)) {
                return accountID;
            }
        }
    }

    static boolean isDuplicate(String accountID) {
        for (int i = 0; i < ATMSystem.ALL_ACCOUNTS.size(); i++) {
            if (accountID.equals(ATMSystem.ALL_ACCOUNTS.get(i).getAcctID())) {
                return true;
            }
        }
        return false;
    }

    static Acct searchAcctWithAcctID(String accountID){
        for (int i = 0; i < ATMSystem.ALL_ACCOUNTS.size(); i ++){
            Acct acct = ATMSystem.ALL_ACCOUNTS.get(i);
            if (acct.getAcctID().equals(accountID)){
                return acct;
            }
        }
        return null;
    }
}
