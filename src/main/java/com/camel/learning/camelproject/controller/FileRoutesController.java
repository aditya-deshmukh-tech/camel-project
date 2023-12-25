package com.camel.learning.camelproject.controller;

import com.camel.learning.camelproject.learn.FileRouteControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileRoutesController {

    @Autowired
    private FileRouteControl fileRouteControl;

    @GetMapping("/start/route/{routeId}")
    public ResponseEntity<?> startRouteWithId(@PathVariable String routeId) throws Exception {
        fileRouteControl.startTheRoute(routeId);
        return ResponseEntity.ok("route started..");
    }
}
