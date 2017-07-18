package lab.JDBCTransaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTransaction {
	private static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String url="jdbc:sqlserver://localhost;Database=EShopV10;";
	private static String user="sa";
	private static String pass="123";

	public static void main(String[] args) {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url,user,pass);
			Statement stmt = conn.createStatement();
			
			conn.setAutoCommit(false);
			try {
				String sql1="INSERT INTO Categories(Name,NameVN) VALUES('A',N'A')";
				stmt.executeUpdate(sql1);
				
				String sql2="INSERT INTO Categories(Name,NameVN) VALUES('B',N'B')";
				stmt.executeUpdate(sql2);
				
				/*incorrect sql3:
				 * - 1 in 3 code line in try catch is incorrect, 
				 * jump into Exception, do rollback(), 
				 * and alert insert failed.
				 * */
				String sql3="INSERT INTO Categories(Name,NameVN) VALUES('C',N'C'C')";
				stmt.executeUpdate(sql3);
				
//				/*correct sql3*/
//				String sql3="INSERT INTO Categories(Name,NameVN) VALUES('C',N'C')";
//				stmt.executeUpdate(sql3);
				
				conn.commit();
				System.out.println("Insert successfully!");
			} catch (Exception e) {
				conn.rollback();
				System.out.println("Insert failed!");
			}	
			
			conn.setAutoCommit(true);			
			conn.close();			
		} catch (ClassNotFoundException e) {
			System.out.println("Insert failed!");
		} catch (SQLException e) {
			System.out.println("Insert failed!");
		}
	}

}
