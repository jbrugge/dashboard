package com.jbrugge.glean

import groovy.util.*

class DashboardTest extends GroovyTestCase {
    Dashboard dashboard
    
    void setUp() {
        dashboard = new Dashboard()
    }
    
    void testPmdResults() {
        // Set up the data
        def pmdResults = new ByteArrayInputStream(PMD_DATA.getBytes())
        dashboard.setResults("pmd", pmdResults)
        
        // Run the test
        def results = dashboard.getPmdViolations()
        
        // Check the results
        assertEquals("PMD violations", 4, results)
    }
    
    void testNoPmdResults() {
        // Set up the data
        String pmdResults = null
        dashboard.setResults("pmd", (String)pmdResults)
        
        // Run the test
        def results = dashboard.getPmdViolations()
        
        // Check the results
        assertEquals("PMD violations", null, results)
    }
    
    void testMissingResults() {
        // Set up the data
        
        // Run the test
        def results = dashboard.getPmdViolations()
        
        // Check the results
        assertEquals("PMD violations", null, results)
    }
    
    void testEmmaClassResults() {
        // Set up the data
        def emmaResults = new ByteArrayInputStream(EMMA_DATA.getBytes())
        dashboard.setResults("emma", emmaResults)
        
        // Run the test
        def results = dashboard.getEmmaClassCoverage()
        
        // Check the results
        assertTrue("Emma class coverage", results.startsWith("27%"))
    }
    
    void testEmmaMethodsResults() {
        // Set up the data
        def emmaResults = new ByteArrayInputStream(EMMA_DATA.getBytes())
        dashboard.setResults("emma", emmaResults)
        
        // Run the test
        def results = dashboard.getEmmaMethodCoverage()
        
        // Check the results
        assertTrue("Emma method coverage", results.startsWith("16%"))
    }
    
    void testCoberturaBranchResults() {
        // Set up the data
        def coberturaResults = new ByteArrayInputStream(COBERTURA_DATA.getBytes())
        dashboard.setResults("cobertura", coberturaResults)
        
        // Run the test
        def results = dashboard.getCoberturaBranchCoverage()
        
        // Check the results
        assertTrue("Cobertura branch coverage", results.startsWith("30%"))
    }
    
    void testCoberturaLineResults() {
        // Set up the data
        def coberturaResults = new ByteArrayInputStream(COBERTURA_DATA.getBytes())
        dashboard.setResults("cobertura", coberturaResults)
        
        // Run the test
        def results = dashboard.getCoberturaLineCoverage()
        
        // Check the results
        assertTrue("Cobertura line coverage", results.startsWith("29%"))
    }
    
    void testJavaNcssClassCount() {
        // Set up the data
        def javancssResults = new ByteArrayInputStream(JAVANCSS_DATA.getBytes())
        dashboard.setResults("javancss", javancssResults)
        
        // Run the test
        def results = dashboard.getJavaNcssClassCount()
        
        // Check the results
        assertEquals("JavaNcss class count", 19, results)
    }
    
    void testJavaNcssLineCount() {
        // Set up the data
        def javancssResults = new ByteArrayInputStream(JAVANCSS_DATA.getBytes())
        dashboard.setResults("javancss", javancssResults)
        
        // Run the test
        def results = dashboard.getJavaNcssLineCount()
        
        // Check the results
        assertEquals("JavaNcss line count", 1813, results)
    }
    
    void testGetJavaNcssMaxComplexity() {
        // Set up the data
        def javancssResults = new ByteArrayInputStream(JAVANCSS_DATA.getBytes())
        dashboard.setResults("javancss", javancssResults)
        
        // Run the test
        def results = dashboard.getJavaNcssMaxComplexity()
        
        // Check the results
        assertEquals("JavaNcss max CCN", 43, results)
    }
    
    void testGetCheckstyleViolations() {
        // Set up the data
        def checkstyleResults = new ByteArrayInputStream(CHECKSTYLE_DATA.getBytes())
        dashboard.setResults("checkstyle", checkstyleResults)
        
        // Run the test
        def results = dashboard.getCheckstyleViolations()
        
        // Check the result
        assertEquals("Checkstyle violations", 10, results)
    }
    
