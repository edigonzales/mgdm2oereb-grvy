package ch.so.agi.mgdm2oereb;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.stream.StreamSource;

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

public class Xslt {
    
    public static void transform(File xsltFile, File inputFile, File outputFile, Map<String,String> params) throws SaxonApiException {
        Processor proc = new Processor(false);
        XsltCompiler comp = proc.newXsltCompiler();
        XsltExecutable exp = comp.compile(new StreamSource(xsltFile));
        
        XdmNode source = proc.newDocumentBuilder().build(new StreamSource(inputFile));
        Serializer outXtf = proc.newSerializer(outputFile);
        XsltTransformer trans = exp.load();
        trans.setInitialContextNode(source);
        trans.setDestination(outXtf);
        
        for (Map.Entry<String, String> entry : params.entrySet()) {
            trans.setParameter(new QName(entry.getKey()), (XdmValue) XdmAtomicValue.makeAtomicValue(entry.getValue()));
        }

        /*
        if (oereblex) {
            trans.setParameter(new QName("oereblex_host"), (XdmValue) XdmAtomicValue.makeAtomicValue(settings.getValue(Mgdm2Oereb.OEREBLEX_HOST)));
            trans.setParameter(new QName("oereblex_output"), (XdmValue) XdmAtomicValue.makeAtomicValue(oereblexXmlFileName));
        }
        */
        trans.transform();
        trans.close();
    }

    public static void transform(String xsltResourceName, File inputFile, File outputFile, Map<String,String> params) throws IOException, SaxonApiException {
        var outputDirectory = Files.createTempDirectory("mgdm2oereb").toFile();
        var xsltFile = Paths.get(outputDirectory.getAbsolutePath(), xsltResourceName).toFile();
        Util.loadFile("xsl/"+xsltResourceName, xsltFile);
        
        transform(xsltFile, inputFile, outputFile, params);
    }
}
