package org.proffart.pan.fileManager;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.imgscalr.Scalr;
import org.proffart.pan.DbManager;
import org.proffart.pan.User;

import com.google.gson.Gson;


public class FileUpload extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private File fileUploadPath;
    private String storagePath;
    private String path;
    private String className;
    private HashMap<String, Object> params;
    private ServletConfig config;

    @Override
    public void init(ServletConfig localConfig) {
    	config = localConfig;
    }
    public static class ObjectFile {
		public int id = 0;
		public String name ="";
		public String mimeType ="";
		public long size = 0;
		public String path = "";
		public String fileName = "";
	}
    public static class FileList {
    	public int from = 0;
    	public int limit = 0;
    	public int maxRowCount = 0;
    	public ObjectFile[] files = null;
    }
    private static class UploadResult {
    	public ObjectFile[] files;
    }
    /**
        * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
        * 
        */
    @SuppressWarnings("unchecked")
	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String action = ""; 
    	String paramsString = "";
    	Gson gson = new Gson(); 
    	if(request.getParameter("class") != null)
			className = request.getParameter("class");
    	className = "org.proffart.pan." + className;
    	if(request.getParameter("action") != null)
    		action = request.getParameter("action");
    	if(request.getParameter("params") != null)
    		paramsString = request.getParameter("params");
    	if(!paramsString.isEmpty()){
    		params = gson.fromJson(paramsString,HashMap.class);
    	}
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
    	switch (action) {
    	case "delete" :
    		String delResult = "";
    		try{
    			int fileID = Integer.parseInt(request.getParameter("file_id"));
    			callBackDel(request, fileID);
    			String sql = "SELECT `name` FROM `file` WHERE id = "+fileID;
    			DbManager db = DbManager.getInstance();
    			HashMap<String, String> tmp = db.getRow(sql);
    			delResult = "{\"files\":[{\""+tmp.get("name")+"\": true}]}";
    		}catch( Exception e ){
    			//ss
    		}finally{
	            writer.write(gson.toJson(delResult));
	            writer.close();
			}
		default:
			int from = 0;
			int limit = 0;
			if(request.getParameter("from") != null)
				from = Integer.parseInt(request.getParameter("from"));
			if(request.getParameter("limit") != null)
				from = Integer.parseInt(request.getParameter("limit"));
			FileList result = new FileList(); 
			try{
				Class<?> dynClass = Class.forName(className);
		    	Constructor<?> ctor = dynClass.getConstructor(HttpServletRequest.class);
		    	Object obj = ctor.newInstance(request);
		    	Method fileGetLst = Class.forName(className).getMethod("fileGetLst",int.class,int.class,HashMap.class);
		    	result = (FileList) fileGetLst.invoke(obj, from, limit, params);
			}catch( Exception e ){
				//
			}finally{
	            writer.write(gson.toJson(result));
	            writer.close();
			}
			break;
		}
