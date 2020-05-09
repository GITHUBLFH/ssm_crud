package com.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssm.bean.Employee;
import com.ssm.bean.EmployeeExample;
import com.ssm.bean.EmployeeExample.Criteria;
import com.ssm.dao.EmployeeMapper;
@Service
public class EmployeeService {

	@Autowired
	EmployeeMapper employeeMapper;
	
//	查询所有员工信息
	public List<Employee> getALLEmployee() {
		return employeeMapper.selectByExampleWithDept(null);
	}
//	员工保存
	public void savaEmp(Employee employee) {
		employeeMapper.insertSelective(employee);
	}
	//校验用户名的是否重复
	public boolean checkEmployeeName(String empName) {
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count = employeeMapper.countByExample(example);
		return count == 0;
	}
	//按照id查询员工
	public Employee getEmp(Integer id) {
		Employee employee = employeeMapper.selectByPrimaryKey(id);
		return employee;
	}
	//员工的更新方法
	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}
	//员工删除，根据id删除
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
	}
	public void deleteBathch(List<Integer> ids) {
		// TODO Auto-generated method stub
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmpIdIn(ids);
		employeeMapper.deleteByExample(example);
		
	}

}
