import { useState } from "react";
import { Form, Container, FormControl, FormGroup, Col, Row, Button, ListGroup } from "react-bootstrap";
import API from "./API";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './list.css'
import { ModalProfile } from "./modals/ModalProfile";


function LandingPage(props) {

    const [email, setEmail] = useState('');
    const [ean, setEan] = useState('');
    const [listOfProfiles, setListOfProfiles] = useState([]);
    const [listOfProducts, setListOfProducts] = useState([]);
    const [showModalProfile, setShowModalProfile] = useState(false);
    const [add, setAdd] = useState(false);


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

    const addProfile = (profile) => {
        //profile validation
        if(profile.email === "" || profile.name === "" || profile.surname === "") {
            notifyProfileError("Invalid parameters")
            return
        }
        API.addProfile(profile).then(
            profile => {
                notifyProfileSuccess("Profile added correctly!")
            }//notify success
        ).catch(error => {
            notifyProfileError(error.detail)
            console.error(error.detail)
        })
        emptyListOfProfiles()
    }

    const updateProfile = (profile) => {
        //profile validation
        if(profile.email === "" || profile.name === "" || profile.surname === "" || profile.newEmail === "") {
            notifyProfileError("Invalid parameters")
            return
        }
        API.updateProfile(profile).then(
            profile => {
                notifyProfileSuccess("Profile updated correctly!")
            }//notify success
        ).catch(error => {
            notifyProfileError(error.detail)
            console.error(error.detail)
        })
        emptyListOfProfiles()
    }


    const handleCloseModalProfile = () => setShowModalProfile(false);
    const handleShowModalAddProfile = () => {setAdd(true);setShowModalProfile(true)};
    const handleShowModalUpdateProfile = () => {setAdd(false);setShowModalProfile(true)};

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

    const notifyProfileSuccess = (success) => toast.success(success, {
        position: "top-left",
        autoClose: 5000,
        hideProgressBar: false,
        closeOnClick: true,
        pauseOnHover: true,
        draggable: true,
        progress: undefined,
        theme: "colored",
        });
    

    return(
        <Container fluid className="p-5 text-center justify-content-center">
            <ModalProfile show={showModalProfile} onHide={handleCloseModalProfile} addProfile={addProfile} updateProfile={updateProfile} add={add}/>
            <ToastContainer/>
            <Row className="row d-flex justify-content-center m-auto">
            <Col >
                <Row><h1>Select the users</h1></Row>
                <Row className="d-flex justify-content-center m-auto">
                    <Form noValidate onSubmit={event => handleSubmitProfile(event)} className="w-50">
                        <FormGroup className="mb-3" controlId="testForm">
                            <FormControl autoComplete="off" type="text" placeholder="Enter the email..." onChange={(event) => {setEmail(event.target.value)}}></FormControl>
                        </FormGroup>
                    </Form>
                </Row>
                <Row className="w-75 justify-content-center m-auto">
                    <Col>
                        {listOfProfiles.length === 0 
                            ? <Button onClick={() => getProfiles()}>Get profiles</Button>
                            : <Button variant="danger" onClick={() => emptyListOfProfiles()}>{listOfProfiles.length !== 1 ? "Hide profiles" : "Hide profile"}</Button>}
                
                    </Col>
                    <Col>
                            <Button onClick={handleShowModalAddProfile}>Add profile</Button>
                    </Col>
                    <Col>
                            <Button onClick={handleShowModalUpdateProfile}>Update profile</Button>
                    </Col>
                </Row>
                {listOfProfiles.length !== 0
                    ?   <Row>
                            <ListGroup>
                                <ListGroup.Item key="title" as='li' className="d-flex justify-content-beetween list-titles mt-2">
                                    <Container>Name</Container>
                                    <Container>Surname</Container>
                                    <Container>Email</Container>
                                </ListGroup.Item>
                                {listOfProfiles.map( profile => <ProfileRow key={profile.email} profile={profile}/>)}
                            </ListGroup>
                        </Row>
                    : undefined}
            </Col>
            <Col>
                <Row><h1>Select the products</h1></Row>
                <Row className="d-flex justify-content-center m-auto">
                    <Form noValidate onSubmit={event => handleSubmitProduct(event)} className="w-50">
                        <FormGroup className="mb-3" controlId="testForm">
                            <FormControl autoComplete="off" type="text" placeholder="Enter the ean..." onChange={(event) => {setEan(event.target.value)}}></FormControl>
                        </FormGroup>
                    </Form>
                </Row>
                <Row className="w-50 justify-content-center m-auto">
                    <Col>
                        {listOfProducts.length === 0 
                            ? <Button onClick={() => getProducts()}>Get products</Button>
                            : <Button variant="danger" onClick={() => emptyListOfProduct()}>{listOfProducts.length !== 1 ? "Hide products" : "Hide product"}</Button>}
                    </Col>
                </Row>
                {listOfProducts.length !== 0
                    ?   <Row>
                            <ListGroup>
                                <ListGroup.Item key="title" as='li' className="d-flex justify-content-beetween list-titles mt-2">
                                    <Container>EAN</Container>
                                    <Container>Name</Container>
                                    <Container>Brand</Container>
                                </ListGroup.Item>
                                {listOfProducts.map( product => <ProductRow key={product.ean} product={product}/>)}
                            </ListGroup>
                        </Row>
                    : undefined}
            </Col>
        </Row>
        </Container>
    )
}


function ProfileRow(props) {
    return(
        <ListGroup.Item as='li' className="d-flex justify-content-beetween px-0 py-4 mb-2">
            <Container>{props.profile.name}</Container>
            <Container>{props.profile.surname}</Container>
            <Container>{props.profile.email}</Container>
        </ListGroup.Item>
    )

}



function ProductRow(props) {
    return(
        <ListGroup.Item as='li' className="d-flex justify-content-beetween px-0 py-3 mb-2">
            <Container>{props.product.ean}</Container>
            <Container>{props.product.name}</Container>
            <Container>{props.product.brand}</Container>
        </ListGroup.Item>
    )

}



export default LandingPage;