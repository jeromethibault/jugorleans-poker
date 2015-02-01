package fr.jugorleans.poker.server.spec;

/**
 * Abstract base implementation of composite {@link Specification} with default implementations for {@code and},
 * {@code or} and {@code not}
 */
public abstract class AbstractSpecification<T> implements Specification<T> {

    /**
     * {@inheritDoc}
     */
    public abstract boolean isSatisfiedBy(T t);

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
