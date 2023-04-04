const APIURL = '/API';


function getAllProfiles() {
    return new Promise((resolve, reject) => { 
        fetch(`${APIURL}/profiles/`)
            .then((result) => {
                if (result.ok) {
                    resolve(result.json())
                }  
            }).catch((err) => {
                resolve({error: "Unable to communicate with the server"})
            });
     })
    
}

function getAllProducts() {
    return new Promise((resolve, reject) => { 
        fetch(`${APIURL}/products/`)
            .then(result => {
                if(result.ok)
                    resolve(result.json())
            }).catch(err => {
                resolve({error: "Unable to communicate with the server"})
            })
     })
}

function getProfile(email) {
    return new Promise((resolve, reject) => { 
        fetch(`${APIURL}/profiles/${email}`)
            .then(result => {
                if(result.ok) {
                    resolve(result.json())
                } else {
                    result.json().then(error => reject(error)).catch(() => reject({ error: "Cannot parse server response." }))
                }
            }).catch(err => {
                resolve({error: "Unable to communicate with the server"})
            })
     })
}

function getProduct(ean) {
    return new Promise((resolve, reject) => { 
        fetch(`${APIURL}/products/${ean}`)
            .then(result => {
                if(result.ok)
                    resolve(result.json())
                else 
                    result.json().then(error => reject(error)).catch(() => reject({ error: "Cannot parse server response." }))
                
            }).catch(err => {
                resolve({error: "Unable to communicate with the server"})
            })
     })
}

const API = { getAllProfiles, getAllProducts, getProfile, getProduct };
export default API;