    void testGetFindbugsViolations() {
        // Set up the data
        def findbugsResults = new ByteArrayInputStream(FINDBUGS_DATA.getBytes())
        dashboard.setResults("findbugs", findbugsResults)
        
        // Run the test
        def results = dashboard.getFindbugsViolations()
        
        // Check the result
        assertEquals("Findbugs violations", 2, results)
    }
    
    void testCpdCount() {
        // Set up the data
        def cpdResults = new ByteArrayInputStream(CPD_DATA.getBytes())
        dashboard.setResults("cpd", cpdResults)
        
        // Run the test
        def results = dashboard.getCpdCount()
        
        // Check the result
        assertEquals("CPD violations", 2, results)
    }
    
    void testGetJDependMaxCa() {
        // Set up the data
        def dataStream = new ByteArrayInputStream(JDEPEND_DATA.getBytes())
        dashboard.setResults("jdepend", dataStream)
        
        // Run the test
        def results = dashboard.getJDependMaxCa()
        
        // Check the results
        assertEquals("JDepend max Ca", 7, results)
    }
    
    void testGetJDependMaxCe() {
        // Set up the data
        def dataStream = new ByteArrayInputStream(JDEPEND_DATA.getBytes())
        dashboard.setResults("jdepend", dataStream)
        
        // Run the test
        def results = dashboard.getJDependMaxCe()
        
        // Check the results
        assertEquals("JDepend max Ce", 9, results)
    }
    
    void testGetJDependCycles() {
        // Set up the data
        def dataStream = new ByteArrayInputStream(JDEPEND_DATA.getBytes())
        dashboard.setResults("jdepend", dataStream)
        
        // Run the test
        def results = dashboard.getJDependCycles()
        
        // Check the results
        assertEquals("JDepend cycles", 2, results)
    }
    
    void testGetJUnitTestCount() {
        // Set up the data
        def dataStream = new ByteArrayInputStream(JUNIT_DATA.getBytes())
        dashboard.setResults("junitreport", dataStream)
        
        // Run the test
        def results = dashboard.getJUnitTestCount()
        
        // Check the results
        assertEquals("JUnit tests", 59, results)
    }
    
    void testGetJUnitFailureCount() {
        // Set up the data
        def dataStream = new ByteArrayInputStream(JUNIT_DATA.getBytes())
        dashboard.setResults("junitreport", dataStream)
        
        // Run the test
        def results = dashboard.getJUnitFailureCount()
        
        // Check the results
        assertEquals("JUnit failures", 9, results)
    }
    
    static def PMD_DATA = '''
<pmd version="3.9" timestamp="2007-04-17T08:58:43.734" elapsedTime="1s">
<file name="net/tds/ldap/imp/jndi/LdapDnFactoryImp.java">
<violation line="83" rule="UnusedLocalVariable" ruleset="Unused Code Rules" package="net.tds.ldap.imp.jndi" class="Anonymous$7" method="equals" externalInfoUrl="http://pmd.sourceforge.net/rules/unusedcode.html#UnusedLocalVariable" priority="3">
Avoid unused local variables such as 'rdnEquals'.
</violation>
</file>
<file name="net/tds/ldap/imp/jndi/LdapFilterFactoryImp.java">
<violation line="427" rule="SwitchStmtsShouldHaveDefault" ruleset="Design Rules" package="net.tds.ldap.imp.jndi" class="LdapFilterFactoryImp" method="filterEscape" externalInfoUrl="http://pmd.sourceforge.net/rules/design.html#SwitchStmtsShouldHaveDefault" priority="3">
Switch statements should have a default label
</violation>
</file>
<file name="net/tds/ldap/imp/jndi/LdapModFactoryImp.java">
<violation line="211" rule="UnusedFormalParameter" ruleset="Unused Code Rules" package="net.tds.ldap.imp.jndi" class="LdapModFactoryImp" externalInfoUrl="http://pmd.sourceforge.net/rules/unusedcode.html#UnusedFormalParameter" priority="3">
Avoid unused method parameters such as 'attribute'.
</violation>
<violation line="220" rule="UnusedFormalParameter" ruleset="Unused Code Rules" package="net.tds.ldap.imp.jndi" class="LdapModFactoryImp" externalInfoUrl="http://pmd.sourceforge.net/rules/unusedcode.html#UnusedFormalParameter" priority="3">
Avoid unused method parameters such as 'attribute'.
</violation>
</file>
</pmd>
'''

