A basic Java 8 & Spring Boot application that provides a Rest-style endpoint that accepts input via an HTTP POST 
(designed to be from a customer service or contact us web page) and sends an email to a customer service 
email account and also auto-replies to the submitting customer.

Uses Tomcat as the embedded servlet container. Port and other server configuration can be changed via the 
application.properties file.

Built using Java 8, Maven 3.3, Spring Boot, & AWS SES.

Prerequisites: Java 8, Maven 3.3, AWS account with SES configured

Compile & Build:  
1. Download source code  
2. Update the properties file with your AWS credentials and email account. Logging configuration via log4j, logback.xml in the resources folder  
3. Change to your appropriate AWS region in the example.customer.service.util.AWSEmailSender.java line 55  
4. Run maven build: mvn clean package  
5. Start the application: java -jar CustomerServiceApp-1.0.0.jar  
6. Ensure the application starts with no issues  
7. Submit a test HTTP POST with body to http://localhost:8080/cs/  
8. When ready deploy the compiled jar file to your server infrastructure, ensure HTTP POST requests can reach the server and port  

NOTE: This is a basic example Spring Boot application with no security configured, to configure security 
look into using Spring Security.
