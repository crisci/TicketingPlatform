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
        if (res.ok) {
            const jwt = await res.json()
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
        } else if (res.status === 400) {
            throw Error("Invalid credentials, please retry.")
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


async function expertRegistration(expert) {
    return fetch(`${APIURL}/createExpert`, {
        method: 'POST',
        credentials: 'include',
        headers: {
            'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(expert)
    }).then(async res => {
        if (res.ok) {
            return true
        } else if (res.status === 401) {
                try {
                    await refreshToken()
                    await expertRegistration(expert)
                } catch (error) {
                    throw Error(error.detail)
                }
        } else {
            throw Error(await res.json().then(err => err.detail))
        }
    }).catch(err => { throw Error(err) })
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
            } else if(res.status === 401) {
                refreshToken().then(_ => {
                        addProduct(user, ean).then(() => resolve(true)).catch(err => reject(err))
                }).catch(err => reject(err))            
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
    })
}

function removeProduct(ean) {
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
            } if(res.status === 401) {
                refreshToken().then(_ => {
                    removeProduct(ean).then(() => resolve(true)).catch(err => reject(err))
                }).catch(err => reject(err))
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
                refreshToken().then(_ => {
                        getProducts().then(products => resolve(products)).catch(err => reject(err))
                }).catch(err => reject(err))
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
    })
}

