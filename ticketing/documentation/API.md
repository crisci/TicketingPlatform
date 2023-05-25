API

# Web Application 2: Ticketing Platform
## Group: G16

## __React Client Application Routes__

- Route `/`: It is used to drive the user to the login page.

## __API Server__

### __Customers API__

- GET `/API/customers/tickets`
    - Description: Return the list of all tickets related to the customerId.
    - Request body: `none`
    - Response status: `200 Success`, `400 Bad Request`,`401 Unathorized`, `403 Forbidden`,`404 Not Found` ,`503 Internal Server Error` (generic server error).
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
                    


- GET `/API/customers/tickets/{idTicket}/messages`
    - Description: Return the list of all the messages related to the ticketId opened by the customer with customerId
    - Request body: `none`
    - Response status: `200 Success`, `400 Bad Request`,`401 Unathorized`, `403 Forbidden`,, `404 Not Found` ,`503 Internal Server Error`
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


- GET `/API/customers/email={email}`
    - Description: Return the customer that has the email passed
    - Request body: none
    - Response status:  `200 Success`, `400 Bad Request`, `401 Unathorized`, `403 Forbidden`,`404 Not Found` ,`503 Internal Server Error`
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

- GET `/API/customers/tickets`
    - Description: It is used from the customer identified by the customerId to open a ticket.
    - Request body: `none`
    - Response status: `201 Created`, `404 Not Found`, `401 Unathorized`, `403 Forbidden`, `503 Internal Server Error`
    - Response body:   
            
            [
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
                },
                ...
            ]


- POST `/API/customers/tickets`
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

    - Response status: `201 Created`, `401 Unathorized`, `403 Forbidden`, `404 Not Found`, `503 Internal Server Error`
    - Response body: `none`


- POST `/API/customers/tickets/{idTicket}/messages`
    - Description: It is used to send the message for the given ticket identified by idTicket
    - Request body: 

            {
                "body": "Text body",
                "date": "2023-05-07T20:53:23.000000",
                "listOfAttachment": [],
                "expert": null
            }
    
    - Response status: `201 Created`, `401 Unathorized`, `403 Forbidden`, `404 Not Found`, `503 Internal Server Error`
    - Response body: `none`


- PUT `/API/customers/tickets/{idTicket}/resolved`
    - Description: It set the status of the ticket to resolved.
    - Request body: `none`
    - Response status: `202 Accepted`, `403 Forbidden`, `401 Unathorized`, `404 Not Found`, `503 Internal Server Error`
    - Response body: `none`


- PUT `/API/customers/tickets/{idTicket}/reopen`
    - Description: It set the status of the ticket to reopen.
    - Request body: `none`
    - Response status: `202 Accepted`, `401 Unathorized`, `403 Forbidden`, `404 Not Found`, `500 Internal Server Error`
    - Response body: `none`

### __Experts API__

- GET `/API/expert/tickets`
    - Description: Return all the tickets associated to the idExpert
    - Request body:

          {
              "expert": "expertUUID"
          }

    - Response status: `200 Success`, `401 Unathorized`, `403 Forbidden`, `503 Internal Server Error`
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

- PUT `/API/expert/{ticketId}/stop`
    - Description: It is used by the expert to change the ticket status to stopped given the ticketId
    - Request body:

          {
              "expert": "expertUUID"
          }

    - Response status: `202 Accepted`, `403 Forbidden`, `404 Not Found`, `500 Internal Server Error`
    - Response body: `none`

### __Managers API__

- GET `/API/manager/tickets?status={status}`
  - Description: Return all the tickets with the given status
  - Request body: `none`
  - Response status: `200 Success`, `400 Bad Request`,`401 Unathorized`, `403 Forbidden`, `500 Internal Server Error`
  - Response Body: 
            
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

- GET `/API/manager/customers?id={idCustomer}`
    - Description: Return the customer with the given id.
    - Request body: `none`
    - Response status: `200 Success`, `401 Unathorized`, `403 Forbidden`, `404 Not Found` ,`503 Internal Server Error` (generic server error).
    - Response body:

          
            {
              "id": 1,
              "first_name": "Name",
              "last_name": "Surname",
              "email": "email@polito.it",
              "dob": "2000-05-06",
              "address": "Location",
              "phone_number": "00000000000",
              }
            

