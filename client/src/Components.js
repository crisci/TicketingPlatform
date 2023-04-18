import React, { useState } from 'react';
import { Card, Button, Modal, Form, FormControl, FormGroup, FormLabel } from 'react-bootstrap/'
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import './custom.css';
import API from './API';

function ProdList(props){
    
    if(Array.isArray(props.products)){
        return (
            props.products.map((r) => 
                <Card key={r.ean} className='mt-1'>
                    <Card.Header>
                        {r.brand}
                    </Card.Header>
                    <Card.Body>
                        {r.name}
                    </Card.Body>
                </Card>
            )
        )
    }else{
        return (
                <Card key={props.products.ean} className='mt-1'>
                    <Card.Header>
                        {props.products.brand}
                    </Card.Header>
                    <Card.Body>
                        {props.products.name}
                    </Card.Body>
                </Card>
        )
    }
    

}

function UserList(props){
    
    if(props.user != null){
        return (
            <Card key={props.user.id} className='mt-1'>
                <Card.Header>
                    {props.user.name} {props.user.surname}
                </Card.Header>
                <Card.Body>
                    {props.user.email}
                </Card.Body>
            </Card>
        )
    }
}

function UserModal(){

    const [show, setShow] = useState(false);

    const [name, setName] = useState("");
    const [surname, setSurname] = useState("");
    const [email, setEmail] = useState("");

    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);
    
    function handlePost(e){
        e.preventDefault();
        if(name != "" && surname != "" && email != ""){
            API.addProfile({name, surname, email}).then(() => {
                handleClose();
                toast.success("User added!", {autoClose: 3000})
            })
        }
    }

    function handlePut(e){
        e.preventDefault();
        if(name != "" && surname != "" && email != ""){
            API.updateProfile({name, surname, email}).then(() => {
                handleClose();
                toast.success("User updated!", {autoClose: 3000})
            })
        }
    }

    return (
        <>
        <Button onClick={handleShow}>
            Add/Modify user
        </Button>
        <ToastContainer/>
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
            <Modal.Title>Modal heading</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form noValidate className="w-50">
                    <FormGroup className="mb-3" controlId="testForm">
                        <FormLabel>Name</FormLabel>
                        <FormControl autoComplete="off" type="text" placeholder="Pippo" onChange={(e) => {setName(e.target.value)}} ></FormControl>
                        <FormLabel>Surname</FormLabel>
                        <FormControl autoComplete="off" type="text" placeholder="Mario" onChange={(e) => {setSurname(e.target.value)}} ></FormControl>
                        <FormLabel>Email</FormLabel>
                        <FormControl autoComplete="off" type="text" placeholder="pippo@mario.com" onChange={(e) => {setEmail(e.target.value)}} ></FormControl>
                    </FormGroup>
                </Form>
            </Modal.Body>
            <Modal.Footer>
            <Button onClick={handlePost}>Add user</Button>
            <Button onClick={handlePut}>Update user</Button>
            <Button variant="danger" onClick={handleClose}>
                Close
            </Button>
            </Modal.Footer>
        </Modal>
        </>
    );
    
}

export { UserList, ProdList, UserModal }