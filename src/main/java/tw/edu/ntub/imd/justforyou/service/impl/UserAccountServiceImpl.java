package tw.edu.ntub.imd.justforyou.service.impl;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tw.edu.ntub.birc.common.util.CollectionUtils;
import tw.edu.ntub.imd.justforyou.bean.UserAccountBean;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.UserAccountDAO;
import tw.edu.ntub.imd.justforyou.databaseconfig.dao.specification.UserAccountSpecification;
import tw.edu.ntub.imd.justforyou.databaseconfig.dto.Pager;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.UserAccount;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.Role;
import tw.edu.ntub.imd.justforyou.exception.NotFoundException;
import tw.edu.ntub.imd.justforyou.service.UserAccountService;
import tw.edu.ntub.imd.justforyou.service.transformer.UserAccountTransformer;
import tw.edu.ntub.imd.justforyou.util.EmailTransformUtils;

import java.util.*;

@Service
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccountBean, UserAccount, String> implements UserAccountService {
    @Value("${google.clientId}")
    private String clientId;
    private final UserAccountDAO userAccountDAO;
    private final UserAccountTransformer transformer;
    private final UserAccountSpecification specification;

    public UserAccountServiceImpl(UserAccountDAO userAccountDAO, UserAccountTransformer transformer, UserAccountSpecification specification) {
        super(userAccountDAO, transformer);
        this.userAccountDAO = userAccountDAO;
        this.transformer = transformer;
        this.specification = specification;
    }

    @Override
    public UserAccountBean save(UserAccountBean userAccountBean) {
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GoogleIdTokenVerifier verifier =
                new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new GsonFactory())
                        .setAudience(Collections.singletonList(clientId))
                        .build();
        try {
            GoogleIdToken idToken = verifier.verify(username);
            if (idToken != null) {
                IdToken.Payload payload = idToken.getPayload();
                String googleId = payload.getSubject();

                String email = (String) payload.get("email");
                if (!email.contains("@ntub.edu.tw")) {
                    throw new NotFoundException("請使用北商Mail登入");
                }
                Optional<UserAccount> optional = userAccountDAO.findByUserIdAndAvailableIsTrue(email);

                UserAccount userAccount;
                if (optional.isEmpty()) {
                    userAccount = new UserAccount();
                    userAccount.setUserId(email);
                    userAccount.setUserName((String) payload.get("name"));
                    userAccount.setPicture((String) payload.get("picture"));
                    userAccount.setGoogleId(googleId);
                    userAccount.setAvailable(true);
                    userAccount.setCreateId(email);

                    email = EmailTransformUtils.remove(email);

                    if (email.length() == 8 && email.matches("\\d{8}|[nN]\\d{7}|\\d{4}[a-zA-Z]\\d{3}|[nN]\\d{3}[a-zA-Z]\\d{3}")) {
                        userAccount.setRole(Role.STUDENT);
                    } else {
                        userAccount.setRole(Role.CASE_MANAGEMENT);
                    }
                    userAccountDAO.save(userAccount);
                } else {
                    userAccount = optional.get();
                }

                return new UserDetails() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        List<GrantedAuthority> authorities = new ArrayList<>();
                        authorities.add(new SimpleGrantedAuthority(userAccount.getRole().getTypeName()));
                        return authorities;
                    }

                    @Override
                    public String getPassword() {
                        return googleId;
                    }

                    @Override
                    public String getUsername() {
                        return userAccount.getUserId();
                    }

                    @Override
                    public boolean isAccountNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isAccountNonLocked() {
                        return true;
                    }

                    @Override
                    public boolean isCredentialsNonExpired() {
                        return true;
                    }

                    @Override
                    public boolean isEnabled() {
                        return userAccount.getAvailable();
                    }
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new UsernameNotFoundException("帳號已被停用或未使用北商Mail登入");
    }

    @Override
    public List<UserAccountBean> searchData(String type, Pager pager) {
        return CollectionUtils.map(type.equals("0") ?
                userAccountDAO.findAll(PageRequest.of(pager.getZeroBasedPage(), pager.getCount())).getContent() :
                userAccountDAO.findAll(specification.checkBlank(type), PageRequest.of(pager.getZeroBasedPage(),
                        pager.getCount())).getContent(), transformer::transferToBean);
    }

    @Override
    public List<UserAccountBean> searchKeywordList(String userId, String userName, String department, Pager pager) {
        return CollectionUtils.map(
                userAccountDAO.findAll(specification.checkBlank(userId, userName, department), PageRequest.of(pager.getZeroBasedPage(),
                        pager.getCount())).getContent(), transformer::transferToBean);
    }

    @Override
    public int getTotalPage(String type, int count) {
        return type.equals("0") ?
                userAccountDAO.findAll(PageRequest.of(0, count)).getTotalPages() :
                userAccountDAO.findAll(specification.checkBlank(type), PageRequest.of(0, count)).getTotalPages();
    }

    @Override
    public List<UserAccountBean> searchByTeacher() {
        return CollectionUtils.map(userAccountDAO.findByTeacher(), transformer::transferToBean);
    }

    @Override
    public int getKeywordListTotalPage(String userId, String userName, String department, int count) {
        return userAccountDAO.findAll(specification.checkBlank(userId, userName, department),
                PageRequest.of(0, count)).getTotalPages();
    }

    @Override
    public void updateAvailable(String userId) {
        Optional<UserAccount> userAccountOptional = userAccountDAO.findById(userId);
        if (userAccountOptional.isPresent()) {
            UserAccount userAccount = userAccountOptional.get();
            userAccount.setAvailable(!userAccount.getAvailable());
            userAccountDAO.update(userAccount);
        } else {
            throw new NotFoundException("查無此筆資料： " + userId);
        }
    }
}