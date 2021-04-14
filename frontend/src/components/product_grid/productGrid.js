import React from 'react';
import "components/product_grid/productGrid.scss";
import Product from 'components/product/product';

function ProductGrid(props) {
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

    const getItems = () => {
        let items = [];
        for (let i = 0; i < nItems; i++) {
            items.push(
                <Product key={i} product={getProduct(i)}
                    grid={props.grid}
                    small={props.ncols === 4 || props.small}
                    categories={props.categories} />
            )
        }

        return items;
    }

    const getContent = () => {
        if (nItems === 0) return <p className="no-content">Nothing to show</p>;

        return getItems();
    }

    return (
        <div className={`${props.grid ? "grid" : "list"} ${props.small ? "col-4" : ""}`}>
            {getContent()}
        </div>
    );
}

export default ProductGrid;
