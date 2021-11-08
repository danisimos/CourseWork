package ru.itis.services.impl;

import ru.itis.dao.FilesRepository;
import ru.itis.dao.UserRepository;
import ru.itis.dto.UserDto;
import ru.itis.models.FileInfo;
import ru.itis.services.FilesService;

import javax.servlet.ServletOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class FilesServiceImpl implements FilesService {
    FilesRepository filesRepository;
    UserRepository userRepository;

    String path = "C:\\Users\\danil\\Documents\\files\\";

    public FilesServiceImpl(FilesRepository filesRepository, UserRepository userRepository) {
        this.filesRepository = filesRepository;
        this.userRepository = userRepository;
    }

    @Override
    public FileInfo saveFileToStorage(UserDto userDto, InputStream inputStream, String originalFileName, String contentType, Long size) {
        FileInfo fileInfo = FileInfo.builder()
                .originalFileName(originalFileName)
                .storageFileName(UUID.randomUUID().toString())
                .type(contentType)
                .size(size)
                .build();

        fileInfo = filesRepository.save(fileInfo);

        try {
            Files.copy(inputStream, Paths.get(path + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        userRepository.updateAvatarForUser(userDto.getId(), fileInfo.getId());

        return fileInfo;
    }

    @Override
    public FileInfo getFileInfoById(Integer id) {
        return filesRepository.findById(id).orElseThrow(IllegalAccessError::new);
    }

    @Override
    public void readFileFromStorage(Integer fileId, ServletOutputStream outputStream) {
        FileInfo fileInfo = filesRepository.findById(fileId).orElseThrow(IllegalAccessError::new);

        File file = new File(path + fileInfo.getStorageFileName() + "." + fileInfo.getType().split("/")[1]);
        try {
            Files.copy(file.toPath(), outputStream);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
