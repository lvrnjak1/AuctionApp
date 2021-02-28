import React, { useState } from 'react';
import "components/shop/shop.scss";
import Categories from 'components/categories/categories';
import ProductGrid from 'components/product_grid/productGrid';

function Shop() {
    const [nrows, setNRows] = useState(3);

    return (
        <div className="shop-page">
            <div className="side-bar">
                <Categories expandable items={[]} border />
            </div>
            <div className="content">
                <ProductGrid nrows={nrows} items={[]} />
                <button>Explore more</button>
            </div>
        </div>
    );
}

export default Shop;
