package cn.org.ferry.core.interceptors;

import cn.org.ferry.core.dto.FerryRequest;
import cn.org.ferry.core.dto.FerrySession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>description
 *
 * @author ferry ferry_sy@163.com
 * created by 2019/11/19 16:59
 */

public class RegisterRequestInterceptor implements HandlerInterceptor {
//    @Autowired
//    private SysUserService sysUserService;

    private FerrySession ferrySession;

    private FerryRequest ferryRequest;

    public RegisterRequestInterceptor(FerrySession ferrySession, FerryRequest ferryRequest){
        this.ferrySession = ferrySession;
        this.ferryRequest = ferryRequest;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*String _token = request.getHeader(ConstantUtils._TOKEN);
        if(StringUtils.isNotEmpty(_token) && StringUtils.isEmpty(ferrySession.getSessionId())){
            SysUser user;
            try{
                String userCode = JWT.decode(_token).getAudience().get(0);
                user = sysUserService.queryByUserCode(userCode);
            }catch (JWTDecodeException e){
                throw new TokenException("Invalid token");
            }
            ferrySession.setSessionId(request.getRequestedSessionId());
            ferrySession.setUserId(user.getUserId().toString());
            ferrySession.setUserCode(user.getUserCode());
            ferrySession.setUserNameEn(user.getUserNameEn());
            ferrySession.setUserNameZh(user.getUserNameZh());

            HttpSession session = request.getSession();
            Enumeration<String> enumeration = session.getAttributeNames();
            while (enumeration.hasMoreElements()){
                String key = enumeration.nextElement();
                ferrySession.put(key, session.getAttribute(key));
            }
        }
        ferryRequest.setToken(_token);
        Enumeration<String> enumeration = request.getAttributeNames();
        while (enumeration.hasMoreElements()){
            String key = enumeration.nextElement();
            ferryRequest.put(key, request.getAttribute(key));
        }
        return true;*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
