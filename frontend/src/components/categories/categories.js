import React, { useState } from 'react';
import "components/categories/categories.scss";
import { useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { addCategoryId, setCategoryId } from 'state/actions/filterParamsActions';

function Categories(props) {
    const [activeCategory, setActiveCategory] = useState(-1);
    const dispatch = useDispatch();
    const history = useHistory();

    const toggleExpand = async (e, index) => {
        if (activeCategory === index) {
            setActiveCategory(-1);
        } else {
            setActiveCategory(index);
        }

        if (!props.expandable) {
            await handleFilter(e, props.items[index]);
        }
    }

    const handleFilter = async (e, category, subcategory) => {
        e.preventDefault();
        if (!props.expandable) {
            if (category) {
                dispatch(addCategoryId(category.id));
                history.push("/shop", { categoryId: category.id, categoryName: category.name });
            } else {
                dispatch(setCategoryId(null));
                history.push("/shop", { categoryId: null, categoryName: "All categories" });
            }
            return;
        }

        let id = null;
        if (category && subcategory) {
            id = subcategory.id;
        } else if (category) {
            id = category.id;
        }

        await props.onFilter(id);
    }

    return (
        <div className={`categories-div ${props.border && "border"}`}>
            <button className="category-title">Categories</button>
            <div className="line"></div>
            <ul>
                {props.items.map(c => {
                    const index = props.items.indexOf(c)
                    const isActive = index === activeCategory;
                    return <li key={c.id}>
                        <button onClick={e => toggleExpand(e, index)}>
                            <p>{c.name}</p>
                            {props.expandable && <p className="button-icon">{isActive ? "-" : "+"}</p>}
                        </button>
                        <div className={`sub-categories ${!isActive && "inactive"}`}>
                            <ul>
                                {c.subcategories.map(sc => {
                                    return <li key={c.subcategories.indexOf(sc)}>
                                        <button
                                            className={`subcategory`}
                                            onClick={(e) => handleFilter(e, c, sc)}
                                        >
                                            {`${sc.name} (${sc.numberOfProducts})`}
                                        </button>
                                    </li>
                                })}
                                <li key={c.subcategories.length}>
                                    <button
                                        className={`subcategory`}
                                        onClick={(e) => handleFilter(e, c)}
                                    >
                                        {`All (${c.numberOfProducts})`}
                                    </button>
                                </li>
                            </ul>
                        </div>
                        <div className="line"></div>
                    </li>
                })}
                <li><button onClick={(e) => handleFilter(e)}>All Categories</button></li>
            </ul>
        </div >
    );
}

export default Categories;
