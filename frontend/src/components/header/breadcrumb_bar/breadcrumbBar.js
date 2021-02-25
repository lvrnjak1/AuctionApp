import React from 'react'
import "components/header/breadcrumb_bar/breadcrumbBar.scss"


function BreadcrumbBar(props) {

    return (
        <div className="breadcrumb-bar">

            <div className="current">{props.content && props.content.current}</div>
            <div className="breadcrumb">
                {props.content && props.content.breadcrumbs && props.content.breadcrumbs.map(bc => <p key={bc}>{bc}</p>)}
            </div>
        </div>
    )
}

export default BreadcrumbBar;