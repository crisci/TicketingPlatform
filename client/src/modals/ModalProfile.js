import { useState } from "react";
import { Button, Form, Modal } from "react-bootstrap";

export function ModalProfile(props) {

  const [name, setName] = useState("")
  const [surname, setSurname] = useState("")
  const [email, setEmail] = useState("")
  const [newEmail, setNewEmail] = useState("")

  function handleOnClick() {
    if(props.add) 
      props.addProfile({name, surname, email})
    else
      props.updateProfile({name, surname, email, newEmail})
    props.onHide()
    setName("")
    setSurname("")
    setEmail("")
    setNewEmail("")
  }

  function handleOnClose() {
    props.onHide()
    setName("")
    setSurname("")
    setEmail("")
    setNewEmail("")
  }

  const {addProfile, updateProfile, add, ...rest} = props //to avoid react warning on the modal creation

    return (
        <Modal
          {...rest}
          size="lg"
          aria-labelledby="contained-modal-title-vcenter"
          centered
        >
          <Modal.Header closeButton>
            <Modal.Title id="contained-modal-title-vcenter">
              {props.add ? "Add a new profile" : "Update profile"} 
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form noValidate onSubmit={event => event.preventDefault()}>
                <Form.Group className="mb-3">
                    <Form.Label>{props.add ? "First name": "New first name"}</Form.Label>
                    <Form.Control placeholder="Enter the first name..." onChange={event => setName(event.target.value)}/>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>{props.add ? "Last name": "New last name"}</Form.Label>
                    <Form.Control placeholder="Enter the last name..." onChange={event => setSurname(event.target.value)}/>
                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                  <Form.Label>{props.add ? "Email address": "Old email address"}</Form.Label>
                  <Form.Control type="email" placeholder="Enter the email..." onChange={event => setEmail(event.target.value)}/>
                </Form.Group>
                {!props.add ?
                  <Form.Group className="mb-3" controlId="formBasicEmail">
                  <Form.Label> New email address </Form.Label>
                  <Form.Control type="email" placeholder="Enter the email..." onChange={event => setNewEmail(event.target.value)}/>
                </Form.Group>
                : undefined}
            </Form>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={handleOnClick}>{props.add ? "Add" : "Update"} </Button>
            <Button variant="danger" onClick={handleOnClose}>Close</Button>
          </Modal.Footer>
        </Modal>
      );
}
