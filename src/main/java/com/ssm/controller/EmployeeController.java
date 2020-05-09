package com.ssm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.bean.Employee;
import com.ssm.bean.Msg;
import com.ssm.service.EmployeeService;

/**
 * 处理员工的crud请求
 * @author Administrator
 *
 */
@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeService employeeService;
	
	/**
	 * 员工删除,单个批量二合一
	 * 批量删除：1-2-3-4
	 * 单个删除：1
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/emp/{ids}",method = RequestMethod.DELETE)
	@ResponseBody
	public Msg deleteEmpById(@PathVariable("ids")String ids) {
		if(ids.contains("-")) {
			List<Integer> del_ids = new ArrayList<Integer>();
			String[] str_ids = ids.split("-");
			for (String string : str_ids) {
				del_ids.add(Integer.parseInt(string));
			}
			employeeService.deleteBathch(del_ids);
		}else {
			Integer id = Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		return Msg.success();
	}
	/**
	 * 员工的更新请求
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
	@ResponseBody
	public Msg savaEmp(Employee employee) {
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	
	
	
	
	
	
	/**
	 * 按照id查询员工请求
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Msg getEmp(@PathVariable("id")Integer id) {
		Employee employee = employeeService.getEmp(id);
		return Msg.success().add("emp", employee);
	}
	
	/**
	 * 检查用户名是否可用
	 * @param empName
	 * @return
	 */
	@RequestMapping(value = "/checkempName",method = RequestMethod.POST)
	@ResponseBody
	public Msg checkempName(@RequestParam("empName")String empName) {
		//先判断用户名是否是合法的表达式
		String regex = "(^[a-zA-Z0-9_-]{3,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if(!empName.matches(regex)){
			return Msg.fail().add("va_msg", "用户名可以是3——16位的英文和数字的组合或者是2——5位的中文");
		}
		boolean b = employeeService.checkEmployeeName(empName);
		if(b) {
			return Msg.success();
		}else {
			return Msg.fail().add("va_msg", "用户名不可用");
		}
	}
	
	/**
	 * 保存用户(后端的校验)
	 * 1.支持JSR303校验
	 * 2.导入Hibernate-Validator
	 * 3.
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/emp",method = RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee,BindingResult result) {
		if(result.hasErrors()) {
			//校验失败，应该返回失败，在模态框中显示错误的信息
			Map<String, Object> map = new HashMap<String, Object>();
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fieldError : errors) {
				//fieldError.getField():错误的字段名
				//fieldError.getDefaultMessage()：错误的信息
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		}else {
			employeeService.savaEmp(employee);
			return Msg.success();
		}
	}

		/**
		 * 导入Jackson包
		 * @param pn
		 * @param model
		 * @return
		 */
		@RequestMapping("/emps")
		@ResponseBody
		public Msg getEmpsWithJson(@RequestParam(value = "pn",defaultValue = "1")Integer pn) {
			//引入PageHelper分页插件
			//在查询之前只需要调用,传入页码，以及每页的大小
			PageHelper.startPage(pn, 10);
			//startPage后面紧跟的这个就是一个分页查询
			List<Employee> emps = employeeService.getALLEmployee();
			//使用PageInfo包装查询后的结果，只需要将PageInfo交给页面就行了。
			//封装了详细的信息，包括我们查询出来的数据
			PageInfo page = new PageInfo(emps,5);
			return Msg.success().add("pageInfo", page);
		}
	
	/**
	 * 查询员工数据（分页查询）
	 * @return
	 */
	//@RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pn",defaultValue = "1")Integer pn,Model model) {
		//引入PageHelper分页插件
		//在查询之前只需要调用,传入页码，以及每页的大小
		PageHelper.startPage(pn, 10);
		//startPage后面紧跟的这个就是一个分页查询
		List<Employee> emps = employeeService.getALLEmployee();
		//使用PageInfo包装查询后的结果，只需要将PageInfo交给页面就行了。
		//封装了详细的信息，包括我们查询出来的数据
		PageInfo page = new PageInfo(emps,5);
		model.addAttribute("pageInfo", page);
		return "list";
	}
}
