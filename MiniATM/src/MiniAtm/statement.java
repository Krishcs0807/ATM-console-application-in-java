package MiniAtm;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class statement {

	public statement(String Acount_Number,int pin) {
		
		String url = "jdbc:mysql://localhost:3306/atmdemo";
		String username = "root";
		String Password = "Krishcs0807@";
		String getRecordQurey="select * from statement where AccountNumber =? And pin=?";
		try {
			Connection con=DriverManager.getConnection(url, username, Password);
			          PreparedStatement pts=con.prepareStatement(getRecordQurey);
			          pts.setString(1,Acount_Number);
			          pts.setInt(2,pin);
			         ResultSet Record=pts.executeQuery();
			         if(Record.next()) {
			         System.out.println("                                 ---------------------AccountNumber--"+Acount_Number+"---------");
			         System.out.println("                                 --------------------------------------------------------");
			         System.out.println("                                      Date         Time     Credit     Debit     Balance ");
			         System.out.println("                                 --------------------------------------------------------");
			        while(Record.next()) { 
			        Date date=Record.getDate(1);
			        Time time=Record.getTime(2);
			        int credits=Record.getInt(4);
			        int debits=Record.getInt(5);
			        int balance=Record.getInt(6);
			        System.out.println("                                  "+date+"     "+time+"     "+credits+"         "+debits+"      "+balance+"");
			        System.out.println("                                  __________________________________________________________\n");
			        }
			         }else {
			        	 System.out.println("                                 !Wrong pin and Accound number\n");
			         }
			        
			        
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
	}
	
}
