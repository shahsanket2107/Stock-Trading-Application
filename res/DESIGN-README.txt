For this assignment we have first changed a few things as per the codewalk review and the self eval review.
-> We have now taken commission fees as static that is entered by the user while running the program.
-> We have created a super portfolio interface and the flexible and inflexible portfolio interfaces extend that interface. Super portfolio will have all
the common functionalities of all portfolios and then if in future new portfolios with new definitions are added then they would just be implementing
the super portfolio interface.
-> Then based on the feedback of the assignment this time we are doing exception handling both at model as well as controller level, so even if in
this design our model does not receive any invalid inputs; this model can work fine with any different controller and it will throw an exception if it
is given an invalid input.

Now for the new features implemented we have made a new interface called UserModelExtension which extends the UserModel, and it's impl extends UserImpl.
This is done because using strategy user also have the option to make an investment in the future, so all future investments are written to a file named user_persistance.json and
whenever portfolio compositon or valuation or any methods are called first that file is loaded and it is checked that from the time when the file was written to
present date if any investesments are to be made then it is made and the files are updated accordingly.

And then for all normal cases where start date and end date are in range that is less than current date then investment is done according to the fractional
distribution provided for all the time intervals.

Commission fee is deducted from the amount to be invested beforehand and the remaining amount is then distributed among all the shares.

- Here, we made a new controller gor the GUI part. In the main we are giving the option to the user to choose text based view or GUI view.
- Depending on the option chosen by the user, we would call the controller in the main method.
- The new controller has a Features interface which consists of all the features that our program supports and are included in the main menu.
- This controller is coupled with UserModelExtension and the methods are called for the functionalities.
- We have made a new GuiView class which is also coupled with the controller.
- The GuiView class has all the code required for the GUI part. It initializes the Jframe, and all the panels and buttons are made here.
- All the input and output dialog boxes are also made here.
- We have attempted to find performance of portfolio using bar chart and for that we have used JFreeChart library which is implemented in the view.

--------------------------------------------------------------------------------------------------------------------------------------------------------
The design of the previous assignment was as follows :-

The entire design is divided into MVC framework. There are 7 models, 1 view and 1 controller.
src folder contains 3 packages of model, view and controller and 1 Main methdod.
The 7 models are of User,Stocks,Portfolio, Flexible Portfolio, API, File Operations and DataStoreFromApi.

Each model has an interface and its Impl class.
Stocks Impl stores ticker, quantity, date and cost basis. The class mainly consists of getters and setters for these variables.
In flexible portfolios, we are storing these stock objects, so all the buy and sell transactions are stored in json format with ticker, quantity, date and cost-basis.
The cost basis is the total money invested in the portfolio but we are storing that in each stocks by multiplying the buy price with quantity and adding the commission fee on each buy and sell transaction. So, when user wants to find the total cost basis, all the cost basis in the tickers upto the date entered by the user would be summed up. Right now, we are charging a fixed commission fee of $3.33 for each transaction.
So what we are doing is we are computing the stock basis on the spot as soon as the user makes any transaction.
For selling stock transactions, we are storing the quantity in negative, so while computing the composition of portfolio, tickerswould be clubbed and quantities would sum up so if there is a negative quantity that means a sell transaction has occured and the quantity of that ticker in portfolio would reduce.

Then Portfolio Impl stores portfolio name and a map to store ticker and quantities of that ticker for that portfolio.
It also has a method of getValuation which returns a Map which maps ticker to it's total valuation at a particular date.

The FlexiblePortfolio Impl is basically a new model which was created to support flexible portfolios. It consists of a list of stocks which have 
a ticker, quantity, date and cost basis. Since the inflexible portfolios did not need dates and cost basis as there was no buying and selling 
of stocks but we needed it here, so we made a new model.

User Impl stores the name of the user and a list of portfolios that the user may have. This is the main model that interacts with the controller. It has all the main functions a user can access like create portfolio, load portfolio, get portfolio names, get portfolio composition, and get total valuation of a portfolio for inflexible portfolio.
Now, we added new methods of create flexible portfolio, buy stocks, sell stocks, check composition of flexible portfolio, load flexible portfolio, get total valuation, get cost basis and display chart.
This class has objects of models - Api, FileOperations and DataStorFromApi.

Api model is basically made to call the alpha vantage api. This was made in a new model because design-wise all the apis which we need to call in the future, would be called from here and we would just have to call the method using an Api object and the original API would be hit.

There was a new model of FileOperations which was made. This class has all the methods to read, write and edit files. Previously, we were storing the data in xml files but for this assignment, JSON format is used to store data. We have used Jackson library for parsing JSON. So this new model was made because right now we are allowing only 2 file formats - xml for inflexible and json for flexible portfolio, but in the future if we want to support any more type of files, all the file operations would be done here.

The DataStoreFrom Api model was made fetch,store and display the AlphaVantage API results, to reduce the number of API calls. So what we are basically doing is while creating a flexible portfolio, loading and buying stocks, we store the tickers of those stocks and hit the AlphaVantage API to get the values on all dates. So when we want to get the valuation or cost basis, the number of api calls will be reduced as we have already computed the values before and stored it in a map consisting a ticker symbol and  the values of that ticker on a different date stored in JSON form.


Apart from these classes and interfaces, in model there is a Ticker enum which stores the list of shares that are valid. So in this assignment all the NASDAQ 100 shares are supported and are mentioned in the enum class.

Now view interface has all the methods needed to display various outputs and it's Impl takes in an outputstream and writes to it.

Controller Impl takes in inputstream,outstream,view and model as parameters. It reads from inputstream, then calls the functions of the user mmodel and passes the output to view to write the result to outputstream. In this way it acts as an intermediator. It has various functions to read, write and perform various operations.

This is the entire code design.

Now for test design there are test classes for all models, views and controllers.
For all 3 models Impl class all their methods are tested.
For view all methods it is tested that correct output is written.
For controller there are two tests : One (UserControllerTestsUsingMockmodel) tests for IO abstracting using Mock Model and tests that all model methods are properly called in order with the correct parameters by maintaining a log in the function calls. The other test tests for actual controller by passing in a byte inputStream of the set of inputs a user will enter and tests with actual model asserting it with actual expected outputs.

So that's the overall design of our implemenatation.


