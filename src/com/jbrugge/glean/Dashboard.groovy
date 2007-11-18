package com.jbrugge.glean

import groovy.xml.*
import org.xml.sax.*

/**
 * A class that extracts summary information from a variety of code feedback tools
 * and generates an XML document from it. The tools that this class knows about
 * all generate XML as their output, so this class simply looks for the appropriate
 * place for the particular summary data.
 *
 * To use it, call the <code>setResults</code> method for each set of tool data
 * that you have, and then ask it for the results with <code>toXml</code>.
 *
 * The XML output can also include references to code documentation output to
 * provide a more complete description of the feedback available for a given
 * codebase. Set the <code>docRoot</code> property to the directory containing
 * doc output, and the class will include references to any of the known doc
 * tools that it finds.
 *
 * @author John Brugge
 */
class Dashboard {
    def toolResults = [:]
    
    // While we don't extract information from documentation tools,
    // we will add information about doc output that can be found
    // in the final XML output.
    def docTools = ["javadoc" : "Java API doc",
                "beandoc" : "Spring bean doc",
                "java2html14" : "Source cross ref",
                "java2html15" : "Source cross ref",
                "schemaspy" : "Database schema browser",
                "dbdoclet" : "Javadoc as Docbook",
                "umlgraph-doc" : "Javadoc with embedded UML",
                "linguine-ant" : "Ant structure doc",
                "linguine-hbm" : "Hibernate structure doc",
                "statsvn" : "Subversion repository activity",
                "statcvs" : "CVS repository activity"
                ]
    String projectName
    String buildTime
    String buildLabel
    String docRoot
    
    // Meta info to use in building the final data set
    def sections = ['standards' : "Design and Coding Standards", 
                    'tests' : "Unit Testing Measures",
                    'design' : "Design Quality Measures",
                    'size' : "Codebase Size"]

    // [ tool name, dashboard section, measurement name, retrieval function ]
    def pmdInfo = ['pmd', 'standards', 'Rule violations', "getPmdViolations" ]
    def checkstyleInfo = ['checkstyle', 'standards', 'Rule violations', "getCheckstyleViolations" ]
    def findbugsInfo = ['findbugs', 'standards', 'Rule violations', "getFindbugsViolations" ]
    def cpdInfo = ['cpd', 'standards', 'Copy and paste fragments', "getCpdCount" ]
    def emmaClassInfo = ['emma', 'tests', 'Class coverage', "getEmmaClassCoverage" ]
    def emmaMethodInfo = ['emma', 'tests', 'Method coverage', "getEmmaMethodCoverage" ]
    def coberturaBranchInfo = ['cobertura', 'tests', 'Branch coverage', "getCoberturaBranchCoverage" ]
    def coberturaLineInfo = ['cobertura', 'tests', 'Line coverage', "getCoberturaLineCoverage" ]
    def junitTestInfo = ['junitreport', 'tests', 'Unit tests run', "getJUnitTestCount" ]
    def junitFailureInfo = ['junitreport', 'tests', 'Unit tests failed', "getJUnitFailureCount" ]
    def jdependCaInfo = ['jdepend', 'design', 'Max afferent', "getJDependMaxCa" ]
    def jdependCeInfo = ['jdepend', 'design', 'Max efferent', "getJDependMaxCe" ]
    def javancssCCNInfo = ['javancss', 'design', 'Max complexity (CCN)', "getJavaNcssMaxComplexity" ]
    def javancssLineInfo = ['javancss', 'size', 'Line count (NCSS)', "getJavaNcssLineCount" ]
    def javancssClassInfo = ['javancss', 'size', 'Class count', "getJavaNcssClassCount" ]

    def toolInfo = [ pmdInfo, checkstyleInfo, findbugsInfo, cpdInfo,
                emmaClassInfo, emmaMethodInfo, coberturaBranchInfo, coberturaLineInfo,
                junitTestInfo, junitFailureInfo,
                jdependCaInfo, jdependCeInfo, javancssCCNInfo,
                javancssLineInfo, javancssClassInfo ]
    
    def Dashboard() {
    }
    
    def Dashboard(name, time, label) {
        projectName = name
        buildTime = time
        buildLabel = label
    }
    
    def getRecords(toolFile) {
        XmlSlurper slurper = new XmlSlurper()
        
        return slurper.parse(toolFile)
    }
    
    void setResults(String toolName, String fileName) {
        if (fileName != null && fileName.length() > 0) {
            File toolResultsFile = new File(fileName)
            if (toolResultsFile.exists()) {
                toolResults[toolName] = toolResultsFile
            }
        }
    }
    void setResults(String toolName, InputStream stream) {
        if (stream != null) {
            toolResults[toolName] = stream
        }
    }
    
    def getPmdViolations() {
        def results = null
        
        if (toolResults["pmd"] != null) {
            def records = getRecords(toolResults["pmd"])
    
            def violationRecords = records.file.violation
            
            results = violationRecords.size()
        }
        
        return results
    }
    
    def getCheckstyleViolations() {
        def results = null
        
        if (toolResults["checkstyle"] != null) {
            def records = getRecords(toolResults["checkstyle"])
        
            def violationRecords = records.file.error
        
            results = violationRecords.size()
        }
        
        return results
    }
    
