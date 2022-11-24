SETUP-README

Libraries used: org.codehaus.jackson:jackson-xc:1.9.13

To set up the project in IntelliJ create a new project, add the src, test and res folders to it.
Go to the project structure and go to modules to add external dependency.
Click on the + icon -> Libraries -> From Maven -> Search in org.codehaus.jackson:jackson-xc:1.9.13 and press enter.
The dependency will be added, click apply and then click ok.
Now you can run the main method and it will work just as running the jar file.

After running the jar file if you would directly like to run flexible portfolio methods for assignment-2
Please go to the latter section of this file. I have described how to run each methods.

These are the steps to run the jar file

1. Open the res folder in cmd.

2. Write the command java -jar Assignment5.jar

3. The command prompt will now ask for your name. Enter your name.
 
4. Now you will see a welcome message along with a menu with all the functionalities.

5. You will be shown the following options :-

Welcome sanket !! Please select an option from the menu!!

Enter 1 for making portfolio
Enter 2 to examine the composition of a particular portfolio
Enter 3 to determine the total value of portfolio on a certain date
Enter 4 to view all portfolio names
Enter 5 to load your portfolio
Enter 6 to create flexible portfolio (You would be charged a commission fee of $3.33 per transaction)
Enter 7 to examine composition of flexible portfolio
Enter 8 to load a flexible portfolio
Enter 9 to buy stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 10 to sell stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 11 to determine the total value of flexible portfolio on a certain date
Enter 12 to find cost basis of a flexible portfolio on a certain date
Enter 13 to view how the portfolio has performed over a period of time using chart
Enter q to exit
 
If you enter any other input apart from this, a default message 
will pop up and you will be redirected to the main menu again to choose. After you choose any option or write an input, hit enter for line break.

---------------------------------------------------- These are the steps to run the inflexible portfolio methods -------------------------------------------------------------------

6. When you enter 1 to create a portfolio, you will be asked to enter your portfolio name. If a 
portfolio with that name already exists, you will be thrown a message and asked to enter your 
portfolioName again. 

7. After that you will be given 2 options, 1 to add stocks to your portfolio and q to quit and return 
to the main menu. 

8. When you enter 1, you will be asked for the stock’s ticker symbol. If that ticker symbol is invalid or is not listed in the NASDAQ-1oo stocks, then it will display a message and prompt you to 
enter a valid ticker. All the valid ticker symbols are mentioned in an enum class called ticker 
which is present in model package.

9. After that, you will be asked to enter a quantity of the stocks. The quantity should be a positive integer and if any other input is given, the program will prompt appropriate message and ask user to enter correct quantity.

10. User can add as many stocks as he wants to the portfolio and after all his stocks are added, 
when he presses q, a message of portfolio created successfully is shown and he is thrown back 
to the main menu. Now the user can create a new portfolio or choose any other options of the 
menu.

11. Here, the xml file of the portfolio is created automatically with the name as 
“nameOfUser_portfolios.xml” with nameOfUser being the user’s name which was asked in the 
first place. All the portfolios which a user creates are stored in this file only with the tags 
structure as follows – 

<?xml version="1.0" encoding="UTF-8" standalone="no"?> 
<portfolios> 
    <portfolio name="p1"> 
        <stocks parameter="ticker">GOOGL</stocks> 
        <stocks parameter="quantity">30</stocks> 
        <stocks parameter="ticker">AAPL</stocks> 
        <stocks parameter="quantity">2</stocks> 
    </portfolio> 
    <portfolio name="p2"> 
        <stocks parameter="ticker">AAPL</stocks> 
        <stocks parameter="quantity">30</stocks> 
        <stocks parameter="ticker">TSLA</stocks> 
        <stocks parameter="quantity">21</stocks> 
        <stocks parameter="ticker">AMZN</stocks> 
        <stocks parameter="quantity">6</stocks> 
    </portfolio> 
</portfolios>

12. Now if the user presses 2 to examine the composition of a portfolio, he will be asked to enter 
the portfolio name first. If that portfolio does not exist, appropriate message is shown and user 
is thrown back to the main menu.

13. If the portfolio name is valid, then the output is shown in the format as follows – 
Portfolio_name: p1
{
        Stock_Ticker: GOOGL
        Quantity: 30
}
{
        Stock_Ticker: AAPL
        Quantity: 2
}

