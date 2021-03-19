import React from 'react'
import "components/header/breadcrumb_bar/breadcrumbBar.scss"
import { useSelector } from 'react-redux'


function BreadcrumbBar(props) {
    const currentCategory = useSelector(state => state.currentCategory)

    const getBreadcrumbs = () => {
        if (props.content && props.content.breadcrumbs) return props.content.breadcrumbs.map(bc => <p key={bc}>{bc}</p>);
    }

    return (
        <div className="breadcrumb-bar">
            <div className="current">{props.content && props.content.current}</div>
            <div className="breadcrumb">
                {getBreadcrumbs()}
            </div>
        </div>
    )
}

export default BreadcrumbBar;