# Banko-De-Orio

## Here are the cmd prompts to run this beautiful Banko
cd C:\Users\nesab\eclipse-workspace\A1_Client\src


## --- SERVER
javac com\server\HandleClient.java com\server\Server.java com\server\Bank.java com\seneca\accounts\Chequing.java com\seneca\accounts\GIC.java com\seneca\accounts\Taxable.java com\seneca\accounts\Account.java 	

java com.server.Server com.server.HandleClient com.server.Bank com.seneca.accounts.Chequing com.seneca.accounts.GIC com.seneca.accounts.Taxable com.seneca.accounts.Account 	

## --- CLIENT
javac com\client\FinancialApp.java
java com.client.FinancialApp


## --- JAR
cd C:\Users\nesab\Desktop\A1
jar xvf ja2.jar
java -cp .;client com/server/Server
java com/client/FinancialApp

