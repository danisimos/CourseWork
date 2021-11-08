package ru.itis.services;

import ru.itis.dto.UserDto;
import ru.itis.models.FileInfo;

import javax.servlet.ServletOutputStream;
import java.io.InputStream;

public interface FilesService {
    FileInfo saveFileToStorage(UserDto userDto, InputStream inputStream, String originalFileName, String contentType, Long size);
    FileInfo getFileInfoById(Integer id);
    void readFileFromStorage(Integer fileId, ServletOutputStream outputStream);
}
