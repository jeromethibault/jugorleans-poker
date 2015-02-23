package fr.jugorleans.poker.server.spec;

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
}
