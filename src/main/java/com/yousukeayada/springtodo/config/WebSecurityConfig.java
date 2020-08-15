package com.yousukeayada.springtodo.config;

import com.yousukeayada.springtodo.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	//パスワード暗号化に利用
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        // 静的リソース(img,js,css)を認可処理の対象から除外する
        web.ignoring().antMatchers("/img/**", "/css/**", "/js/**");
    }
	
	/**
	 * 認証・認可の情報を設定する
	 * 画面遷移のURL・パラメータを取得するname属性の値を設定
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
            .authorizeRequests()
                .antMatchers("/home").permitAll() //home は誰でもアクセス可能
                .antMatchers("/admin").hasRole("ADMIN") //admin は認証を通り、かつ ADMIN 権限をもつユーザのみ。hasAnyRolesで複数権限も設定可能。
		        .anyRequest().authenticated() //その他は認証が必要。認証さえ通れば権限は不要。
		        .and()
		    .formLogin()
		        .loginPage("/login") //ログインページはコントローラを経由しないのでViewNameとの紐付けが必要
		        .loginProcessingUrl("/sign_in") //login.htmlでこのパスを指定する。このURLへリクエストが送られると認証処理が実行される
		        .usernameParameter("username") //login.htmlのフォームのname属性を明示
		        .passwordParameter("password")
		        .successForwardUrl("/")
		        .failureUrl("/login?error") //login.html内の${param.error}で参照される
		        .permitAll()
		        .and()
		    .logout()
		        .logoutUrl("/logout")
		        .logoutSuccessUrl("/login?logout") //login.html内の${param.logout}で参照される
		        .permitAll();
	}
	
	/**
	 * 認証時に利用するデータソースを定義する設定メソッド
	 * ここではDBから取得したユーザ情報をuserDetailsServiceへセットすることで認証時の比較情報としている
	 */
	@Autowired
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
        // loadUserByUsernameが呼ばれる（UserDetailsServiceImplでオーバライドしてる）
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        
        // インメモリDBの場合
        // 指定したユーザ名、パスワード、権限がインメモリに保存される。
        // 本当はパスワードのハッシュ化が必要。passwordEncoder().encode("pass")
		// auth
		//     .inMemoryAuthentication()
        //         .withUser("user").password("pass").roles("USER")
        //         .and()
        //         .withUser("admin").password("pass2").roles("ADMIN");
        
	}

}