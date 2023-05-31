package MiniAtm;

import java.util.Scanner;

public class jdbc_connection {

	public static void main(String[] args) {

		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		boolean loop = true;
		while (loop) {
			System.out.println("                                 ________________________\n"
					+"                                 WELCOME TO MY BANK ATM\n"
					+ "                                 ________________________\n");
			System.out.print("                                      1.Withdrawal\n"
					+ "                                      _____________\n"
					+ "                                      |2.Deposit\n"
		 			+ "                                      __________\n"
					+ "                                      3.Balance\n"
					+ "                                      __________\n"
					+ "                                      4.Money_Transfer\n"
					+ "                                      ________________\n"
					+ "                                      5.Statement\n"
					+ "                                      ___________\n"
					+ "                                      6.exit\n"
					+ "                                      _______\n");
			int choose = sc.nextInt();
			switch (choose) {
			case 1: {
				System.out.println("                                Enter your pin number");
				int pin = sc.nextInt();
				System.out.println("                                withdrawal Amount");
				int withdrawal = sc.nextInt();
				new withdrawal(pin, withdrawal);
				break;
			}
			case 2: {
				System.out.println("                                Enter your pin number");
				int pin = sc.nextInt();
				System.out.println("                                Deposit Amount");
				int Deposit = sc.nextInt();
				new Deposit(pin, Deposit);
				break;
			}
			case 3: {
				System.out.println("                                Enter your pin number");
				int pin = sc.nextInt();
				new BalanceCheck(pin);

				break;
			}
			case 4: {
				System.out.println("                                Enter your pin number");
				int pin = sc.nextInt();
				System.out.println("                                Transfer Account Number");
				String TransferAC = sc.next();
				System.out.println("                                Transfer Account holder Phonenumber Number");
				String Ph = sc.next();
				System.out.println("                                Transfer Amount");
				int TransferAount = sc.nextInt();
				new MoneyTransfer(pin, TransferAount, TransferAC, Ph);
				break;
			}
			case 5: {
				System.out.println("                                Account Number");
				String AC = sc.next();
				System.out.println("                                Enter your pin number");
				int pin = sc.nextInt();
				new statement(AC, pin);
				break;
			}
			case 6: {
				System.out.println("********************************Thank you for My Bank ATM************************************");
				loop = false;
				break;
			}
			}
		}

	}

}
