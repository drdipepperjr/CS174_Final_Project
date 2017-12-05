import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.util.Calendar;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class BrokerageInterface {

    public static final String HOST = "jdbc:mysql://cs174a.engr.ucsb.edu:3306/domenicdipeppeDB";
    public static final String USER = "domenicdipeppe";
    public static final String PWD = "907";
    
    Connection connection = null;
    private String date_s = null;
    Calendar cal;
    Date date=null;
    DecimalFormat df;
    SimpleDateFormat dt;
    String isOpen = null;
    String name;

    public BrokerageInterface(String name){

	df = new DecimalFormat("#");
	df.setMaximumFractionDigits(2);
	this.name = name;
	
    }
    
    public void initialize() throws SQLException {
	System.out.println("Welcome to the System, " + name);
	
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


	// Get the date from the DB
	try{
	    PreparedStatement ps = connection.prepareStatement("SELECT * FROM Date");
	    ResultSet dateSet = ps.executeQuery();

	    while(dateSet.next()){
	        isOpen = dateSet.getString("isopen");
		date_s = dateSet.getString("date");
		dt = new SimpleDateFormat("yyyy-MM-dd");
		try {
		    date = dt.parse(date_s);
		}
		catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

	System.out.print("The current date is " + date_s + ".     The market is currently ");

	if("no".equals(isOpen))
	    System.out.print("closed.\n");

	else if("yes".equals(isOpen))
	    System.out.println("open. \n");
	
	boolean loggedin = true;
	Scanner scanner = new Scanner(System.in);

	while(loggedin){
	    System.out.println("What would you like to do? (Add Interest / Generate Monthly Statement / List Active Customers / Generate DTER / Customer Report / Delete Transactions/ Open or Close Market / Change Date / Add Employee / Change Stock Prices / Add Stock / Logout");
	    String choice = scanner.nextLine();
	    choice = choice.toLowerCase();

	    if("add interest".equals(choice) || "ai".equals(choice)){
		addInterest();
	    }
	    
	    else if("monthly statement".equals(choice) || "ms".equals(choice)){
		String username;
		System.out.print("Please enter the customer's username to generate their monthly statement: ");
		username = scanner.nextLine();
		generateMonthlyStatement(username);
	    }

	    else if("customer report".equals(choice) || "cr".equals(choice)){
                String username;
                System.out.print("Please enter the customer's username to generate their report: ");
                username = scanner.nextLine();
                customerReport(username);
	    }
	    
	    else if("list active customers".equals(choice)){
		listActiveCustomers();
	    }

	    else if("dter".equals(choice)){
		generateDTER();
	    }
	    
	    else if("delete transactions".equals(choice)){

		System.out.println("Are you sure you want to delete ALL transactions for this month? (yes/no): ");
		String lastchance = scanner.nextLine();
		if("yes".equals(lastchance)){
		    deleteTransactions();
		}
		else
		    System.out.println("Transactions for this month were NOT deleted");
	    }
	    
	    else if("logout".equals(choice)){
		System.out.println("Logging out...");
		loggedin = false;
	    }

	    else if("open market".equals(choice) || "open".equals(choice)){
		if(isOpen.equals("yes")){
		    System.out.println("The market is already open");
		    
		}

		else{
		    try{
			PreparedStatement ps = connection.prepareStatement("update Date set isopen =?");
			ps.setString(1,"yes");
			ps.executeUpdate();
			System.out.println("The market is now open.");
			isOpen = "yes";
		    } catch (SQLException e){
			e.printStackTrace();
		    }
		    
		}
	    }

	    else if("close market".equals(choice) || "close".equals(choice)){
		if(isOpen.equals("no")){
		    System.out.println("The market is already closed");	   
		}

		else{
		    try{
			PreparedStatement ps = connection.prepareStatement("update Date set isopen =?");
			ps.setString(1,"no");
			ps.executeUpdate();
			System.out.println("The market is now closed.");
			isOpen = "no";
		    } catch (SQLException e){
			e.printStackTrace();
		    }
		}   
	    }

	    else if("change date".equals(choice) || "cd".equals(choice)){

		System.out.print("What would you like to change the date to? (yyyy-mm-dd): ");
		String newDate = scanner.nextLine();
		
		try{

		    PreparedStatement ps = connection.prepareStatement("update Date set date = ?");
		    ps.setString(1,newDate);
		    ps.executeUpdate();
		    System.out.println("Date changed to   " + newDate);
		    
		} catch (SQLException e){
		    e.printStackTrace();
		}
	    }

	    else if("change stock prices".equals(choice) || "csp".equals(choice){
		
		Double price;
		String stockid;    
		System.out.print("Which stock would you like to change? :   ");
		stockid = scanner.nextLine();
		Sytem.out.print("What is the new price of the stock? :   ");
		price = scanner.nextDouble();
		scanner.nextLine();
		    
	    	try{
			PreparedStatement ps = connection.prepareStatement("update Stocks set stockid = ? AND set price = ? where stockid = ?);
			ps.setString(1,stockid);
			ps.setDouble(2,price);
		    	ps.setString(3,stockid);
			ps.executeUpdate();						   
	        } catch (SQLException e){
			e.printStackTrace();
		}
			
	    }
	    
	    else if("add stock".equals(choice) || "as".equals(choice){
		    
		    Double price;
		    String stockid, dob, name;
		    System.out.print("What is the name of the new stock? :   ");
		    stockid = scanner.nextLine();
		    System.out.print("What is the price of the stock? :    ");
		    price = scanner.nextDouble();
		    scanner.nextLine();
		    System.out.print("What is the name of the actor? :   ");
		    name = scanner.nextLine();
		    System.out.print("What is the Date of Birth of the actor? (yyyy-mm-dd) :   ");
		    dob = scanner.nextLine();
		    
		    try{
			    PreparedStatement ps = connection.prepareStatement("insert into stocks (stockid,currentprice,dob,name) values (?,?,?,?));
			    ps.setString(1,stockid);
			    ps.setDouble(2,price);
		            ps.setString(3,dob);
		            ps.setString(4,name);
			    ps.executeUpdate();
	            } catch(SQLException e){
			    e.printStackTrace();
		    }
			
	     }
									       
									       
	    else if("add employee".equals(choice) || "ae".equals(choice)){

		String newusername;
		String newpassword;
                

		System.out.print("Please enter the new username: ");
		newusername = scanner.nextLine();
                
		// Check DB for existing user
		PreparedStatement ps = connection.prepareStatement("SELECT * from Employees WHERE username=?");
		ps.setString(1,newusername);
		ResultSet existinguser = ps.executeQuery();
		if(!existinguser.first()){
		    String name, address, state, phone, email, SSN;
		    int taxid;
                    
		    // Ask user for personal info
		    System.out.print("Please enter the password: ");
		    newpassword = scanner.nextLine();
		    System.out.print("Please enter their name: ");
		    name = scanner.nextLine();
		    System.out.print("Please enter their address: ");
		    address = scanner.nextLine();
		    System.out.print("Please enter their state they live in (2 character state code): ");
		    state = scanner.nextLine();
		    System.out.print("Please enter their phone number ( (xxx)xxxxxxx ): ");
		    phone = scanner.nextLine();
		    System.out.print("Please enter their email: ");
		    email = scanner.nextLine();
		    System.out.print("Please enter their Tax Identification Number (4 digits): ");
		    taxid = scanner.nextInt();
		    scanner.nextLine();
		    System.out.print("Please enter their Social Security Number (xxx-xx-xxxx): ");
		    SSN = scanner.nextLine();
                    
		    // Insert values into the DB
		    ps = connection.prepareStatement("INSERT into Employees (name, username, password, address, state, phone, email, taxid, SSN) VALUES(?,?,?,?,?,?,?,?,?)");
		    ps.setString(1,name);
		    ps.setString(2,newusername);
		    ps.setString(3,newpassword);
		    ps.setString(4,address);
		    ps.setString(5,state);
		    ps.setString(6,phone);
		    ps.setString(7,email);
		    ps.setInt(8,taxid);
		    ps.setString(9,SSN);
		    ps.executeUpdate();
		    
		    System.out.println("The new employee has been added");
		}
		
		
		else
		    System.out.println("Sorry, username is already taken. Please enter a unique username");
	    }
	    
	    
	    else{
		System.out.println("Sorry, that was not a valid option.");
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
	 try{
	     System.out.println("Adding interest to all accounts...");
	     
	     PreparedStatement ps = connection.prepareStatement("SELECT * from Customers");
	     ResultSet customers = ps.executeQuery();
	     while(customers.next()){

		 String username = customers.getString("username");
		 double balance = 0;
		 int accountid = 0;
		 
		 PreparedStatement ps1 = connection.prepareStatement("select a.accountid, a.balance from Customers c, Accounts a where c.username = ? AND a.taxid = c.taxid");
		 ps1.setString(1,username);
		 ResultSet accounts = ps1.executeQuery();

		 while(accounts.next()){
		     accountid = accounts.getInt("accountid");
		     balance = accounts.getDouble("balance");
		 }

		 double balanceAccum = balance;
		 double DAB = 0;
		 Date tempDate = date;
		 String tempDate_s = dt.format(tempDate);
		 
		 try {
		     PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM Markettransactions where accountid = ? ");
		     ps2.setInt(1,accountid);
		     ResultSet transactions = ps2.executeQuery();
		     System.out.println("Accountid: " + accountid);

		     transactions.afterLast();
		     while(transactions.previous()){
			 
			 String transDate_s = transactions.getString("date");

			 Date transDate=null;
			 try {
			     transDate = dt.parse(transDate_s);
			 }
			 catch (Exception e) {
			     e.printStackTrace();
			 }
			 
			 double total  = transactions.getDouble("total");
			 String type = transactions.getString("type");
			 if(type.equals("withdraw") || type.equals("buy")){
			     total *= 1;
			 }
			 
			 if(transDate.equals(tempDate)){
			     balanceAccum -= total;
			 }
			 
			 else if(!transDate.equals(tempDate)){
			     DAB += balanceAccum*(getDayFromDate(tempDate_s) - getDayFromDate(transDate_s));
			     balanceAccum += total;
			     tempDate = transDate; 
			 }

			 System.out.println("balanceaccum is: " + balanceAccum);
		     }

		     int days_left = getDayFromDate(tempDate_s);
		     DAB += balanceAccum * days_left;
		     double interest = DAB*0.03/getDayFromDate(date_s);
		     System.out.println("Interest payment is: " + interest);
		     
		     try{
			 PreparedStatement ps3 = connection.prepareStatement("insert into Markettransactions (accountid, date, type, total) values (?,?,?,?)");
			 ps3.setInt(1,accountid);
			 ps3.setString(2,date_s);
			 ps3.setString(3,"interest");
			 ps3.setDouble(4,interest);
			 ps3.executeUpdate();
		     } catch (SQLException e){
			 e.printStackTrace();
		     }

		     try{
			 PreparedStatement ps4 = connection.prepareStatement("Update Accounts set balance = ? where accountid = ?");
			 double newBalance = balance + interest;
			 ps4.setDouble(1, newBalance);
			 ps4.setInt(2, accountid);
			 ps4.executeUpdate();

		     } catch (SQLException e){
			 e.printStackTrace();
		     }
		     
		 } catch (SQLException e){
		     e.printStackTrace();
		 }
		 
	     }

	 }catch (SQLException e){
            e.printStackTrace();
	 }
	 
    }

    public int getDayFromDate(String date){
	String day = "";
	for(int i = date.length() -1; i > 0; i--){
	    char num = date.charAt(i);
	    if((num == '0' && i != date.length()-1) || num == '-') break;
	    day = num + day;
	}
	int result = Integer.parseInt(day);
	return result;
    }
    
    // Given a customer,  do the following for each account she/he owns:
    // generate a list of all transactions that have occurred in the current month.
    // This statement should list the name and email address of the customer.
    public void generateMonthlyStatement(String username) throws SQLException{ //(string username?)
	
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
	// Market transactions
	try{
	    PreparedStatement ps = connection.prepareStatement("SELECT * FROM Markettransactions m WHERE(SELECT a.taxid FROM Accounts a, Customers c WHERE c.username =? AND a.taxid = c.taxid AND a.accountid = m.accountid)");
	    ps.setString(1,username);
	    ResultSet mtrans = ps.executeQuery();

	    // print it out and format it is it looks pretty
	    System.out.println("TRANSACTION HISTORY FOR " + name + " (" + email + ")");
	    System.out.println("MARKET TRANSACTIONS");
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

	// Stock transactions
	try{
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Stocktransactions m WHERE(SELECT a.taxid FROM Accounts a, Customers c WHERE c.username =? AND a.taxid = c.taxid AND a.accountid = m.accountid)");
            ps.setString(1,username);
            ResultSet strans = ps.executeQuery();

            // print it out and format it is it looks pretty
	    System.out.println("\nSTOCK TRANSACTIONS");
	    System.out.println("Account ID |    Date    |    Type    |  Stock ID  |   Price   |  Quantity  |  Total ");

	    while(strans.next()){
                int accountid = strans.getInt("accountid");
                String date = strans.getString("date");
                String type = strans.getString("type");
		String stockid = strans.getString("stockid");
		int price = strans.getInt("price");
		int qty = strans.getInt("qty");
		int total = strans.getInt("total");
		
                String accountidS = String.format("%03d",accountid);
		String priceS = String.format("%-9d",price);
		String qtyS = String.format("%-10d",qty);
		accountidS = String.format("%-11s", accountidS);
                date = String.format("%-10s",date);
                type = String.format("%-10s",type);
		
                System.out.println(accountidS + "|  " + date + "|  " + type +"|    " + stockid + "     | " + priceS + " | " + qtyS + " | " + total );
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

	
    }
    
    
    // Generate a list of all customers who have traded (buy or sell) at least 1000 shares in the past month
    public void listActiveCustomers() throws SQLException{
	
	try{
	    PreparedStatement ps = connection.prepareStatement("select c.name from Customers c, Accounts a, Stocktransactions s where c.taxid = a.taxid AND a.accountid = s.accountid group by a.accountid having sum(qty) > 1000");
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
    public void generateDTER() throws SQLException{

	try{
	    System.out.println("Generating Drug and Tax Evasion Report (DTER)");
	    PreparedStatement ps = connection.prepareStatement("select c.username, c.state, sum(total) as totalEarned from Customers c, Accounts a, Markettransactions m  where c.taxid = a.taxid AND a.accountid = m.accountid AND (m.type = ? OR m.type = ?) group by c.username having totalEarned > 10000");
	    ps.setString(1,"sell");
	    ps.setString(2,"interest");
	    ResultSet DTER = ps.executeQuery();
	    while(DTER.next()){
		String username = DTER.getString("username");
		String state = DTER.getString("state");
		double totalEarned = DTER.getDouble("totalEarned");
		System.out.println(username + ", " + state + ", " + totalEarned);
	    }
	    
	    
	} catch (SQLException e) {
	    e.printStackTrace();
	}	
    }

    // Generate a list of all accounts associated with a particular customer and the current balance.
    public void customerReport(String username) throws SQLException{
	String name = "";
	String email = "";
	
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
	
	try{
            PreparedStatement ps = connection.prepareStatement("Select a.accountid,a.balance from Customers c, Accounts a where c.username=? AND a.taxid = c.taxid");
	    ps.setString(1,username);
	    ResultSet abalance = ps.executeQuery();
	    
	    System.out.println("Customer Report for " + name + " (" + email + ")\n");
	    System.out.println("Account ID |   Type   |  Balance");
	    while(abalance.next()){
		int aid = abalance.getInt("accountid");
		String balance = ""+abalance.getInt("balance");
		String aidS= String.format("%03d",aid);
		aidS = String.format("%-11s", aidS);
		String type = " Market ";
		System.out.println(aidS + "| " + type + " | $" + balance);
	    }
	    
	} catch (SQLException e) {
            e.printStackTrace();
        }
	
	
	try{
	    
	    PreparedStatement ps = connection.prepareStatement("Select s.stockqty,s.stockid from SharesOwned s, Customers c where c.username =? AND s.taxid = c.taxid");
	    ps.setString(1,username);
	    ResultSet stocks = ps.executeQuery();

	    System.out.print("Stocks owned (Name,Quantity):    ");
	    
	    while(stocks.next()){
		
		String qty = ""+stocks.getInt("stockqty");
		String stockid = stocks.getString("stockid");
		System.out.print("("+stockid + "," + qty + ")  ");
		
	    }
	    System.out.println("");
	    
	} catch (SQLException e) {
            e.printStackTrace();
        }
	
	
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
