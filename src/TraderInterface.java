import java.sql.*;
import java.util.Scanner;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class TraderInterface{
    
    public static final String HOST = "jdbc:mysql://cs174a.engr.ucsb.edu:3306/domenicdipeppeDB";
    public static final String MOVIE = "jdbc:mysql://cs174a.engr.ucsb.edu:3306/moviesDB";
    public static final String REVIEW= "jdbc:mysql://cs174a.engr.ucsb.edu:3306/reviewsDB";
    public static final String USER = "domenicdipeppe";
    public static final String PWD = "907";
    
    Connection connection = null;
    Connection movieconnection = null;
    Connection reviewconnection = null;
    private String username_s =null;
    private String password =null;
    private int taxID =0;
    private String date_s =null;
    
    public TraderInterface(String user, String pass,int tax, String day){
        username_s = user;
        password = pass;
        taxID = tax;
        date_s = day;
    }
    
    public void initialize() throws SQLException {
        
        DecimalFormat df = new DecimalFormat("#");
        df.setMaximumFractionDigits(2);
        
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date = dt.parse(date_s);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        //get last month date
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        
        String lastMonth_s= dt.format(cal.getTime());
        System.out.println(lastMonth_s);
        
        
        System.out.println("Welcome to the System");
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            int accountID=0;
            double balance=0;
            
            connection = DriverManager.getConnection(HOST, USER, PWD);
            

            PreparedStatement account = connection.prepareStatement("SELECT * FROM Accounts WHERE taxid= ?");
            account.setInt(1,taxID);
            ResultSet rs = account.executeQuery();
            
            if(rs.first()){
                accountID=rs.getInt("accountid");
                balance=rs.getDouble("balance");
                System.out.println("accountid: " +accountID+ " balance: "+balance);
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
                        System.out.println(taxID);
                        System.out.println(username_s);
                        System.out.println(accountID);
                        System.out.print("Enter amount to deposit:  ");
                        double amount = scan.nextDouble();
                        if(amount <= 0){
                            System.out.println("You must deposit an amount greater than 0.");
                        } else {
                            balance = balance + amount;
                            PreparedStatement adjustAccount = connection.prepareStatement("UPDATE Accounts SET balance=? WHERE taxid=?");
                            adjustAccount.setDouble(1,balance);
                            adjustAccount.setInt(2,taxID);
                            
                            adjustAccount.executeUpdate();
                            System.out.println("New Balance: " + df.format(balance));
                            adjustAccount.close();
                            
                            PreparedStatement addTransaction = connection.prepareStatement("INSERT into Markettransactions (accountid, date, type, total) VALUES(?,?,'deposit',?)");
                            addTransaction.setInt(1,accountID);
                            addTransaction.setString(2,date_s);
                            addTransaction.setDouble(3,amount);
                            addTransaction.executeUpdate();
                            addTransaction.close();
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
                            PreparedStatement adjustAccount = connection.prepareStatement("UPDATE Accounts SET balance=? WHERE taxid=?");
                            adjustAccount.setDouble(1,balance);
                            adjustAccount.setInt(2, taxID);
                            adjustAccount.executeUpdate();
                            System.out.println("New Balance: " + df.format(balance));
                            adjustAccount.close();
                            
                            PreparedStatement addTransaction = connection.prepareStatement("INSERT into Markettransactions (accountid, date, type, total) VALUES(?,?,'withdraw',?)");
                            addTransaction.setInt(1,accountID);
                            addTransaction.setString(2,date_s);
                            addTransaction.setDouble(3,amount);
                            addTransaction.executeUpdate();
                            addTransaction.close();
                            
                            break;
                        }
                    }
                    
                    // buy
                    else if(choice.equals("Buy")||choice.equals("buy")||choice.equals("B")||choice.equals("b")){
                        System.out.println("Enter stock name: ");
                        String stockID = scan.nextLine();
                        //if stockID exists
                        PreparedStatement stocks = connection.prepareStatement("SELECT * from Stocks WHERE stockid=?");
                        stocks.setString(1,stockID);
                        rs = stocks.executeQuery();
                        double currentPrice=0;
                        if(rs.first()){
                            currentPrice=rs.getDouble("currentprice");
                        }else {
                            System.out.println("Invalid stockid");
                            break;
                        }
                        
                        System.out.println("Enter how much of this stock you want to buy: ");
                        double quanity = scan.nextDouble();
                        double total = currentPrice * quanity;
                        if (total > balance){
                            System.out.println("Not enough in balance to buy");
                        } else{
                            //adjust balance
                            balance=balance-total;
                            PreparedStatement adjustAccount = connection.prepareStatement("UPDATE Accounts SET balance=? WHERE taxid=?");
                            adjustAccount.setDouble(1,balance);
                            adjustAccount.setInt(2, taxID);
                            adjustAccount.executeUpdate();
                            System.out.println("New Balance: " + df.format(balance));
                            adjustAccount.close();
                            //add transaction
                            PreparedStatement addTransaction = connection.prepareStatement("INSERT into Stocktransactions (accountid, date, type, stockid, price, qty, total) VALUES(?,?,'buy',?,?,?,?)");
                            addTransaction.setInt(1,accountID);
                            addTransaction.setString(2,date_s);
                            addTransaction.setString(3,stockID);
                            addTransaction.setDouble(4,currentPrice);
                            addTransaction.setDouble(5,quanity);
                            addTransaction.setDouble(6,total);
                            addTransaction.executeUpdate();
                            addTransaction.close();
                            
                            //update SharesOwned
                            //check if stock already owned
                            PreparedStatement ownCheck = connection.prepareStatement("SELECT * FROM SharesOwned WHERE taxid=? AND stockid=?");
                            
                            ownCheck.setInt(1,taxID);
                            ownCheck.setString(2,stockID);
                            rs = ownCheck.executeQuery();
                            if(rs.first()){
                                //owned so only update
                                double sq= rs.getDouble("stockqty");
                                sq= sq+quanity;
                                PreparedStatement adjustOwned = connection.prepareStatement("UPDATE SharesOwned SET stockqty=? WHERE taxid=? AND stockid=?");
                                adjustOwned.setDouble(1,sq);
                                adjustOwned.setInt(2, taxID);
                                adjustOwned.setString(3,stockID);
                                adjustOwned.executeUpdate();
                                adjustOwned.close();
                            }else{
                                //not owned sp add stocks into SharesOwned
                                PreparedStatement addSharesOwned = connection.prepareStatement("INSERT into SharesOwned (taxid, stockqty, stockid) VALUES(?,?,?)");
                                addSharesOwned.setInt(1,taxID);
                                addSharesOwned.setDouble(2,quanity);
                                addSharesOwned.setString(3,stockID);
                                addSharesOwned.executeUpdate();
                                addSharesOwned.close();
                                
                            }
                            
                        }
                        
                        stocks.close();
                        break;
                        
                    }
                    
                    // sell
                    else if(choice.equals("Sell")||choice.equals("sell")||choice.equals("S")||choice.equals("s")){
                        System.out.println("Enter stock name: ");
                        String stockID = scan.nextLine();
                        
                        System.out.println("Enter stock amount: ");
                        double stockam = scan.nextDouble();
                        

                        //if stockID exists
                        PreparedStatement ownCheck = connection.prepareStatement("SELECT * FROM SharesOwned WHERE taxid=? AND stockid=?");
                        
                        ownCheck.setInt(1,taxID);
                        ownCheck.setString(2,stockID);
                        rs = ownCheck.executeQuery();
                        if(rs.first()){
                            //owned so only update
                            double sq= rs.getDouble("stockqty");
                            if(sq >= stockam){
                                sq= sq -stockam;
                                //update sharesOwned
                                PreparedStatement adjustOwned = connection.prepareStatement("UPDATE SharesOwned SET stockqty=? WHERE taxid=? AND stockid=?");
                                adjustOwned.setDouble(1,sq);
                                adjustOwned.setInt(2, taxID);
                                adjustOwned.setString(3,stockID);
                                adjustOwned.executeUpdate();
                                adjustOwned.close();
                                
                                //check stock prices
                                PreparedStatement stocks = connection.prepareStatement("SELECT * from Stocks WHERE stockid=?");
                                stocks.setString(1,stockID);
                                rs = stocks.executeQuery();
                                double currentPrice=0;
                                if(rs.first()){
                                    currentPrice=rs.getDouble("currentprice");
                                }else {
                                    System.out.println("Invalid stockid");
                                    break;
                                }
                                double total = currentPrice * stockam;
                                //adjust balance
                                balance=balance+total;
                                PreparedStatement adjustAccount = connection.prepareStatement("UPDATE Accounts SET balance=? WHERE taxid=?");
                                adjustAccount.setDouble(1,balance);
                                adjustAccount.setInt(2, taxID);
                                adjustAccount.executeUpdate();
                                System.out.println("New Balance: " + df.format(balance));
                                adjustAccount.close();
                                
                                
                                //add transaction
                                PreparedStatement addTransaction = connection.prepareStatement("INSERT into Stocktransactions (accountid, date, type, stockid, price, qty, total) VALUES(?,?,'sell',?,?,?,?)");
                                addTransaction.setInt(1,accountID);
                                addTransaction.setString(2,date_s);
                                addTransaction.setString(3,stockID);
                                addTransaction.setDouble(4,currentPrice);
                                addTransaction.setDouble(5,stockam);
                                addTransaction.setDouble(6,total);
                                addTransaction.executeUpdate();
                                addTransaction.close();
                                
                            }else{
                                System.out.println("You don't own enough this stock");
                                break;
                            }
                        }else{
                            //not owned
                            System.out.println("You don't own this stock");
                            break;
                        }
                        
                        //get balance
                        //get current stock price
                        break;
                    }
                    
                    //get market balance
                    else if(choice.equals("Get Balance")||choice.equals("Get balance")||choice.equals("get balance")){
                        System.out.println("Balance: $" + df.format(balance));
                        break;
                    }
                    
                    //get stock transaction history
                    else if(choice.equals("Get Monthly Stock Transactions")||choice.equals("Get monthly stock transactions")||choice.equals("get monthly stock transactions")){
                        
                        Date tempDate = date;

                        String tempDate_s= dt.format(tempDate);
                        
                        System.out.println("Here are the transactions since "+ lastMonth_s);
                        while(!tempDate_s.equals(lastMonth_s)){
                            //debugging code
                            //System.out.println("temp: "+ tempDate_s);
                            PreparedStatement stocksTrans = connection.prepareStatement("SELECT * FROM Stocktransactions WHERE date=? AND accountid=accountID");
                            stocksTrans.setString(1,tempDate_s);
                            rs = stocksTrans.executeQuery();
                            //System.out.println(accountID);
                            while (rs.next()){
                                String transdate =rs.getString("date");
                                String stock =rs.getString("stockid");
                                String type =rs.getString("type");
                                double price= rs.getDouble("price");
                                double qty= rs.getDouble("qty");
                                double total= rs.getDouble("total");
                                System.out.println(transdate +"\t"+stock+"\t"+type+"\t$"+price+"\t"+df.format(qty)+"\tTotal:$"+ total);
                                
                            }
                            cal.setTime(tempDate);
                            cal.add(Calendar.DATE, -1);
                            tempDate=cal.getTime();
                            tempDate_s= dt.format(tempDate);
                        }
                        break;
                        
                    }
                    
                    //List Current Stock Prices
                    else if(choice.equals("List Current Stock Prices")||choice.equals("List current stock prices")||choice.equals("list current stock prices")){
                        
                        PreparedStatement stocks = connection.prepareStatement("SELECT * FROM Stocks");
                        rs = stocks.executeQuery();
                        while (rs.next()){
                            String stock =rs.getString("stockid");
                            double currentprice=rs.getDouble("currentprice");
                            String name =rs.getString("name");
                            String dob =rs.getString("dob");
                            System.out.println(stock+"\tCurrent Price: $"+ df.format(currentprice) +"\tname: "+ name+"\tDOB: "+ dob);
                            //stock has these contracts
                            PreparedStatement contracts = connection.prepareStatement("SELECT * FROM Contracts WHERE stockid=?");
                            contracts.setString(1,stock);
                            ResultSet crs = contracts.executeQuery();
                            while (crs.next()){
                                
                                String movietitle =crs.getString("movietitle");
                                String role =crs.getString("role");
                                int year =crs.getInt("year");
                                double value=crs.getDouble("value");
                                System.out.println("Has movie contract with: "+ movietitle + " as a(n) " +role + " in "+ year+" for a value of: $"+  df.format(value));
                            }
                            
                        }
                        break;
                        
                    }
                    
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
                            System.out.print("Rating: " + rating);
                            int year= mcrs.getInt("production_year");
                            System.out.println("\tYear: " + year);
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
                                System.out.println("\tYear: " + year);
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
                            System.out.println("Author: " + reviewAuthor +"\tReview: " + reviewText);
                            
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
