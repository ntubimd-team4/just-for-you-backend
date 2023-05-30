package tw.edu.ntub.imd.justforyou.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.justforyou.bean.UserAccountBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dto.Pager;
import tw.edu.ntub.imd.justforyou.service.UserAccountService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.justforyou.util.json.array.ArrayData;
import tw.edu.ntub.imd.justforyou.util.json.object.ObjectData;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/user-account")
public class UserAccountController {
    private final UserAccountService userAccountService;

    @GetMapping(path = "/list")
    public ResponseEntity<String> searchAll(Pager pager) {
        ArrayData arrayData = new ArrayData();
        for (UserAccountBean userAccountBean : userAccountService.searchAll(pager)) {
            ObjectData objectData = arrayData.addObject();
            objectData.add("userId", userAccountBean.getUserId());
            objectData.add("userName", userAccountBean.getUserName());
            objectData.add("userSex", userAccountBean.getUserSex());
            objectData.add("department", userAccountBean.getDepartment());
            objectData.add("picture", userAccountBean.getPicture());
            objectData.add("role", userAccountBean.getRole().getTypeName());
            objectData.add("available", userAccountBean.getAvailable());
        }
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(arrayData)
                .build();
    }

    @PatchMapping("/status")
    public ResponseEntity<String> patchStudentStatus(@RequestParam("userId") String userId) {
        userAccountService.updateAvailable(userId);
        return ResponseEntityBuilder.success()
                .message("更改成功")
                .build();
    }
}