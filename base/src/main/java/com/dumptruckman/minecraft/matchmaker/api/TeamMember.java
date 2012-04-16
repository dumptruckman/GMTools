package com.dumptruckman.minecraft.matchmaker.api;

/**
 * Represents a Minecraft Player that can be a part of a {@link Team}.
 */
public interface TeamMember {

    /**
     * @return The name of the TeamMember which should correspond to the Minecraft Player's username.
     */
    String getName();
}
