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

	/** {@inheritDoc} */
	@Override
	default boolean test(final T t) {
		return this.isSatisfiedBy(t);
	}
}
