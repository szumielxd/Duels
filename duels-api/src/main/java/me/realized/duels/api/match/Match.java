package me.realized.duels.api.match;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import me.realized.duels.api.arena.Arena;
import me.realized.duels.api.kit.Kit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an ongoing Match.
 */
public interface Match {


    /**
     * The {@link Arena} this {@link Match} is taking place in.
     *
     * @return {@link Arena} this {@link Match} is taking place in.
     */
    @NotNull
    Arena getArena();


    /**
     * The start of this match {@link Match} milliseconds.
     * Note: {@link System#currentTimeMillis()} subtracted by the result of this method will give the duration of the current {@link Match} in milliseconds.
     *
     * @return start of this match in milliseconds.
     */
    long getStart();


    /**
     * The {@link Kit} used in this {@link Match}.
     *
     * @return {@link Kit} used in this {@link Match} or null if players are using their own inventories.
     */
    @Nullable
    Kit getKit();


    /**
     * UnmodifiableList of ItemStacks the player has bet for this {@link Match}.
     *
     * @param player {@link Player} to get the list of bet items.
     * @return Never-null UnmodifiableList of ItemStacks the player has bet for this {@link Match}.
     */
    @NotNull
    List<ItemStack> getItems(@NotNull final Player player);


    /**
     * The bet amount for this {@link Match}.
     *
     * @return bet Bet amount for this {@link Match} or 0 if no bet was specified.
     */
    int getBet();


    /**
     * Whether or not this {@link Match} is finished.
     *
     * @return true if this {@link Match} has finished or false otherwise.
     * @since 3.4.1
     */
    boolean isFinished();
    
    /**
     * UnmodifiableSet of alive players in this {@link Match}.
     *
     * @return Never-null UnmodifiableSet of alive players in this {@link Match}.
     * @since 3.1.0
     */
    @NotNull
    @Deprecated
    default Set<Player> getPlayers() {
    	return getAlivePlayers();
    }


    /**
     * UnmodifiableSet of alive players in this {@link Match}.
     *
     * @return Never-null UnmodifiableSet of alive players in this {@link Match}.
     * @since 4.0.0
     */
    @NotNull
    default Set<Player> getAlivePlayers() {
    	return getTeams().stream()
    			.map(MatchTeam::getAliveMembers)
    			.flatMap(Collection::stream)
    			.collect(Collectors.toUnmodifiableSet());
    }


    /**
     * UnmodifiableSet of players who started this {@link Match}.
     *
     * @return Never-null UnmodifiableSet of players who started this {@link Match}.
     * @since 3.4.1
     */
    @NotNull
    @Deprecated
    default Set<Player> getStartingPlayers() {
    	return getAllPlayers();
    }
    
    /**
     * UnmodifiableSet of players who started this {@link Match}.
     *
     * @return Never-null UnmodifiableSet of players who started this {@link Match}.
     * @since 4.0.0
     */
    @NotNull
    default Set<Player> getAllPlayers() {
    	return getTeams().stream()
    			.map(MatchTeam::getAllMembers)
    			.flatMap(Collection::stream)
    			.collect(Collectors.toUnmodifiableSet());
    }
    
    
    Set<MatchTeam> getTeams();
    
    
}
