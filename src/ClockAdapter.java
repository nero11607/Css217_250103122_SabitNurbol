import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

public class ClockAdapter implements IModernCalendar {
    private final LegacyClock legacyClock;

    public ClockAdapter(LegacyClock legacyClock) {
        this.legacyClock = legacyClock;
    }

    @Override
    public LocalDate getCurrenDate(){
        long epochSeconds = legacyClock.getEpochSeconds();
        return Instant.ofEpochSecond(epochSeconds).atZone(ZoneOffset.UTC).toLocalDate();
    }
}