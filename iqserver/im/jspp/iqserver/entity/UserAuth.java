package im.jspp.iqserver.entity;

import com.lamfire.mongodb.morphia.annotations.Entity;
import com.lamfire.mongodb.morphia.annotations.Id;

/**
 * 用户身份认证
 * User: lamfire
 * Date: 14-2-27
 * Time: 上午10:30
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class UserAuth {
    @Id
    private String username;

    private String password;

    private String userId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
