package com.gupaoedu.vip.aop.test;

import com.gupaoedu.vip.aop.aspect.ArgsAspect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gupaoedu.vip.aop.service.MemberService;
import com.gupaoedu.vip.model.Member;

@ContextConfiguration(locations = {"classpath*:application-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ArgsTest {
	@Autowired MemberService annotationService;
	@Autowired ApplicationContext app;
	
	
	@Test
//	@Ignore
	public void test(){
		
		System.out.println("=====这是一条华丽的分割线======");
		ArgsAspect aspect = app.getBean(ArgsAspect.class);
		System.out.println(aspect);
		annotationService.save(new Member());
		
		System.out.println("=====这是一条华丽的分割线======");
		annotationService.get();
		
		
	}
	
}
