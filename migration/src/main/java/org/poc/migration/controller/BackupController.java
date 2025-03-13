package org.poc.migration.controller;

import org.poc.migration.service.BackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class BackupController {
    private final BackupService backupService;
    @Autowired
    public BackupController(BackupService backupService) {
        this.backupService = backupService;
    }

    @GetMapping("/backup")
    public String backup() throws IOException {
        return backupService.backupData();
    }

    @PostMapping("/restore")
    public String restore(@RequestParam("entityName") final String entityName) throws IOException {
        return backupService.restoreData(entityName);
    }
}