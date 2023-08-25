package com.au.app.account.archunit;

import com.au.app.archunit.AbstractArchUnitTests;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;

@AnalyzeClasses(packages = "com.au.app",
        importOptions = {ImportOption.DoNotIncludeTests.class, ImportOption.DoNotIncludeJars.class})
public class ArchUnitTests extends AbstractArchUnitTests {
}
