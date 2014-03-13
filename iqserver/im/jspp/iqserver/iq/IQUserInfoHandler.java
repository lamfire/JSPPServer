package im.jspp.iqserver.iq;

import com.lamfire.json.JSON;
import im.jspp.elements.ARGS;
import im.jspp.elements.ERROR;
import im.jspp.elements.IQ;
import im.jspp.elements.RESULT;
import im.jspp.iqserver.IQHandler;
import im.jspp.iqserver.NSNames;
import im.jspp.iqserver.annotation.NS;
import im.jspp.iqserver.dao.UserInfoDAO;
import im.jspp.iqserver.entity.UserInfo;


/**
 * 获得用户信息
 * User: lamfire
 * Date: 14-2-27
 * Time: 下午2:02
 * To change this template use File | Settings | File Templates.
 */
@NS(name = NSNames.NS_USER_INFO)
public class IQUserInfoHandler implements IQHandler {
    @Override
    public IQ execute(IQ iq) {
        ARGS args = iq.getArgs();
        String uid = args.getString("uid");

        UserInfoDAO dao = new UserInfoDAO();
        UserInfo info = dao.get(uid);
        if(info == null){
            iq.setType(IQ.TYPE_ERROR);
            iq.setError(new ERROR(404,"Not Found"));
            return iq;
        }

        RESULT result = new RESULT();
        result.putAll(JSON.fromJavaObject(info));
        iq.setResult(result);
        iq.setType(IQ.TYPE_RESULT);

        return iq;
    }
}
