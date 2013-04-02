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
import java.util.ArrayList;
import java.util.List;

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
        System.out.println(new java.io.File( "." ).getCanonicalPath());
        SolrQuery query = new SolrQuery();
        SolrDocumentList list = new SolrDocumentList();
        ArrayList<SolrInputDocument> inputList = new ArrayList<SolrInputDocument>();
        CSVParser parser = new CSVParser(new FileReader("./target/classes/faq_extract.csv"));
        String[][] inputfile =  parser.getAllValues();
        System.out.println(inputfile.length);
        for (int i = 1; i < inputfile.length; i++) {
            SolrInputDocument doc = new SolrInputDocument();
            for (int j = 0; j < inputfile[i].length; j++) {
                doc.addField(inputfile[0][j], inputfile[i][j]);
            }
          //  System.out.println(doc);
            inputList.add(doc);
            //System.out.println(inputfile[i]);

        }
        server.add(inputList, 0);
        server.commit();
      /*  SolrInputDocument doc = new SolrInputDocument();
        server.
        doc.*/

        query.setQuery("*:*");
        QueryResponse qr = server.query(query);
        SolrDocumentList sdl = qr.getResults();

        assertNotNull("Results (SolrDocumentList) from Core 0 should not be null.", sdl);

        assertTrue("Core 0 should not be empty.", sdl.getNumFound() != 0);
    }
}