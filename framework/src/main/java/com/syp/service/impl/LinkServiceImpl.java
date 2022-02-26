package com.syp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.syp.constants.SystemConstants;
import com.syp.domain.ResponseResult;
import com.syp.domain.entity.Link;
import com.syp.domain.vo.linkVo;
import com.syp.mapper.LinkMapper;
import com.syp.service.LinkService;
import com.syp.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询审核通过的友链
        LambdaQueryWrapper<Link> query=new LambdaQueryWrapper();
        query.eq(Link::getStatus, SystemConstants.LINk_STATUS_NORMAL);
        List<Link> links=list(query);
        //转换成vo
        List<linkVo> linkVos = BeanCopyUtils.copyBeanList(links, linkVo.class);

        return ResponseResult.okResult(linkVos);
    }
}
