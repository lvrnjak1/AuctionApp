import React from 'react';
import Transformation from 'cloudinary-react/lib/components/Transformation';
import Image from 'cloudinary-react/lib/components/Image';
import { getPublicId } from 'util/images_util';

function CustomImage(props) {
    return (
        props.url ? <Image
            className={props.styles}
            cloudName={process.env.REACT_APP_CLOUDNAME}
            publicId={getPublicId(props.url)} >
            <Transformation
                height={props.height}
                width={props.width} crop={props.crop || "scale"}
                quality="auto"
                flags="lossy" />
        </Image> : <img
                src={process.env.PUBLIC_URL +
                    `${!props.avatar ? '/images/image-placeholder.png' : "/images/profile-pic.png"}`}
                className={props.styles}
                alt={props.alt || ""}
            />
    );
}

export default CustomImage;
