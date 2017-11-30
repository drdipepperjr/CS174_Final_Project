import java.sql.*;
import java.util.Scanner;

public class StockTradeMain {

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
		    System.out.print("Please enter your username");
		    newusername = scanner.nextLine();

		    // Check DB for existing user
		    PreparedStatement ps = connection.prepareStatement("SELECT * from Customers WHERE username=?");
		    ps.setString(1,newusername);
		    ResultSet existinguser = ps.executeQuery();
		    if(!existinguser.first()){
			// TODO: add user to the DB
			System.out.println("Username is unique!");
		    }
		}
		
		else if("existing".equals(input))
		    break;   

		else if("quit".equals(input)){
		    System.out.println("Bye.");
		    System.exit(0);
		}
		
		else{
		    System.out.println("Sorry, that was not a valid respone. Please try again.");
		}
	    }

	    
	    // Prompt user for username and password
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
