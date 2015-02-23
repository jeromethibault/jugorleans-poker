package fr.jugorleans.poker.server.spec;

import fr.jugorleans.poker.server.core.Board;
import fr.jugorleans.poker.server.core.Hand;

/**
 * Specification permettant d'évaluer si la main et le board constituent une paire
 */
public class PairSpecification implements Specification<Hand> {

	/** Le board */
	private final Board board;

	/**
	 * Construire un {@link PairSpecification} sur un board donné
	 *
	 * @param board le board
	 */
	public PairSpecification(final Board board) {
		this.board = board;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSatisfiedBy(final Hand hand) {
		final int nbUniqueCardOnBoard = this.board.nbUniqueValueCard();
		final int nbFirstCard = this.board.nb(hand.getFirstCard().getCardValue());
		final int nbSecondCard = this.board.nb(hand.getSecondCard().getCardValue());
		return ((nbFirstCard == 1) && (nbSecondCard == 0)) || ((nbFirstCard == 0) && (nbSecondCard == 1))
				|| (nbUniqueCardOnBoard == 4) || (nbUniqueCardOnBoard == 2);
	}
}