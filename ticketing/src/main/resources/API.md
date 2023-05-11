API

# Web Application 2: Ticketing Platform
## Group: G16

## __React Client Application Routes__

- Route `/`: It is used to drive the user to the login page.

## __API Server__

### __Customers API__
- GET `/API/customers/id={idCustomer}`
    - Description: Return the list with all the customer and the information related to the ticket that he/she has opened.
    - Request body: `none`
    - Response status: `200 Success`, `404 Not Found` ,`503 Internal Server Error` (generic server error).
    - Response body:

          [
            {
              "id": 1,
              "first_name": "Name",
              "last_name": "Surname",
              "email": "email@polito.it",
              "dob": "2000-05-06",
              "address": "Location",
              "phone_number": "00000000000",
              }
            ]

- GET `/API/customers/{idCustomer}/tickets`
    - Description: Return the list of all tickets related to the customerId.
    - Request body: `none`
    - Response status: `200 Success`, `400 Bad Request`, `404 Not Found` ,`503 Internal Server Error` (generic server error).
    - Response body:
                  
          [
              {
                  "id": 1,
                  "title": "Title",
                  "dateTime": "2017-05-06T11:08:48", 
                  "description": "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
                  "priority": "MEDIUM",
                  "product": {
                      "ean": "1",
                      "name": "iPad",
                      "brand": "Apple"
                    }
              }
              ...
            ]
                    


- GET `/API/customers/{idCustomer}/tickets/{idTicket}/messages`
    - Description: Return the list of all the messages related to the ticketId opened by the customer with customerId
    - Request body: `none`
    - Response status: `200 Success`, `400 Bad Request`, `404 Not Found` ,`503 Internal Server Error`
    - Response body:
    

            [
                {
                    "id": 1,
                    "type": "EXPERT",
                    "body": "Text",
                    "date": "2023-05-07T20:53:23",
                    "listOfAttachment": [],
                    "expert": {
                        "id": 1,
                        "first_name": "GG",
                        "last_name": "GG",
                        "email": "text@test.it",
                        "type": "EXPERT"
                    }
                },
                {
                    "id": 2,
                    "type": "CUSTOMER",
                    "body": "Text",
                    "date": "2023-05-07T20:53:24",
                    "listOfAttachment": [],
                    "expert": null
                }
                ...
            ]


- GET `/API/customers/`
    - Description: Return the list of all the customers
    - Request body: `none`
    - Response status:  `200 Success`, `503 Internal Server Error`
    - Response body:

                [
                    {
                        "id": 1,
                        "first_name": "Name",
                        "last_name": "Surname",
                        "email": "email@polito.it",
                        "dob": "2000-05-06",
                        "address": "Location",
                        "phone_number": "00000000000"
                    },
                    ...
                ]


- GET `/API/customers/email={email}`
    - Description: Return the customer that has the email passed
    - Request body: none
    - Response status:  `200 Success`, `400 Bad Request`, `404 Not Found` ,`503 Internal Server Error`
    - Response body:
 
            {
               "id": 1,
               "first_name": "Name",
               "last_name": "Surname",
               "email": "email@polito.it",
               "dob": "2000-05-06",
               "address": "Location",
               "phone_number": "00000000000"
            }              

- POST `/API/customers`
    - Description: Used to register a new customer inside the system
    - Request body: 

            {
                "customer": {
                    "first_name": "Name",
                    "last_name": "Surname",
                    "email": "aa@polito.it",
                    "dob": "2000-05-06",
                    "address": "Location",
                    "phone_number": "00000000000"
                },
                "password": "12345678"
            }
    - Response status:  `201 Created`, `400 Bad Request`, `503 Internal Server Error`
    - Response body: `none`

- PUT `/API/customers/{email}`
    - Description: It update the customer information if the given password match the previous one
    - Request body: 

            {
                "customer": {
                    "first_name": "Name",
                    "last_name": "Surname",
                    "email": "aa1111@polito.it",
                    "dob": "2000-05-06",
                    "address": "Location",
                    "phone_number": "00000000000"
                },
                "password": "12345678"
            }

    - Response status:  `201 Created`, `400 Bad Request`, `503 Internal Server Error`
    - Response body: `none`


### __Products API__
 
- GET `/API/produtcs/`
    - Description: Return all the products registered
    - Request body: `none`
    - Response status: `200 Success`, `503 Internal Server Error`
    - Response body:

            [
                {
                    "ean": "1",
                    "name": "iPad",
                    "brand": "Apple"
                }
            ]

- GET `/API/products/{productId}`
    - Description: Return the information related to the product that has the productId provided
    - Request body: `none`
    - Response status: `200 Success`, `503 Internal Server Error`
    - Response body:
            
            {
                "ean": "1",
                "name": "iPad",
                "brand": "Apple"
            }

- GET `/API/products/{productId}/tickets`
    - Description: return all the tickets related to the product that has the productId provided
    - Request body: `none`
    - Response status: `200 Success`, `503 Internal Server Error`
    - Response body:
            
            [
                 {
                    "id": 1,
                    "dateTime": "2017-05-06T11:08:48",
                    "title": "Title",
                    "description": "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
                    "priority": "MEDIUM",
                    "product": {
                        "ean": "1",
                        "name": "iPad",
                        "brand": "Apple"
                    }
                },
                ...
            ]


