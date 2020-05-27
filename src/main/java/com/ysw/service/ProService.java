package com.ysw.service;

import com.ysw.dao.ProDao;
import com.ysw.entity.Cate;
import com.ysw.entity.Pro;
import com.ysw.entity.ProDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProService {

    @Autowired
    private ProDao proDao;

    /**
     * 根据产品类别查询并返回所有产品
     *
     * @param cid
     * @return
     */
    public List<Pro> findByCid(Integer cid){
        return proDao.findByCid(cid);
    }

    /**
     * 根据cid查询总的记录数
     *
     * @param cid
     * @return
     */
    public Integer getCountByCid(Integer cid){
        return proDao.getCountByCid(cid);
    }

    /**
     * 根据产品名查询产品个数
     *
     * @param pName
     * @return
     */
    public Integer getCountByName(String pName) {
        //设置当前的产品名，加上"%"进行模糊查询
        String current_pName = "%" + pName + "%";
        return proDao.getCountByName(current_pName);
    }

    /**
     * 根据id查询产品pro
     *
     * @param id
     * @return
     */
    public Pro getProById(Integer id){
        return proDao.getProById(id);
    }

    /**
     * 根据pName进行模糊查询
     *
     * @param pName
     * @return
     */
    public List<Pro> findProByName(String pName){

        //设置当前的产品名，加上"%"进行模糊查询
        String current_pName = "%" + pName + "%";

        //返回一个模糊查询的集合
        return proDao.findProByName(current_pName);
    }

    /**
     * 查询所有产品的方法
     *
     * @return
     */
    public List<ProDetail> findAll(){
        return proDao.findAll();
    }

    /**
     * 获取总数据数
     *
     * @return
     */
    public Integer getTotal(){
        return proDao.getTotal();
    }

    /**
     * 根据id删除产品
     *
     * @param id
     */
    public void deleteById(Integer id){
        proDao.deleteById(id);
    }

    /**
     * 获取所有的产品类别
     *
     * @return
     */
    public List<Cate> getAllCid(){
        return proDao.getAllCid();
    }

    /**
     * 根据产品id更新产品信息
     *
     * @param pro
     */
    public void updateById(Pro pro){
        proDao.updateById(pro);
    }

    /**
     * 新增产品
     *
     * @param pro
     */
    public void addPro(Pro pro){
        proDao.addPro(pro);
    }

}