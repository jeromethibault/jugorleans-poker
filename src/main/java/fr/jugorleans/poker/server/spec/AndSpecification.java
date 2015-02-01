package fr.jugorleans.poker.server.spec;

/**
 * AND specification, used to create a new specifcation that is the AND of two other specifications.
 */
public class AndSpecification extends AbstractSpecification {

    private Specification spec1;
    private Specification spec2;

    /**
     * Create a new AND specification based on two other spec.
     *
     * @param spec1 Specification one.
     * @param spec2 Specification two.
     */
    public AndSpecification(final Specification spec1, final Specification spec2) {
        this.spec1 = spec1;
        this.spec2 = spec2;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isSatisfiedBy(final Object o) {
        return spec1.isSatisfiedBy(o) && spec2.isSatisfiedBy(o);
    }
}
