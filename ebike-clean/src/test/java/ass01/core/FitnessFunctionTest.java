package ass01.core;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

import org.junit.jupiter.api.Test;
public class FitnessFunctionTest {

    private final JavaClasses importedClasses = new ClassFileImporter().importPackages("ass01.core");
    @Test
    public void businessDependencyRule() {
        ArchRule rule =
                noClasses().that().resideInAPackage("..business..")
                        .should().dependOnClassesThat().resideInAPackage("..adapters..");

        rule.check(importedClasses);
    }
    @Test
    public void portsRule() {
        ArchRule rule =
                classes().that().resideInAPackage("..business.ports..")
                        .should().beInterfaces().orShould().beRecords();

        rule.check(importedClasses);
    }
}