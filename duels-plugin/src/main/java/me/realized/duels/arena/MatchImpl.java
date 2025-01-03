package me.realized.duels.arena;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;
import me.realized.duels.api.match.Match;
import me.realized.duels.api.match.MatchTeam;
import me.realized.duels.kit.KitImpl;
import me.realized.duels.queue.Queue;

public class MatchImpl implements Match {

    @Getter
    private final ArenaImpl arena;
    @Getter
    private final long start;
    @Getter
    private final KitImpl kit;
    private final Map<UUID, List<ItemStack>> items;
    @Getter
    private final int bet;
    @Getter
    @Nullable
    private final Queue source;

    @Getter
    private boolean finished;

    @NotNull
    private final Map<UUID, MatchTeamImpl> teamsByPlayer;
    @Getter
    @NotNull
    private final Set<MatchTeam> teams;

    MatchImpl(final @NotNull ArenaImpl arena, final @Nullable KitImpl kit, final @NotNull Map<UUID, List<ItemStack>> items, final @NotNull List<Set<Player>> teams, final int bet, final @Nullable Queue source) {
        this.arena = arena;
        this.start = System.currentTimeMillis();
        this.kit = kit;
        this.items = items;
        this.bet = bet;
        this.source = source;
        if (teams.size() < 2) {
        	throw new IllegalArgumentException("Match cannot have less than 2 teams");
        }
        this.teams = teams.stream()
        		.map(MatchTeamImpl::new)
        		.collect(Collectors.toUnmodifiableSet());
        this.teamsByPlayer = this.teams.stream()
        		.flatMap(t -> t.getAllMembers().stream().map(p -> new SimpleEntry<>(p.getUniqueId(), (MatchTeamImpl) t)))
        		.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    /*Map<Player, Boolean> getPlayerMap() {
        return players;
    }*/

    /*Set<Player> getAlivePlayers() {
        return players.entrySet().stream()
        		.filter(entry -> !entry.getValue())
        		.map(Entry::getKey)
        		.collect(Collectors.toSet());
    }*/

    /*public Set<Player> getAllPlayers() {
        return players.keySet();
    }*/

    public boolean isDead(final @NotNull Player player) {
    	return Optional.ofNullable(this.teamsByPlayer.get(player.getUniqueId()))
    			.map(team -> team.isMemberDead(player))
    			.orElse(true);
    }
    
    public boolean isAlive(final @NotNull Player player) {
    	return !isDead(player);
    }

    public boolean isFromQueue() {
        return source != null;
    }

    public boolean isOwnInventory() {
        return kit == null;
    }
    
    @NotNull
    public List<ItemStack> getItems() {
    	return Optional.ofNullable(items).stream()
    			.map(Map::values)
    			.flatMap(Collection::stream)
    			.flatMap(Collection::stream)
    			.toList();
    }

    void setFinished() {
        finished = true;
    }

    public long getDurationInMillis() {
        return System.currentTimeMillis() - start;
    }

    @NotNull
    @Override
    public List<ItemStack> getItems(@NotNull final Player player) {
        Objects.requireNonNull(player, "player");
        return Optional.ofNullable(this.items)
        		.map(i -> i.get(player.getUniqueId()))
        		.orElse(Collections.emptyList());
    }

    /*@NotNull
    @Override
    public Set<Player> getPlayers() {
        return Collections.unmodifiableSet(getAlivePlayers());
    }

    @NotNull
    @Override
    public Set<Player> getStartingPlayers() {
        return Collections.unmodifiableSet(getAllPlayers());
    }*/
}
