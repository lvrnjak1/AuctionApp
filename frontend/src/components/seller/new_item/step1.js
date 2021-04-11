import React, { useState } from 'react';
import ImageUploader from "components/seller/image_uploader/imageUploader";
import { useSelector } from 'react-redux';
import { UPLOAD_IMAGE_ENDPOINT } from 'http/endpoints';
import { updateMessage } from 'util/info_div_util';
import { uploadFormData } from 'http/requests';


function StepOne(props) {
    const categories = useSelector(state => state.categories);
    const [selectedCategoryIndex, setSelectedCategoryIndex] = useState(0);
    const [selectedSubcategoryIndex, setSelectedSubcategoryIndex] = useState(0);

    const findIndex = (collection, id) => {
        return collection.findIndex(cat => parseInt(cat.id) === parseInt(id));
    }

    return (
        <form className="form" onSubmit={props.handleNext}>
            <p className="form-title">INFORMATION ABOUT PRODUCT</p>
            <div className="form-content">
                <div className="input-label-group">
                    <label>What do you sell?</label>
                    <input
                        className="input"
                        required
                        type="text"
                        value={props.productName}
                        onChange={e => props.setProductName(e.target.value)} />
                </div>
                {categories.length > 0 && <div className="flex-form-group">
                    <div className="input-label-group">
                        <select
                            required
                            className="input"
                            value={categories[selectedCategoryIndex].id}
                            onChange={e => {
                                props.setCategory(e.target.value);
                                const index = findIndex(categories, e.target.value);
                                setSelectedCategoryIndex(index);
                                setSelectedSubcategoryIndex(0);
                            }}>
                            {categories.map(cat => {
                                return <option key={cat.id} value={cat.id}>{cat.name}</option>
                            })}
                        </select>
                    </div>
                    <div className="input-label-group">
                        <select
                            required
                            className="input"
                            value={categories[selectedCategoryIndex].subcategories[selectedSubcategoryIndex].id}
                            onChange={e => {
                                props.setSubcategory(e.target.value);
                                const index = findIndex(categories[selectedCategoryIndex].subcategories, e.target.value);
                                setSelectedSubcategoryIndex(index);
                            }}>
                            {/* {categories[selectedCategoryIndex].subcategories.length == 0 ?
                                <option value="" disabled>Subcategory</option> : ""} */}
                            {categories[selectedCategoryIndex].subcategories.map(sc => {
                                return <option key={sc.id} value={sc.id}>{sc.name}</option>
                            })}
                        </select>
                    </div>
                </div>}
                <div className="input-label-group">
                    <label>Description</label>
                    <textarea
                        maxLength="700"
                        className="textarea"
                        value={props.productDescription}
                        onChange={e => props.setProductDescription(e.target.value)} />
                    <p className="textarea-info">100 words (700 characters)</p>
                </div>
                <div className="input-label-group image-upload-container">
                    <ImageUploader images={props.images} setImages={props.setImages} />
                </div>
            </div>
            <button type="submit" className="btn">
                {props.activeStep < props.maxSteps - 1 ? "Next" : "Submit"}
            </button>
            <button
                className="btn btn-back"
                onClick={props.handleBack}
                disabled={props.activeStep === 0}>
                Back
            </button>
        </form>
    );
}

export default StepOne;


