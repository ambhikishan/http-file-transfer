package com.filesharing.http.Controller;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


@RestController
public class UploadController {
    @PostMapping("/uploads")
    public Mono<ResponseEntity<?>> fileUploads(ServerHttpRequest request, @RequestHeader("File-Name") String fileName, @RequestHeader("Upload-Path") String filePath) throws IOException {

        Path destination = Paths.get(filePath, fileName);
        return DataBufferUtils.write(request.getBody(), destination, StandardOpenOption.CREATE)
                .then(Mono.just(ResponseEntity.ok().build()));


    }
}
