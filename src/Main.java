import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        BankAccount.addUserAuto(); // MENAMBAHKAN OBJEK BANKACCOUNT
        //1. Account Number = 123 & pass = 122
        //2. Account number = 234 & pass = 233
        //3. Account Number = 345 & pass = 344

        BankAccount user;
        String accountNumber, password, choose;
        Scanner inputObj = new Scanner(System.in);
        boolean isRun = true;
        while (isRun) {
            System.out.print("====== WELCOME TO BANK X ======\n1. Start Transactation\n2. Exit\nPlease Choose (1-2) : ");
            choose = inputObj.nextLine();
            switch (choose) {
                case "1":
                    System.out.print("Enter your account number : ");
                    accountNumber = inputObj.nextLine();
                    System.out.print("Enter your password account number : ");
                    password = inputObj.nextLine();
                    user = BankAccount.isValidUser(accountNumber, password);
                    if (user != null)
                        user.menu();
                    else
                        System.out.println("INVALID ACCOUNT NUMBER");
                    break;
                case "2":
                    isRun = false;
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }
}

