import java.sql.*;
import java.util.Scanner;

public class BrokerageInterface {

    public static final String HOST = "jdbc:mysql://cs174a.engr.ucsb.edu:3306/domenicdipeppeDB";
    public static final String USER = "domenicdipeppe";
    public static final String PWD = "907";
    
    Connection connection = null;
    
    public BrokerageInterface(){
    }
    
    public void initialize() throws SQLException {
	System.out.println("Welcome to the System, John Admin");
	
	try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	
	try {
            connection = DriverManager.getConnection(HOST, USER, PWD);
	}
	
	catch (SQLException e) {
	    e.printStackTrace();
	}
    }
   
    public void endSession() throws SQLException{
	try{
	    if(connection != null)
		connection.close();
	} catch (SQLException e){
	    e.printStackTrace();
	}
	
    }

    // For all market accounts, add the appropriate amount of monthly interest to the balance
    public void addInterest(){

    }

    // Given a customer,  do the following for each account she/he owns:
    // generate a list of all transactions that have occurred in the current month.
    // This statement should list the name and email address of the customer.
    public void generateMonthlyStatement(Customer c){

    }
    
    
    // Generate a list of all customers who have traded (buy or sell) at least 1000 shares in the past month
    public void listActiveCustomers() throws SQLException{
	
	try{
	    PreparedStatement ps = connection.prepareStatement("SELECT * from Customers");
	    ResultSet customers = ps.executeQuery();
	    while(customers.next()){
		String name = customers.getString("name");
		System.out.println(name);
	    }
	    
	} catch (SQLException e) {
	    e.printStackTrace();
	}
    }

    // Generate a list of all customers who have made more than $10,000 in the last month,
    // including earnings from buying/selling stocks and interest. 
    // The residence state of each customer should also be listed.
    public void generateDTER(){

    }

    // Generate a list of all accounts associated with a particular customer and the current balance.
    public void customerReport(Customer c){
	
    }

    // Delete the list of transactions from each of the accounts
    public void deleteTransactions(){

    }
}
