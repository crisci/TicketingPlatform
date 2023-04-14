import { useState } from "react";
import { Button, Form, Modal } from "react-bootstrap";

export function ModalProduct(props) {

    //TODO: NOT NECESSARY
    
    const [ean, setEan] = useState("")
    const [name, setName] = useState("")
    const [brand, setBrand] = useState("")

    function handleOnClick() {
      props.onSubmitAddProduct(ean, name, brand)
      setEan("")
      setName("")
      setBrand("")
    }

    function handleOnClose() {
      props.onHide()
      setEan("")
      setName("")
      setBrand("")
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
              Add a new product
            </Modal.Title>
          </Modal.Header>
          <Modal.Body>
          <Form noValidate onSubmit={event => event.preventDefault()}>
                <Form.Group className="mb-3">
                    <Form.Label>EAN</Form.Label>
                    <Form.Control placeholder="Enter the ean..." onChange={event => setEan(event.target.value)}/>
                </Form.Group>
                <Form.Group className="mb-3">
                    <Form.Label>Product name</Form.Label>
                    <Form.Control placeholder="Enter the product name..." onChange={event => setName(event.target.value)}/>
                </Form.Group>
                <Form.Group className="mb-3">
                  <Form.Label>Brand</Form.Label>
                  <Form.Control placeholder="Enter the brand..." onChange={event => setBrand(event.target.value)}/>
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