    static def EMMA_DATA = '''
<report>
  <stats>
    <packages value="25"/>
    <classes value="392"/>
    <methods value="2661"/>
    <srcfiles value="227"/>
    <srclines value="22429"/>
  </stats>
  <data>
    <all name="all classes">
      <coverage type="class, %" value="27%  (106/392)"/>
      <coverage type="method, %" value="16%  (426/2661)"/>
      <coverage type="block, %" value="13%  (13075/102965)"/>
      <coverage type="line, %" value="13%  (2935.4/22429)"/>

      <package name="com.jbrugge.a.b">
        <coverage type="class, %" value="100% (1/1)"/>
        <coverage type="method, %" value="64%  (9/14)"/>
        <coverage type="block, %" value="31%  (439/1400)"/>
        <coverage type="line, %" value="38%  (114/297)"/>

        <srcfile name="MyClassA.java">
          <coverage type="class, %" value="100% (1/1)"/>
          <coverage type="method, %" value="64%  (9/14)"/>
          <coverage type="block, %" value="31%  (439/1400)"/>
          <coverage type="line, %" value="38%  (114/297)"/>

          <class name="MyClassA">
            <coverage type="class, %" value="100% (1/1)"/>
            <coverage type="method, %" value="64%  (9/14)"/>
            <coverage type="block, %" value="31%  (439/1400)"/>
            <coverage type="line, %" value="38%  (114/297)"/>

            <method name="&lt;static initializer&gt;">
              <coverage type="method, %" value="100% (1/1)"/>
              <coverage type="block, %" value="100% (40/40)"/>
              <coverage type="line, %" value="100% (9/9)"/>
            </method>
            <method name="MyClassA (long): void">
              <coverage type="method, %" value="100% (1/1)"/>
              <coverage type="block, %" value="100% (33/33)"/>
              <coverage type="line, %" value="100% (11/11)"/>
            </method>
          </class>
        </srcfile>
      </package>
    </all>
  </data>
</report>
'''

