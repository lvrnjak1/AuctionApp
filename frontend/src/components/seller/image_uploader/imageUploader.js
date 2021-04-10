import React from 'react';
import ImageUploading from 'react-images-uploading';
import "components/seller/image_uploader/imageUploader.scss";

function ImageUploader(props) {
    const [images, setImages] = React.useState([]);
    const maxNumber = 10;

    const onChange = (imageList, addUpdateIndex) => {
        console.log(imageList, addUpdateIndex);
        setImages(imageList);
        // props.handleUpload()
    };

    return (
        <div>
            <ImageUploading
                multiple
                value={images}
                onChange={onChange}
                maxNumber={maxNumber}
                dataURLKey="data_url"
            >
                {({
                    imageList,
                    onImageUpload,
                    onImageRemoveAll,
                    onImageUpdate,
                    onImageRemove,
                    isDragging,
                    dragProps,
                }) => (
                    <>
                        <div {...dragProps} className="upload__image-wrapper upload-wrapper">
                            <button
                                className="upload-button"
                                onClick={onImageUpload}
                                {...dragProps}
                            >Upload Photos <span>or just drag and drop</span></button>
                            <p className="upload-text">+ Add at least 3 photos</p>
                        </div>
                        {images.length > 0 && <div className="uploaded-photos">
                            {images.map((image, index) => {
                                return <div key={index} className="uploaded-photo">
                                    <p>{image.file.name}</p>
                                    <button onClick={() => onImageRemove(index)}>Remove</button>
                                </div>
                            })}
                        </div>}
                    </>
                )}
            </ImageUploading>
        </div>
    );
}

export default ImageUploader;


 // write your building UI

            //         < className="upload__image-wrapper">
            //             <button
            //                 style={isDragging ? { color: 'red' } : undefined}
            //                 onClick={onImageUpload}
            //                 {...dragProps}
            //             >
            //                 Click or Drop here
            // </button>
            // &nbsp;
            //             <button onClick={onImageRemoveAll}>Remove all images</button>
            //             {imageList.map((image, index) => (
            //                 <div key={index} className="image-item">
            //                     <img src={image['data_url']} alt="" width="100" />
            //                     <div className="image-item__btn-wrapper">
            //                         <button onClick={() => onImageUpdate(index)}>Update</button>
            //                         <button onClick={() => onImageRemove(index)}>Remove</button>
            //                     </div>
            //                 </div>
            //             ))}
            //         </>