package com.dumptruckman.minecraft.gmtools.test.utils;

import com.dumptruckman.minecraft.pluginbase.locale.Message;

public class MockMessages {
    public final static Message TEST_MESSAGE = new Message("test.message", "This is a &ctest message! (%1)",
            "And an additional line.");
    
    public static void init() { }
}
