package tw.edu.ntub.imd.justforyou.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.justforyou.bean.UserAccountBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dto.Pager;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.Role;
import tw.edu.ntub.imd.justforyou.service.UserAccountService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.justforyou.util.json.object.ObjectData;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/user-account")
public class UserAccountController {
    private final UserAccountService userAccountService;

    @GetMapping(path = "/list")
    public ResponseEntity<String> searchAll(@RequestParam(name = "type") String type, Pager pager) {
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(userAccountService.searchData(type, pager), this::addUserAccountListToObjectData)
                .build();
    }

    private void addUserAccountListToObjectData(ObjectData objectData, UserAccountBean userAccountBean) {
        objectData.add("userId", userAccountBean.getUserId());
        objectData.add("userName", userAccountBean.getUserName());
        objectData.add("userSex", userAccountBean.getUserSex() == null ? null : !userAccountBean.getUserSex() ? "男" : "女");
        objectData.add("department", userAccountBean.getDepartment());
        objectData.add("picture", userAccountBean.getPicture());
        objectData.add("role", userAccountBean.getRole().getTypeName());
        objectData.add("available", userAccountBean.getAvailable());
    }

    @PatchMapping("/status")
    public ResponseEntity<String> updateAvailable(@RequestParam("id") String id) {
        userAccountService.updateAvailable(id);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }
}