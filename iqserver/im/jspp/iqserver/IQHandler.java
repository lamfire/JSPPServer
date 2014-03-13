package im.jspp.iqserver;


import im.jspp.elements.IQ;

/**
 * IQ服务接口
 * User: lamfire
 * Date: 14-2-17
 * Time: 上午10:44
 * To change this template use File | Settings | File Templates.
 */
public interface IQHandler {
    public IQ execute(IQ iq);
}
