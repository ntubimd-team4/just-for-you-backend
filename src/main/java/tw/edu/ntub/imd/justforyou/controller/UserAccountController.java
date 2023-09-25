package tw.edu.ntub.imd.justforyou.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "帳號 /user-account")
@AllArgsConstructor
@RestController
@RequestMapping(path = "/user-account")
public class UserAccountController {
    private final UserAccountService userAccountService;

    @Operation(summary = "權限查詢")
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

    @Operation(summary = "人員管理 - 全查")
    @GetMapping(path = "")
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

    @Operation(summary = "人員管理 - 總頁數查詢")
    @GetMapping(path = "/count")
    public ResponseEntity<String> searchTotalPage(@RequestParam(name = "type") String type,
                                                  @RequestParam(name = "count") int count) {
        int totalPage = userAccountService.getTotalPage(type, count);
        return ResponseEntityBuilder.success()
                .message("查詢成功")
                .data(SingleValueObjectData.create("totalPage", totalPage))
                .build();
    }

    @Operation(summary = "人員管理 - 模糊查詢")
    @GetMapping(path = "/query")
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

    @Operation(summary = "人員管理 - 模糊查的總頁數查詢")
    @GetMapping(path = "/query/count")
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

    @Operation(summary = "人員管理 - 單查")
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

    @Operation(summary = "個人帳戶資訊")
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

    @Operation(summary = "修改個人資訊")
    @PatchMapping(path = "/profile")
    public ResponseEntity<String> updateAccountProfile(@RequestBody UserAccountBean userAccountBean) {
        String id = SecurityUtils.getLoginUserAccount();
        userAccountService.update(id, userAccountBean);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }

    @Operation(summary = "人員管理 - 修改帳號資訊")
    @PatchMapping(path = "")
    public ResponseEntity<String> updateAccountData(@RequestBody UserAccountBean userAccountBean) {
        userAccountService.update(userAccountBean.getUserId(), userAccountBean);
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }

    @Operation(summary = "人員管理 - 修改啟用狀態")
    @PatchMapping(path = "/status")
    public ResponseEntity<String> updateAvailable(@RequestBody UserAccountBean userAccountBean) {
        userAccountService.updateAvailable(userAccountBean.getUserId());
        return ResponseEntityBuilder.success()
                .message("修改成功")
                .build();
    }
}