import { useEffect, useState } from "react";
import { Form, Container, FormControl, FormGroup, FormLabel, Col, Row } from "react-bootstrap";
import { Outlet } from "react-router-dom";



function LandingPage(props) {

    const [field1, setField1] = useState('');
    const [field2, setField2] = useState('');
    

    return(
        <Container fluid className="p-5 text-center justify-content-center">
            <Row className="row d-flex vh-100 justify-content-center m-auto">
            <Col >
                <Row><h1>Select the users</h1></Row>
                <Row className="row d-flex vh-100 justify-content-center m-auto">
                    <Form noValidate onSubmit={() => {}} className="w-50">
                        <FormGroup className="mb-3" controlId="testForm">
                            <FormLabel></FormLabel>
                            <FormControl autoComplete="off" type="text" placeholder="Enter something..." onChange={(event) => {setField1(event.target.value)}}></FormControl>
                        </FormGroup>
                    </Form>
                </Row>
                <Row><p>{field1}</p></Row>
            </Col>
            <Col>
                <Row><h1>Select the products</h1></Row>
                <Row className="row d-flex vh-100 justify-content-center m-auto">
                    <Form noValidate onSubmit={() => {}} className="w-50">
                        <FormGroup className="mb-3" controlId="testForm">
                            <FormLabel></FormLabel>
                            <FormControl autoComplete="off" type="text" placeholder="Enter something..." onChange={(event) => {setField2(event.target.value)}}></FormControl>
                        </FormGroup>
                    </Form>
                </Row>
                <Row><p>{field2}</p></Row>
            </Col>
        </Row>
        </Container>
    )
}




export default LandingPage;