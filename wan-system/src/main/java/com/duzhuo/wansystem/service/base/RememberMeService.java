package  com.duzhuo.wansystem.service.base;

import com.duzhuo.common.core.base.BaseService;
import com.duzhuo.common.exception.ServiceException;
import com.duzhuo.common.utils.ServletUtils;
import com.duzhuo.wansystem.dao.base.RememberMeDao;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.entity.base.RememberMe;
import com.duzhuo.wansystem.shiro.ShiroUtils;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 记住我--Service
 * @email: 1434495271@qq.com
 * @author: 万宏远
 * @date: 2021/03/20 14:22:29
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class RememberMeService extends BaseService<RememberMe, Long> {
    /**
     * remember-me
     */
    public static final String REMEMBER_COOKIES_NAME = "remember-me";

    @Resource
    private RememberMeDao rememberMeDao;

    @Resource
    public void setBaseDao(RememberMeDao rememberMeDao){
        super.setBaseDao(rememberMeDao);
    }

    /**
     * 从cookies获取admin对象
     * @param request
     * @return
     */
    public Admin getCookAdmin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = ServletUtils.getCookiesValue(cookies,REMEMBER_COOKIES_NAME);
        if (cookie==null){
            return null;
        }
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 获取客户ip
        String ipAddress = null;
        try {
            ipAddress = ServletUtils.getIpAddress(request);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            return null;
        }
        RememberMe rememberMe = findByToken(cookie.getValue());
        if (rememberMe==null){
            return null;
        }
        //防止cookies盗用
        Boolean b  = rememberMe.getIp().equals(ipAddress)&&rememberMe.getBrowser().equals(browser)&&rememberMe.getOs().equals(os);
        if (b){
            return rememberMe.getAdmin();
        }
        return null;
    }

    /**
     * 记住我--数据校验
     * @param rememberMeVO
     */
    private void check(RememberMe rememberMeVO){
        //throw new ServiceException("功能暂未完成！");
    }

    /**
     * 根据token查询Remember
     * @param token
     * @return
     */
    private RememberMe findByToken(String token){
        return rememberMeDao.findByToken(token);
    }


    /**
     * 记住我--新增
     * @param rememberMeVO
     * @return
     */
    public void addData(RememberMe rememberMeVO) {
        super.validation(rememberMeVO);
        this.check(rememberMeVO);
        super.save(rememberMeVO);
    }

    /**
     * 记住我--删除
     * @param id
     * @return
     */
    public void del(Long id) {
        if (id==null){
            throw new ServiceException("请选择数据！");
        }
        super.delete(id);
    }

    /**
     * 记住我
     * @param rem
     */
    public void rememberMe(HttpServletRequest request, HttpServletResponse response, Boolean rem) {
        if (!rem){
            return;
        }
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        // 获取客户ip
        String ipAddress = null;
        try {
            ipAddress = ServletUtils.getIpAddress(request);
        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        String token = UUID.randomUUID().toString();
        RememberMe rememberMe = new RememberMe();
        rememberMe.setAdmin(ShiroUtils.getCurrAdmin());
        rememberMe.setBrowser(browser);
        rememberMe.setOs(os);
        rememberMe.setIp(ipAddress);
        rememberMe.setToken(token);
        Cookie cookie = new Cookie(REMEMBER_COOKIES_NAME,token);
        cookie.setMaxAge(60*60*24*60);
        cookie.setPath("/");
        response.addCookie(cookie);
        this.addData(rememberMe);
    }

    /**
     *
     * @param admin
     */
    public void removeByAdmin(Admin admin){
        List<RememberMe> rememberMeList = rememberMeDao.findByAdmin(admin);
        if (!rememberMeList.isEmpty()){
            super.delete(rememberMeList);
        }
    }
}
