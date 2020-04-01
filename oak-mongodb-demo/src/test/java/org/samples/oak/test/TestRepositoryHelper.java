package org.samples.oak.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import javax.jcr.LoginException;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.junit.Before;
import org.junit.Test;
import org.samples.oak.demo.bean.AssetDetail;
import org.samples.oak.demo.bean.FileResponse;
import org.samples.oak.demo.bean.VersionHistory;
import org.samples.oak.demo.service.RepoHistoryHelper;
import org.samples.oak.demo.service.RepositoryBuilder;
import org.samples.oak.demo.service.RepositoryHelper;

public class TestRepositoryHelper {
    private static Repository repo;

    @Test
    public void shouldEditFile() {
        try {
            Session session = getSession();
            RepositoryHelper.editFile(session, "/testNode/deal/subdeal", new File("ChildClass.java"), "bishnu");
            System.out.println("File Edit Complete");
            cleanUp(session); // do this in finally
        } catch (RepositoryException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetAssets() {
        String basePath = "/testNode/deal/subdeal";
        try {
            Session session = getSession();
            System.out.println("Starting the asset fetch...");
            List<AssetDetail> assets = RepositoryHelper.getAssets(session, basePath);
            System.out.println(assets.size());
            for (AssetDetail assetDetail : assets) {
                System.out.println(assetDetail);
            }
            cleanUp(session); // do this in finally
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetVersionHistory() {
        try {
            Session session = getSession();
            List<VersionHistory> versions = RepoHistoryHelper.getVersionHistory(session, "/testNode/deal/subdeal",
                    "ChildClass.java");
            for (VersionHistory versionHistory : versions) {
                System.out.println(versionHistory);
            }
            cleanUp(session); // do this in finally
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRestoreVersion() {
        try {
            Session session = getSession();
            RepoHistoryHelper.restoreVersion(session, "56d9b193-7a4b-4c69-88db-20d29633d822");
            cleanUp(session); // do this in finally
        } catch (RepositoryException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldDeleteNode() {
        try {
            Session session = getSession();
            RepoHistoryHelper.deleteVersionHistories(session, "/testNode/deal/subdeal", "ChildClass.java");
            RepositoryHelper.deleteNode(session, "/testNode/deal/subdeal", "ChildClass.java");
            cleanUp(session); // do this in finally
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testGetFileContent() {
        try {
            Session session = getSession();
            System.out.println("Fething the file...");
            FileResponse fileResponse = RepositoryHelper.getFileContents(session, "/Main1",
                    "Main.java");
            byte[] content = fileResponse.getBytes();
            if (content != null && content.length > 0) {
                FileOutputStream fos = new FileOutputStream("/home/nasir/Downloads/SETUP/" + "Main11.java");
                fos.write(content);
                fos.close();
                System.out.println("File fetch complete...");
            }
            cleanUp(session); // do this in finally
        } catch (RepositoryException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void shoudAddFile() {
        try {
            System.out.println("Adding the file...");
            Session session = getSession();

//            RepositoryHelper.addFileNode(session, "/testNode/deal/subdeal", new File("ChildClass.java"), "admin");

            RepositoryHelper.addFileNode(session, "/Main1", new File("/nasir-data/jackrabbit/test-files/Main.java"), "admin");
            
            System.out.println("Files added...");
            cleanUp(session); // do this in finally
        } catch (RepositoryException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldCreateNodes() {
        try {
            Session session = getSession();
            RepositoryHelper.createNodes(session, "/testNode/deal3");
            System.out.println("Node created...");
            cleanUp(session); // do this in finally
        } catch (RepositoryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void getParagraphInfo() {
    	
    	try {
    		QueryManager queryManager = getSession().getWorkspace().getQueryManager();
			
//			String expression = "SELECT * FROM [nt:file]";
//    		String expression = "SELECT * FROM [nt:file] As node WHERE contains(node.*, 'Measures')";

//    		String expression = "SELECT * FROM [nt:file] As node WHERE CONTAINS(*,'is')";
    		
//    		String expression = "SELECT * FROM [nt:resource] As node WHERE CONTAINS(node.*,'Measures associated')";
    		
    		
//    		 Query query = queryManager.createQuery("select excerpt(.) from nt:resource where contains(., 'Measures')", Query.SQL);

    		
    		String expression = "SELECT * FROM [nt:unstructured] as node WHERE CONTAINS(node.*,'Measures')";
    		
			 Query query = queryManager.createQuery(expression,Query.JCR_SQL2);
			
			 QueryResult result = query.execute();
			 
//			 for (RowIterator it = result.getRows(); it.hasNext(); ) {
//				    Row r = it.nextRow();
//				    Value excerpt = r.getValue("rep:excerpt(.)");
//				    System.err.println(excerpt);
//				}
			 
			 NodeIterator nodeIter = result.getNodes();
			 
			 while ( nodeIter.hasNext() ) {
             
			    javax.jcr.Node node = nodeIter.nextNode();
			    System.out.println("node");
			    System.out.println(node.getName());
			    System.out.println(node.getPath());

			}
			 
//			 String[] columnNames = result.getColumnNames();

//			 javax.jcr.query.RowIterator rowIter = result.getRows();
//
//			 
//			 while ( rowIter.hasNext() ) {
//
//				    javax.jcr.query.Row row = rowIter.nextRow();
//
//				    // Iterate over the column values in each row ...
//
//				    javax.jcr.Value[] values = row.getValues();
//
//				    for ( javax.jcr.Value value : values ) {
//			    		System.out.println("----- value -----");
//			    		System.out.println(value);
//				    }
//
//				    // Or access the column values by name ...
//
//				    for ( String columnName : columnNames ) {
//
//				        javax.jcr.Value value = row.getValue(columnName);
//
//				        System.out.println("columName");
//				        System.out.println(value);
//				    }
//
//				}
			 
			 
			 
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    

	private Session getSession() throws LoginException, RepositoryException {
        if (repo != null)
            return repo.login(new SimpleCredentials("admin", "admin".toCharArray()));
        else
            throw new NullPointerException("Repository not initialized");
    }

    @Before
    public void doSetup() {
        try {
            repo = RepositoryBuilder.getRepo("localhost", 27017);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void cleanUp(Session session) {
        if (session != null) {
            session.logout();
        }
    }
}
