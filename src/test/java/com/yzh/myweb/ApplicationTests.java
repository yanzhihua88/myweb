package com.yzh.myweb;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yzh.myweb.dao.UserRepository;
import com.yzh.myweb.entity.User2;
import com.yzh.myweb.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MywebApplication.class)
public class ApplicationTests {

	@Autowired
	private UserService userSerivce;

	@Before
	public void setUp() {
		// 准备，清空user表
	}

	@Test
	public void test() throws Exception {
		// 插入5个用户

	}

	@Autowired
	private UserRepository userRepository;

	@Test
	public void test1() throws Exception {

		// 创建10条记录
		userRepository.save(new User2("AAA", 10));
		userRepository.save(new User2("BBB", 20));
		userRepository.save(new User2("CCC", 30));
		userRepository.save(new User2("DDD", 40));
		userRepository.save(new User2("EEE", 50));
		userRepository.save(new User2("FFF", 60));
		userRepository.save(new User2("GGG", 70));
		userRepository.save(new User2("HHH", 80));
		userRepository.save(new User2("III", 90));
		userRepository.save(new User2("JJJ", 100));

		// 测试findAll, 查询所有记录
		Assert.assertEquals(10, userRepository.findAll().size());

		// 测试findByName, 查询姓名为FFF的User
		Assert.assertEquals(60, userRepository.findByName("FFF").getAge().longValue());

		// 测试findUser, 查询姓名为FFF的User
		Assert.assertEquals(60, userRepository.findUser("FFF").getAge().longValue());

		// 测试findByNameAndAge, 查询姓名为FFF并且年龄为60的User
		Assert.assertEquals("FFF", userRepository.findByNameAndAge("FFF", 60).getName());

		// 测试删除姓名为AAA的User
		userRepository.delete(userRepository.findByName("AAA"));

		// 测试findAll, 查询所有记录, 验证上面的删除是否成功
		Assert.assertEquals(9, userRepository.findAll().size());

	}

}