### __Tickets API__
- GET `/API/tickets/{idCustomer}`
    - Description: It return all the tickets related to the idCustomer provided
    - Request body: `none`
    - Response status: `200 Success`, `503 Internal Server Error`
    - Response body:

            [
                {
                    "id": 1,
                    "dateTime": "2017-05-06T11:08:48",
                    "title": "Title",
                    "description": "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
                    "priority": "MEDIUM",
                    "product": {
                        "ean": "1",
                        "name": "iPad",
                        "brand": "Apple"
                    }
                },
                ...
            ]

- GET `/API/tickets/{idCustomer}/messages`
    - Description: Return all the tickets with the list of messages associated to the idCustomer
    - Request body: `none`
    - Response status: `200 Success`, `503 Internal Server Error`
    - Response body:

            [
                {
                    "ticket": {
                        "id": 1,
                        "dateTime": "2017-05-06T11:08:48",
                        "title": "Title",
                        "description": "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx",
                        "priority": "MEDIUM",
                        "product": {
                            "ean": "1",
                            "name": "iPad",
                            "brand": "Apple"
                        }
                    },
                    "listOfMessage": [
                        {
                            "id": 1,
                            "type": "EXPERT",
                            "body": "Text",
                            "date": "2023-05-07T20:53:23",
                            "listOfAttachment": [],
                            "expert": 1,
                        }
                        ...
                    ]
                },
                ...
            ]
            

- GET `/API/tickets/{idExpert}/{ticketId}/messages`
    - Description: Return all the messages sent by the given expert identified by the idExpert for the given ticket identified by the ticketId
    - Request body: `none`
    - Response status: `200 Success`, `404 Not Found`, `503 Internal Server Error`
    - Response body:

             [
                {
                    "id": 1,
                    "type": "EXPERT",
                    "body": "Text",
                    "date": "2023-05-07T20:53:23"
                },
                ...
            ]
            
        

- GET `/API/tickets/{idExpert}/{ticketId}/status`
    - Description: Return the current status of the ticket identified by the ticketId
    - Request body: `none`
    - Response status: `200 Success`, `404 Not Found`, `503 Internal Server Error`
    - Response body:
        
                "IN_PROGESS"

- POST `/API/tickets/{idTicket}/messages`
    - Description: It is used to send the message for the given ticket identified by idTicket
    - Request body: 

            {
                "type": 0,
                "body": "Text body",
                "date": "2023-05-07T20:53:23.000000",
                "listOfAttachment": [],
                "expert": null
            }
                OR
            {
                "type": 1,
                "body": "Text body",
                "date": "2023-05-07T20:53:23.000000",
                "listOfAttachment": [],
                "expert": 1
            }

    - Response status: `201 Created`, `404 Not Found`, `503 Internal Server Error`
    - Response body: `none`

- POST `/API/tickets/{idCustomer}`
    - Description: It is used from the customer identified by the customerId to open a ticket.
    - Request body: 
    
            {
                "dateTime": "2017-05-06T11:08:48",
                "title": "Title",
                "description": "Description",
                "priority": "HIGH",
                "product": {
                    "ean": "1",
                    "name": "iPad",
                    "brand": "Apple"
                }
            }

    - Response status: `201 Created`, `404 Not Found`, `503 Internal Server Error`
    - Response body: `none`

- PUT `/API/tickets/{idTicket}/resolved`
    - Description: It set the status of the ticket to resolved.
    - Request body: `none`
    - Response status: `202 Accepted`, `403 Forbidden`, `404 Not Found`, `503 Internal Server Error`
    - Response body: `none`

- PUT `/API/tickets/{idTicket}/reopen`
    - Description: It set the status of the ticket to reopen.
    - Request body: `none`
    - Response status: `202 Accepted`, `403 Forbidden`, `404 Not Found`, `503 Internal Server Error`
    - Response body: `none`

- PUT `/API/tickets/{idExpert}/{idTicket}/stop`
    - Description: It is used by the expert identified by idExpert to stop the ticket and make it opened again and reassignable to another expert.
    - Request body: `none`
    - Response status: `202 Accepted`, `403 Forbidden`, `404 Not Found`, `503 Internal Server Error`
    - Response body: `none`

- PUT `/API/tickets/{idExpert}/{ticketId}/close` (???)
    - Description:
    - Request body: 
    - Response status: 
    - Response body: 

### __Messages API__
- GET `/API/messages/{messageId}/attachments`
    - Description: Returns all the attachments given the messageId
    - Request body: `none`
    - Response status: `200 Success`, `404 Not Found`, `503 Internal Server Error`
    - Response body:

             [
                "imageAsBytes1",
                ...
             ]

- POST `/API/messages/{messageId}/attachments`
    - Description: Adds all the provided attachment given the messageId
    - Request body: 

            [
                "attachment": MultipartFile
            ]

    - Response status: `201 Created`, `404 Not Found`, `503 Internal Server Error`
    - Response body: `none`

- PUT `/API/messages/{messageId}`
    - Description: Updates the body of a given message identified by messageId
    - Request body: 

            {
                "message": "Message body"
            }

  - Response status: `202 Accepted`, `403 Forbidden`, `404 Not Found`, `503 Internal Server Error`
  - Response body: `none`