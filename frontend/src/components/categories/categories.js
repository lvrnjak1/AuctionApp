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
                        <button onClick={e => toggleExpand(index)}>{c.name}</button>
                        <div className={`sub-categories ${activeCategory !== index && "inactive"}`}>
                            <ul>
                                {c.subCategories.map(sc => {
                                    return <li key={c.subCategories.indexOf(sc)}>
                                        <button className="subcategory">
                                            {`${sc.name} (0)`}
                                        </button>
                                    </li>
                                })}
                            </ul>
                        </div>
                        <div className="line"></div>
                    </li>
                })}
                <li><button>All Categories</button></li>
            </ul>
        </div>
    );
}

export default Categories;
