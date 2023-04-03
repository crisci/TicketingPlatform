import { useState } from "react";
import { Form, Container, FormControl, FormGroup, FormLabel, Col, Row, Button } from "react-bootstrap";
import API from "./API";



function LandingPage(props) {

    const [field1, setField1] = useState('');
    const [field2, setField2] = useState('');
    const [listOfProfiles, setListOfProfiles] = useState([]);
    const [listOfProducts, setListOfProducts] = useState([]);

    function getProfiles() {
        API.getAllProfiles().then((profiles) => {
            setListOfProfiles(profiles)}).catch(error => console.error(error))
    }

    function getProducts() {
        API.getAllProducts().then((products) => {
            setListOfProducts(products)}).catch(error => console.error(error))
    }


    function emptyListOfProfiles() {
        setListOfProfiles([])
    }

    function emptyListOfProduct() {
        setListOfProducts([])
    }
    
    

    return(
        <Container fluid className="p-5 text-center justify-content-center">
            <Row className="row d-flex justify-content-center m-auto">
            <Col >
                <Row><h1>Select the users</h1></Row>
                <Row className="row d-flex justify-content-center m-auto">
                    <Form noValidate onSubmit={() => {}} className="w-50">
                        <FormGroup className="mb-3" controlId="testForm">
                            <FormLabel></FormLabel>
                            <FormControl autoComplete="off" type="text" placeholder="Enter something..." onChange={(event) => {setField1(event.target.value)}}></FormControl>
                        </FormGroup>
                    </Form>
                </Row>
                <Row className="w-25 justify-content-center m-auto">
                    {listOfProfiles.length === 0 
                        ? <Button onClick={() => getProfiles()}>Profiles</Button>
                        : <Button variant="danger" onClick={() => emptyListOfProfiles()}>Hide profiles</Button>}
                </Row>
                {listOfProfiles.map( profile => <ProfileRow key={profile.email} profile={profile}/>)}
            </Col>
            <Col>
                <Row><h1>Select the products</h1></Row>
                <Row className="row d-flex justify-content-center m-auto">
                    <Form noValidate onSubmit={() => {}} className="w-50">
                        <FormGroup className="mb-3" controlId="testForm">
                            <FormLabel></FormLabel>
                            <FormControl autoComplete="off" type="text" placeholder="Enter something..." onChange={(event) => {setField2(event.target.value)}}></FormControl>
                        </FormGroup>
                    </Form>
                </Row>
                <Row className="w-25 justify-content-center m-auto">
                    {listOfProducts.length === 0 
                        ? <Button onClick={() => getProducts()}>Products</Button>
                        : <Button variant="danger" onClick={() => emptyListOfProduct()}>Hide products</Button>}
                </Row>
                {listOfProducts.map( product => <ProductRow key={product.ean} product={product}/>)}
            </Col>
        </Row>
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