14. When you press 3 to determine the value of portfolio, again the portfolio name is asked first. 
After entering a valid portfolio name, next date is asked to enter in a particular format. If the 
date is invalid or not in the format mentioned, then the appropriate message is displayed and 
the user is redirected to the main menu. 

15. Here, we are using Alpha vantage api to fetch real- time data but it has limitations. We can only 
make 5 api calls in a minute. Here in this program we are supporting dates from 1st January 2000 
till current day. If the value of stocks is not fetched from the api on the date you inputted, 
appropriate message is shown.

16. If the data for the given date exists, the total value of portfolio is shown along with the 
individual stock break-down.


18. When the user presses 4 to view all portfolio names, the list of portfolio names is returned and 
if no portfolio exists, appropriate message is shown.

19. When user presses 5 to load the portfolio, he has to mention the file path along with extension. 
Usually the file will be saved in the same folder so it is ok if he just enters file name along with 
extension but file path is also valid.

20. Only files with .xml extension are allowed (while loading an inflexible portfolio) so if the user
enters any file path which is invalid or which is not of the proper format, appropriate message is shown.

21. If the user passes an xml file but it has no portfolio tags as shown in point 11 then also, a 
message is shown telling the user that no portfolio is associated with that file so it is not loaded.

22. If the user enters the correct file path, a message is shown telling the user that it has been 
loaded successfully.

23. User can also verify that by pressing option 4 of the main menu and checking all his portfolio 
names.



Instructions on how to run program using dummy data – 



I have mentioned in bullets what needs to be typed and along with it is the entire flow of output. 	
>	Type java -jar Assignment5.jar in cmd
Please Enter your name:
>	Tony Stark
Welcome Tony Stark !! Please select an option from the menu!!

Enter 1 for making portfolio
Enter 2 to examine the composition of a particular portfolio
Enter 3 to determine the total value of portfolio on a certain date
Enter 4 to view all portfolio names
Enter 5 to load your portfolio
Enter 6 to create flexible portfolio (You would be charged a commission fee of $3.33 per transaction)
Enter 7 to examine composition of flexible portfolio
Enter 8 to load a flexible portfolio
Enter 9 to buy stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 10 to sell stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 11 to determine the total value of flexible portfolio on a certain date
Enter 12 to find cost basis of a flexible portfolio on a certain date
Enter 13 to view how the portfolio has performed over a period of time using chart
Enter q to exit

>	1
Enter your portfolio name:
>	portfolio_1
Enter 1 to add stocks to your portfolio
Enter q to exit
>	1
Enter ticker of stock you want to add to the portfolio:
>	aapl
Enter quantity of stocks:
>	50
Enter 1 to add stocks to your portfolio
Enter q to exit
>	1
Enter ticker of stock you want to add to the portfolio:
>	tsla
Enter quantity of stocks:
>	100
Enter 1 to add stocks to your portfolio
Enter q to exit
>	1
Enter ticker of stock you want to add to the portfolio:
>	amzn
Enter quantity of stocks:
>	30
Enter 1 to add stocks to your portfolio
Enter q to exit
>	q
Portfolio created successfully!!

Enter 1 for making portfolio
Enter 2 to examine the composition of a particular portfolio
Enter 3 to determine the total value of portfolio on a certain date
Enter 4 to view all portfolio names
Enter 5 to load your portfolio
Enter 6 to create flexible portfolio (You would be charged a commission fee of $3.33 per transaction)
Enter 7 to examine composition of flexible portfolio
Enter 8 to load a flexible portfolio
Enter 9 to buy stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 10 to sell stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 11 to determine the total value of flexible portfolio on a certain date
Enter 12 to find cost basis of a flexible portfolio on a certain date
Enter 13 to view how the portfolio has performed over a period of time using chart
Enter q to exit

>	1
Enter your portfolio name:
>	portfolio_2
Enter 1 to add stocks to your portfolio
Enter q to exit
>	1
Enter ticker of stock you want to add to the portfolio:
>	googl
Enter quantity of stocks:
>	100
Enter 1 to add stocks to your portfolio
Enter q to exit
>	1
Enter ticker of stock you want to add to the portfolio:
>	msft
Enter quantity of stocks:
>	50
Enter 1 to add stocks to your portfolio
Enter q to exit
>	q
Portfolio created successfully!!

