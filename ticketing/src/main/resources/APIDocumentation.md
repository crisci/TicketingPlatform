# Web Application 2: Ticketing Platform
## Group: G16

## __React Client Application Routes__

- Route `/`: It is used to drive the user to the login page.

## __API Server__

### __Customers API__
- GET `/customers/{idCustomer}`
    - Description: Return the list with all the customer and the information related to the ticket that he/she has opened.
    - Body request: `none`
    - Response status: `200 success`, `404 Not Found` ,`503 Internal Server Error` (generic server error).
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

- GET `/customers/{idCustomer}/tickets`
    - Description: Return the list of all tickets related to the customerId.
    - Body request: `none`
    - Response status: `200 success`, `404 Not Found` ,`503 Internal Server Error` (generic server error).
    - Response body:
                  
          [
              {
                  "id": 1,
                  "title": "Title",
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
                    


### __Tickets API__

- PUT `/tickets/{idTicket}/resolve`
    - Description: It is used to resolve a ticket.
    - Request body: `none`
    - Response status: `200 success`, `404 Not Found` ,`503 Internal Server Error` (generic server error).
    - Response body:
        
        [
   
        ]




            "available": 1,
          "plan": [
-
                      {
                        "codice": "01UDFOV",
                        "titolo": "Applicazioni Web I",
                        "crediti": 6,
                        "incompatibilita": "01TXYOV",
                        "propedeuticita": null
                      },
                      {
                        "codice": "01URROV",
                        "titolo": "Computational intelligence",
                        "crediti": 6,
                        "incompatibilita": null,
                        "propedeuticita": null
                      },
                      ...
                    ]
          }

    - Response status: `200 Success`, `422 Unprocessable Entity` (invalid format), `503 Internal Server Error`  (generic server error).
    - Response body:

          {
               "type": 1,
               "plan": [
                         {
                           "codice": "01UDFOV",
                           "titolo": "Applicazioni Web I",
                           "crediti": 6,
                           "incompatibilita": "01TXYOV",
                           "propedeuticita": null
                         },
                         {
                           "codice": "01URROV",
                           "titolo": "Computational intelligence",
                           "crediti": 6,
                           "incompatibilita": null,
                           "propedeuticita": null
                         },
                         ...
                       ]
          }


- PUT `/api/plans`
    - Description: permette di modificare un piano di studi esistente.
    - Request body:

          {
            "available": 1,
            "plan": [
                      {
                          "codice": "03UEWOV",
                          "titolo": "Challenge",
                          "crediti": 5,
                          "incompatibilita": null,
                          "propedeuticita": null
                      },
                      {
                          "codice": "05BIDOV",
                          "titolo": "Ingegneria del software",
                          "crediti": 6,
                          "incompatibilita": "04GSPOV",
                          "propedeuticita": "02GOLOV"
                      },
                      ...
                    ]
          }
    - Response status: `200 Success`, `401 Unauthorized` (not authenticated), `503 Internal Server Error`.
    - Response body:

          {
                "available": 1,
                "plan": [
                          {
                              "codice": "03UEWOV",
                              "titolo": "Challenge",
                              "crediti": 5,
                              "incompatibilita": null,
                              "propedeuticita": null
                          },
                          {
                              "codice": "05BIDOV",
                              "titolo": "Ingegneria del software",
                              "crediti": 6,
                              "incompatibilita": "04GSPOV",
                              "propedeuticita": "02GOLOV"
                          },
                          ...
                        ]
          }

- DELETE `/api/plans`
    - Description: consente di eliminare il piano di studi.
    - Request body: `none`
    - Response status: `200 Success`, `401 Unauthorized` (not authenticated), `503 Internal Server Error`.
    - Response body:

          {
            "message": "Delete success"
          }

    
