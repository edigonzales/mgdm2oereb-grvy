package ch.so.agi.mgdm2oereb;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.interlis2.validator.Validator;

import static org.junit.jupiter.api.Assertions.*;

class Mgdm2OerebTest {
    @Test
    public void hello() {
        System.out.println("hallo welt");
    }
    
//    @Test 
//    public void convertWithoutOereblex(@TempDir Path tempDir) throws Mgdm2OerebException {
//        var settings = new Settings();
//        settings.setValue(Mgdm2Oereb.MODEL, "Planungszonen_V1_1");
//        settings.setValue(Mgdm2Oereb.THEME_CODE, "ch.Planungszonen");
//        settings.setValue(Mgdm2Oereb.CATALOG, "ch.sh.OeREBKRMkvs_supplement.xml");
//        settings.setValue(Mgdm2Oereb.VALIDATE, Boolean.toString(true));
//        
//        var tempDirectory = tempDir.toFile();
//        
//        Mgdm2Oereb mgdm2oereb = new Mgdm2Oereb();
//        mgdm2oereb.convert("src/test/data/ch.Planungszonen.sh.mgdm.v1_1.xtf", tempDirectory.getAbsolutePath(), settings);
//        
//        var iliSettings = new ch.ehi.basics.settings.Settings();
//        var outputFileName = Paths.get(tempDirectory.getAbsolutePath(), "OeREBKRMtrsfr_V2_0.xtf").toFile();
//        boolean valid = Validator.runValidation(outputFileName.getAbsolutePath(), iliSettings);
//        assertTrue(valid);
//    }
    
    
    @Test 
    public void convertWithOereblex(@TempDir Path tempDir) throws Mgdm2OerebException {
        var settings = new Settings();
        settings.setValue(Mgdm2Oereb.MODEL, "Planungszonen_V1_1");
        settings.setValue(Mgdm2Oereb.THEME_CODE, "ch.Planungszonen");
        settings.setValue(Mgdm2Oereb.CATALOG, "ch.sh.OeREBKRMkvs_supplement.xml");
        settings.setValue(Mgdm2Oereb.OEREBLEX_HOST, "oereblex.sh.ch");
        settings.setValue(Mgdm2Oereb.OEREBLEX_CANTON, "sh");
        settings.setValue(Mgdm2Oereb.DUMMY_OFFICE_NAME, "DUMMYOFFICE");
        settings.setValue(Mgdm2Oereb.DUMMY_OFFICE_URL, "https://google.ch");
        settings.setValue(Mgdm2Oereb.VALIDATE, Boolean.toString(true));

        var tempDirectory = tempDir.toFile();

        Mgdm2Oereb mgdm2oereb = new Mgdm2Oereb();
        mgdm2oereb.convert("src/test/data/ch.Planungszonen.sh.mgdm_oereblex.v1_1.xtf", "/Users/stefan/tmp/mgdm2oereb/", settings);
        //mgdm2oereb.convert("src/test/data/ch.Planungszonen.sh.mgdm_oereblex.v1_1.xtf", tempDirectory.getAbsolutePath(), settings);
        
        // TODO assertion
    }
    
    
}
