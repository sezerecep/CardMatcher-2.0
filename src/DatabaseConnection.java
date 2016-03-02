import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class DatabaseConnection {
	private Connection myConn;
	
	public DatabaseConnection() throws Exception
	{
		myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/memorygame", "root", "didemhatun1103");
		
	}
	public void NonReturnQuery(String name,int score,String diff,int dimension)
	{
		PreparedStatement myStmt = null;
		try {
			myStmt = myConn.prepareStatement("CALL AddNewScore(?,?,?,?);");
			myStmt.setString(1,name);
			myStmt.setString(2,diff);
			myStmt.setInt(3, score);
			myStmt.setInt(4,dimension);
			myStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public JTable ReturnHighScores()
	{
		ResultSet Set;
		PreparedStatement myStmt = null;
		try {
			Statement stmt = myConn.createStatement();
			Set= stmt.executeQuery("CALL GetOrderedScores");
			JTable table = new JTable(buildTableModel(Set));
			return table;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static DefaultTableModel buildTableModel(ResultSet rs)
	        throws SQLException {

	    ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    columnNames.add("Name");
	    columnNames.add("Difficulty");
	    columnNames.add("Score");
	    columnNames.add("Game Type");

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }
	    DefaultTableModel gogo = new DefaultTableModel(data, columnNames){
	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return false;
	        }
	    };
	    return gogo;

	}
	
}
