import java.sql.*;
import java.util.Scanner;
import java.text.DecimalFormat;


public class StockTradeMain {
    //public String date="01/02/2014";
    
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
                System.out.print("Are you a new user, existing user or administrator? (new/existing/admin/quit): ");
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
                            ps.close();
                            
                            // Insert accounts
                            addAccounts = connection.prepareStatement("INSERT into Accounts (taxid, balance) VALUES(?,0)");
                            addAccounts.setInt(1,taxid);
                            addAccounts.executeUpdate();
                            addAccounts.close();
			
                            System.out.println("Thank you for joining us. Please login to continue");
                            break;
                        }
                        else
                            System.out.println("Sorry, username is already taken. Please enter a unique username");
                    }
                    
                }

		// admin login
		else if("admin".equals(input)){
		    String username, password;
		    System.out.println("Please enter your username and password");
		    System.out.print("username: ");
                    username = scanner.nextLine();
                    System.out.print("password: ");
                    password = scanner.nextLine();

		    PreparedStatement ps = connection.prepareStatement("SELECT * from Employees WHERE username=? AND password=?");
                    ps.setString(1,username);
                    ps.setString(2,password);
		    ResultSet usernameCheck = ps.executeQuery();

		    if(!usernameCheck.first()){
			System.out.println("Invalid username/password combination.");
			continue;
		    }

		    usernameCheck.beforeFirst();
		    while(usernameCheck.next()){
			String name = usernameCheck.getString("name");
			BrokerageInterface bi = new BrokerageInterface(name);
			bi.initialize();
		    }
		}

		// Customer Login
                else if("existing".equals(input) || "e".equals(input)){// Prompt user for username and password
                    String username, password;
                    String currentDate_s = null;
                    System.out.println("Please enter your username and password");
                    System.out.print("username: ");
                    username = scanner.nextLine();
                    System.out.print("password: ");
                    password = scanner.nextLine();
		    
                    // Create SQL query
                    PreparedStatement ps = connection.prepareStatement("SELECT * from Customers WHERE username=? AND password=?");
                    ps.setString(1,username);
                    ps.setString(2,password);
                   
		    String isOpen = null;
		    PreparedStatement ps1 = connection.prepareStatement("Select * from Date");
		    ResultSet dateSet = ps1.executeQuery();
		    while(dateSet.next()){
			currentDate_s = dateSet.getString("date");
			isOpen = dateSet.getString("isopen"); 
		    }	
		    
		    // If there is no matching entry (first() returns false) then exit program
		    ResultSet usernameCheck = ps.executeQuery();
		    if(!usernameCheck.first()){
			System.out.println("Invalid username/password combination.");
			continue;
		    }
		    int taxID=usernameCheck.getInt("taxid");
		    System.out.println(taxID);
		    
		    TraderInterface ti= new TraderInterface(username, password, taxID ,currentDate_s, isOpen);
		    ti.initialize();
                    
                    
                }
                
                
                else if("quit".equals(input)){
                    System.out.println("Bye.");
                    System.exit(0);
                }
                
                else{
                    System.out.println("Sorry, that was not a valid respone. Please try again.");
                }
		
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
