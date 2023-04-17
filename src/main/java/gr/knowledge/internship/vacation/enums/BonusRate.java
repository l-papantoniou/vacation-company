package gr.knowledge.internship.vacation.enums;

public enum BonusRate {
    WINTER("Winter", 1.3),
    AUTUMN("Autumn", 0.4),
    SPRING("Spring", 0.6),
    SUMMER("Summer", 0.7);

    private final String season;
    private final Double rate;

    BonusRate(String season, Double rate) {
        this.season = season;
        this.rate = rate;
    }

    public String getSeason() {
        return season;
    }

    public Double getRate() {
        return rate;
    }

    public static BonusRate getRateForSeason(String season) {
        for (BonusRate bonusRate : values()) {
            if (bonusRate.getSeason().equalsIgnoreCase(season)) {
                return bonusRate;
            }
        }
        throw new IllegalArgumentException("Invalid season: " + season);
    }
}
