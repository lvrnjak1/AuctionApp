import React from 'react'
import "components/header/breadcrumb_bar/breadcrumbBar.scss"
import { useDispatch, useSelector } from 'react-redux';
import { resetSuggestion } from 'state/actions/suggestionActions';
import { useHistory } from 'react-router-dom';
import { setName } from 'state/actions/filterParamsActions';
import { setSearch } from 'state/actions/searchActions';


function BreadcrumbBar(props) {
    const suggestion = useSelector(state => state.suggestion);
    const dispatch = useDispatch();
    const history = useHistory();

    const getBreadcrumbs = () => {
        if (props.content && props.content.breadcrumbs) return props.content.breadcrumbs.map(bc => <p key={bc}>{bc}</p>);
    }

    const handleSuggestionClick = () => {
        dispatch(setName(suggestion));
        dispatch(setSearch(suggestion));
        dispatch(resetSuggestion());
        history.push("/shop");
    }

    return (
        <div className="breadcrumb-bar">
            {suggestion && <div
                className="did-you-mean">Did you mean?
                <button onClick={handleSuggestionClick} className="suggestion">{suggestion}</button>
            </div>
            }
            <div className="current">{props.content && props.content.current}</div>
            <div className="breadcrumb">
                {getBreadcrumbs()}
            </div>
        </div>
    )
}

export default BreadcrumbBar;