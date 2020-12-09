package com.ruoyi.project.module.upload.controller;

import com.ruoyi.common.utils.file.FTPUtils;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/file")
public class FileController extends BaseController {

    @GetMapping("/1")
    public String upload() {
        return "module/upload/1";
    }

    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        String path = "D:\\Work\\Workload System\\Upload Files\\";
        String fileName = "hasaki.txt";
        Path file = Paths.get(path, fileName);
        response.setContentType("application/x-gzip");
        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode("hasaki.txt", "UTF-8"));
        Files.copy(file, response.getOutputStream());
    }

    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult upload(MultipartFile[] files) {
        String path = "/test/hehe";
        boolean success = FTPUtils.upload(files, path);
        if (success) return success();
        return error();
    }

}
