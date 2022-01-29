package hr.java.production.model;

import hr.java.production.exception.IllegalMeasuringUnitArgumentException;

/**
 * Record holding name of the measurement unit.
 */
public record MeasuringUnit(String name) {
    /**
     * Constructor preventing blank unit name.
     *
     * @param name name of the measurement unit.
     */
    public MeasuringUnit {
        if (name.isBlank())
            throw new IllegalMeasuringUnitArgumentException("Measuring unit can't be blank");
    }
}
