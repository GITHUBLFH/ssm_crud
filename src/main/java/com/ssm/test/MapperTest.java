package com.ssm.test;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ssm.bean.Department;
import com.ssm.bean.Employee;
import com.ssm.dao.DepartmentMapper;
import com.ssm.dao.EmployeeMapper;

/**
 * 测试dao层的工作
 * 推荐使用Spring的项目就可以使用Spring的单元测试，可以自动注入我们需要的组件
 * 1.导入SpringTest模块
 * 2.@ContextConfiguration指定Spring的配置文件的位置
 * 3.直接使用@Autowired要使用的组件即可
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {

	@Autowired
	DepartmentMapper departmentMapper;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	@Autowired
	SqlSession sqlSession;
	
	@Test
	public void test01() {
		//System.out.println(departmentMapper);
		
		//插入几个部门
//		departmentMapper.insertSelective(new Department(null,"开发部"));
//		departmentMapper.insertSelective(new Department(null,"测试部"));
		//插入几个员工
//		employeeMapper.insertSelective(new Employee(null,"Jerry","男","Jerry@qq.com",3));
//		employeeMapper.insertSelective(new Employee(null,"Tomcat","女","Tomcat@qq.com",3));
//		List<Employee> list = employeeMapper.selectByExampleWithDept(null);
//		for (Employee employee : list) {
//			System.out.println(employee);
//		}
		
		Employee employee = employeeMapper.selectByPrimaryKey(2);
		System.out.println(employee);
//		EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//		//批量的插入
//		for (int i = 0; i < 1000; i++) {
//			String sex = "女";
//			String substring = UUID.randomUUID().toString().substring(0, 5);
//			int j = i%3+1;
//			if(i%2==0) {
//				sex = "男";
//			}
//			mapper.insertSelective(new Employee(null,substring,sex,substring+"@qq.com",j));
//		}
//		System.out.println("批量完成。");
	}
	
}
