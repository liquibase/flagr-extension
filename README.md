# Flagr Feature Flags Extension
[Precondition](https://docs.liquibase.com/concepts/changelogs/preconditions.html) to control the execution of a changelog or changeset based on the state of the feature flag in Flagr.

## Supported Editions
[![Community](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/mcred/liquibase-header-footer/feature/badges/badges/community.json)](https://liquibase.org/)
[![Pro](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/mcred/liquibase-header-footer/feature/badges/badges/pro.json)](https://www.liquibase.com/pricing/pro)

## Installation
The easiest way to install this extension is with `lpm` [liquibase package manager](https://github.com/liquibase/liquibase-package-manager).
```shell
lpm update
lpm add flagr
```

## Setup
URL is required for the extension to locate the Flagr API.
```
--flagr-url=PARAM
     URL for Flagr API
     (liquibase.flagr.url)
     (LIQUIBASE_FLAGR_URL)
     [deprecated: --flagrUrl]
```

## Usage
To use this extension, add the `flagrFeatureFlag` precondition to your Changelog or Changeset with an `enabledFlags` attribute. The value for `enabledFlags` is either a string with one feature flag key or a comma separated string with multiple feature flag keys. All feature flags must be enabled for the precondition to pass.   

## Example
```yaml
databaseChangeLog:
  -  preConditions:
     -  flagrFeatureFlag:
          enabledFlags: changelog-testing
```
```xml
<changeSet id="1" author="example">
    <preConditions>
        <ext:flagrFeatureFlag enabledFlags="changelog-testing"/>
    </preConditions>
    ...
</changeSet>
```

## Feedback and Issues
Please submit all feedback and issues to [this idea board](https://ideas.liquibase.com/c/69-flagr-feature-flag-extensions).