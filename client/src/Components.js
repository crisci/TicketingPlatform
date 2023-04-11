import React, { useState, useEffect } from 'react';
import { Card, Container, Form, Button, Modal, ListGroup, Alert, Collapse } from 'react-bootstrap/'

import './custom.css';

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

export { UserList, ProdList }