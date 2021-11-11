package ru.geekbrains.vklimovich.springmvc.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {

    private static final String IMAGE_FOLDER_PATH = "/data/image";
    private static final String PRODUCT_IMAGE_FOLDER_PATH = IMAGE_FOLDER_PATH + "/product";

    private FileUtil() { }

    public static Path saveProductImage(MultipartFile imageFile) {
        if (imageFile == null) {
            throw new IllegalArgumentException("Image file can not be null!");
        }

        createDirectories(Paths.get(System.getProperty("user.dir"), PRODUCT_IMAGE_FOLDER_PATH));

        Path savePath = Paths.get(PRODUCT_IMAGE_FOLDER_PATH, imageFile.getOriginalFilename());
        saveFile(imageFile, Paths.get(System.getProperty("user.dir"), savePath.toString()));

        return savePath;
    }

    private static void createDirectories(Path path) {
        try {
            Files.createDirectories(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveFile(MultipartFile file, Path path) {
        try {
            file.transferTo(path);
        } catch (IOException e) {
            throw new RuntimeException("Can't save file by path=" + path);
        }
    }

}
