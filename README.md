# CS174_Final_Project
Final project for CS 174
Domenic Dipeppe and Rebecca Lee

## Description:

- A program that manages the "Stars-R-Us" brokerage, a brokerage that trades stocks for movie stars and directors.
- [Link to specifications](https://piazza-resources.s3.amazonaws.com/j80grhkos2e5er/j97xn583er77nm/Project.pdf?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=ASIAIYKJ4WAWHDYG6KJQ%2F20180715%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20180715T234435Z&X-Amz-Expires=10800&X-Amz-SignedHeaders=host&X-Amz-Security-Token=FQoDYXdzEJb%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaDNGisGJzGDD0ZFT3GiK3A6BLUtiKexS9GGhltMEHXS5bKX%2F7qs91jjkGDtylkvy5tzbqNcQr2Zgg%2FsQr9DuLk3cVjlMol8YD601%2BABR4jRWLFt1CMRHxpCnajfLPeOlRSgHBdbRpusU9q%2Bg4KleyBv2wKzxA25YtpbLcXZY8diVGrUD7PprBGx29SFgPD3C9I56RDFGch5Yr8bjU8jSmiXtBL1y1NjmxVT64vzNHj4a%2F70S1D1kM6mpF1aDE6bh2cryaCrPDgiu0Dy9nShgzNQ0sj8nphaBSeta701The0L%2FCC7TQL3ArV2oc0HBnfhR33njCQuQbqDOrJaeHVS1IqCMBMgOPM7ihe26nf1PF5CvLgBvblYe5VKHi8cCpP6WKZc5tjiKG39QB5FL0yOymILc9g66eCGZFVS%2BKumBbd4ZnQfIBijrFxHrYoXervb1ElDVEoCTbvwhdP5vom14aNY9NkhAnKWnn8ZlKZQxO%2FnzAOkvXubxbruDTv5t3xv0UgjFeTJMZZKKrtcwf1Rzp4WKRDYfl1Cu2U9L%2BevhTzLHK0ZqrzE7PHRXf%2Fei7nd2VII30mBQvB%2BJ33FVg2LXMeQ6myCySD0o1uWu2gU%3D&X-Amz-Signature=3a084eaa08c90bb6bfed2041ac1ddaf3fd162ce834559c9b9dcd4aa4a3d8573f)

### Classes:

- StocktradeMain.java
  - Log in as either an administrator or a customer
  - Check username/passowrd combination against database for valid login
  - Create new user accounts
  
- BrokerageInterface.java
  - Add monthly interest to all customer accounts
  - Generate monthly statement for a given customer (a list of all transactions made during the current month)
  - List active customers (customers who have traded at least 1000 shares in the current month)
  - Generate Government Drug and Tax Evasion Report (list of customers who have earned at least $10,000 in the past month)
  - Generate Customer Report (List of all accounts associated with a particular customer with the current balance of each account)
  - Delete transaction history for the current month
  
- TraderInterface.java
  - Deposit and withdraw money
  - Buy and sell stocks
  - Display balance for all accounts
  - Display transaction history
  - Lookup stock prices
  - Lookup information on a given movie
  - Display movie reviews for a given movie
  - Display the top-rated movies from any time interval (ex. 1970-1980)
  
