package im.jspp.iqserver.iq;

import com.lamfire.code.PUID;
import im.jspp.elements.ARGS;
import im.jspp.elements.ERROR;
import im.jspp.elements.IQ;
import im.jspp.elements.RESULT;
import im.jspp.iqserver.IQHandler;
import im.jspp.iqserver.NSNames;
import im.jspp.iqserver.annotation.NS;
import im.jspp.iqserver.dao.UserAuthDAO;
import im.jspp.iqserver.dao.UserInfoDAO;
import im.jspp.iqserver.entity.UserAuth;
import im.jspp.iqserver.entity.UserInfo;


/**
 * 用户注册
 * User: lamfire
 * Date: 14-2-17
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
@NS(name = NSNames.NS_USER_REGISTER)
public class IQRegisterHandler implements IQHandler {
    @Override
    public IQ execute(IQ iq) {
        ARGS args = iq.getArgs();
        String username = args.getString("accounts");
        String password = args.getString("password");
        String nickname = args.getString("nickname");
        int gender = args.getInteger("gender");


        //查找账号
        UserAuthDAO dao = new UserAuthDAO();
        UserAuth auth = dao.get(username);
        if(auth != null){
            iq.setType(IQ.TYPE_ERROR);
            iq.setError(new ERROR(401,"The Accounts Was Exists"));
            return iq;
        }

        //注册
        UserInfo info = new UserInfo();
        info.setId(PUID.puidAsString());
        info.setGender(gender);
        info.setNickname(nickname);
        UserInfoDAO infoDAO = new UserInfoDAO();
        infoDAO.save(info);

        auth = new UserAuth();
        auth.setUserId(info.getId());
        auth.setUsername(username);
        auth.setPassword(password);
        dao.save(auth);

        //成功
        RESULT result = new RESULT();
        result.put("uid",auth.getUserId());
        iq.setType(IQ.TYPE_RESULT);
        iq.setResult(result);
        return iq;
    }
}
