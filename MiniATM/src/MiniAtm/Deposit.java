package MiniAtm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Deposit {
	
       public Deposit(int pin,int DepositAmount) {
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

   				String query = "SELECT Accountholders.AccountNumber,Accountholders.Name,Accountholders.PhoneNumber,Accountholders.BalanceAmount,PinNumber.ATMpin FROM Accountholders INNER JOIN PinNumber ON Accountholders.AccountNumber ="
   						+ AC;

   				Statement st2 = con.createStatement();
   				ResultSet result = st2.executeQuery(query);
   				result.next();
   				int amount = result.getInt("BalanceAmount");
   				int balance = amount + DepositAmount;
   				String updateqr = "UPDATE Accountholders SET BalanceAmount=" + balance + " WHERE AccountNumber=" + AC
   						+ ";";
   				int row = st.executeUpdate(updateqr);
   				if(row==1) {
					System.out.println("                                 Amount Sucessfull credit in your account\n");
				}else {
					System.out.println("                                 !Sorry somthing technicol problems Try Again\n");
				}
   				
   				
   				String Recordsave = "insert into Statement(AccountNumber,credit,Balance,pin) values(?,?,?,?);";

				PreparedStatement pts = con.prepareStatement(Recordsave);
				pts.setString(1, AC);
				pts.setInt(2, DepositAmount);
				pts.setInt(3, balance);
				pts.setInt(4,  pin);
				pts.executeUpdate();
   				
   				
   			} catch (Exception e) {
   				System.out.println("                                 !Wrong pinnumber\n");
   			}

   		} catch (SQLException e) {

   			e.printStackTrace();

   		}
	}
}
