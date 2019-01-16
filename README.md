"# money-transfer" 
The application use the following technology
1. DropWizard
2. rest webservice
3. flyway
4. h2 database.

#How to run the application
In order to run the application type the following:

java -jar  money-transfer-1.0.0.jar server application.yml
the application run on port 9090

#How to use the application
The application contains 3 resources
/user
/transaction
/account

user resource contains two operation /all to list all the users, and find the user by email.

List all users: http://localhost:9090/user/all

find user by email: http://localhost:9090/user/altayeb@gmail.com

account resource contains two operation /all to list all the accounts, and find by accountId.

List all the accounts: http://localhost:9090/account/all

find account by accountId: http://localhost:9090/account/1