Enter 1 for making portfolio
Enter 2 to examine the composition of a particular portfolio
Enter 3 to determine the total value of portfolio on a certain date
Enter 4 to view all portfolio names
Enter 5 to load your portfolio
Enter 6 to create flexible portfolio (You would be charged a commission fee of $3.33 per transaction)
Enter 7 to examine composition of flexible portfolio
Enter 8 to load a flexible portfolio
Enter 9 to buy stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 10 to sell stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 11 to determine the total value of flexible portfolio on a certain date
Enter 12 to find cost basis of a flexible portfolio on a certain date
Enter 13 to view how the portfolio has performed over a period of time using chart
Enter q to exit

>	2
Enter your portfolio name:
>	portfolio_1
Portfolio_Name: portfolio_1
{
        Stock_Ticker: AAPL
        Quantity: 50
}
{
        Stock_Ticker: TSLA
        Quantity: 100
}
{
        Stock_Ticker: AMZN
        Quantity: 30
}




Enter 1 for making portfolio
Enter 2 to examine the composition of a particular portfolio
Enter 3 to determine the total value of portfolio on a certain date
Enter 4 to view all portfolio names
Enter 5 to load your portfolio
Enter 6 to create flexible portfolio
Enter 7 to examine composition of flexible portfolio
Enter 8 to load a flexible portfolio
Enter 9 to buy stocks on a specific date
Enter 10 to sell stocks on a specific date
Enter 11 to determine the total value of flexible portfolio on a certain date
Enter 12 to find cost basis of a flexible portfolio on a certain date
Enter 13 to view how the portfolio has performed over a period of time using chart
Enter q to exit

>	4
The list of portfolios is:
portfolio_1
portfolio_2


Enter 1 for making portfolio
Enter 2 to examine the composition of a particular portfolio
Enter 3 to determine the total value of portfolio on a certain date
Enter 4 to view all portfolio names
Enter 5 to load your portfolio
Enter 6 to create flexible portfolio
Enter 7 to examine composition of flexible portfolio
Enter 8 to load a flexible portfolio
Enter 9 to buy stocks on a specific date
Enter 10 to sell stocks on a specific date
Enter 11 to determine the total value of flexible portfolio on a certain date
Enter 12 to find cost basis of a flexible portfolio on a certain date
Enter 13 to view how the portfolio has performed over a period of time using chart
Enter q to exit

>	3
Enter your portfolio name:
>	portfolio_1
Enter the date (in format yyyy-MM-dd):
>	2022-10-21
Portfolio_Name: portfolio_1
Portfolio_Valuation at 2022-10-21 is : $ 32387.1
The stock valuation breakdown is:
AAPL : $7363.500000000001
TSLA : $21444.0
AMZN : $3579.6


---------------------------------------------------- These are the steps to run the flexible portfolio methods -------------------------------------------------------------------

Note that the program is still running.
The inflexible portfolios are already created and they exists and now we create flexible portfolio and see how to execute all their methods.
User cannot create portfolio, buy stocks or sell stocks on weekend dates as Stock Market is closed on those days.
User can choose any of NASDAQ-100 stocks to buy/sell. Apart from those he can't buy/sell any new stocks.

-> Enter 6 to create a new Flexible Portfolio. You would prompted for portfolio name
repeatedly till you enter a name (empty name is not valid ). Then you can enter 1 to 
add stocks to the portfolio or q to quit. You can add as many stocks you like by entering
valid tickers, dates and quantities. Any invalid values will ask the user to enter the values again
and again.
Once user presses q, portfolio will be created successfully and a new file will also be created.

-> Enter 7 to view composition of a flexible portfolio. User will be asked for portfolio name and date.
Then he will be shown the portfolio composition till that date.

-> Enter 8 to load a flexible portfolio. User will be prompted for a filename with extension. The file
will be loaded successfully if he enters an existing file with json format and extension, else it will
show appropriate message and ask user to enter the correct file.

-> Enter 9 to buy stocks on a specific date. Here user will be prompted for portfolio name,ticker,qty and date
If he enters a portfolio name,ticker that does not exist or a date that happens to be at weekend, it will 
tell him so and prompt for them again.
Once he enters the valid input for all, the stocks he entered would be bought and written to the file as well.
If the stocks are bought successfully then before storing it, cost basis is computed along with the commission fee
and the entire transaction with all parameters is written to the file.
So now if he calls view composition on that date, he could see that stock.

-> Enter 10 to sell stocks on a specific date.  Here user will be prompted for portfolio name,ticker,qty and date
If he enters a portfolio name,ticker that does not exist or a date that happens to be at weekend, it will 
tell him so and prompt for them again. If a user tries to sell a stock that he does not possess or if he tries to sell
more quantity of stocks than he possesses at that time, he would be displayed an appropriate message
and he would be unable to sell the stock.
Only if all input is valid then he can sell the shares and if the sell is successfull then cost basis is computed as a commission fee
and the wntire transaction is then written to the file.

