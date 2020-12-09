package com.ruoyi.common.utils.file;

import com.ruoyi.project.need.mapper.NeedMapper;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 从FTP服务器上传下载文件的公共类
 */
@Service
public class FTPUtils {

    private static String ip;

    private static int port;

    private static String username;

    private static String password;

    private static String localUrl;

    @Autowired
    NeedMapper needMapper;

    /**
     * 初始化方法
     * 功能：读取配置文件中的内容，初始化工具类的值
     */
    private static void init() {
        PropertiesReadUtils readUtils = PropertiesReadUtils.getInstance();
        ip = readUtils.getValue("ftp.ip");
        String portStr = readUtils.getValue("ftp.port");
        port = Integer.parseInt(portStr);
        username = readUtils.getValue("ftp.username");
        password = readUtils.getValue("ftp.password");
        localUrl = readUtils.getValue("local.url");
    }

    /**
     * 连接FTP服务器方法
     * 功能：创建连接、登录FTP服务器
     */
    private static FTPClient connect() {
        init();
        FTPClient client = null;
        try {
            client = new FTPClient();
            client.setControlEncoding("UTF-8");
            client.connect(ip, port);
            client.login(username, password);
            client.enterLocalPassiveMode();
            client.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return client;
    }

    /**
     * 多文件上传接口
     * 功能：把文件数组上传至FTP服务器
     *
     * @param files 从前端获取的文件数组
     * @param ftpPath 希望上传到FTP服务器的路径
     *
     * @return 是否成功上传至FTP服务器
     */
    public static boolean upload(MultipartFile[] files, String ftpPath) {
        FTPClient client = null;
        FileInputStream fis = null;
        boolean success = false;
        try {
            client = connect();
            client.changeWorkingDirectory("/");
            String[] dirs = ftpPath.split("/");
            for (String dir : dirs) {
                if ("".equals(dir)) continue;
                boolean hasJump = client.changeWorkingDirectory(dir);
                if (!hasJump) {
                    client.makeDirectory(dir);
                    client.changeWorkingDirectory(dir);
                }
            }
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                if (fileName != null && !"".equals(fileName)) {
                    int index = fileName.indexOf(".");
                    String prefix = fileName.substring(0, index);
                    String suffix = fileName.substring(index);
                    String localPath = localUrl + "/" + prefix + System.nanoTime() + suffix;
                    File fileLocal = new File(localPath);
                    if (fileLocal.exists()) fileLocal.delete();
                    file.transferTo(fileLocal);
                    fis = new FileInputStream(fileLocal);
                    success = client.storeFile(fileName, fis);
                    if (success) {
                        fis.close();
                        fileLocal.delete();
                    }
                }
            }
        } catch (IOException | IllegalStateException e) {
            e.printStackTrace();
        } finally {
            close(fis, client);
        }
        return success;
    }

    public static void download(String remoteName, String downloadName, HttpServletResponse response) throws IOException {
        String path = "/home/webapp/workload/excels/download/";
//        String path = "D:\\Work\\Workload System\\Upload Files\\download";
        Path file = Paths.get(path, remoteName);
        response.setContentType("application/x-gzip");
        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(downloadName, "UTF-8"));
        Files.copy(file, response.getOutputStream());
    }

//    /**
//     * 文件下载接口
//     * 功能：把FTP服务器上的文件下载至用户本地
//     * ！！！开发中：已实现从FTP服务器把文件下载至服务运行的本地（225），未实现从225下载至用户本地
//     *
//     * @param fileName 从前端获取的文件数组
//     * @param remotePath FTP服务器上文件所在的路径
//     * @param downloadPath 用户下载
//     *
//     * @return 是否成功从FTP服务器下载文件
//     */
//    public static boolean download(String fileName, String remotePath, String downloadPath) {
//        FTPClient client = null;
//        boolean success = false;
//        FileOutputStream fos = null;
//        try {
//            client = connect();
//            boolean hasJump = client.changeWorkingDirectory(remotePath);
//            if (hasJump) {
//                String localPath = downloadPath + "/" + fileName;
//                File file = new File(localPath);
//                fos = new FileOutputStream(file);
//                success = client.retrieveFile(fileName, fos);
//            }
//        } catch (IOException | IllegalStateException e) {
//            e.printStackTrace();
//        } finally {
//            close(fos, client);
//        }
//        return success;
//    }

    /**
     * 关闭方法
     * 功能：关闭输入流、登出FTP服务器、断开FTP服务器连接
     *
     * @param stream 输入流
     * @param client FTP服务器连接
     */
    private static void close(InputStream stream, FTPClient client) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (client != null) {
            try {
                client.logout();
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭方法
     * 功能：关闭输出流、登出FTP服务器、断开FTP服务器连接
     *
     * @param stream 输出流
     * @param client FTP服务器连接
     */
    private static void close(OutputStream stream, FTPClient client) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (client != null) {
            try {
                client.logout();
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean delete(String fileName, String remotePath) {
        FTPClient client = null;
        try {
            client = connect();
            boolean hasJump = client.changeWorkingDirectory(remotePath);
            if (hasJump) {
                return client.deleteFile(fileName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            disconnect(client);
        }
        return false;
    }

    private static void disconnect(FTPClient client) {
        if (client != null) {
            try {
                client.logout();
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * // 删除文件至FTP通用方法（删除文件至FTP通用方法）
     * @param fileName 文件名
     * @param remotePath 文件路径
     */
    public static  boolean deleteFileFtp(String fileName, String remotePath){
        FTPClient client = null;
        boolean flag = false;
        try {
            client=connect();
            try {
                client.changeWorkingDirectory(remotePath);
                client.dele(fileName);
                flag = true;
            } catch (Exception e) {
                System.out.println("删除文件失败！请检查系统FTP设置,并确认FTP服务启动");
            }finally {
                if (client != null) {
                    try {
                        client.logout();
                        client.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            client.logout();
            client.disconnect();
        } catch (Exception e) {
            System.out.println("删除文件失败！");
        }
        return flag;
    }

    /**
     * 获取（/需求类型/需求名称/工作大类/工作小类/工作项）
     */
    public String filePath(String demandid,String modelid){
        String typeName="";
        String demandName="";
        String workItem="";
        String workBigType="";
        String smallTypeId="";
        String  filePath="";
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        String time = format.format(new Date());
        String index="工作量"+time+"/";
        //查询需求相关
        if(!StringUtils.isFullNull(demandid)){
            Map<String, String> map=needMapper.selectNeed(demandid);
            if(map !=null && map.isEmpty()){
                typeName = map.get("TYPE_NAME");
                demandName = map.get("DEMAND_CONTENT");
            }
            if(!StringUtils.isFullNull(modelid)){
                //查询工作项大类
                workBigType = needMapper.selectBtypename(modelid);//工作大类
                if(StringUtils.isFullNull(workBigType)){
                    workBigType="";
                }
                Map<String, String> stypename = needMapper.selectStypename(modelid);//小类和工作项
                if(stypename!=null && stypename.isEmpty()){
                    workItem = stypename.get("WORK_ITEM");//工作项
                    smallTypeId = stypename.get("TYPE_NAME");//工作小类
                }
            }
            //拼接路径（/工作量202011/和生活功能/摇一摇新需求/支撑/程序开发/接口开发/登录流程.txt）
           filePath=typeName+"/"+demandName+"/"+workBigType+"/"+smallTypeId+"/"+workItem;
            String[] split = filePath.split("/");
            for (String s:split) {
                if(StringUtils.isFullNull(s)){
                    s="";
                    continue;
                }
                index+=s+"/";
            }
        }
        return index;
    }



}
