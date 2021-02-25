package ba.abh.AuctionApp.requests;

public class ProductImageRequest {
    private String imageUrl;

    public ProductImageRequest() {
    }

    public ProductImageRequest(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
