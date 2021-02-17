import React from 'react'
import "./breadcrumbBar.scss"


function BreadcrumbBar(props) {

    return (
        <div id="breadcrumb-bar">
            <div id="current">{props.content.current}</div>
            <div id="breadcrumbs">
                {props.content.breadcrumbs.map(bc => <p>{bc}</p>)}
            </div>
        </div>
    )
}

export default BreadcrumbBar;