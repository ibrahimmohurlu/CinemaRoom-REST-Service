# CinemaRoom-REST-Service
## Scenario
- In this application users can buy ticket based on row and column number.
- Users can see available seats and total row and column number.
- Users can return a purchased ticket with it's token.
- Owner of movie theater can see statistics about movie theater.

## End points
- **GET** ***/seats***
  - Return the total number of rows and columns of movie theater and available seats.
- **POST** ***/purchase*** 
  - Buy a seat based on row and column number. Row and column number should be in the request body.
- **POST** ***/return***
  - Return a ticket using it's token to recognize the ticket.
- **POST** ***/stats***
  - See the statistics about movie theater like current income, purchased seats and available seats. Owner of the movie theater has a password for the stats endpoint.
  Password is 'super_secret' and hardcoded. This end point accepts password as request parameter. example: /stats?password=super_secret


