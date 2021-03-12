import React from 'react';
import "components/product_grid/productGrid.scss";
import { Image, Transformation } from 'cloudinary-react';
import { useHistory } from 'react-router-dom';
import { getPublicId } from 'util/images_util';

function ProductGrid(props) {
    const ncols = props.col3 ? 3 : 4;
    const nItems = props.items.length;
    const history = useHistory();

    const handleProductClick = (id, name, categories) => {
        if (!props.categories) {
            history.push(`shop/item/${id}`);
        } else {
            history.push("/shop", { categoryId: id, categoryName: name });
        }
    }

    const getRows = () => {
        const rows = [];
        for (let i = 0; i < props.nrows; i++) {
            rows.push(
                <div key={i} className={`row ${props.col3 ? "row-3" : "row-4"} ${props.small ? "small" : ""}`}>
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

            cols.push(
                <div key={j} className="product">
                    <button className="image-button" onClick={() => handleProductClick(id, name)}>
                        <Image className="product-image" cloudName="lvrnjak" publicId={getPublicId(imageUrl)} >
                            <Transformation height={300} width={400} crop="scale" quality="auto" flags="lossy" />
                        </Image>
                    </button>
                    <p className="name">{name}</p>
                    {price ? <p className="price">{`Start from - $${price}`}</p> : ""}
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
