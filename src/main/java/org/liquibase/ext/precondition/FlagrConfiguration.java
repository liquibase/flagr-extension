package org.liquibase.ext.precondition;

import liquibase.configuration.AutoloadedConfigurations;
import liquibase.configuration.ConfigurationDefinition;

public class FlagrConfiguration implements AutoloadedConfigurations {

    public static ConfigurationDefinition<String> FLAGR_URL;

    static {
        ConfigurationDefinition.Builder builder = new ConfigurationDefinition.Builder("liquibase.flagr");
        FLAGR_URL = builder.define("url", String.class)
                .addAliasKey("flagr.url")
                .setDescription("URL for Flagr API")
                .build();
    }
}
