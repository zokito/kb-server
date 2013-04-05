package com.cloudsense.kcs.solr.tests;

import org.apache.solr.SolrTestCaseJ4;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.SolrParams;
import org.apache.solr.util.AbstractSolrTestCase;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


/**
 * Created with IntelliJ IDEA.
 * User: zoran.zunko
 * Date: 03.04.13.
 * Time: 12:18
 * To change this template use File | Settings | File Templates.
 */
public class FaqLoaderTest extends AbstractSolrTestCase {

    private SolrServer server;
    private Properties solrProperties;

    @Override
    public String getSolrHome() {
        return getSolrProperties().getProperty("solr.solr.home");
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        SolrTestCaseJ4.initCore("solr.xml","schema.xml", getSolrProperties().getProperty("solr.solr.home"),getSolrProperties().getProperty("solr.default.core.name"));
        //h = new TestHarness( //TestHarness.createConfig(getSolrProperties().getProperty("solr.solr.home"), getSolrProperties().getProperty("solr.solr.home"))
        server = new EmbeddedSolrServer(h.getCoreContainer(), h.getCore().getName());
    }

    @Test
    public void testThatNoResultsAreReturned() throws SolrServerException {
        SolrParams params = new SolrQuery("text that is not found");
        QueryResponse response = server.query(params);
        assertEquals(0L, response.getResults().getNumFound());
    }

    @Test
    public void testThatDocumentIsFound() throws SolrServerException, IOException {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", "1");
        document.addField("name", "my name");

        server.add(document);
        server.commit();

        SolrParams params = new SolrQuery("name");
        QueryResponse response = server.query(params);
        assertEquals(1L, response.getResults().getNumFound());
        assertEquals("1", response.getResults().get(0).get("id"));
    }

    public Properties getSolrProperties() {
        if(solrProperties == null)
        {
            solrProperties =  new Properties();
            try {
                //load a properties file from class path, inside static method
                solrProperties.load(FaqLoaderTest.class.getClassLoader().getResourceAsStream("config.properties"));
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return solrProperties;
    }
}