import React from 'react';
import CustomTable from 'components/custom_table/customTable';

const dummyData = {
    headings: [
        <p className="p-reset center-align">Item</p>,
        <p className="p-reset">Name</p>,
        <p className="p-reset">Time Left</p>,
        <p className="p-reset center-align">Your Price</p>,
        <p className="p-reset center-align">No. Bids</p>,
        <p className="p-reset center-align ">Highest Bid</p>,
        ""
    ],
    data: [
        [<div className="image"></div>,
        <p className="bold-text p-reset">Monogrammed Rain Jacket - Two Color</p>,
        <p className="p-reset">12h</p>,
        <p className="p-reset center-align">$80.00</p>,
        <p className="p-reset center-align">4</p>,
        <p className="blue-text bold-text p-reset center-align">$120.00</p>,
        <button className="table-button">View</button>
        ],
        [<div className="image"></div>,
        <p className="bold-text p-reset">Product 1</p>,
        <p className="p-reset">12h</p>,
        <p className="p-reset center-align">$80.00</p>,
        <p className="p-reset center-align">4</p>,
        <p className="blue-text bold-text p-reset center-align">$120.00</p>,
        <button className="table-button">View</button>
        ],
        [<div className="image"></div>,
        <p className="bold-text p-reset">Product 1</p>,
        <p className="p-reset">12h</p>,
        <p className="p-reset center-align">$80.00</p>,
        <p className="p-reset center-align">4</p>,
        <p className="blue-text bold-text p-reset center-align">$120.00</p>,
        <button className="table-button">View</button>
        ],
        [<div className="image"></div>,
        <p className="bold-text p-reset">Product 1</p>,
        <p className="p-reset">12h</p>,
        <p className="p-reset center-align">$80.00</p>,
        <p className="p-reset center-align">4</p>,
        <p className="blue-text bold-text p-reset center-align">$120.00</p>,
        <button className="table-button">View</button>
        ]
    ]
}

function Bids() {

    return (
        <CustomTable headings={dummyData.headings} data={dummyData.data} />
    );
}

export default Bids;
