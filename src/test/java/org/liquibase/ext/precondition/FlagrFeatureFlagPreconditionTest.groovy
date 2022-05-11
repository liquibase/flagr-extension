package org.liquibase.ext.precondition

import liquibase.changelog.ChangeSet
import liquibase.exception.PreconditionErrorException
import liquibase.exception.PreconditionFailedException
import liquibase.parser.ChangeLogParserFactory

class FlagrFeatureFlagPreconditionTest extends AbstractFeatureFlagPreconditionTest {

    def dbChangeLog = ChangeLogParserFactory.getInstance().getParser("xml", clra)
            .parse("changelogs/flagr.xml", null, clra)
    def changeSet = new ChangeSet(dbChangeLog)
    def flagrFeatureFlagPrecondition = dbChangeLog.getChangeSets()[0].getPreconditions().getNestedPreconditions()[0]

    def "GetName"() {
        expect:
        flagrFeatureFlagPrecondition.getName() == "flagrFeatureFlag"
    }

    def "Flagr feature flag is not available" () {
        when:
        flagrFeatureFlagPrecondition.flagr = Mock(Flagr)
        flagrFeatureFlagPrecondition.flagr.Enabled(*_) >> { throw new IOException("Unable to connect to Flagr API.") }
        flagrFeatureFlagPrecondition.check(database, dbChangeLog, changeSet, changeExecListener)

        then:
        thrown(PreconditionErrorException)
    }

    def "Flagr feature flag is not found" () {
        when:
        flagrFeatureFlagPrecondition.flagr = Mock(Flagr)
        flagrFeatureFlagPrecondition.flagr.Enabled(*_) >> { false }
        flagrFeatureFlagPrecondition.check(database, dbChangeLog, changeSet, changeExecListener)

        then:
        thrown(PreconditionFailedException)

    }

    def "Flagr feature flag is not enabled" () {
        when:
        flagrFeatureFlagPrecondition.flagr = Mock(Flagr)
        flagrFeatureFlagPrecondition.flagr.Enabled(*_) >> { false }
        flagrFeatureFlagPrecondition.check(database, dbChangeLog, changeSet, changeExecListener)

        then:
        thrown(PreconditionFailedException)

    }

    def "Flagr feature flag is enabled"() {
        given:
        flagrFeatureFlagPrecondition.flagr = Mock(Flagr)
        flagrFeatureFlagPrecondition.flagr.Enabled(*_) >> { true }

        expect:
        flagrFeatureFlagPrecondition.check(database, dbChangeLog, changeSet, changeExecListener)
    }
}
