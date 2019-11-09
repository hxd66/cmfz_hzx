import com.baizhi.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class) //开启SpringBoot的测试
@SpringBootTest(classes = Application.class) //初始化SpringBoot的相关配置
public class T {
   /* try{
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(new FileInputStream("D:\\article.xls"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        HSSFSheet sheet = workbook.getSheet("书籍信息");
        HSSFRow row = null;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            System.out.println((int)row.getCell(0).getNumericCellValue()
                    + " " + row.getCell(1).getStringCellValue() + " "
                    + row.getCell(2).getDateCellValue());
        }
    }catch(IOException e) {
        e.printStackTrace();

    }*/
}
