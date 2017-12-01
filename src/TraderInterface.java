import java.sql.*;
import java.util.Scanner;
<<<<<<< HEAD

public class TraderInterface {

    public static final String HOST = "jdbc:mysql://cs174a.engr.ucsb.edu:3306/domenicdipeppeDB";
=======
import java.text.DecimalFormat;

public class TraderInterface{
    
    public static final String HOST = "jdbc:mysql://cs174a.engr.ucsb.edu:3306/domenicdipeppeDB";
    public static final String MOVIE = "jdbc:mysql://cs174a.engr.ucsb.edu:3306/moviesDB";
    public static final String REVIEW= "jdbc:mysql://cs174a.engr.ucsb.edu:3306/reviewsDB";
>>>>>>> 222f431a2c2539a9768cab7e105a507420193f4b
    public static final String USER = "domenicdipeppe";
    public static final String PWD = "907";
    
    Connection connection = null;
<<<<<<< HEAD
    
    public TraderInterface(String username){
    }

    // Set up the connection and prompt the customer
    public void beginSession() throws SQLException {
	
	try {
=======
    Connection movieconnection = null;
    Connection reviewconnection = null;
    private String username =null;
    private String password =null;
    
    public TraderInterface(String user, String pass){
        username = user;
        password = pass;
    }
    
    public void initialize() throws SQLException {
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        System.out.println("Welcome to the System");
        
        try {
>>>>>>> 222f431a2c2539a9768cab7e105a507420193f4b
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
<<<<<<< HEAD
	
	try {
            connection = DriverManager.getConnection(HOST, USER, PWD);
	}
	
	catch (SQLException e) {
	    e.printStackTrace();
	}

	
	Scanner scanner = new Scanner(System.in);

	
	System.out.println("Enter on of the following options:");
	System.out.println("[ Options: Deposit, Withdraw, Buy, Sell, Get Balence, Get Monthly Stock Transactions, Logout, List Current Stock Prices, List Movie Information, Get Top Movies, Get Reviews]");
	String choice = scanner.nextLine();

	// if("logout".equals(choice)){
	endSession();

	/*
	while(true) {
	    if(choice.equals("Deposit")){
		double amount = scanner.nextDouble();
		scanner.nextLine(); // There's an extra \n character to get rid of
		deposit(amount); 
	}
	*/

    }
   
    public void endSession() throws SQLException{
	try{
	    if(connection != null)
		connection.close();
	} catch (SQLException e){
	    e.printStackTrace();
	}
	System.out.println("Thank you for using the StarsRUs system");
    }

    // TODO: make functions for each

    // ex.
    // public void deposit(double amount){ }
}

// Here is your code, I just moved it. please make seperate functions for each option

                    //Options
                    /*  boolean loggedin=true;
                    while (loggedin){
		    // Menu starts from here

			while(true){
                            //deposit
                            if(choice.equals("Deposit")||choice.equals("deposit")||choice.equals("D")||choice.equals("d")){
                                System.out.print("Enter amount to deposit:  ");
                                double amount = scanner.nextDouble();
                                if(amount <= 0){
                                    System.out.println("You must deposit an amount greater than 0.");
                                } else {
                                    System.out.println("You have deposited " + (amount));
                                    //TODO:get balence
                                    //balence=balence+amount;
                                    //TODO: update Balence
                                    System.out.println("New Balance = " + account.getBalance());
                                }
                            }
                            // withdraw
                            else if(choice.equals("Withdraw")||choice.equals("withdraw")||choice.equals("W")||choice.equals("w")){
                                System.out.print("Enter amount to be withdrawn: ");
                                double amount = scanner.nextDouble();
                                //TODO:get balence
                                if (amount > balance){
                                    System.out.println("Not enough in balence");
                                } else{
                                    account.withdraw(amount);
                                    //balence=balence+amount;
                                    //TODO: update Balence
                                    System.out.println("New Balance = " + account.getBalance());
                                }
                            }
                            // buy
                            else if(choice.equals("Buy")||choice.equals("buy")||choice.equals("B")||choice.equals("b")){
                                System.out.println("Enter stock name: ");
                                String stockID = scanner.nextLine();
                                //if stockID exists
                                //get balence
                                //get current stock price
                                //check if enough money
                                System.out.println("New stock balence:");
                                
                            }
                            
                            // sell
                            else if(choice.equals("Sell")||choice.equals("sell")||choice.equals("S")||choice.equals("s")){
                                System.out.println("Enter stock name: ");
                                String stockID = scanner.nextLine();
                                //if stockID exists
                                //get balence
                                //get current stock price
                                System.out.println("New Stock balence:");
                            }
                            //get market balence
                            else if(choice.equals("Get Balence")||choice.equals("Get balence")||choice.equals("get balence")){
                                
                            }
                            
                            //get stock transaction history
                            else if(choice.equals("Get Monthly Stock Transactions")||choice.equals("Get monthly stock transactions")||choice.equals("get monthly stock transactions")){
                                
                            }
                            
                            //List Current Stock Prices
                            else if(choice.equals("List Current Stock Prices")||choice.equals("List current stock prices")||choice.equals("list current stock prices")){
                                
                            }
                            
                            //List movie information
                            else if(choice.equals("List Movie Information")||choice.equals("List movie information")||choice.equals("list movie information")){
                                
                            }
                            
                            //Get Top movies
                            else if(choice.equals("Get Top Movies")||choice.equals("Get top movies")||choice.equals("get top movies")){
                                
                            }
                            
                            //Get Reviews
                            else if(choice.equals("Get Reviews")||choice.equals("Get reviews")||choice.equals("get reviews")){
                                
                            }
                            
                            //logout
                            else if(choice.equals("Logout")||choice.equals("logout")){
                                break;
                            }
                            else {
                                System.out.print("Not An Option");
                                
                            }
                        }*/
=======
        
        try {
            int taxID=0;
            int accountid=0;
            double balance=0;
            
            connection = DriverManager.getConnection(HOST, USER, PWD);
            
            PreparedStatement customer = connection.prepareStatement("SELECT * FROM Customers WHERE username=username");
            ResultSet rs = customer.executeQuery();
            while (rs.next()){
                taxID=rs.getInt("taxid");
            }
            PreparedStatement account = connection.prepareStatement("SELECT * FROM Accounts WHERE taxid= taxID");
            rs = account.executeQuery();
            while (rs.next()){
                accountid=rs.getInt("accountid");
                balance=rs.getDouble("balance");
            }
            boolean loggedin=true;
            
            while (loggedin){
                Scanner scan = new Scanner(System.in);
                
                // Menu starts from here
                
                System.out.println("Enter on of the following options:");
                System.out.println("[ Options: Deposit, Withdraw, Buy, Sell, Get Balance, Get Monthly Stock Transactions, Logout, List Current Stock Prices, List Movie Information, Get Top Movies, Get Reviews]");
                String choice = scan.nextLine();
                
                //Options
                while(true){
                    
                    //deposit
                    if(choice.equals("Deposit")||choice.equals("deposit")||choice.equals("D")||choice.equals("d")){
                        System.out.print("Enter amount to deposit:  ");
                        double amount = scan.nextDouble();
                        if(amount <= 0){
                            System.out.println("You must deposit an amount greater than 0.");
                        } else {
                            System.out.println("You have deposited " + amount);
                            balance= balance + amount;
                            PreparedStatement adjustAccount = connection.prepareStatement("UPDATE Accounts SET balance=? WHERE taxid=taxID");
                            adjustAccount.setDouble(1,balance);
                            adjustAccount.executeUpdate();
                            System.out.println("New Balance: " + df.format(balance));
                            adjustAccount.close();
                            break;
                        }
                    }
                    
                    // withdraw
                    else if(choice.equals("Withdraw")||choice.equals("withdraw")||choice.equals("W")||choice.equals("w")){
                        System.out.print("Enter amount to be withdrawn: ");
                        double amount = scan.nextDouble();
                        if (amount > balance){
                            System.out.println("Not enough in balance");
                        } else{
                            balance=balance-amount;
                            PreparedStatement adjustAccount = connection.prepareStatement("UPDATE Accounts SET balance=? WHERE taxid=taxID");
                            adjustAccount.setDouble(1,balance);
                            adjustAccount.executeUpdate();
                            System.out.println("New Balance: " + df.format(balance));
                            adjustAccount.close();
                            break;
                        }
                    }
                    /*
                    // buy
                    else if(choice.equals("Buy")||choice.equals("buy")||choice.equals("B")||choice.equals("b")){
                        System.out.println("Enter stock name: ");
                        String stockID = scan.nextLine();
                        //if stockID exists
                        //get balance
                        //get current stock price
                        //check if enough money
                        System.out.println("New stock balance:");
                        break;
                        
                    }
                    
                    // sell
                    else if(choice.equals("Sell")||choice.equals("sell")||choice.equals("S")||choice.equals("s")){
                        System.out.println("Enter stock name: ");
                        String stockID = scan.nextLine();
                        //if stockID exists
                        //get balance
                        //get current stock price
                        System.out.println("New Stock balance:");
                        break;
                    }
                    */
                    //get market balance
                    else if(choice.equals("Get Balance")||choice.equals("Get balance")||choice.equals("get balance")){
                        System.out.println("Balance: $" + df.format(balance));
                        break;
                    }
                    /*
                    //get stock transaction history
                    else if(choice.equals("Get Monthly Stock Transactions")||choice.equals("Get monthly stock transactions")||choice.equals("get monthly stock transactions")){
                        break;
                        
                    }
                    
                    //List Current Stock Prices
                    else if(choice.equals("List Current Stock Prices")||choice.equals("List current stock prices")||choice.equals("list current stock prices")){
                        break;
                        
                    }
                    */
                    //List movie information
                    else if(choice.equals("List Movie Information")||choice.equals("List movie information")||choice.equals("list movie information")){
                        System.out.println("What movie do you need info on?: ");
                        String movietitle = scan.nextLine();
                        movieconnection = DriverManager.getConnection(MOVIE, USER, PWD);
                        PreparedStatement mc = movieconnection.prepareStatement("SELECT * FROM Movies WHERE title=?");
                        mc.setString(1,movietitle);
                        ResultSet mcrs = mc.executeQuery();
                        if(!mcrs.first()){
                            System.out.println("No movies with this name in moviesDB");
                        }
                        else{
                            mcrs.first();
                            float rating=mcrs.getFloat("rating");
                            System.out.println("Rating: " + rating);
                            int year= mcrs.getInt("production_year");
                            System.out.println("Year: " + year);
                        }
                        break;
                        
                    }
                    
                    //Get Top movies
                    else if(choice.equals("Get Top Movies")||choice.equals("Get top movies")||choice.equals("get top movies")){
                        System.out.println("Starting from what year: ");
                        int startYear = scan.nextInt();
                        System.out.println("To what year: ");
                        int endYear = scan.nextInt();
                        
                        movieconnection = DriverManager.getConnection(MOVIE, USER, PWD);
                        while (endYear-startYear>=0){
                            PreparedStatement mc = movieconnection.prepareStatement("SELECT * FROM Movies WHERE production_year=? AND rating=5");
                            mc.setInt(1,startYear);
                            ResultSet mcrs = mc.executeQuery();

                            while(mcrs.next()){
                                String title=mcrs.getString("title");
                                System.out.print("Title: " + title);
                                int year= mcrs.getInt("production_year");
                                System.out.println("   Year: " + year);
                            }
                            startYear++;

                        }
                        System.out.println("End of list for top moves");
                        break;
                        
                    }
                    
                    //Get Reviews
                    else if(choice.equals("Get Reviews")||choice.equals("Get reviews")||choice.equals("get reviews")){
                        System.out.println("What movie do you want reviews for?: ");
                        String movietitle = scan.nextLine();
                        movieconnection = DriverManager.getConnection(MOVIE, USER, PWD);
                        PreparedStatement mc = movieconnection.prepareStatement("SELECT * FROM Movies WHERE title=?");
                        mc.setString(1,movietitle);
                        ResultSet mcrs = mc.executeQuery();
                        int id=0;
                        if(!mcrs.first()){
                            System.out.println("No movies with this name in moviesDB");
                        }
                        else{
                            id= mcrs.getInt("id");
                        }

                        reviewconnection = DriverManager.getConnection(MOVIE, USER, PWD);
                        PreparedStatement rc = reviewconnection.prepareStatement("SELECT * FROM Reviews WHERE movie_id=?");
                        rc.setInt(1,id);
                        ResultSet rcrs = rc.executeQuery();
                        while (rcrs.next()){
                            String reviewAuthor=rcrs.getString("author");
                            String reviewText=rcrs.getString("review");
                            System.out.println("Author: " + reviewAuthor +"  Review: " + reviewText);
                            
                        }
                        
                        System.out.println("End of reviews.");


                        break;
                    }
                    
                    
                    //logout
                    else if(choice.equals("Logout")||choice.equals("logout")){
                        loggedin=false;
                        break;
                    }
                    else {
                        System.out.println("Not An Option");
                        break;
                    }
                }
            }

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
    

}
>>>>>>> 222f431a2c2539a9768cab7e105a507420193f4b
