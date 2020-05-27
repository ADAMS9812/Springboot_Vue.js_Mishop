package com.ysw.controller;

import com.github.pagehelper.PageHelper;
import com.ysw.entity.Cate;
import com.ysw.entity.Pro;
import com.ysw.entity.ProDetail;
import com.ysw.service.ProService;
import com.ysw.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

//RestController是controller和responsebody的结合

/**
 * 产品模块的controller层
 */

@RestController
@RequestMapping("/pro")
public class ProController {

    @Autowired
    private ProService proService;

    /**
     * 根据cid进行产品的分页操作
     *
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/findByCid/{cid}")
    public List<Pro> findByCid(@PathVariable Integer cid,
                               @RequestParam(name = "currentPage", defaultValue = "1") Integer currentPage,
                               @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){

        //使用分页工具进行分页操作,默认第一页,每一页有10条数据
        PageHelper.startPage(currentPage,pageSize);

        //根据cid查询所有的pro集合,返回一个带有数据的pro集合
        return proService.findByCid(cid);
    }

    /**
     * 获取总页数
     *
     * @return
     */
    @GetMapping("/getTotalPageByCid/{cid}")
    public Integer getTotalPageByCid(@PathVariable Integer cid){

        //总页数 = 总记录数 / 每一页的pageSize（10），向上取整
        int totalPage = (int) (Math.ceil(proService.getCountByCid(cid)*1.0/10));

        //返回总页数
        return totalPage;
    }

    /**
     * 获取总页数
     *
     * @return
     */
    @GetMapping("/getTotalPageByName/{pName}")
    public Integer getTotalPageByName(@PathVariable String pName){

        //总页数 = 总记录数 / 每一页的pageSize（10），向上取整
        int totalPage = (int) (Math.ceil(proService.getCountByName(pName)*1.0/10));

        //返回总页数
        return totalPage;
    }

    /**
     * 根据id查询产品
     *
     * @param id
     * @return
     */
    @GetMapping("/getProById/{id}")
    public Pro getProById(@PathVariable Integer id){

        //根据id查询pro产品
        Pro pro = proService.getProById(id);

        //返回一个带有数据的pro对象
        return pro;
    }

    /**
     * 根据产品名进行模糊查询
     *
     * @param pName
     * @return
     */
    @GetMapping("/getProByName/{pName}")
    public List<Pro> findProByName(@PathVariable String pName,
                                   @RequestParam(name = "currentPage", defaultValue = "1") Integer currentPage,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize){

        //使用分页工具进行分页操作,默认第一页,每一页有10条数据
        PageHelper.startPage(currentPage,pageSize);

        //返回分页好的数据
        return proService.findProByName(pName);
    }

    /**
     * 根据指定数据列的方式展示所有的pro产品
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/findAll")
    public List<ProDetail> findAll(@RequestParam(value = "currentPage",defaultValue = "1")Integer currentPage,
                                   @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){

        //分页工具
        PageHelper.startPage(currentPage,pageSize);

        //查询所有的产品并返回集合
        List<ProDetail> proDetailList = proService.findAll();

        //返回集合给前端进行数据渲染
        return proDetailList;

    }

    /**
     * 计算总页数
     *
     * @return
     */
    @GetMapping("/getTotalPage")
    public Integer getTotalPage(){
        //获取总数据数
        Integer totalCount = proService.getTotal();
        //获取总页数
        Integer totalPage = (int) Math.ceil(totalCount*1.0/10);

        //返回总页数
        return totalPage;
    }

    /**
     * 根据id删除产品
     *
     * @param id
     * @return
     */
    @GetMapping("/deleteById/{id}")
    public String deleteById(@PathVariable Integer id){

        try {
            //根据id删除
            proService.deleteById(id);
        } catch (Exception e) {
            return "ERROR";
        }

        return "OK";
    }

    /**
     * 获取所有的类别
     *
     * @return
     */
    @GetMapping("/getAllCid")
    public List<Cate> getAllCid(){
        return proService.getAllCid();
    }

    /**
     * 根据产品id更新产品
     *
     * @param proid
     * @param pName
     * @param iPrice
     * @param pNum
     * @param cid
     * @param pImg
     * @param xqImg
     * @return
     */
    @GetMapping("/updateById/{proid}")
    public String updateById(@PathVariable Integer proid,String pName,
                             Double iPrice, Integer pNum, Integer cid,
                             String pImg, String xqImg, HttpServletRequest request){

        //初始化session
        HttpSession session = request.getSession();

        String proimages_uuid = "";     //封面的uuid
        String xqimages_uuid = "";      //详情的uuid

        //获取uuid
        if (session.getAttribute("proimages_uuid") != null) {
            proimages_uuid = (String) session.getAttribute("proimages_uuid");
        }

        if (session.getAttribute("xqimg_uuid") != null) {
            xqimages_uuid = (String) session.getAttribute("xqimg_uuid");
        }

        //初始化一个pro对象进行更新的操作
        Pro pro = new Pro();

        String[] array_pImg;
        String current_pImg = "";
        //两张图片的路径要处理一下
        if (pImg != null && !"".equals(pImg)) {
            array_pImg = pImg.split("\\\\");
            //取出最后一个字符串赋值给当前pImg
            current_pImg = "proimages/" + proimages_uuid +array_pImg[array_pImg.length - 1];
        }

        String[] array_xqImg;
        String current_xqImg = "";
        if (xqImg != null && !"".equals(xqImg)) {
            //详情图片地址的切割
            array_xqImg = xqImg.split("\\\\");
            //取出最后一个字符串赋值给当前pImg
            current_xqImg = "xqimg/" + xqimages_uuid + array_xqImg[array_xqImg.length - 1];
        }

        //如果用户没有上传产品图片的话就不用更新数据库
        Pro old_pro = proService.getProById(proid);

        //如果说pImg图片路径为空的话就把旧图片的地址赋值给pImg
        if (pImg == null || "".equals(pImg)) {
            current_pImg = old_pro.getpImg();
        }

        //如果说xqImg图片路径为空的话就把旧图片的地址赋值给xqImg
        if (xqImg == null || "".equals(xqImg)) {
            current_xqImg = old_pro.getXqImg();
        }

        pro.setId(proid);
        pro.setpName(pName);
        pro.setiPrice(iPrice);
        pro.setpNum(pNum);
        pro.setCid(cid);
        pro.setpImg(current_pImg);
        pro.setXqImg(current_xqImg);

        //System.out.println(pro);        //输出测试

        //根据对象来更新产品
        try {
            proService.updateById(pro);
        } catch (Exception e) {
            return "ERROR";
        }

        return "OK";
    }

