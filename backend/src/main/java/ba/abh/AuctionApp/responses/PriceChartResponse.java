package ba.abh.AuctionApp.responses;

import java.util.ArrayList;
import java.util.List;

public class PriceChartResponse {
    List<String> labels;
    List<Long> data;
    Double min;
    Double max;
    Double step;

    public PriceChartResponse() {
        this.labels = new ArrayList<>();
        data = new ArrayList<>();
    }

    public PriceChartResponse(final List<String> labels,
                              final List<Long> data,
                              final Double min,
                              final Double max,
                              final Double step) {
        this.labels = labels;
        this.data = data;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(final List<String> labels) {
        this.labels = labels;
    }

    public List<Long> getData() {
        return data;
    }

    public void setData(final List<Long> data) {
        this.data = data;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(final Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(final Double max) {
        this.max = max;
    }

    public Double getStep() {
        return step;
    }

    public void setStep(final Double step) {
        this.step = step;
    }
}
