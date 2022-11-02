Features incorporated :-
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
20.	The only limitation is that alpha vantage API allows only 5 API calls per minute, so if the number of API calls are increased more than 5 than the program returns a dummy value instead of fetching actual value from the API and also displays the dummy value to the user so he can know his stock valuation as well as portfolio valuation.

So to summarise as described above, all the features mentioned in the assignment description are implemented and work correctly, covering most of the edge cases and inavlid inputs also.