-> Enter 11 to determine the total value of flexible portfolio on a certain date. User will be prompted for portfolio name and date.
Once he enters it correctly, he can see portfolio valuation along with individual stock breakdown valuation till that date.

-> Enter 12 to find cost basis for a flexible portfolio. User will be prompted for portfolio name and date. Once he enters it correctly
He can view the cost basis till that date.

-> Enter 13 to view portfolio performance via chart. User will be prompted for portfolio name, start date and end date. If he enters all correctly
Then he can view the portfolio performance via a graph with appropriate scaling of timeline as well as value of each asterics.


--------------------------------------------------------------------------------------------------------------------------------------------------------

Detailed instructions on how to run your program to create a portfolio, 
purchase stocks of at least 3 different companies in that portfolio at different dates and 
then query the value and cost basis of that portfolio on two specific dates.

First open cmd in res.
type in java - jar Assignment5.jar

Now please follow the input/ output given below :-

C:\Users\Sanket\Documents\PDP\Assignments\Assignment-4\Assignment4\res>java -jar Assignment5.jar

Please Enter your name:
> sanket
Welcome sanket !! Please select an option from the menu!!

Enter 1 for making portfolio
Enter 2 to examine the composition of a particular portfolio
Enter 3 to determine the total value of portfolio on a certain date
Enter 4 to view all portfolio names
Enter 5 to load your portfolio
Enter 6 to create flexible portfolio (You would be charged a commission fee of $3.33 per transaction)
Enter 7 to examine composition of flexible portfolio
Enter 8 to load a flexible portfolio
Enter 9 to buy stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 10 to sell stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 11 to determine the total value of flexible portfolio on a certain date
Enter 12 to find cost basis of a flexible portfolio on a certain date
Enter 13 to view how the portfolio has performed over a period of time using chart
Enter q to exit
> 6
Enter your portfolio name:
test_portfolio_1
Enter 1 to add stocks to your portfolio
Enter q to exit
> 1
Enter ticker of stock you want to add to the portfolio:
> aapl
Enter quantity of stocks:
> 12
Enter the date (in format yyyy-MM-dd):
> 2022-10-03
Enter 1 to add stocks to your portfolio
Enter q to exit
> 1
Enter ticker of stock you want to add to the portfolio:
> tsla
Enter quantity of stocks:
> 24
Enter the date (in format yyyy-MM-dd):
> 2022-10-14
Enter 1 to add stocks to your portfolio
Enter q to exit
> 1
Enter ticker of stock you want to add to the portfolio:
> msft
Enter quantity of stocks:
> 8
Enter the date (in format yyyy-MM-dd):
> 2022-10-28
Enter 1 to add stocks to your portfolio
Enter q to exit
> q
Portfolio created successfully!!

This created the portfolio with 3 stocks on different dates.

The file content after this is as follows :-

[ {
  "name" : "test_portfolio_1",
  "stocks" : [ {
    "ticker" : "AAPL",
    "date" : "2022-10-03",
    "qty" : 12,
    "costBasis" : 1712.7299999999998
  }, {
    "ticker" : "TSLA",
    "date" : "2022-10-14",
    "qty" : 24,
    "costBasis" : 4923.09
  }, {
    "ticker" : "MSFT",
    "date" : "2022-10-28",
    "qty" : 8,
    "costBasis" : 1890.29
  } ]
} ]

Now we can either buy/sell stocks in this portfolio and it would be written in this format in the file.

Say for example I sell 5 shares of TSLA on 2022-10-25.
Then the file content would be like :-

[ {
  "name" : "test_portfolio_1",
  "stocks" : [ {
    "ticker" : "AAPL",
    "date" : "2022-10-03",
    "qty" : 12,
    "costBasis" : 1712.7299999999998
  }, {
    "ticker" : "TSLA",
    "date" : "2022-10-14",
    "qty" : 24,
    "costBasis" : 4923.09
  }, {
    "ticker" : "MSFT",
    "date" : "2022-10-28",
    "qty" : 8,
    "costBasis" : 1890.29
  }, {
    "ticker" : "TSLA",
    "date" : "2022-10-25",
    "qty" : -5,
    "costBasis" : 3.33
  } ]
} ]

