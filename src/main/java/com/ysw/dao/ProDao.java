package com.ysw.dao;

import com.ysw.entity.Cate;
import com.ysw.entity.Pro;
import com.ysw.entity.ProDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProDao {

    /**
     * 根据类别进行分类
     *
     * @param cid
     * @return
     */
    List<Pro> findByCid(Integer cid);

    /**
     * 根据cid查询产品个数
     *
     * @param cid
     * @return
     */
    Integer getCountByCid(Integer cid);

    /**
     * 根据产品名查询产品的个数
     *
     * @param name
     * @return
     */
    Integer getCountByName(String name);

    /**
     * 根据产品id查询产品的详细信息
     *
     * @param id
     * @return
     */
    Pro getProById(Integer id);

    /**
     * 根据产品名进行模糊查询
     *
     * @param pName
     * @return
     */
    List<Pro> findProByName(String pName);

    /**
     * 查询所有的产品集合
     *
     * @return
     */
    List<ProDetail> findAll();

    /**
     * 返回总的数据数
     *
     * @return
     */
    Integer getTotal();

    /**
     * 根据id删除产品
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 查询所有的产品类别
     *
     * @return
     */
    List<Cate> getAllCid();

    /**
     * 根据产品更新产品
     *
     * @param pro
     */
    void updateById(Pro pro);

    /**
     * 新增产品
     *
     * @param pro
     */
    void addPro(Pro pro);
}
