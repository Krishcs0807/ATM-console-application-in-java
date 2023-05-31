package MiniAtm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MoneyTransfer {

	private String url = "jdbc:mysql://localhost:3306/atmdemo";
	private String username = "root";
	private String Password = "Krishcs0807@";

	public MoneyTransfer(int pin, int TransferAmount, String TransferAC, String Phonenumber) {

		try {
			Connection con = DriverManager.getConnection(url, username, Password);
			String getpinQr = "SELECT * FROM PinNumber WHERE ATMpin =" + pin;
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery(getpinQr);

			res.next();
			try {
			String AC = res.getString("AccountNumber");

			String SelectAcountnumber = "SELECT Accountholders.AccountNumber,Accountholders.Name,Accountholders.PhoneNumber,Accountholders.BalanceAmount,PinNumber.ATMpin FROM Accountholders INNER JOIN PinNumber ON Accountholders.AccountNumber ="
					+ AC;

			Statement st2 = con.createStatement();
			ResultSet result = st2.executeQuery(SelectAcountnumber);
			result.next();
			
				int amount = result.getInt("BalanceAmount");

				// ****************************************************************************************************

				String query = "SELECT * FROM Accountholders where AccountNumber =? AND PhoneNumber=?;";
				PreparedStatement st3 = con.prepareStatement(query);
				st3.setString(1, TransferAC);
				st3.setString(2, Phonenumber);
				ResultSet TransferResult = st3.executeQuery();
				TransferResult.next();
				try {
					int Amount = TransferResult.getInt("BalanceAmount");

					int AddAmount = Amount + TransferAmount;
					String updateqr2 = "UPDATE Accountholders SET BalanceAmount=" + AddAmount + " WHERE AccountNumber="
							+ TransferAC + ";";
					int row2 = st.executeUpdate(updateqr2);

					int balance = amount - TransferAmount;
					String updateqr1 = "UPDATE Accountholders SET BalanceAmount=" + balance + " WHERE AccountNumber=" + AC
							+ ";";
					int row1 = st.executeUpdate(updateqr1);

					if (row1 == 1 && row2 == 1) {
						System.out.println("                                 Money Transfer Sucessfull\n");
					}

					String Recordsave1 = "insert into Statement(AccountNumber,debit,Balance,pin) values(?,?,?,?);";

					PreparedStatement pts1 = con.prepareStatement(Recordsave1);
					pts1.setString(1, AC);
					pts1.setInt(2, TransferAmount);
					pts1.setInt(3, balance);
					pts1.setInt(4, pin);
					pts1.executeUpdate();

					String Recordsave2 = "insert into Statement(AccountNumber,credit,Balance,pin) values(?,?,?,?);";

					String getpin = "Select ATMpin FROM PinNumber where AccountNumber="+TransferAC;
					Statement st4 = con.createStatement();
					ResultSet pinresult = st4.executeQuery(getpin);
					pinresult.next();
					int getPin = pinresult.getInt("ATMpin");

					PreparedStatement pts2 = con.prepareStatement(Recordsave2);
					pts2.setString(1, TransferAC);
					pts2.setInt(2, TransferAmount);
					pts2.setInt(3, AddAmount);
					pts2.setInt(4, getPin);
					pts2.executeUpdate();

				} catch (Exception e) {
					System.out.println("                                 !Wrong Account number and phone number\n");
				}
				
			} catch (Exception e) {
				System.out.println("                                 !Wrong pin\n");
			}
			
		} catch (SQLException e) {

			e.printStackTrace();

		}

	}
}
