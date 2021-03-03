import React, { useState } from 'react';
import "components/categories/categories.scss";
import { resetCurrentCategory, setCurrentCategory } from 'state/actions/currentCategoryActions';
import { useDispatch } from 'react-redux';

function Categories(props) {
    const [activeCategory, setActiveCategory] = useState(-1);
    const [activeSubcategory, setActiveSubcategory] = useState();
    const dispatch = useDispatch();

    const toggleExpand = index => {
        if (!props.expandable) {
            return;
        }

        if (activeCategory === index) {
            setActiveCategory(-1);
        } else {
            setActiveCategory(index);
        }
    }

    const handleFilter = async (c, sc) => {
        let id = null;
        if (c) {
            setActiveSubcategory(sc.id);
            dispatch(setCurrentCategory(`${c.name}/`, sc.name));
            id = sc.id;
        } else {
            dispatch(resetCurrentCategory(true));
        }

        await props.onFilter(id);
    }

    return (
        <div className={`categories-div ${props.border && "border"}`}>
            <button className="title">Categories</button>
            <div className="line"></div>
            <ul>
                {props.items.map(c => {
                    const index = props.items.indexOf(c)
                    const isActive = index === activeCategory;
                    return <li key={c.id}>
                        <button onClick={e => toggleExpand(index)}>
                            <p>{c.name}</p>
                            {props.expandable && <p className="button-icon">{isActive ? "-" : "+"}</p>}
                        </button>
                        <div className={`sub-categories ${!isActive && "inactive"}`}>
                            <ul>
                                {c.subcategories.map(sc => {
                                    const isActiveSub = sc.id === activeSubcategory;
                                    return <li key={c.subcategories.indexOf(sc)}>
                                        <button
                                            className={`subcategory ${isActiveSub && "active-text"}`}
                                            onClick={() => handleFilter(c, sc)}
                                        >
                                            {`${sc.name} (${sc.numberOfProducts})`}
                                        </button>
                                    </li>
                                })}
                            </ul>
                        </div>
                        <div className="line"></div>
                    </li>
                })}
                <li><button onClick={() => handleFilter(null)}>All Categories</button></li>
            </ul>
        </div>
    );
}

export default Categories;