    /**
     * 用于产品封面上传的方法
     *
     * @param upload
     * @throws IOException
     */
    @PostMapping("/uploadpImg")
    public String uploadpImg(MultipartFile upload, HttpServletRequest request) throws IOException {

        //初始化session
        HttpSession session = request.getSession();

        //使用fileupload组件完成文件上传
        //上传的位置要指定
        //指定到static目录下的
        String path = ClassUtils.getDefaultClassLoader().getResource("static/proimages").getPath();

        //判断该路径是否存在
        File file = new File(path);
        if (!file.exists()) {

            //如果这个文件夹不存在的话,就创建这个文件
            file.mkdirs();
        }

        //获取上传文件名称
        String filename = upload.getOriginalFilename();

        //把文件名称设置成唯一值 uuid 以防止文件名相同覆盖
        String uuid = UUID.randomUUID().toString().replace("-","");
        //新文件名
        filename = uuid + "_" + filename;

        session.setAttribute("proimages_uuid",uuid + "_");

        //完成文件上传
        upload.transferTo(new File(path,filename));
        //System.out.println(path + filename);

        return "上传成功！";
    }

    /**
     * 用于产品封面上传的方法
     *
     * @param upload
     * @throws IOException
     */
    @PostMapping("/uploadxqImg")
    public String uploadxqImg(MultipartFile upload, HttpServletRequest request) throws IOException {

        //初始化session
        HttpSession session = request.getSession();

        //使用fileupload组件完成文件上传
        //上传的位置要指定
        //指定到static目录下的
        String path = ClassUtils.getDefaultClassLoader().getResource("static/xqimg").getPath();

        //判断该路径是否存在
        File file = new File(path);
        if (!file.exists()) {
            //如果这个文件夹不存在的话,就创建这个文件
            file.mkdirs();
        }

        //获取上传文件名称
        String filename = upload.getOriginalFilename();

        //把文件名称设置成唯一值 uuid 以防止文件名相同覆盖
        String uuid = UUID.randomUUID().toString().replace("-","");
        //新文件名
        filename = uuid + "_" + filename;

        session.setAttribute("xqimg_uuid",uuid + "_");

        //完成文件上传
        upload.transferTo(new File(path,filename));
        //System.out.println(path + filename);

        return "上传成功！";
    }

    @GetMapping("/addPro")
    public String addPro(String pName, String pSn, Double mPrice, Double iPrice, Integer pNum, Integer cid,
                         String pImg, String xqImg, HttpServletRequest request){

        //初始化session
        HttpSession session = request.getSession();

        String proimages_uuid = "";     //封面的uuid
        String xqimages_uuid = "";      //详情的uuid

        //获取uuid
        if (session.getAttribute("proimages_uuid") != null) {
            proimages_uuid = (String) session.getAttribute("proimages_uuid");
        }

        if (session.getAttribute("xqimg_uuid") != null) {
            xqimages_uuid = (String) session.getAttribute("xqimg_uuid");
        }

        //初始化一个pro对象进行新增的操作
        Pro pro = new Pro();

        String[] array_pImg;
        String current_pImg = "";
        //两张图片的路径要处理一下
        if (pImg != null && !"".equals(pImg)) {
            array_pImg = pImg.split("\\\\");
            //取出最后一个字符串赋值给当前pImg
            current_pImg = "proimages/" + proimages_uuid +array_pImg[array_pImg.length - 1];
        }

        String[] array_xqImg;
        String current_xqImg = "";
        if (xqImg != null && !"".equals(xqImg)) {
            //详情图片地址的切割
            array_xqImg = xqImg.split("\\\\");
            //取出最后一个字符串赋值给当前pImg
            current_xqImg = "xqimg/" + xqimages_uuid + array_xqImg[array_xqImg.length - 1];
        }

        //给pro对象赋值参数
        pro.setpName(pName);
        pro.setpSn(pSn);
        pro.setmPrice(mPrice);
        pro.setiPrice(iPrice);
        pro.setpNum(pNum);
        pro.setCid(cid);
        pro.setpImg(current_pImg);
        pro.setXqImg(current_xqImg);
        pro.setPubTime(TimeUtils.getTime());

        //System.out.println(pro);        //输出测试

        //根据对象来更新产品
        try {
            //新增产品
            proService.addPro(pro);
        } catch (Exception e) {
            return "ERROR";
        }

        return "OK";

    }

}