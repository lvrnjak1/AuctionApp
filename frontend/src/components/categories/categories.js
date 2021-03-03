import React, { useState } from 'react';
import "components/categories/categories.scss";

function Categories(props) {
    const [activeCategory, setActiveCategory] = useState(-1);

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

    return (
        <div className={`categories-div ${props.border && "border"}`}>
            <button className="title">Categories</button>
            <div className="line"></div>
            <ul>
                {props.items.map(c => {
                    const index = props.items.indexOf(c)
                    return <li key={c.id}>
                        <button onClick={e => toggleExpand(index)}>
                            <p>{c.name}</p>
                            {props.expandable && <p className="button-icon">{index === activeCategory ? "-" : "+"}</p>}
                        </button>
                        <div className={`sub-categories ${activeCategory !== index && "inactive"}`}>
                            <ul>
                                {c.subcategories.map(sc => {
                                    return <li key={c.subcategories.indexOf(sc)}>
                                        <button className="subcategory" onClick={() => props.onFilter(sc.id)}>
                                            {`${sc.name} (${sc.numberOfProducts})`}
                                        </button>
                                    </li>
                                })}
                            </ul>
                        </div>
                        <div className="line"></div>
                    </li>
                })}
                <li><button onClick={() => props.onFilter(null)}>All Categories</button></li>
            </ul>
        </div>
    );
}

export default Categories;
