package tw.edu.ntub.imd.justforyou.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.justforyou.bean.ConsultationRecordBean;
import tw.edu.ntub.imd.justforyou.service.ConsultationRecordService;
import tw.edu.ntub.imd.justforyou.util.http.BindingResultUtils;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;

import javax.validation.Valid;

@Tag(name = "諮商紀錄 /consultation-record")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/consultation-record")
public class ConsultationRecordController {
    private final ConsultationRecordService consultationRecordService;

    @Operation(summary = "修改諮商紀錄")
    @PatchMapping(path = "")
    public ResponseEntity<String> updateConsultationRecord(@RequestBody ConsultationRecordBean consultationRecordBean) {
        consultationRecordService.update(consultationRecordBean.getCid(), consultationRecordBean);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }

    @Operation(summary = "新增諮商紀錄")
    @PostMapping(path = "")
    public ResponseEntity<String> createConsultationRecord(@Valid @RequestBody ConsultationRecordBean consultationRecordBean,
                                                           BindingResult bindingResult) {
        BindingResultUtils.validate(bindingResult);
        consultationRecordService.save(consultationRecordBean);
        return ResponseEntityBuilder
                .success()
                .message("新增成功")
                .build();
    }
}