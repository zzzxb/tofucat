package xyz.zzzxb.tofucat.protocol.cmpp.cmpp2;



/**
 * @author zzzxb
 * 2024/11/19
 */
public class ServiceFactory {

    public static CtxProcess getService(ServiceType serviceType) {
        return serviceType.get();
    }
}