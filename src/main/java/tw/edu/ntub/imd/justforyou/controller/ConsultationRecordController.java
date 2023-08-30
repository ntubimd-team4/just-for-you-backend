package tw.edu.ntub.imd.justforyou.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.justforyou.bean.ConsultationRecordBean;
import tw.edu.ntub.imd.justforyou.service.ConsultationRecordService;
import tw.edu.ntub.imd.justforyou.util.http.BindingResultUtils;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/consultation-record")
public class ConsultationRecordController {
    private final ConsultationRecordService consultationRecordService;

    @PatchMapping(path = "")
    public ResponseEntity<String> updateStudent(@RequestBody ConsultationRecordBean consultationRecordBean) {
        consultationRecordService.update(consultationRecordBean.getCid() ,consultationRecordBean);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }

    @PostMapping(path = "")
    public ResponseEntity<String> createStudent(@Valid @RequestBody ConsultationRecordBean consultationRecordBean,
                                                BindingResult bindingResult) {
        BindingResultUtils.validate(bindingResult);
        consultationRecordService.save(consultationRecordBean);
        return ResponseEntityBuilder
                .success()
                .message("新增成功")
                .build();
    }
}