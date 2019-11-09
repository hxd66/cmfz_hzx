package com.baizhi.action;

import com.baizhi.api.BaseApiService;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("chapter")
public class ChapterAction extends BaseApiService {
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private AlbumService albumService;

    @RequestMapping("showall")
    @ResponseBody
    public Map<String, Object> showall(Integer page, Integer rows, String id) {
        HashMap<String, Object> map = new HashMap<>();
        List<Chapter> chapters = chapterService.selectAll(id, page, rows);
        map.put("rows", chapters);
        Integer count = chapterService.getCount(id);
        Integer pageCount = count % rows == 0 ? count / rows : count / rows + 1;
        map.put("records", count);
        map.put("total", pageCount);
        map.put("page", page);
        return map;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Map<String, Object> insert(String albumid, String oper, Chapter chapter, HttpSession session) {

        if ("add".equals(oper)) {
            Date date = new Date();
            java.sql.Date date1 = new java.sql.Date(date.getTime());
            chapter.setCreateDate(date1);
            chapter.setId(UUID.randomUUID().toString());
            Album album = new Album();
            album.setId(albumid);
            chapter.setAlbumId(albumid);
            Album album1 = albumService.selectOne(album);
            album1.setChapterCount(album1.getChapterCount() + 1);
            albumService.upload(album1);
            chapterService.addOne(chapter);
            return setResultSuccessData(chapter.getId());
        } else if ("edit".equals(oper)) {
            chapterService.update(chapter);
            return setResultSuccessData(chapter.getId());
        } else if ("del".equals(oper)) {
            Album album = new Album();
            album.setId(albumid);
            chapter.setAlbumId(albumid);
            Album album1 = albumService.selectOne(album);
            album1.setChapterCount(album1.getChapterCount() - 1);
            albumService.upload(album1);
            chapterService.del(chapter);
            return setResultSuccessData(chapter.getId());
        }
        return null;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public void upload(String id, MultipartFile url, HttpServletRequest request) {
        if (!url.isEmpty()) {
            String realPath = request.getRealPath("/upload-mp3");

            File file = new File(realPath, url.getOriginalFilename());
            try {
                url.transferTo(new File(realPath, url.getOriginalFilename()));//文件上传
            } catch (IOException e) {
                e.printStackTrace();
            }
            long length = 0l;
            Encoder encoder = new Encoder();
            try {
                length = encoder.getInfo(file).getDuration();
            } catch (EncoderException e) {
                e.printStackTrace();
            }
            BigDecimal size = new BigDecimal(url.getSize());
            BigDecimal mod = new BigDecimal(1024);
            //除两个1024，保留两位小数，进行四舍五入
            size = size.divide(mod).divide(mod).setScale(2, BigDecimal.ROUND_HALF_UP);
            String c = size.toString();
            Chapter chapter = new Chapter();
            chapter.setId(id);
            chapter.setSize(c);
            chapter.setDuration(length / 1000 / 60 + "分" + length / 1000 % 60 + "秒");
            chapter.setUrl(url.getOriginalFilename());
            chapterService.update(chapter);
        }

    }

}
