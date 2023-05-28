package tw.edu.ntub.imd.justforyou.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.edu.ntub.imd.justforyou.bean.UserAccountBean;
import tw.edu.ntub.imd.justforyou.config.util.SecurityUtils;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.Role;
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
    public ResponseEntity<String> searchData() {
        ArrayData arrayData = new ArrayData();
        for (UserAccountBean userAccountBean : userAccountService.searchData()) {
            ObjectData objectData = arrayData.addObject();
            objectData.add("userId",userAccountBean.getUserId());
            objectData.add("userName",userAccountBean.getUserName());
            objectData.add("userSex",userAccountBean.getUserSex());
            objectData.add("department",userAccountBean.getDepartment());
            objectData.add("department",userAccountBean.getDepartment());
            objectData.add("googleId",userAccountBean.getGoogleId());
            objectData.add("role", userAccountBean.getRole().ordinal());//問33.ordinal()
            objectData.add("available",userAccountBean.getAvailable());
        }
        return ResponseEntityBuilder.success()
                .message("成功")
                .data(arrayData)
                .build();
    }
}