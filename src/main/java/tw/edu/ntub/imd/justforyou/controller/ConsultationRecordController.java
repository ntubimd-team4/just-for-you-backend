package tw.edu.ntub.imd.justforyou.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.justforyou.bean.ConsultationRecordBean;
import tw.edu.ntub.imd.justforyou.service.ConsultationRecordService;
import tw.edu.ntub.imd.justforyou.util.encryption.EncryptionUtils;
import tw.edu.ntub.imd.justforyou.util.http.BindingResultUtils;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;

import javax.validation.Valid;

@Tag(name = "諮商紀錄 /consultation-record")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/consultation-record")
public class ConsultationRecordController {
    private final ConsultationRecordService consultationRecordService;

    @Operation(summary = "新增及修改諮商紀錄")
    @PatchMapping(path = "")
    public ResponseEntity<String> updateConsultationRecord(@Valid @RequestBody ConsultationRecordBean consultationRecordBean,
                                                           BindingResult bindingResult) {
        String message;
        BindingResultUtils.validate(bindingResult);
        consultationRecordBean.setContent(EncryptionUtils.cryptText(consultationRecordBean.getContent()));
        if (consultationRecordService.getById(consultationRecordBean.getSid()).isEmpty()) {
            consultationRecordService.save(consultationRecordBean);
            message = "新增成功";
        } else {
            consultationRecordService.update(consultationRecordBean.getSid(), consultationRecordBean);
            message = "修改成功";
        }
        return ResponseEntityBuilder.success()
                .message(message)
                .build();
    }
}