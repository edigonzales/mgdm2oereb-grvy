package ch.so.agi.mgdm2oereb

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import groovy.xml.XmlSlurper

class OereblexDownloader {
    static Logger log = LoggerFactory.getLogger(OereblexDownloader.class);
    
    public static void process(File oereblexFile) {        
        log.info(oereblexFile.text)
        def transformed = new XmlSlurper().parseText(oereblexFile.text)

        def oereblex_geolink_unique = [:]
        def it = transformed.childNodes()
        while(it.hasNext()) {
            def child = (groovy.xml.slurpersupport.Node)it.next()
            
            if (!oereblex_geolink_unique.containsKey(child.attributes["mgdm_geolink_id"])) {
                oereblex_geolink_unique[child.attributes["mgdm_geolink_id"]] = {}
                log.info("LexlinkID {} was added.", child.attributes["mgdm_geolink_id"])
    
            }
            
            
        }        
    }
}
