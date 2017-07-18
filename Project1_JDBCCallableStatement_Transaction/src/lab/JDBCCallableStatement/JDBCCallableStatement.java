package lab.JDBCCallableStatement;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.concurrent.Callable;

public class JDBCCallableStatement {
	private static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String url="jdbc:sqlserver://localhost;Database=EShopV10;";
	private static String user="sa";
	private static String pass="123";

	public static void main(String[] args) {
		//demo1();//Call the stored procedure spInsertCategoryWithoutOutputId without Id
		demo2();//Call the stored procedure spInsertCategoryWithOutputId with OUTPUT Id
		//demo3();//Call the stored procedure spSearchProduct
	}

	/**
	 * Search Product which have unit price from 5 to 10 
	 * using stored procedure via spSearchProduct in SQL server.
	 * */
	private static void demo3() {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "{call spSearchProduct(?,?)}";
			CallableStatement cstmt = conn.prepareCall(sql);
			cstmt.setDouble(1, 5);
			cstmt.setDouble(2, 10);
			ResultSet rs = cstmt.executeQuery();
			
			while(rs.next()){
				System.out.println(">Name: "+rs.getString("Name"));
				System.out.println(">Unit Price: "+rs.getString("UnitPrice"));
				System.out.println();
			}
			conn.close();
			System.out.println("Call the stored procedure spSearchProduct successfully!");
		} catch (Exception e) {
			System.out.println("Call the stored procedure spSearchProduct failed!");
		}
	}

	/**
	 * Insert Product: Soda, Nước ngọt using stored procedure 
	 * via spInsertCategoryWithOutputId with OUTPUT Id in SQL server.
	 * */
	private static void demo2() {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "{call spInsertCategoryWithOutputId(?,?,?)}";
			CallableStatement cstmt = conn.prepareCall(sql);
			cstmt.setString(1, "Soda");
			cstmt.setString(2, "Nước ngọt");
			cstmt.registerOutParameter(3, Types.INTEGER);
			cstmt.executeUpdate();
			int id = cstmt.getInt(3);
			System.out.println(">Id: "+id);
			conn.close();
			System.out.println("Call the stored procedure spInsertCategoryWithOutputId successfully!");
		} catch (Exception e) {
			System.out.println("Call the stored procedure spInsertCategoryWithOutputId failed!");
		}	
	}
	
	/**
	 * Insert Product: Light, Đèn điện using stored procedure 
	 * via spInsertCategory without OUTPUT Id in SQL server.
	 * */
	private static void demo1() {
		try {
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, pass);
			String sql = "{call spInsertCategoryWithoutOutputId(?,?)}";
			CallableStatement cstmt = conn.prepareCall(sql);
			cstmt.setString(1, "Light");
			cstmt.setString(2, "Đèn điện");
			cstmt.executeUpdate();
			conn.close();
			System.out.println("Call the stored procedure spInsertCategoryWithoutOutputId successfully!");
		} catch (Exception e) {
			System.out.println("Call the stored procedure spInsertCategoryWithoutOutputId failed!");
		}	
	}
	
}
