package com.filesharing.http.Controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;

@RestController
public class HttpController {

    private static File file;
    static String basePath = "C:/";
    @GetMapping("/")
    public Mono<ResponseEntity<?>> fileExploreNnDownload(@RequestParam(value = "path",required = false , defaultValue = "") String filePath)
    {
        if(filePath.equals(""))
        {
            file = new File(basePath);
            return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(file.listFiles()));
        }
        else
        {
            file = new File(filePath);

            if(file.isDirectory()) {
                return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(file.listFiles()));
            }
            else
            {
              
                Resource resource = new FileSystemResource(file.getPath());


                return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+file.getName())
                        .body(resource));
            }



        }

    }
}
