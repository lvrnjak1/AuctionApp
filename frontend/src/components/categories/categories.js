import React, { useState } from 'react';
import allCategories from "components/categories/cat"
import "components/categories/categories.scss";

function Categories() {
    const [categories, setCategories] = useState(allCategories);
    const [activeCategory, setActiveCategory] = useState(-1);

    const toggleExpand = index => {
        if (activeCategory === index) {
            setActiveCategory(-1);
        } else {
            setActiveCategory(index);
        }
    }

    return (
        <div className="categories-div">
            <button className="title">Categories</button>
            <div className="line"></div>
            <ul>
                {categories.map(c => {
                    const index = categories.indexOf(c)
                    return <li key={c.id}>
                        <button onClick={e => toggleExpand(index)}>{c.name}</button>
                        <div className={activeCategory !== index ? "inactive sub-categories" : "sub-categories"}>
                            <ul>
                                {c.subCategories.map(sc => {
                                    return <li key={c.subCategories.indexOf(sc)}>
                                        <p>{`${sc.name} (0)`}</p>
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
