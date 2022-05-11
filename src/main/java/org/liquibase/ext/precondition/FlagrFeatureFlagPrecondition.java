package org.liquibase.ext.precondition;

import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.database.Database;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;

import java.io.IOException;

public class FlagrFeatureFlagPrecondition extends AbstractFeatureFlagPrecondition {

    public static Flagr flagr;

    static {
        flagr = new Flagr(
                FlagrConfiguration.FLAGR_URL.getCurrentValue()
        );
    }

    @Override
    public String getName() {
        return "flagrFeatureFlag";
    }

    @Override
    public void check(Database database, DatabaseChangeLog databaseChangeLog, ChangeSet changeSet, ChangeExecListener changeExecListener) throws PreconditionFailedException, PreconditionErrorException {
        try {
            for (String flag: this.getEnabledFlags()) {
                if (!flagr.Enabled(flag.replaceAll("\\s", ""))) {
                    throw new PreconditionFailedException(this.getName() + "->" + flag + " is not enabled.", databaseChangeLog, this);
                }
            }
        } catch (IOException e) {
            throw new PreconditionErrorException(e, databaseChangeLog, this);
        }
    }
}
