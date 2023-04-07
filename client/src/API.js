const APIURL = 'http://localhost:8080/API/';

//GET /API/products
async function getProducts(){
  const response = await fetch(new URL('products', APIURL));
  const res = await response.json();
  if (response.ok) {
    return res.map((r) => ({
        ean: r.ean,
        name: r.name,
        brand: r.brand
    }))
  } else {
    throw res;
  }
}

//GET /API/products/:productId
async function getProductById(productId){
    const response = await fetch(new URL('products/' + productId, APIURL));
    const res = await response.json();
    if (response.ok) {
        return res.map((r) => ({
            ean: r.ean,
            name: r.name,
            brand: r.brand
        }))
    } else {
      throw res;
    }
}

//GET /API/profiles/:email
async function getProfile(email){
    const response = await fetch(new URL('profiles/' + email, APIURL));
    const res = await response.json();
    if (response.ok) {
        return res.map((r) => ({
            id: r.id,
            name: r.name,
            surname: r.surname,
            email: r.email
        }))
    } else {
        throw res;
    }
}

//POST /API/profiles
export function addProfile(data) {
    return new Promise((resolve, reject) => {
        fetch(new URL('profiles', APIURL), {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify( data ),
        }).then((response) => {
            if (response.ok) {
                resolve(null);
            } else {
                // analyze the cause of error
                response.json()
                    .then((message) => { reject(message); }) // error message in the response body
                    .catch(() => { reject({ error: "Cannot parse server response." }) }); // something else
            }
        }).catch(() => { reject({ error: "Cannot communicate with the server." }) }); // connection errors
    });
}

//PUT /API/profiles/:email
function updateProfile(data) {
    return new Promise((resolve, reject) => {
        fetch(new URL('profiles/' + data.email, APIURL), {
            method: 'PUT',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ id: data.id, name: data.name, surname: data.surname, email: data.email }),
        }).then((response) => {
            if (response.ok) {
                resolve(null);
            } else {
                // analyze the cause of error
                response.json()
                    .then((obj) => { reject(obj); }) // error message in the response body
                    .catch(() => { reject({ error: "Cannot parse server response." }) }); // something else
            }
        }).catch(() => { reject({ error: "Cannot communicate with the server." }) }); // connection errors
    });
}

const API = { getProducts, getProductById, getProfile, addProfile, updateProfile };
export default API;