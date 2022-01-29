package com.drovo.controller;

import com.azure.core.annotation.Post;
import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.drovo.entity.Person;
import com.drovo.service.UserService;
import org.hibernate.cfg.BaselineSessionEventsListenerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    private static final String CONNECTION_STRING = "DefaultEndpointsProtocol=https;AccountName=drovosa;AccountKey=C4PwAk/Jpdu+d7INkDIFhzhCsI1jdgQUuy3at9fqBwQXoWwIzdAh4pV6NW0A6zFG/EVnCQ8KLj72eFqJCekVsw==;EndpointSuffix=core.windows.net";

    @GetMapping("/")
    String sayHello(){
        return "Hello There User";
    }

    @PostMapping("/user/register")
    public ResponseEntity<Boolean> addUser(@RequestBody Person user)
    {
        boolean flag = userService.registerUser(user);

        if(flag == true) {
            createContainer(String.valueOf(user.getPhoneNo()));
            return new ResponseEntity<>(true , HttpStatus.CREATED);
        }
        else return new ResponseEntity<>(false , HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/user/login")
    public ResponseEntity<Boolean> logInUser(@RequestBody Person user){
       boolean  check = userService.logInUser(user);
       if (check == true) return new ResponseEntity<>(true , HttpStatus.OK);
       else             return new ResponseEntity<>(false , HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user/allUsers")
    public List<Person> getAllUsers(){
        return userService.getAllUsers();
    }


    public void createContainer(String containerName){
        BlobServiceClient client = new BlobServiceClientBuilder().connectionString(CONNECTION_STRING).buildClient();

        BlobContainerClient containerClient = client.createBlobContainer(containerName);
    }

    @GetMapping("/user/{phone}/drive")
    public ResponseEntity<List<Map<String , String>> > containerDetails(@PathVariable("phone") String phone){
        BlobServiceClient client = new BlobServiceClientBuilder().connectionString(CONNECTION_STRING).buildClient();

        List<Map<String , String>> fileNames = new ArrayList<>();
        boolean flag = false;
        ResponseEntity<List<Map<String , String>> > response;
        try{
            BlobContainerClient containerClient = client.getBlobContainerClient(phone);
            for(BlobItem item : containerClient.listBlobs()){
                Map<String ,String> details = new HashMap<>();
                details.put("name" , item.getName());
                details.put("size" , String.valueOf(item.getProperties().getContentLength()/1024));
                fileNames.add(details);
            }
        }
        catch (Exception e){
            fileNames.add(new HashMap<>());
            return new ResponseEntity<>(fileNames , HttpStatus.NOT_FOUND);
        }

        response = new ResponseEntity<>(fileNames , HttpStatus.OK);

        return response;
    }

    @GetMapping("/user/{phone}/{image}")
    public ResponseEntity<String> getImg(@PathVariable("image") String imageName , @PathVariable("phone") String phone){
        BlobServiceClient client = new BlobServiceClientBuilder().connectionString(CONNECTION_STRING).buildClient();
        BlobContainerClient containerClient = client.getBlobContainerClient(phone);

        BlobClient blobClient =  containerClient.getBlobClient(imageName);

        try{
            byte[] res = blobClient.downloadContent().toBytes();
            return new ResponseEntity<String>(
                    Base64.getEncoder().encodeToString(res), HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<String>((String) null, HttpStatus.NOT_FOUND);
        }

    }

//    @GetMapping("/user/download")
//    public String imageDownload(@PathVariable("image") String imageName , @PathVariable("phone") String phone){
//
//        BlobServiceClient client = new BlobServiceClientBuilder().connectionString(CONNECTION_STRING).buildClient();
//        BlobContainerClient containerClient = client.getBlobContainerClient(phone);
//        BlobClient blobClient = containerClient.getBlobClient(imageName);
//        return blobClient.toString();
//    }

    @PostMapping("/user/{phoneNo}/upload")
    ResponseEntity<String> uploadImg(@RequestParam("file") String encodedImage , @RequestParam("name") String name , @PathVariable("phoneNo") String phoneNo){

        byte[] decodedImage = Base64.getDecoder().decode(encodedImage);

        BlobServiceClient client = new BlobServiceClientBuilder().connectionString(CONNECTION_STRING).buildClient();
        BlobContainerClient containerClient = client.getBlobContainerClient(phoneNo);

        BlobClient blobClient =  containerClient.getBlobClient(name);
        InputStream is = new ByteArrayInputStream(decodedImage);
        blobClient.upload(BinaryData.fromStream(is));
        return new ResponseEntity<String>( "uploaded successfully" ,HttpStatus.OK);
    }

    @DeleteMapping("/user/{phoneNo}/{imageName}")
    ResponseEntity<String> deleteImage(@PathVariable("phoneNo") String phoneNo ,@PathVariable("imageName") String imageName){
        BlobServiceClient client = new BlobServiceClientBuilder().connectionString(CONNECTION_STRING).buildClient();
        BlobContainerClient containerClient = client.getBlobContainerClient(phoneNo);

        BlobClient blobClient =  containerClient.getBlobClient(imageName);

        blobClient.delete();
        return new ResponseEntity<>("deleted" , HttpStatus.OK);
    }




}

