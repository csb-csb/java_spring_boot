package com.sfac.javaSpringBoot.modules.test.controller;

import com.sfac.javaSpringBoot.modules.test.entity.City;
import com.sfac.javaSpringBoot.modules.test.entity.Country;
import com.sfac.javaSpringBoot.modules.test.service.CityService;
import com.sfac.javaSpringBoot.modules.test.service.CountryService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;


@Controller
@RequestMapping("/test")
public class TestController {
    //Value读取配置文件的值
    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);
    @Value("${server.port}")
    private int port;
    @Value("${com.name}")
    private String name;
    @Value("${com.age}")
    private int age;
    @Value("${com.desc}")
    private String desc;
    @Value("${com.random}")
    private String random;

   @Autowired
    private ApplicationTest applicationTest;
    @Autowired
    private CountryService countryService;
    @Autowired
    private CityService cityService;




    /*
    127.0.0/test/index
    */
   @GetMapping("/indexSimple")
    public String indexSimple(ModelMap map){
      // map.addAttribute("tempate","test/index");

      //返回到外层的组装器
        return "indexSimple";
    }

  @GetMapping("/index2")
    public String testIndex2Page(ModelMap map){
        map.addAttribute("template","test/index2");

        //返回到外层的组装器
        return "index";
    }
@GetMapping("/download1")
public void downloadFile1(HttpServletRequest request, HttpServletResponse response,@RequestParam String fileName) throws IOException {
       String filePath="c:/upload"+File.separator+fileName;
       File downloadFile=new File(filePath);
       if (downloadFile.exists()){
           response.setContentType("application/octet-stream");
           response.setContentLength((int) downloadFile.length());
           response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                   String.format("attachment;filename=\"%s\"",fileName));

           byte[] buffer=new byte[1024];
           FileInputStream fis=null;
           BufferedInputStream bis=null;

           try {
               fis=new FileInputStream(downloadFile);
               bis=new BufferedInputStream(fis);
               OutputStream os=response.getOutputStream();
               int i=bis.read(buffer);
               while (i!=-1){
                   os.write(buffer,0,i);
                   i=bis.read(buffer);
               }
           } catch (FileNotFoundException e) {
               LOGGER.debug(e.getMessage());
               e.printStackTrace();
           }finally {
               try {
                   if (fis!=null){
                       fis.close();
                   }
                   if (bis != null) {
                       bis.close();
                   }
               }catch (Exception e2){
                   LOGGER.debug(e2.getMessage());
                   e2.printStackTrace();

               }

           }

       }

}

    /**
     * 以包装类 IOUtils 输出文件
     */
    @RequestMapping("/download2")
    public void downloadFile2(HttpServletRequest request,
                              HttpServletResponse response, @RequestParam String fileName) {
        String filePath = "C:/upload" + File.separator + fileName;
        File downloadFile = new File(filePath);

        try {
            if (downloadFile.exists()) {
                String aa = new String(fileName.getBytes("utf-8"), "ISO-8859-1");

                response.setContentType("application/octet-stream");
                response.setContentLength((int) downloadFile.length());
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("attachment;filename=\"%s\"",aa));

                InputStream is = new FileInputStream(downloadFile);
                IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
            }
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
        }
    }

//该方法用的会比较多
    @PostMapping(value = "/file",consumes = "multipart/form-data")
    public String uploadFile(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes) {
       //先判断文件非空
        if (file.isEmpty()){
           redirectAttributes.addFlashAttribute("message","please select file");
           return "redirect:/test/index";
       }
       try {
           //写入传输的路径和文件名
       String destFilePath="c:\\upload\\"+file.getOriginalFilename();
       //对应的文件相关信息和路径传入新的文件对象
        File destFile=new File(destFilePath);
        //载入文件，蒋带有信息的文件转到新的位置
        file.transferTo(destFile);
           redirectAttributes.addFlashAttribute(
                   "message", "Upload file success.");
       }catch (IOException e) {
           e.printStackTrace();
           redirectAttributes.addFlashAttribute(
                   "message", "Upload file failed.");

       }
        return "redirect:/test/index";

    }

    @PostMapping(value = "/files",consumes = "multipart/form-data")
    public String uploadFiles(@RequestParam MultipartFile[] files,
                              RedirectAttributes redirectAttributes) {

       boolean empty=true;
        try {
       for (MultipartFile file:files){
           //同样要判断为空
           if (file.isEmpty()){
               continue;
           }
           //获取上传的路径和文件名
               String destFilePath="c:\\upload\\"+file.getOriginalFilename();
           //文件信息传入新的文件对象
               File destFile=new File(destFilePath);
               //文件传输
               file.transferTo(destFile);
               empty=false;
       }if (empty){
                redirectAttributes.addFlashAttribute(
                        "message", "Please select file.");
            } else {
                redirectAttributes.addFlashAttribute(
                        "message", "Upload file success.");
            }
           } catch (IOException e) {
            redirectAttributes.addFlashAttribute(
                    "message", "Upload file failed.");
               e.printStackTrace();
           }
       return "redirect:/test/index";
    }


     // 127.0.0.1/test/index

    @GetMapping("/index")
    public String testIndexPage(ModelMap modelMap) {
        int countryId = 522;
        List<City> cities = cityService.getCitesByCountryId(countryId);
        cities = cities.stream().limit(10).collect(Collectors.toList());
        Country country = countryService.getCountryByCountryId(countryId);

        modelMap.addAttribute("thymeleafTitle", "scdscsadcsacd");
        modelMap.addAttribute("checked", true);
        modelMap.addAttribute("currentNumber", 99);
        modelMap.addAttribute("changeType", "checkbox");
        modelMap.addAttribute("baiduUrl", "/test/log");
        modelMap.addAttribute("city", cities.get(0));
        modelMap.addAttribute("shopLogo",
                "http://cdn.duitang.com/uploads/item/201308/13/20130813115619_EJCWm.thumb.700_0.jpeg");
        modelMap.addAttribute("shopLogo",
                "/upload/1111.png");
        modelMap.addAttribute("country", country);
        modelMap.addAttribute("cities", cities);
        modelMap.addAttribute("updateCityUri", "/api/city");
        //modelMap.addAttribute("template", "test/index");
        // 返回外层的碎片组装器
        return "index";
    }




    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }





    @GetMapping("/logTest")
    @ResponseBody
    public String logTest() {
        LOGGER.trace("This is trace log");
        LOGGER.debug("This is debug log");
        LOGGER.info("This is info log");
        LOGGER.warn("This is warn log");
        LOGGER.error("This is error log");
        return "This is log test11111";
    }

    @GetMapping("/config")
    @ResponseBody
    public String configTest() {
        StringBuffer sb = new StringBuffer();
        sb.append(port).append("----")
                .append(name).append("----")
                .append(age).append("----")
                .append(desc).append("----")
                .append(random).append("----").append("<br>");
        sb.append(applicationTest.getPort()).append("----")
                .append(applicationTest.getName()).append("----")
                .append(applicationTest.getAge()).append("----")
                .append(applicationTest.getDesc()).append("----")
                .append(applicationTest.getRandom()).append("----").append("<br>");

        return sb.toString();
    }


        @GetMapping("/testDesc")
         @ResponseBody//代表接口返回类型
        public String testDesc(HttpServletRequest request,
                               @RequestParam(value = "paramKey") String paramValue) {
            String paramValue2 = request.getParameter("paramKey");
            return "This is test module desc." + paramValue + "==" + paramValue2;
        }

}
