package com.itagenten.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.internal.csv.CSVParser;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ITCore0 {
    private SolrServer server;

    @Before
    public void init() {
        String url = "http://localhost:8080/solr/";
        server = new HttpSolrServer(url);
    }

    @Test
    public void testIndexNotEmpty() throws SolrServerException, IOException {
        System.out.println("dfkdposfpsdfjdpsg");
        SolrQuery query = new SolrQuery();
        SolrDocumentList list = new SolrDocumentList();
        CSVParser parser = new CSVParser(new FileReader("faq_extract.csv"));
        String[][] response =  parser.getAllValues();
        SolrInputDocument doc = new SolrInputDocument();

        query.setQuery("*:*");
        QueryResponse qr = server.query(query);
        SolrDocumentList sdl = qr.getResults();
        System.out.println(sdl.getNumFound() + "-----------------------------------");
        assertNotNull("Results (SolrDocumentList) from Core 0 should not be null.", sdl);

        assertTrue("Core 0 should not be empty.", sdl.getNumFound() == 0);
    }
}