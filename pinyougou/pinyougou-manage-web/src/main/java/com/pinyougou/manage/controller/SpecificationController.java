package com.pinyougou.manage.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.sellergoods.service.SpecificationService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;
import com.pinyougou.vo.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/specification")
@RestController
public class SpecificationController {

    @Reference
    private SpecificationService specificationService;

    @RequestMapping("/findAll")
    public List<TbSpecification> findAll() {
        return specificationService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult findPage(@RequestParam(value = "page", defaultValue = "1")Integer page,
                               @RequestParam(value = "rows", defaultValue = "10")Integer rows) {
        return specificationService.findPage(page, rows);
    }

    /**
     * 保存规格及其选项列表到数据库中
     * @param specification 规格信息（规格及选项列表）；如：
     *                  {"specificationOptionList":[{"optionName":"蓝色","orders":"1"}],"specification":{"specName":"颜色"}}
     * @return 操作结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody Specification specification) {
        try {
            specificationService.add(specification);
            return Result.ok("增加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("增加失败");
    }

    /**
     * 根据规格id到数据库中查询规格及其选项
     * @param id 规格id
     * @return 规格及其选项
     */
    @GetMapping("/findOne")
    public Specification findOne(Long id) {
        return specificationService.findOne(id);
    }

    /**
     * 规格、选项集合更新到数据库中
     * @param specification 规格及其选项
     * @return 操作结果
     */
    @PostMapping("/update")
    public Result update(@RequestBody Specification specification) {
        try {
            specificationService.update(specification);
            return Result.ok("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("修改失败");
    }

    /**
     * 删除规格及其对应的所有选项
     * @param ids 规格id集合
     * @return 操作结果
     */
    @GetMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            specificationService.deleteSpecificationByIds(ids);
            return Result.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("删除失败");
    }

    /**
     * 分页查询列表
     * @param specification 查询条件
     * @param page 页号
     * @param rows 每页大小
     * @return
     */
    @PostMapping("/search")
    public PageResult search(@RequestBody  TbSpecification specification, @RequestParam(value = "page", defaultValue = "1")Integer page,
                               @RequestParam(value = "rows", defaultValue = "10")Integer rows) {
        return specificationService.search(page, rows, specification);
    }

    /**
     * 查询规格列表；结构如：[{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
     * @return 规格列表
     */
    @GetMapping("/selectOptionList")
    public List<Map<String, String>> selectOptionList(){
        return specificationService.selectOptionList();
    }

}
