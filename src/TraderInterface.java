import java.sql.*;
import java.util.Scanner;

public class TraderInterface {

    public static final String HOST = "jdbc:mysql://cs174a.engr.ucsb.edu:3306/domenicdipeppeDB";
    public static final String USER = "domenicdipeppe";
    public static final String PWD = "907";
    
    Connection connection = null;
    
    public TraderInterface(String username){
    }

    // Set up the connection and prompt the customer
    public void beginSession() throws SQLException {
	
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
