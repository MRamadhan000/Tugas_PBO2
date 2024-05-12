import java.util.Scanner;

public class SavingBonds{
    private String userName, accountNumber,loanPurpose,id;
    private int term, monthsRemaining; // jagngka waktu dan bulan yang tersisia
    private double balance, rate, bondAmount;
    // balance jumlah yang yang harus dibayar tiap bulan
    // bondAmout jumlah awal peminjaman

    private static Scanner inputObj = new Scanner(System.in);
    SavingBonds(String userName, String accountNumber, double bondAmount, String loanPurpose,String id){
        this.userName = userName;
        this.accountNumber = accountNumber;
        this.bondAmount = bondAmount;
        this.loanPurpose = loanPurpose;
        this.id = id;
    }
    public void displayBonds() {
        String border = "+----------------------------------------------+";
        System.out.println(border);
        System.out.printf("| %-22s: %-21s|%n", "Name", this.getUserName());
        System.out.printf("| %-22s: %-21s|%n", "Monthly Installment", BankAccount.formatCurrencyIDR(this.getBalance()));
        System.out.printf("| %-22s: %-21s|%n", "Rate", this.getRate());
        System.out.printf("| %-22s: %-21s|%n", "Months Remaining", this.getMonthsRemaining());
        System.out.printf("| %-22s: %-21s|%n", "Bond Purpose", this.getLoanPurpose());
        System.out.printf("| %-22s: %-21s|%n", "Bond Id", this.getId());
        System.out.println(border);
    }

    public void payment() {
        this.displayBonds();
        System.out.print("\n1. Yes\n2. No\nDo you want paid this bond : ");
        int choose = inputObj.nextInt();
        switch (choose){
            case 1:
                BankAccount bankAccount = BankAccount.getUser(this.getAccountNumber());
                if (bankAccount.getBalance() >= this.getBalance())
                    this.earnInterest();
                else
                    System.out.println(" ======== Insufficient balance =========");
                break;
            case 2:
                System.out.println(" ===== YOU ARE NOT PAY THE BOND ======");
                break;
            default:
                System.out.println(" ====== INVALID INPUT ========");
                break;
        }
    }

    public void processLoanInput(int t){
        this.setTermAndRate(t);
        this.setBalance(this.calInstallment());
        System.out.println(" ========= Loan successfully processed ======\n");
    }

    public double calInstallment(){
        double interest = bondAmount * this.getRate()*this.getTerm();
        return (this.bondAmount + interest)/this.getTerm();
    }

    public void setTermAndRate(int t){
        if(t>=0 && t<12)
            this.setRate(0.05);
        else if(t>=12 && t<24)
            this.setRate(0.010);
        else if(t>=24 && t<36)
            this.setRate(0.015);
        else if(t>=36 && t<48)
            this.setRate(0.020);
        else if(t>=48 && t<=60)
            this.setRate(0.025);
        else{
            System.out.println("Invalid Term");
            t = 0;
        }
        this.setTerm(t);
        this.setMonthsRemaining(t);
        BankAccount bankAccount = BankAccount.getUser(getAccountNumber());
        bankAccount.setBalance(bankAccount.getBalance()+this.bondAmount); // MENAMBAH UANG YANG DINJAM KEPADA REKENING USER
    }

    public void earnInterest() {
        if (this.getMonthsRemaining() > 0){
            this.setMonthsRemaining(this.getMonthsRemaining() - 1);
            BankAccount bankAccount = BankAccount.getUser(getAccountNumber());
            bankAccount.setBalance(bankAccount.getBalance()- this.getBalance());
            this.displayBonds();
        } else
            System.out.println("Bond Matured");
    }

    public String getId() {
        return id;
    }

    public String getLoanPurpose() {
        return loanPurpose;}
    public String getUserName() {
        return userName;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public double getBondAmount() {
        return bondAmount;
    }
    public void setBondAmount(double bondAmount) {
        this.bondAmount = bondAmount;
    }
    public double getBalance() {
        return balance;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }
    public double getRate() {
        return rate;
    }
    public void setRate(double rate) {
        this.rate = rate;
    }
    public int getTerm() {
        return term;
    }
    public void setTerm(int term) {
        this.term = term;
    }
    public int getMonthsRemaining() {
        return monthsRemaining;
    }
    public void setMonthsRemaining(int monthsRemaining) {
        this.monthsRemaining = monthsRemaining;
    }
}
