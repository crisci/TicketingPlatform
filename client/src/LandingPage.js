import { useEffect, useState } from "react";
import { Form, Container, FormControl, FormGroup, FormLabel, Col, Row, Navbar, Nav } from "react-bootstrap";
import { useNavigate, Outlet } from "react-router-dom";
import './custom.css';
import API from './API';

import { UserList, ProdList } from "./Components";


function DefaultLayout(props) {

    const navigate = useNavigate();

    return(
        <>
        <Navbar collapseOnSelect expand='sm' bg='dark' variant='dark'>
            <Container>
                <Nav.Link className="d-sm-block navbar-brand" onClick={() => {navigate('/')}}>Tickets</Nav.Link>
                <Navbar.Toggle aria-controls='responsive-navbar-nav'/>
                <Navbar.Collapse id='responsive-navbar-nav'>
                    <Nav>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
        <Container className='mgbottomsm backgroundtransparent pt-1'><Outlet/></Container>
        </>
    )
}

function LandingPage(props){

    const [userField, setUserField] = useState('');
    const [prodField, setProdField] = useState('');
    const [products, setProducts] = useState([]);
    const [users, setUsers] = useState([]);

    const [dirtyProd, setDirtyProd] = useState(true);
    const [dirtyUser, setDirtyUser] = useState(true);

    useEffect(() => {
        if(dirtyProd){
            API.getProducts().then(r => {
                setProducts(r);
                setDirtyProd(false);
            });
        }
    }, [dirtyProd]);

    useEffect(() => {
        if(dirtyUser){
            API.getProfile().then(r => {
                setUsers(r);
                setDirtyUser(false);
            });
        }
    }, [dirtyUser]);

    return(
        <Container fluid className="p-5 text-center justify-content-center">
            <Row className="row d-flex justify-content-center m-auto">
            <Col>
                <Row><h1>Select the users</h1></Row>
                <Row className="row d-flex justify-content-center m-auto">
                    <Form noValidate onSubmit={(e) => {e.preventDefault(); setDirtyUser(true)}} className="w-50">
                        <FormGroup className="mb-3" controlId="testForm">
                            <FormLabel></FormLabel>
                            <FormControl autoComplete="off" type="text" placeholder="Enter something..." onChange={(event) => {setUserField(event.target.value)}}></FormControl>
                        </FormGroup>
                    </Form>
                </Row>
                <Row><p>{users}</p></Row>
                <UserList></UserList>
            </Col>
            <Col>
                <Row><h1>Select the products</h1></Row>
                <Row className="row d-flex justify-content-center m-auto">
                    <Form noValidate onSubmit={(e) => {e.preventDefault(); setDirtyProd(true)}} className="w-50">
                        <FormGroup className="mb-3" controlId="testForm">
                            <FormLabel></FormLabel>
                            <FormControl autoComplete="off" type="text" placeholder="Enter something..." onChange={(event) => {setProdField(event.target.value)}}></FormControl>
                        </FormGroup>
                    </Form>
                </Row>
                <Row><p>{products}</p></Row>
                <ProdList></ProdList>
            </Col>
            </Row>
        </Container>
    )

}


export { DefaultLayout, LandingPage }