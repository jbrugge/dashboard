<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:output method="html" 
     indent="yes"  />
<xsl:param name="indexpage" select="index.html"/>

<xsl:template match="/summary/project">
    <html>
    <head>
    <META HTTP-EQUIV="Content-Style-Type" CONTENT="text/css"/>
    <title>Dashboard for <xsl:value-of select="@name"/> feedback</title>
    <link rel="stylesheet" type="text/css" href="dashboard.css"/>
    </head>
    <body>
    <h1>Dashboard for <xsl:value-of select="@name"/> feedback</h1>
    <p align="right">Run with <a href="http://jbrugge.com/glean">Glean</a> and <a href="http://ant.apache.org">Ant</a> on <xsl:value-of select="build/@time"/></p>
    <hr size="2" />
    <div id="docbar">
        <xsl:apply-templates select="//documentation"/>
    </div>
    <div id="metrics">
        <xsl:apply-templates select="//feedback"/>
    </div>
    </body>
    </html>
</xsl:template>

<xsl:template match="//feedback">
    <table class="summary" align="left">
        <caption><xsl:value-of select="@description"/></caption>
    <tr><th>Source</th>
        <xsl:for-each select="result">
            <td><a><xsl:attribute name="href"><xsl:value-of select="@source"/>/<xsl:value-of select="@indexpage"/></xsl:attribute><xsl:value-of select="@source"/></a></td>
        </xsl:for-each>
    </tr>
    <tr><th>Metric</th>
        <xsl:for-each select="result">
            <td><xsl:value-of select="@metric"/></td>
        </xsl:for-each>
    </tr>
    <tr><th>Measure</th>
        <xsl:for-each select="result">
            <td class="measure"><xsl:value-of select="@measurement"/></td>
        </xsl:for-each>
    </tr>
    <!--
        <tr>
        <th>Source</th>
        <th>Metric</th>
        <th>Measure</th>
        </tr>
        <xsl:apply-templates/>
    -->
    </table>
    <br clear="left"/>
</xsl:template>

<xsl:template match="result">
    <tr>
    <td><a><xsl:attribute name="href"><xsl:value-of select="@source"/>/<xsl:value-of select="@indexpage"/></xsl:attribute><xsl:value-of select="@source"/></a></td>
    <td><xsl:value-of select="@metric"/></td>
    <td class="measure"><xsl:value-of select="@measurement"/></td>
    </tr>
</xsl:template>

<xsl:template match="//documentation">
    <table class="summary" align="center">
        <caption><xsl:value-of select="@description"/></caption>
        <tr>
        <th>Description</th>
        <th>Source</th>
        </tr>
        <xsl:apply-templates/>
    </table>
</xsl:template>

<xsl:template match="//doc">
    <tr>
    <td><xsl:value-of select="@description"/></td>
    <td><a><xsl:attribute name="href"><xsl:value-of select="@source"/>/<xsl:value-of select="@indexpage"/></xsl:attribute><xsl:value-of select="@source"/></a></td>
    </tr>
</xsl:template>

</xsl:stylesheet>
