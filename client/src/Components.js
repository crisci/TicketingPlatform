import React, { useState, useEffect } from 'react';
import { Card, Container, Form, Button, Modal, ListGroup, Alert, Collapse } from 'react-bootstrap/'

import './custom.css';

function ProdList(props){
    
    return (
        <Card>
            <Card.Header>
                Products
            </Card.Header>
            <Card.Body>
                Body
            </Card.Body>
        </Card>
    )

}

function UserList(props){
    
    return (
        <Card>
            <Card.Header>
                Users
            </Card.Header>
            <Card.Body>
                Body
            </Card.Body>
        </Card>
    )

}

export { UserList, ProdList }