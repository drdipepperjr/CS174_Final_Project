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

	boolean loggedin = true;
	Scanner scanner = new Scanner(System.in);

	while(loggedin){
	    System.out.println("What would you like to do? (Add Interest/Generate Monthly Statement/List Active Customers/Generate DTER/Customer Report/Delete Transactions/Logout");
	    String choice = scanner.nextLine();

	    if("generate monthly statement".equals(choice)){
		String username;
		System.out.print("Please enter the customer's username to generate their monthly statement: ");
		username = scanner.nextLine();
		generateMonthlyStatement(username);
	    }

	    else if("delete transactions".equals(choice)){

		System.out.println("Are you sure you want to delete ALL transactions for this month? (yes/no): ");
		String lastchance = scanner.nextLine();
		if("yes".equals(lastchance)){
		    //deleteTransactions();
		}
		else
		    System.out.println("Transactions for this month were NOT deleted");
	    }
	    
	    if("logout".equals(choice)){
		System.out.println("Logging out...");
		loggedin = false;
	    }
	}

	
	endSession();
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
    public void generateMonthlyStatement(String username){ //(string username?)
	
	String name = "";
	String email = "";

	// See if customer exists. If yes, get the name and email address. If not, exit
	try{
	    PreparedStatement ps = connection.prepareStatement("SELECT * FROM Customers WHERE username=?");
	    ps.setString(1,username);
	    ResultSet customer = ps.executeQuery();
	    if(!customer.first()){
		System.out.println("Sorry, that customer does not exist");
		return;
	    }
	    customer.beforeFirst();
	    while(customer.next()){
		name = customer.getString("name");
		email = customer.getString("email");
	    }
	    
	} catch (SQLException e) {
            e.printStackTrace();
        }

	// Get customer's transaction history 
	try{
	    PreparedStatement ps = connection.prepareStatement("SELECT * FROM Markettransactions m WHERE(SELECT a.taxid FROM Accounts a, Customers c WHERE c.username =? AND a.taxid = c.taxid AND a.accountid = m.accountid)");
	    ps.setString(1,username);
	    ResultSet mtrans = ps.executeQuery();

	    // print it out and format it is it looks pretty
	    System.out.println("TRANSACTION HISTORY FOR " + name + " (" + email + ")");
	    System.out.println("Account ID |    Date    |    Type    |   Total");
	    while(mtrans.next()){
		int accountid = mtrans.getInt("accountid");
		String date = mtrans.getString("date");
		String type = mtrans.getString("type");
		int total = mtrans.getInt("total");

		
		String accountidS = String.format("%03d",accountid);
		accountidS = String.format("%-11s", accountidS);
		date = String.format("%-10s",date);
		type = String.format("%-10s",type);
		
		System.out.println(accountidS + "|  " + date + "|  " + type +"|  " + total);
            }

	    
        } catch (SQLException e) {
            e.printStackTrace();
        }

	
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
    public void customerReport(){
	
    }

    // Delete the list of transactions from each of the accounts
    // Currently deletes all transactions from the system
    public void deleteTransactions() throws SQLException {

	try{
	    System.out.println("Deleting transactions for this month...");
            PreparedStatement ps = connection.prepareStatement("TRUNCATE Markettransactions");
	    ps.executeUpdate();
	} catch (SQLException e) {
            e.printStackTrace();
        }

	try{
            PreparedStatement ps = connection.prepareStatement("TRUNCATE Stocktransactions");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
