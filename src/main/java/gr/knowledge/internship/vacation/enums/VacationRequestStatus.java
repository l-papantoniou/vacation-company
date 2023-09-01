package gr.knowledge.internship.vacation.enums;

import java.util.Optional;

public enum VacationRequestStatus {
    PENDING("pending"),
    APPROVED("approved"),
    ACCEPTED("accepted"),
    REJECTED("rejected");

    private static final VacationRequestStatus[] VALUES;

    static {
        VALUES = values();
    }

    private final String description;

    VacationRequestStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Optional<VacationRequestStatus> getEnumFromDescription(String description) {
        if (description == null) {
            return Optional.empty();
        }
        // Use cached VALUES instead of values() to prevent array allocation.
        for (VacationRequestStatus status : VALUES) {
            if (status.getDescription().equalsIgnoreCase(description)) {
                return Optional.of(status);
            }
        }
        return Optional.empty();
    }
}
