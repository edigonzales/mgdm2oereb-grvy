package ch.so.agi.mgdm2oereb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.stream.StreamSource;

import org.interlis2.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XdmValue;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;

public class Mgdm2Oereb {
    static Logger log = LoggerFactory.getLogger(Mgdm2Oereb.class);
    
    private static final String PYTHON = "python";

    private static final String SOURCE_FILE_NAME = "xsl/oereblex.download.py";

    /*
     *  Download Geolink and create xml file with all documents for processing in main xsl transformation.
     */
    private String processOereblex(Settings settings, File outDirectory, String inputXtfFileName) throws Mgdm2OerebException {
        return "";
//        String venvExePath;
//        try {
//            var geoLinkXslFileName = settings.getValue(Mgdm2Oereb.MODEL) + ".oereblex.geolink_list.xsl";
//            var geoLinkXslFile = Paths.get(outDirectory.getAbsolutePath(), geoLinkXslFileName).toFile();
//            Util.loadFile("xsl/"+geoLinkXslFileName, geoLinkXslFile);  
//            
//            settings.setValue(Mgdm2Oereb.GEOLINK_LIST_TRAFO_PATH, geoLinkXslFile.getAbsolutePath());
//
//            URL resourceUrl = Mgdm2Oereb.class.getClassLoader().getResource(Paths.get("venv", "bin", "graalpy").toString());
//            if (resourceUrl != null) {
//                venvExePath = resourceUrl.getPath();
//            } else {
//                String venvZipName = "venv.zip";
//                String venvParentPath = Files.createTempDirectory(Paths.get(System.getProperty("java.io.tmpdir")), "mgdm2oereb_").toFile().getAbsolutePath();
//                try (InputStream is = getClass().getResourceAsStream("/"+venvZipName)) {
//                    File file = Paths.get(venvParentPath, new File(venvZipName).getName()).toFile();
//                    Files.copy(is, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
//                    
//                    String zipFilePath = Paths.get(venvParentPath, venvZipName).toFile().getAbsolutePath();
//                    log.debug("<zipFilePath> {}", zipFilePath);
//                    Zip.unzip(zipFilePath, new File(venvParentPath));
//                    log.debug("<venvParentPath> {}", venvParentPath);
//                    
//                    venvExePath = Paths.get(venvParentPath, "venv", "bin", "graalpy").toString();
//                }
//            }
//        } catch (IOException e) {
//            throw new Mgdm2OerebException(e.getMessage());
//        }
//        log.debug("<venvExePath> {}", venvExePath);
//        
//        var code = new InputStreamReader(Mgdm2Oereb.class.getClassLoader().getResourceAsStream(SOURCE_FILE_NAME));
//
//        String oereblexXmlFileName = null;
//        try (var context = Context.newBuilder("python")
//                .allowAllAccess(true)
//                .option("python.Executable", venvExePath)
//                .option("python.ForceImportSite", "true")
//                .build()) {
//            
//            context.eval(Source.newBuilder(PYTHON, code, SOURCE_FILE_NAME).build());
//
////            Value pyOereblexDownloaderClass = context.getPolyglotBindings().getMember("OereblexDownloader");
////            Value pyOereblexDownloader = pyOereblexDownloaderClass.newInstance();
////
////            OereblexDownloader oereblexDownloader = pyOereblexDownloader.as(OereblexDownloader.class);
////            oereblexXmlFileName = oereblexDownloader.run(new File(inputXtfFileName).getAbsolutePath(), outDirectory.getAbsolutePath(), settings);
//
//            // Approach mit Interface funktioniert mit native-image nicht, da nicht klar ist, welches Klasse das Interface implementiert.
//            Value oereblexDownloaderFn = context.getBindings("python").getMember("process_oereblex");
//            Value oereblexDownloadResult = oereblexDownloaderFn.execute(new File(inputXtfFileName).getAbsolutePath(), outDirectory.getAbsolutePath(), settings);
//            oereblexXmlFileName = oereblexDownloadResult.asString();
//            
//        } catch (IOException e) {
//            throw new Mgdm2OerebException(e.getMessage());
//        }
//        return oereblexXmlFileName;
    }
    
