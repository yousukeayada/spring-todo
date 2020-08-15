package com.yousukeayada.springtodo.service;

import java.util.ArrayList;
import java.util.List;

import com.yousukeayada.springtodo.dao.LoginUserDao;
import com.yousukeayada.springtodo.entity.LoginUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * -Impl とするのは慣例
 * Spring Securityのユーザ検索用のサービスの実装クラス
 * WebSecurityConfig の configure メソッドで使われる
 * DataSourceの引数として指定することで認証にDBを利用できるようになる
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	//DBからユーザ情報を検索するメソッドを実装したクラス
	@Autowired
	private LoginUserDao userDao;
	
	/**
	 * UserDetailsServiceインタフェースの実装メソッド
	 * フォームから取得したユーザ名でDBを検索し、合致するものが存在したとき、
	 * パスワード、権限情報と共にUserDetailsオブジェクトを生成
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        
        System.out.println("username: " + userName);

        // select * from user where username = userName を実行
		LoginUser user = userDao.findUser(userName);
		
		if (user == null) {
			throw new UsernameNotFoundException(userName + ": not found");
        }
        System.out.println("user: " + user);
		//権限のリスト
		//AdminやUserなどが存在するが、今回は利用しないのでUSERのみを仮で設定
		//権限を利用する場合は、DB上で権限テーブル、ユーザ権限テーブルを作成し管理が必要
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		GrantedAuthority authority = new SimpleGrantedAuthority("USER");
		grantList.add(authority);
		
		//rawDataのパスワードは渡すことができないので、暗号化
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
        // loadUserByUsernameではUserDetails型を返却する必要がある。
        // ここで使っているのは自作のUserクラスではなく、Spring が用意しているUserクラス。
        // UserDetailsをimplementしており、そのユーザが使用可能か、有効期限切れかなど、様々なフィールドをもつ。
        // ここではユーザ名、パスワード、権限のリストを引数にとるコンストラクタを使用。
		UserDetails userDetails = (UserDetails)new User(user.getUserName(), encoder.encode(user.getPassword()),grantList);
		
		return userDetails;
	}

}