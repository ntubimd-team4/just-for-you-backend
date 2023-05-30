package tw.edu.ntub.imd.justforyou.databaseconfig.dao.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import tw.edu.ntub.birc.common.util.StringUtils;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.UserAccount;
import tw.edu.ntub.imd.justforyou.databaseconfig.entity.UserAccount_;
import tw.edu.ntub.imd.justforyou.databaseconfig.enumerate.Role;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserAccountSpecification {
    public Specification<UserAccount> checkBlank(String type) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(type)) {
                predicates.add(criteriaBuilder.equal(root.get(UserAccount_.ROLE), Role.of(type)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}