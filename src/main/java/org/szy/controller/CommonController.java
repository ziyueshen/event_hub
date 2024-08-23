package org.szy.controller;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.szy.common.R;
import org.szy.entity.Event;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Value("${pic.path}")
    private String basePath;
    @PostMapping("/upload")
    public R<Map<String, String>> upload(MultipartFile file) throws IOException {
        // file is temp
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        // uuid new fila name, avoid duplicates
        String filename = UUID.randomUUID().toString() + suffix;
        file.transferTo(new File(basePath + filename));
        Map<String, String> mp = new HashMap<>();
        mp.put("items", filename);
        return R.success(mp);
    }
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {

        try {
            // read file content
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
            // write file content to response
            ServletOutputStream outputStream = response.getOutputStream();
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];  // 1024bytes, 1kb
            while( (len = fileInputStream.read(bytes)) != -1) {  // won't exceed the length of bytes
                outputStream.write(bytes, 0, len);
                outputStream.flush(); // refresh
            }

            fileInputStream.close();
            outputStream.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
