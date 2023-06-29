import { useState } from "react";
import { Button, Container, Form, InputGroup } from "react-bootstrap";
import { useNavigate } from "react-router-dom";


function TicketForm(props) {

    const navigate = useNavigate()

    const [title, setTitle] = useState("")
    const [description, setDescription] = useState("")
    const [priority, setPriority] = useState("")
    const [product, setProduct] = useState("")


    function resetStates() {
        setTitle("")
        setDescription("")
        setPriority("")
        setProduct("")
    }

    function handleCancel() {
        resetStates()
        navigate("/")
    }

    function handleConfirm() {
        const ticket = {id: 0, title, description, priority,  dateTime: new Date(), product: JSON.parse(product)}
        props.openTicket(ticket)
        navigate("/")
        resetStates()

    }

    return (
        <Container className="w-50">
            <Form onSubmit={event => { event.preventDefault() }}>
                <Form.Group className="mb-3" controlId='title'>
                    <Form.Label>Title</Form.Label>
                    <Form.Control type="username" value={title} onChange={(event) => { setTitle(event.target.value) }} />
                </Form.Group>
                <Form.Group className="mb-3" controlId="description">
                    <Form.Label>Description</Form.Label>
                    <Form.Control value={description} as="textarea" aria-label="With textarea" style={{ height: "6rem" }} onChange={(event) => { setDescription(event.target.value) }} />
                </Form.Group>
                <Form.Group className="mb-3" controlId='priority'>
                    <Form.Label>Priority</Form.Label>
                    <Form.Select value={priority} onChange={(event) => { setPriority(event.target.value) }}>
                        {priority === "" ? <option>Select the priority</option> : null}
                        <option value="LOW">Low</option>
                        <option value="MEDIUM">Medium</option>
                        <option value="HIGH">High</option>
                    </Form.Select>
                </Form.Group>
                <Form.Group className="mb-3" controlId='product'>
                    <Form.Label>Product</Form.Label>
                    <Form.Select disabled={props.products.length === 0 ? true : false} onChange={(event) => { setProduct(event.target.value) }}>
                        {product === "" ? <option>Select the product</option> : null}
                        {props.products.map(p => {return <option value={JSON.stringify(p)}>{p.name}</option>})}
                    </Form.Select>
                </Form.Group> 
                <Container className="d-flex p-0 pt-3 justify-content-between">
                    <Button variant="danger" onClick={handleCancel}>Cancel</Button>
                    <Button variant="primary" onClick={handleConfirm}>Confirm</Button>
                </Container>
            </Form>
        </Container>
    )
}

export default TicketForm;