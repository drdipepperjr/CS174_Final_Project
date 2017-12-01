import java.sql.*;
import java.util.Scanner;




public class StockTradeMain {
    public int month=1;
    public int data=1;
    public int year=2017
    
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
                        System.exit(0);
                    }
                    
                    //if username is not manager
                    
                    boolean loggedin=true;
                    while (loggedin){
                        Scanner scan = new Scanner(System.in);
                        Scanner input = new Scanner(System.in);
                        // Menu starts from here

                        System.out.println("Enter on of the following options:");
                        System.out.println("[ Options: Deposit, Withdraw, Buy, Sell, Get Balence, Get Monthly Stock Transactions, Logout, List Current Stock Prices, List Movie Information, Get Top Movies, Get Reviews]");
                        String choice = input.nextLine();
                        //Options
                        while(true){
                            //deposit
                            if(choice.equals("Deposit")||choice.equals("deposit")||choice.equals("D")||choice.equals("d")){
                                System.out.print("Enter amount to deposit:  ");
                                double amount = scan.nextDouble();
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
                                double amount = scan.nextDouble();
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
                                String stockID = scan.nextLine();
                                //if stockID exists
                                //get balence
                                //get current stock price
                                //check if enough money
                                System.out.println("New stock balence:");

                            }
                            
                            // sell
                            else if(choice.equals("Sell")||choice.equals("sell")||choice.equals("S")||choice.equals("s")){
                                System.out.println("Enter stock name: ");
                                String stockID = scan.nextLine();
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
                        }
                        
                        
                        else if("quit".equals(input)){
                            System.out.println("Bye.");
                            System.exit(0);
                        }
                        
                        else{
                            System.out.println("Sorry, that was not a valid respone. Please try again.");
                        }
                    }
                    
                }   // end of menu loop
                
                // Otherwise, do other stuff
                String A = usernameCheck.getString("name");
                //int B = resultSet.getInt("B");
                //int C = resultSet.getInt("C");
                
                System.out.println(A);
                
                
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

