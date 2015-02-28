package fr.jugorleans.poker.server.spec;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Specification interface.
 * <p>
 * Delegate {@link Predicate#test(Object)} to {@link #isSatisfiedBy(Object)}.
 * </p>
 */
public interface Specification<T> extends Predicate<T> {

    /**
     * Check if {@code t} is satisfied by the {@link Specification}.
     *
     * @param t Object to test.
     * @return {@code true} if {@code t} satisfies the specification.
     */
    boolean isSatisfiedBy(T t);

    /**
     * {@inheritDoc}
     */
    @Override
    default boolean test(final T t) {
        return this.isSatisfiedBy(t);
    }

    /**
     * @see Predicate#and(Predicate)
     */
    default Specification<T> and(final Specification<? super T> other) {
        final Predicate<T> and = Predicate.super.and(other);
        return t -> and.test(t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default Specification<T> negate() {
        final Predicate<T> negate = Predicate.super.negate();
        return t -> negate.test(t);
    }

    /**
     * @see Predicate#or(Predicate)
     */
    default Specification<T> or(final Specification<? super T> other) {
        final Predicate<T> or = Predicate.super.or(other);
        return t -> or.test(t);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical XOR of this predicate and another.
     *
     * @param other a predicate that will be logically-XORed with this predicate
     * @return a composed predicate that represents the short-circuiting logical XOR of this predicate and the
     * {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default Specification<T> xor(final Specification<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> this.test(t) ^ other.test(t);
    }
}
