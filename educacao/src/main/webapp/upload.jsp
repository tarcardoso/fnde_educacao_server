<%@page import="java.sql.*, javax.sql.*, javax.naming.*"%>
<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.output.*" %>

<%!
public void atualizaTimeLine(Context context, String filePath, String idPost, String fileName){
	DataSource ds = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSetMetaData rsmd = null;
    try{
      Context envCtx = (Context) context.lookup("java:comp/env");
      ds =  (DataSource)envCtx.lookup("jdbc/educacaoDS");
      if (ds != null) {
	        conn = ds.getConnection();
	        stmt = conn.createStatement();
	        
	        File file = new File(filePath+"/"+fileName);
	        String novo = idPost+"_"+fileName;
	        File file2 = new File( filePath+"/"+novo );
	        //if(file2.exists()) throw new java.io.IOException("file exists");
	        boolean success = file.renameTo(file2);
	        if (!success) {
	           	System.out.println(">>>>SUCESSO<<<<");
	        }
	        
	        int qtd = stmt.executeUpdate("UPDATE timeline set tximagem= '"+novo+"' WHERE idtimeline = "+idPost );
	        System.out.println("qtd: "+qtd );
       }
     }
     catch (Exception e) {
    	 e.printStackTrace();
        System.out.println("Error occurred " + e);
     }finally{
    	 try{
         	stmt.close();
         	conn.close();
    	 }catch (SQLException e) {
 	        System.out.println("Error occurred " + e);
 	     }
     }
}
%>
<%
   File file ;
   int maxFileSize = 5000 * 1024;
   int maxMemSize = 5000 * 1024;
   ServletContext context = pageContext.getServletContext();
   String filePath = getServletContext().getRealPath("/imagens/");

   // Verify the content type
   String contentType = request.getContentType();
   if ((contentType.indexOf("multipart/form-data") >= 0)) {

      DiskFileItemFactory factory = new DiskFileItemFactory();
      // maximum size that will be stored in memory
      factory.setSizeThreshold(maxMemSize);
      // Location to save data that is larger than maxMemSize.
      String path = getServletContext().getRealPath("/imagens/");
      factory.setRepository(new File( path ));

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
      
      // maximum file size to be uploaded.
      upload.setSizeMax( maxFileSize );
      try{ 
         // Parse the request to get file items.
         List fileItems = upload.parseRequest(request);

         // Process the uploaded file items
         Iterator i = fileItems.iterator();
         
         out.println("<html>");
         out.println("<head>");
         out.println("<title>JSP File upload</title>");  
         out.println("</head>");
         out.println("<body>");
         String idPost = "";
         String fileName = "";

         while ( i.hasNext () ) {
            FileItem fi = (FileItem)i.next();
            if ( !fi.isFormField () )	{
	            // Get the uploaded file parameters
	            String fieldName = fi.getFieldName();
	            fileName = fi.getName();
	            /* int pos = fileName.lastIndexOf(".");
	            if( pos > 0 ){
	            	fileName = idPost+"_evidencia"+fileName.substring(pos, fileName.length() );
	            } */
	            
	            boolean isInMemory = fi.isInMemory();
	            long sizeInBytes = fi.getSize();
	            // Write the file
	            if( fileName.lastIndexOf("\\") >= 0 ){
		            file = new File( filePath +"/"+ 
		            	fileName.substring( fileName.lastIndexOf("\\"))) ;
	            }else{
		            file = new File( filePath +"/"+ 
		            	fileName.substring(fileName.lastIndexOf("\\")+1)) ;
	            }
	            fi.write( file ) ;
	            out.println("Uploaded Filename: " + filePath + "/"+
	            fileName + "<br>");
            }else{
            	String fieldName = fi.getFieldName();
                String fieldValue = fi.getString();
                System.out.println(fieldName+": "+ fieldValue);
                if( "idPost".equals(fieldName)){
                	idPost = fieldValue;
                }
            }
         }
         
         atualizaTimeLine( new InitialContext(), filePath, idPost, fileName );
         
         out.println("</body>");
         out.println("</html>");
      }catch(Exception ex) {
    	  ex.printStackTrace();
          System.out.println(ex);
      }
   }else{
      out.println("<html>");
      out.println("<head>");
      out.println("<title>Servlet upload</title>");  
      out.println("</head>");
      out.println("<body>");
      out.println("<p>No file uploaded</p>"); 
      out.println("</body>");
      out.println("</html>");
   }
%>