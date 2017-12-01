import java.sql.*;
import java.util.Scanner;
import java.text.DecimalFormat;


public class StockTradeMain {
    public int month=1;
    public int data=1;
    public int year=2017;
    
    public static final String HOST = "jdbc:mysql://cs174a.engr.ucsb.edu:3306/domenicdipeppeDB";
    public static final String USER = "domenicdipeppe";
    public static final String PWD = "907";
    
    public static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        try {
            connection = DriverManager.getConnection(HOST, USER, PWD);
            
            // Ask for new or existing user
            while(true){
                System.out.print("Are you a new or existing user? (new/existing/quit): ");
                String input = scanner.nextLine();
                if("new".equals(input)){
                    String newusername;
                    String newpassword;
                    
                    while(true){
                        System.out.print("Please enter your new username: ");
                        newusername = scanner.nextLine();
                        
                        // Check DB for existing user
                        PreparedStatement ps = connection.prepareStatement("SELECT * from Customers WHERE username=?");
                        ps.setString(1,newusername);
                        ResultSet existinguser = ps.executeQuery();
                        if(!existinguser.first()){
                            String name, address, state, phone, email, SSN;
                            int taxid;
                            
                            // Ask user for personal info
                            System.out.print("Please enter your password: ");
                            newpassword = scanner.nextLine();
                            System.out.print("Please enter your name: ");
                            name = scanner.nextLine();
                            System.out.print("Please enter your address: ");
                            address = scanner.nextLine();
                            System.out.print("Please enter the state you live in (2 character state code): ");
                            state = scanner.nextLine();
                            System.out.print("Please enter your phone number ( (xxx)xxxxxxx ): ");
                            phone = scanner.nextLine();
                            System.out.print("Please enter your email: ");
                            email = scanner.nextLine();
                            System.out.print("Please enter your Tax Identification Number (4 digits): ");
                            taxid = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Please enter your Social Security Number (xxx-xx-xxxx): ");
                            SSN = scanner.nextLine();
                            
                            // Insert values into the DB
                            ps = connection.prepareStatement("INSERT into Customers (name, username, password, address, state, phone, email, taxid, SSN) VALUES(?,?,?,?,?,?,?,?,?)");
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
                            
                            System.out.println("Thank you for joining us. Please login to continue");
                            break;
                        }
                        else
                            System.out.println("Sorry, username is already taken. Please enter a unique username");
                    }
                    
                }
                
                else if("existing".equals(input)){// Prompt user for username and password
                    String username, password;
                    System.out.println("Please enter your username and password");
                    System.out.print("username: ");
                    username = scanner.nextLine();
                    System.out.print("password: ");
                    password = scanner.nextLine();
                    
                    // Create SQL query
                    PreparedStatement ps = connection.prepareStatement("SELECT * from Customers WHERE username=? AND password=?");
                    ps.setString(1,username);
                    ps.setString(2,password);
                    
                    // If there is no matching entry (first() returns false) then exit program
                    ResultSet usernameCheck = ps.executeQuery();
                    if(!usernameCheck.first()){
                        System.out.println("Invalid username/password combination.");
                        continue;
                    }
                    
                    TraderInterface ti= new TraderInterface(username, password);
                    ti.initialize();
                    
                    /*
                    //if username is not manager
                    
                    boolean loggedin=true;
                    while (loggedin){
                        // Menu starts from here
                        
                        System.out.println("Enter on of the following options:");
                        System.out.println("[ Options: Deposit, Withdraw, Buy, Sell, Get Balence, Get Monthly Stock Transactions, Logout, List Current Stock Prices, List Movie Information, Get Top Movies, Get Reviews]");
                        String choice = scanner.nextLine();
                        int taxID =6660;
                        double money=55555555;
                        while(true){
                            if(choice.equals("u")){
                                int accountID=0;
                                PreparedStatement account = connection.prepareStatement("SELECT * from Accounts WHERE taxid=taxID");
                                ResultSet rs = account.executeQuery();
                                while (rs.next())
                                {
                                    int tax = rs.getInt("taxid");
                                    if (tax ==taxID){
                                        accountID = rs.getInt("accountid");
                                        System.out.println("accountid: "+ accountID);
                                    }
                                    
                                }
                                account.close();
                                System.out.println("Updating");
                                PreparedStatement adjustAccount = connection.prepareStatement("UPDATE Accounts SET balance=? WHERE taxid=taxID");
                                adjustAccount.setDouble(1,money);
                                adjustAccount.executeUpdate();
                                adjustAccount.close();

                                PreparedStatement adjustedAccount = connection.prepareStatement("SELECT * from Accounts WHERE taxid=taxID");
                                rs = adjustedAccount.executeQuery();
                                while (rs.next())
                                {
                                    
                                    
                                    int tax = rs.getInt("taxid");
                                    if (tax ==taxID){
                                        double newBal = rs.getDouble("balance");
                                    
                                        System.out.println("get balance");
                                        DecimalFormat df = new DecimalFormat("#");
                                        df.setMaximumFractionDigits(2);
                                        System.out.println("new Bal: " +df.format(newBal));
                                    }
                                    
                                }
                                adjustedAccount.close();
                                //ps.setInt(2,taxID);
                                break;
                                
                            }
                            if(choice.equals("q")){
                                loggedin=false;
                                break;
                            }
                        }
                        */
                }
                
                else if("quit".equals(input)){
                    System.out.println("Bye.");
                    System.exit(0);
                }
                
                else{
                    System.out.println("Sorry, that was not a valid respone. Please try again.");
                }
                
                
                // end of menu loop
                
                // Otherwise, do other stuff
                //String A = usernameCheck.getString("name");
                //int B = resultSet.getInt("B");
                //int C = resultSet.getInt("C");
                
                //System.out.println(A);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }
            
            if (connection != null) {
                connection.close();
            }
        }
        
        
    }
}

