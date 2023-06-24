import { useState } from "react";
import { Button, Form, Modal } from "react-bootstrap";

export function ModalProfile(props) {

  const [first_name, setFirstName] = useState("")
  const [last_name, setLastName] = useState("")
  const [email, setEmail] = useState("")
  const [newEmail, setNewEmail] = useState("")
  const [dob, setDob] = useState("")
  const [address, setAddress] = useState("")
  const [phoneNumber, setPhoneNumber] = useState("")
  const [password, setPassword] = useState("")


  var reg = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

  function handleOnClick() {
    if(props.add) {
      //fields validation not null
      if(password.length < 8) {
        props.notifyProfileError("Password must be at least 8 characters long")
      }
      if(email.trim() === "" || first_name.trim() === "" || last_name.trim() === "") {
        props.notifyProfileError("Fields must not be blank")
        return
      } 
      //email validation
      if (!reg.test(email)) {
        props.notifyProfileError("Invalid email format")
        return
      }
      props.addProfile({first_name: first_name.trim(), last_name: last_name.trim(), email: email.toLocaleLowerCase().trim(),  dob: dob, address: address, phone_number: phoneNumber, password: password})
    } else {
      if(email.trim() === "" || first_name.trim() === "" || last_name.trim() === "" || newEmail.trim() === "") {
        props.notifyProfileError("Fields must not be blank")
        return
      }
      if (!reg.test(email) || !reg.test(newEmail)) {
        props.notifyProfileError("Invalid email format")
        return
      }
      props.updateProfile({first_name: first_name.trim(), last_name: last_name.trim(), email: email.toLocaleLowerCase().trim(), newEmail: newEmail.toLocaleLowerCase().trim(), dob: dob, address: address, phone_number: phoneNumber, password: password})
    }
    props.onHide()
    setFirstName("")
    setLastName("")
    setEmail("")
    setNewEmail("")
    setAddress("")
    setDob("")
    setPhoneNumber("")
    setPassword("")
  }

  function handleOnClose() {
    props.onHide()
    setFirstName("")
    setLastName("")
    setEmail("")
    setNewEmail("")
    setAddress("")
    setDob("")
    setPhoneNumber("")
    setPassword("")
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
                    <Form.Control placeholder="Enter the first name..." onChange={event => setFirstName(event.target.value)}/>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>{props.add ? "Last name": "New last name"}</Form.Label>
                    <Form.Control placeholder="Enter the last name..." onChange={event => setLastName(event.target.value)}/>
                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                  <Form.Label>{props.add ? "Email address": "Old email address"}</Form.Label>
                  <Form.Control type="email" placeholder="Enter the email..." onChange={event => setEmail(event.target.value)}/>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>{props.add ? "Date of birth": "New Date of birth"}</Form.Label>
                    <Form.Control type="date" placeholder="Enter the date of birth name..." onChange={event => setDob(event.target.value)}/>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>{props.add ? "Address": "New address"}</Form.Label>
                    <Form.Control placeholder="Enter the address..." onChange={event => setAddress(event.target.value)}/>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>{props.add ? "Phone number": "New phone number"}</Form.Label>
                    <Form.Control placeholder="Enter the phone number..." onChange={event => setPhoneNumber(event.target.value)}/>
                </Form.Group>
                {!props.add ?
                  <Form.Group className="mb-3" controlId="formBasicEmail">
                  <Form.Label> New email address </Form.Label>
                  <Form.Control type="email" placeholder="Enter the email..." onChange={event => setNewEmail(event.target.value)}/>
                </Form.Group>
                : undefined}
                 <Form.Group className="mb-3">
                    <Form.Label>{props.add ? "Password": "Insert the password for that profile"}</Form.Label>
                    <Form.Control type="password" placeholder="Enter the password..." onChange={event => setPassword(event.target.value)}/>
                </Form.Group>
            </Form>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="success" onClick={handleOnClick}>{props.add ? "Add" : "Update"} </Button>
            <Button variant="danger" onClick={handleOnClose}>Close</Button>
          </Modal.Footer>
        </Modal>
      );
}
