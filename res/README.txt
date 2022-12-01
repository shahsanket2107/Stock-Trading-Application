Features incorporated :-

For assignment6 new strategy features and GUI :-

1. User can invest a certain amount in an existing portfolio across a series of stocks based on their fractional weights.
2. User can create a new portfolio according to dollar cost average strategy where he can invest a certain amount periodically
from a given start date to a given end date.
3. End date can be in the future or End date can be not specified.
4. User can also create the strategy on an existing portfolio.
5. GUI is implemented where user can access all the methods of the flexible portfolio as well as the new strategy methods.
6. For extra credit the performace of portfolio using bar chart is also implemented using GUI.


For assignment4 inflexible portfolios :-

1.	User can create multiple portfolios with multiple stocks and quantities.
2.	Quantities cannot be 0, negative or fractional. If a user enters any such quantity then he would be prompted to enter the quantity again.
3.	All the portfolios that the user creates on successful creation are written to an xml file that is named {name_of_user}_portfolios.xml.
4.	If a portfolio name already exists then user cannot create another portfolio with the same name. It would be prompted to him that the portfolio already exists and he would be prompted to enter a new name.
5.	If in 1 portfolio user first enters 10 shares of apple and then later on enter 15 more shares of apple then the portfolio composition shows 25 shares of apple.
6.	Portfolio name cannot be empty.
7.	The portfolio composition works perfectly and shows the portfolio name along with the stocks composition consisting of tickers and quantities.
8.	The user can enter the name of portfolio to view it’s composition.
9.	If the name of portfolio exists then it’s composition is viewed else a message is shown saying portfolio name does not exists.
10.	User can view all the portfolio names that he has created.
11.	User can load his own portfolio via an xml file.
12.	If the portfolio he loads is not an xml file, program will not accept it saying only xml files are supported.
13.	If the filename entered by the user does not exist in the project then he would be prompted that the file does not exist.
14.	If the filename also exists and is an xml file but if it does not follow proper structure then too it cannot be loaded and it would be prompted that incorrect structure.
15.	If everything is correct and in proper format then the portfolio would be loaded successfully and the success message would be displayed to the user.
16.	For getting the valuation at a particular date program uses alphavantage api calls.
17.	If a user passes incorrect portfolio name for getting valuation he would be prompted that no such portfolio exists.
18.	If a user enters date in incorrect format, invalid date or a future date (or any weekend date for which data does not exists) then he would be prompted that no data found for that date.
19.	If everything is correct i.e. correct portfolio name as well as date then user will be displayed the correct portfolio valuation at that date along with the individual stocks valuation at that date.

For assignment5 flexible portfolios :-

20.	User can create flexible portfolios which can be edited.
21. 	User can buy and sell stocks on particular dates. If on that day, the market is closed, appropriate message is displayed.
22.	All the edge cases like buying stocks of invalid ticker, buying invalid quantities, adding stock to a portfolio which does not exists, selling more stocks than what are in the portfolio, selling stocks on date before he bought the stocks are handled and appropriate message would be displayed if the user attempts to do any of these.
23.	While creating the portfolio, the portfolio is written to a json file named {name_of_user}_portfolios.json. 
24.	The portfolio is saved locally and can be loaded and used for buying,selling, checking total value or any other operation.
25.	As of now, flexible portfolios are stored in json format and only .json files can be loaded.
26. 	When a user buys or sells a stock, the file is updated with the updated quantities, ticker(if a new stock is bought), date and cost basis which is calculated on the spot.
27.	User can check composition of his portfolio on a particular date. 
28. 	User can view the total valuation of his portfolio with stock breakdown on a particular date.
29.	User can view the cost basis of his portfolio on a particular date. Cost basis is total amount of money invested till that date whch includes the commission fee of $ 3.33 per transaction.
30.	User can view how the portfolio has performed over a period of time. The performance will be shown in the form of a chart with stars.
31.	The chart is dynamic based on dates and values. If the difference between start date and end date is till 30 days, user will see the performance in days, after that he will see weekly performance, once 5 weeks are crossed, user will see monthly performace and once 30 months are crossed, the user will see yearly performance.
32.	Also the stars are dynamic based on the max and min value of the portfolio on all the dates.
33.	Lastly, the alphavantage API is incoroporated which allows real-time buying and selling stocks and checking the valuation.

So to summarise as described above, all the features mentioned in the assignment description are implemented and work correctly, covering most of the edge cases and invalid inputs also.