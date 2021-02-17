import React from 'react'
import "./index.css"


function BreadcrumbBar(props) {

    return (
        <div id="breadcrumb-bar">
            <div id="current">{props.content.current}</div>
            <div id="breadcrumbs">
                {props.content.breadcrumbs.map(bc => <p class="breadcrumb">{bc}</p>)}
            </div>
        </div>
    )
}

export default BreadcrumbBar;