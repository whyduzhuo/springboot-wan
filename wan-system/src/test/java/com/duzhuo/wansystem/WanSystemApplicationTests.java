package com.duzhuo.wansystem;

import com.duzhuo.common.utils.BeanUtils;
import com.duzhuo.common.utils.Tools;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WanSystemApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void haha(){

		Object[] args = new Object[]{"哈哈"};
		BeanUtils.ceshi(Tools.class,"test",args);
	}

	@Test
	public void hehe(){
		System.err.println("dwad");
	}
}
