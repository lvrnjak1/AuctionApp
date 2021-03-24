package ba.abh.AuctionApp.responses;

import java.util.ArrayList;
import java.util.List;

public class PriceChartResponse {
    List<Double> labels;
    List<Double> data;

    public PriceChartResponse() {
        labels = new ArrayList<>();
        data = new ArrayList<>();
    }

    public PriceChartResponse(final List<Double> labels, final List<Double> data) {
        this.labels = labels;
        this.data = data;
    }

    public List<Double> getLabels() {
        return labels;
    }

    public void setLabels(final List<Double> labels) {
        this.labels = labels;
    }

    public List<Double> getData() {
        return data;
    }

    public void setData(final List<Double> data) {
        this.data = data;
    }
}
