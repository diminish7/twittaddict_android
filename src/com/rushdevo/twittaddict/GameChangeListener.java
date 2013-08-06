package com.rushdevo.twittaddict;

/**
 * Interface for those things that need to be notified
 * when the game changes (e.g. Score changes, game finishes) 
 */
public interface GameChangeListener {
	public abstract void onGameChange(Twittaddict game);
}
