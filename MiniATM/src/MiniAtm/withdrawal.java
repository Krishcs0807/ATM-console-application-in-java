package MiniAtm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class withdrawal {

	public withdrawal(int pin, int withdrawal) {
		String url = "jdbc:mysql://localhost:3306/atmdemo";
		String username = "root";
		String Password = "Krishcs0807@";

		try {

			Connection con = DriverManager.getConnection(url, username, Password);
			String getpin = "SELECT * FROM PinNumber WHERE ATMpin =" + pin;
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery(getpin);

			res.next();
			try {
				String AC = res.getString("AccountNumber");

				String SelectAcountnumber = "SELECT Accountholders.AccountNumber,Accountholders.Name,Accountholders.PhoneNumber,Accountholders.BalanceAmount,PinNumber.ATMpin FROM Accountholders INNER JOIN PinNumber ON Accountholders.AccountNumber ="
						+ AC;

				Statement st2 = con.createStatement();
				ResultSet result = st2.executeQuery(SelectAcountnumber);
				result.next();
				int amount = result.getInt("BalanceAmount");
				if (0 < amount) {
					int balance = amount - withdrawal;
					String updateqr = "UPDATE Accountholders SET BalanceAmount=" + balance + " WHERE AccountNumber="
							+ AC + ";";
					int row = st.executeUpdate(updateqr);
					if (row == 1) {
						System.out.println("                                 *Collect The Amount*\n");
					} else {
						System.out.println("                                 !Sorry somthing technicol problems\n");
					}

					String Recordsave = "insert into Statement(AccountNumber,debit,Balance,pin) values(?,?,?,?);";

					PreparedStatement pts = con.prepareStatement(Recordsave);
					pts.setString(1, AC);
					pts.setInt(2, withdrawal);
					pts.setInt(3, balance);
					pts.setInt(4, pin);
					pts.executeUpdate();
				}

			} catch (Exception e) {
				System.out.println("                                 !Wrong pinnumber\n");
			}

		} catch (SQLException e) {

			e.printStackTrace();

		}
	}

}
