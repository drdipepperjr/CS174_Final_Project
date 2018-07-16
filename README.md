# CS174_Final_Project
Final project for CS 174
Domenic Dipeppe and Rebecca Lee

## Description:

- A program that manages the "Stars-R-Us" brokerage, a brokerage that trades stocks for movie stars and directors.

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
  
