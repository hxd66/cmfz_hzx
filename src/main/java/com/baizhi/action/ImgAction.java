package com.baizhi.action;

import com.baizhi.entity.Img;
import com.baizhi.service.ImgService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("img")
public class ImgAction {
    @Autowired
    private ImgService imgService;

    @RequestMapping("/edit")
    @ResponseBody
    public String insert(String oper, Img img, HttpSession session) {

        if ("add".equals(oper)) {
            img.setId(UUID.randomUUID().toString());
            Date date = new Date();
            img.setCreateDate(new java.sql.Date(date.getTime()));
            session.setAttribute("img", img);
            imgService.addOne(img);
        } else if ("edit".equals(oper)) {
            Date date = new Date();
            img.setLastUpdateDate(new java.sql.Date(date.getTime()));
            session.setAttribute("img", img);
            imgService.upload(img);
        } else if ("del".equals(oper)) {
            imgService.del(img);
        }
        return null;
    }


    @RequestMapping("/showall")
    @ResponseBody
    public Map<String, Object> showAll(Integer page, Integer rows) {
        HashMap<String, Object> map = new HashMap<>();
        List<Img> imgs = imgService.selectAll(page, rows);
        map.put("rows", imgs);
        Integer count = imgService.getCount();
        Integer pageCount = 0;
        if (count % rows == 0) {
            pageCount = count / rows;
        } else {
            pageCount = count / rows + 1;
        }
        map.put("total", pageCount);
        map.put("records", count);
        map.put("page", page);
        return map;
    }

    @RequestMapping("/upload")
    @ResponseBody
    public void upload(MultipartFile file, HttpServletRequest request) {
        if (!file.isEmpty()) {
            String realPath = request.getRealPath("/upload-img");
            try {
                file.transferTo(new File(realPath, file.getOriginalFilename()));//文件上传
            } catch (IOException e) {
                e.printStackTrace();
            }
            Img img = (Img) request.getSession().getAttribute("img");
            img.setImage(file.getOriginalFilename());
            imgService.upload(img);
        }

    }

    @RequestMapping("/showimg")
    @ResponseBody
    public Map<String, Object> showImg(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/upload-img/";
        File file = new File(request.getSession().getServletContext().getRealPath("/upload-img"));
        File[] files = file.listFiles();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            HashMap<Object, Object> img = new HashMap<>();
            img.put("is_dir", false);
            img.put("has_file", false);
            img.put("filesize", f.length());
            img.put("is_photo", true);
            img.put("filetype", FilenameUtils.getExtension(f.getName()));
            img.put("filename", f.getName());
            img.put("datetime", new Date());
            arrayList.add(img);
        }
        map.put("current_url", url);
        map.put("total_count", files.length);
        map.put("file_list", arrayList);
        return map;
    }

    @RequestMapping("/uploadimg")
    @ResponseBody
    public Map<String, Object> uploadimg(MultipartFile imgFile, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (!imgFile.isEmpty()) {
            String realPath = request.getRealPath("/upload-img");
            try {
                imgFile.transferTo(new File(realPath, imgFile.getOriginalFilename()));//文件上传
                String url = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/upload-img/" + imgFile.getOriginalFilename();
                map.put("error", 0);
                map.put("url", url);
            } catch (IOException e) {
                map.put("error", 1);
                e.printStackTrace();
            }
        }
        return map;
    }
}
