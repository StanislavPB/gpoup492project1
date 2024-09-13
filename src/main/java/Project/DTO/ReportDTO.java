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
