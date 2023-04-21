
# Public Banking System



The Public Banking System is a accounting system that aids its user to a larger controll of their finances trough elegant and efficient tools.

## Authors

- [@eliastrana](https://www.github.com/eliastrana)
- [@emiljohnsen](https://www.github.com/emiljohnsen)
- [@henrikfredriksen](https://www.github.com/henrikfredriksen)
- [@sanderskofsrud](https://www.github.com/sanderskofsrud)
- [@vegardjohnsen](https://www.github.com/vegardjohnsen)




![Logo](https://eliastrana.no/PBS/logo/pbslogo.png)


## FAQ

#### Is the program a good program?

Yes the program is great.

#### Does the program steal my data?

The program does not connect to any server, and therefore all the encrypted information stays on your local computer.


## Acknowledgements

We want to thank our families.

## Preface 

During the development of this program we have had our focus on one thing in particular, creating a positive experience for the user. With this as our goal, the development process has been motivated and driven by the idea of creating a clean, efficient and fast program.

To define clean, we would describe it as a program that shows you the information you need, and nothing more. Everything presented to the user has a purpose and a function. What we mean by efficient is that the program lets the user get what it needs done, quickly. The program is supposed to improve the life of the user, and because of that, we are trying to make the experience for the user as seamless and without hiccups as possible. The reason we want our program to be fast is so that the program works for the user, rather than the user having to work for the program. The user should not have to wait for the program, the program should always wait for the user. 

The reasons for our principles are clear. Our program comes as an addition to the regular use of a bank, and as such, we are not needed by the user. Our user should want to use it because they believe that the use of the program will improve their situation. And because of this, we want to be as least bothersome and as efficient as we possibly can. 
## Documentation

[GitLab Wiki](https://gitlab.stud.idi.ntnu.no/idatt1002_2023_13/idatt1002/-/wikis/home)



## User Manual

This user manual will demonstrate the structure and functionality of our program. It will be a detailed guide to how a user should maneuver and utilize the functionality available. 

### Login Screen
The login screen allows the user to either log in with an existing username and password, create a user, or reset your password through an email address. This screen is what the user is presented with when opening the program. It presents a scenic photo with a motivational text of what the product will provide. The program picks between different photos to show, making the program feel dynamic. 

### Create User

If the user of the program does not have a user, it can press the create user text, and be presented with this page. Here the user is asked for a username, email and a password. The password requirements are strong, making sure that the users data is protected as much as possible. The passwords are also encrypted, making it so that the data is secure. 

### Forgot Password
In the forgot password pane, the user is allowed to reset their password given an email. After giving their address, an email will be sent out containing a master password. This master password allows the user to reset their current password, so that they once again can log into the program. 

### Overview
After successfully logging in to the program using an existing user or a newly created one, the user will be shown the overview page of the program. This page gives the user the core information of the current situation of their finances, and allows the user to get a general idea of the total situation. Another word for this would be to get an overview. 


The structure of the overview page is in simple terms so show how much is entering and available to spend, versus how much has been spent. This should give the user an approximation of whether they should reduce their spending og increase their income, based on their own judgment. 

The left pie chart shows the total savings registered through all the accounts that the user has created. Since this program is made for maintaining control of the users finances, it is advised that these accounts match the accounts that are in the users real bank. If the user adds accounts or moves money between the accounts, the pie chart will change accordingly. 

The right pie chart represents the total spending of the current month. It gathers all the categories, and shows the total spending. The categories are already available, and if a purchase does not fit within the already available categories, the category “other” is available to use. The right pie chart does not calculate the average spending per month, it shows all the spending that is within the current month that the user uses the program in. This means that if the user has added a spending from the previous month, it will not show up in the right pie chart. 


Below the pie chart the user can find the color coded categories that present the accounts or categories along with the money available or the spending amount. This color coding is also presented with text so that the diagram is within the demands of universal design. 

Color blind simulation: https://pilestone.com/pages/color-blindness-simulator-1
Simulated with Red-Blind/Protanopia.

Below the color coding there are two tables. These tables are used to present the content of what the pie chart uses as their data. The left table shows the money added to different accounts. It also shows when money is moved between accounts. It works as a log of the different actions taken by the user. It presents the account, the amount and the date of the action. 

The right table shows the purchases of the user. The purchases are presented with the name, price, date, category and from what account the purchase was paid with. This information allows the user to get a deeper understanding of what the money is being spent on. 


### Top menu 
Above the overview, the user will find a row of icons with text. This top menu allows the user to maneuver around the program with ease. The top menu is always present, and allows the user to quickly move from any of the pages, to any of the other pages. The top menu is designed with speed and efficiency in mind. The buttons are labeled with icons and text, to allow for the maximum readability. When the buttons are hovered, the text turns white and the buttons turn dark. This allows the user to know that the button is ready to be pressed.




### Transfer 

#### Transfer between accounts
The transfer page is structured with three different functionalities. The first one is “transfer between accounts”. This allows the user to move money from one account to the other. When an account is selected on the left side, the selected account is removed from the available accounts on the right side. This is to prohibit the user from picking the same account on both sides, resulting in a neutral transfer where no money is moved. Between the two category-menus there is an arrow to show where the money is moving. This is universal, so it transcends language barriers, as well as being efficient. 

#### Register new income
The register new income functionality allows the user to add an amount of money into a specified account. This is done because we, the creators of the program, do not know the amount of money the user has. The user has to add it themselves, so that it matches with their current real life account status. If the user suddenly increases their wealth, the new money can be added at any time, to any account. The accounts that show up in the menu are based on what accounts the user has created. 


#### Add new account

The add new account function allows the user to add a new account. The function uses two text fields, where the first is the name and the second one is the amount that the user wishes to add to the account. This allows for great customization for individual users, and allows the user to make the program work for them.  




### Add Expense 

Add expense page allows the user to add new expenses. It allows the user to pick what account the money is to be withdrawn from, as well as what category the purchase fits within. The user can choose any date for the purchase, allowed by the calendar on the right side. 

The confirm button registers the expense by giving a confirmation sound. If any of the fields are left empty and the confirm-button is pressed, an alert will be shown telling the user that a field was forgotten. This is done to make sure that all the information is added correctly. 

This page can be seen as the second most used page as it is supposed to be where the user goes to add expenses throughout the duration of a set time, adding a few purchases here and there. At the end, it can be looked at as a total spending, in terms of a report. 


### Report 


Report allows the user to select between a “Result” or a “Balance” -report. After that is selected the user is able to select which month the report should present. Below are two buttons that allow for either exporting to PDF og printing the file to Excel. The reason for providing both is that some users wish to use the exported version while others might want to edit or manipulate their printed pdt before using it for something else. 





LEGG TIL EXCEL PRINT OGSÅ

### Budget

The budget page of the program allows the user to create a plan for what they wish to spend within the different categories. The user can select the category, followed by the amount, and then confirm the choice. This adds the amount to the budget. This information is used in the overview pane as well, allowing the user to see its given budget next to the spending within those same categories.  




### Bank Statement

The bank statement is the page where the user can select an account, a category, and two timeframes. With this information the program export purchases that fit within the given parameters, to present a detailed view of the purchase history. 

You can spot the Porsche 911 in the table above. 


After adding these parameters, this is what is given to the user:


This functionality allows for the user to parse through a large amount of information, and find the specified purchases within a large list. It can be looked at as a power tool for the user, and becomes more valuable the larger the purchase history becomes. 



### Settings

The settings page is where the user can view their own email and password. It also allows them to update either one. The settings page also provides a selection between different color modes, so that the program can provide a more customized experience. 




### Log out 
The logout button logs the user out of the program. This button makes sure that the information you entered is not available to anyone who does not know the user's password. The logout button provides a sense of security, something that is important since the program contains personal finance information. 

