package  com.duzhuo.wansystem.dao.base;


import com.duzhuo.common.core.base.BaseDao;
import com.duzhuo.wansystem.entity.base.Admin;
import com.duzhuo.wansystem.entity.base.RememberMe;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 记住我--Mapper
 * @email: 1434495271@qq.com
 * @author: 万宏远
 * @date: 2021/03/20 14:22:29
 */
@Transactional(rollbackFor = Exception.class)
public interface RememberMeDao extends BaseDao<RememberMe,Long> {

    /**
     *
     * @param token
     * @return
     */
    RememberMe findByToken(String token);

    /**
     * 记住我
     * @param admin
     * @return
     */
    List<RememberMe> findByAdmin(Admin admin);

    /**
     *
     * @param value
     */
    @Query(value = "DELETE FROM t_base_remember_me WHERE token = ?1",nativeQuery = true)
    @Modifying
    void removeToken(String value);
}

