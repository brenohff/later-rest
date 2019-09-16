package br.com.brenohff.later.service;

import br.com.brenohff.later.service.exceptions.FileException;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    BufferedImage getJpgImageFromFile(MultipartFile uploadedFile) {
        String ext = FilenameUtils.getExtension(uploadedFile.getOriginalFilename());
        if (!"png".equals(ext) && !"jpg".equals(ext) && !"jpeg".equals(ext)) {
            throw new FileException("Somente imagens JPG e PNG são permitidas =(");
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(uploadedFile.getInputStream());
            if ("png".equals(ext)) {
                bufferedImage = pngToJpg(bufferedImage);
            }
            return bufferedImage;
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo =(");
        }
    }

    private BufferedImage pngToJpg(BufferedImage bufferedImage) {
        BufferedImage jpgImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        jpgImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
        return jpgImage;
    }

    InputStream getInputStream(BufferedImage image, String extension) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, extension, os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            throw new FileException("Erro ao ler arquivo =(");
        }

    }

    public BufferedImage cropSquare(BufferedImage image) {
        int min = (image.getHeight() <= image.getWidth()) ? image.getHeight() : image.getWidth();

        return Scalr.crop(image, (image.getWidth() / 2) - (min / 2), (image.getHeight() / 2) - (min / 2), min, min);
    }

    BufferedImage resize(BufferedImage image, int width, int height) {
        return Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, width, height);
    }
}
