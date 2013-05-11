package com.goraksh.rest.auth;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class WebConnector {

    private String url;

    private String proxyServer;

    private String proxyPort;

    private static int READ_TIMEOUT = 500000;

    private static int CONNECT_TIMEOUT = 100000;

    private String ENCODING = "UTF-8";

    private HashMap<String, List<String>> responseHeaderMap;

    private HashMap<String, String> requestHeaderMap;

    private static final String multipartBoundary = "WebKitFormBoundaryX6nBO7q27yQ1JNbb";
    
    private static final String NEWLINE = System.getProperty("line.separator");
    
   // private static String CRLF = "\r"+ NEWLINE;
   
    
    /**
     * The Carriage Return ASCII character value.
     */
    public static final byte CR = 0x0D;


    /**
     * The Line Feed ASCII character value.
     */
    public static final byte LF = 0x0A;


    /**
     * The dash (-) ASCII character value.
     */
    public static final byte DASH = 0x2D;


    /**
     * The maximum length of <code>header-part</code> that will be
     * processed (10 kilobytes = 10240 bytes.).
     */
    public static final int HEADER_PART_SIZE_MAX = 10240;


    /**
     * The default length of the buffer used for processing a request.
     */
    protected static final int DEFAULT_BUFSIZE = 4096;


    /**
     * A byte sequence that marks the end of <code>header-part</code>
     * (<code>CRLFCRLF</code>).
     */
    protected static final byte[] HEADER_SEPARATOR = {
        CR, LF, CR, LF };


    /**
     * A byte sequence that that follows a delimiter that will be
     * followed by an encapsulation (<code>CRLF</code>).
     */
    protected static final byte[] FIELD_SEPARATOR = {
        CR, LF};
    
    private static String CRLF = null;
    static {
        try
        {
            CRLF = new String(FIELD_SEPARATOR, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public enum Http
    {
        GET, POST, PUT, DELETE
    }

    static class UserAgent
    {
        public static final String MOZILLA = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:7.0.1) Gecko/20100101 Firefox/7.0.12011-10-16 20:23:00";

        public static final String LOCAL = "Local test";
    }

    private String filename1 = "D:\\download\\export1.zip";

    private String filename = "D:\\svn_tree\\testfile.txt";
    private String filename_name = "export1.zip";

    public WebConnector(String url)
    {
        this.url = url;
        responseHeaderMap = new HashMap<String, List<String>>(8);
    }

    public WebConnector(String url, String filename)
    {
        this.url = url;
        this.filename = filename;
        responseHeaderMap = new HashMap<String, List<String>>(8);
    }

    public WebConnector(String url, String proxyServer, String proxyPort)
    {
        this(url);
        this.proxyPort = proxyPort;
        this.proxyServer = proxyServer;
    }

    /**
     * 
     * @return
     */
    private Proxy getProxy()
    {
        if (proxyPort == null || proxyServer == null)
        {
            return Proxy.NO_PROXY;
        }
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyServer, Integer.valueOf(proxyPort).intValue()));

        return proxy;
    }

    /**
     * 
     * @return
     * @throws IOException
     */
    private URLConnection getUrlConnection() throws IOException
    {
        Proxy proxy = getProxy();
        URL urlObj = new URL(this.url);
        URLConnection connection = urlObj.openConnection(proxy);
        return connection;
    }

    /**
     * 
     * @param paramMap
     * @return
     * @throws UnsupportedEncodingException
     */
    private String getUrlEncodedString(Map<String, Object> paramMap) throws UnsupportedEncodingException
    {
        if (paramMap == null || paramMap.size() == 0)
        {
            return null;
        }
        String ret = "";
        int i = 0;
        for (Entry<String, Object> en : paramMap.entrySet())
        {
            if (i == 0)
            {
                ret += en.getKey() + "=" + URLEncoder.encode(en.getValue().toString(), ENCODING);
                continue;
            }
            ret += "&" + en.getKey() + "=" + URLEncoder.encode(en.getValue().toString(), ENCODING);
        }
        return ret;
    }

    /**
     * 
     * @param connection
     */
    private void setRequestProps(URLConnection connection)
    {
        connection.setRequestProperty("User-Agent", UserAgent.MOZILLA);
        // connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        // connection.setRequestProperty("Content-Type", "multipart/form-data");
        connection.setRequestProperty("Content-Type", "application/xml");
       // connection.setRequestProperty("Content-Type", "application/xml; charset=ISO-8859-1");
        connection.setRequestProperty("Content-Language", "en-US");
        // connection.setRequestProperty("Content-Disposition", "attachment; filename=\"" + filename
        // + "\"");

        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
    }

    /**
     * 
     * @return
     */
    private byte[] getAttachmentBytes()
    {
        File file = new File(filename);
        BufferedReader bf = null;
        StringBuilder sb = null;
        try
        {
            bf = new BufferedReader(new FileReader(file));
            String line = null;
            sb = new StringBuilder();
            while ((line = bf.readLine()) != null)
            {
                sb.append(line);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return sb.toString().getBytes();

    }

    private byte[] getBoundaryMessage() throws IOException
    {
        String boundary = multipartBoundary;
        String fileType = "text/plain";
        String fileType1 = "application/zip";

        String boundaryMessage = getBoundaryMessage(boundary, "export1", filename, fileType);

       // String endBoundary = "\r\n--" + boundary + "\r\n";
        String endBoundary = CRLF + "--" + boundary + CRLF;
        //String endBoundary = "--" + boundary + CRLF;
        //endBoundary = endBoundary + "Content-Type: text/xml" + "\r\n\r\n";
        endBoundary = endBoundary + "Content-Type: text/xml" + CRLF;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bos.write(boundaryMessage.getBytes());

        bos.write(getAttachmentBytes());
        
       // bos.write(endBoundary.getBytes());
        //bos.write(CRLF.getBytes());

        // this.postBytes = bos.toByteArray();

        bos.close();
        return bos.toByteArray();
    }
    
    /**
     * 
     * @param boundary
     * @param fileField
     * @param fileName
     * @param fileType
     * @return
     */
    String getBoundaryMessage(String boundary, String fileField, String fileName, String fileType)
    {
        StringBuffer res = new StringBuffer(CRLF)
        .append( getMultipartDelimiter(boundary));
        res.append("Content-Disposition: form-data; name=\"").append(fileField).append("\"; filename=\"").append(fileName).append(
       
        "\"").append(CRLF).append("Content-Type: ").append(fileType).append( "; charset=UTF-8").append(CRLF)
        .append( "Content-Transfer-Encoding: binary")
        .append( CRLF);

        return res.toString();
    }

    String getBoundaryMessage0(String boundary, String fileField, String fileName, String fileType)
    {
        StringBuffer res = new StringBuffer(CRLF)
        .append( getMultipartDelimiter(boundary));//new StringBuffer(CRLF).append("--").append(boundary).append(CRLF);
        res.append("Content-Disposition: form-data; name=\"").append(fileField).append("\"; filename=\"").append(fileName).append(
         //   "\"\r\n").append("Content-Type: ").append(fileType).append("\r\n\r\n");
        "\"").append(CRLF).append("Content-Type: ").append(fileType).append( "; charset=UTF-8").append(CRLF)
        .append( "Content-Transfer-Encoding: binary")
        .append( CRLF);

        return res.toString();
    }
    
    /**
     * 
     * @param boundary
     * @return
     */
    private static String getMultipartDelimiter( String boundary ) {
    	 StringBuffer res = new StringBuffer(boundary).append(CRLF);
    	 return res.toString();
    }

    private byte[] writeContentDisposition(String input)
    {
        try
        {
            //String endBoundary = "\r\n--" + multipartBoundary + "--\r\n";
            String endBoundary =  CRLF + CRLF  + "--" + multipartBoundary + "--" ;
            byte[] message = getBoundaryMessage();
            
             ByteArrayOutputStream bos = new ByteArrayOutputStream();

            bos.write(message);

            //bos.write(input.getBytes());

           bos.write(endBoundary.getBytes());
            log( bos.toByteArray() );
            return bos.toByteArray();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    
    private void sanityCheck( byte[] message, int len ) {
        //ByteArrayInputStream in = new ByteArrayInputStream( message );
        //final int len = 219;
       // in.read(message, 0 , len );
        
        String s  = new String(message, 0, len);
        String s1  = new String(message, 149, 189-149);
        System.out.println("This len : " +  len + "  Length : " + message.length + " --" + s);
        System.out.println("Next--" + s1 );
    }

    
    /**
     * 
     * @param connection
     */
    private void setRequestPropsWithContentDisposition(URLConnection connection)
    {
        connection.setRequestProperty("User-Agent", UserAgent.MOZILLA);
      // connection.setRequestProperty("Accept", "multipart/form-data");
        // connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Type", "multipart/mixed; boundary=--" + multipartBoundary);
        // connection.setRequestProperty("Content-Type", "application/xml");
        connection.setRequestProperty("Content-Language", "en-US");
        // connection.setRequestProperty("Content-Disposition", "attachment; filename=\"" + filename
        // + "\"");

        connection.setConnectTimeout(CONNECT_TIMEOUT);
        connection.setReadTimeout(READ_TIMEOUT);
    }

    /**
     * 
     * @param connection
     */
    private void responseHeaderMap(URLConnection connection)
    {
        responseHeaderMap.clear();
        for (Entry<String, List<String>> header : connection.getHeaderFields().entrySet())
        {
            System.out.println(header.getKey() + "=" + header.getValue());

            this.responseHeaderMap.put(header.getKey(), header.getValue());
        }
    }

    /**
     * 
     * @return
     */
    public HashMap<String, List<String>> getResponseHeadermap()
    {
        return this.responseHeaderMap;
    }

    /**
     * 
     * @param connection
     * @return
     * @throws IOException
     */
    private String getResponse(HttpURLConnection connection) throws IOException
    {
        BufferedReader rd = null;
        // Get Response
        String contentType = connection.getHeaderField("Content-Type");
        String charset = "UTF-8";
        if (contentType != null)
        {
            for (String param : contentType.replace(" ", "").split(";"))
            {
                if (param.startsWith("charset="))
                {
                    charset = param.split("=", 2)[1];
                    break;
                }
            }
        }

        try
        {
            InputStream is = connection.getInputStream();
            rd = new BufferedReader(new InputStreamReader(is, charset));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = rd.readLine()) != null)
            {
                response.append(line);
                response.append('\r');
            }
            return response.toString();
        }
        finally
        {
            if (rd != null)
            {
                rd.close();
            }
        }
    }

    /**
     * 
     * @param hMap
     */
    public void setRequestHeaderMap(HashMap<String, String> hMap)
    {
        this.requestHeaderMap = hMap;
    }

    /**
     * 
     * @param method
     * @return
     * @throws IOException
     */
    private HttpURLConnection make(Http method) throws IOException
    {
        // Create connection
        HttpURLConnection connection = (HttpURLConnection) getUrlConnection();
        connection.setRequestMethod(method.name());
        return connection;
    }

    /**
     * 
     * @param connection
     * @throws IOException
     */
    private void connect(URLConnection connection) throws IOException
    {
        connection.connect();
    }

    /**
     * 
     * @param connection
     * @throws IOException
     */
    private void doCommonWithContentDisposition(URLConnection connection) throws IOException
    {
        setRequestPropsWithContentDisposition(connection);

        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
    }

    /**
     * 
     * @param connection
     * @throws IOException
     */
    private void doCommon(URLConnection connection) throws IOException
    {
        setRequestProps(connection);

        connection.setUseCaches(false);
        connection.setDoInput(true);
        connection.setDoOutput(true);
    }

    /**
     * 
     * @param name
     * @param val
     */
    public void setRequestHeaderProperty(String name, String val)
    {
        if (requestHeaderMap == null || requestHeaderMap.size() == 0)
        {
            requestHeaderMap = new HashMap<String, String>(6);
        }
        requestHeaderMap.put(name, val);
    }

    /**
     * 
     * @param name
     * @param val
     * @param connection
     */
    private void setHeader(String name, String val, URLConnection connection)
    {
        connection.addRequestProperty(name, val);
    }

    /**
     * 
     * @param connection
     */
    private void setAllRequestHeadderProperties(URLConnection connection)
    {
        if (requestHeaderMap == null || requestHeaderMap.size() == 0)
        {
            return;
        }
        for (Entry<String, String> entry : requestHeaderMap.entrySet())
        {
            setHeader(entry.getKey(), entry.getValue(), connection);
        }
    }

    /**
     * 
     * @return
     * @throws IOException
     */
    public String doDelete() throws IOException
    {
        return doGet0(Http.DELETE);
    }

    /**
     * 
     * @return
     * @throws IOException
     */
    public String doGet() throws IOException
    {
        return doGet0(Http.GET);
    }

    /**
     * 
     * @return
     * @throws IOException
     */
    private String doGet0(Http http) throws IOException
    {
        HttpURLConnection connection = null;
        try
        {
            connection = make(Http.GET);
            setAllRequestHeadderProperties(connection);
            doCommon(connection);
            connect(connection);
            responseHeaderMap(connection);

            String res = getResponse(connection);
            System.out.println("Got response :\n" + res);
            return res;

        }
        catch (Exception e)
        {

            e.printStackTrace();
            return null;
        }
        finally
        {
            if (connection != null)
            {
                connection.disconnect();
            }
        }
    }

    /**
     * 
     * @param paramMap
     * @throws IOException
     */
    public String doPost(Map<String, Object> paramMap) throws IOException
    {
        return doPost(getUrlEncodedString(paramMap));
    }

    /**
     * 
     * @param input
     * @param http
     * @return
     */
    private String doPost0(String input, Http http) throws IOException
    {
        HttpURLConnection connection = null;
        DataOutputStream wr = null;

        try
        {
            connection = make(http);
            setAllRequestHeadderProperties(connection);
            doCommon(connection);
            connect(connection);
            if (input != null)
            {
                wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(input);
                wr.flush();
                wr.close();
            }
            responseHeaderMap(connection);
            String res = getResponse(connection);
            System.out.println("Got response :\n" + res);
            return res;

        }
        catch (Exception e)
        {

            e.printStackTrace();
            return null;
        }
        finally
        {
            if (wr != null)
            {
                wr.close();
            }

            if (connection != null)
            {
                connection.disconnect();
            }
        }
    }
    
    private void log( byte[] log ) {
        File f = new File ("D:\\svn_tree\\a.txt");
        FileOutputStream out = null;
        try
        {
            out = new FileOutputStream( f );
            out.write( log );
            out.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
    }
    
    /**
     * 
     * @param input
     * @param http
     * @return
     */
    private String doPost1(String input, Http http) throws IOException
    {
        HttpURLConnection connection = null;
        DataOutputStream wr = null;

        try
        {
            connection = make(http);
            setAllRequestHeadderProperties(connection);
            doCommonWithContentDisposition(connection);
            byte[] bytesall = writeContentDisposition(input);
            connection.setRequestProperty("Content-Length",  String.valueOf( bytesall.length ) );
            // writeContentDisposition();
            sanityCheck( bytesall, 149 );
            connect(connection);
            if (input != null)
            {
                wr = new DataOutputStream(connection.getOutputStream());
                //byte[] bytesall = writeContentDisposition(input);
                // wr.writeBytes(input);
                wr.write(bytesall);
                wr.flush();
                wr.close();
            }
            responseHeaderMap(connection);
            String res = getResponse(connection);
            System.out.println("Got response :\n" + res);
            return res;

        }
        catch (Exception e)
        {

            e.printStackTrace();
            return null;
        }
        finally
        {
            if (wr != null)
            {
                wr.close();
            }

            if (connection != null)
            {
                connection.disconnect();
            }
        }
    }

    /**
     * 
     * @param input
     * @return
     * @throws IOException
     */
    public String doPost(String input) throws IOException
    {
        return doPost0(input, Http.POST);
    }

    /**
     * 
     * @param input
     * @return
     * @throws IOException
     */
    public String doMultipartPost(String input) throws IOException
    {
        return doPost1(input, Http.POST);
    }

    /**
     * 
     * @param input
     * @return
     * @throws IOException
     */
    public String doPut(String input) throws IOException
    {
        return doPost0(input, Http.PUT);
    }

    /**
     * 
     * @param input
     * @param sessionIdName
     * @return
     * @throws IOException
     */
    public String logout(String input) throws IOException
    {
        HttpURLConnection connection = null;
        DataOutputStream wr = null;

        try
        {
            connection = make(Http.DELETE);
            setAllRequestHeadderProperties(connection);
            doCommon(connection);

            if (input != null)
            {
                wr = new DataOutputStream(connection.getOutputStream());
                wr.writeBytes(input);
                wr.flush();
                wr.close();
            }

            responseHeaderMap(connection);

            String res = getResponse(connection);
            System.out.println("Got response :\n" + res);
            return res;

        }
        catch (Exception e)
        {

            e.printStackTrace();
            return null;
        }
        finally
        {
            if (wr != null)
            {
                wr.close();
            }

            if (connection != null)
            {
                connection.disconnect();
            }
        }
    }

    public void testClusterDelete() throws IOException
    {
        String ps = "10.10.56.10";
        String pp = "800";

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("userName", "nitesh_kb1");
        param.put("password", "");
        param.put("userType", "3");

        String host1 = "ggns32";
        String host = "ggnv48q";
        String loginUser = "nitesh_kb1";
        String loginPwd = "";
        String userType = "3";
        String clusterid = "1000000002";
        String caseId = "1000000022";

        String input = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ns3:Login xmlns:ns2=\"http://egain/ws/model/v3/gen/common\" xmlns=\"http://egain/ws/model/v3/gen/kb\" xmlns:ns3=\"egain/ws/model/v3/xsd/kb/login.xsd\"><userName>" +
            loginUser + "</userName><password>" + loginPwd + "</password><userType>" + userType + "</userType></ns3:Login>";

        String urlbase = "http://" + host + ":9001/system/ws/v11/kb";
           String urlCase = urlbase + "/case/" + caseId;
        String urlLogin = urlbase + "/authenticate/login";
        String urlLogout = urlbase + "/authenticate/logout";

        /* ********** Login ************************************************* */
        WebConnector webconn = new WebConnector(urlLogin);
        String res = webconn.doPost(input);
        System.out.println(res);

        HashMap<String, List<String>> responseHMap = webconn.getResponseHeadermap();

        HashMap<String, String> requestHMap = new HashMap<String, String>();
        List<String> list = responseHMap.get("X-egain-session");
        if (list != null && list.size() > 0)
        {
            requestHMap.put("X-egain-session", list.get(0));
        }

        /* ***************** delete *************************************** */

        String urlDelete = urlbase + "/cluster/" + clusterid.trim();
        WebConnector webconnDel = new WebConnector(urlDelete);
        webconnDel.setRequestHeaderMap(requestHMap);
        String res1 = webconnDel.doDelete();
        System.out.println(res1);

        /* ****************** get ****************************************** */
        /*
         * String urlGet = urlbase + "/cluster/" + clusterid.trim(); WebConnector webconnGet = new
         * WebConnector(urlGet); webconnGet.setRequestHeaderMap(requestHMap); String res3 =
         * webconnGet.doGet(); System.out.println(res3);
         */

        /* ************** post-AnswerSet ************************************* */

        /* ************** Logout ************************************** */

        WebConnector webconnLogout = new WebConnector(urlLogout);
        webconnLogout.setRequestHeaderMap(requestHMap);
        String res2 = webconnLogout.logout(null);
        System.out.println(res2);
    }

    private String readXml(String filename) throws IOException
    {
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new FileReader(new File(filename)));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null)
            {
                line = line.trim();
                if ("".equals(line))
                    continue;
                sb.append(line);
            }
            return sb.toString();
        }
        finally
        {
            if (br != null)
            {
                br.close();
            }
        }
    }

      public static void main(String[] args) throws IOException
    {
          /* ********** Login ************************************************* */
    	  String url = "http://localhost:8080/abc";
    	  String input = "a=aa&b=bb";
          WebConnector webconn = new WebConnector(url);
          String res = webconn.doPost(input);
    }

}
