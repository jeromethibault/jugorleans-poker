package fr.jugorleans.poker.server.spec;

import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Hand;

/**
 * Specification permettant d'évaluer si la main et le board constituent une flush
 */
public class FlushSpecification implements Specification<Hand> {

	/** Le board */
	private final Board board;

	/**
	 * Construire un {@link FlushSpecification} sur un board donné
	 *
	 * @param board le board
	 */
	public FlushSpecification(final Board board) {
		this.board = board;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSatisfiedBy(final Hand hand) {
		// TODO
		return false;
	}
}
