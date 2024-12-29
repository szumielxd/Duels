package me.realized.duels.api.match;

import java.util.Set;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface MatchTeam {
	
	/**
     * UnmodifiableSet of alive players in this {@link Match}.
     *
     * @return Never-null UnmodifiableSet of alive players in this {@link Match}.
     * @since 4.0.0
     */
    @NotNull
    Set<Player> getAliveMembers();
    
    /**
     * UnmodifiableSet of players who started this {@link Match}.
     * Note: This set includes players who are offline. If you keep a reference
     * to this match, all the player objects of those who started this match will
     * not be garbage-collected.
     *
     * @return Never-null UnmodifiableSet of players who started this {@link Match}.
     * @since 4.0.0
     */
    @NotNull
    Set<Player> getAllMembers();
    
    boolean isDead();

}
