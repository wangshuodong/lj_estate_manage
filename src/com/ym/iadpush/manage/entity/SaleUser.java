/**
 * 
 */
package com.ym.iadpush.manage.entity;

/**业务员
 * @Copyright: 本内容仅限于重庆爱赢科技有限公司内部使用，禁止转发. 
 * @Author: ly 2017年8月1日 上午10:48:39 
 * @Version: $Id$
 * @Desc: <p></p>
 */
public class SaleUser {
    private int id;
    private String name;
    private String phone;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
