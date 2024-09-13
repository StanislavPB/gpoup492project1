package Project.DTO;

import java.time.LocalDate;

public class ReportDTO <T> {
    T body;
    LocalDate startDate;
    LocalDate endDate;
    String category;

    public ReportDTO(T body, LocalDate startDate, LocalDate endDate, String category) {
        this.body = body;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
    }

    public T getBody() {
        return body;
    }

    public LocalDate getStartDate() {
        return startDate;
    }


    public LocalDate getEndDate() {
        return endDate;
    }


    public String getCategory() {
        return category;
    }

}
