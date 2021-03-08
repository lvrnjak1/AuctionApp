import React, { useState } from 'react';
import "components/categories/categories.scss";
import { resetCurrentCategory, setCurrentCategory } from 'state/actions/currentCategoryActions';
import { useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';

function Categories(props) {
    const [activeCategory, setActiveCategory] = useState(-1);
    const [activeSubcategory, setActiveSubcategory] = useState();
    const dispatch = useDispatch();
    const history = useHistory();

    const toggleExpand = async index => {
        if (activeCategory === index) {
            setActiveCategory(-1);
        } else {
            setActiveCategory(index);
        }

        await handleFilter(props.items[index]);
    }

    const handleFilter = async (category, subcategory) => {
        if (!props.expandable) {
            history.push("/shop", { categoryId: category.id, categoryName: category.name });
            return;
        }

        let id = null;
        if (category && subcategory) {
            setActiveSubcategory(subcategory.id);
            dispatch(setCurrentCategory(`${category.name}/`, subcategory.name));
            id = subcategory.id;
        } else if (category) {
            dispatch(setCurrentCategory(`${category.name}/`, ""));
            id = category.id;
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
