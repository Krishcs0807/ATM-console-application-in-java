package MiniAtm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BalanceCheck {
	
	public BalanceCheck(int pin) {
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
				
				System.out.println("                                 Accountno:"+AC);
				System.out.println("                                 ___________________\n");
				System.out.println("                                 Balance:"+amount);
				System.out.println("                                 ___________________\n");
				
			} catch (Exception e) {
				System.out.println("                                 !Wrong pinnumber\n");
			}

		} catch (SQLException e) {

			e.printStackTrace();

		}
		
	}

}
