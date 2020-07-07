package com.study.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor // lombok注解，生成一个无参数的构造方法
public class UserEntity implements Serializable {

	private static final long serialVersionUID = -1649080014488800810L;

	protected Integer id;
	
	/**
	* 登录名称
	*/
	private String loginName;

	/**
	 * 密码
	 */
	private String loginPasswd;

	/**
	 * 用户昵称
	 */
	private String userName;

	/**
	 * 盐值
	 */
	private String salt;

	/**
	 * 用户状态 0 删除 1 正常  2停用  3 锁定
	 */
	private Short userStatus;

	/**
	 * 用户电话
	 */
	private String userPhone;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 最后修改时间
	 */
	private Date updateTime;

	/**
	 * 微信唯一id
	 */
	private String openid;

	/**
	 * 用户备注
	 */
	private String remark;

	/**
	 * 用户登录失败次数
	 */
	private Short failCount;

	/**
	 * 用户登录成功生成的token
	 */
	private String token;

	/**
	 * 用户类型：0内部用户 1公众用户
	 */
	private Short userType;

	private Integer ownerId;// 业主id

	private String ownerName; // 业主名称

	private String code;// 区域编码

	private String city;// 城市名

	/**
	 * 所属部门id 
	 */
	private Integer orgId;

	/**
	 * 所属部门名称
	 */
	private String orgName;

	private Set<String> permission;
	
	private Set<Integer> permissionId;

	private Set<Integer> deptIds;

	private Set<Integer> roleIds;
	
	private int randomHashCode;

	protected Integer createBy;
	
	protected Integer editBy;
	
	protected Date editTime;

	/**
	* 数据版本控制
	*/
	protected Integer version;
	/**
	* 逻辑删除 0未删除，1删除
	*/
	protected Integer delFlag;

}
