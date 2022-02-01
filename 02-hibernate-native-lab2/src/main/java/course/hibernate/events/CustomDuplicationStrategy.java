package course.hibernate.events;

import org.hibernate.event.service.spi.DuplicationStrategy;

public class CustomDuplicationStrategy implements DuplicationStrategy {
    @Override
    public boolean areMatch(Object listener, Object original) {
        return false;
    }

    @Override
    public Action getAction() {
        return Action.REPLACE_ORIGINAL;
    }
}