function getTickets(user) {
    let role = "";
    if(user.role === "Client"){
        role = "customers";
    }else if(user.role === "Expert"){
        role = "expert";
    } else if(user.role === "Manager"){
        role = "manager"
    }
    return new Promise((resolve, reject) => {
        fetch(`${APIURL}/${role}/tickets`, {
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res.ok) {
                res.json().then(tickets => resolve(tickets)).catch(_ => reject("Unable to parse the response."))
            } else if(res.status === 401) {
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
                refreshToken().then(res => {
                    if(res.ok) {
                        openTicket(ticket).then(() => resolve(true)).catch(err => reject(err))
                    }
                }).catch(err => reject(err))
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
            } if(res.status === 401) {
                refreshToken().then(_ => {
                    closeTicket(ticketId).then(() => resolve(true)).catch(err => reject(err))
                }).catch(err => reject(err))
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
            } else if(res.status === 401) {
                refreshToken().then(_ => {
                    reopenTicket(ticketId).then(() => resolve(true)).catch(err => reject(err))
                }).catch(err => reject(err))
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
            } else if (res.status === 401) {
                refreshToken().then(_ => {
                    resolveTicket(ticketId).then(() => resolve(true)).catch(err => reject(err))
                }).catch(err => reject(err))
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
function getAllProducts() {
    return new Promise((resolve, reject) => {
        fetch(`${APIURL}/products`, {
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token
            }
        })
            .then(result => {
                if (result.ok)
                   { resolve(result.json())}
                   else if(result.status === 401) {
                    refreshToken().then(_ => {
                            getAllProducts().then(products => resolve(products)).catch(err => reject(err))
                    }).catch(err => reject(err))
                } else {
                    result.json().then(error => reject(error)).catch(() => reject({ detail: "Cannot parse server response." }))
                }
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
                refreshToken().then(_ => {
                        getMessages(ticketId).then(messages => resolve(messages)).catch(err => reject(err))
                }).catch(err => reject(err))
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
    })
}

//GET /API/manager/experts
function getExperts(){
    return new Promise((resolve,reject)=>{
        fetch(`${APIURL}/manager/experts`,{
            method: 'GET',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            }
        }).then((response)=>{
            if (response.ok){
                resolve(response.json())
            } else {
                response.json().then(error => reject(error)).catch(() => reject({ detail: "Cannot parse server response." }))
            }
        })
    })
}

//POST /API/customers/tickets/{idTicket}/messages
function addClientMessage(idTicket, message, listOfAttachments) {
    return new Promise(async (resolve, reject) => {
        return fetch(`${APIURL}/customers/tickets/${idTicket}/messages`, {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                body: message, listOfAttachments
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
function addExpertMessage(idTicket, message, listOfAttachments) {
    return new Promise((resolve, reject) => {
        return fetch(`${APIURL}/expert/tickets/${idTicket}/messages`, {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                body: message, listOfAttachments
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

//GET /API/manager/tickets
function getManagerTickets(){
    return new Promise((resolve, reject) => {
        fetch(`${APIURL}/manager/tickets`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res.ok) {
                res.json().then(tickets => resolve(tickets)).catch(_ => reject("Unable to parse the response."))
            } else if(res.status === 401) {
                refreshToken().then(_ => {
                    getManagerTickets().then(tickets => resolve(tickets)).catch(err => reject(err))
                }).catch(err => reject(err))
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
    })
}

//PUT /API/expert/{ticketId}/stop
function stopTicket(ticketId) {
    return new Promise((resolve, reject) => {
        return fetch(`${APIURL}/expert/${ticketId}/stop`, {
            method: 'PUT',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res.ok) {
                resolve(true)
            } else if (res.status === 401) {
                refreshToken().then(_ => {
                    stopTicket(ticketId).then(() => resolve(true)).catch(err => reject(err))
                }).catch(err => reject(err))
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
})}

//GET /API/tickets/{ticketId}/expert
function getTicketCurrentExpert(ticketId){
    return new Promise((resolve, reject) => {
        fetch(`${APIURL}/tickets/${ticketId}/expert`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res.ok) {
                res.json().then(expert => resolve(expert)).catch(_ => resolve(""))
            } else if (res.status === 401) {
                refreshToken().then(_ => {
                    getTicketCurrentExpert(ticketId).then(expert => resolve(expert)).catch(_ => resolve(""))
                }).catch(err => reject(err))
            }else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
    })
}

//PUT "/API/manager/tickets/{ticketId}/reassign"
function reasignTicket(ticketId, expertId){
    return new Promise((resolve, reject) => {
        fetch(`${APIURL}/manager/tickets/${ticketId}/reassign`, {
            method: 'PUT',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(expertId)
        }).then(res => {
            if (res.ok) {
                resolve(true)
            } else if(res.status === 401) {
                refreshToken().then(_ => {
                    reasignTicket().then(expert => resolve(expert)).catch(err => reject(err))
                }).catch(err => reject(err))
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
    })
}

//GET API/tickets/{ticketId}/history
function getTicketHistory(ticketId){
    return new Promise((resolve, reject) => {
        fetch(`${APIURL}/tickets/${ticketId}/history`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res.ok) {
                res.json().then(history => resolve(history)).catch(_ => reject("Unable to parse the response."))
            } else if (res.status === 401) {
                refreshToken().then(_ => {
                    getTicketHistory(ticketId).then(history => resolve(history)).catch(err => reject(err))
                }).catch(err => reject(err))
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
    })
}

//GET API/tickets/{ticketId}/messages
function getTicketMessage(ticketId){
    return new Promise((resolve, reject) => {
        fetch(`${APIURL}/tickets/${ticketId}/messages`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res.ok) {
                res.json().then(message => resolve(message)).catch(_ => reject("Unable to parse the response."))
            } else if (res.status === 401) {
                refreshToken().then(_ => {
                    getTicketMessage(ticketId).then(message => resolve(message)).catch(err => reject(err))
                }).catch(err => reject(err))
            }else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
    })
}

//PUT /API/manager/experts/{expertId}/approve
function approveExpert(expertId){
    return new Promise((resolve, reject) => {
        fetch(`${APIURL}/manager/experts/${expertId}/approve`, {
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
    })
}

//POST /API/products
function addManagerProduct(ean, brand, name) {
    return new Promise((resolve, reject) => {
        return fetch(`${APIURL}/products`, {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ean, brand, name})
        }).then(res => {
            if (res.ok) {
                resolve(true)
            } else if (res.status === 401) {
                refreshToken().then(_ => {
                    addManagerProduct(ean, brand, name).then(() => resolve(true)).catch(err => reject(err))
                }).catch(err => reject(err))
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        }).catch(err => reject(err))
    })
}


function updateManagerProduct(oldEan, brand, name) {
    return new Promise((resolve, reject) => {
        return fetch(`${APIURL}/products/${oldEan}`, {
            method: 'PUT',
            credentials: 'include',
            body: JSON.stringify({ean: oldEan, brand, name}),
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("jwt")).access_token,
                'Content-Type': 'application/json'
            }
        }).then(res => {
            if (res.ok) {
                resolve(true)
            } else if (res.status === 401) {
                refreshToken().then(_ => {
                    updateManagerProduct(oldEan, brand, name).then(() => resolve(true)).catch(err => reject(err))
                }).catch(err => reject(err))
            } else {
                res.json().then(err => reject(err)).catch(_ => reject("Unable to parse the response."))
            }
        })
    })
}



const API = { getAllProfiles, getAllProducts, getProfile, getProduct, addProfile, logIn, signup, getProducts, addProduct, removeProduct, 
    getTickets, openTicket, closeTicket, resolveTicket, reopenTicket,getTicketHistory,getTicketMessage, approveExpert,
    getMessages, addClientMessage, addExpertMessage, getExperts,getManagerTickets,getTicketCurrentExpert,reasignTicket, stopTicket,
    expertRegistration, addManagerProduct, updateManagerProduct };

    export default API;
