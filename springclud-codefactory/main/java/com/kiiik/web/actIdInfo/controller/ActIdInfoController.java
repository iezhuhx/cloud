package com.kiiik.web.actIdInfo.controller;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.kiiik.pub.bean.Page;
import com.kiiik.pub.bean.R;
import com.kiiik.pub.mybatis.service.GenericService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import com.kiiik.web.actIdInfo.entity.ActIdInfoEntity;
import com.kiiik.web.actIdInfo.service.ActIdInfoService;

/**
 * <br>
 * 请求控制层<br>
 * 作者: iechenyb<br>
 * 邮件: zzuchenyb@sina.com<br>
 * 日期: 2019-07-03 16:46:48<br>
 */
@RestController
@RequestMapping("actIdInfo")
@Api(value = "**管理", description = "***基本信息操作API", tags = "ActIdInfoApi")
public class ActIdInfoController {

	Log log = LogFactory.getLog(ActIdInfoController.class);
	/**
	*通用的service接口
	**/
	@Autowired
	GenericService genericService;
	/**
	*服务接口
	**/
    @Autowired
    private ActIdInfoService actIdInfoService;

   
    /**
     * 
     *作者 : iechenyb<br>
     *数据列表<br>
     *创建时间: 2019-07-03 16:46:48<br>
     *@param 
     *@return
     */
    @SuppressWarnings("unchecked")
    @GetMapping("/list")
    @ApiOperation(value = "/list", notes = "列表信息 ")
    public R<List<ActIdInfoEntity>> list(@RequestBody ActIdInfoEntity entity){
       List<ActIdInfoEntity> entitys = genericService.queryDBEntityList(entity);
        return new R<List<ActIdInfoEntity>>(entitys).success();
    }
    
     /**
     * 
     *作者 : iechenyb<br>
     *数据列表分页查询<br>
     *创建时间: 2019-07-03 16:46:48<br>
     *@param 
     *@return
     */
    @SuppressWarnings("unchecked")
	@GetMapping("listPage")
	@ApiOperation(value = "/listPage", notes = "分页查询 ")
	public R<List<ActIdInfoEntity>> listUsersPage(ActIdInfoEntity entity,@ModelAttribute @Validated Page page) {
		List<ActIdInfoEntity> entitys = genericService.queryDBEntityList(entity, page.getPageNum(), page.getPageSize(), " id asc");
		return new R<List<ActIdInfoEntity>>(entitys).success();
	}
    
    /**
     * 
     *作者 : iechenyb<br>
     *新增记录<br>
     *创建时间: 2019-07-03 16:46:48<br>
     *@param 
     *@return
     */
	@PutMapping("add")
	@ApiOperation(value="add",notes="新增信息")
	public R<String> addActIdInfoEntity(@RequestBody ActIdInfoEntity entity){
		return actIdInfoService.addActIdInfoEntity(entity);
		
	}
	
	/**
     * 
     *作者 : iechenyb<br>
     *更新记录<br>
     *创建时间: 2019-07-03 16:46:48<br>
     *@param 
     *@return
     */
	@PostMapping("update")
	@ApiOperation(value="update",notes="更新信息")
	public R<String> updActIdInfoEntity(@RequestBody ActIdInfoEntity entity){
		return actIdInfoService.updActIdInfoEntity(entity);
	}
	
	 /**
     * 
     *作者 : iechenyb<br>
     *根据主键删除记录<br>
     *创建时间: 2019-07-03 16:46:48<br>
     *@param 
     *@return
     */
	@DeleteMapping("deleteById")
	@ApiOperation(value="deleteById",notes="根据主键删除信息")
	public R<String> delActIdInfoEntity(Integer id){
	    return actIdInfoService.delActIdInfoEntity(id);
	}
    /**
     *
     *作者 : iechenyb<br>
     *方法描述: 删除信息<br>
     *创建时间: 2018年11月20日
     *@param id
     *@return
     */
    @DeleteMapping("{id}")
    @ApiOperation("删除信息，支持批量")
    public R<String> delete(@PathVariable String[] id){
        log.info("delete");
        List<String> ids = new ArrayList<String>();
        for(int i=0;i<id.length;i++){
            ids.add(id[i]);
			actIdInfoService.delActIdInfoEntity(id);
        }
        return new R<String>(ids.toString()).success();
    }

}
