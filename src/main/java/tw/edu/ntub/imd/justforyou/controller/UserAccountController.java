package tw.edu.ntub.imd.justforyou.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tw.edu.ntub.imd.justforyou.bean.UserAccountBean;
import tw.edu.ntub.imd.justforyou.config.util.SecurityUtils;
import tw.edu.ntub.imd.justforyou.databaseconfig.dto.Pager;
import tw.edu.ntub.imd.justforyou.exception.NotFoundException;
import tw.edu.ntub.imd.justforyou.service.UserAccountService;
import tw.edu.ntub.imd.justforyou.util.http.ResponseEntityBuilder;
import tw.edu.ntub.imd.justforyou.util.json.object.ObjectData;
import tw.edu.ntub.imd.justforyou.util.json.object.SingleValueObjectData;


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

    @GetMapping(path = "/data")
    public ResponseEntity<String> searchKeywordList(@RequestParam(name = "userId") String userId,
                                                    @RequestParam(name = "userName") String userName,
                                                    @RequestParam(name = "department") String department,
                                                    Pager pager) {
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(userAccountService.searchKeywordList(userId, userName, department, pager),
                        this::addUserAccountListToObjectData)
                .build();
    }

    @GetMapping(path = "/data/count")
    public ResponseEntity<String> searchKeywordListPage(@RequestParam(name = "userId") String userId,
                                                        @RequestParam(name = "userName") String userName,
                                                        @RequestParam(name = "department") String department,
                                                        int count) {
        int totalPage = userAccountService.getKeywordListTotalPage(userId, userName, department, count);
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(SingleValueObjectData.create("totalPage", totalPage))
                .build();
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<String> getById(@PathVariable(name = "id") String id) {
        UserAccountBean userAccountBean = userAccountService.getById(id)
                .orElseThrow(() -> new NotFoundException("查無此帳號"));
        ObjectData objectData = new ObjectData();
        addUserAccountListToObjectData(objectData, userAccountBean);
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(objectData)
                .build();
    }

    @GetMapping(path = "/role")
    public ResponseEntity<String> getAccountRole() {
        String id = SecurityUtils.getLoginUserAccount();
        UserAccountBean userAccountBean = userAccountService.getById(id)
                .orElseThrow(() -> new NotFoundException("查無此帳號"));
        ObjectData objectData = new ObjectData();
        objectData.add("value", userAccountBean.getRole().getValue());
        objectData.add("description", userAccountBean.getRole().getTypeName());
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(objectData)
                .build();
    }

    @GetMapping(path = "/profile")
    public ResponseEntity<String> getAccountProfile() {
        String id = SecurityUtils.getLoginUserAccount();
        UserAccountBean userAccountBean = userAccountService.getById(id)
                .orElseThrow(() -> new NotFoundException("查無此帳號"));
        ObjectData objectData = new ObjectData();
        addUserAccountListToObjectData(objectData, userAccountBean);
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(objectData)
                .build();
    }

    @GetMapping(path = "/count")
    public ResponseEntity<String> searchTotalPage(@RequestParam(name = "type") String type,
                                                  @RequestParam(name = "count") int count) {
        int totalPage = userAccountService.getTotalPage(type, count);
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(SingleValueObjectData.create("totalPage", totalPage))
                .build();
    }

    @PatchMapping(path = "")
    public ResponseEntity<String> updateAccountData(@RequestBody UserAccountBean userAccountBean) {
        userAccountService.update(userAccountBean.getUserId(), userAccountBean);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }

    @PatchMapping(path = "/profile")
    public ResponseEntity<String> updateAccountProfile(@RequestBody UserAccountBean userAccountBean) {
        String id = SecurityUtils.getLoginUserAccount();
        userAccountService.update(id, userAccountBean);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }

    @PatchMapping(path = "/status")
    public ResponseEntity<String> updateAvailable(@RequestParam("id") String id) {
        userAccountService.updateAvailable(id);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }
}