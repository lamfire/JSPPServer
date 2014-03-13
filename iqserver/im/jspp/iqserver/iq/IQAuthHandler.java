package im.jspp.iqserver.iq;

import com.lamfire.code.PUID;
import com.lamfire.utils.StringUtils;
import im.jspp.elements.ARGS;
import im.jspp.elements.ERROR;
import im.jspp.elements.IQ;
import im.jspp.elements.RESULT;
import im.jspp.iqserver.IQHandler;
import im.jspp.iqserver.NSNames;
import im.jspp.iqserver.annotation.NS;
import im.jspp.iqserver.dao.UserAuthDAO;
import im.jspp.iqserver.entity.UserAuth;

/**
 * 用户身份验证
 * User: lamfire
 * Date: 14-2-17
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
@NS(name = NSNames.NS_USER_AUTH)
public class IQAuthHandler implements IQHandler {

    @Override
    public IQ execute(IQ iq) {
        ARGS args = iq.getArgs();
        String username = args.getString("accounts");
        String password = args.getString("password");

        //查找账号
        UserAuthDAO dao = new UserAuthDAO();
        UserAuth auth = dao.get(username);
        if(auth == null){
            iq.setType(IQ.TYPE_ERROR);
            iq.setError(new ERROR(404,"Not Found"));
            return iq;
        }

        //验证用户名密码正确
        if(StringUtils.equals(password, auth.getPassword())){
            RESULT result = new RESULT();
            result.put("token", PUID.puidAsString());
            result.put("uid",auth.getUserId());
            iq.setResult(result);
            iq.setType(IQ.TYPE_RESULT);
            return iq;
        }

        //验证失败
        iq.setType(IQ.TYPE_ERROR);
        iq.setError(new ERROR(401,"Accounts Auth Failed"));
        return iq;
    }
}
