import { useState } from "react";
import { Form, Container, FormControl, FormGroup, FormLabel, Col, Row, Button } from "react-bootstrap";
import API from "./API";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';



function LandingPage(props) {

    const [email, setEmail] = useState('');
    const [ean, setEan] = useState('');
    const [listOfProfiles, setListOfProfiles] = useState([]);
    const [listOfProducts, setListOfProducts] = useState([]);

    function getProfiles() {
        API.getAllProfiles().then((profiles) => {
            setListOfProfiles(profiles)}).catch(error => { 
                notifyProfileError(error.detail)
                console.error(error.detail)
            })
    }

    function getProducts() {
        API.getAllProducts().then((products) => {
            setListOfProducts(products)}).catch(error => {
                notifyProductError(error.detail)
                console.error(error.detail)
            })
    }


    function emptyListOfProfiles() {
        setListOfProfiles([])
    }

    function emptyListOfProduct() {
        setListOfProducts([])
    }

    function handleSubmitProfile(event) {
        event.preventDefault()
        API.getProfile(email).then(profile => {
            setListOfProfiles([profile])
        }).catch(error => { 
            notifyProfileError(error.detail)
            console.error(error.detail)
        })
    }
    

    function handleSubmitProduct(event) {
        event.preventDefault()
        API.getProduct(ean).then(product => {
            setListOfProducts([product])
        }).catch(error => {
            notifyProductError(error.detail)
            console.error(error.detail)
        })
    }

    const notifyProductError = (error) => toast.error(error, {
        position: "top-right",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: false,
        draggable: true,
        progress: undefined,
        theme: "colored",
        });

    const notifyProfileError = (error) => toast.error(error, {
        position: "top-left",
        autoClose: 3000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: false,
        draggable: true,
        progress: undefined,
        theme: "colored",
        });
    

    return(
        <Container fluid className="p-5 text-center justify-content-center">
            <Row className="row d-flex justify-content-center m-auto">
            <Col >
                <Row><h1>Select the users</h1></Row>
                <Row className="row d-flex justify-content-center m-auto">
                    <Form noValidate onSubmit={event => handleSubmitProfile(event)} className="w-50">
                        <FormGroup className="mb-3" controlId="testForm">
                            <FormControl autoComplete="off" type="text" placeholder="Enter the email..." onChange={(event) => {setEmail(event.target.value)}}></FormControl>
                        </FormGroup>
                    </Form>
                </Row>
                <Row className="w-25 justify-content-center m-auto">
                    {listOfProfiles.length === 0 
                        ? <Button onClick={() => getProfiles()}>Get profiles</Button>
                        : <Button variant="danger" onClick={() => emptyListOfProfiles()}>{listOfProfiles.length !== 1 ? "Hide profiles" : "Hide profile"}</Button>}
                </Row>
                {listOfProfiles.map( profile => <ProfileRow key={profile.email} profile={profile}/>)}
            </Col>
            <Col>
                <Row><h1>Select the products</h1></Row>
                <Row className="row d-flex justify-content-center m-auto">
                    <Form noValidate onSubmit={event => handleSubmitProduct(event)} className="w-50">
                        <FormGroup className="mb-3" controlId="testForm">
                            <FormLabel></FormLabel>
                            <FormControl autoComplete="off" type="text" placeholder="Enter the ean..." onChange={(event) => {setEan(event.target.value)}}></FormControl>
                        </FormGroup>
                    </Form>
                </Row>
                <Row className="w-25 justify-content-center m-auto">
                    {listOfProducts.length === 0 
                        ? <Button onClick={() => getProducts()}>Get products</Button>
                        : <Button variant="danger" onClick={() => emptyListOfProduct()}>{listOfProducts.length !== 1 ? "Hide products" : "Hide product"}</Button>}
                </Row>
                {listOfProducts.map( product => <ProductRow key={product.ean} product={product}/>)}
            </Col>
        </Row>
        <ToastContainer />
        </Container>
    )
}


function ProfileRow(props) {
    return(
        <Row>
            <Col>{props.profile.name}</Col>
            <Col>{props.profile.surname}</Col>
            <Col>{props.profile.email}</Col>
        </Row>
    )

}



function ProductRow(props) {
    return(
        <Row>
            <Col>{props.product.ean}</Col>
            <Col>{props.product.name}</Col>
            <Col>{props.product.brand}</Col>
        </Row>
    )

}



export default LandingPage;