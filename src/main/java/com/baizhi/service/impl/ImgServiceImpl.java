package com.baizhi.service.impl;

import com.baizhi.dao.ImgDAO;
import com.baizhi.entity.Img;
import com.baizhi.redis.RedisCache;
import com.baizhi.service.ImgService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ImgServiceImpl implements ImgService {
    @Autowired
    private ImgDAO imgDAO;

    @Override
    public void addOne(Img img) {
        imgDAO.insert(img);
    }

    @Override
    @RedisCache
    public List<Img> selectAll(Integer page, Integer rows) {
        RowBounds rowBounds = new RowBounds((page - 1) * rows, rows);
        List<Img> imgs = imgDAO.selectByRowBounds(new Img(), rowBounds);
        return imgs;
    }

    @Override
    public Integer getCount() {
        int i = imgDAO.selectCount(new Img());
        return i;
    }

    @Override
    public void upload(Img img) {
        imgDAO.updateByPrimaryKeySelective(img);
    }

    @Override
    public void del(Img img) {
        imgDAO.delete(img);
    }

    public List<Img> selectNew() {
        List<Img> imgs = imgDAO.selectNew();
        return imgs;
    }
}
