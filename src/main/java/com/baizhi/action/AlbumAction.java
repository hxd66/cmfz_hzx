package com.baizhi.action;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.api.BaseApiService;
import com.baizhi.entity.Album;
import com.baizhi.entity.Chapter;
import com.baizhi.service.AlbumService;
import com.baizhi.service.ChapterService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("album")
public class AlbumAction extends BaseApiService {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/showall")
    @ResponseBody
    public Map<String, Object> showAll(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        List<Album> albums = albumService.selectAll(page, rows);
        map.put("rows", albums);
        Integer count = albumService.getCount();
        Integer pageCount = count % rows == 0 ? count / rows : count / rows + 1;
        map.put("total", pageCount);
        map.put("records", count);
        map.put("page", page);
        return map;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Map<String, Object> insert(String oper, Album album, HttpSession session) {

        if ("add".equals(oper)) {
            Date date = new Date();
            java.sql.Date date1 = new java.sql.Date(date.getTime());
            album.setCreateDate(date1);
            album.setChapterCount(0);
            album.setId(UUID.randomUUID().toString());
            albumService.addOne(album);
            return setResultSuccessData(album.getId());
        } else if ("edit".equals(oper)) {
            albumService.upload(album);
            return setResultSuccessData(album.getId());
        } else if ("del".equals(oper)) {
            albumService.del(album);
            return setResultSuccessData(album.getId());
        }
        return null;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public void upload(String id, MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            String realPath = request.getRealPath("/upload-mp3");
            try {
                file.transferTo(new File(realPath, file.getOriginalFilename()));//文件上传
            } catch (IOException e) {
                e.printStackTrace();
            }
            Album album = new Album();
            album.setId(id);
            album.setCover(file.getOriginalFilename());
            albumService.upload(album);
        }

    }

    @RequestMapping("exportAlbum")
    @ResponseBody
    public Map<String, Object> exportExcel(HttpServletResponse response) {
        List<Album> selectall = albumService.selectall();
        for (int i = 0; i < selectall.size(); i++) {
            Album album = selectall.get(i);
            album.setCover("D:\\frame\\IDEA\\IDEACode\\cmfz_hzx\\src\\main\\webapp\\upload-img\\" + album.getCover());
            Chapter chapter = new Chapter();
            chapter.setAlbumId(album.getId());
            List<Chapter> chapters = chapterService.selectAll(chapter);
            album.setChapters(chapters);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("专辑", "学生"),
                Album.class, selectall);
        try {
            String filename = URLEncoder.encode("album.xls", "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + filename);
            response.setContentType("application/vnd.ms-excel");
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
