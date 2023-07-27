package tw.edu.ntub.imd.justforyou.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.justforyou.service.ConsultationRecordService;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/consultation-record")
public class ConsultationRecordController {
    private final ConsultationRecordService consultationRecordService;
}