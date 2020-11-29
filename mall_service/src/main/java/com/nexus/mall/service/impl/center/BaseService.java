package com.nexus.mall.service.impl.center;

import com.github.pagehelper.PageInfo;
import com.nexus.mall.common.api.PagedGridResult;

import java.util.List;
/**

* @Description:    分页

* @Author:         Nexus

* @CreateDate:     2020/11/29 22:31

* @UpdateUser:     Nexus

* @UpdateDate:     2020/11/29 22:31

* @UpdateRemark:   修改内容

* @Version:        1.0

*/
public class BaseService {
    public PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
