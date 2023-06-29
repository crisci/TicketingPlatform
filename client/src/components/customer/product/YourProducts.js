import { useState } from "react";
import { Button, Container, Form } from "react-bootstrap";
import AlertDismissibleExample from "../../Alert";
import ProductList from "./ProductList";
import Notification from "../../../utils/Notifications";


function YourProducts(props) {


    const [form, setForm] = useState(false)

    function handleShowForm() {
        setForm(true)
    }

    function disableForm() {
        setForm(false)}

    return (
        <Container className="mt-3">
            <h1>Your Products</h1>
            {form ? <ProductForm user={props.user} disableForm={disableForm} addProduct={props.addProduct}/> : null}
            {!form ? <Button onClick={handleShowForm }>{"Add Product"}</Button>: null }
    
                {props.products.length === 0 ? <h3>You have no produts</h3> : <ProductList products={props.products} removeProduct={props.removeProduct}/>
                 }
        </Container>
    )
}


function ProductForm(props) {

    const [ean, setEan] = useState('')
    const [error, setError] = useState(undefined)

    function handleAddProduct() {
        if(ean.length === 13) {
            props.addProduct(ean)
            props.disableForm()
        } else {
            Notification.showError("EAN must be 13 digits")
        }
    }
    
    function handleCancel() {
        props.disableForm()
    }

    function resetError() {
        setError(undefined)
    }
    

    return(
        <Container className="w-50">
        {error ? <AlertDismissibleExample title="Error" description={error} resetError={resetError}/> : null}
        <Form onSubmit={event => {event.preventDefault()}}>
            <Form.Group className="mb-3" controlId='ean'>
                <Form.Label>Please, insert the ean of your product</Form.Label>
                <Form.Control className="text-center" type="username" value={ean} onChange={(event) => { setEan(event.target.value) } } />
            </Form.Group>
            <Button className="me-4" variant="danger" onClick={handleCancel}>Cancel</Button>
            <Button onClick={handleAddProduct}>Confirm</Button>
        </Form>
    </Container>
    )
}

export default YourProducts;