In selling costBasis is the commission fee for each transaction.
So wither while creating portfolio or at any later stage user can buy portfolio
He can sell it anytime too and every transaction is stored.

Query the value of portfolio on 2 different dates (2022-10-24 & 2022-11-02) :-

Enter 1 for making portfolio
Enter 2 to examine the composition of a particular portfolio
Enter 3 to determine the total value of portfolio on a certain date
Enter 4 to view all portfolio names
Enter 5 to load your portfolio
Enter 6 to create flexible portfolio (You would be charged a commission fee of $3.33 per transaction)
Enter 7 to examine composition of flexible portfolio
Enter 8 to load a flexible portfolio
Enter 9 to buy stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 10 to sell stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 11 to determine the total value of flexible portfolio on a certain date
Enter 12 to find cost basis of a flexible portfolio on a certain date
Enter 13 to view how the portfolio has performed over a period of time using chart
Enter q to exit
> 11
Enter your portfolio name:
> test_portfolio_1
Enter the date (in format yyyy-MM-dd):
> 2022-10-24
Portfolio_Name: test_portfolio_1
The stock valuation breakdown is:
AAPL : $ 1793.3999999999999
TSLA : $ 5070.0
Portfolio_Valuation at 2022-10-24 is : $ 6863.4

----------------------------------------------------------------------------------------------

Enter 1 for making portfolio
Enter 2 to examine the composition of a particular portfolio
Enter 3 to determine the total value of portfolio on a certain date
Enter 4 to view all portfolio names
Enter 5 to load your portfolio
Enter 6 to create flexible portfolio (You would be charged a commission fee of $3.33 per transaction)
Enter 7 to examine composition of flexible portfolio
Enter 8 to load a flexible portfolio
Enter 9 to buy stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 10 to sell stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 11 to determine the total value of flexible portfolio on a certain date
Enter 12 to find cost basis of a flexible portfolio on a certain date
Enter 13 to view how the portfolio has performed over a period of time using chart
Enter q to exit
> 11
Enter your portfolio name:
> test_portfolio_1
Enter the date (in format yyyy-MM-dd):
> 2022-11-02
Portfolio_Name: test_portfolio_1
The stock valuation breakdown is:
MSFT : $ 1760.8
AAPL : $ 1740.3600000000001
TSLA : $ 4084.62
Portfolio_Valuation at 2022-11-02 is : $ 7585.78

--------------------------------------------------------------------------------

Now we compute the cost basis on those two exact dates :-

Enter 1 for making portfolio
Enter 2 to examine the composition of a particular portfolio
Enter 3 to determine the total value of portfolio on a certain date
Enter 4 to view all portfolio names
Enter 5 to load your portfolio
Enter 6 to create flexible portfolio (You would be charged a commission fee of $3.33 per transaction)
Enter 7 to examine composition of flexible portfolio
Enter 8 to load a flexible portfolio
Enter 9 to buy stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 10 to sell stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 11 to determine the total value of flexible portfolio on a certain date
Enter 12 to find cost basis of a flexible portfolio on a certain date
Enter 13 to view how the portfolio has performed over a period of time using chart
Enter q to exit
> 12
Enter your portfolio name:
> test_portfolio_1
Enter the date (in format yyyy-MM-dd):
> 2022-10-24
Portfolio_Name: test_portfolio_1
Cost basis of your portfolio at 2022-10-24 is : $ 6635.82

--------------------------------------------------------------------------------------------

Enter 1 for making portfolio
Enter 2 to examine the composition of a particular portfolio
Enter 3 to determine the total value of portfolio on a certain date
Enter 4 to view all portfolio names
Enter 5 to load your portfolio
Enter 6 to create flexible portfolio (You would be charged a commission fee of $3.33 per transaction)
Enter 7 to examine composition of flexible portfolio
Enter 8 to load a flexible portfolio
Enter 9 to buy stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 10 to sell stocks on a specific date (You would be charged a commission fee of $3.33 per transaction)
Enter 11 to determine the total value of flexible portfolio on a certain date
Enter 12 to find cost basis of a flexible portfolio on a certain date
Enter 13 to view how the portfolio has performed over a period of time using chart
Enter q to exit
> 12
Enter your portfolio name:
> test_portfolio_1
Enter the date (in format yyyy-MM-dd):
> 2022-11-02
Portfolio_Name: test_portfolio_1
Cost basis of your portfolio at 2022-11-02 is : $ 8529.44

