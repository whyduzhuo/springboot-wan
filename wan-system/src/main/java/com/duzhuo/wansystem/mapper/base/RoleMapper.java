package com.duzhuo.wansystem.mapper.base;

import com.duzhuo.common.core.CustomSearch;
import com.duzhuo.common.enums.IsDelete;
import com.duzhuo.common.utils.SQLUtils;
import com.duzhuo.wansystem.entity.base.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author: wanhy
 * @date: 2020/8/8 15:10
 */
@Repository
public class RoleMapper {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public Page<Admin> showAdmin(Long roleId, Map<String, Object> searchParams, CustomSearch<Admin> customSearch) {
        StringBuilder sql = new StringBuilder();

        sql.append("select t2.id,T2.USERNAME,T2.REALNAME,T2.IS_DELETE from T_BASE_ADMIN_ROLE t1\n" +
                "left join T_BASE_ADMIN t2 on T1.ADMIN_ID = T2.ID\n" +
                "where T1.ROLE_ID = ?");
        Long total = jdbcTemplate.queryForObject(SQLUtils.countOracle(sql.toString()), Long.class,roleId);
        String pageSql = SQLUtils.pageOracle(sql.toString(),customSearch.getPageNumber(),customSearch.getPageSize());
        List<Admin> adminList = jdbcTemplate.query(pageSql, (rs, rowNum) -> {
            Admin admin = new Admin();
            admin.setId(rs.getLong(1));
            admin.setUsername(rs.getString(2));
            admin.setRealname(rs.getString(3));
            admin.setIsDelete(IsDelete.values()[rs.getInt(4)]);
            return admin;
        },roleId);
        Pageable pageable = PageRequest.of(customSearch.getPageNumber() - 1, customSearch.getPageSize());
        customSearch.setPagedata(new PageImpl<>(adminList, pageable, total));
        return customSearch.getPagedata();
    }
}
