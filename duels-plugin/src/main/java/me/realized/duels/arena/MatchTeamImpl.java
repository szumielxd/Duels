package me.realized.duels.arena;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import lombok.Getter;
import me.realized.duels.api.match.MatchTeam;

public class MatchTeamImpl implements MatchTeam {
	
	// Default value for players is false, which is set to true if player is killed in the match.
	@NotNull
	private final Map<Player, Boolean> players = new HashMap<>();
	@Getter
	private boolean dead;
	
	MatchTeamImpl(@NotNull Set<Player> players) {
		players.forEach(p -> this.players.put(p, false));
	}
    
	public void markMemberDead(@NotNull Player player) {
		this.players.computeIfPresent(player, (k, v) -> false);
		if (this.getAliveMembers().isEmpty()) {
			this.dead = true;
		}
	}
	
	public boolean isMemberDead(@NotNull Player player) {
		return this.players.getOrDefault(player, true);
	}

	@Override
	public @NotNull Set<Player> getAliveMembers() {
		return this.players.entrySet().stream()
				.filter(e -> !e.getValue())
				.map(Entry::getKey)
				.collect(Collectors.toUnmodifiableSet());
	}

	@Override
	public @NotNull Set<Player> getAllMembers() {
		return Collections.unmodifiableSet(this.players.keySet());
	}

}
