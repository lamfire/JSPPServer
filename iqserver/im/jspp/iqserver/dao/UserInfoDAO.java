package im.jspp.iqserver.dao;

import com.lamfire.mongodb.DAOSupport;
import im.jspp.iqserver.entity.UserInfo;

/**
 * 用户信息数据库访问接口
 * User: lamfire
 * Date: 14-2-27
 * Time: 上午11:21
 * To change this template use File | Settings | File Templates.
 */
public class UserInfoDAO extends DAOSupport<UserInfo,String> {

    public UserInfoDAO() {
        super("user", "USER");
    }

    public static void main(String[] args) {
        UserInfoDAO dao = new UserInfoDAO();
        dao.count();
    }
}
