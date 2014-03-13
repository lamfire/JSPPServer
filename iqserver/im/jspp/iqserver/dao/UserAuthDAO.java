package im.jspp.iqserver.dao;

import com.lamfire.mongodb.DAOSupport;
import im.jspp.iqserver.entity.UserAuth;

/**
 * 身份认证数据库访问接口
 * User: lamfire
 * Date: 14-2-27
 * Time: 上午11:21
 * To change this template use File | Settings | File Templates.
 */
public class UserAuthDAO extends DAOSupport<UserAuth,String> {

    public UserAuthDAO() {
        super("user", "USER");
    }
}
