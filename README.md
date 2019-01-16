# money-transfer 

The application use the following technology
1. DropWizard
2. rest webservice
3. flyway
4. h2 database.

# How to run the application

In order to run the application type the following:

java -jar  money-transfer-1.0.0.jar server application.yml
The application run on port 9090

# How to use the application

The application contains 3 main resources
/user
/transaction
/account

1. user resource contains two operation /all to list all the users, and find the user by email.

List all users: http://localhost:9090/user/all

find user by email: http://localhost:9090/user/altayeb@gmail.com

2. account resource contains two operation /all to list all the accounts, and find by accountId.

List all the accounts: http://localhost:9090/account/all

find account by accountId: http://localhost:9090/account/1

3. transaction resource this service mainly used to tranfer the money between the two accounts

curl -X POST -H "Content-Type: application/json" -d '{"currencyCode":"USD", "amount":10.0000, "fromAccountId":1, "toAccountId":2}' http://localhost:9090/transaction

to check if fromAccountId debited type http://localhost:9090/account/1  
to check if toAccountId credited type http://localhost:9090/account/2

# Validations

Three main validation should be proceed when the transfer for the fund take place
    1. transaction currency should be ISO valid
    2. transaction currency should be the same between the sender and receiver
    3. the sender should not have Insufficient Funds