- GET `/API/manager/expert?id={idExpert}`
    - Description: Return the expert with the given id.
    - Request body: `none`
    - Response status: `200 Success`, `401 Unathorized`, `401 Unathorized`, `404 Not Found` ,`503 Internal Server Error` (generic server error).
    - Response body:

            {
                "id": "28bcc4db-ac83-49be-9cb8-b231b869a1a4",
                "first_name": "Expert",
                "last_name": "2",
                "email": "expert2@ticketing.com",
                "type": "EXPERT"
            }

- GET `/API/manager/experts/`
    - Description: Return the list with all the experts.
    - Request body: `none`
    - Response status: `200 Success`, `401 Unathorized`,`401 Unathorized`, `404 Not Found` ,`503 Internal Server Error` (generic server error).
    - Response body:

            {
                "id": "28bcc4db-ac83-49be-9cb8-b231b869a1a4",
                "first_name": "Expert",
                "last_name": "2",
                "email": "expert2@ticketing.com",
                "type": "EXPERT"
            }


- GET `/API/manager/tickets/{ticketId}/messages`
    - Description: Return all the messages for the given ticket identified by the ticketId
    - Request body: `none`
    - Response status: `200 Success`, `401 Unathorized`, `403 Forbidden`,`404 Not Found`, `500 Internal Server Error`
    - Response body:

             [
                {
                    "id": 1,
                    "body": "Text",
                    "date": "2023-05-07T20:53:23",
                    "expert": "28bcc4db-ac83-49be-9cb8-b231b869a1a4"
                },
                ...
            ]

- PUT `/API/tickets/{idTicket}/assign?expert={idExpert}&priority={priorityLevel}`
    - Description: It is used by the manager to assign the ticket to an expert
    - Request body: `none`
    - Response status: `202 Accepted`, `403 Forbidden`, `404 Not Found`, `500 Internal Server Error`
    - Response body: `none`



### __Products API__
 
- GET `/API/produtcs/`
    - Description: Return all the products registered
    - Request body: `none`
    - Response status: `200 Success`, `401 Unathorized`, `403 Forbidden`, `503 Internal Server Error`
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
    - Response status: `200 Success`, `401 Unathorized`, `403 Forbidden`, `503 Internal Server Error`
    - Response body:
            
            {
                "ean": "1",
                "name": "iPad",
                "brand": "Apple"
            }

- GET `/API/products/{productId}/tickets/`
    - Description: return all the tickets related to the product that has the productId provided
    - Request body: `none`
    - Response status: `200 Success`, `401 Unathorized`, `403 Forbidden`, `503 Internal Server Error`
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

- POST `/API/products`
    - Description: Adds a product
    - Request body: 

            {
                "ean": "1",
                "name": "iPad",
                "brand": "Apple"
            }

    - Response status: `201 Created`, `401 Unathorized`, `403 Forbidden`,`404 Not Found`, `503 Internal Server Error`
    - Response body: `none`

- PUT `/API/products/{productId}`
    - Description: Updates a product given its fields
    - Request body: 

            {
                "ean": "1",
                "name": "iPad",
                "brand": "Apple"
            }

    - Response status: `202 Accepted`, `401 Unathorized`, `403 Forbidden`, `404 Not Found`, `503 Internal Server Error`
    - Response body: none

- DELETE `/API/products/{productId}`
    - Description: Deletes a product
    - Request body: none
    - Response status: `204 No Content`, `401 Unathorized`, `403 Forbidden`, `404 Not Found, 503 Internal Server Error`
    - Response body: none

### __Tickets API__
- GET `/API/tickets/{idCustomer}`
    - Description: It returns all the tickets related to the idCustomer provided
    - Request body: `none`
    - Response status: `204 No Content`, `401 Unathorized`, `403 Forbidden`, `404 Not Found`, `503 Internal Server Error`
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
    - Response status: `200 Success`, `401 Unathorized`, `403 Forbidden`, `503 Internal Server Error`
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

- PUT `/API/tickets/{ticketId}/close`
    - Description: It is used to close the ticket
    - Request body: `none`
    - Response status: `204 No Content`, `401 Unathorized`, `403 Forbidden`, `404 Not Found`, `500 Internal Server Error`
    - Response body: `none`

