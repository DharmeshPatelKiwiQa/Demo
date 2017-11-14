package com.Framework.Index;

import org.testng.annotations.Test;

import com.Framework.Init.Common;
import com.Framework.Init.SeleniumInit;

public class Index extends SeleniumInit{
	
	@Test
	public void DemoMethod()
	{
		Common.pause(3);
		System.out.println("Script Done");
		Common.log("========  Script Done   ==========");
	}

}
