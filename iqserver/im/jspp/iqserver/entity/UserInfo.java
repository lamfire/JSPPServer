package im.jspp.iqserver.entity;

import com.lamfire.mongodb.morphia.annotations.Entity;
import com.lamfire.mongodb.morphia.annotations.Id;
import com.lamfire.mongodb.morphia.annotations.Indexed;

/**
 * 用户信息
 * User: lamfire
 * Date: 14-2-27
 * Time: 上午10:05
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class UserInfo {

    @Id
    private String id;   //UUID

    private String nickname;

    private String avatar;

    private Integer gender;

    private String mood;

    @Indexed
    private Integer age;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
