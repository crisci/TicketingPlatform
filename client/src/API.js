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

const API = { getAllProfiles };
export default API;