package gr.knowledge.internship.vacation.enums;

public enum VacationRequestStatus {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    public final String description;

    VacationRequestStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
