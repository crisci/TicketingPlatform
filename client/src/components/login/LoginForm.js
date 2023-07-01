import './loginform.css'
import '../../colors.css'
import { useState } from "react";
import { useNavigate } from "react-router-dom"
import { Container, Form, Col, Row, Button, Alert } from "react-bootstrap";

function LoginForm(props) {

    const [username, setUsername] = useState('client1');
    const [password, setPassword] = useState('password');
    const [errorMessage, setErrorMessage] = useState("");

    const navigate = useNavigate()

    const handleRegistration = (event) => {
        event.preventDefault();
        navigate('/registration')
    }

    const handleSubmit = (event) => {
        event.preventDefault();
        setErrorMessage();
        if (password.trim() === '') {
            setErrorMessage('Password is mandatory.')
        } else {
            //login
            props.login({ username, password }).catch(err => {setErrorMessage(err.message)});
        }
    }

    return (
        <Row className="vh-100 m-0 login-background">
            <Col className="m-auto pb-5">
                <Container className='login-form'>
                    <h1 className="text-center mb-4">Login</h1>
                    {errorMessage
                        ? <Alert variant='danger' onClose={() => setErrorMessage()} dismissible>
                            <Alert.Heading>Something went wrong!</Alert.Heading>
                            {errorMessage}
                        </Alert>
                        : ''}
                    <Form onSubmit={event => {event.preventDefault()}}>
                        <Form.Group className="mb-3" controlId='username'>
                            <Form.Label>Username</Form.Label>
                            <Form.Control type="username" value={username} maxLength={55} onChange={(event) => { setUsername(event.target.value) }} />
                        </Form.Group>
                        <Form.Group controlId='password'>
                            <Form.Label>Password</Form.Label>
                            <Form.Control type="password" value={password} maxLength={55} onChange={(event) => { setPassword(event.target.value) }} />
                        </Form.Group>
                        <Row className='m-auto d-flex justify-content-between'>
                            <Button className='btn-primary mt-3 signin-btn' onClick={handleRegistration}>Sign up</Button>
                            <Button className='btn-primary mt-3 signin-btn' onClick={handleSubmit}>Confirm</Button>
                        </Row>
                    </Form>
                </Container>
            </Col>
        </Row>
    )
}

export default LoginForm;