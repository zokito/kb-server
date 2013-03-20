package com.itagenten.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Before;
import org.junit.Test;

public class ITCore0Test {
    private SolrServer server;

    @Before
    public void init() {
        String url = "http://localhost:9090/solr/core0";
        server = new HttpSolrServer(url);
    }

    @Test
    public void testIndexNotEmpty() throws SolrServerException {
        System.out.println("dfkdposfpsdfjdpsg");
        SolrQuery query = new SolrQuery();
        query.setQuery("*:*");
        QueryResponse qr = server.query(query);
        SolrDocumentList sdl = qr.getResults();
        assertNotNull("Results (SolrDocumentList) from Core 0 should not be null.", sdl);
        assertTrue("Core 0 should not be empty.", sdl.getNumFound() > 0);
    }
}