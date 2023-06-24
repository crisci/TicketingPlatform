import './signup.css'
import { useState } from "react";
import { Container, Form, Col, Row, Button, Alert } from "react-bootstrap";
import { useNavigate } from 'react-router-dom';

function SignUpForm(props) {

    const navigate = useNavigate();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [dob, setDob] = useState('');
    const [address, setAddress] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [errorMessage, setErrorMessage] = useState("");

    const emailValidation = (username) => {
        return String(username)
            .toLowerCase()
            .match(
                /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
            );
    };


    const handleSubmit = (event) => {
        event.preventDefault();
        setErrorMessage();
        //email validation
        if (!emailValidation(email)) {
            setErrorMessage("Email not valid.");
        } else if (password.trim() === '') {
            setErrorMessage('Password is mandatory.')
        } else {
            //registration api than login asynch await
            console.log(JSON.stringify({ username, password, firstName, lastName, email, dob, address, phoneNumber }))
            props.signup({ username, password, firstName, lastName, email, dob, address, phoneNumber }).catch(err => { setErrorMessage(err.message) });
        }
    }

    const handleLogin = (event) => {
        event.preventDefault();
        navigate('/login')
    }

    return (
        <Row className="m-0 signup-background">
            <Col>
                <Container className='signup-form'>
                    <h1 className="text-center mb-4">Sign up</h1>
                    {errorMessage
                        ? <Alert variant='danger' onClose={() => setErrorMessage()} dismissible>
                            <Alert.Heading>Something went wrong!</Alert.Heading>
                            {errorMessage}
                        </Alert>
                        : ''}
                    <Form onSubmit={event => event.preventDefault()}>
                        <Row className="mb-3">
                            <Col>
                                <Form.Group controlId='firstName'>
                                    <Form.Label>First Name</Form.Label>
                                    <Form.Control type="firstName" value={firstName} onChange={(event) => { setFirstName(event.target.value) }} />
                                </Form.Group>
                            </Col>
                            <Col>
                                <Form.Group controlId='lastName'>
                                    <Form.Label>Last Name</Form.Label>
                                    <Form.Control type="lastName" value={lastName} onChange={(event) => { setLastName(event.target.value) }} />
                                </Form.Group>
                            </Col>
                        </Row>
                        <Row className="mb-3">
                            <Col>
                                <Form.Group controlId='dob'>
                                    <Form.Label>Date of Birth</Form.Label>
                                    <Form.Control type="date" value={dob} onChange={(event) => { setDob(event.target.value) }} />
                                </Form.Group>
                            </Col>
                            <Col>
                                <Form.Group controlId='phone_number'>
                                    <Form.Label>Phone number</Form.Label>
                                    <Form.Control type="tel" value={phoneNumber} onChange={(event) => { setPhoneNumber(event.target.value) }} />
                                </Form.Group>
                            </Col>
                        </Row>
                        <Form.Group className="mb-3" controlId='address'>
                            <Form.Label>Address</Form.Label>
                            <Form.Control type="address" value={address} onChange={(event) => { setAddress(event.target.value) }} />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId='username'>
                            <Form.Label>Username</Form.Label>
                            <Form.Control type="username" value={username} onChange={(event) => { setUsername(event.target.value) }} />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId='email'>
                            <Form.Label>Email</Form.Label>
                            <Form.Control type="email" value={email} onChange={(event) => { setEmail(event.target.value) }} />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId='password'>
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" value={password} onChange={(event) => { setPassword(event.target.value) }} />
                        </Form.Group>
                        <Row className='m-auto d-flex justify-content-between'>
                            <Button className='btn-primary mt-3 signup-btn' onClick={handleLogin}>Sign in</Button>
                            <Button className='btn-primary mt-3 signup-btn' onClick={handleSubmit}>Sign up</Button>
                        </Row>
                    </Form>
                </Container>
            </Col>
        </Row>
    )
}

export default SignUpForm;