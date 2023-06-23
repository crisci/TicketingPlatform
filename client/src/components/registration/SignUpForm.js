import './signup.css'
import { useState } from "react";
import { Container, Form, Col, Row, Button, Alert } from "react-bootstrap";

function SignUpForm(props) {

    const [username, setUsername] = useState('client1');
    const [password, setPassword] = useState('password');
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
        // if (!usernameValidation(username)) {
        //     setErrorMessage("username not valid.");
        /* } else */if (password.trim() === '') {
            setErrorMessage('Password is mandatory.')
        } else {
            //registration api than login asynch await
            props.login({ username, password }).catch(err => {setErrorMessage(err.message)});
        }
    }

    return (
        <Row className="vh-100 m-0 login-background">
            <Col className="m-auto pb-5">
                <Container className='login-form'>
                    <h1 className="text-center mb-4">Sign up</h1>
                    {errorMessage
                        ? <Alert variant='danger' onClose={() => setErrorMessage()} dismissible>
                            <Alert.Heading>Something went wrong!</Alert.Heading>
                            {errorMessage}
                        </Alert>
                        : ''}
                    <Form onSubmit={event => event.preventDefault()}>
                        <Form.Group className="mb-3" controlId='username'>
                            <Form.Label>Username</Form.Label>
                            <Form.Control type="username" value={username} onChange={(event) => { setUsername(event.target.value) }} />
                        </Form.Group>
                        <Form.Group controlId='password'>
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" value={password} onChange={(event) => { setPassword(event.target.value) }} />
                        </Form.Group>
                        <Row className='m-auto d-flex justify-content-between'>
                            <Button className='btn-primary mt-3 signup-btn' onClick={handleSubmit}>Sign in</Button>
                            <Button className='btn-primary mt-3 signup-btn' onClick={handleSubmit}>Sign up</Button>
                        </Row>
                    </Form>
                </Container>
            </Col>
        </Row>
    )
}

export default SignUpForm;