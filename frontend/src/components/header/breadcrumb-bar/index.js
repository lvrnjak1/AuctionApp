import React from 'react'
import { Link } from "react-router-dom"
import "./index.css"


function BreadcrumbBar() {
    return (
        <div id="breadcrumb-bar">
            <div id="current">ABOUT US</div>
            <div id="breadcrumb">SHOP/ ABOUT</div>
        </div>
    )
}

export default BreadcrumbBar;