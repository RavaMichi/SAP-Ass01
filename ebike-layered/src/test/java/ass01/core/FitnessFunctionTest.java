package ass01.core;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

import org.junit.jupiter.api.Test;
public class FitnessFunctionTest {

    private final JavaClasses importedClasses = new ClassFileImporter().importPackages("ass01.core");
    @Test
    public void presentationLayerRule() {
        ArchRule rule =
                noClasses().that().resideInAPackage("..presentation..")
                        .should().dependOnClassesThat().resideInAPackage("..persistence..");

        rule.check(importedClasses);
    }
    @Test
    public void businessLayerRule() {
        ArchRule rule =
                noClasses().that().resideInAPackage("..business..")
                        .should().dependOnClassesThat().resideInAPackage("..presentation..");

        rule.check(importedClasses);
    }
    @Test
    public void persistenceLayerRule() {
        ArchRule rule =
                noClasses().that().resideInAPackage("..persistence..")
                        .should().dependOnClassesThat().resideInAnyPackage("..business..","..presentation..");

        rule.check(importedClasses);
    }
}
