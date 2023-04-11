import { useEffect, useState } from "react";
import { Form, Container, FormControl, FormGroup, FormLabel, Col, Row, Navbar, Nav, Button } from "react-bootstrap";
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
    const [users, setUsers] = useState(null);

    const [dirtyProd, setDirtyProd] = useState(false);
    const [dirtyUser, setDirtyUser] = useState(false);
    const [hideUserButton, setHideUserButton] = useState(true);

    useEffect(() => {
        if(dirtyProd){
            if(prodField != ''){
                API.getProductById(prodField).then(r => {
                    setProducts(r);
                });
                setDirtyProd(false);
            }else{
                API.getProducts().then(r => {
                    setProducts(r);
                });
                setDirtyProd(false);
            }
            
        }
    }, [dirtyProd]);

    useEffect(() => {
        if(dirtyUser){
            if(userField != ''){
                API.getProfile(userField).then(r => {
                    setUsers(r);
                });
                setDirtyUser(false);
            }else{
                setDirtyUser(false);
            }
        }
    }, [dirtyUser]);

    useEffect(() => {
        if(userField != ''){
            setHideUserButton(false);
        }else{
            setHideUserButton(true);
        }
    }, [userField]);

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
                            { hideUserButton ? <Button className="mt-3 mb-3" type="submit" >Get user</Button> : <Button className="mt-3 mb-3" type="submit" >Get user</Button> }
                            
                        </FormGroup>
                    </Form>
                </Row>
                <UserList user={users}></UserList>
            </Col>
            <Col>
                <Row><h1>Select the products</h1></Row>
                <Row className="row d-flex justify-content-center m-auto">
                    <Form noValidate onSubmit={(e) => {e.preventDefault(); setDirtyProd(true)}} className="w-50">
                        <FormGroup className="mb-3" controlId="testForm">
                            <FormLabel></FormLabel>
                            <FormControl autoComplete="off" type="text" placeholder="Enter something..." onChange={(event) => {setProdField(event.target.value)}}></FormControl>
                            <Button className="mt-3 mb-3" type="submit" >Get products</Button>
                        </FormGroup>
                    </Form>
                </Row>
                <ProdList products={products}></ProdList>
            </Col>
            </Row>
        </Container>
    )

}


export { DefaultLayout, LandingPage }