/*
        
        if (request.getParameter("getfile") != null && !request.getParameter("getfile").isEmpty()) {
            File file = new File(fileUploadPath,
                    request.getParameter("getfile"));
            if (file.exists()) {
                int bytes = 0;
                ServletOutputStream op = response.getOutputStream();

                response.setContentType(getMimeType(file));
                response.setContentLength((int) file.length());
                response.setHeader( "Content-Disposition", "inline; filename=\"" + file.getName() + "\"" );

                byte[] bbuf = new byte[1024];
                DataInputStream in = new DataInputStream(new FileInputStream(file));

                while ((in != null) && ((bytes = in.read(bbuf)) != -1)) {
                    op.write(bbuf, 0, bytes);
                }

                in.close();
                op.flush();
                op.close();
            }
        } else if (request.getParameter("delfile") != null && !request.getParameter("delfile").isEmpty()) {
            File file = new File(fileUploadPath, request.getParameter("delfile"));
            if (file.exists()) {
                file.delete(); // TODO:check and report success
            } 
        } else if (request.getParameter("getthumb") != null && !request.getParameter("getthumb").isEmpty()) {
            File file = new File(fileUploadPath, request.getParameter("getthumb"));
                if (file.exists()) {
                    String mimetype = getMimeType(file);
                    if (mimetype.endsWith("png") || mimetype.endsWith("jpeg") || mimetype.endsWith("gif")) {
                        BufferedImage im = ImageIO.read(file);
                        if (im != null) {
                            BufferedImage thumb = Scalr.resize(im, 75); 
                            ByteArrayOutputStream os = new ByteArrayOutputStream();
                            if (mimetype.endsWith("png")) {
                                ImageIO.write(thumb, "PNG" , os);
                                response.setContentType("image/png");
                            } else if (mimetype.endsWith("jpeg")) {
                                ImageIO.write(thumb, "jpg" , os);
                                response.setContentType("image/jpeg");
                            } else {
                                ImageIO.write(thumb, "GIF" , os);
                                response.setContentType("image/gif");
                            }
                            ServletOutputStream srvos = response.getOutputStream();
                            response.setContentLength(os.size());
                            response.setHeader( "Content-Disposition", "inline; filename=\"" + file.getName() + "\"" );
                            os.writeTo(srvos);
                            srvos.flush();
                            srvos.close();
                        }
                    }
            } // TODO: check and report success
        } else {
            PrintWriter writer = response.getWriter();
            writer.write("call POST with multipart form data");
        }*/
    }
    
    /**
        * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
        * 
        */
    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if(request.getParameter("class") != null)
			className = request.getParameter("class");
    	className = "org.proffart.pan." + className;
    	String paramsString = "";
    	Gson gson = new Gson();
    	if(request.getParameter("params") != null)
    		paramsString = request.getParameter("params");
    	if(!paramsString.isEmpty()){
    		params = gson.fromJson(paramsString,HashMap.class);
    	}
    	
        if (!ServletFileUpload.isMultipartContent(request)) {
            throw new IllegalArgumentException("Request is not multipart, please 'multipart/form-data' enctype for your form.");
        }
        try {
			getConfig(request);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

        ServletFileUpload uploadHandler = new ServletFileUpload(new DiskFileItemFactory());
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        UploadResult uploadResult = new UploadResult();
        try {
            List<FileItem> items = uploadHandler.parseRequest(request);
            ObjectFile objectItem = null;
            for (FileItem item : items) {
                if (!item.isFormField()) {
                	objectItem = new ObjectFile();
                	objectItem.name = item.getName();
                	objectItem.size = item.getSize();
                	//String filename = FilenameUtils.getName(item.getName());
                	//objectItem.mimeType = request.getServletContext().getMimeType(filename);
                	objectItem.mimeType = "image/jpg";
                	String ex = objectItem.mimeType.replaceAll("(.*)/", ".");
                	String tmpName = UUID.randomUUID().toString();
                	tmpName += ex;
                    File file = new File(fileUploadPath, tmpName);
                    item.write(file);
                    HashMap<String, Object> insert = new HashMap<String, Object>();
                    int userID = User.getID(request);
                    objectItem.fileName = tmpName;
                    objectItem.path = path;
                    
                    insert.put("user_id", userID);
                    insert.put("path", objectItem.path);
                    insert.put("file_name", objectItem.fileName);
                    insert.put("mime_type", objectItem.mimeType);
                    insert.put("name", objectItem.name);
                    insert.put("creation_date", new java.sql.Date(System.currentTimeMillis()));
                    insert.put("size", objectItem.size);
                    
                    DbManager db = DbManager.getInstance();
                    objectItem.id = db.insert("file", insert);
                   
                    /*
                    jsono.addProperty("name", item.getName());
                    jsono.addProperty("size", item.getSize());
                    jsono.addProperty("url", "upload?getfile=" + item.getName());
                    jsono.addProperty("thumbnail_url", "upload?getthumb=" + item.getName());
                    jsono.addProperty("delete_url", "upload?delfile=" + item.getName());
                    jsono.addProperty("delete_type", "GET");
                    */
                        
                }
            }
            if(objectItem != null) {
            	callBackAdd(request, objectItem);
            	uploadResult.files = new ObjectFile[1];
            	uploadResult.files[0] = objectItem;
            }
        } catch (FileUploadException e) {
                throw new RuntimeException(e);
        } catch (Exception e) {
                throw new RuntimeException(e);
        } finally {
        	writer.write(gson.toJson(uploadResult));
            writer.close();
        }

    }
    private void getConfig(HttpServletRequest request) throws Exception {
    	storagePath = config.getInitParameter("storage_path");
    	Class<?> dynClass = Class.forName(className);
    	Constructor<?> ctor = dynClass.getConstructor(HttpServletRequest.class);
    	Object obj = ctor.newInstance(request);
    	Method fileGetPath = Class.forName(className).getMethod("fileGetPath",new Class[] {});
		path = (String) fileGetPath.invoke(obj, new Object[] {});
		storagePath += path;
		fileUploadPath = new File(storagePath);
    }
    private void callBackAdd(HttpServletRequest request, ObjectFile file ) throws Exception {
    	Class<?> dynClass = Class.forName(className);
    	Constructor<?> ctor = dynClass.getConstructor(HttpServletRequest.class);
    	Object obj = ctor.newInstance(request);
		Method fileAdd = Class.forName(className).getMethod("fileAdd", ObjectFile.class, HashMap.class);
		fileAdd.invoke(obj,file,params);
    }
    private void callBackDel(HttpServletRequest request, int fileID ) throws Exception {
    	Class<?> dynClass = Class.forName(className);
    	Constructor<?> ctor = dynClass.getConstructor(HttpServletRequest.class);
    	Object obj = ctor.newInstance(request);
		Method fileDel = Class.forName(className).getMethod("fileDel", int.class, HashMap.class);
		fileDel.invoke(obj,fileID,params);
    }
    
}