    def getFindbugsViolations() {
        def results = null
        
        if (toolResults["findbugs"] != null) {
            def records = getRecords(toolResults["findbugs"])
        
            def violationCount = records.FindBugsSummary.@total_bugs
        
            results = violationCount.toInteger()
        }
        
        return results
    }
    
    def getEmmaClassCoverage() {
        def results = null
        
        if (toolResults["emma"] != null) {
            def records = getRecords(toolResults["emma"])
        
            def baseRecords = records.data.all.coverage
        
            def classData = baseRecords.find { it.@type.text().contains("class") }
        
            results = classData.@value[0].text()
        }
        
        return results
    }
    
    def getEmmaMethodCoverage() {
        def results = null
        
        if (toolResults["emma"] != null) {
            def records = getRecords(toolResults["emma"])
        
            def baseRecords = records.data.all.coverage
            
            def methodData = baseRecords.find { it.@type.text().contains("method") }
        
            results = methodData.@value[0].text()
        }
        
        return results
    }
    
    def getCoberturaBranchCoverage() {
        def results = null
        
        if (toolResults["cobertura"] != null) {
            def records = getRecords(toolResults["cobertura"])
        
            def data = records.'@branch-rate'
            def percent = (data.toBigDecimal() * 100).intValue()
        
            results = percent + "%"
        }
        
        return results
    }
    
    def getCoberturaLineCoverage() {
        def results = null
        
        if (toolResults["cobertura"] != null) {
            def records = getRecords(toolResults["cobertura"])
        
            def data = records.'@line-rate'
            def percent = (data.toBigDecimal() * 100).intValue()
        
            results = percent + "%"
        }
        
        return results
    }
    
    def getJavaNcssClassCount() {
        def results = null
        
        if (toolResults["javancss"] != null) {
            def records = getRecords(toolResults["javancss"])
        
            def classes = records.packages.total.classes[0]
        
            results = classes.text().toInteger()
        }
        
        return results
    }

    def getJavaNcssLineCount() {
        def results = null
        
        if (toolResults["javancss"] != null) {
            def records = getRecords(toolResults["javancss"])
        
            def lines = records.packages.total.ncss[0]
        
            results = lines.text().toInteger()
        }
        
        return results
    }
    
    def getJavaNcssMaxComplexity() {
        def results = null
        
        if (toolResults["javancss"] != null) {
            def records = getRecords(toolResults["javancss"])

            def ccnList = records.functions.function.ccn.list()*.text()*.toInteger()
        
            results = ccnList.max().toInteger()
        }
        
        return results
    }
    
    def getCpdCount() {
        def results = null
        
        if (toolResults["cpd"] != null) {
            def records = getRecords(toolResults["cpd"])
        
            def dups = records.duplication
        
            results = dups.size()
        }
        
        return results
    }
    
    def getJUnitTestCount() {
        def results = null
        
        if (toolResults["junitreport"] != null) {
            def records = getRecords(toolResults["junitreport"])
        
            def testList = records.testsuite.@tests.list()*.toInteger()
        
            results = testList.sum()
        }
        
        return results
    }
    
    def getJUnitFailureCount() {
        def results = null
        
        if (toolResults["junitreport"] != null) {
            def records = getRecords(toolResults["junitreport"])
        
            def errorList = records.testsuite.@errors.list()*.toInteger()
            def failureList = records.testsuite.@failures.list()*.toInteger()
        
            results = errorList.sum() + failureList.sum()
        }
        
        return results
    }
    
    def getJDependMaxCa() {
        def results = null
        
        if (toolResults["jdepend"] != null) {
            def records = getRecords(toolResults["jdepend"])
        
            def caList = records.Packages.Package.Stats.Ca.list()*.text()
        
            results = caList.max().toInteger()
        }
        
        return results
    }
    
    def getJDependMaxCe() {
        def results = null
        
        if (toolResults["jdepend"] != null) {
            def records = getRecords(toolResults["jdepend"])
        
            def ceList = records.Packages.Package.Stats.Ce.list()*.text()
        
            results = ceList.max().toInteger()
        }
        
        return results
    }
    
    def getJDependCycles() {
        def results = null
        
        if (toolResults["jdepend"] != null) {
            def records = getRecords(toolResults["jdepend"])
        
            def cycleList = records.Cycles.Package
        
            results = cycleList.size()
        }
        
        return results
    }
    
    String toXml() {
        def writer = new StringWriter()
        def builder = new MarkupBuilder(writer)
        
        builder.summary() {
            project(name: "$projectName") {
                build(time: "$buildTime", label: "$buildLabel", docpath : "$docRoot") {
                    sections.each { section, desc ->
                        feedback(name : "$section", description : "$desc") {
                            def sectionTools = toolInfo.findAll { info -> info[1] == section }
                            
                            sectionTools.each { tool ->
                                def params = [] as Object[]
                                def toolMeasure = this.invokeMethod(tool[3], params)
                                if (toolMeasure != null) {
                                    result(metric : tool[2],
                                           measurement : toolMeasure,
                                           source : tool[0])
                                }
                            }
                        }
                    }
                    documentation(description : "Project Docs") {
                        docTools.each { name, desc ->
                            def docOutput = new File(docRoot + "/" + name)
                            if (docOutput.exists()) {
                                doc(description : desc,
                                    source : name)
                            }
                        }
                    }

                }
            }
        }
        
        return writer.toString()
    }
    
}

