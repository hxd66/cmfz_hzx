package com.baizhi.action;

import com.baizhi.api.BaseApiService;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/article")
public class ArticleAction extends BaseApiService {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/showall")
    @ResponseBody
    public Map<String, Object> showAll(Integer page, Integer rows) {
        return articleService.selectAll(page, rows);
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Map<String, Object> insert(String oper, Article article, HttpSession session) {
        if ("add".equals(oper)) {
            article.setId(UUID.randomUUID().toString());
            return articleService.addOne(article);
        } else if ("edit".equals(oper)) {
            return articleService.update(article);
        } else if ("del".equals(oper)) {
            return articleService.del(article);
        }
        return null;
    }

    @RequestMapping("exportExcel")
    @ResponseBody
    public Map<String, Object> exportExcel(HttpServletResponse response) {
        List<Article> articles = articleService.selectAll();
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表
        HSSFSheet sheet = workbook.createSheet("文章信息");
        //创建标题行
        HSSFRow row = sheet.createRow(0);
        String[] title = {"编号", "标题", "作者", "内容"};
        HSSFCell cell = null;
        for (int i = 0; i < title.length; i++) {
            row.createCell(i).setCellValue(title[i]);
        }
        for (int i = 0; i < articles.size(); i++) {
            HSSFRow rows = sheet.createRow(i + 1);
            rows.createCell(0).setCellValue(articles.get(i).getId());
            rows.createCell(1).setCellValue(articles.get(i).getTitle());
            rows.createCell(2).setCellValue(articles.get(i).getAuthor());
            rows.createCell(3).setCellValue(articles.get(i).getContent());
        }
        try {
            String filename = URLEncoder.encode("article.xls", "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + filename);
            response.setContentType("application/vnd.ms-excel");
            workbook.write(response.getOutputStream());
            workbook.close();
            return setResultSuccess();
        } catch (IOException e) {
            e.printStackTrace();
            return setResultError();
        }
    }

    @RequestMapping("importArticle")
    public Map<String, Object> importArticle() {
        return null;
    }
}
