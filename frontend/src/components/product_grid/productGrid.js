import React, { useState } from 'react';
import "components/product_grid/productGrid.scss"

function ProductGrid(props) {
    const ncols = props.categories ? 3 : 4;
    const nItems = props.items.length;

    const getRows = () => {
        const rows = [];
        for (let i = 0; i < props.nrows; i++) {
            rows.push(
                <div key={i} className={`row ${props.categories ? "row-3" : "row-4"} ${props.small ? "small" : ""}`}>
                    {getCols(i)}
                </div>
            );
        }

        return rows;
    }

    const getCols = (nrow) => {
        let index = nrow * ncols;
        const cols = [];
        for (let j = 0; j < ncols && index < nItems; j++) {
            cols.push(
                <div key={j} className="product">
                    <img className="product-image"
                        src={process.env.PUBLIC_URL + '/images/shoes.jpg'}
                        alt="Something"
                    />
                    {props.categories ?
                        <>
                            <p className="name">{props.items[index].name}</p>
                        </>
                        : <>
                            <p className="name">{props.items[index].product.name}</p>
                            <p className="price">{`Start from - $${props.items[index].startPrice.toFixed(2)}`}</p>
                        </>
                    }
                </div>
            );
            index++;
        }

        return cols;
    }


    return (
        <div className="grid">
            {nItems > 0 ? getRows() : "Nothing to show"}
        </div>
    );
}

export default ProductGrid;
