Price-Comparator Market Application

This is a Java-based console application that allows managing products, discounts, price alerts, 
and shopping lists, with data persisted using CSV files and SQLite database.

Once you run the application, you'll see a menu like this:
0 : EXIT
1 : Product Management
2 : Discount Management
3 : Price Alerts Management
4 : Shopping List
5 : Show Product Substitutes

You can then choose an option you want. For example, if you press 1 , you see another menu for managing
products.
Same goes if you choose option 2, but you are be able to manage discounts. You can view all discounts,
view top products with the highest active discounts and see the most recently added product discounts.

If you enter 3, you can choose to add a target price for a product, which will be saved in a SQL database.
Check triggered alerts will check if any of the alerts are triggered. If no alert is triggered it will show 
a message saying that no alerts were triggered.

If you want to make a shopping list with some products, choose option 4.
The shopping list will be saved in a SQL database. 

Products with highest current discounts are shown if you choose the last option, option number 5.

The PriceHistory feature has all the methods implemented, the only thing remaining is to develop the
product date logic.
