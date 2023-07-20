const APIURL = '/API';


async function refreshToken() {
    return fetch(`${APIURL}/refresh`, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({"refresh_token": JSON.parse(localStorage.getItem("jwt")).refresh_token})}
    ).then(async res => {
        console.log(res)
        if (res.ok) {
            const jwt = await res.json()
            console.log(jwt)
            localStorage.setItem("jwt", JSON.stringify(jwt))
            return jwt
        } else {
            throw Error("An error occurred while refreshing the token.")
        }
    })};


//POST /API/login
async function logIn(credentials) {
    return fetch(`${APIURL}/login`, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(credentials)
    }).then(async res => {
        if (res.ok && res !== undefined) {
            const jwt = await res.json()
            localStorage.setItem("jwt", JSON.stringify(jwt))
            return jwt
        } else if (res.ok && res === undefined) {
            throw Error("An error occurred while logging in.")
        } else {
            throw Error("A network error occurred while logging in.")
        }
    });
}

async function signup(user) {
    return fetch(`${APIURL}/signup`, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(user)
    }).then(async res => {
        if (res.ok) {
            return true
        } else {
            throw Error("An error occurred while signing up.")
        }
    })

}

function addProduct(user, ean) {
    return new Promise((resolve, reject) => {
        return fetch(`${APIURL}/customers/product`, {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            },
            body: ean
        }).then(res => {
            if (res.ok) {
                resolve(true)
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
    })
}

function removeProduct(user, ean) {
    return new Promise((resolve, reject) => {
        return fetch(`${APIURL}/customers/product`, {
            method: 'DELETE',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            },
            body: ean
        }).then(res => {
            if (res.ok) {
                resolve(true)
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
    })
}

function getProducts() {
    return new Promise((resolve, reject) => {
        fetch(`${APIURL}/customers/products`, {
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json',
            }
        }).then(res => {
            if (res.ok) {
                res.json().then(products => resolve(products)).catch(_ => reject("Unable to parse the response."))
            } else if(res.status === 401) {
                console.log("Refreshing token...")
                refreshToken().then(_ => {
                        getProducts().then(products => resolve(products)).catch(err => reject(err))
                }).catch(err => reject(err))
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
    })
}

function getTickets() {
    return new Promise((resolve, reject) => {
        fetch(`${APIURL}/customers/tickets`, {
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res.ok) {
                res.json().then(tickets => resolve(tickets)).catch(_ => reject("Unable to parse the response."))
            } else if(res.status === 401) {
                console.log("Refreshing token...")
                refreshToken().then(_ => {
                        getTickets().then(tickets => resolve(tickets)).catch(err => reject(err))
                }).catch(err => reject(err))
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
    })
}



function openTicket(ticket) {
    return new Promise((resolve, reject) => {
        return fetch(`${APIURL}/customers/tickets`, {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(ticket)
        }).then(res => {
            if (res.ok) {
                resolve(true)
            } else if(res.status === 401) {
                console.log("Refreshing token...")
                refreshToken().then(res => {
                    if(res.ok) {
                        console.log(res.json())
                        openTicket(ticket).then(() => resolve(true)).catch(err => reject(err))
                    }
                }).catch(err => console.log("error while refreshing token: " + err.detail))
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
    })
}

function closeTicket(ticketId) {
    return new Promise((resolve, reject) => {
        return fetch(`${APIURL}/customers/tickets/${ticketId}/close`, {
            method: 'PUT',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res.ok) {
                resolve(true)
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
})}

function reopenTicket(ticketId) {
    return new Promise((resolve, reject) => {
        return fetch(`${APIURL}/customers/tickets/${ticketId}/reopen`, {
            method: 'PUT',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res.ok) {
                resolve(true)
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
})}

function resolveTicket(ticketId) {
    return new Promise((resolve, reject) => {
        return fetch(`${APIURL}/customers/tickets/${ticketId}/resolved`, {
            method: 'PUT',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res.ok) {
                resolve(true)
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
})}

//GET /API/profiles/
function getAllProfiles() {
    return new Promise((resolve, reject) => {
        fetch(`${APIURL}/customers/`)
            .then((result) => {
                if (result.ok) {
                    resolve(result.json());
                }
            }).catch((err) => {
                resolve({ detail: "Unable to communicate with the server" })
            });
    })

}


//GET /API/products
function getAllProducts(user) {
    return new Promise((resolve, reject) => {
        fetch(`${APIURL}/products`, {
            headers: {
                'Authorization': 'Bearer ' + user.access_token,
            }
        })
            .then(result => {
                if (result.ok)
                    resolve(result.json())
            }).catch(err => {
                resolve({ detail: "Unable to communicate with the server" })
            })
    })
}


//GET /API/profiles/:email
function getProfile(email) {
    return new Promise((resolve, reject) => {
        fetch(`${APIURL}/customers/email=${email}`)
            .then(result => {
                if (result.ok)
                    resolve(result.json())
                else
                    result.json().then(error => reject(error)).catch(() => reject({ detail: "Cannot parse server response." }))

            }).catch(err => {
                resolve({ detail: "Unable to communicate with the server" })
            })
    })
}

//GET /API/products/:ean
function getProduct(ean) {
    return new Promise((resolve, reject) => {
        fetch(`${APIURL}/products/id=${ean}`)
            .then(result => {
                if (result.ok)
                    resolve(result.json())
                else
                    result.json().then(error => reject(error)).catch(() => reject({ detail: "Cannot parse server response." }))

            }).catch(err => {
                resolve({ detail: "Unable to communicate with the server" })
            })
    })
}
//POST /API/profiles
export function addProfile(profile) {
    return new Promise((resolve, reject) => {
        fetch(`${APIURL}/customers`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                customer: {
                    first_name: profile.first_name,
                    last_name: profile.last_name,
                    email: profile.email,
                    dob: profile.dob,
                    address: profile.address,
                    phone_number: profile.phone_number,
                },
                password: profile.password
            }),
        }).then((response) => {
            if (response.ok) {
                resolve(null);
            } else {
                // analyze the cause of error
                response.json()
                    .then((message) => { reject(message); }) // error message in the response body
                    .catch(() => { reject({ detail: "Cannot parse server response." }) }); // something else
            }
        }).catch(() => { reject({ detail: "Cannot communicate with the server." }) }); // connection errors
    });
}

//GET /API/messages
function getMessages(ticketId) {
    return new Promise((resolve, reject) => {
        fetch(`${APIURL}/messages?ticket=${ticketId}`, {
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json',
            }
        }).then(res => {
            if (res.ok) {
                res.json().then(messages => resolve(messages)).catch(_ => reject("Unable to parse the response."))
            } else if(res.status === 401) {
                console.log("Refreshing token...")
                refreshToken().then(_ => {
                        getMessages().then(messages => resolve(messages)).catch(err => reject(err))
                }).catch(err => reject(err))
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
    })
}


//POST /API/customers/tickets/{idTicket}/messages
function addClientMessage(idTicket, message) {
    return new Promise((resolve, reject) => {
        return fetch(`${APIURL}/customers/tickets/${idTicket}/messages`, {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                body:message
            }),
        }).then(res => {
            if (res.ok) {
                resolve(true)
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
    })
}


//POST /API/expert/tickets/{idTicket}/messages
function addExpertMessage(idTicket, message) {
    return new Promise((resolve, reject) => {
        return fetch(`${APIURL}/expert/tickets/${idTicket}/messages`, {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                body:message
            }),
        }).then(res => {
            if (res.ok) {
                resolve(true)
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
    })
}


const API = { getAllProfiles, getAllProducts, getProfile, getProduct, addProfile, logIn, signup, getProducts, addProduct, removeProduct, 
    getTickets, openTicket, closeTicket, resolveTicket, reopenTicket,
    getMessages, addClientMessage, addExpertMessage };
export default API;