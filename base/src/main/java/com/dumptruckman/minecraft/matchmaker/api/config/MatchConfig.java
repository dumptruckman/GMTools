package com.dumptruckman.minecraft.matchmaker.api.config;

import com.dumptruckman.minecraft.pluginbase.config.ConfigEntry;
import com.dumptruckman.minecraft.pluginbase.config.EntryBuilder;

/**
 * Contains the configuration for a single match.
 */
public interface MatchConfig extends Config {

    ConfigEntry<Integer> MAX_ROUNDS = new EntryBuilder<Integer>(Integer.class, "rounds.max").def(3).build();

    /**
     * Time limit for each round in seconds. 0 means no limit
     */
    ConfigEntry<Integer> TIME_LIMIT = new EntryBuilder<Integer>(Integer.class, "rounds.time_limit").def(0).build();
    
    ConfigEntry<Integer> MAX_TEAMS = new EntryBuilder<Integer>(Integer.class, "team.max").def(2).build();

    ConfigEntry<Integer> TEAM_SIZE = new EntryBuilder<Integer>(Integer.class, "team.size").def(4).build();
    
    ConfigEntry<Integer> MAX_LIVES = new EntryBuilder<Integer>(Integer.class, "lives.max").def(3).build();

    ConfigEntry<Boolean> TEAM_LIVES = new EntryBuilder<Boolean>(Boolean.class, "lives.team").def(false).build();
    

}
