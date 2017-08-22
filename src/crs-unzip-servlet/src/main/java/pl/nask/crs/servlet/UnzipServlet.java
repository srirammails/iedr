package pl.nask.crs.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.zip.*;
import java.net.URL;

public class UnzipServlet extends HttpServlet {

    private final static String COMPRESSIONED_RESOURCE_POSTFIX = ".gz";

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String resourcePath = getResourcePath(context, request);         
        if (isResourceExists(context, resourcePath)) {
            setMimeType(context, resourcePath, response);
            InputStream inputStream = context.getResourceAsStream(resourcePath);
            writeInputStreamToResponse(inputStream, response);
        } else {
            String compressedResourcePath = getCompressedResourcePath(resourcePath);
            if (isResourceExists(context, compressedResourcePath)) {
                setMimeType(context, resourcePath, response);
                unzipFile(context, compressedResourcePath, response);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    private String getResourcePath(ServletContext context, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String contextPath = context.getContextPath();
        return requestURI.replace(contextPath, "");
    }

    private boolean isResourceExists(ServletContext context, String resourcePath) throws IOException {
        URL url = context.getResource(resourcePath);
        return url != null;
    }

    private void setMimeType(ServletContext context, String resourcePath, HttpServletResponse response) {
        String mimeType = context.getMimeType(resourcePath);
        if (mimeType == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.setContentType(mimeType);
    }

    private void writeInputStreamToResponse(InputStream inputStream, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] buf = new byte[1024];
        int count;
        while ((count = inputStream.read(buf)) >= 0) {
            outputStream.write(buf, 0, count);
        }
        inputStream.close();
        outputStream.close();
    }
    
    private String getCompressedResourcePath(String resourcePath) {
        return resourcePath + COMPRESSIONED_RESOURCE_POSTFIX;
    }

    private void unzipFile(ServletContext context, String path, HttpServletResponse response) throws IOException {
        GZIPInputStream inputStream = new GZIPInputStream(context.getResourceAsStream(path));
        writeInputStreamToResponse(inputStream, response);
    }
}