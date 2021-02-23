import React from 'react';
import Categories from 'components/categories/categories';

function Home() {

    return (
        <div className="home">
            <div className="top">
                <div className="categories"><Categories /></div>
                <div className="featured-product">Featured product</div>
            </div>
            <div className="featured-collections">Featured Collections</div>
            <div className="featured-products">Featured Products</div>
            <div className="bottom">Bottom</div>
        </div>
    );
}

export default Home;