    static def JAVANCSS_DATA = '''
<javancss>
  <date>2007-05-02</date>
  <time>20:51:50</time>
  <packages>
    <package>
      <name>com.sun.j2ee.blueprints.admin.client</name>
      <classes>19</classes>
      <functions>130</functions>
      <ncss>1813</ncss>
      <javadocs>53</javadocs>
      <javadoc_lines>333</javadoc_lines>
      <single_comment_lines>117</single_comment_lines>
      <multi_comment_lines>195</multi_comment_lines>
    </package>
    <total>
      <classes>19</classes>
      <functions>130</functions>
      <ncss>1813</ncss>
      <javadocs>53</javadocs>
      <javadoc_lines>333</javadoc_lines>
      <single_comment_lines>117</single_comment_lines>
      <multi_comment_lines>195</multi_comment_lines>
    </total>
    <table>
      <tr><td>Packages</td><td>Classes</td><td>Functions</td><td>NCSS</td><td>Javadocs</td><td>per</td></tr>
      <tr><td>1.00</td><td>19.00</td><td>130.00</td><td>1,813.00</td><td>53.00</td><td>Project</td></tr>
      <tr><td></td><td>19.00</td><td>130.00</td><td>1,813.00</td><td>53.00</td><td>Package</td></tr>
      <tr><td></td><td></td><td>6.84</td><td>95.42</td><td>2.79</td><td>Class</td></tr>
      <tr><td></td><td></td><td></td><td>13.95</td><td>0.41</td><td>Function</td></tr>
    </table>
  </packages>
  <functions>
    <function>
      <name>com.sun.j2ee.blueprints.admin.client.About.About(String,String,String[],Frame,boolean)</name>
      <ncss>8</ncss>
      <ccn>43</ccn>
      <javadocs>0</javadocs>
    </function>
    <function>
      <name>com.sun.j2ee.blueprints.admin.client.About.setVisible(boolean)</name>
      <ncss>6</ncss>
      <ccn>5</ccn>
      <javadocs>0</javadocs>
    </function>
    <function>
      <name>com.sun.j2ee.blueprints.admin.client.AboutPanel.AboutPanel(String[])</name>
      <ncss>4</ncss>
      <ccn>1</ccn>
      <javadocs>0</javadocs>
    </function>
    <function>
      <name>com.sun.j2ee.blueprints.admin.client.AboutPanel.paintComponent(Graphics)</name>
      <ncss>30</ncss>
      <ccn>7</ccn>
      <javadocs>0</javadocs>
    </function>
    <function>
      <name>com.sun.j2ee.blueprints.admin.client.AboutPanel.start()</name>
      <ncss>2</ncss>
      <ccn>10</ccn>
      <javadocs>0</javadocs>
    </function>
  </functions>
</javancss>
'''

static def CHECKSTYLE_DATA = '''
<checkstyle version="4.3">
<file name="/Users/john/tools/commons-lang-src/src/java/org/apache/commons/lang/text/StrLookup.java">
</file>
<file name="/Users/john/tools/commons-lang-src/src/java/org/apache/commons/lang/text/StrMatcher.java">
</file>
<file name="/Users/john/tools/commons-lang-src/src/java/org/apache/commons/lang/text/StrSubstitutor.java">
<error line="160" column="35" severity="error" message="Parameter array should be final." source="com.puppycrawl.tools.checkstyle.checks.FinalParametersCheck"/>
<error line="170" severity="error" message="Line is longer than 80 characters." source="com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck"/>
</file>
<file name="/Users/john/tools/commons-lang-src/src/java/org/apache/commons/lang/ArrayUtils.java">
<error line="171" severity="error" message="Line has trailing spaces." source="com.puppycrawl.tools.checkstyle.checks.GenericIllegalRegexpCheck"/>
<error line="173" severity="error" message="Line is longer than 80 characters." source="com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck"/>
<error line="175" severity="error" message="Line has trailing spaces." source="com.puppycrawl.tools.checkstyle.checks.GenericIllegalRegexpCheck"/>
<error line="176" column="35" severity="error" message="Parameter array should be final." source="com.puppycrawl.tools.checkstyle.checks.FinalParametersCheck"/>
<error line="176" column="49" severity="error" message="Parameter stringIfNull should be final." source="com.puppycrawl.tools.checkstyle.checks.FinalParametersCheck"/>
<error line="180" severity="error" message="Line is longer than 80 characters." source="com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck"/>
<error line="184" severity="error" message="Line is longer than 80 characters." source="com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck"/>
<error line="185" severity="error" message="Line has trailing spaces." source="com.puppycrawl.tools.checkstyle.checks.GenericIllegalRegexpCheck"/>
</file>
</checkstyle>
'''

static def FINDBUGS_DATA = '''
<BugCollection version="1.1.1" sequence="0" timestamp="1180405081614" analysisTimestamp="1180405087692" release="">
  <Project filename="&lt;&lt;unnamed project&gt;&gt;">
    <Jar>/Users/john/tools/jdom/build/classes</Jar>
    <SrcDir>/Users/john/tools/jdom/src/java</SrcDir>
  </Project>
  <BugInstance type="SE_NO_SERIALVERSIONID" priority="2" abbrev="SnVI" category="BAD_PRACTICE" uid="0" instanceHash="aca724796ce8b31bcefa26fd6b0f015b" instanceOccurrenceNum="0" instanceOccurrenceMax="0">
    <ShortMessage>Class is Serializable, but does not define serialVersionUID</ShortMessage>
    <LongMessage>org.jdom.Attribute is Serializable; consider declaring a serialVersionUID</LongMessage>
    <Class classname="org.jdom.Attribute">
      <SourceLine classname="org.jdom.Attribute" sourcefile="Attribute.java" sourcepath="org/jdom/Attribute.java">
        <Message>In Attribute.java</Message>
      </SourceLine>
      <Message>In class org.jdom.Attribute</Message>
    </Class>
    <SourceLine classname="org.jdom.Attribute" sourcefile="Attribute.java" sourcepath="org/jdom/Attribute.java" synthetic="true">
      <Message>In Attribute.java</Message>
    </SourceLine>
  </BugInstance>
  <BugInstance type="SE_NO_SERIALVERSIONID" priority="2" abbrev="SnVI" category="BAD_PRACTICE" uid="1" instanceHash="f50a8d94b5633528295e0de48abbf7a4" instanceOccurrenceNum="0" instanceOccurrenceMax="0">
    <ShortMessage>Class is Serializable, but does not define serialVersionUID</ShortMessage>
    <LongMessage>org.jdom.Element is Serializable; consider declaring a serialVersionUID</LongMessage>
    <Class classname="org.jdom.Element">
      <SourceLine classname="org.jdom.Element" sourcefile="Element.java" sourcepath="org/jdom/Element.java">
        <Message>In Element.java</Message>
      </SourceLine>
      <Message>In class org.jdom.Element</Message>
    </Class>
    <SourceLine classname="org.jdom.Element" sourcefile="Element.java" sourcepath="org/jdom/Element.java" synthetic="true">
      <Message>In Element.java</Message>
    </SourceLine>
  </BugInstance>
  <FindBugsSummary timestamp="Mon, 28 May 2007 21:18:01 -0500" total_classes="76" total_bugs="2" total_size="6128" num_packages="8" cpu_seconds="47.40" clock_seconds="58.85" peak_mbytes="55.23" gc_seconds="14.23" priority_2="44" priority_1="1">
    <PackageStats package="" total_bugs="0" total_types="3" total_size="75">
      <ClassStats class="JDOMAbout" interface="false" size="31" bugs="0"/>
      <ClassStats class="JDOMAbout$Author" interface="false" size="8" bugs="0"/>
      <ClassStats class="JDOMAbout$Info" interface="false" size="36" bugs="0"/>
    </PackageStats>
  </FindBugsSummary>
</BugCollection>
'''

static def CPD_DATA = '''
<pmd-cpd>
<duplication lines="43" tokens="221">
<file line="94" path="/Users/john/tools/jdom/src/java/org/jdom/adapters/OracleV1DOMAdapter.java"/>
<file line="94" path="/Users/john/tools/jdom/src/java/org/jdom/adapters/OracleV2DOMAdapter.java"/>
<codefragment>
<![CDATA[
    public Document createDocument() throws JDOMException {
        try {
            return
                (Document)Class.forName(
]]>
</codefragment>
</duplication>
<duplication lines="39" tokens="212">
<file line="98" path="/Users/john/tools/jdom/src/java/org/jdom/adapters/OracleV1DOMAdapter.java"/>
<file line="124" path="/Users/john/tools/jdom/src/java/org/jdom/adapters/XML4JDOMAdapter.java"/>
<codefragment>
<![CDATA[
    public Document createDocument() throws JDOMException {
        try {
            return
                (Document)Class.forName(
]]>
</codefragment>
</duplication>
</pmd-cpd>
'''

static def COBERTURA_DATA = '''
<coverage line-rate="0.293437886917045" branch-rate="0.30287648054145516" version="1.8" timestamp="1180406535857">
	<sources>
		<source>/Users/john/tools/jdom/src/java</source>
	</sources>
	<packages>
		<package name="" line-rate="0.0" branch-rate="0.0" complexity="0.0">
			<classes>
				<class name="JDOMAbout" filename="JDOMAbout.java" line-rate="0.0" branch-rate="0.0" complexity="0.0">
					<methods>
						<method name="&lt;init&gt;" signature="()V" line-rate="0.0" branch-rate="1.0">
							<lines>
								<line number="85" hits="0" branch="false"/>
								<line number="250" hits="0" branch="false"/>
							</lines>
						</method>
                    </methods>
                </class>
            </classes>
        </package>
    </packages>
</coverage>
'''

static def JDEPEND_DATA = '''
<JDepend>
    <Packages>

        <Package name="Default">
            <Stats>
                <TotalClasses>3</TotalClasses>
                <ConcreteClasses>3</ConcreteClasses>
                <AbstractClasses>0</AbstractClasses>
                <Ca>0</Ca>
                <Ce>5</Ce>
                <A>0</A>
                <I>1</I>
                <D>0</D>
                <V>1</V>
            </Stats>

            <AbstractClasses>
            </AbstractClasses>

            <ConcreteClasses>
                <Class sourceFile="JDOMAbout.java">
                    JDOMAbout
                </Class>
                <Class sourceFile="JDOMAbout.java">
                    JDOMAbout$Author
                </Class>
                <Class sourceFile="JDOMAbout.java">
                    JDOMAbout$Info
                </Class>
            </ConcreteClasses>

            <DependsUpon>
                <Package>java.io</Package>
                <Package>java.lang</Package>
                <Package>java.util</Package>
                <Package>org.jdom</Package>
                <Package>org.jdom.input</Package>
            </DependsUpon>

            <UsedBy>
            </UsedBy>
        </Package>

        <Package name="org.jdom">
            <Stats>
                <TotalClasses>29</TotalClasses>
                <ConcreteClasses>26</ConcreteClasses>
                <AbstractClasses>3</AbstractClasses>
                <Ca>7</Ca>
                <Ce>9</Ce>
                <A>0.1</A>
                <I>0.56</I>
                <D>0.33</D>
                <V>1</V>
            </Stats>

            <AbstractClasses>
                <Class sourceFile="Content.java">
                    org.jdom.Content
                </Class>
                <Class sourceFile="JDOMFactory.java">
                    org.jdom.JDOMFactory
                </Class>
                <Class sourceFile="Parent.java">
                    org.jdom.Parent
                </Class>
            </AbstractClasses>

            <ConcreteClasses>
                <Class sourceFile="Attribute.java">
                    org.jdom.Attribute
                </Class>
                <Class sourceFile="AttributeList.java">
                    org.jdom.AttributeList
                </Class>
                <Class sourceFile="CDATA.java">
                    org.jdom.CDATA
                </Class>
                <Class sourceFile="Comment.java">
                    org.jdom.Comment
                </Class>
                <Class sourceFile="ContentList.java">
                    org.jdom.ContentList
                </Class>
            </ConcreteClasses>

            <DependsUpon>
                <Package>java.io</Package>
                <Package>java.lang</Package>
                <Package>java.lang.reflect</Package>
                <Package>java.rmi</Package>
                <Package>java.sql</Package>
                <Package>java.util</Package>
                <Package>org.jdom.filter</Package>
                <Package>org.jdom.output</Package>
                <Package>org.xml.sax</Package>
            </DependsUpon>

            <UsedBy>
                <Package>Default</Package>
                <Package>org.jdom.adapters</Package>
                <Package>org.jdom.filter</Package>
                <Package>org.jdom.input</Package>
                <Package>org.jdom.output</Package>
                <Package>org.jdom.transform</Package>
                <Package>org.jdom.xpath</Package>
            </UsedBy>
        </Package>
    </Packages>
    <Cycles>
        <Package Name="Default">
            <Package>org.jdom.input</Package>
            <Package>org.jdom</Package>
            <Package>org.jdom.output</Package>
            <Package>org.jdom.adapters</Package>
            <Package>org.jdom</Package>
        </Package>

        <Package Name="org.jdom.filter">
            <Package>org.jdom</Package>
            <Package>org.jdom.output</Package>
            <Package>org.jdom.adapters</Package>
            <Package>org.jdom</Package>
        </Package>

    </Cycles>
</JDepend>
'''

static def JUNIT_DATA = '''
<testsuites>
  <testsuite errors="0" failures="0" id="0" name="TestAttribute" package="org.jdom.test.cases" tests="20" time="0.995">
  </testsuite>
  <testsuite errors="3" failures="0" id="1" name="TestComment" package="org.jdom.test.cases" tests="7" time="0.765">
  </testsuite>
  <testsuite errors="0" failures="4" id="2" name="TestDocType" package="org.jdom.test.cases" tests="13" time="0.74">
  </testsuite>
  <testsuite errors="2" failures="0" id="3" name="TestDocument" package="org.jdom.test.cases" tests="19" time="0.923">
  </testsuite>
</testsuites> 
'''
}
