package com.dumptruckman.minecraft.matchmaker.api;

import com.dumptruckman.minecraft.matchmaker.api.config.TeamConfig;
import com.dumptruckman.minecraft.matchmaker.api.config.TeamRecord;

import java.util.List;
import java.util.Set;

/**
 * Represents a team of players for pvp matches.
 */
public interface Team {

    /**
     * @return The name of the team.
     */
    String getName();
    
    void setName(String name);

    /**
     * @return A list of the team's members.
     */
    List<TeamMember> getMembers();
    
    Set<TeamMember> getCaptains();
    
    Set<TeamMember> getManagers();
    
    TeamConfig config();
    
    TeamRecord record();
}
