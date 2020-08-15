package com.yousukeayada.springtodo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebSecurityConfig = ログインコントローラ
 * ログイン画面のコントローラの役割はWebSecurityConfigが担っているので、
 * ViewNameと画面の対応づけを行うために実装
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {
	
	/**
     * Spring ではデフォルトでlogin.htmlが用意されているが、今回は自作する
	 * 「/login」をlogin.htmlに対応させる
	 */
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
	}

}