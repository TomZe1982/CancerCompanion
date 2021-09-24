package de.tomze.backend.controller;


import de.tomze.backend.model.ImageEntity;
import de.tomze.backend.model.UserEntity;
import de.tomze.backend.repository.ImageRepository;
import de.tomze.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@RestController
@CrossOrigin
@RequestMapping("api/tomze/image")
public class ImageController {


    private final ImageRepository imageRepository;
    private final UserService userService;

    @Autowired
    public ImageController(ImageRepository imageRepository, UserService userService) {
        this.imageRepository = imageRepository;
        this.userService = userService;
    }


    @PostMapping
    Long uploadImage(@AuthenticationPrincipal UserEntity authUser, @RequestParam MultipartFile multipartImage) throws Exception {
        UserEntity userEntity = userService.getUser(authUser.getUserName());

        ImageEntity dbImage = new ImageEntity();
        dbImage.setName(multipartImage.getName());
        dbImage.setContent(multipartImage.getBytes());
        dbImage.setId(userEntity);

        imageRepository.save(dbImage);

        return dbImage.getImageId();
    }

    @GetMapping(value = "/{userName}/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    Resource downloadImage(@AuthenticationPrincipal UserEntity authUser,@PathVariable String userName, @PathVariable Long imageId) throws IllegalAccessException {
        if(authUser.getRole().equals("user") && !authUser.getUserName().equals(userName)){
            throw new IllegalAccessException("User can not download other users image");
        }

        byte[] image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
                .getContent();

        return new ByteArrayResource(image);

    }

}