- GET `/API/tickets/{ticketId}/messages`
    - Description: Return all the messages associated to the ticketId
    - Request body: `none`
    - Response status: `200 Success`, `401 Unathorized`, `403 Forbidden`, `503 Internal Server Error`
    - Response body:

          [
              {
                  "id": 1,
                  "body": "Text",
                  "date": "2023-05-07T20:53:23",
                  "listOfAttachment": [],
                  "expert": 1,
              },
              ...
          ]

- GET `/API/tickets/{ticketId}/status`
    - Description: Return the current status of the ticket identified by the ticketId
    - Request body: `none`
    - Response status: `200 Success`, `401 Unathorized`, `403 Forbidden`, `404 Not Found`, `500 Internal Server Error`
    - Response body:
        
                "IN_PROGESS"


- POST `/API/tickets/{idTicket}/messages`
    - Description: It is used to send the message for the given ticket identified by idTicket
    - Request body: 

            {
                "body": "Text body",
                "date": "2023-05-07T20:53:23.000000",
                "listOfAttachment": [],
                "expert": null
            }
                OR
            {
                "body": "Text body",
                "date": "2023-05-07T20:53:23.000000",
                "listOfAttachment": [],
                "expert": 1
            }

    - Response status: `201 Created`, `401 Unathorized`, `401 Unathorized`, `404 Not Found`, `503 Internal Server Error`
    - Response body: `none`

- PUT `/API/tickets/{idTicket}/resolved`
    - Description: It set the status of the ticket to resolved.
    - Request body: `none`
    - Response status: `202 Accepted`, `403 Forbidden`, `401 Unathorized`, `404 Not Found`, `503 Internal Server Error`
    - Response body: `none`

- PUT `/API/tickets/{idTicket}/reopen`
    - Description: It set the status of the ticket to reopen.
    - Request body: `none`
    - Response status: `202 Accepted`, `401 Unathorized`, `403 Forbidden`, `404 Not Found`, `500 Internal Server Error`
    - Response body: `none`

- PUT `/API/tickets/{idExpert}/{idTicket}/stop`
    - Description: It is used by the expert identified by idExpert to stop the ticket and make it opened again and reassignable to another expert.
    - Request body: `none`
    - Response status: `202 Accepted`, `401 Unathorized`, `403 Forbidden`, `404 Not Found`, `500 Internal Server Error`
    - Response body: `none`

- PUT `/API/tickets/{idExpert}/{ticketId}/close` 
    - Description: It is used by the expert identified by idExpert to close the ticket
    - Request body: `none`
    - Response status: `204 No Content`, `401 Unathorized`, `403 Forbidden`, `404 Not Found`, `500 Internal Server Error`
    - Response body: `none`


### __Messages API__
- GET `/API/messages/{messageId}/attachments`
    - Description: Returns all the attachments given the messageId
    - Request body: `none`
    - Response status: `200 Success`, `401 Unathorized`, `401 Unathorized`, `404 Not Found`, `503 Internal Server Error`
    - Response body:

             [
                {
                 "attachment": "Image as bytes"
                },
                ...
             ]

- GET `/API/messages?ticket={idTicket}`
    - Description: Return all the tickets with the given status
    - Request body: `none`
    - Response status: `200 Success`, `401 Unathorized`, `403 Forbidden`, `404 Not Found`, `500 Internal Server Error`
    - Response Body: 

            [
                {
                    "id": 1,
                    "body": "Text",
                    "date": "2023-05-07T20:53:23",
                    "expert": 1
                },
                ...
            ]

- GET `/API/attachments?id={idAttachment}`
    - Description: Return the attachment visualizable in image format
    - Request body: `none`
    - Response status: `200 Success`, `401 Unathorized`, `403 Forbidden`, `404 Not Found`, `500 Internal Server Error`
    - Response Body:    

            image.jpeg
            OR
            image.png

- POST `/API/messages/{messageId}/attachments`
    - Description: Adds all the provided attachment given the messageId
    - Request body: 

            [
                "attachment": MultipartFile
            ]

    - Response status: `201 Created`, `401 Unathorized`, `403 Forbidden`,`404 Not Found`, `503 Internal Server Error`
    - Response body: `none`

- PUT `/API/messages/{messageId}`
    - Description: Updates the body of a given message identified by messageId
    - Request body: 

            {
                "message": "Message body"
            }

  - Response status: `202 Accepted`, `401 Unathorized`, `403 Forbidden`, `404 Not Found`, `503 Internal Server Error`
  - Response body: `none`