import { useEffect, useState } from "react";
import API from "../../API";
import Notification from "../../utils/Notifications";
import { Button, Col, Container, Form, ListGroup, OverlayTrigger, Row, Spinner, Tooltip } from "react-bootstrap";
import { BsCheckCircleFill, BsPencilSquare, BsXCircleFill } from "react-icons/bs";


function Products(props) {

    const [products, setProducts] = useState([])
    const [productForm, setProductForm] = useState(false)
    const [loadingProducts, setLoadingProducts] = useState(false)
    const [nameFilter, setNameFilter] = useState("")

    useEffect(() => {
        setProducts([])
        setLoadingProducts(true)
    }, [])

    useEffect(() => {
        if (loadingProducts === true) {
            setLoadingProducts(false)
            API.getAllProducts()
                .then(products => {
                    setProducts(products)
                    setLoadingProducts(false)
                }).catch(error => {
                    setLoadingProducts(false)
                    Notification.showError(error.message)
                })
        }
    }, [loadingProducts])

    function closeForm() {
        setProductForm(false)
    }

    function addedSuccessfully() {
        Notification.showSuccess("Product added successfully!")
        setLoadingProducts(true)
    }

    function updateProduct(oldEan, newEan, brand, name) {
        API.updateManagerProduct(oldEan, newEan, brand, name).then(_ => {
            setLoadingProducts(true)
            Notification.showSuccess("Product updated correctly!")
        }).catch(error => {
            Notification.showError(error.detail)
        })
    }

    return (
        <>
            <Container className="justify-content-center align-items-center">
                <h1 className="m-0">Products</h1>
                {!productForm ? <Button style={{ fontWeight: "700" }} className="mx-3 py-2" onClick={() => { setProductForm(true) }}>{"Add Product"}</Button> : null}
            </Container>
            {
                productForm
                    ? <ProductForm closeForm={closeForm} addedSuccessfully={addedSuccessfully} />
                    : null
            }
            <Form className="d-flex justify-content-center pt-3">
                    <Form.Group className="w-50">
                        <Form.Control type="text" placeholder="Search..." value={nameFilter} onChange={event => setNameFilter(event.target.value)} />
                    </Form.Group>
                </Form>
            <Container className="mt-3">
                {
                    loadingProducts
                        ? <Spinner variant="primary" />
                        : <ProductManagerList products={products} updateProduct={updateProduct} nameFilter={nameFilter} />
                }
            </Container>
        </>
    )
}

function ProductForm(props) {
    const [name, setName] = useState('')
    const [brand, setBrand] = useState('')
    const [ean, setEan] = useState('')

    function handleCancel() {
        setName('')
        setBrand('')
        setEan('')
        props.closeForm()
    }

    function handleConfirm() {
        if (name === "" || brand === "" || ean === "") {
            Notification.showError("Please fill all the fields!")
            return
        } else if (ean.length !== 13) {
            Notification.showError("EAN must be 13 digits!")
            return
        }
        API.addManagerProduct(ean, brand, name).then(_ => {
            setName('')
            setBrand('')
            setEan('')
            props.addedSuccessfully()
            props.closeForm()
        }).catch(error => {
            Notification.showError(error.detail)
        })
    }


    return (
        <Container className="w-50">
            <Form onSubmit={event => { event.preventDefault() }}>
                <Form.Group className="mb-3" controlId='ean'>
                    <Form.Label>EAN</Form.Label>
                    <Form.Control className="text-center" value={ean} maxLength={13} onChange={(event) => { setEan(event.target.value) }} />
                </Form.Group>
                <Form.Group className="mb-3" controlId="brand">
                    <Form.Label>Brand</Form.Label>
                    <Form.Control className="text-center" value={brand} onChange={(event) => { setBrand(event.target.value) }} />
                </Form.Group>
                <Form.Group className="mb-3" controlId="name">
                    <Form.Label>Name</Form.Label>
                    <Form.Control className="text-center" value={name} onChange={(event) => { setName(event.target.value) }} />
                </Form.Group>
                <Container className="d-flex p-0 pt-3 justify-content-between">
                    <Button variant="danger" onClick={handleCancel}>Cancel</Button>
                    <Button variant="primary" onClick={handleConfirm}>Confirm</Button>
                </Container>
            </Form>
        </Container>
    )

}


