package cn.chairc.chat.service;

import cn.chairc.chat.model.ResultSet;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    ResultSet upload(MultipartFile file);
}
