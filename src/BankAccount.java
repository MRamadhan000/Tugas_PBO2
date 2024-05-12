import java.util.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class BankAccount {
    private String accountNumber,nameUser,password;
    private double balance;
    private ArrayList<SavingBonds> listUserBonds = new ArrayList<>(); // array daftar angsuran tiap tiap user
    static private ArrayList<BankAccount> listAccountNumber =  new ArrayList<>(); // array daftar user
    static Scanner inputObj = new Scanner(System.in);

    public BankAccount(String accountNumber, double balance, String nameUser, String password) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.nameUser = nameUser;
        this.password = password;
    }

    public void addListLoan(SavingBonds savingBonds){
        listUserBonds.add(savingBonds);
    }

    public void transfer(){
        String accountDes;
        BankAccount dest = null;
        System.out.print("Enter account destinantion number : ");
        accountDes = inputObj.nextLine();
        for (BankAccount x : BankAccount.listAccountNumber){
            if(x.accountNumber.equals(accountDes) && !x.accountNumber.equals(this.getAccountNumber())) {
                dest = x;
                break;
            }
        }
        if (dest!=null){
            String amount;
            System.out.print("Enter amount : ");
            amount = inputObj.nextLine();
            if(Integer.parseInt(amount) < this.getBalance()){
                this.setBalance(this.getBalance() - Integer.parseInt(amount));
                dest.setBalance(dest.getBalance()+Integer.parseInt(amount));
                displayTransaction(dest.accountNumber,Double.parseDouble(amount));
                System.out.println("TRANSACTION SUCCESSFUL");
            }
            else
                System.out.println("Your balance is insufficient");
        }
        else
            System.out.println("Account number doesn't found");
    }

    public void requestBond() {
        double loanAmount;
        int choose,choosePurpose;
        String loanPurpose;

        System.out.print("Enter bond amount: ");
        loanAmount = inputObj.nextDouble();

        System.out.print("1. House\n2. Motorcylae\n3. Investment\nPlease choose : ");
        choosePurpose = inputObj.nextInt();
        switch (choosePurpose) {
            case 1:
                loanPurpose = "House";
                break;
            case 2:
                loanPurpose = "Motorcyle";
                break;
            case 3:
                loanPurpose = "Investment";
                break;
            default:
                System.out.println("Unknown loan purpose.");
                return;
        }
        System.out.print("======= LIST OF TERMS =======\nA. 0-11 months\nB. 12-23 months\nC. 24-35 months\nD. 36-47 months\nE. 48-60 months\nPlease choose (1-5): ");
        choose = inputObj.nextInt();
        SavingBonds loanData = new SavingBonds(this.getNameUser(), this.getAccountNumber(), loanAmount,loanPurpose,BankAccount.generateID());
        this.addListLoan(loanData);
        loanData.processLoanInput(choose);
        inputObj.nextLine();
    }

    public void menu(){
        Scanner inputObj = new Scanner(System.in);
        String choose;
        boolean isRun = true;
        while (isRun) {
            System.out.print("===== MAIN MENU ======\n1. Display Balance\n2. Withdraw Funds\n3. Deposit Funds\n4. Transfer Funds to Another Account\n5. Request Bond\n6. Pay Bond\n7. Display Bond\n8. Exit\nPlease choose (1-8): ");
            choose = inputObj.nextLine();
            switch (choose) {
                case "1":
                    this.displayUserInfo();
                    break;
                case "2":
                    this.withdraw();
                    break;
                case "3":
                    this.deposit();
                    break;
                case "4":
                    this.transfer();
                    break;
                case "5":
                    this.requestBond();
                    break;
                case "6":
                    this.payBond();
                    break;
                case "7":
                    if(listAccountNumber.isEmpty())
                        System.out.println("======= YOU DONT HAVE ANY BOND =========\n");
                    else
                        this.displayListBond();
                    break;
                case "8":
                    System.out.println(" ======= YOU ARE LOGOUT =====\n");
                    isRun = false;
                    break;
                default:
                    break;
            }
        }
    }

    public void withdraw(){
        double withdrawAmount;
        do {
            System.out.print("Enter amount to withdraw : ");
            withdrawAmount = inputObj.nextDouble();
            if (withdrawAmount < 0) {
                System.out.println("Please enter a positive amount.");
            }
        } while (withdrawAmount < 0);
        if(this.getBalance() >= withdrawAmount){
            this.setBalance(this.getBalance() - withdrawAmount);
            System.out.println(" ======== Withdrawal successful ========\n");
        } else
            System.out.println(" ======== Insufficient balance =========\n");
        inputObj.nextLine();
    }

    public void deposit(){
        double amount;
        do {
            System.out.print("Enter the deposit amount : ");
            amount = inputObj.nextDouble();
            if(amount< 1)
                System.out.println("Invalid deposit amount");
            else
                this.setBalance(this.getBalance()+ amount);
        }while (amount < 1 );
        System.out.println("======= TRANSACTION SUCCESSFUL =========\n");
        inputObj.nextLine();
    }

    public void payBond(){
        String id;
        if(listAccountNumber.isEmpty())
            System.out.println("======= YOU DONT HAVE ANY LOAN =========\n");
        else {
            this.displayListBond();
            System.out.print("Enter Bond Id : ");
            id = inputObj.nextLine();
            SavingBonds data = this.checkBond(id);
            if (data != null)
                data.payment();
            else
                System.out.println(" ======== Id " + id + " NOT FOUND =======");
        }
    }

    public SavingBonds checkBond(String id){
        for (SavingBonds data : listUserBonds){
            if(data.getId().equals(id))
                return data;
        }
        return null;
    }

    public void displayTransaction(String destAccountNum, double amount) {
        System.out.println("BANK X");
        System.out.println("TRANSACTION RECEIPT");
        System.out.println("+-----------------------------------------------+");
        System.out.println(String.format("| %-45s |", "Source Account: " + this.getAccountNumber()));
        System.out.println(String.format("| %-45s |", "Destination Account: " + destAccountNum));
        System.out.println(String.format("| %-45s |", "Transferred Amount: " + formatCurrencyIDR(amount)));
        System.out.println(String.format("| %-45s |", "Timestamp: " + getCurrentDate()));
        System.out.println("+-----------------------------------------------+");
    }

    public void displayUserInfo() {
        System.out.println("+------------------------------------------+");
        System.out.printf("| %-18s: %-21s|%n", "Account Name", this.getNameUser());
        System.out.printf("| %-18s: %-21s|%n", "Account Number", this.getAccountNumber());
        System.out.printf("| %-18s: %-21s|%n", "Date", getCurrentDate());
        System.out.printf("| %-18s: %-21s|%n", "Current Balance", formatCurrencyIDR(this.getBalance()));
        System.out.println("+------------------------------------------+");
    }

    public void displayListBond(){
        for (SavingBonds data : listUserBonds){
            data.displayBonds();
        }
    }

    public static String formatCurrencyIDR(double amount) {
        Locale localeID = new Locale("id", "ID");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(localeID);
        return currencyFormatter.format(amount);
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        return dateFormat.format(new Date());
    }

    public static BankAccount isValidUser(String inputAccountNum,String password){
        for (BankAccount user : listAccountNumber){
            if(user.accountNumber.equals(inputAccountNum) && user.password.equals(password)){
                return user;
            }
        }
        return null;
    }

    public static String generateID() {
        Random rand = new Random();
        char letter = (char) (rand.nextInt(26) + 'A');
        int digit1 = rand.nextInt(10);
        int digit2 = rand.nextInt(10);
        String id = String.valueOf(letter) + String.valueOf(digit1) + String.valueOf(digit2);
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getNameUser() {
        return nameUser;
    }

    public static BankAccount getUser(String accountNumber){
        for (BankAccount x : listAccountNumber){
            if (x.accountNumber.equals(accountNumber)){
                return x;
            }
        }
        return null;
    }

    public static void addUserAuto(){
        BankAccount person1 = new BankAccount("123",300000,"JOHN","122");
        listAccountNumber.add(person1);
        BankAccount person2 = new BankAccount("234",500000,"ROSE","233");
        listAccountNumber.add(person2);
        BankAccount person3 = new BankAccount("345", 700000,"MARK","344");
        listAccountNumber.add(person3);
    }
}
