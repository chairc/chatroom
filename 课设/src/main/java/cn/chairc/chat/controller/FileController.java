package cn.chairc.chat.controller;

import cn.chairc.chat.model.ResultSet;
import cn.chairc.chat.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    FileService fileService;

    @RequestMapping("/upload")
    @ResponseBody
    public ResultSet uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return fileService.upload(file);
    }
}
