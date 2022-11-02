SETUP-README



1.	Open the res folder in cmd.

2.	Write the command java -jar Assignment4.jar

3.	The command prompt will now ask for your name. Enter your name.

4.	Now you will see a welcome message along with a menu with all the functionalities.

5.	You can enter 1 to create a portfolio, 2 to examine the composition of a portfolio, 3 to 
determine the total valuation of a portfolio on a certain date, 4 to list all portfolio names ,5 to 
load your portfolio and q to quit. If you enter any other input apart from this, a default message 
will pop up and you will be redirected to the main menu again to choose.

6.	When you enter 1 to create a portfolio, you will be asked to enter your portfolio name. If a 
portfolio with that name already exists, you will be thrown a message and asked to enter your 
portfolioName again. 

7.	After that you will be given 2 options, 1 to add stocks to your portfolio and q to quit and return 
to the main menu. 

8.	When you enter 1, you will be asked for the stock’s ticker symbol. If that ticker symbol is invalid 
or is not listed in the NASDAQ-1oo stocks, then it will display a message and prompt you to 
enter a valid ticker. All the valid ticker symbols are mentioned in an enum class called ticker 
which is present in model package.

9.	After that, you will be asked to enter a quantity of the stocks. The quantity should be a positive 
integer and if any other input is given, the program will prompt appropriate message and ask 
user to enter correct quantity.

10.	User can add as many stocks as he wants to the portfolio and after all his stocks are added, 
when he presses q, a message of portfolio created successfully is shown and he is thrown back 
to the main menu. Now the user can create a new portfolio or choose any other options of the 
menu.

11.	Here, the xml file of the portfolio is created automatically with the name as 
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

12.	Now if the user presses 2 to examine the composition of a portfolio, he will be asked to enter 
the portfolio name first. If that portfolio does not exist, appropriate message is shown and user 
is thrown back to the main menu.

13.	If the portfolio name is valid, then the output is shown in the format as follows – 
Portfolio_name: p1
{
        Stock_Ticker: GOOGL
        Quantity: 30
}
{
        Stock_Ticker: AAPL
        Quantity: 2
}

14.	When you press 3 to determine the value of portfolio, again the portfolio name is asked first. 
After entering a valid portfolio name, next date is asked to enter in a particular format. If the 
date is invalid or not in the format mentioned, then the appropriate message is displayed and 
the user is redirected to the main menu. 

15.	Here, we are using Alpha vantage api to fetch real- time data but it has limitations. We can only 
make 5 api calls in a minute. Here in this program we are supporting dates from 1st January 2000 
till current day. If the value of stocks is not fetched from the api on the date you inputted, 
appropriate message is shown.

16.	If the data for the given date exists, the total value of portfolio is shown along with the 
individual stock break-down.

17.	Here, in this program due to that API limitations if there are more than 5 stocks in a portfolio 
and you need to find it’s valuation we use dummy data. So for all the stocks within that 
limitation, actual value is returned but if there are more than 5 API calls, for the rest of the 
stocks we are returning the stock value as a dummy fixed value.

18.	When the user presses 4 to view all portfolio names, the list of portfolio names is returned and 
if no portfolio exists, appropriate message is shown.

19.	When user presses 5 to load the portfolio, he has to mention the file path along with extension. 
Usually the file will be saved in the same folder so it is ok if he just enters file name along with 
extension but file path is also valid.

20.	Only files with .xml extension are allowed so if the user enters any file path which is invalid or 
which is not of the proper format, appropriate message is shown.

21.	If the user passes an xml file but it has no portfolio tags as shown in point 11 then also, a 
message is shown telling the user that no portfolio is associated with that file so it is not loaded.

22.	If the user enters the correct file path, a message is shown telling the user that it has been 
loaded successfully.

23.	User can also verify that by pressing option 4 of the main menu and checking all his portfolio 
names.



Instructions on how to run program using dummy data – 



I have mentioned in bullets what needs to be typed and along with it is the entire flow of output. 	
>	Type java -jar Assignment4.jar in cmd
Please Enter your name:
>	Tony Stark
Welcome Tony Stark !! Please select an option from the menu!!

Enter 1 for making portfolio
Enter 2 to examine the composition of a particular portfolio
Enter 3 to determine the total value of portfolio on a certain date
Enter 4 to view all portfolio names
Enter 5 to load your portfolio
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

