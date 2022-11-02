The design of the assignment is as follows :-

The entire design is divided into MVC framework. There are 3 models, 1 view and 1 controller.
src folder contains 3 packages of model, view and controller and 1 Main methdod.
The 3 models are of User,Stocks and Portfolio.

Each model has an interface and its Impl class.
Stocks Impl stores ticker and has a function to fetch closing value from date.

Then Portfolio Impl stores portfolio name and a map to store ticker and quantities of that ticker for that portfolio.
It also has a method of getValuation which returns a Map which maps ticker to it's total valuation at a particular date.

User Impl stores the name of the user and a list of portfolios that the user may have. This is the main model that interacts with the controller. It has all the main funcctions a user can access like create portfolio, load portfolio, get portfolio names, get portfolio composition, and get total valuation of a portfolio.

Apart from these classes and interfaces, in model there is a Ticker enum which stores the list of shares that are valid. So in this assignment all the NASDAC 100 shares are supported and are mentioned in the enum class.

Now view interface has all the methods needed to display various outputs and it's Impl takes in an outputstream and writes to it.

Controller Impl takes in inputstream,outstream,view and model as parameters. It reads from inputstream, then calls the functions of the user mmodel and passes the output to view to write the result to outputstream. In this way it acts as an intermediator. It has various functions to read, write and perform various operations.

This is the entire code design.

Now for test design there are test classes for all models, views and controllers.
For all 3 models Impl class all their methods are tested.
For view all methods it is tested that correct output is written.
For controller there are two tests : One (UserControllerTestsUsingMockmodel) tests for IO abstracting using Mock Model and tests that all model methods are properly called in order with the correct parameters by maintaining a log in the function calls. The other test tests for actual controller by passing in a byte inputStream of the set of inputs a user will enter and tests with actual model asserting it with actual expected outputs.

So that's the overall design of our implemenatation.


