import { useState } from "react";
import { Alert, Button, Col, Container, Form, Row, Spinner } from "react-bootstrap"
import { useNavigate } from "react-router-dom";


function ExpertRegistration(props) {

    const navigate = useNavigate();

    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
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
           props.handleCreateExpert({ username, password, firstName, lastName, email })
        }
    }

    const handleCancel = (event) => {
        event.preventDefault();
        navigate("/")
    }

    return (
        <>
            <h1>Expert Registration</h1>
            <Row className="mt-4">
            <Col>
                <Container className="w-50">
                    {errorMessage
                        ? <Alert variant='danger' onClose={() => setErrorMessage()} dismissible>
                            <Alert.Heading>Something went wrong!</Alert.Heading>
                            {errorMessage}
                        </Alert>
                        : ''}
                    <Form onSubmit={event => event.preventDefault()} style={{textAlign:"initial"}}>
                        <Row className="mb-3">
                            <Col>
                                <Form.Group controlId='firstName'>
                                    <Form.Label >First Name</Form.Label>
                                    <Form.Control type="firstName" value={firstName} maxLength={55} onChange={(event) => { setFirstName(event.target.value) }} />
                                </Form.Group>
                            </Col>
                            <Col>
                                <Form.Group controlId='lastName'>
                                    <Form.Label>Last Name</Form.Label>
                                    <Form.Control type="lastName" value={lastName} maxLength={55} onChange={(event) => { setLastName(event.target.value) }} />
                                </Form.Group>
                            </Col>
                        </Row>
                        <Form.Group className="mb-3" controlId='username'>
                            <Form.Label>Username</Form.Label>
                            <Form.Control type="username" value={username} maxLength={55} onChange={(event) => { setUsername(event.target.value) }} />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId='email'>
                            <Form.Label>Email</Form.Label>
                            <Form.Control type="email" value={email} maxLength={55} onChange={(event) => { setEmail(event.target.value) }} />
                        </Form.Group>
                        <Form.Group className="mb-3" controlId='password'>
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" value={password} maxLength={55} onChange={(event) => { setPassword(event.target.value) }} />
                        </Form.Group>
                       {!props.loadingExpertRegistration 
                        ? <Row className='m-auto d-flex justify-content-between'>
                            <Button className='btn-primary mt-3 signup-btn' variant="danger" onClick={handleCancel}>Cancel</Button>
                            <Button className='btn-primary mt-3 signup-btn' onClick={handleSubmit}>Create</Button>
                        </Row>
                        : <Row className="justify-content-center">
                        <Spinner variant="primary" />
                        </Row>}
                    </Form>
                </Container>
            </Col>
        </Row>
        </>
    )
}

export default ExpertRegistration;