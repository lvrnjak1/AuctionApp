import React from 'react';
import "static_pages/about/about.scss";

function About() {
    return (
        <div className="about-page">
            <p className="about-title">About Us</p>
            <div className="content">
                <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Aliquam nulla facilisi cras fermentum odio eu. Cras semper auctor neque vitae tempus. Gravida cum sociis natoque penatibus et magnis dis. Enim facilisis gravida neque convallis a. Fusce id velit ut tortor pretium viverra suspendisse potenti nullam. Aliquet enim tortor at auctor urna nunc id cursus metus. Urna molestie at elementum eu facilisis. Fringilla ut morbi tincidunt augue interdum velit euismod in. Morbi tincidunt ornare massa eget. Amet venenatis urna cursus eget nunc scelerisque. Vel risus commodo viverra maecenas accumsan lacus vel facilisis. Potenti nullam ac tortor vitae purus faucibus ornare. Sed nisi lacus sed viverra tellus. Amet venenatis urna cursus eget nunc scelerisque. Arcu vitae elementum curabitur vitae. In hendrerit gravida rutrum quisque non tellus orci ac.</p>
                <p>Interdum posuere lorem ipsum dolor sit amet consectetur. Integer quis auctor elit sed vulputate mi sit amet. Vestibulum rhoncus est pellentesque elit ullamcorper dignissim cras tincidunt. Mi bibendum neque egestas congue quisque egestas diam in arcu. Tristique senectus et netus et malesuada fames. Viverra adipiscing at in tellus integer feugiat scelerisque varius morbi. Nec ullamcorper sit amet risus nullam eget felis eget. Nisl suscipit adipiscing bibendum est ultricies integer. Condimentum mattis pellentesque id nibh tortor. Nunc aliquet bibendum enim facilisis gravida neque convallis a cras. Fames ac turpis egestas sed tempus urna et. Adipiscing diam donec adipiscing tristique risus nec feugiat. Nullam non nisi est sit amet facilisis magna etiam tempor. Nullam non nisi est sit amet facilisis magna. Vulputate eu scelerisque felis imperdiet proin. In hendrerit gravida rutrum quisque non tellus orci ac auctor. Natoque penatibus et magnis dis parturient. Quis viverra nibh cras pulvinar mattis nunc sed blandit. Laoreet suspendisse interdum consectetur libero id. Pharetra convallis posuere morbi leo. Neque egestas congue quisque egestas diam. Sed pulvinar proin gravida hendrerit lectus. Porta nibh venenatis cras sed felis eget. Tellus in metus vulputate eu scelerisque felis imperdiet proin fermentum. </p>
            </div>
            <div className="images">
                <img src={process.env.PUBLIC_URL + '/images/img1.jpg'} alt="first" className="image-large" />
                <img src={process.env.PUBLIC_URL + '/images/img2.jpg'} alt="second" className="image-small" />
                <img src={process.env.PUBLIC_URL + '/images/img3.jpg'} alt="third" className="image-small" />
            </div>
        </div >

    );
}

export default About;