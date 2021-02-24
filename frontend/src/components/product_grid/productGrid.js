import React from 'react';
import "components/product_grid/productGrid.scss"

function ProductGrid(props) {
    const ncols = props.categories ? 3 : 4;
    const rows = [];
    const cols = [];
    const price = 240;

    for (let j = 0; j < ncols; j++) {
        cols.push(
            <div key={j} className="product">
                <img className="product-image"
                    src={process.env.PUBLIC_URL + '/images/shoes.jpg'}
                    alt="Something"
                />
                <p className="name">Running Shoes</p>
                <p className="price">{`Start from - $${price.toFixed(2)}`}</p>
            </div>
        );
    }

    for (let i = 0; i < props.nrows; i++) {
        rows.push(
            <div key={i} className={`row ${props.categories ? "row-3" : "row-4"} ${props.small ? "small" : ""}`}>
                {cols}
            </div>
        );
    }

    return (
        <div className="grid">
            {rows}
        </div>
    );
}

export default ProductGrid;
