import { useState } from "react";
import { Button, Form, Modal } from "react-bootstrap";

export function ModalProfile(props) {

  const [name, setName] = useState("")
  const [surname, setSurname] = useState("")
  const [email, setEmail] = useState("")

  function handleOnClick() {
    props.addProfile({name, surname, email})
    setName("")
    setSurname("")
    setEmail("")
  }

  function handleOnClose() {
    props.onHide()
    setName("")
    setSurname("")
    setEmail("")
  }

    return (
        <Modal
          {...props}
          size="lg"
          aria-labelledby="contained-modal-title-vcenter"
          centered
        >
          <Modal.Header closeButton>
            <Modal.Title id="contained-modal-title-vcenter">
              Add a new profile
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form noValidate onSubmit={event => event.preventDefault()}>
                <Form.Group className="mb-3">
                    <Form.Label>First name</Form.Label>
                    <Form.Control placeholder="Enter the first name..." onChange={event => setName(event.target.value)}/>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Last name</Form.Label>
                    <Form.Control placeholder="Enter the last name..." onChange={event => setSurname(event.target.value)}/>
                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                  <Form.Label>Email address</Form.Label>
                  <Form.Control type="email" placeholder="Enter the email..." onChange={event => setEmail(event.target.value)}/>
                </Form.Group>
            </Form>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={handleOnClick}>Add</Button>
            <Button variant="danger" onClick={handleOnClose}>Close</Button>
          </Modal.Footer>
        </Modal>
      );
}
