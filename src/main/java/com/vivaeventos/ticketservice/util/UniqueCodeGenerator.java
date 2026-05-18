package com.vivaeventos.ticketservice.util;

import java.util.UUID;

public class UniqueCodeGenerator {

    private UniqueCodeGenerator() {}

    public static String generate() {
        return UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }
}