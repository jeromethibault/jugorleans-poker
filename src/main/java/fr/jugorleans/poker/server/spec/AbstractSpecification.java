package fr.jugorleans.poker.server.spec;

/**
 * Abstract base implementation of composite {@link Specification} with default implementations for {@code and},
 * {@code or} and {@code not}
 */
public abstract class AbstractSpecification implements Specification {

    /**
     * {@inheritDoc}
     */
    public abstract boolean isSatisfiedBy(Object o);

    /**
     * {@inheritDoc}
     */
    public Specification and(final Specification specification) {
        return new AndSpecification(this, specification);
    }

    /**
     * {@inheritDoc}
     */
    public Specification or(final Specification specification) {
        return new OrSpecification(this, specification);
    }

    /**
     * {@inheritDoc}
     */
    public Specification not(final Specification specification) {
        return new NotSpecification(specification);
    }
}
