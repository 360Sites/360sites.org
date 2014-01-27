package org.proffart.pan.fileManager;

//Import required java libraries
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;


/**
 * 
 * @author Karen
 * @version 1.00
 * @category Files
 * 
 * Servlet for uploading files to server
 */
public class FileUpload extends HttpServlet {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String UPLOAD_DIRECTORY = "E:/uploads";
	static Logger LOG = Logger.getLogger(FileUpload.class);

   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
 LOG.info("Uplading file...");
       //process only if its multipart content
       if(ServletFileUpload.isMultipartContent(request)){
           try {
               List<FileItem> multiparts = new ServletFileUpload(
                                        new DiskFileItemFactory()).parseRequest(request);
             
               for(FileItem item : multiparts){
                   if(!item.isFormField()){
                       String name = new File(item.getName()).getName();
                       item.write( new File(UPLOAD_DIRECTORY + File.separator + name));
                   }
               }
          
              //File uploaded successfully
              request.setAttribute("message", "File Uploaded Successfully");
              LOG.info("File Uploaded Successfully");
           } catch (Exception ex) {
        	   
        	  LOG.error("File Upload Failed due to " + ex); 
              request.setAttribute("message", "File Upload Failed due to " + ex);
           }          
        
       }else{
    	   LOG.error("This Servlet only handles file upload request"); 
           request.setAttribute("message",
                                "Sorry this Servlet only handles file upload request");
       }
   
       request.getRequestDispatcher("/").forward(request, response);
    
   }
}
