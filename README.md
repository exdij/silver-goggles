# silver-goggles
SmartAviation task

Usage:

GET request with flightNumber and date parameters to get cargo, baggage and sum weight for requested flight at:

https://smart-aviation-task.herokuapp.com/weigh 

example:

https://smart-aviation-task.herokuapp.com/weight?flightNumber=5744&date=2016-04-25
  





GET request with Iata parameter to get flights and total number of baggage departing and arriving from/at the given airport:

https://smart-aviation-task.herokuapp.com/iata

example:

https://smart-aviation-task.herokuapp.com/iata?Iata=ANC
  


POST request with JSON body to insert your own flight data at

https://smart-aviation-task.herokuapp.com/flight

POST request with JSON body to insert your own cargo data at

https://smart-aviation-task.herokuapp.com/cargo
  
 NOTE - you can set "Trim" header to "true" if you want to disable table truncating before data insertion
