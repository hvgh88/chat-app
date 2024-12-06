package com.umass.hangout.controller;

import com.umass.hangout.service.ICSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ICSController {
    @Autowired
    private ICSService icsService;

    @GetMapping("/group/{groupId}/ics")
    public ResponseEntity<byte[]> generateICS(@PathVariable Long groupId) {
        try {
            byte[] icsContent = icsService.generateICS(groupId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("text/calendar"));
            headers.setContentDisposition(ContentDisposition.attachment().filename("event.ics").build());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(icsContent);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
