package Project.DTO;

import java.time.LocalDate;

public class ReportDTO {
    Integer id;
    LocalDate startDate;
    LocalDate endDate;
    String category;

    public ReportDTO(Integer id, LocalDate startDate, LocalDate endDate, String category) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
