import React from 'react';
import "components/product_grid/productGrid.scss";
import Product from 'components/product/product';

function ProductGrid(props) {
    const ncols = props.col3 ? 3 : 4;
    const nItems = props.items.length;

    const getProduct = (index) => {
        const name = props.categories ?
            props.items[index].name :
            props.items[index].product.name;
        const imageUrl = props.categories ?
            props.items[index].imageUrl :
            props.items[index].product.images[0].imageUrl;
        const price = props.categories ?
            null :
            props.items[index].startPrice.toFixed(2);
        const id = props.items[index].id;
        const description = props.categories ? null : props.items[index].product.description;
        return { name, imageUrl, price, id, description };
    }

    const getRows = () => {
        const rows = [];
        for (let i = 0; i < /*props.nrows*/3; i++) {
            rows.push(
                <div key={i} /*className={`row ${props.col3 ? "row-3" : "row-4"} ${props.small ? "small" : ""}`}*/>
                    {getCols(i)}
                </div>
            );
        }

        return rows;
    }

    const getCols = (nrow) => {
        let index = nrow * ncols;
        const cols = [];
        for (let j = 0; j < /*ncols && index < nItems*/ 3; j++) {
            cols.push(
                // <Product key={j} product={getProduct(index)} grid={props.grid} />
                <div className="product" key={j}></div>
            );
            index++;
        }

        return cols;
    }

    const getList = () => {
        let list = [];
        for (let i = 0; i < nItems; i++) {
            list.push(
                <Product key={i} product={getProduct(i)} grid={props.grid} />
            )
        }

        return list;
    }

    const getContent = () => {
        if (nItems === 0) return "Nothing to show";

        if (props.grid) {
            return getRows();
        } else {
            return getList();
        }
    }

    return (
        <div className="grid">
            {getContent()}
        </div>
    );
}

export default ProductGrid;
