package im.jspp.messagerouter;


import im.jspp.elements.JSPP;

/**
 * Created with IntelliJ IDEA.
 * User: lamfire
 * Date: 14-2-20
 * Time: 上午10:48
 * To change this template use File | Settings | File Templates.
 */
interface Router {

    public void route(JSPP message);
}