function ProductManagerList(props) {

    const [updateForm, setUpdateForm] = useState("")

    function handleU(ean) {
        setUpdateForm(ean)
    }

    function cancelUpdate() {
        setUpdateForm("")
    }

    

    const tooltip = (
        <Tooltip id="tooltip">
            Update Product
        </Tooltip>
    )

    return (
        <ListGroup variant="flush" className="px-3">
            <ListGroup.Item key="title" as='li' className="d-flex justify-content-beetween list-titles">
                <Container>EAN</Container>
                <Container>Name</Container>
                <Container>Brand</Container>
                <Container>Actions</Container>
            </ListGroup.Item>
            {
                props.products
                    .filter(p => 
                        p.name.toLowerCase().startsWith(props.nameFilter.toLowerCase())
                        || p.brand.toLowerCase().startsWith(props.nameFilter.toLowerCase())
                        || p.ean.startsWith(props.nameFilter))
                    .map(product =>
                        updateForm === product.ean
                            ? <UpdateForm ean={product.ean} name={product.name} brand={product.brand} cancelUpdate={cancelUpdate} updateProduct={props.updateProduct} />
                            : <ListGroup.Item key={product.ean} as='li' className="d-flex justify-content-beetween mb-3 py-3">
                                <Container>{product.ean}</Container>
                                <Container>{product.name}</Container>
                                <Container>{product.brand}</Container>
                                <Container ><OverlayTrigger placement="top" overlay={tooltip}>
                                    <div style={{ cursor: 'pointer' }} onClick={() => handleU(product.ean, product.brand, product.name)}>
                                        <BsPencilSquare size="20px" />
                                    </div>
                                </OverlayTrigger></Container>
                            </ListGroup.Item>)
            }
        </ListGroup>
    )
}


function UpdateForm(props) {
    const [newEan, setNewEan] = useState(props.ean)
    const [newBrand, setNewBrand] = useState(props.brand)
    const [newName, setNewName] = useState(props.name)

    const tooltip = (message) => (
        <Tooltip id="tooltip">
            {message}
        </Tooltip>
    )

    function handleX() {
        props.cancelUpdate()
    }

    function handleC() {
        props.updateProduct(props.ean, newEan, newBrand, newName)
    }


    return (
        <ListGroup.Item key={props.ean} as='li' className="d-flex justify-content-beetween mb-3 py-3">
            <Container>
                <Form.Group className="mb-3" controlId='ean'>
                    <Form.Control className="text-center" value={newEan} maxLength={13} onChange={(event) => { setNewEan(event.target.value) }} />
                </Form.Group>
            </Container>
            <Container>
                <Form.Group className="mb-3" controlId='name'>
                    <Form.Control className="text-center" value={newName} onChange={(event) => { setNewName(event.target.value) }} />
                </Form.Group>
            </Container>
            <Container>
                <Form.Group className="mb-3" controlId='name'>
                    <Form.Control className="text-center" value={newBrand} onChange={(event) => { setNewBrand(event.target.value) }} />
                </Form.Group>
            </Container>
            <Container className="d-flex justify-content-center"><OverlayTrigger placement="top" overlay={tooltip("Cancel")}>
                <div style={{ cursor: 'pointer', marginRight: "0.4rem" }} onClick={() => handleX(props.ean, props.brand, props.name)}>
                    <BsXCircleFill color="red" size="20px" />
                </div>
            </OverlayTrigger>
                <OverlayTrigger placement="top" overlay={tooltip("Update Product")}>
                    <div style={{ cursor: 'pointer' }} onClick={() => handleC(props.ean, props.brand, props.name)}>
                        <BsCheckCircleFill color="green" size="20px" />
                    </div>
                </OverlayTrigger></Container>
        </ListGroup.Item>
    )
}

export default Products;