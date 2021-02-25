package ba.abh.AuctionApp.requests;

public class ProductImageRequest {
    private String imageUrl;

    public ProductImageRequest() {
    }

    public ProductImageRequest(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ProductImageRequest setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
