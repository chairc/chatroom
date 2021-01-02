package cn.chairc.chat.service.Impl;

import cn.chairc.chat.model.ResultSet;
import cn.chairc.chat.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FileServiceImpl implements FileService {

    private static Logger log = LoggerFactory.getLogger(FileServiceImpl.class); //slf4j

    @Value("${chat-file.upload-path}")
    private String UPLOAD_PATH;

    @Override
    public ResultSet upload(MultipartFile file) {
        ResultSet resultSet = new ResultSet();
        String filename = file.getOriginalFilename();
        String filePath = UPLOAD_PATH;
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        File file1 = new File(filePath + filename);
        try {
            if (file.isEmpty()) {
                log.error("上传文件失败，文件为空");
                resultSet.fail("上传文件失败，文件为空");
                return resultSet;
            }
            file.transferTo(file1);
            log.info("上传文件成功");
            resultSet.success("上传文件成功");
        } catch (Exception e) {
            log.error("上传文件失败");
            log.error(e.toString(), e);
            resultSet.fail("上传文件失败");
        }
        return resultSet;
    }
}
