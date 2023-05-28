package tw.edu.ntub.imd.justforyou.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.justforyou.config.util.SecurityUtils;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.Role;
import tw.edu.ntub.imd.justforyou.service.UserAccountService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.justforyou.util.json.object.ObjectData;


@AllArgsConstructor
@RestController
@RequestMapping(path = "/user-account")
public class UserAccountController {
    private final UserAccountService userAccountService;

    @GetMapping(path = "")
    public ResponseEntity<String> getLoginUser() {
        ObjectData objectData = new ObjectData();
        userAccountService
                .getById(SecurityUtils.getLoginUserAccount())
                .ifPresentOrElse(
                        userAccountBean -> {
                            objectData.add("id", userAccountBean.getUserId());
                            objectData.add("name", userAccountBean.getUserName());

                            String role = SecurityUtils.getLoginUserRole();

                            if (Role.isStudent(role)) {
                                objectData.add("isStudent", true);
                            } else if (Role.isTeacher(role)) {
                                objectData.add("isTeacher", true);
                            } else if (Role.isManage(role)) {
                                objectData.add("isManage", true);
                            }
                        },
                        () -> objectData.add("name", ""));
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(objectData)
                .build();
    }
}