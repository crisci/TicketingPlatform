import { useState } from "react";
import { Button, Form, Modal } from "react-bootstrap";

export function ModalProfile(props) {

  const [name, setName] = useState("")
  const [surname, setSurname] = useState("")
  const [email, setEmail] = useState("")
  const [newEmail, setNewEmail] = useState("")


  var reg = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

  function handleOnClick() {
    if(props.add) {
      //fields validation not null
      if(email.trim() === "" || name.trim() === "" || surname.trim() === "") {
        props.notifyProfileError("Fields must not be blank")
        return
      } 
      //email validation
      if (!reg.test(email)) {
        props.notifyProfileError("Invalid email format")
        return
      }
      props.addProfile({name: name.trim(), surname: surname.trim(), email: email.toLocaleLowerCase().trim()})
    } else {
      if(email.trim() === "" || name.trim() === "" || surname.trim() === "" || newEmail.trim() === "") {
        props.notifyProfileError("Fields must not be blank")
        return
      }
      if (!reg.test(email) || !reg.test(newEmail)) {
        props.notifyProfileError("Invalid email format")
        return
      }
      props.updateProfile({name: name.trim(), surname: surname.trim(), email: email.toLocaleLowerCase().trim(), newEmail: newEmail.toLocaleLowerCase().trim()})
    }
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

  const {addProfile, updateProfile, add, notifyProfileError, ...rest} = props //to avoid react warning on the modal creation

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
