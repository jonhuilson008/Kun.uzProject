package com.example.service;

import com.example.entity.AttachEntity;
import com.example.exps.ItemNotFoundException;
import com.example.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.UUID;

@Service
public class AttachService {
    @Autowired
    private AttachRepository attachRepository;


    public String saveToSystem(MultipartFile file) {
        try {
            File folder = new File("attaches");
            if (!folder.exists()) {
                folder.mkdir();
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get("attaches/" + file.getOriginalFilename());
            // attaches/test_imge_1.jpg
            Files.write(path, bytes);
            return file.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String saveToSystem2(MultipartFile file) {
        try {
            File folder = new File("attaches");
            if (!folder.exists()) {
                folder.mkdir();
            }
            byte[] bytes = file.getBytes();
            String extension = getExtension(file.getOriginalFilename());
            String fileName = UUID.randomUUID().toString();
            Path path = Paths.get("attaches/" + fileName + "." + extension);
            // attaches/test_imge_1.jpg
            // attaches/uuid().jpg
            Files.write(path, bytes);
            return fileName + "." + extension;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }
    public byte[] loadImage(String fileName) {
        byte[] imageInByte;

        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read(new File("attaches/" + fileName));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(originalImage, "png", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
            return imageInByte;
        } catch (Exception e) {
            return new byte[0];
        }
    }

    public byte[] open_general(String attachName) {
        byte[] data;
        try {
            Path file = Paths.get("attaches/" + attachName);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public String saveToSystem3(MultipartFile file) {
        try {
            String pathFolder = getYmDString(); // 2022/04/23
            File folder = new File("attaches/" + pathFolder);  // attaches/2023/04/26
            if (!folder.exists()) {
                folder.mkdirs();
            }
            byte[] bytes = file.getBytes();
            String extension = getExtension(file.getOriginalFilename());

            AttachEntity attachEntity = new AttachEntity();
            attachEntity.setId(UUID.randomUUID().toString());
            attachEntity.setCreatedData(LocalDateTime.now());
            attachEntity.setExtension(extension);
            attachEntity.setSize(file.getSize());
            attachEntity.setPath(pathFolder);
            attachEntity.setOriginalName(file.getOriginalFilename());
            attachRepository.save(attachEntity);

            Path path = Paths.get("attaches/" + pathFolder + "/" + attachEntity.getId() + "." + extension);
            // attaches/2023/04/26/uuid().jpg
            Files.write(path, bytes);
            return attachEntity.getId() + "." + extension;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }
    public byte[] open_general2(String attachName) {
        // 20f0f915-93ec-4099-97e3-c1cb7a95151f.jpg
        int lastIndex = attachName.lastIndexOf(".");
        String id = attachName.substring(0,lastIndex);
        AttachEntity attachEntity = get(id);
        byte[] data;
        try {                                                     // attaches/2023/4/25/20f0f915-93ec-4099-97e3-c1cb7a95151f.jpg
            Path file = Paths.get("attaches/" + attachEntity.getPath() + "/" + attachName);
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
    public AttachEntity get(String id) {
        return attachRepository.findById(Integer.valueOf(id)).orElseThrow(() -> {
            throw new ItemNotFoundException("Attach not ound");
        });
    }
//    package com.example.service;
//
//
//import com.example.dto.attach.AttachDTO;
//import com.example.entity.AttachEntity;
//import com.example.exceptions.ItemNotFoundException;
//import com.example.repository.AttachRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class AttachService {
//    @Value("${attach.upload.folder}")
//    private String attachFolder;
//
//
//    @Value("${application.url}")
//    private String attachOpenUrl;
//
//    @Autowired
//    private AttachRepository attachRepository;
//  /*  public String saveToSystemOld(MultipartFile file) {
//        try {
//            // attaches/2022/04/23/UUID.png
//            String attachPath = getYmDString(); // 2022/04/23
//            String extension = getExtension(file.getOriginalFilename()); // .png....
//            String fileName = UUID.randomUUID().toString() + "." + extension; // UUID.png
//
//            File folder = new File(attachFolder + attachPath);  // attaches/2022/04/23/
//            if (!folder.exists()) {
//                folder.mkdirs();
//            }
//
//            byte[] bytes = file.getBytes();
//
//            Path path = Paths.get(attachFolder + attachPath + "/" + fileName); // attaches/2022/04/23/UUID.png
//            Files.write(path, bytes);
//            return fileName;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }*/
//
//    public AttachDTO saveToSystem(MultipartFile file) {
//        try {
//            // attaches/2022/04/23/UUID.png
//            String attachPath = getYmDString(); // 2022/04/23
//            String extension = getExtension(file.getOriginalFilename()); // .png....
//            String uuid = UUID.randomUUID().toString();
//            String fileName = uuid + "." + extension; // UUID.png
//
//            File folder = new File(attachFolder + attachPath);  // attaches/2022/04/23/
//            if (!folder.exists()) {
//                folder.mkdirs();
//            }
//
//            byte[] bytes = file.getBytes();
//
//            Path path = Paths.get(attachFolder + attachPath + "/" + fileName); // attaches/2022/04/23/UUID.png
//            Files.write(path, bytes);
//
//            AttachEntity entity = new AttachEntity();
//            entity.setPath(attachPath);
//            entity.setExtension(extension);
//            entity.setSize(file.getSize());
//            entity.setOriginalName(file.getOriginalFilename());
//            entity.setCreatedData(LocalDateTime.now());
//            entity.setId(uuid);
//            attachRepository.save(entity);
//
//            AttachDTO attachDTO = toDTO(entity);
//            attachDTO.setOriginalName(file.getOriginalFilename());
//            attachDTO.setUrl(attachOpenUrl + fileName);
//
//
//            return attachDTO;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    private AttachDTO toDTO(AttachEntity entity) {
//        AttachDTO dto = new AttachDTO();
//        dto.setId(entity.getId());
//        dto.setCreatedDate(entity.getCreatedData());
//        dto.setOriginalName(entity.getOriginalName());
//        dto.setPath(entity.getPath());
//        dto.setSize(entity.getSize());
//        return dto;
//    }
//
//   /* public String saveToSystemOld(MultipartFile file) {
//        try {
//            File folder = new File("attaches");
//            if (!folder.exists()) {
//                folder.mkdir();
//            }
//
//            byte[] bytes = file.getBytes();
//
//            Path path = Paths.get("attaches/" + file.getOriginalFilename());
//            Files.write(path, bytes);
//            return file.getOriginalFilename();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }*/
//
//    public byte[] loadImage(String fileName) {
//        byte[] imageInByte;
//        BufferedImage originalImage;
//
//        try {
//            Optional<AttachEntity> byId = attachRepository.findById(fileName);
//            if (byId.isEmpty()) {
//                throw new ItemNotFoundException("attach");
//            }
//            File file = new File("attaches/" + byId.get().getPath() + "/" + fileName + "." + byId.get().getExtension());
//            if (!file.exists()) {
//                throw new ItemNotFoundException("File");
//            }
//            originalImage = ImageIO.read(file);
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(originalImage, "png", baos);
//
//
//            baos.flush();
//            imageInByte = baos.toByteArray();
//            baos.close();
//            return imageInByte;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//   /* public byte[] loadImageOld(String fileName) {
//        byte[] imageInByte;
//        BufferedImage originalImage;
//        try {
//            originalImage = ImageIO.read(new File("attaches/" + fileName));
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ImageIO.write(originalImage, "png", baos);
//
//            baos.flush();
//            imageInByte = baos.toByteArray();
//            baos.close();
//            return imageInByte;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new byte[0];
//    }*/
///*    public byte[] open_generalOld(String fileName) {
//        byte[] data;
//        try {
//            Path file = Paths.get("attaches/2022/12/7" + fileName);
//            data = Files.readAllBytes(file);
//            return data;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new byte[0];
//    }*/
//
//
//    public byte[] open_general(String key) {
//        byte[] data;
//        try {
//            AttachEntity entity = get(key);
//            String path = entity.getPath() + "/" + key + "." + entity.getExtension();
//            Path file = Paths.get(attachFolder + path);
//            data = Files.readAllBytes(file);
//            return data;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new byte[0];
//    }
//
//
//    public ResponseEntity<Resource> download(String fileName) {
//        try {
//            AttachEntity entity = get(fileName);
//            String path = entity.getPath() + "/" + fileName + "." + entity.getExtension();
//            Path file = Paths.get(attachFolder + path);
//            Resource resource = new UrlResource(file.toUri());
//            if (resource.exists() || resource.isReadable()) {
//                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                        "attachment; filename=\"" + entity.getOriginalName() + "\"").body(resource);
//            } else {
//                throw new RuntimeException("Could not read the file!");
//            }
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            throw new RuntimeException("ERROR: " + e.getMessage());
//        }
//    }
//
//    /* public Resource downloadOld(String fileName) {
//         try {
//             Path file = Paths.get("attaches/" + fileName);
//             Resource resource = new UrlResource(file.toUri());
//
//             if (resource.exists() || resource.isReadable()) {
//                 return resource;
//             } else {
//                 throw new RuntimeException("Could not read the file!");
//             }
//         } catch (MalformedURLException e) {
//             throw new RuntimeException("Error: " + e.getMessage());
//         }
//     }*/
//    public String getYmDString() {
//        int year = LocalDateTime.now().getYear();
//        int month = LocalDateTime.now().getMonthValue();
//        int day = LocalDateTime.now().getDayOfMonth();
//
//        return year + "/" + month + "/" + day; // 2022/04/23
//    }
//
//    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
//        int lastIndex = fileName.lastIndexOf(".");
//        return fileName.substring(lastIndex + 1);
//    }
//
//
//    public AttachEntity get(String id) {
//        return attachRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Attach not found"));
//    }
//
//    public Page<AttachDTO> getPaginationUrl(Integer page, Integer size) {
//        Pageable paging = PageRequest.of(page, size);
//
//        Page<AttachEntity> all = attachRepository.findAll(paging);
//
//        List<AttachEntity> allContent = all.getContent();
//
//        List<AttachDTO> dtoList = new ArrayList<>();
//
//        for (AttachEntity attach : allContent) {
//            AttachDTO dto = toDTO(attach);
//            dto.setUrl(attachOpenUrl + getExtension(attach.getId()));
//            dtoList.add(dto);
//        }
//
//        return new PageImpl<>(dtoList, paging, all.getTotalElements());
//    }
//
//    public Boolean delete(String id) {
//        AttachEntity attach = attachRepository.findById(id).orElseThrow(() -> {
//            throw new ItemNotFoundException("Not found");
//        });
//
//        File file = new File(attachFolder + attach.getPath() + "/" + id + "." + attach.getExtension());
//        if (file.delete()) {
//            attachRepository.deleteById(id);
//            return true;
//        }
//        return false;
//    }
//}
}