    public boolean convert(String inputXtfFileName, String outputDirectory, Settings settings) throws Mgdm2OerebException {
        var outDirectory = new File(outputDirectory);
        
        Map<String,String> params = new HashMap<String,String>();
        params.put("theme_code", settings.getValue(Mgdm2Oereb.THEME_CODE));
        params.put("model", settings.getValue(Mgdm2Oereb.MODEL));

        var xsltFileName = settings.getValue(Mgdm2Oereb.MODEL) + ".trafo.xsl";

        boolean oereblex = false;
        if (settings.getValue(Mgdm2Oereb.OEREBLEX_HOST) != null) {
            oereblex = true;
            xsltFileName = settings.getValue(Mgdm2Oereb.MODEL) + ".oereblex.trafo.xsl";
        }
        
        
        // Oereblex processing
        String oereblexXmlFileName = null;
        if (oereblex) {
           
            //oereblexXmlFileName = this.processOereblex(settings, outDirectory, inputXtfFileName);           
            
//            dom = ET.parse(xtf_path)
//                    xslt = ET.parse(geolink_list_trafo_path)
//                    transform = ET.XSLT(xslt)
//                    transformed = transform(dom, oereblex_host=ET.XSLT.strparam(oereb_lex_host))

            try {
                var oereblexFile = Paths.get(outputDirectory, "oereblex.xml").toFile();
                
                var geolinkXsltFileName = settings.getValue(Mgdm2Oereb.MODEL) + ".oereblex.geolink_list.xsl";
                var geolinkXsltFile = Paths.get(outputDirectory, geolinkXsltFileName).toFile();
                Util.loadFile("xsl/"+geolinkXsltFileName, geolinkXsltFile);
                
                params.put("oereblex_host", settings.getValue(Mgdm2Oereb.OEREBLEX_HOST));
                params.put("oereblex_output", oereblexFile.getAbsolutePath());
                
                Xslt.transform(geolinkXsltFile, new File(inputXtfFileName), oereblexFile, params);
                
                OereblexDownloader.process(oereblexFile);
                
                
                
            } catch (IOException | SaxonApiException e) {
                throw new Mgdm2OerebException(e.getMessage());
            }
        }
        
        // Main xsl transformation
//        try {
//            var xsltFile = Paths.get(outDirectory.getAbsolutePath(), xsltFileName).toFile();
//            Util.loadFile("xsl/"+xsltFileName, xsltFile);
//            
//            var outputXtfFile = Paths.get(outDirectory.getAbsolutePath(), "OeREBKRMtrsfr_V2_0.xtf").toFile();
//
//            var catalogFileName = settings.getValue(Mgdm2Oereb.CATALOG);
//            var catalogFile = Paths.get(outDirectory.getAbsolutePath(), catalogFileName).toFile();
//            Util.loadFile("catalogs/"+catalogFileName, catalogFile);
//                                    
//            params.put("catalog", catalogFile.getAbsolutePath());
//
//            Xslt.transform(xsltFile, new File(inputXtfFileName), outputXtfFile, params);
//            
//            boolean validate = Boolean.parseBoolean(settings.getValue(Mgdm2Oereb.VALIDATE));
//            
//            if (validate) {
//                var iliSettings = new ch.ehi.basics.settings.Settings();
//                var valid = Validator.runValidation(outputXtfFile.getAbsolutePath(), iliSettings);
//                return valid;
//            }
//            
//        } catch (IOException | SaxonApiException e) {
//            throw new Mgdm2OerebException(e.getMessage());
//        }
        return true;
    } 

    public static final String MODEL = "MODEL"; 
    
    public static final String THEME_CODE = "THEME_CODE";
    
    public static final String OEREBLEX_HOST = "OEREBLEX_HOST";

    public static final String OEREBLEX_CANTON = "OEREBLEX_CANTON";
    
    public static final String GEOLINK_LIST_TRAFO_PATH = "GEOLINK_LIST_TRAFO_PATH";

    public static final String CATALOG = "CATALOG";
       
    public static final String DUMMY_OFFICE_NAME = "DUMMY_OFFICE_NAME";
    
    public static final String DUMMY_OFFICE_URL = "DUMMY_OFFICE_URL";
    
    public static final String VALIDATE = "VALIDATE";

    public static final String LOGLEVEL = "LOGLEVEL";
}
