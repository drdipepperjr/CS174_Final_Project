import java.sql.*;
import java.util.Scanner;
import java.text.DecimalFormat;

public class TraderInterface{
    
    public static final String HOST = "jdbc:mysql://cs174a.engr.ucsb.edu:3306/domenicdipeppeDB";
    public static final String MOVIE = "jdbc:mysql://cs174a.engr.ucsb.edu:3306/moviesDB";
    public static final String REVIEW= "jdbc:mysql://cs174a.engr.ucsb.edu:3306/reviewsDB";
    public static final String USER = "domenicdipeppe";
    public static final String PWD = "907";
    
    Connection connection = null;
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
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
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
