package tw.edu.ntub.imd.justforyou.config.util;

import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {
    public static final Integer REFRESH_HOUR = 24;
    public static final String HAS_ADMIN_AUTHORITY = "hasAuthority('ADMIN')";

    private SecurityUtils() {

    }

    public static String getLoginUserAccount() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static boolean isLogin() {
        return SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }
}
