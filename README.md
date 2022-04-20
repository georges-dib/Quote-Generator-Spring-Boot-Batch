# Quote-Generator-Spring-Boot-Batch
This repo is a small project that sends an email to a list of recipients containing a quote fetched from rapidapi.com

### Under "src/main/resources/", two files must be added:
#### recipients.csv:
A CSV file containing a header of "recipientId","firstName","lastName","email"
(only firstName and email are used in the code, feel free to change)


#### custom.properties:
A properties file containing the email and password of the gmail account used to send emails, and the API key to authenticate on rapidapi.
- username
- password
- rapid_api_key

You can change the server port in "src/main/resources/application.properties".

## This code has been written to the best of my knowledge around Spring Boot and Spring Batch, and it is the first project I build using them, so I cannot recommend taking everything that you see as an example.

Happy coding :)
