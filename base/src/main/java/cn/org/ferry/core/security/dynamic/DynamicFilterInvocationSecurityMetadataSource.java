package cn.org.ferry.core.security.dynamic;

import cn.org.ferry.sys.dto.SysResource;
import cn.org.ferry.sys.service.SysResourceService;
import cn.org.ferry.sys.service.SysRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>动态元数据加载器
 *
 * @author ferry ferry_sy@163.com
 * created by 2020/06/06 21:02
 */

public class DynamicFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(DynamicFilterInvocationSecurityMetadataSource.class);

    @Autowired
    private SysResourceService sysResourceService;
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        final HttpServletRequest request = ((FilterInvocation) object).getRequest();
        Set<RequestMatcher> requestMatchers = sysResourceService.queryAllEnabledResourceByType(SysResource.RESOURCE_TYPE_REST)
                .stream().map(AntPathRequestMatcher::new).collect(Collectors.toSet());
        RequestMatcher reqMatcher = requestMatchers.stream().filter(
                requestMatcher -> requestMatcher.matches(request)
        ).findAny().orElseThrow(() -> new AccessDeniedException("Undefined resources [" + request.getRequestURI() + "]."));
        AntPathRequestMatcher antPathRequestMatcher = (AntPathRequestMatcher) reqMatcher;
        List<String> roles = sysRoleService.obtainEnabledRolesByPattern(SysResource.RESOURCE_TYPE_REST, antPathRequestMatcher.getPattern());
        if(CollectionUtils.isEmpty(roles)){
            throw new AccessDeniedException("Unauthorized resources 【" + antPathRequestMatcher.getPattern() + "】.");
        }
        return SecurityConfig.createList(roles.toArray(new String[roles.size()]));
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        List<String> roles = sysRoleService.queryAllEnabledRoleCode();
        return CollectionUtils.isEmpty(roles) ? null : SecurityConfig.createList(roles.toArray(new String[0]));
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
